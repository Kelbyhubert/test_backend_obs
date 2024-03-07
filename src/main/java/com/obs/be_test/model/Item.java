package com.obs.be_test.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "TB_ITEM")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Item {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "_name")
    private String name;

    private int price;

    @OneToMany(fetch = FetchType.LAZY ,mappedBy = "item")
    private List<Inventory> inventorys = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY ,mappedBy = "item")
    private List<Order> orders = new ArrayList<>();

    public int getCurrentStock(){
        int stock = 0;
        for (Inventory invItem : this.inventorys) {
            if(invItem.getType() == 'T'){
                stock += invItem.getQty();
            }else{
                stock -= invItem.getQty();
            }
        }

        for (Order order : orders) {
            stock -= order.getQty();
        }

        return stock < 0 ? 0 : stock;
    }
}
