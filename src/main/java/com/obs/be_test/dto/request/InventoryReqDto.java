package com.obs.be_test.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InventoryReqDto {
    

    private Integer itemId;
    private Integer qty;
    private Character type;
}
