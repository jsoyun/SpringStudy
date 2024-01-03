package hello.springmvc.itemservice.web.basic;

import hello.springmvc.itemservice.domain.Item;
import hello.springmvc.itemservice.domain.ItemRepository;
import hello.springmvc.itemservice.domain.dto.ItemUpdateParamDto;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/basic/items")
@RequiredArgsConstructor   //있으면 final 붙은 필드있다면 그 필드로 초기화하는 생성자를 만들어줌
public class BasicItemController {


    private final Logger log = LoggerFactory.getLogger(getClass());

    private final ItemRepository itemRepository;


//    @Autowired
//    public BasicItemController(ItemRepository itemRepository) {
//        this.itemRepository = itemRepository;
//    }


    //TODO: 모델과 entity 차이가 뭐지?? 뭐로 하지??
    @GetMapping
    public String items(Model model) {

        List<Item> items = itemRepository.findAll();
        log.info("getmapping items = {}", items);
        model.addAttribute("items", items);
        return "basic/springmvc1/items";

    }

    @GetMapping("/{itemId}") //itemId
    public String item(@PathVariable Long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "basic/springmvc1/item";

    }

    @GetMapping("/add")
    public String addForm() {
        return "basic/springmvc1/addForm";
    }

    //    @PostMapping("/add")
    public String addItem1(@RequestParam String itemName,
                           @RequestParam int price,
                           @RequestParam Integer quantity,
                           Model model
    ) {
        Item item1 = new Item();
        item1.setItemName(itemName);
        item1.setPrice(price);
        item1.setQuantity(quantity);
        itemRepository.save(item1);

        model.addAttribute("item", item1);

        return "basic/springmvc1/item";

    }

    //    @PostMapping("/add")
    public String addItem2(@ModelAttribute("item") Item item1
//                           Model model
    ) {

        itemRepository.save(item1);

//        model.addAttribute("item", item1); 알아서 반영된다!

        return "basic/item";

    }

    //    @PostMapping("/add")
    public String addItem3(@ModelAttribute("item") Item item1) {
        itemRepository.save(item1);
        return "redirect:/basic/items/" + item1.getId();

    }

    @PostMapping("/add")
    public String addItem4(Item item1, RedirectAttributes redirectAttributes) {
        Item savedItem = itemRepository.save(item1);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("IsSaved", true);
        return "redirect:/basic/items/{itemId}";

    }


    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable Long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "basic/springmvc1/editForm";
    }

    @PostMapping("/{itemId}/edit")
    public String edit(@PathVariable Long itemId, @ModelAttribute ItemUpdateParamDto itemUpdateParamDto) {
        itemRepository.update(itemId, itemUpdateParamDto);
        return "redirect:/basic/items/{itemId}";

    }

    /**
     * 테스트용 데이터 추가
     */
    @PostConstruct
    public void init() {

        itemRepository.save(new Item("itemA", 1000, 10));
        itemRepository.save(new Item("itemB", 1000, 10));
        log.info("itemRepository= {}", itemRepository);

    }
}
