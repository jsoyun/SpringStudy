package hello.springmvc.itemservice.web.validation;

import hello.springmvc.itemservice.domain.Item.DeliveryCode;
import hello.springmvc.itemservice.domain.Item.Item;
import hello.springmvc.itemservice.domain.Item.ItemRepository;
import hello.springmvc.itemservice.domain.Item.ItemType;
import hello.springmvc.itemservice.web.validation.form.ItemSaveForm;
import hello.springmvc.itemservice.web.validation.form.ItemUpdateForm;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping("/v4/basic/items")
@RequiredArgsConstructor
public class ValidationItemControllerV4 {

    private final ItemRepository itemRepository;
//    private final ItemValidator itemValidator;
//
//
//    @InitBinder
//    public void init(WebDataBinder dataBinder) {
//        dataBinder.addValidators(itemValidator);
//
//    }


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

        List<hello.springmvc.itemservice.domain.Item.Item> items = itemRepository.findAll();
        log.info("getmapping items = {}", items);
        model.addAttribute("items", items);
        return "validation/v4/items";

    }

    @GetMapping("/{itemId}") //itemId
    public String item(@PathVariable Long itemId, Model model) {
        hello.springmvc.itemservice.domain.Item.Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "validation/v4/item";


    }

    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("item", new hello.springmvc.itemservice.domain.Item.Item());

        return "validation/v4/addForm";
    }


    @PostMapping("/add")
    public String addItem(@Validated @ModelAttribute("item") ItemSaveForm itemSaveForm, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        log.info("objectName = {}", bindingResult.getObjectName());
        log.info("target = {}", bindingResult.getTarget());


        //특정필드가 아닌 복합 룰 검증
        if (itemSaveForm.getPrice() != null && itemSaveForm.getQuantity() != null) {
            int resultPrice = itemSaveForm.getPrice() * itemSaveForm.getQuantity();
            if (resultPrice < 10000) {
                bindingResult.reject("totalPriceMin", new Object[]{10000, resultPrice}, null);
            }
        }
        //검증에 실패하면 다시 입력 폼으로
        if (bindingResult.hasErrors()) {
            log.info("error = {}", bindingResult);
            return "validation/v4/addForm";

        }

        //TODO: 변경) getter, setter보다 생성자에서 만들어주는게 더 좋다
        hello.springmvc.itemservice.domain.Item.Item item = new hello.springmvc.itemservice.domain.Item.Item();
        item.setItemName(itemSaveForm.getItemName());
        item.setPrice(itemSaveForm.getPrice());
        item.setQuantity(itemSaveForm.getQuantity());


        hello.springmvc.itemservice.domain.Item.Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("IsSaved", true);
        return "redirect:/v4/basic/items/{itemId}";

    }


    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable Long itemId, Model model) {
        hello.springmvc.itemservice.domain.Item.Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        log.info("edit open = {}", item.getOpen());
        return "validation/v4/editForm";
    }


    @PostMapping("/{itemId}/edit")
    public String edit(@PathVariable Long itemId, @Validated @ModelAttribute("item") ItemUpdateForm itemUpdateForm, BindingResult bindingResult) {

        //특정필드가 아닌 복합 룰 검증
        if (itemUpdateForm.getPrice() != null && itemUpdateForm.getQuantity() != null) {
            int resultPrice = itemUpdateForm.getPrice() * itemUpdateForm.getQuantity();
            if (resultPrice < 10000) {
                bindingResult.reject("totalPriceMin", new Object[]{10000, resultPrice}, null);
            }
        }
        //검증에 실패하면 다시 입력 폼으로
        if (bindingResult.hasErrors()) {
            log.info("edit error = {}", bindingResult);
            return "validation/v4/addForm";

        }


        //item으로 받고 그 값들을
        //dto로 변환해서 넘기면 되지 않을까?
        Item item = new Item();
        item.setItemName(itemUpdateForm.getItemName());
        item.setPrice(itemUpdateForm.getPrice());
        item.setQuantity(itemUpdateForm.getQuantity());


        itemRepository.update(itemId, item);
        log.info("itemUpdateParamDto open ={}", item.getOpen());
        return "redirect:/v4/basic/items/{itemId}";

    }

    /**
     * 테스트용 데이터 추가
     */
    @PostConstruct
    public void init() {

        itemRepository.save(new hello.springmvc.itemservice.domain.Item.Item("itemAv3", 1000, 10));
        itemRepository.save(new hello.springmvc.itemservice.domain.Item.Item("itemBv3", 1000, 10));
        log.info("itemRepository= {}", itemRepository);

    }
}



