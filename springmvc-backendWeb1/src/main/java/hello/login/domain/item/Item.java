package hello.login.domain.item;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@NoArgsConstructor
//@Getter
//@Setter
@Data
public class Item {
    //    @NotNull(groups = UpdateCheck.class)
    private Long id;
    //    @NotBlank(groups = {SaveCheck.class, UpdateCheck.class})
    private String itemName;
    //    @NotNull(groups = {SaveCheck.class, UpdateCheck.class})
//    @Range(min = 1000, max = 1000000, groups = {SaveCheck.class, UpdateCheck.class})
    private Integer price;
    //    @NotNull(groups = {SaveCheck.class, UpdateCheck.class})
//    @Max(value = 9999, groups = {SaveCheck.class})
    private Integer quantity;

    private Boolean open; //판매여부
    private List<String> regions; //등록지역
    private String deliveryCode; //배송방식


    public Item(String itemName, Integer price, Integer quantity) {
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;

    }

    @Override
    public String toString() {
        return "Item{" +
            "id=" + id +
            ", itemName='" + itemName + '\'' +
            ", price=" + price +
            ", quantity=" + quantity +
            ", open=" + open +

            ", deliveryCode='" + deliveryCode + '\'' +
            '}';
    }
}
