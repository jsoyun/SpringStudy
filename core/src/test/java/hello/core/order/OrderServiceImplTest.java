package hello.core.order;

import hello.core.discount.FixDiscountPolicy;
import hello.core.member.Grade;
import hello.core.member.Member;
import hello.core.member.MemoryMemberRepository;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OrderServiceImplTest {
    //순수 자바코드로
    //구현체들 조합해서 테스트 코드 짬. 생성자 주입을 해야 필요한 객체를 바로 확인할 수 있다.
    //또한 final 키워드로  생성자를 통해서만 값을 셋팅할 수 있다.
    //fianl이 생성자 만들시에 필요한 객체를 컴파일 에러로 알려준다.
    @Test
    void createOrder (){
        MemoryMemberRepository memberRepository = new MemoryMemberRepository();
        memberRepository.save(new Member(1L, "name", Grade.VIP));
        OrderServiceImpl orderService = new OrderServiceImpl(new MemoryMemberRepository(), new FixDiscountPolicy());
        Order order = orderService.createOrder(1L, "itemA", 10000);
        org.assertj.core.api.Assertions.assertThat(order.getDiscountPrice()).isEqualTo(1000);
    }

}