package hello.login.domain.member;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.*;

//TODO:REFACTOR MemberRepository를 interface로 만들어서 별도 구현체를 사용해서 DB연결 등 해보기
@Slf4j
@Repository
public class MemberRepository {

    private static Map<Long, Member> store = new HashMap<>();
    private static long sequence = 0L;

    public Member save(Member member) { //TODO:CHECK:member 객체를 꼭 넘겨줘야 하나??
//        Member.builder().id(++sequence).build(); //TODO:CHECK id값이 왜 null로 되는거지?
        member.setId(++sequence);
        log.info("save member= {} ", member.getId());
        store.put(member.getId(), member);

        return member;
    }

    public Member findById(Long id) {
        return store.get(id);
    }

    //값이 없을 수도 있으니까 null값을 대비해서
    public Optional<Member> findByLoginId(String loginId) {
//        List<Member> all = findAll();
//        for (Member m : all) {
//            if (m.getLoginId().equals(loginId)) {
//                return Optional.of(m);
//            }
//
//        }
//        return Optional.empty();

        return findAll().stream()
            .filter(m -> m.getLoginId().equals(loginId))
            .findFirst();

    }

    public List<Member> findAll() {
        return new ArrayList<>(store.values()); //키값들로 리스트조회
    }

    //테스트시 초기화 목적
    public void clearStore() {
        store.clear();
    }

}
