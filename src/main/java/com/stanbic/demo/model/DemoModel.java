package com.stanbic.demo.model;

import lombok.Data;
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import javax.persistence.Table;
public class DemoModel {

//    @Entity
    @Data
//    @Table(name = "CUSTOMER_PAYMENT_DETAILS")
    public class CustomerPaymentDetails {


//        @Id
//        @GeneratedValue(strategy = GenerationType.SEQUENCE)
        private Integer id;
        private String dataNumber;
        private String phoneNumber;
        private String accountName;
        private String paymentMethod;
        private String verificationCode;
        private Boolean isVerified;
        private Boolean isDefault;



    }

}
