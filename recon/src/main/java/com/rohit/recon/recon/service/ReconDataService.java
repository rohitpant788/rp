package com.rohit.recon.recon.service;

import com.rohit.recon.recon.domain.DonationSearchResult;
import com.rohit.recon.recon.domain.ReconDataCase;
import com.rohit.recon.recon.repo.DonationSearchResultRepository;
import com.rohit.recon.recon.repo.ReconDataCaseRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class ReconDataService {

    private final DonationSearchResultRepository donationSearchResultRepository;
    private final ReconDataCaseRepository reconDataCaseRepository;
    private final CamundaService camundaService;

    public void createReconCasesFromSearchResults() {
        List<DonationSearchResult> allResults = donationSearchResultRepository.findAll();

        for (DonationSearchResult result : allResults) {
            if (!reconDataCaseRepository.existsByDetailLink(result.getDetailLink())) {
                ReconDataCase newCase = new ReconDataCase();

                // Copy fields from DonationSearchResult
                newCase.setDetailLink(result.getDetailLink());
                newCase.setEntityName(result.getEntityName());
                newCase.setRegister(result.getRegister());
                newCase.setCampaigningName(result.getCampaigningName());
                newCase.setEntityType(result.getEntityType());
                newCase.setValue(result.getValue());
                newCase.setAcceptedDate(result.getAcceptedDate());
                newCase.setReceivedBy(result.getReceivedBy());
                newCase.setDonorName(result.getDonorName());
                newCase.setReportedUnder6212(result.getReportedUnder6212());
                newCase.setIsSponsorship(result.getIsSponsorship());
                newCase.setDonorStatus(result.getDonorStatus());
                newCase.setIsIrishSource(result.getIsIrishSource());
                newCase.setRegulatedDoneeType(result.getRegulatedDoneeType());
                newCase.setCompanyRegNo(result.getCompanyRegNo());
                newCase.setPostcode(result.getPostcode());
                newCase.setDonationType(result.getDonationType());
                newCase.setStatus("DRAFT");
                newCase.setCreatedAt(LocalDateTime.now());

                // Save to get generated ID
                ReconDataCase savedCase = reconDataCaseRepository.save(newCase);

                // Generate custom caseId e.g. REC000000123
                String caseId = generateCaseId(savedCase.getId());
                savedCase.setCaseId(caseId);

                // Start Camunda 7 Workflow
                String processInstanceId = camundaService.startWorkflow(caseId);
                savedCase.setProcessInstanceId(processInstanceId);

                // Save updated fields
                reconDataCaseRepository.save(savedCase);
            }
        }
    }

    private String generateCaseId(Long id) {
        return String.format("REC%09d", id); // REC000000001
    }
}
