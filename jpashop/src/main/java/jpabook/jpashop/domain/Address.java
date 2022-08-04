package jpabook.jpashop.domain;

import lombok.Getter;

import javax.persistence.Embeddable;

@Embeddable
@Getter //Setter 제거해서 변경 불가능하게
public class Address {
    private String city;
    private String street;
    private String zipcode;

    protected Address() {
    } //값 타입 기본 생성자에 protected 주기; JPA 구현 라이브러리가 객체를 생성할 때 리플렉션 같은 기술을 사용하기 때문임.

    public Address(String city, String street, String zipcode) {
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }
}
