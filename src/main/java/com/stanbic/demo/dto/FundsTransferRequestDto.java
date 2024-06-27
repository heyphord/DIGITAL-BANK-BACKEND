package com.stanbic.demo.dto;


import lombok.Data;

import java.math.BigDecimal;

@Data
public class FundsTransferRequestDto {
    public String senderId;
    public String recipientId;
    public BigDecimal amount;
}
