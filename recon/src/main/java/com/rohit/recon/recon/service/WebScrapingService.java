package com.rohit.recon.recon.service;

import com.rohit.recon.recon.config.ScraperUrlProperties;
import com.rohit.recon.recon.domain.DonationSearchResult;
import com.rohit.recon.recon.domain.ScraperJobRun;
import com.rohit.recon.recon.dto.DonationSearchResultDto;
import com.rohit.recon.recon.dto.DonationSummary;
import com.rohit.recon.recon.mapper.DonationSearchResultMapper;
import com.rohit.recon.recon.repo.DonationSearchResultRepository;
import com.rohit.recon.recon.repo.ScraperJobRunRepository;
import io.github.bonigarcia.wdm.WebDriverManager;
import lombok.AllArgsConstructor;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@AllArgsConstructor
public class WebScrapingService {

    private final ScraperUrlProperties props;
    private final ScraperConfigService scraperConfigService;
    private final DonationSearchResultRepository donationSearchResultRepository;
    private final ScraperJobRunRepository scraperJobRunRepository;

    @Scheduled(cron = "0 0 0 1 1,4,7,10 ?") // Run quarterly: Jan, Apr, Jul, Oct on 1st at 00:00
    public void searchSummary() {
        System.out.println("⏰ Scheduled scraping triggered...");
        List<DonationSummary> donationSummaryList = getSearchSummary();
        // Optional: Save to DB or file here
    }

    public List<DonationSummary> getSearchSummary() {
        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--no-sandbox", "--disable-dev-shm-usage", "user-agent=Mozilla/5.0");
        // options.addArguments("--headless");

        WebDriver driver = new ChromeDriver(options);
        try {
            //String fullUrl = props.getUrl();
            String fullUrl = scraperConfigService.getEffectiveUrl();
            System.out.println("Navigating to: " + fullUrl);
            driver.get(fullUrl);

            return extractDonationSummary(driver);

        } catch (Exception e) {
            System.err.println("Scraping error: " + e.getMessage());
            return List.of(new DonationSummary("ERROR", "0", e.getMessage()));
        } finally {
            driver.quit();
        }
    }

    private List<DonationSummary> extractDonationSummary(WebDriver driver) {
        List<DonationSummary> summaryList = new java.util.ArrayList<>();

        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#tab-1 table")));

            WebElement table = driver.findElement(By.cssSelector("#tab-1 table"));
            List<WebElement> rows = table.findElements(By.cssSelector("tbody tr"));

            for (WebElement row : rows) {
                List<WebElement> cells = row.findElements(By.tagName("td"));
                if (cells.size() == 3) {
                    summaryList.add(new DonationSummary(
                            cells.get(0).getText(),
                            cells.get(1).getText(),
                            cells.get(2).getText()
                    ));
                }
            }

            WebElement footer = table.findElement(By.cssSelector("tfoot tr"));
            List<WebElement> footCells = footer.findElements(By.tagName("td"));
            if (footCells.size() == 3) {
                summaryList.add(new DonationSummary(
                        footCells.get(0).getText(),
                        footCells.get(1).getText(),
                        footCells.get(2).getText()
                ));
            }

        } catch (Exception e) {
            System.err.println("Error extracting donation summary: " + e.getMessage());
            summaryList.add(new DonationSummary("ERROR", "0", e.getMessage()));
        }

        return summaryList;
    }


    /**
     * Donation Search Results
     * @return
     */


    public List<DonationSearchResultDto> extractSearchResults() {
        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--no-sandbox", "--disable-dev-shm-usage", "user-agent=Mozilla/5.0");
        // options.addArguments("--headless");

        WebDriver driver = new ChromeDriver(options);
        boolean success = false;

        List<DonationSearchResultDto> dtoList = Collections.emptyList();

        String errorMessage = null;

        try {
            //String fullUrl = props.getUrl();
            String fullUrl = scraperConfigService.getEffectiveUrl();
            driver.get(fullUrl);

            // Wait for full page load
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
            wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("div#panelResults")));

            dtoList = extractAllDonationSearchResults(driver);

            // Save to DB
            List<DonationSearchResult> entities = DonationSearchResultMapper.toEntityList(dtoList);
            donationSearchResultRepository.saveAll(entities);

            success = true;
            return dtoList; // this is your earlier method
        } catch (Exception e) {
            e.printStackTrace();
            success = false;
            errorMessage = e.getMessage();
            return dtoList;
        } finally {
            scraperJobRunRepository.save(
                    ScraperJobRun.builder()
                            .jobType("donation-search-results")
                            .runTime(LocalDateTime.now())
                            .success(success)
                            .recordCount(dtoList.size())
                            .errorMessage(errorMessage)
                            .build()
            );
            driver.quit();
        }
    }

    private List<DonationSearchResultDto> extractDonationSearchResult(WebDriver driver) {
        List<DonationSearchResultDto> results = new ArrayList<>();
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div#panelResults table")));

            WebElement table = driver.findElement(By.cssSelector("div#panelResults table"));
            List<WebElement> rows = table.findElements(By.cssSelector("tbody tr"));

            for (WebElement row : rows) {
                List<WebElement> cells = row.findElements(By.tagName("td"));
                if (cells.size() >= 14) {  // Adjust as per column count in actual table
                    DonationSearchResultDto dto = new DonationSearchResultDto();

                    // Extracting fields by position
                    dto.setDetailLink(cells.get(0).findElement(By.tagName("a")).getAttribute("href"));
                    dto.setEntityName(cells.get(1).getText());
                    dto.setRegister(cells.get(2).getText());
                    dto.setCampaigningName(cells.get(3).getText());
                    dto.setEntityType(cells.get(4).getText());
                    dto.setValue(cells.get(5).getText());
                    dto.setAcceptedDate(cells.get(6).getText());
                    dto.setReceivedBy(cells.get(7).getText());
                    dto.setDonorName(cells.get(8).getText());
                    dto.setReportedUnder6212(cells.get(9).getText());
                    dto.setIsSponsorship(cells.get(10).getText());
                    dto.setDonorStatus(cells.get(11).getText());
                    dto.setIsIrishSource(cells.get(12).getText());
                    dto.setRegulatedDoneeType(cells.get(13).getText());
                    dto.setCompanyRegNo(cells.size() > 14 ? cells.get(14).getText() : "");
                    dto.setPostcode(cells.size() > 15 ? cells.get(15).getText() : "");
                    dto.setDonationType(cells.size() > 16 ? cells.get(16).getText() : "");

                    results.add(dto);
                }
            }
        } catch (Exception e) {
            System.err.println("Error extracting search result data: " + e.getMessage());
        }
        return results;
    }

    private List<DonationSearchResultDto> extractAllDonationSearchResults(WebDriver driver) {
        List<DonationSearchResultDto> allResults = new ArrayList<>();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        try {
            while (true) {
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div#panelResults table")));
                WebElement table = driver.findElement(By.cssSelector("div#panelResults table"));
                List<WebElement> rows = table.findElements(By.cssSelector("tbody tr"));

                for (WebElement row : rows) {
                    List<WebElement> cells = row.findElements(By.tagName("td"));
                    if (cells.size() >= 14) {
                        DonationSearchResultDto dto = new DonationSearchResultDto();
                        dto.setDetailLink(cells.get(0).findElement(By.tagName("a")).getAttribute("href"));
                        dto.setEntityName(cells.get(1).getText());
                        dto.setRegister(cells.get(2).getText());
                        dto.setCampaigningName(cells.get(3).getText());
                        dto.setEntityType(cells.get(4).getText());
                        dto.setValue(cells.get(5).getText());
                        dto.setAcceptedDate(cells.get(6).getText());
                        dto.setReceivedBy(cells.get(7).getText());
                        dto.setDonorName(cells.get(8).getText());
                        dto.setReportedUnder6212(cells.get(9).getText());
                        dto.setIsSponsorship(cells.get(10).getText());
                        dto.setDonorStatus(cells.get(11).getText());
                        dto.setIsIrishSource(cells.get(12).getText());
                        dto.setRegulatedDoneeType(cells.get(13).getText());
                        dto.setCompanyRegNo(cells.size() > 14 ? cells.get(14).getText() : "");
                        dto.setPostcode(cells.size() > 15 ? cells.get(15).getText() : "");
                        dto.setDonationType(cells.size() > 16 ? cells.get(16).getText() : "");

                        allResults.add(dto);
                    }
                }

                // Check "Next" button's parent li class
                WebElement nextLi = driver.findElement(By.xpath("//a[contains(text(), 'Next')]/parent::li"));
                String liClass = nextLi.getAttribute("class");
                if (liClass.contains("disabled")) {
                    break; // No more pages
                }

                // Click "Next"
                WebElement nextBtn = nextLi.findElement(By.tagName("a"));
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", nextBtn);

                Thread.sleep(2000); // Let the page load
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return allResults;
    }

    private WebDriver createWebDriver() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--no-sandbox", "--disable-dev-shm-usage", "user-agent=Mozilla/5.0");
        // options.addArguments("--headless"); // consider toggling via properties
        return new ChromeDriver(options);
    }

}
