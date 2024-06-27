/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.stanbic.demo.controller;

import com.stanbic.demo.dto.AccountDetailsResponseDto;
import com.stanbic.demo.dto.FundsTransferRequestDto;
import com.stanbic.demo.dto.GrantTokenRequestDto;
import com.stanbic.demo.dto.GrantTokenResponseDto;
import com.stanbic.demo.service.DemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Value;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("api/v1/")
public class DemoController {

    @Autowired
    DemoService demoService;
    @Value("${auth.username}")
    private String APP_USERNAME;

    @Value("${auth.password}")
    private String APP_PASSWORD;


    @PostMapping(path = "/auth/token/grant")
    public ResponseEntity<Object> grantToken(@RequestBody GrantTokenRequestDto request) {

        try {
            //dont allow login if username and password is incorrect
            String username = request.getUsername();
            String password = request.getPassword();

            if(!username.equals(APP_USERNAME) || !password.equals(APP_PASSWORD)){
                Map<String, Object> response = Map.of(
                        "data", request,
                        "message", "Unauthorized "
                );
                return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
            }

            //generate JWT token
            String token = demoService.generateToken(username);
            GrantTokenResponseDto responseDto = new GrantTokenResponseDto();
            responseDto.setToken(token);

            Map<String, Object> response = Map.of(
                    "data", responseDto,
                    "message", "Authentication success"
            );
            return new ResponseEntity<>(response, HttpStatus.OK);


        }catch (Exception e){
            Map<String, Object> response = Map.of(
                    "data", "",
                    "message", e.getMessage()
            );
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);

        }
    }

    @GetMapping(path = "/customers/{customerId}/account")
    public ResponseEntity<Object> getAccountDetails(@PathVariable("customerId") String customerId, @RequestHeader("Authorization") String authorizationHeader ) {

        String token = authorizationHeader.substring(7);

        try {
            //SECURITY CHECK:check if token was passed a in the Auth Header
            if(authorizationHeader == null){
                Map<String, Object> response = Map.of(
                        "data", "",
                        "message", "No Bearer authorization found "
                );
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }


            //SECURITY CHECK:check if token is valid
            Boolean isTokenValid = demoService.validateToken(token, APP_USERNAME);

            if(!isTokenValid) {
                Map<String, Object> response = Map.of(
                        "data", "",
                        "message", "Invalid header token"
                );
                return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
            }

            //SECURITY CHECK:check if customer id exists before processing
            if(customerId == null){
                Map<String, Object> response = Map.of(
                        "data", "",
                        "message", "Customer id is required "
                );
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }

            //NOW RETURN THE CUSTOMERS ACCOUNT DETAILS
            AccountDetailsResponseDto accountDetailsResponseDto = new AccountDetailsResponseDto();
            accountDetailsResponseDto.setName("Hayford Owusu Ansah");
            accountDetailsResponseDto.setBalance(BigDecimal.valueOf(3000.00));
            accountDetailsResponseDto.setCustomerId(customerId);

            Map<String, String> transactionsMap = Map.of(
                    "2024-01-29", "1000 GH Credit",
                    "2024-02-01", "300 GH Debit",
                    "2024-03-05", "300 GH Debit",
                    "2024-04-29", "3437 GH Debit"
            );

            accountDetailsResponseDto.setTransactions(transactionsMap);


            Map<String, Object> response = Map.of(
                    "data", accountDetailsResponseDto,
                    "message", "Success"
            );
            return new ResponseEntity<>(response, HttpStatus.OK);


        }catch (Exception e){
            Map<String, Object> response = Map.of(
                    "data", "",
                    "message", e.getMessage()
            );
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);

        }
    }

    @PostMapping(path = "/transfers/")
    public ResponseEntity<Object> transferFunds(@RequestHeader("Authorization") String authorizationHeader , @RequestBody FundsTransferRequestDto requestDto) {

        String token = authorizationHeader.substring(7);

        try {
            //SECURITY CHECK:check if token was passed in the Auth Header
            if(authorizationHeader == null){
                Map<String, Object> response = Map.of(
                        "data", "",
                        "message", "No Bearer authorization found "
                );
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }


            //SECURITY CHECK:check if token is valid
            Boolean isTokenValid = demoService.validateToken(token, APP_USERNAME);

            if(!isTokenValid) {
                Map<String, Object> response = Map.of(
                        "data", "",
                        "message", "Invalid header token"
                );
                return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
            }

            //SECURITY CHECK:check if recipientId, senderId and amount is not null
            if( requestDto.getAmount() == null || requestDto.recipientId == null || requestDto.getSenderId() == null){
                Map<String, Object> response = Map.of(
                        "data", "",
                        "message", "Request Values cannot be empty"
                );
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }

            //return successful response



            Map<String, Object> response = Map.of(
                    "data", "",
                    "message", "You have successfully transferred "+requestDto.amount.toString()+" to "+requestDto.getRecipientId()
            );
            return new ResponseEntity<>(response, HttpStatus.OK);


        }catch (Exception e){
            Map<String, Object> response = Map.of(
                    "data", "",
                    "message", e.getMessage()
            );
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);

        }
    }


}
