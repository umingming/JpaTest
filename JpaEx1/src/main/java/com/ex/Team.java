package com.ex;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Team {
    @Id
    @GeneratedValue
    @Column(name = "TEAM_ID")
    private Long id;
    private String name;

    /*
        mappedBy의 정체 <- 이해하기 어려움. 객체와 테이블 관계를 맺는 차이를 이해해야 함.
        객체와 테이블 관계 차이가 뭔가?
        객체 연관 관계 = 2개, 회원 -> 팀, 팀 -> 회원  (단방향)
        테이블 연관 관계 = 1개, 회원 <-> 팀  (양방향)
        [객체]
        -객체의 양방향 관계는 사실 양방향 관계가 아니라 서로 다른 단방향 관계 2개임.
        -객체를 양방향으로 참조하려면 단방향 연관관계를 2개 만들어야 함.
        [테이블]
        -테이블은 외래키 하나로 두 테이블 연관관계 관리
        -외래키로 조인 사용함.
        => 그래서 둘 중 하나로 외래 키를 관리해야 함! ; 연관관계 주인을 정해야한다는 것,
        연관 관계 주읜
        - 객체의 두 관계 중 하나를 연관 관계의 주인으로 지정
        - 연관 관계의 주인만이 외래 키 관리
        - 주인이 아닌 쪽은 읽기만 가능
        - 주인은 mappedBy 속성 사용하지 않음.
        - 주인이 아니면 mappedBy 속성으로 주인 지정함.
        누구를 주인으로?
        - 외래 키가 있는 곳을 주인으로 해야 해!
        MEMBER 엔티티에 TEAM_ID가 있잖음.
        그래서 Member의 team 객체가 진짜 연관관계에 주인임! 이걸 진짜 매핑이라고 함.
        가짜 매핑은? 주인의 반대편인 Team 객체의 members임. 여기서 member는 엔티티 상에 없잖음.
        team의 members가 변경 가능하면 엄~청 헷갈림.
        DB입장에선 외래키가 있는 쪽이 N임. 즉 N의 엔티티가 연관관계의 주인이 된다는 것이다.
    */

    /*
        양방향 매핑시 가장 많이 하는 실수
        -연관 관계의 주인에 값을 입력하지 않음.
     */
    @OneToMany(mappedBy = "team")         //멤버에서 team 변수가 있고, 이와 매핑하는 거임.
    private List<Member> members = new ArrayList<>();    //ArrayList로 초기화하는 건 관례임, add 할 때 null 포인트 방지.

    public List<Member> getMembers() {
        return members;
    }

    public void addMember(Member member){
        member.setTeam(this);
        members.add(member);
    }
    /*
        연관관계 편의 메소드는 양쪽에 있으면 문제 생길 수 있어 이거 쓸 거면 멤버의 편의 메소드는 지워야 함. 하나만 하거라.
        이거 상황마다 다르니까 연관 관계 주인 클래스라고 편의 메소드 넣고 그런 거 아님.
        잘못하면 무한루프 걸림.
     */


    public void setMembers(List<Member> members) {
        this.members = members;
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

    @Override
    public String toString() {
        return "Team{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", members=" + members +
                '}';
    }
    /*
        양쪽 모두에 toString 걸려 있으면 무한 루프 걸려버림. toString 외에도, lombok, JSON 조심할 것.
        권장
        1. lombok으로 toString 쓰지 말 것
        2. 컨트롤러에는 엔티티 절대 반환하지 말 것! 컨트롤러에서 엔티티를 JSON으로 반환하면 무한루프 생길 뿐만 아니라
            엔티티를 변형 하는 순간 API 스펙이 바뀜.
            엔티티는 DTO로 변환해서 반환하는 것을 추천함. 컨트롤러에서 하지마,,,
     */
}