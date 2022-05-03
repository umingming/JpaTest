package jpabook.jpashop.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/*
    변수는 카멜을 쓰나, db는 언더스코어를 사용하므로 애매한 거만 컬럼 어노테이션으로 매핑하기
 */
@Entity
public class Member extends BaseEntity {
    @Id @GeneratedValue       //@GeneratedValue는 기본 auto임.
    @Column(name = "MEMBER_ID")    //소문자든 대문자든 회사 내규에 따르셈.
    private Long id;
    /*
        String을 그냥 매핑하게 되면 varchar의 값이 최대인 255로 들어감.
        이게 싫으면 length 이용하기<- 가급적이면 메타데이터를 클래스에 표현하기.
        어노테이션을 통해 디비를 보지 않고도 제약조건을 이해할 수 있기 때문     */
    @Column(length = 10)
    private String name;
    private String city;
    private String street;
    private String zipcode;

    @OneToMany(mappedBy = "member")
    private List<Order> orders = new ArrayList<>();
    /*
        orders랑 orderItem 없어도 됨.
        단방향 설계가 제일 중요해!
     */

    public Long getId() {        //getter는 꼭 만들고, setter의 경우 많이 만들면 유지보수성이 떨어지니 주의할 것.
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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }
}
