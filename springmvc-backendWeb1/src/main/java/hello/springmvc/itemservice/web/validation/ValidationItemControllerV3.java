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
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping("/v3/basic/items")
@RequiredArgsConstructor
public class ValidationItemControllerV3 {

    private final ItemRepository itemRepository;
    private final ItemValidator itemValidator;
//    private final UpdatedItemValidator updatedItemValidator;

//    public ValidationItemControllerV3(ItemRepository itemRepository, ItemValidator itemValidator, UpdatedItemValidator updatedItemValidator) {
//        this.itemRepository = itemRepository;
//        this.itemValidator = itemValidator;
//        this.updatedItemValidator = updatedItemValidator;
//    }


    @InitBinder
    public void init(WebDataBinder dataBinder) {
        dataBinder.addValidators(itemValidator);
//        dataBinder.addValidators(updatedItemValidator);
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
        return "validation/v3/items";

    }

    @GetMapping("/{itemId}") //itemId
    public String item(@PathVariable Long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "validation/v3/item";


    }

    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("item", new Item());

        return "validation/v3/addForm";
    }


    @PostMapping("/add")
    public String addItem(@Validated @ModelAttribute Item item1, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {
        log.info("objectName = {}", bindingResult.getObjectName());
        log.info("target = {}", bindingResult.getTarget());


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
            return "validation/v3/addForm";

        }

        log.info("item.open={}", item1.getOpen());
        log.info("item.region={}", item1.getRegions());
        log.info("item.itemType={}", item1.getItemType());

        Item savedItem = itemRepository.save(item1);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("IsSaved", true);
        return "redirect:/v3/basic/items/{itemId}";

    }


    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable Long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        log.info("edit open = {}", item.getOpen());
        return "validation/v3/editForm";
    }

    //    @PostMapping("/{itemId}/edit")
//    public String edit(@PathVariable Long itemId, @Validated @ModelAttribute ItemUpdateParamDto itemUpdateParamDto, BindingResult bindingResult) {
//
//        //특정필드가 아닌 복합 룰 검증
//        if (itemUpdateParamDto.getPrice() != null && itemUpdateParamDto.getQuantity() != null) {
//            int resultPrice = itemUpdateParamDto.getPrice() * itemUpdateParamDto.getQuantity();
//            if (resultPrice < 10000) {
//                bindingResult.reject("totalPriceMin", new Object[]{10000, resultPrice}, null);
//            }
//        }
//        //검증에 실패하면 다시 입력 폼으로
//        if (bindingResult.hasErrors()) {
//            log.info("edit error = {}", bindingResult);
//            return "validation/v3/addForm";
//
//        }
//
//        itemRepository.update(itemId, itemUpdateParamDto);
//        log.info("itemUpdateParamDto open ={}", itemUpdateParamDto.getOpen());
//        log.info("edit regions = {}", itemUpdateParamDto.getRegions());
//        return "redirect:/v3/basic/items/{itemId}";
//
//    }
    @PostMapping("/{itemId}/edit")
    public String edit(@PathVariable Long itemId, @Validated @ModelAttribute Item item, BindingResult bindingResult) {

        //특정필드가 아닌 복합 룰 검증
        if (item.getPrice() != null && item.getQuantity() != null) {
            int resultPrice = item.getPrice() * item.getQuantity();
            if (resultPrice < 10000) {
                bindingResult.reject("totalPriceMin", new Object[]{10000, resultPrice}, null);
            }
        }
        //검증에 실패하면 다시 입력 폼으로
        if (bindingResult.hasErrors()) {
            log.info("edit error = {}", bindingResult);
            return "validation/v3/addForm";

        }


        //item으로 받고 그 값들을
        //dto로 변환해서 넘기면 되지 않을까?
        ItemUpdateParamDto itemUpdateParamDto1 = new ItemUpdateParamDto();
        itemUpdateParamDto1.setItemName(item.getItemName());
        itemUpdateParamDto1.setItemType(item.getItemType());
        itemUpdateParamDto1.setPrice(item.getPrice());
        itemUpdateParamDto1.setQuantity(item.getQuantity());
        itemUpdateParamDto1.setRegions(item.getRegions());
        itemUpdateParamDto1.setOpen(item.getOpen());
        itemUpdateParamDto1.setDeliveryCode(item.getDeliveryCode());

        itemRepository.update(itemId, itemUpdateParamDto1);
        log.info("itemUpdateParamDto open ={}", itemUpdateParamDto1.getOpen());
        log.info("edit regions = {}", itemUpdateParamDto1.getRegions());
        return "redirect:/v3/basic/items/{itemId}";

    }

    /**
     * 테스트용 데이터 추가
     */
    @PostConstruct
    public void init() {

        itemRepository.save(new Item("itemAv3", 1000, 10));
        itemRepository.save(new Item("itemBv3", 1000, 10));
        log.info("itemRepository= {}", itemRepository);

    }
}



