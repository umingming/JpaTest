package com.ex;

import javax.persistence.*;

/*
    자식 클래스에 Entity 매핑을 안 하면 그냥 Item만 생겨.
    근데 엔티티 매핑을 하고 실행하면 Item 클래스에 자식 필드들이 다 칼럼으로 들어감.
    create table Item (
       DTYPE varchar(31) not null,
        id bigint not null,
        name varchar(255),
        price integer not null,
        artist varchar(255),
        actor varchar(255),
        director varchar(255),
        primary key (id)
    )

    그래서 어노테이션을 써야 해.
    @Inheritance라는 어노테이션이 있어.
    @Inheritance(strategy = InheritanceType.JOINED) 라고 잡으면 조인전략 설정이 됨.
    create table Item (
       id bigint not null,
        name varchar(255),
        price integer not null,
        primary key (id)
    )
    create table Movie (
       actor varchar(255),
        director varchar(255),
        id bigint not null,
        primary key (id)
    )
    alter table Movie
       add constraint FK5sq6d5agrc34ithpdfs0umo9g
       foreign key (id)
       references Item
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Item {
    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private int price;
}
