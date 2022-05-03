package com.ex;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

/*
    파일이 존재하면 drop table로 지우기, 가장 하위 노드부터 지워야 함.
 */
public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            /*
                객체를 테이블에 맞추어 모델링
                1. 협력 관계를 만들 수 없음.
                    - 테이블은 외래 키 조인을 사용해서 연관된 테이블을 찾음.
                    - 객체는 참조를 사용해 연관된 객체를 찾음.
                    - 테이블과 객체 사이에는 이런 큰 간격이 있음.
                Team team = new Team();
                team.setName("TeamA");
                em.persist(team);
                Member member = new Member();
                member.setUsername("member1");
                member.setTeamId(team.getId());
                em.persist(member);

                Member findMember = em.find(Member.class, member.getId());
                Long findTeamId = findMember.getTeamId();
                Team findTeam = em.find(Team.class, findTeamId);     //객체 지향적이지 않음!
             */

             /*
                양방향 매핑시 가장 많이 하는 실수
                -연관 관계의 주인에 값을 입력하지 않음. ;역방향(주인이 아닌 방향)만 연관관계 설정
                -순수한 객체 관계를 고려하면 항상 양쪽 다 값을 입력해야 함.
                -연관관계 편의 메소드를 생성
                -양방향 매핑시에 무한 루프를 조심
             */

            /*
                양방향 매핑 정리
                - 단방향 매핑만으로도 이미 연관관계 매핑은 완료!
                - 양방향 매핑은 반대 방향으로 조회 기능이 추가된 것 뿐
                - 실무에선 역방향으로 탐색할 일이 많음.
                - 단방향 매핑을 잘 하고 양방향은 필요할 때 추가해도 됨.
             */

            /*
                연관 관계 주인을 정하는 기준
                - 비지니스 로직을 기준으로 연관 관계의 주인을 선택하면 안 됨.
                - 연관 관계 주인은 외래 키의 위치를 기준으로 정함.
             */

//            Team team = new Team();
//            team.setName("TeamA");
//            em.persist(team);
//
//            Member member = new Member();
//            member.setUsername("member1");
//            member.setTeam(team);
//            em.persist(member);


            Member member = new Member();
            member.setUsername("member1");
            em.persist(member);

            Team team = new Team();
            team.setName("TeamA");
            team.getMembers().add(member);
            em.persist(team);
//
//            em.flush();                    //이걸 해줘야 DB에서 깔끔하게 값을 가져 옴.
//            em.clear();
//            Member findMember = em.find(Member.class, member.getId());  //영속성 컨텍스트 덕분에 select 쿼리 안 날라감.

//            Team findTeam = findMember.getTeam();
//            System.out.println("findTeam = " + findTeam.getId());
//
//            Team newTeam = em.find(Team.class, 100L);
//            findMember.setTeam(newTeam);      //팀을 setter 이용해 수정할 수 있음.

//            List<Member> members = findMember.getTeam().getMembers();

            //flush 안 해주면 출력 안 됨. 커밋 전이라 테이블이 비어 있어서
//            for(Member m : members){
//                System.out.println("m = " + m.getUsername());
//            }

//            Member member = new Member();
//            member.setUsername("member1");
//            em.persist(member);
//
//            Team team = new Team();
//            team.setName("TeamA");
//            team.getMembers().add(member);   //집어넣어도 멤버의 팀이 null이 됨!!!! 역방향(주인이 아닌 방향)만 연관관계 설정을 했기 때문임. 무소용이다,,
//            em.persist(team);
//
            // JPA 입장에서 제대로된 코드로 member에 팀 값이 들어감.
//            Team team = new Team();
//            team.setName("TeamA");
//            em.persist(team);
//
//            Member member = new Member();
//            member.setUsername("member1");
//            member.changeTeam(team);   //add와 깜빡깜빡할 수 있음 -> 연관관계 편의 메소드를 만들자!
//            em.persist(member);

            // 이걸 changeTeam에 추가하면 없어도 알아서 돌아가는 거임! 짱짱

//            em.flush();
//            em.clear();     //얘네 없으면 컬렉션에 값이 없어서 select 쿼리 안 날라감.

//            Team findTeam = em.find(Team.class, team.getId());  //1차 캐시
//            List<Member> members = findTeam.getMembers();
//            /*
//                List에 값을 세팅하지 않아도 호출됨.
//             */
//
//            for (Member m : members){
//                System.out.println("m = " + m.getUsername());
//            }

            tx.commit();
        } catch (Exception e){
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }
}
