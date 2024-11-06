package axgiri.github.DocumentService.Service;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
public class FileNameService {

    public String generateFileName(String insuranceType, String insurancePackage, Long govId, LocalDate startDate, LocalDate endDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyyy");
        String start = startDate.format(formatter);
        String end = endDate.format(formatter);
        return String.format("%s_%s_insurance_contract_with_%d_from_%s_to_%s.pdf",
                insuranceType.toLowerCase(),
                insurancePackage.toLowerCase(),
                govId,
                start,
                end);
    }
}
