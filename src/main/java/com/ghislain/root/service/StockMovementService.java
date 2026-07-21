package com.ghislain.root.service;

import com.ghislain.root.entity.Product;
import com.ghislain.root.entity.StockMovement;
import com.ghislain.root.entity.StockMovement.MovementType;
import com.ghislain.root.repository.ProductRepository;
import com.ghislain.root.repository.StockMovementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class StockMovementService {

    private final StockMovementRepository movementRepository;
    private final ProductRepository productRepository;

    @Autowired
    public StockMovementService(StockMovementRepository movementRepository,
                                 ProductRepository productRepository) {
        this.movementRepository = movementRepository;
        this.productRepository = productRepository;
    }

    @Transactional
    public StockMovement recordMovement(Long productId, MovementType type, Integer quantity, String reason) {
        Product product = productRepository.findById(productId)
            .orElseThrow(() -> new RuntimeException("Produit introuvable avec l'id " + productId));

        if (type == MovementType.OUT && product.getQuantity() < quantity) {
            throw new IllegalStateException("Stock insuffisant : disponible " + product.getQuantity() + ", demandé " + quantity);
        }

        // Mise à jour de la quantité du produit
        int newQuantity = type == MovementType.IN
            ? product.getQuantity() + quantity
            : product.getQuantity() - quantity;
        product.setQuantity(newQuantity);
        productRepository.save(product);

        // Enregistrement du mouvement
        StockMovement movement = new StockMovement();
        movement.setProduct(product);
        movement.setType(type);
        movement.setQuantity(quantity);
        movement.setReason(reason);

        return movementRepository.save(movement);
    }

    public List<StockMovement> getMovementsByProduct(Long productId) {
        return movementRepository.findByProductId(productId);
    }
}