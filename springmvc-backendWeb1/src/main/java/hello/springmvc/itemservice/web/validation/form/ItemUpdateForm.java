package hello.springmvc.itemservice.web.validation.form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

@Getter
@Setter
public class ItemUpdateForm {

    @NotNull
    private Long id;
    @NotBlank
    private String itemName;
    @NotNull
    @Range(min = 1000, max = 1000000)
    private Integer price;
    //수정에서는 수량은 자유롭게 변경할 수 있다
    private Integer quantity;
}

