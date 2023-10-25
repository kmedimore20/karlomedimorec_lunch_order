package com.example.lunchorder_v2.model;

import jakarta.persistence.*;

@Entity
@Table(name = "menu")
public class Menu {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long mealId;

    @Column(name = "meal_name")
    private String mealName;

    @Column(name = "price")
    private Double price;

    public long getId(){
        return mealId;
    }

    public Menu(){

    }

    public Menu(String mealName, Double price){
        this.mealName = mealName;
        this.price = price;
    }

    public void setId(){
        this.mealId = mealId;
    }

    public String getMealName(){
        return mealName;
    }

    public void setMealName(){
        this.mealName = mealName;
    }

    public Double getPrice(){
        return price;
    }

    public void setPrice(){
        this.price = price;
    }
}
