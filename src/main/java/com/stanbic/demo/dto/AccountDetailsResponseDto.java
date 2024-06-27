package com.stanbic.demo.dto;


import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Data
public class AccountDetailsResponseDto {
    public String customerId;
    public String name;
    public String currency;
    public BigDecimal balance;
    public Map<String, String> transactions;
}
