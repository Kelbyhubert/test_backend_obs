package com.obs.be_test.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InventoryResDto {
    
    private int id;
    private int itemId;
    private String name;
    private int qty;
    private Character type;
}
