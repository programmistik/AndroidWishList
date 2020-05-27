package com.example.testxml;

public class Currency {
    private String Code;
    private String Name ;
    private Double Nominal ;
    private Double Value;
    // public String Value;

    Currency(String Code, String Name, String Nominal, String Value) {
        this.Code = Code;
        this.Name = Name;
        this.Nominal = Double.parseDouble(Nominal);
        this.Value = Double.parseDouble(Value);
    }
    public String getCode() { return this.Code;  }
    public void setName(String name){
        this.Name = name;
    }
    public String getName() { return this.Name;  }
    public void setValue(Double val){
        this.Value = val;
    }
    public Double getValue() { return this.Value;  }
}