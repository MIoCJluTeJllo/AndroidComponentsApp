package com.example.stylist;

//класс расхода/дохода
public class FinanceItem {
    String name;
    Integer sum;
    Boolean up;
    public FinanceItem(String name, Integer sum, Boolean up){
        this.name = name;
        this.sum = sum;
        this.up = up;
    }

    public Boolean getUp() {
        return up;
    }

    public String getName() {
        return name;
    }

    public Integer getSum() {
        return sum;
    }
}
