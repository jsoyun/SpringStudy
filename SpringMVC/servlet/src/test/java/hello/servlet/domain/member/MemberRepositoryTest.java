package hello.servlet.domain.member;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class MemberRepositoryTest {

    MemberRepository memberRepository = MemberRepository.getInstance();

    //테스트 끌날 때마다 초기화
    @AfterEach
    void afterEach(){
        memberRepository.clearStore();
    }

    //저장테스트
    @Test
    void save(){
        //given
        Member member = new Member("hello",20); //이거 하면서 생성자 새로 만들었는데 맞나?
        //when
        Member saveMember = memberRepository.save(member);
        //then
        Member findMember = memberRepository.findById(saveMember.getId());
        assertThat(findMember).isEqualTo(saveMember);


    }

    //모든걸 조회
    @Test
    void findAll(){
        //given
        Member member1 = new Member("member1", 20);
        Member member2 = new Member("member1", 20);

        memberRepository.save(member1);
        memberRepository.save(member2);

        //when
        List<Member> result = memberRepository.findAll();
        //then
        assertThat(result.size()).isEqualTo(2);
        assertThat(result).contains(member1,member2);

    }


}