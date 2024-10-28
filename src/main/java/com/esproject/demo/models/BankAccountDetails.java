package com.esproject.demo.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class BankAccountDetails implements BaseDataModel{

    @JsonProperty("account_number")
    private int accountNumber;
    private long balance;
    private String firstname;
    private String lastname;
    private int age;
    private String gender;
    private String address;
    private String employer;
    private String email;
    private String city;
    private String state;

    @Override
    public String getIndex() {
        return "bank";
    }
}
