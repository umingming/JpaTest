package jpql;

import javax.persistence.*;

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
            tx.commit();
            //TypedQuery<Member> query = em.createQuery("select m from Member m where m.id = 10", Member.class);
            /*
            // 파라미터 관련
            TypedQuery<Member> query = em.createQuery("select m from Member m where m.username = :username", Member.class);
            query.setParameter("username", "member1");

            Member result = query.getSingleResult();  //값이 하나면 그냥 싱글리저트 사용할 수 있어. 이럴 땐 where 절로 쿼리 걸어줘야 함.
            System.out.println(result.getUsername());

            */

            //이름 기준 파라미터 바인딩이 권장됨.
            Member result1 = em.createQuery("select m from Member m where m.username = :username", Member.class)
                    .setParameter("username", "member1")
                    .getSingleResult();

            //위치 기준 파라미터 바인딩
            Member result2 = em.createQuery("select m from Member m where m.username = ?1", Member.class)
                    .setParameter(1, "member1")
                    .getSingleResult();



            /*
                getResultList(); 결과가 하나 이상일 때, 리스트 반환함. 결과가 없으면 빈 리스트 반환
                getSingleResult(); 결과가 무조건 하나여야 함. 단일 객체 반환. 값이 무조건 있을 때 사용해야 함. 아니면 에러남.
             */

            /*
                파라미터 바인딩; 이름 기준/ 위치 기준
             */

        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }

        emf.close();

    }
}
