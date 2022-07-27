package jpql;

import java.util.List;

import javax.persistence.*;

public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
        	for (int i=0; i<100; i++) {
        		Member member = new Member();
        		member.setUsername("member" + i);
        		member.setAge(i);
        		em.persist(member);
        	}
            
            em.flush();
            em.clear();
            
            List<Member> result = em.createQuery("select m from Member m order by m.age", Member.class)
            						.setFirstResult(0)
            						.setMaxResults(10)
            						.getResultList();
            
            for (Member member : result) {
            	System.out.println(member);
            }
            
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
