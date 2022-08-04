package jpabook.jpashop.domain;

import jpabook.jpashop.jpashop.domain.Address;
import jpabook.jpashop.jpashop.domain.Order;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Member {
    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String name;

    @Embedded //내장 타입 표현으로 둘 중 하나만 표기해도 됨.
    private Address address;

    @OneToMany(mappedBy = "member")
    private List<Order> orderList = new ArrayList<>(); //컬렉션은 필드에서 초기화 하는 것이 안전함. 변경하지 말 것.
}
