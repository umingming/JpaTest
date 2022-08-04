package jpabook.jpashop.domain;

import jpabook.jpashop.jpashop.domain.Address;
import jpabook.jpashop.jpashop.domain.DeliveryStatus;
import jpabook.jpashop.jpashop.domain.Order;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class Delivery {
    @Id @GeneratedValue
    @Column(name = "delivery_id")
    private Long id;

    @OneToOne(mappedBy = "delivery", fetch = FetchType.LAZY)
    private Order order;

    @Embedded
    private Address address;

    @Enumerated(EnumType.STRING)   //ORDINAL은 숫자로 들어감. 중간에 뭐가 추가되면 망하는 거. 그래서 타입은 STRING으로 써라.
    private DeliveryStatus status;
}
