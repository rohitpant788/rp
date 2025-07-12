package com.rohit.recon.recon.service;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

public class HelperService {

    /*    *//*
    This method scrapes the "Type of donation" summary table that is displayed on the Electoral Commission website under the “Summary” tab. It extracts:
    Table headers
    Each row in the body (<tbody>)
    The total row from the footer (<tfoot>)
     *//*
    private void extractDonationSummary(WebDriver driver) {
        try {
            // Wait until the donation summary table is visible inside tab-1
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

            //Wait until a <table> inside the element with id="tab-1" is visible.
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#tab-1 table")));

            //gets a reference to the table element so we can parse it.
            WebElement table = driver.findElement(By.cssSelector("#tab-1 table"));

            //his collects all <th> elements inside the <thead> of the table.
            //These are the column headers: "Description", "No.", "Total value"
            List<WebElement> headers = table.findElements(By.cssSelector("thead th"));

            *//*
                Prints the table header row in aligned columns:
                %-40s → left-aligned 40-character string
                %6s → right-aligned 6-character string
                %20s → right-aligned 20-character string
             *//*
            System.out.printf("\n%-40s | %-6s | %-20s\n", headers.get(0).getText(), headers.get(1).getText(), headers.get(2).getText());

            //=.repeat(75) draws a divider line like a table border.
            System.out.println("=".repeat(75));

            *//*
                This collects all table rows (<tr>) in the <tbody> section.
                These rows contain the donation types and values.
             *//*
            List<WebElement> rows = table.findElements(By.cssSelector("tbody tr"));

            for (WebElement row : rows) {
                //Gets all <td> (columns) in the current row.
                List<WebElement> cells = row.findElements(By.tagName("td"));

                *//*
                    Ensures the row has exactly 3 cells — one for each column:
                    Description
                    No. (count)
                    Total value
                 *//*
                if (cells.size() == 3) {
                    String type = cells.get(0).getText();
                    String count = cells.get(1).getText();
                    String value = cells.get(2).getText();
                    System.out.printf("%-40s | %-6s | %-20s\n", type, count, value);
                }
            }



            *//*
                After processing the body rows, it selects the footer row.
                This contains the total sum row (i.e., grand total of all donations).
             *//*
            WebElement footer = table.findElement(By.cssSelector("tfoot tr"));

            //Gets all 3 columns in the footer row.
            List<WebElement> footCells = footer.findElements(By.tagName("td"));
            if (footCells.size() == 3) {
                String label = footCells.get(0).getText();
                String totalCount = footCells.get(1).getText();
                String totalValue = footCells.get(2).getText();
                System.out.println("=".repeat(75));
                System.out.printf("%-40s | %-6s | %-20s\n", label, totalCount, totalValue);
            }

        } catch (Exception e) {
            System.err.println("Error extracting donation summary: " + e.getMessage());
        }
    }*/


    private String buildQueryUrl(String baseUrl, Map<String, Object> params) {
        StringBuilder url = new StringBuilder(baseUrl).append("?");

        for (Map.Entry<String, Object> entry : params.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();

            if (value instanceof List<?>) {
                for (Object val : (List<?>) value) {
                    url.append(key).append("=").append(encode(val.toString())).append("&");
                }
            } else {
                url.append(key).append("=").append(encode(value.toString())).append("&");
            }
        }

        if (url.charAt(url.length() - 1) == '&') {
            url.deleteCharAt(url.length() - 1);
        }

        return url.toString();
    }

    private String encode(String value) {
        return URLEncoder.encode(value, StandardCharsets.UTF_8);
    }
}
