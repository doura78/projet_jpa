package fr.diginamic.recensement.util;


import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

/**
 * Fournit un accès centralisé à l'EntityManagerFactory.
 */
public class JpaUtil {

    private static final EntityManagerFactory emf =
            Persistence.createEntityManagerFactory("recensement");

    /**
     * Retourne la factory JPA unique de l'application.
     * @return EntityManagerFactory
     */
    public static EntityManagerFactory getEntityManagerFactory() {
        return emf;
    }
}
