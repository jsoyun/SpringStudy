package hello.springmvc.itemservice.domain.Item;

import hello.springmvc.itemservice.domain.dto.ItemUpdateParamDto;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ItemRepository { //아이템 저장소
    //static 사용함 스프링 컨테이너가 싱글톤으로 객체를 관리해주긴 하지만 싱글톤 객체 명시적으로
    private static final Map<Long, Item> store = new HashMap<>();
    private static long sequence = 0L;


    public Item save(Item item) {
        item.setId(++sequence);
        store.put(item.getId(), item);
        return item;
    }

    public Item findById(Long id) {
        return store.get(id);
    }

    public List<Item> findAll() {
        return new ArrayList<>(store.values());

    }

    public void update(Long itemId, ItemUpdateParamDto itemUpdateParamDto) {
        Item findItem = findById(itemId);
        findItem.setItemName(itemUpdateParamDto.getItemName());
        findItem.setPrice(itemUpdateParamDto.getPrice());
        findItem.setQuantity(itemUpdateParamDto.getQuantity());
        findItem.setOpen(itemUpdateParamDto.getOpen());
        findItem.setRegions(itemUpdateParamDto.getRegions());
        findItem.setItemType(itemUpdateParamDto.getItemType());
        findItem.setDeliveryCode(itemUpdateParamDto.getDeliveryCode());

    }

    public void clearStore() {
        store.clear();
    }
}
