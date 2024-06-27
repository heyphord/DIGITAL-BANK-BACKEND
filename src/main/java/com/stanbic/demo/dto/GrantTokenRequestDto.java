package com.stanbic.demo.dto;


import lombok.Data;

@Data
public class GrantTokenRequestDto {
    public String username;
    public String password;
}
