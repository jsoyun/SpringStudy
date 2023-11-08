package hello.core.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

//구현체가 하나만 있을 때 Impl
@Component
public class MemberServiceImpl implements MemberService{

    private final MemberRepository memberRepository;
    @Autowired //자동으로 이런 코드가 들어간다고 생각하면 된다. ac.getBean(MemberRepository.class)
    public MemberServiceImpl (MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }


    @Override
    public void join(Member member) {
        memberRepository.save(member);
    }

    @Override
    public Member findMember(Long memberId) {
        return memberRepository.findById(memberId);
    }

    ///테스트 용도
    public MemberRepository getMemberRepository(){
        return memberRepository;
    }
}