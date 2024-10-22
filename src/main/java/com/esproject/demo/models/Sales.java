package com.esproject.demo.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Sales implements BaseDataModel {
    private static final String SALES_INDEX = "sales";

    private String orderID;
    private String orderAmount;
    private String currency;
    private String state;
    private String country;

    @Override
    public String getIndex() {
        return SALES_INDEX;
    }

    @Override
    public String toString() {
        return "Sales{" +
                "orderID='" + orderID + '\'' +
                ", orderAmount='" + orderAmount + '\'' +
                ", currency='" + currency + '\'' +
                ", state='" + state + '\'' +
                ", country='" + country + '\'' +
                '}';
    }
}
