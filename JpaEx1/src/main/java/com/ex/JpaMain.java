package com.ex;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.time.LocalDateTime;
import java.util.List;

/*
    임베디드 타입과 테이블 매핑
    - 임베디드 타입은 엔티티의 값일 뿐임.
    - 임베디드 타입을 사용하기 전과 후에 매핑하는 테이블 같음.
    - 객체와 테이블을 아주 세밀하게 매핑하는 것이 가능함.
    - 잘 설계한 ORM 애플리케이션은 매핑한 테이블의 수보다 클래스의 수가 더 많음.
 */

/*
    속성 재정의; AttributeOverride
    한 엔티티에서 같은 값 타입을 사용하면?
    컬럼 명이 중복됨.
    여럿이면 @AttributeOverrides, 하나면 @AttributeOverride 사용해 컬럼명 속성 재정의 하는 것

    @AttributeOverrides({
        @AttributeOverride(name="city", column=@Column(name="WORK_CITY")),
        @AttributeOverride(name="street", column=@Column(name="WORK_STREET")),
        @AttributeOverride(name="zipcode", column=@Column(name="WORK_ZIPCODE"))
    })
*/
/*
    임베디드 타입과 null
    임베디드 타입의 값이 null이면, 매핑한 컬럼 모두 null
 */
public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            Member member = new Member();
            member.setUsername("member");
            member.setAddress(new Address("A", "B", "C"));
            member.getFavoriteFoods().add("치킨");
            member.getFavoriteFoods().add("족발");
            member.getFavoriteFoods().add("피자");

            member.getAddressHistory().add(new Address("A", "B", "D"));
            member.getAddressHistory().add(new Address("E", "B", "D"));
            /*
                값 타입 컬렉션이 멤버 저장할 때 같이 저장됨.
                본인의 생명 주기가 없어 address는 member에 의졶남.
             */
            em.persist(member);

            em.flush();
            em.clear();
            System.out.println("=================");
            em.find(Member.class, member.getId());
            System.out.println("=================");

            /*
                값 타입 컬렉션은 지연 로딩 전략 사용해 조회함.
             */

            Member findMember = em.find(Member.class, member.getId());
//            findMember.getAddress().setCity("newCity");
            Address a = findMember.getAddress();
            findMember.setAddress(new Address("newCity", a.getStreet(), a.getZipcode()));
            findMember.getFavoriteFoods().remove("치킨");
            findMember.getFavoriteFoods().add("한식");
            /*
                마치 영속성 전이처럼 관리할 수 있음.
             */
            findMember.getAddressHistory().remove(new Address("newCity", a.getStreet(), a.getZipcode()));




            tx.commit();
        } catch (Exception e){
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }
}
