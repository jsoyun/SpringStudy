package hello.springmvc.itemservice.web.validation;

import hello.springmvc.itemservice.domain.Item.DeliveryCode;
import hello.springmvc.itemservice.domain.Item.Item;
import hello.springmvc.itemservice.domain.Item.ItemRepository;
import hello.springmvc.itemservice.domain.Item.ItemType;
import hello.springmvc.itemservice.domain.dto.ItemUpdateParamDto;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.*;

@Slf4j
@Controller
@RequestMapping("/v2/basic/items")
@RequiredArgsConstructor
public class ValidationItemControllerV2 {

    private final ItemRepository itemRepository;
    private final ItemValidator itemValidator;

    @InitBinder
    public void init(WebDataBinder dataBinder) {
        dataBinder.addValidators(itemValidator);
    }


    //모델에 자동으로 담기게 된다
    //TODO: 성능최적화, 더 좋은 방법: 스태틱 영역에 불러다 놓고 쓰기로 수정
    //지금 상태는 계속 호출되어 생성됨! 동적으로 변하지 않는 값이기 때문에 고정해서 쓰자
    @ModelAttribute("regions")
    public Map<String, String> regions() {
        Map<String, String> regions = new LinkedHashMap<>();
        regions.put("SEOUL", "서울");
        regions.put("BUSAN", "부산");
        regions.put("JEJU", "제주");
        return regions;
    }

    @ModelAttribute("itemTypes")
    public ItemType[] itemTypes() {
        return ItemType.values();

    }

    @ModelAttribute("deliveryCodes")
    public List<DeliveryCode> deliveryCodes() {
        List<DeliveryCode> deliveryCodes = new ArrayList<>();
        deliveryCodes.add(new DeliveryCode("FAST", "빠른 배송"));
        deliveryCodes.add(new DeliveryCode("NOMAL", "일반 배송"));
        deliveryCodes.add(new DeliveryCode("SLOW", "느린 배송"));
        return deliveryCodes;
    }


    @GetMapping
    public String items(Model model) {

        List<Item> items = itemRepository.findAll();
        log.info("getmapping items = {}", items);
        model.addAttribute("items", items);
        return "validation/v2/items";

    }

    @GetMapping("/{itemId}") //itemId
    public String item(@PathVariable Long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "validation/v2/item";


    }

    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("item", new Item());

        return "validation/v2/addForm";
    }


    //    @PostMapping("/add")
    public String addItemV1(Item item1, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {

        //검증오류 결과를 보관
        Map<String, String> errors = new HashMap<>();
        //검증로직
        if (!StringUtils.hasText(item1.getItemName())) {
            bindingResult.addError(new FieldError("item", "itemName", "상품이름은 필수 입니다."));
        }
        if (item1.getPrice() == null || item1.getPrice() < 1000 || item1.getPrice() > 1000000) {
            bindingResult.addError(new FieldError("item", "price", "가격은 1,000~ 1,000,000까지 허용합니다."));
        }
        if (item1.getQuantity() == null || item1.getQuantity() >= 9999) {
            bindingResult.addError(new FieldError("item", "quantity", "수량은 최대 9,999까지 허용합니다."));
        }
        //특정필드가 아닌 복합 룰 검증
        if (item1.getPrice() != null && item1.getQuantity() != null) {
            int resultPrice = item1.getPrice() * item1.getQuantity();
            if (resultPrice < 10000) {
                bindingResult.addError(new ObjectError("item", "가격*수량의 합은 10,000원 이상이어야 합니다. 현재 값 = " + resultPrice));
            }
        }
        //검증에 실패하면 다시 입력 폼으로
        if (bindingResult.hasErrors()) {
            log.info("error = {}", bindingResult);
            return "validation/v2/addForm";

        }

        log.info("item.open={}", item1.getOpen());
        log.info("item.region={}", item1.getRegions());
        log.info("item.itemType={}", item1.getItemType());

        Item savedItem = itemRepository.save(item1);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("IsSaved", true);
        return "redirect:/v2/basic/items/{itemId}";

    }

    //    @PostMapping("/add")
    public String addItemV2(Item item1, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {


        //검증로직
        if (!StringUtils.hasText(item1.getItemName())) {
            bindingResult.addError(new FieldError("item", "itemName", item1.getItemName(), false, null, null, "상품이름은 필수 입니다."));

        }
        if (item1.getPrice() == null || item1.getPrice() < 1000 || item1.getPrice() > 1000000) {
            bindingResult.addError(new FieldError("item", "price", item1.getPrice(), false, null, null, "가격은 1,000~ 1,000,000까지 허용합니다."));
        }
        if (item1.getQuantity() == null || item1.getQuantity() >= 9999) {
            bindingResult.addError(new FieldError("item", "quantity", item1.getQuantity(), false, null, null, "수량은 최대 9,999까지 허용합니다."));
        }
        //특정필드가 아닌 복합 룰 검증
        if (item1.getPrice() != null && item1.getQuantity() != null) {
            int resultPrice = item1.getPrice() * item1.getQuantity();
            if (resultPrice < 10000) {
                bindingResult.addError(new ObjectError("item", null, null, "가격*수량의 합은 10,000원 이상이어야 합니다. 현재 값 = " + resultPrice));
            }
        }
        //검증에 실패하면 다시 입력 폼으로
        if (bindingResult.hasErrors()) {
            log.info("error = {}", bindingResult);
            return "validation/v2/addForm";

        }

        log.info("item.open={}", item1.getOpen());
        log.info("item.region={}", item1.getRegions());
        log.info("item.itemType={}", item1.getItemType());

        Item savedItem = itemRepository.save(item1);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("IsSaved", true);
        return "redirect:/v2/basic/items/{itemId}";

    }


    //    @PostMapping("/add")
    public String addItemV3(Item item1, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {

        //검증로직
        if (!StringUtils.hasText(item1.getItemName())) {
            bindingResult.addError(new FieldError("item", "itemName", item1.getItemName(), false, new String[]{"required.item.itemName"}, null, null));

        }
        if (item1.getPrice() == null || item1.getPrice() < 1000 || item1.getPrice() > 1000000) {
            bindingResult.addError(new FieldError("item", "price", item1.getPrice(), false, new String[]{"range.item.price"}, new Object[]{1000, 1000000}, null));
        }
        if (item1.getQuantity() == null || item1.getQuantity() >= 9999) {
            bindingResult.addError(new FieldError("item", "quantity", item1.getQuantity(), false, new String[]{"max.item.quantity"}, new Object[]{9999}, null));
        }
        //특정필드가 아닌 복합 룰 검증
        if (item1.getPrice() != null && item1.getQuantity() != null) {
            int resultPrice = item1.getPrice() * item1.getQuantity();
            if (resultPrice < 10000) {
                bindingResult.addError(new ObjectError("item", new String[]{"totalPriceMin"}, new Object[]{10000, resultPrice}, null));
            }
        }
        //검증에 실패하면 다시 입력 폼으로
        if (bindingResult.hasErrors()) {
            log.info("error = {}", bindingResult);
            return "validation/v2/addForm";

        }

        log.info("item.open={}", item1.getOpen());
        log.info("item.region={}", item1.getRegions());
        log.info("item.itemType={}", item1.getItemType());

        Item savedItem = itemRepository.save(item1);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("IsSaved", true);
        return "redirect:/v2/basic/items/{itemId}";

    }


    //    @PostMapping("/add")
    public String addItemV4(Item item1, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {
        log.info("objectName = {}", bindingResult.getObjectName());
        log.info("target = {}", bindingResult.getTarget());

        //검증로직
//        if (!StringUtils.hasText(item1.getItemName())) {
//            bindingResult.rejectValue("itemName", "required");
//        }
        //위와 같음
        ValidationUtils.rejectIfEmptyOrWhitespace(bindingResult, "itemName", "required");
        if (item1.getPrice() == null || item1.getPrice() < 1000 || item1.getPrice() > 1000000) {
            bindingResult.rejectValue("price", "range", new Object[]{1000, 1000000}, null);
        }
        if (item1.getQuantity() == null || item1.getQuantity() >= 9999) {
            bindingResult.rejectValue("quantity", "max", new Object[]{9999}, null);
        }
        //특정필드가 아닌 복합 룰 검증
        if (item1.getPrice() != null && item1.getQuantity() != null) {
            int resultPrice = item1.getPrice() * item1.getQuantity();
            if (resultPrice < 10000) {
                bindingResult.reject("totalPriceMin", new Object[]{10000, resultPrice}, null);
            }
        }
        //검증에 실패하면 다시 입력 폼으로
        if (bindingResult.hasErrors()) {
            log.info("error = {}", bindingResult);
            return "validation/v2/addForm";

        }

        log.info("item.open={}", item1.getOpen());
        log.info("item.region={}", item1.getRegions());
        log.info("item.itemType={}", item1.getItemType());

        Item savedItem = itemRepository.save(item1);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("IsSaved", true);
        return "redirect:/v2/basic/items/{itemId}";

    }

    //    @PostMapping("/add")
    public String addItemV5(@ModelAttribute Item item1, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {
        log.info("objectName = {}", bindingResult.getObjectName());
        log.info("target = {}", bindingResult.getTarget());

        //검증로직
        itemValidator.validate(item1, bindingResult);

        //검증에 실패하면 다시 입력 폼으로
        if (bindingResult.hasErrors()) {
            log.info("error = {}", bindingResult);
            return "validation/v2/addForm";

        }

        log.info("item.open={}", item1.getOpen());
        log.info("item.region={}", item1.getRegions());
        log.info("item.itemType={}", item1.getItemType());

        Item savedItem = itemRepository.save(item1);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("IsSaved", true);
        return "redirect:/v2/basic/items/{itemId}";

    }


    @PostMapping("/add")
    public String addItemV6(@Validated @ModelAttribute Item item1, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {
        log.info("objectName = {}", bindingResult.getObjectName());
        log.info("target = {}", bindingResult.getTarget());

        //Validated 어노테이션 넣으면 Item에 대해서 자동으로 검증이 수행된다.

        //검증에 실패하면 다시 입력 폼으로
        if (bindingResult.hasErrors()) {
            log.info("error = {}", bindingResult);
            return "validation/v2/addForm";

        }

        log.info("item.open={}", item1.getOpen());
        log.info("item.region={}", item1.getRegions());
        log.info("item.itemType={}", item1.getItemType());

        Item savedItem = itemRepository.save(item1);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("IsSaved", true);
        return "redirect:/v2/basic/items/{itemId}";

    }


    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable Long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        log.info("edit open = {}", item.getOpen());
        return "validation/v2/editForm";
    }

    @PostMapping("/{itemId}/edit")
    public String edit(@PathVariable Long itemId, @ModelAttribute ItemUpdateParamDto itemUpdateParamDto) {
        itemRepository.update(itemId, itemUpdateParamDto);
        log.info("itemUpdateParamDto open ={}", itemUpdateParamDto.getOpen());
        log.info("edit regions = {}", itemUpdateParamDto.getRegions());
        return "redirect:/v2/basic/items/{itemId}";

    }


    /**
     * 테스트용 데이터 추가
     */
    @PostConstruct
    public void init() {

        itemRepository.save(new Item("itemAv2", 1000, 10));
        itemRepository.save(new Item("itemBv2", 1000, 10));
        log.info("itemRepository= {}", itemRepository);

    }
}



