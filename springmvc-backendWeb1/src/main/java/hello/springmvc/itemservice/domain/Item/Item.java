package hello.springmvc.itemservice.domain.Item;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

import java.util.List;


@NoArgsConstructor
@Getter
@Setter
public class Item {
    private Long id;
    @NotBlank(message = "공백x")
    private String itemName;
    @NotNull
    @Range(min = 1000, max = 1000000)
    private Integer price;
    @NotNull
    @Max(9999)
    private Integer quantity;

    private Boolean open; //판매여부
    private List<String> regions; //등록지역
    private ItemType itemType; //상품종류
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
            ", regions=" + regions +
            ", itemType=" + itemType +
            ", deliveryCode='" + deliveryCode + '\'' +
            '}';
    }
}
