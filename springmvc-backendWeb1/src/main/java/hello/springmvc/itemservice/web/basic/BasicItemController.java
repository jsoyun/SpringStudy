//package hello.springmvc.itemservice.web.basic;
//
//import hello.springmvc.itemservice.domain.Item.DeliveryCode;
//import hello.springmvc.itemservice.domain.Item.Item;
//import hello.springmvc.itemservice.domain.Item.ItemRepository;
//import hello.springmvc.itemservice.domain.Item.ItemType;
//import hello.springmvc.itemservice.domain.dto.ItemUpdateParamDto;
//import jakarta.annotation.PostConstruct;
//import lombok.RequiredArgsConstructor;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.util.StringUtils;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.servlet.mvc.support.RedirectAttributes;
//
//import java.util.*;
//
//@Controller
//@RequestMapping("/basic/items")
//@RequiredArgsConstructor   //있으면 final 붙은 필드있다면 그 필드로 초기화하는 생성자를 만들어줌
//public class BasicItemController {
//
//
//    private final Logger log = LoggerFactory.getLogger(getClass());
//
//    private final ItemRepository itemRepository;
//
//
////    @Autowired
////    public BasicItemController(ItemRepository itemRepository) {
////        this.itemRepository = itemRepository;
////    }
//
//    //모델에 자동으로 담기게 된다
//    //TODO: 성능최적화, 더 좋은 방법: 스태틱 영역에 불러다 놓고 쓰기로 수정
//    //지금 상태는 계속 호출되어 생성됨! 동적으로 변하지 않는 값이기 때문에 고정해서 쓰자
//    @ModelAttribute("regions")
//    public Map<String, String> regions() {
//        Map<String, String> regions = new LinkedHashMap<>();
//        regions.put("SEOUL", "서울");
//        regions.put("BUSAN", "부산");
//        regions.put("JEJU", "제주");
//        return regions;
//    }
//
//    @ModelAttribute("itemTypes")
//    public ItemType[] itemTypes() {
//        return ItemType.values();
//
//    }
//
//    @ModelAttribute("deliveryCodes")
//    public List<DeliveryCode> deliveryCodes() {
//        List<DeliveryCode> deliveryCodes = new ArrayList<>();
//        deliveryCodes.add(new DeliveryCode("FAST", "빠른 배송"));
//        deliveryCodes.add(new DeliveryCode("NOMAL", "일반 배송"));
//        deliveryCodes.add(new DeliveryCode("SLOW", "느린 배송"));
//        return deliveryCodes;
//    }
//
//
//    @GetMapping
//    public String items(Model model) {
//
//        List<Item> items = itemRepository.findAll();
//        log.info("getmapping items = {}", items);
//        model.addAttribute("items", items);
//        return "basic/springmvc1/items";
//
//    }
//
//    @GetMapping("/{itemId}") //itemId
//    public String item(@PathVariable Long itemId, Model model) {
//        Item item = itemRepository.findById(itemId);
//        model.addAttribute("item", item);
//        return "basic/springmvc1/item";
//
//    }
//
//    @GetMapping("/add")
//    public String addForm(Model model) {
//        model.addAttribute("item", new Item());
//
//        return "basic/springmvc1/addForm";
//    }
//
//
//    //    @PostMapping("/add")
////    public String addItem1(@RequestParam String itemName,
////                           @RequestParam int price,
////                           @RequestParam Integer quantity,
////                           Model model
////    ) {
////        Item item1 = new Item();
////        item1.setItemName(itemName);
////        item1.setPrice(price);
////        item1.setQuantity(quantity);
////        itemRepository.save(item1);
////
////        model.addAttribute("item", item1);
////
////        return "basic/springmvc1/item";
////
////    }
//
//    //    @PostMapping("/add")
////    public String addItem2(@ModelAttribute("item") Item item1
//////                           Model model
////    ) {
////
////        itemRepository.save(item1);
////
//////        model.addAttribute("item", item1); 알아서 반영된다!
////
////        return "basic/item";
////
////    }
//
//    //    @PostMapping("/add")
////    public String addItem3(@ModelAttribute("item") Item item1) {
////        itemRepository.save(item1);
////        return "redirect:/basic/items/" + item1.getId();
////
////    }
//
//    @PostMapping("/add")
//    public String addItem4(Item item1, RedirectAttributes redirectAttributes, Model model) {
//
//        //검증오류 결과를 보관
//        Map<String, String> errors = new HashMap<>();
//        //검증로직
//        if (!StringUtils.hasText(item1.getItemName())) {
//            errors.put("itemName", "상품 이름은 필수입니다.");
//        }
//        if (item1.getPrice() == null || item1.getPrice() < 1000 || item1.getPrice() > 1000000) {
//            errors.put("price", "가격은 1,000~ 1,000,000까지 허용합니다.");
//        }
//        if (item1.getQuantity() == null || item1.getQuantity() >= 9999) {
//            errors.put("quantity", "수량은 최대 9,999까지 허용합니다.");
//        }
//        //특정필드가 아닌 복합 룰 검증
//        if (item1.getPrice() != null && item1.getQuantity() != null) {
//            int resultPrice = item1.getPrice() * item1.getQuantity();
//            if (resultPrice < 10000) {
//                errors.put("globalError", "가격*수량의 합은 10,000원 이상이어야 합니다. 현재 값 = " + resultPrice);
//            }
//        }
//        //검증에 실패하면 다시 입력 폼으로
//        if (!errors.isEmpty()) {
//            log.info("error = {}", errors);
//            model.addAttribute("errors", errors);
//            return "basic/springmvc1/addForm";
//
//        }
//
//        log.info("item.open={}", item1.getOpen());
//        log.info("item.region={}", item1.getRegions());
//        log.info("item.itemType={}", item1.getItemType());
//        Item savedItem = itemRepository.save(item1);
//        redirectAttributes.addAttribute("itemId", savedItem.getId());
//        redirectAttributes.addAttribute("IsSaved", true);
//        return "redirect:/basic/items/{itemId}";
//
//    }
//
////    private boolean hasError(Map<String, String> errors) {
////        if (!errors.isEmpty()) {
////            return true;
////        } else {
////            return false;
////        }
////    }
//
//
//    @GetMapping("/{itemId}/edit")
//    public String editForm(@PathVariable Long itemId, Model model) {
//        Item item = itemRepository.findById(itemId);
//        model.addAttribute("item", item);
//        log.info("edit open = {}", item.getOpen());
//        return "basic/springmvc1/editForm";
//    }
//
//    @PostMapping("/{itemId}/edit")
//    public String edit(@PathVariable Long itemId, @ModelAttribute ItemUpdateParamDto itemUpdateParamDto) {
//        itemRepository.update(itemId, itemUpdateParamDto);
//        log.info("itemUpdateParamDto open ={}", itemUpdateParamDto.getOpen());
//        log.info("edit regions = {}", itemUpdateParamDto.getRegions());
//        return "redirect:/basic/items/{itemId}";
//
//    }
//
//
//    /**
//     * 테스트용 데이터 추가
//     */
//    @PostConstruct
//    public void init() {
//
//        itemRepository.save(new Item("itemAbasic", 1000, 10));
//        itemRepository.save(new Item("itemBbasic", 1000, 10));
//        log.info("itemRepository= {}", itemRepository);
//
//    }
//}
