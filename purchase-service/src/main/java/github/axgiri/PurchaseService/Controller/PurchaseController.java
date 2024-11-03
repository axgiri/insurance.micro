package github.axgiri.PurchaseService.Controller;

import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import github.axgiri.PurchaseService.DTO.PurchaseDTO;
import github.axgiri.PurchaseService.Enum.StatusEnum;
import github.axgiri.PurchaseService.Service.PurchaseService;

@RestController
@RequestMapping("/api/purchase")
public class PurchaseController {
    
    private static final Logger logger = org.slf4j.LoggerFactory.getLogger(PurchaseController.class);

    private final PurchaseService service;

    @Autowired
    public PurchaseController(PurchaseService service){
        this.service = service;
    }

    @GetMapping("/admin")
    public ResponseEntity<List<PurchaseDTO>> get(){
        logger.info("request to fetch all purchases");
        List<PurchaseDTO> purchases = service.get();
        return ResponseEntity.ok(purchases);
    }

    @GetMapping("/admin/getByStatus")
    public ResponseEntity<List<PurchaseDTO>> getByStatus(@RequestParam StatusEnum status){
        logger.info("request to fetch purchases with status: {}", status);
        List<PurchaseDTO> purchases = service.getByStatus(status);
        return ResponseEntity.ok(purchases);
    }

    @GetMapping("/admin/getByUuid")
    public ResponseEntity<PurchaseDTO> getByUuid(@RequestParam UUID uuid){
        logger.info("request to get purchase with uuid: {}", uuid);
        PurchaseDTO purchase = service.getByUuid(uuid);
        return ResponseEntity.ok(purchase);
    }

    @GetMapping("/public/getByGovId")
    public ResponseEntity<PurchaseDTO> getByGovId(@RequestParam String govId){
        logger.info("request to fetch purchase with gov id: {}", govId);
        PurchaseDTO purchase = service.getPurchase(govId);
        return ResponseEntity.ok(purchase);
    }

    @PostMapping("/public/create")
    public ResponseEntity<PurchaseDTO> create(@RequestBody PurchaseDTO purchaseDTO){
        logger.info("reques to new purchase");
        PurchaseDTO purchase = service.create(purchaseDTO);
        return ResponseEntity.ok(purchase);
    }

    @DeleteMapping("/public/close")
    public ResponseEntity<String> close(@RequestParam UUID uuid){
        logger.info("request to close purchase with uuid: {}", uuid);
        service.close(uuid);
        return ResponseEntity.ok("your insutance package with uuid: " + uuid + " is closed successfully");
    }
}
