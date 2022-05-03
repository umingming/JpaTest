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

/*
    @DiscriminatorColumn 어노테이션 설정시 DTYPE이란 컬럼이 생김.
    조인 전략에선 없어도 무방함. SINGTABLECOLUMN에선 필수적
    DTYPE은 자식 엔티티의 이름이 들어감! ex)MOVIE
    name 속성을 사용해 이름을 변경할 수 있음.
    create table Item (
       DTYPE varchar(31) not null,
        id bigint not null,
        name varchar(255),
        price integer not null,
        primary key (id)
    )
 */
/*
    단일 테이블 전략
    @Inheritance(strategy = InheritanceType.SINGLE_TABLE)
    @DiscriminatorColumn 어노테이션 설정 안 해도 DTYPE 생성됨.
    그니까 이름 설정하려면 걍 어노테이션 달렴.
 */
/*
    구현 클래스마다 테이블 전략
    @Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
    Item 클래스가 abstract가 되는 게 맞음. -> 처음부터 이게 맞아 걍
    @DiscriminatorClumn 이 필요가 없어. 넣어도 구분 안 됨.
    item(부모)클래스를 찾을 때 난리가 나는 거임, union 다 날려서 하나씩 있나 찾아봐야 함.
 */
/*
    장단점
    1. 조인 전략
        - 장점: 테이블 정규화, 외래 키 참조 무결성 제약조건 활용, 저장공간 효율화
        - 단점: 조회시 조인을 많이 사용, 성능 저하, 조회 쿼리가 복잡함, 데이터 저장시 insert sql 두 번 호출
        - 기본 적으로 정석인 전략임.
    2. 단일 테이블 전략
        - 장점: 조인이 필요가 없고, 일반적으로 조회 성능이 빠름, 조회 쿼리 단순함.
        - 단점: 자식 엔티티가 매핑한 컬럼은 모두 NULL이 허용되어 데이타 무결성 측면에서 바람직하지 않음.
               단일 테이블에 모든 것을 저장하므로 테이블이 커질 수 있고 상황에 따라 조회 성능이 오히려 느려짐.
    3. 구현 클래스마다 테이블 전략
        - 이 전략은 데이터베이스 설계자와, ORM 전문가 둘 다 추천하지 않음.
        - 장점: 서브 타입을 명확하게 구분해서 처리할 때 효과적, not null 제약 조건 사용 가능
        - 단점: 여러 자식 테이블을 함께 조회할 때 성능이 느림, 자식 테이블을 통합해서 쿼리하기 어려움.
 */
/*
    상속관계 매핑
    슈퍼-서브 타입 모델링이 객체 상속과 유사
 */
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Item {
    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private int price;

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

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
