package jpql;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {

            Team teamA = new Team();
            teamA.setName("teamA");
            em.persist(teamA);

            Team teamB = new Team();
            teamB.setName("teamB");
            em.persist(teamB);

            Member memberA = new Member();
            memberA.setUsername("memberA");
            memberA.setTeam(teamA);

            Member memberB = new Member();
            memberB.setUsername("memberB");
            memberB.setTeam(teamA);

            Member memberC = new Member();
            memberC.setUsername("memberC");
            memberC.setTeam(teamB);

            em.flush();
            em.clear();

            String query = "select m from Member m where m = :member";
            Member member = em.createQuery(query, Member.class)
                    .setParameter("member", memberA)
                    .getSingleResult();
            System.out.println("member = " + member);

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }

}
