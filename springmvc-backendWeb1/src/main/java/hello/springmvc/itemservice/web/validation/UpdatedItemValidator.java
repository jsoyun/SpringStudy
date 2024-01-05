package hello.springmvc.itemservice.web.validation;

import hello.springmvc.itemservice.domain.Item.Item;
import hello.springmvc.itemservice.domain.dto.ItemUpdateParamDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Slf4j

@Component
public class UpdatedItemValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return Item.class.isAssignableFrom(clazz);


    }

    @Override
    public void validate(Object target, Errors errors) {

        ItemUpdateParamDto updateParamDto = (ItemUpdateParamDto) target;
        log.info("updateParamDto ={}", updateParamDto);

        //검증로직
        if (!StringUtils.hasText(updateParamDto.getItemName())) {
            errors.rejectValue("itemName", "required");
        }
        //위와 같음
//        ValidationUtils.rejectIfEmptyOrWhitespace(bindingResult, "itemName", "required");
        if (updateParamDto.getPrice() == null || updateParamDto.getPrice() < 1000 || updateParamDto.getPrice() > 1000000) {
            errors.rejectValue("price", "range", new Object[]{1000, 1000000}, null);
        }
        if (updateParamDto.getQuantity() == null || updateParamDto.getQuantity() >= 9999) {
            errors.rejectValue("quantity", "max", new Object[]{9999}, null);
        }
        //특정필드가 아닌 복합 룰 검증
        if (updateParamDto.getPrice() != null && updateParamDto.getQuantity() != null) {
            int resultPrice = updateParamDto.getPrice() * updateParamDto.getQuantity();
            if (resultPrice < 10000) {
                errors.reject("totalPriceMin", new Object[]{10000, resultPrice}, null);
            }
        }
        //검증에 실패하면 다시 입력 폼으로


    }
}
