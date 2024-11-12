package github.axgiri.PurchaseService.Service;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.hibernate.engine.jdbc.BlobProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import github.axgiri.PurchaseService.DTO.PurchaseDTO;
import github.axgiri.PurchaseService.Enum.StatusEnum;
import github.axgiri.PurchaseService.Model.Purchase;
import github.axgiri.PurchaseService.Repository.PurchaseRepository;

@Service
public class PurchaseService {
    
    private static final Logger logger = LoggerFactory.getLogger(PurchaseService.class);
    
    private final PurchaseRepository repository;

    @Autowired
    public PurchaseService(PurchaseRepository repository){
        this.repository = repository;
    }

    public List<PurchaseDTO> get(){
        logger.info("fetching all purchases");
        List<Purchase> purchases = repository.findAll();
        return purchases.stream()
            .map(PurchaseDTO::fromEntityToDTO)
            .collect(Collectors.toList());
    }

    public List<PurchaseDTO> getByStatus(StatusEnum status){
        logger.info("fetching all purchases with status: {}", status);
        List<Purchase> purchase = repository.findByStatus(status);
        return purchase.stream()
            .map(PurchaseDTO::fromEntityToDTO)
            .collect(Collectors.toList());
    }

    @Cacheable(value = "purchase", key = "#uuid")
    public PurchaseDTO getByUuid(UUID uuid){
        logger.info("fetching purchase with uuid: {}", uuid);
        Purchase purchase = repository.findByUuid(uuid)
                .orElseThrow(() -> new RuntimeException("purchase with uuid " + uuid + " not found"));
        return PurchaseDTO.fromEntityToDTO(purchase);
    }

    public PurchaseDTO getPurchase(String govId) {
        logger.info("fetching purchase with gov id: {}", govId);
        Purchase purchase = repository.findByGovId(govId)
                .orElseThrow(() -> new RuntimeException("purchase with gov id " + govId + " not found"));
        return PurchaseDTO.fromEntityToDTO(purchase);
    }

    public PurchaseDTO create(PurchaseDTO purchaseDTO, MultipartFile pdfFile) {
        try {
            Blob pdfBlob = BlobProxy.generateProxy(pdfFile.getInputStream(), pdfFile.getSize());
            Purchase purchase = purchaseDTO.toEntity();
            purchase.setPdfDocument(pdfBlob);
            repository.save(purchase);
            return PurchaseDTO.fromEntityToDTO(purchase);
        } catch (IOException e) {
            logger.error("error reading PDF file", e);
            throw new RuntimeException("error reading PDF file");
        }
    }

    public String close(UUID uuid) {
        logger.info("closing purchase with uuid: {}", uuid);
        Purchase purchase = repository.findByUuid(uuid)
                .orElseThrow(() -> new RuntimeException("purchase with gov id " + uuid + " not found"));
        purchase.setStatus(StatusEnum.INACTIVE);
        repository.save(purchase);
        return "purchase with gov id " + uuid + " closed";
    }

    @Transactional(readOnly = true)
    public byte[] getPdfByUuid(UUID uuid) throws ResourceNotFoundException, SQLException {
        Blob pdfBlob = repository.findByUuid(uuid)
                .map(Purchase::getPdfDocument)
                .orElseThrow(() -> new ResourceNotFoundException("purchase with UUID " + uuid + " not found."));
        try (InputStream inputStream = pdfBlob.getBinaryStream()) {
            return inputStream.readAllBytes();
        } catch (Exception e) {
            throw new RuntimeException("failed to read PDF document", e);
        }
    }
}
