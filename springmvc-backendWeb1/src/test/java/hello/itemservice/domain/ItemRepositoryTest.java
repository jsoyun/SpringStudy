package hello.itemservice.domain;

import hello.springmvc.itemservice.domain.Item;
import hello.springmvc.itemservice.domain.ItemRepository;
import hello.springmvc.itemservice.domain.dto.ItemUpdateParamDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ItemRepositoryTest {

    ItemRepository itemRepository = new ItemRepository();

    @AfterEach
        //동작이 끝날 때마다 실행됨
    void afterEach() {
        itemRepository.clearStore();
    }

    @Test
    void sava() {
        //given
        Item item = new Item("itemA", 10000, 10);

        //when
        Item savedItem = itemRepository.save(item);

        //then
        Item findItem = itemRepository.findById(item.getId());
        assertThat(findItem).isEqualTo(savedItem);
    }

    @Test
    void findAll() {
        //given
        Item item = new Item("itemA", 10000, 10);
        Item item2 = new Item("item2", 20000, 20);

        itemRepository.save(item);
        itemRepository.save(item2);


        //when
        List<Item> result = itemRepository.findAll();

        //then
        assertThat(result.size()).isEqualTo(2);
    }

    @Test
    void updateItem() {
        //given
        Item item = new Item("itemA", 10000, 10);

        Item savedItem = itemRepository.save(item);
        Long itemId = savedItem.getId();


        //when
        ItemUpdateParamDto itemUpdateParamDto = new ItemUpdateParamDto("newname", 555, 11);
        itemRepository.update(itemId, itemUpdateParamDto);

        //then
        Item findItem = itemRepository.findById(itemId);
        assertThat(findItem.getItemName()).isEqualTo(itemUpdateParamDto.getItemName());
        assertThat(findItem.getPrice()).isEqualTo(itemUpdateParamDto.getPrice());
        assertThat(findItem.getQuantity()).isEqualTo(itemUpdateParamDto.getQuantity());
    }

}
