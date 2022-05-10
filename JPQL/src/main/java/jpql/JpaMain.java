package jpql;

import javax.persistence.*;
import java.util.List;

public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            Member member = new Member();
            member.setUsername("member1");
            member.setAge(10);
            em.persist(member);

            Member member2 = new Member();
            member2.setUsername("member2");
            member2.setAge(30);
            em.persist(member2);
/*

            List<Member> result = em.createQuery("select m from Member m", Member.class)
                                    .getResultList();
            //m이 엔티티니까 result는 엔티티들이 반환됨. 엔티티 프로젝션이란 건, 이 테이블 내 엔티티들이 전부 영속성 컨텍스트에 저장이되고,
            //관리할 수 있다는 뜻임.

            Member foundMember = result.get(0);
            foundMember.setAge(20);
*/
//            List<Team> result = em.createQuery("select m.team from Member m", Team.class)
//                            .getResultList();
//
            //멤버를 찾으나, 팀을 반환하므로 팀클래스로 인자를 설정함.
            //쿼리를 이런 식으로 쓰면 안 돼. 실제 sql 쿼리랑 최대한 비슷하게 작성해야 함. 묵시적 조인

//            List<Team> result = em.createQuery("select t from Member m join m.team t", Team.class)
//                                  .getResultList();
            //조인은 이처럼 명시적으로 하는 게 조음. 명시적 조인


            /*
                select
                    team1_.id as id1_3_,
                    team1_.name as name2_3_
                from
                    Member member0_
                inner join
                    Team team1_
                        on member0_.TEAM_ID=team1_.id
                실제 join 쿼리가 날라감.
             */

//            em.createQuery("select o.address from Order o", Address.class).getResultList();
            //address 칼럼 안 되고 o.붙여서 사용함.

//            List resultList = em.createQuery("select m.username, m.age from Member m").getResultList();

            /*
                프로젝션 여러 값 조회
                1. Qu
             */

            List<MemberDTO> result = em.createQuery("select new jpql.MemberDTO(m.username, m.age) from Member m")
                                        .getResultList();
            //생성자를 통해 값 호출
            MemberDTO memberDTO = result.get(0);

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }

        emf.close();

    }
}
