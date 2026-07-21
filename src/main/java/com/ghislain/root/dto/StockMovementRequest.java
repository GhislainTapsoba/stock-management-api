package com.ghislain.root.dto;

import com.ghislain.root.entity.StockMovement.MovementType;

public class StockMovementRequest {
    private Long productId;
    private MovementType type;
    private Integer quantity;
    private String reason;

    // Getters et setters
    public Long getProductId() { return productId; }
    public void setProductId(Long productId) { this.productId = productId; }
    public MovementType getType() { return type; }
    public void setType(MovementType type) { this.type = type; }
    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }
    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }
}