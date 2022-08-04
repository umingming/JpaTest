package jpabook.jpashop.domain;

import jpabook.jpashop.jpashop.domain.Item;
import jpabook.jpashop.jpashop.domain.Order;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED) //생성메서드 있으면, 기본 생성자로 객체 생성 안 되게 막기
public class OrderItem {
    @Id @GeneratedValue
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private jpabook.jpashop.jpashop.domain.Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    private int orderPrice;

    private int count;

    //생성 메서드
    public static OrderItem createOrderItem(Item item, int orderPrice, int count) {
        OrderItem orderItem = new OrderItem();
        orderItem.setItem(item);
        orderItem.setOrderPrice(orderPrice);
        orderItem.setCount(count);

        item.removeStock(count);
        return orderItem;
    }

    // 비지니스 로직
    public void cancel() {
        getItem().addStock(count);
    }

    //조회 로직
    public int getTotalPrice() {
        return getOrderPrice() * getCount();
    }
}