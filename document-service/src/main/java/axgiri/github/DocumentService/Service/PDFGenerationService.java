package axgiri.github.DocumentService.Service;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import axgiri.github.DocumentService.Request.PolicyResponse;
import axgiri.github.DocumentService.Request.PurchaseRequest;

import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
public class PDFGenerationService {

    public byte[] generatePDF(PurchaseRequest purchaseRequest, PolicyResponse policy)
            throws IOException, DocumentException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Document document = new Document();
        PdfWriter.getInstance(document, baos);
        document.open();

        Font boldFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12);
        Font normalFont = FontFactory.getFont(FontFactory.HELVETICA, 12);

        String insuranceType = policy.getInsuranceType().toLowerCase();
        String insurancePackage = policy.getInsurancePackage().toLowerCase();
        Long govId = purchaseRequest.getGovId();
        LocalDate today = LocalDate.now();
        LocalDate nextYear = today.plusYears(1);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

        document.add(new Paragraph(String.format("%s %s package contract with id: %d",
                insuranceType, insurancePackage, govId), boldFont));

        document.add(new Paragraph("Date of conclusion of the agreement: " + today.format(formatter), normalFont));

        document.add(new Paragraph("\nThe parties to the agreement:", boldFont));

        document.add(new Paragraph(
                "Insurance company: LLC \"github.com/axgiri\", represented by the CEO _______________, acting on the basis of the Charter, hereinafter referred to as the \"Insurer\".",
                normalFont));
        document.add(new Paragraph(
                "The Insured person: _______________, hereinafter referred to as the \"Policyholder\".", normalFont));

        document.add(new Paragraph("\nThe subject of the contract:", boldFont));

        document.add(new Paragraph(String.format(
                "The Insurer undertakes to provide insurance protection for the Insured's vehicle under the %s program for the established insurance premium, and the Policyholder undertakes to pay insurance premiums in a timely manner and comply with the terms of this agreement.",
                capitalizeFirstLetter(insurancePackage)), normalFont));

        document.add(new Paragraph("\nThe main terms of the agreement:", boldFont));

        document.add(new Paragraph("Insurance period:", boldFont));
        document.add(new Paragraph(String.format(
                "The insurance period is 1 (one) year from %s to %s inclusive.",
                today.format(formatter), nextYear.format(formatter)), normalFont));

        document.add(new Paragraph("\nConsent to the processing of personal data:", boldFont));
        document.add(new Paragraph(
                "By signing this agreement, the Policyholder agrees to the processing, storage and use of his personal data, including passport data and vehicle information, by the Insurer solely for the purpose of fulfilling the terms of this agreement and in accordance with the legislation of the Russian Federation.",
                normalFont));

        document.add(new Paragraph("\nResponsibility of the parties:", boldFont));
        document.add(new Paragraph(
                "The Policyholder undertakes to promptly report all incidents related to the occurrence of insured events and comply with the operating conditions of the vehicle. In the event of an insured event, the Insurer undertakes to fulfill its obligations under this agreement and in accordance with the Premium program.",
                normalFont));

        document.add(new Paragraph("\nFinal provisions:", boldFont));
        document.add(new Paragraph(
                "The agreement is drawn up in two copies, one for each party. Both copies have equal legal force.",
                normalFont));

        document.add(new Paragraph("\nSignatures of the parties:", boldFont));

        document.add(new Paragraph("\n____________________ / Policyholder /", normalFont));
        document.add(new Paragraph("____________________ / Representative of the Insurer Picture", normalFont));

        document.close();
        return baos.toByteArray();
    }

    private String capitalizeFirstLetter(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }
}
