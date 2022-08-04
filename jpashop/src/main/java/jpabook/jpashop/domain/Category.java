package jpabook.jpashop.domain;

import jpabook.jpashop.jpashop.domain.Item;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Category {
    @Id @GeneratedValue
    @Column(name = "category_id")
    private Long id;

    private String name;

    @ManyToMany
    @JoinTable(name = "category_item",
        joinColumns =  @JoinColumn(name = "category_id"),
            inverseJoinColumns = @JoinColumn(name = "item_id"))
    private List<Item> itemList = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Category parent;

    @OneToMany(mappedBy = "parent")  //셀프 양방향 연관관계
    private List<Category> childList = new ArrayList<>();

    public void addChildCategory(Category child) {
        this.childList.add(child);
        child.setParent(this);
    }
}
