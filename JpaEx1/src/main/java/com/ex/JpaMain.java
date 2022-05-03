package com.ex;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.time.LocalDateTime;
import java.util.List;

public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            Team team = new Team();
            team.setName("A");
            em.persist(team);

            Member member1 = new Member();
            member1.setUsername("member1");
            member1.setTeam(team);
            em.persist(member1);

            em.flush();
            em.clear();

            Member m = em.find(Member.class, member1.getId());

            System.out.println(m.getTeam().getClass());  //class com.ex.Team$HibernateProxy$FmZ8402o
            /*
                member 객체에서 fetch 타입을 lazy로 설정해서 프록시로 조회하는 것임.
             */

            m.getTeam().getName();
            // 실제 사용하는 시점에 DB가 초기화 됨.
            System.out.println(m.getTeam().getClass());  //class com.ex.Team$HibernateProxy$FmZ8402o
            // 즉시 로딩은 class com.ex.Team, 같이 조인하는 거

//          System.out.println(m1 instanceof Member);  // == 비교 큰 일남! 그냥 instanceof 사용
//          System.out.println(m2 instanceof Member);  // == 비교 큰 일남! 그냥 instanceof 사용


            tx.commit();
        } catch (Exception e){
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }

}
