package jpabook.jpashop.domain;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "ORDERS")   //ORDER 예약어인 DB가 있어서
public class Order {
    @Id @GeneratedValue
    @Column(name = "ORDER_ID")
    private Long id;
//    @Column(name = "MEMBER_ID")
//    private Long memberId;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;      //가급적이면 단방향 매핑이 좋다.

    @OneToMany(mappedBy = "order")    //OrderItem의 order
    private List<OrderItem> orderItems = new ArrayList<>();

    /*
            스프링부트를 쓸 경우, 캐멀을 자동으로 언더스코어로 수정해줄 수 있음. 짱신기. //TODO 찾아보기
            orderDate(카멜표기)를 원하지 않음. ORDER_DATE, order_date
         */
    private LocalDateTime orderDate;
    @Enumerated(EnumType.STRING)    //Enum 타입은 항상 String!
    private OrderStatus status;

    @OneToOne
    @JoinColumn(name = "DELEVERY_ID")
    private Delivery delivery;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public void addOrderItem(OrderItem orderItem) {       //연관 관계 편의 메소드
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }
}
