package com.ex;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/*
    고아객체 제거; 부모 엔티티와 연관관계가 끊어진 자식 엔티티를 자동 삭제.. 말 그대로 고아를 지운다..ㅠ
    orphanRemoval = true
    Parent parent1 = em.find(Parent.class, id);
    parent1.getChildren().remove(0);  -> 자식 엔티티를 컬렉션에서 제거
    DELETE FROM CHILD WHERE ID = ?


 */
@Entity
public class Parent {
    @Id
    @GeneratedValue
    private Long id;
    private String name;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Child> childList = new ArrayList<>();
    /*
        영속성 전이; CASCADE
        -영속성 전이는 연관 관계를 매핑하는 것과 아무 관련 없음.
        -엔티티를 영속화할 때 연관된 엔티티도 함께 영속화하는 편리함 제공

        ALL; 모두 적용
        PERSISTE; 영속
        REMOVE; 삭제
        MERGE, REFRESH, DETACH 있음.

        파일을 여러 군데에서 관리하면 쓰면 안 돼!
        게시판의 첨부파일 같을 때만 사용하는 겨
        CHILD가 다른 엔티티와 연관 관계가 있을 경우 사용 지양.
     */

    //연관관계 편의 메소드
    public void addChild(Child child){
        childList.add(child);
        child.setParent(this);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Child> getChildList() {
        return childList;
    }

    public void setChildList(List<Child> childList) {
        this.childList = childList;
    }
}
