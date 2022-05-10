package helloJpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {

            /*
            String qlString = "select m from Member m where m.username like '%kim%'"; //단순 스트링 동적 쿼리 만들기 어려움
            List<Member> result = em.createQuery(
                    "select m from Member m where m.username like '%kim%'", //단순 스트링 동적 쿼리 만들기 어려움
                    // qlString으로 걍 변수 사용할 수 있음. 당연.
                    Member.class
            ).getResultList();
            */
            // 마이바티스는 동적 쿼리를 편하게 짤 수 있는 장점이 있음.
            /*
                여기서 Member는 테이블이 아니라 엔티티 클래스를 지칭함. 그니까 클래스
             */
            /*
                실제 실행 sql
                select
                    member0_.MEMBER_ID as member_id1_9_,
                    member0_.city as city2_9_,
                    member0_.street as street3_9_,
                    member0_.zipcode as zipcode4_9_,
                    member0_.LOCKER_ID as locker_id8_9_,
                    member0_.endDate as enddate5_9_,
                    member0_.startDate as startdate6_9_,
                    member0_.TEAM_ID as team_id9_9_,
                    member0_.USERNAME as username7_9_
                from
                    Member member0_
                where
                    member0_.USERNAME like '%kim%'

                JPQL 문법이 완전 똑같삼삼
                여기서 m이란 멤버 엔티티 자체를 조회하란 의미임.
               select
                    m
                from
                    Member m
                where
                    m.username like '%kim%'
             */
            /*
            for(Member member : result) {
                System.out.println("member = " + member);
            }

             */

            //크라이테리아 사용 준비
            /*CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Member> query = cb.createQuery(Member.class);
*/
            //조회를 시작할 클래스인 루트 클래스
  /*          Root<Member> m = query.from(Member.class);
*/
            //쿼리 생성

  /*          CriteriaQuery<Member> cq = query.select(m).where(cb.equal(m.get("username"), "kim"));
            List<Member> resultList = em.createQuery(cq).getResultList();
*/
            /*
            select
                generatedAlias0
            from
                Member as generatedAlias0
            where
                generatedAlias0.username=:param0
             */

            /*
                select
                    member0_.MEMBER_ID as member_id1_9_,
                    member0_.city as city2_9_,
                    member0_.street as street3_9_,
                    member0_.zipcode as zipcode4_9_,
                    member0_.LOCKER_ID as locker_id8_9_,
                    member0_.endDate as enddate5_9_,
                    member0_.startDate as startdate6_9_,
                    member0_.TEAM_ID as team_id9_9_,
                    member0_.USERNAME as username7_9_
                from
                    Member member0_
                where
                    member0_.USERNAME=?
             */
//
//            List<Member> list = query.selectFrom(m)
//                                     .where(m.get.gt(18))
//                                     .orderBy(m.name.desc())
//                                     .fetch();

            /*
            네이티브 쿼리
            String sql = "SELECT ID, AGE, TEAM_ID, NAME FROM MEMBER WHERE NAME = 'KIM'";
            List<Member> resultList = em.createNativeQuery(sql, Member.class).getResultList();
            */

            /*
                영속성 컨텍스트는 플러시가 되어야 JPA에 영속됨.
             */

/*
            Member member = new Member();
            member.setUsername("member1");
            em.persist(member);
            List<Member> resultList = em.createNativeQuery("select MEMBER_ID, city, street, zipcode, USERNAME from MEMBER", Member.class)
                                        .getResultList();
            for (Member member1 : resultList){
                System.out.println("member1 = " + member1);
            }
*/

            /* 네이티브 sql 동적 쿼리
                select
                    MEMBER_ID,
                    city,
                    street,
                    zipcode,
                    USERNAME
                from
                    MEMBER
                member1 = helloJpa.Member{id=1, username='member1', team=null}
             */
            /*

            Member member = new Member();
            member.setUsername("member1");
            em.persist(member);

            em.flush(); // 강제로 플러쉬해야
            conn.executeQuery("select * from member"); //아래 코드가 실행됨!
            for (Member member1 : resultList){
                System.out.println("member1 = " + member1);
            }

             */

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
