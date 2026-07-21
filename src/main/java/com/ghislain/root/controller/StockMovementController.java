package com.ghislain.root.controller;

import com.ghislain.root.dto.StockMovementRequest;
import com.ghislain.root.entity.StockMovement;
import com.ghislain.root.service.StockMovementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/stock-movements")
public class StockMovementController {

    private final StockMovementService movementService;

    @Autowired
    public StockMovementController(StockMovementService movementService) {
        this.movementService = movementService;
    }

    @PostMapping
    public StockMovement recordMovement(@RequestBody StockMovementRequest request) {
        return movementService.recordMovement(
            request.getProductId(),
            request.getType(),
            request.getQuantity(),
            request.getReason()
        );
    }

    @GetMapping("/product/{productId}")
    public List<StockMovement> getMovementsByProduct(@PathVariable Long productId) {
        return movementService.getMovementsByProduct(productId);
    }
}