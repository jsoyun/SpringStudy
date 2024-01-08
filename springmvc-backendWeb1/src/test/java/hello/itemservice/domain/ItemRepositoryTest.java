package hello.itemservice.domain;

import hello.springmvc.itemservice.domain.Item.ItemRepository;
import hello.springmvc.itemservice.domain.dto.Item;
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
        hello.springmvc.itemservice.domain.Item.Item item = new hello.springmvc.itemservice.domain.Item.Item("itemA", 10000, 10);

        //when
        hello.springmvc.itemservice.domain.Item.Item savedItem = itemRepository.save(item);

        //then
        hello.springmvc.itemservice.domain.Item.Item findItem = itemRepository.findById(item.getId());
        assertThat(findItem).isEqualTo(savedItem);
    }

    @Test
    void findAll() {
        //given
        hello.springmvc.itemservice.domain.Item.Item item = new hello.springmvc.itemservice.domain.Item.Item("itemA", 10000, 10);
        hello.springmvc.itemservice.domain.Item.Item item2 = new hello.springmvc.itemservice.domain.Item.Item("item2", 20000, 20);

        itemRepository.save(item);
        itemRepository.save(item2);


        //when
        List<hello.springmvc.itemservice.domain.Item.Item> result = itemRepository.findAll();

        //then
        assertThat(result.size()).isEqualTo(2);
    }

    @Test
    void updateItem() {
        //given
        hello.springmvc.itemservice.domain.Item.Item item = new hello.springmvc.itemservice.domain.Item.Item("itemA", 10000, 10);

        hello.springmvc.itemservice.domain.Item.Item savedItem = itemRepository.save(item);
        Long itemId = savedItem.getId();


        //when
        Item itemUpdateParamDto = new Item("newItem", 2000, 10);
        itemRepository.update(itemId, itemUpdateParamDto);

        //then
        hello.springmvc.itemservice.domain.Item.Item findItem = itemRepository.findById(itemId);
        assertThat(findItem.getItemName()).isEqualTo(itemUpdateParamDto.getItemName());
        assertThat(findItem.getPrice()).isEqualTo(itemUpdateParamDto.getPrice());
        assertThat(findItem.getQuantity()).isEqualTo(itemUpdateParamDto.getQuantity());
    }

}
