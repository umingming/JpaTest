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
            Parent parent = new Parent();
            Child child1 = new Child();
            Child child2 = new Child();

            parent.addChild(child1);;
            parent.addChild(child2);;

            em.persist(parent);  //child 를 구태여 넣어줄 필요 없음.

            em.flush();
            em.clear();

            Parent findParent = em.find(Parent.class, parent.getId());
            findParent.getChildList().remove(0);
            /*
                delete
                from
                    Child
                where
                    id=?
                고아 객체 주의; 참조가 제거된 엔티티는 다른 곳에서 참조하지 않는 고아 객체로 보고 삭제하는 기능.
                참조하는 곳이 하나일 때 사용해야 함.
                특정 엔티티가 개인 소유할 때 사용
                @OneToOne, @OneToMany 가능
                개념적으로 부모를 제거하면, 자식은 고아가 됨. 따라서 고아 객체 제거 기능을 활성화 하면, 부모를 제거할 때 자식도 함께 제거
                이것은 CascadeType.REMOVE 처럼 동작함.
             */

            /*
                영속성 전에 + 고아 객체,  -> 생명 주기
                CascadeType.ALL + orphanRemoval = true
                스스로 생명 주기를 관리하는 엔티티는 em.persist()로 영속화, em.remove()로 제거
                두 옵션을 모두 활성화하면 부모 엔티티를 통해서 자식의 생명 주기를 관리할 수 있음.
                도메인 주도 설계의 Aggregate Root 개념을 구현할 때 유용함.
            */

            tx.commit();
        } catch (Exception e){
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }
}
