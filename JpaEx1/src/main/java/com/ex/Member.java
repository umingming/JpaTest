package com.ex;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/*
    변수는 카멜을 쓰나, db는 언더스코어를 사용하므로 애매한 거만 컬럼 어노테이션으로 매핑하기
 */
@Entity
public class Member {
    @Id @GeneratedValue       //@GeneratedValue는 기본 auto임.
    @Column(name = "MEMBER_ID")
    private Long id;
    @Column(name = "USERNAME")
    private String username;

    @ManyToOne(fetch = FetchType.EAGER)                 //멤버 입장에서 many고 team은 one이기 때문, 생각을 해라 하나의 팀에 여러 멤버가 있잖음.
    @JoinColumn(name = "TEAM_ID")            //멤버테이블의 컬럼과 매핑해야 함. 멤버 테이블엔 TEAM_ID가 있겠지? 그걸 적는 거임.
    private Team team;     //어노테이션 매핑을 안 하면 에러남. JPA한테 관계에 대한 정보를 줘야하기 때문.

    @ManyToMany
    @JoinTable(name = "MEMBER_PRODUCT") //테이블명
    private List<Product> products = new ArrayList<>();

    @OneToOne
    @JoinColumn(name = "LOCKER_ID")
    private Locker locker;

    public Team getTeam() {
        return team;
    }

    public void changeTeam(Team team) {                //이름을 set -> change로 수정해야 단순한 setter가 아님을 직관적으로 알 수 있음.
        this.team = team;
        team.getMembers().add(this); //this는 나 자신(Member)임.
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    @Override
    public String toString() {
        return "Member{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", team=" + team +
                '}';
    }

    @Embedded
    private Period period;
    @Embedded
    private Address address;

    @ElementCollection
    @CollectionTable(name = "FAVORITE_FOOD", joinColumns = @JoinColumn(name = "MEMBER_ID"))
    @Column(name = "FOOD_NAME")
    private Set<String> favoriteFoods = new HashSet<>();

    @ElementCollection
    @CollectionTable(name = "ADDRESS", joinColumns = @JoinColumn(name = "MEMBER_ID"))
    private List<Address> addressHistory = new ArrayList<>();


    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public Locker getLocker() {
        return locker;
    }

    public void setLocker(Locker locker) {
        this.locker = locker;
    }

    public Period getPeriod() {
        return period;
    }

    public void setPeriod(Period period) {
        this.period = period;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Set<String> getFavoriteFoods() {
        return favoriteFoods;
    }

    public void setFavoriteFoods(Set<String> favoriteFoods) {
        this.favoriteFoods = favoriteFoods;
    }

    public List<Address> getAddressHistory() {
        return addressHistory;
    }

    public void setAddressHistory(List<Address> addressHistory) {
        this.addressHistory = addressHistory;
    }
}