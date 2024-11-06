package axgiri.github.DocumentService.Controller;

import java.io.IOException;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.itextpdf.text.DocumentException;

import axgiri.github.DocumentService.Request.PolicyResponse;
import axgiri.github.DocumentService.Request.PurchaseRequest;
import axgiri.github.DocumentService.Service.FileNameService;
import axgiri.github.DocumentService.Service.PDFGenerationService;
import axgiri.github.DocumentService.Service.PolicyService;

@RestController
@RequestMapping("/api/document")
public class PDFController {

    private final PolicyService policyService;
    private final PDFGenerationService pdfGenerationService;
    private final FileNameService fileNameService;

    @Autowired
    public PDFController(PolicyService policyService,
            PDFGenerationService pdfGenerationService,
            FileNameService fileNameService) {
        this.policyService = policyService;
        this.pdfGenerationService = pdfGenerationService;
        this.fileNameService = fileNameService;
    }

    @GetMapping("/pdf")
    public ResponseEntity<byte[]> getPDF(@RequestParam Long gov_id, @RequestParam Long policy_id)
            throws DocumentException {
        try {
            PurchaseRequest purchaseRequest = new PurchaseRequest();
            purchaseRequest.setGov_id(gov_id);
            purchaseRequest.setPolicy_id(policy_id);

            PolicyResponse policy = policyService.getPolicyDetails(policy_id);
            if (policy == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }

            byte[] pdfBytes = pdfGenerationService.generatePDF(purchaseRequest, policy);

            LocalDate today = LocalDate.now();
            LocalDate nextYear = today.plusYears(1);
            String fileName = fileNameService.generateFileName(
                    policy.getInsuranceType(),
                    policy.getInsurancePackage(),
                    gov_id,
                    today,
                    nextYear);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("attachment", fileName);
            headers.setContentLength(pdfBytes.length);

            return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);

        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
