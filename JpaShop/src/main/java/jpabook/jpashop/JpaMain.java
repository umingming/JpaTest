package jpabook.jpashop;

import jpabook.jpashop.domain.Member;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

/*
    파일이 존재하면 drop table로 지우기, 가장 하위 노드부터 지워야 함.
 */
public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            /*
                주문한 회원 찾기
                Order order = em.find(Order.class, 1L);
                Long memberId = order.getMemberId();
                Member member = em.find(Member.class, memberId);
                -> 객체지향적이지 않음! 객체는 한 번에 찾아야되는데, 식별자(getter)가 있으면 맥이 끊김. !관계형DB!에 맞춘 설계임.
                Member member = order.getMember();
                -> 객체는 참조 그래프로 쭉쭉 찾아야 함!.

                데이터 중심 설계의 문제점
                -현재 방식은 객체 설계를 테이블 설계에 맞춘 방식
                -테이블의 외래키를 객체에 그대로 가져옴
                -객체 그래프 탐색이 불가능
                -참조가 없으므로 UML도 잘못됨.
                => 이 문제들을 연관 관계 매핑으로 해결할 수 있음! 짱
             */
            Member member = new Member();
            member.setName("유밍3");
            em.persist(member);
            tx.commit();
        } catch (Exception e){
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }
}
