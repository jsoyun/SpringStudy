package hello.springmvc.itemservice.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ItemUpdateParamDto {
    private String itemName;
    private Integer price;
    private Integer quantity;


}
