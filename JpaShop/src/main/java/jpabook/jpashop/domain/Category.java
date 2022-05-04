package jpabook.jpashop.domain;

import org.hibernate.annotations.Fetch;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Entity
public class Category {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    /*
        @ManyToOne은 기본적으로 즉시로딩이라, 지연으로 변경해야 함.
        (fetch = FetchType.LAZY)
        static import하면 LAZY로 설정 ->(fetch = LAZY)
     */
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "PARENT_ID")
    private Category parent;

    @OneToMany(mappedBy = "parent")
    private List<Category> child = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "CATEGORY_ITEM",
            joinColumns = @JoinColumn(name = "CATEGORY_ID"),
            inverseJoinColumns = @JoinColumn(name = "ITEM_ID")
    )
    private List<Item> items = new ArrayList<>();
}
