package hello.springmvc.itemservice.domain.dto;

import hello.springmvc.itemservice.domain.Item.ItemType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class ItemUpdateParamDto {
    private String itemName;
    private Integer price;
    private Integer quantity;

    private Boolean open; //판매여부
    private List<String> regions; //등록지역
    private ItemType itemType; //상품종류
    private String deliveryCode; //배송방식


    public ItemUpdateParamDto() {

    }
}
