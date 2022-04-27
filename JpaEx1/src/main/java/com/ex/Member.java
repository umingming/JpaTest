package com.ex;

import javax.persistence.*;

/*
    변수는 카멜을 쓰나, db는 언더스코어를 사용하므로 애매한 거만 컬럼 어노테이션으로 매핑하기
 */
@Entity
public class Member {
    @Id @GeneratedValue       //@GeneratedValue는 기본 auto임.
    @Column(name = "MEMBER_ID")
    private Long id;
    /*
        String을 그냥 매핑하게 되면 varchar의 값이 최대인 255로 들어감.
        이게 싫으면 length 이용하기<- 가급적이면 메타데이터를 클래스에 표현하기.
        어노테이션을 통해 디비를 보지 않고도 제약조건을 이해할 수 있기 때문     */
    @Column(name = "USERNAME")
    private String username;

//    @Column(name = "TEAM_ID")        //이 부분을 주석 처리하면 단방향 매핑이 가능함.
//    private Long teamId;

    @ManyToOne                 //멤버 입장에서 many고 team은 one이기 때문, 생각을 해라 하나의 팀에 여러 멤버가 있잖음.
    @JoinColumn(name = "TEAM_ID")            //멤버테이블의 컬럼과 매핑해야 함. 멤버 테이블엔 TEAM_ID가 있겠지? 그걸 적는 거임.
    private Team team;     //어노테이션 매핑을 안 하면 에러남. JPA한테 관계에 대한 정보를 줘야하기 때문.

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
}
