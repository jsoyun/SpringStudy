package hello.springmvc.itemservice.web.validation;

import hello.springmvc.itemservice.domain.Item.Item;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;


@Component
public class ItemValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return Item.class.isAssignableFrom(clazz);


    }

    @Override
    public void validate(Object target, Errors errors) {
        Item item1 = (Item) target;


        //검증로직
        if (!StringUtils.hasText(item1.getItemName())) {
            errors.rejectValue("itemName", "required");
        }
        //위와 같음
//        ValidationUtils.rejectIfEmptyOrWhitespace(bindingResult, "itemName", "required");
        if (item1.getPrice() == null || item1.getPrice() < 1000 || item1.getPrice() > 1000000) {
            errors.rejectValue("price", "range", new Object[]{1000, 1000000}, null);
        }
        if (item1.getQuantity() == null || item1.getQuantity() >= 9999) {
            errors.rejectValue("quantity", "max", new Object[]{9999}, null);
        }
        //특정필드가 아닌 복합 룰 검증
        if (item1.getPrice() != null && item1.getQuantity() != null) {
            int resultPrice = item1.getPrice() * item1.getQuantity();
            if (resultPrice < 10000) {
                errors.reject("totalPriceMin", new Object[]{10000, resultPrice}, null);
            }
        }
        //검증에 실패하면 다시 입력 폼으로


    }
}
