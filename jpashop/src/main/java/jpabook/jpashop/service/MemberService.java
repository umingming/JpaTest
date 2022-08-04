package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/*
    JPA에서 데이터 변경은 모두 트랜잭션 하에 있어야 함. public 메소드들이 트랜잭션에 걸림.
    readOlny = true 옵션을 걸면, 더 좋음.
    쓰기 엔 이 옵션 안 되고, 읽기일 경우만 트루하기
 */
@Service
@Transactional(readOnly = true)
//@AllArgsConstructor //생성자 선언할 필요 없이 알아서 만들어줌.
@RequiredArgsConstructor // 파이널이 있는 변수만 생성자 만들어줌. 이게 더 나은 방법임.
public class MemberService {
    //@Autowired ;필드 인젝션
    private final MemberRepository memberRepository; //해당 필드는 변경할 일이 없기 때문에 final로 설정해야 함! 컴파일 시점 체크 가능

    /*
        setter 인젝션
        테스트 코드 작성할 때 주입이 용이함. 메서드를 통해 주입하기 때문에 가짜 멤버레퍼지토리 주입 가능.
        단, 개발 중간에 변경 위험이 있음.

    @Autowired
    public void setMemberRepository(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

     */

    /*
        생성자 인젝션
        중간에 바꿀 위험이 없음. 테스트 케이스 작성할 때, 임의로 레퍼지토리 설정 가능
        생성자가 하나일 경우 스프링이 알아서 어노테이션 지정해주기 때문에 @Autowired 생략 가능함.
    @Autowired
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }
     */

    /*
        쓰기 기능이므로 개별적으로 옵션이 없는 트랜잭션 어노테이션 부여함.
        따로 설정할 경우, 해당 메소드가 우선권을 가짐.
     */
    @Transactional
    public Long join(Member member) {
        validateDuplicateMember(member);
        memberRepository.save(member);
        return member.getId();
    }

    /*
        중복 회원 검증
        1. 예외 발생
        2. 밸리데이션 코드가 있어도, DB에서 유니크 제약 조건 거는 게 제일 안전함!
     */
    private void validateDuplicateMember(Member member) {
        List<Member> findMemberList = memberRepository.findByName(member.getName()); //변수명 고민
        if (!findMemberList.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    public List<Member> findMemberList() {
        return memberRepository.findAll();
    }

    public Member findOne(Long memberId) {
        return memberRepository.findOne(memberId);
    }

}
