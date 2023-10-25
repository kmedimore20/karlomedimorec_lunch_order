package com.example.lunchorder_v2.model;


import jakarta.persistence.*;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ORDERS_SEQUENCE")
    @SequenceGenerator(name = "ORDERS_SEQUENCE", sequenceName = "ORDERS_SEQUENCE_ID", allocationSize = 1)
    private long orderId;

    @Column
    private String menuItems;

    public Order(String menuItems){
        this.menuItems = menuItems;
    }

    public Order() {

    }

    public long getId(){
        return orderId;
    }

    public void setId(){
        this.orderId = orderId;
    }

    public String getMenuItems(){
        return menuItems;
    }

    public void setMenuItems(String menuItems){
        this.menuItems = menuItems;
    }
}
