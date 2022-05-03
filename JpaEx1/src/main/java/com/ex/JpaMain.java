package com.ex;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {

            /*
                Movie를 할당했는데, ITEM에도 INSERT 됨.
                insert
                into
                    Item
                    (name, price, id)
                values
                    (?, ?, ?)
                insert
                into
                    Movie
                    (actor, director, id)
                values
                    (?, ?, ?)

             */
            Movie movie = new Movie();
            movie.setDirector("aaaa");
            movie.setActor("bbbb");
            movie.setName("cccc");
            movie.setPrice(10000);
            em.persist(movie);
            em.flush();
            em.clear();

            /*
                select
                    movie0_.id as id1_4_0_,
                    movie0_1_.name as name2_4_0_,
                    movie0_1_.price as price3_4_0_,
                    movie0_.actor as actor1_8_0_,
                    movie0_.director as director2_8_0_
                from
                    Movie movie0_
                inner join
                    Item movie0_1_
                        on movie0_.id=movie0_1_.id
                where
                    movie0_.id=?
             */
            Item item = em.find(Item.class, movie.getId());
//            Movie findMovie = em.find(Movie.class, movie.getId());
            System.out.println("findMovie = " + item);
            tx.commit();
        } catch (Exception e){
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }
}
