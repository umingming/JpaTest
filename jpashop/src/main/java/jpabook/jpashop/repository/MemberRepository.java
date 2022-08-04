package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository //컴포넌트 스캔을 해서 자동으로 스프링 빈으로 관리가 됨.
@RequiredArgsConstructor
public class MemberRepository {
    /*
    @PersistenceContext //스프링이 엔티티매니저를 만들어서 인젝션 해줌.
    private EntityManager em;

    스프링부트 라이브러리를 쓰면, 위 어노테이션을 Autowired로 변경 가능해 생성자 어노테이션 만들 수 있음.
    */

    private final EntityManager em;

    /*
        팩토리 주입하기
        @PersistenceUnit
        private EntityManagerFactory emf;
        > 거의 쓸 일 없음.
     */

    public void save(Member member) {
        em.persist(member);
    }

    public Member findOne(Long id) {
        return em.find(Member.class, id);
    }

    public List<Member> findAll() {
        return em.createQuery("select m from Member m", Member.class)
                 .getResultList();
    }//jpql은 from의 대상이 테이블이 아니라 엔티티가 되는 거임.

    public List<Member> findByName(String name) {
        return em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();
    }
}
