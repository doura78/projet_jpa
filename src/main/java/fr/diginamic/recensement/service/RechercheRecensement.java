package fr.diginamic.recensement.service;

import fr.diginamic.recensement.entites.Departement;
import fr.diginamic.recensement.entites.Ville;
import fr.diginamic.recensement.util.JpaUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;
import java.util.Scanner;

/**
 * Classe exécutable permettant d'effectuer des recherches
 * dans les données de recensement via des requêtes JPQL.
 */
public class RechercheRecensement {
    public static void main(String[] args) {

        EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();
        Scanner scanner = new Scanner(System.in);

        try {

            // rechercherVillesParPopulation(em);
            // rechercherVillesParPopulationDepartement(em, "78");
            // rechercherVillesParPopulationRegion(em, "44");
            // rechercherPopulationDepartement(em, "34");
            // rechercherPopulationRegion(em, "76");
            // rechercherVillesPopulationDepartement(em, "78", 1000L, 10000L);
            // rechercherVillesPopulationRegion(em, "76", 1000L, 10000L);
             rechercherVillesPopulationFrance(em, 100000L, 500000L);

        } finally {
            em.close();
            scanner.close();
        }
    }

    /**
     * Recherche toutes les villes dans l'ordre de population décroissante.
     *
     * @param em entity manager
     */
    public static void rechercherVillesParPopulation(EntityManager em) {

        String jpql = "SELECT v FROM Ville v ORDER BY v.populationTotale DESC";

        TypedQuery<Ville> query = em.createQuery(jpql, Ville.class);

        List<Ville> villes = query.getResultList();

        for (Ville ville : villes) {
            System.out.println(ville.getNom() + " - " + ville.getPopulationTotale());
        }
        System.out.println("Nombre de résultats : " + villes.size());
    }

    /**
     * Recherche les villes d'un département donné
     * dans l'ordre de population décroissante.
     *
     * @param em              entity manager
     * @param codeDepartement code du département
     */
    public static void rechercherVillesParPopulationDepartement(EntityManager em, String codeDepartement) {

        String jpql = "SELECT v FROM Ville v WHERE v.departement.code = :codeDepartement ORDER BY v.populationTotale DESC";

        TypedQuery<Ville> query = em.createQuery(jpql, Ville.class);
        query.setParameter("codeDepartement", codeDepartement);

        List<Ville> villes = query.getResultList();

        for (Ville ville : villes) {
            System.out.println(ville.getNom() + "_" + ville.getPopulationTotale());
        }
        System.out.println("Nombre de résultats : " + villes.size());
    }

    /**
     * Recherche les villes d'une région donnée
     * dans l'ordre de population décroissante.
     *
     * @param em         entity manager
     * @param codeRegion code de la région
     */
    public static void rechercherVillesParPopulationRegion(EntityManager em, String codeRegion) {

        String jpql = "SELECT v FROM Ville v WHERE v.departement.region.code = :codeRegion ORDER BY v.populationTotale DESC";

        TypedQuery<Ville> query = em.createQuery(jpql, Ville.class);
        query.setParameter("codeRegion", codeRegion);

        List<Ville> villes = query.getResultList();

        for (Ville ville : villes) {
            System.out.println(ville.getNom() + "_" + ville.getPopulationTotale());
        }
        System.out.println("Nombre de résultats : " + villes.size());
    }

    /**
     * Recherche la population totale d'un département donné.
     * La requête doit utiliser un GROUP BY.
     *
     * @param em              entity manager
     * @param codeDepartement code du département
     */
    public static void rechercherPopulationDepartement(EntityManager em, String codeDepartement) {

        String jpql = "SELECT SUM(v.populationTotale) FROM Ville v WHERE v.departement.code = :codeDepartement GROUP BY v.departement.code";

        TypedQuery<Long> query = em.createQuery(jpql, Long.class);
        query.setParameter("codeDepartement", codeDepartement);

        List<Long> resultats = query.getResultList();

        if (resultats.isEmpty()) {
            System.out.println("Aucun résultat pour le département " + codeDepartement);
        } else {
            System.out.println("Population du département " + codeDepartement + " : " + resultats.get(0));
        }
    }

    public static void rechercherPopulationRegion(EntityManager em, String codeRegion) {

        String jpql = "SELECT SUM(v.population) FROM Ville v WHERE v.departement.region.code = :codeRegion GROUP BY v.departement.region.code";

        TypedQuery<Long> query = em.createQuery(jpql, Long.class);
        query.setParameter("codeRegion", codeRegion);

        List<Long> resulats = query.getResultList();
        if (resulats.isEmpty()) {
            System.out.println("Aucun resultats pour la région " + codeRegion);
        } else {
            System.out.println("Population de la région: " +  resulats.get(0));
        }
    }

    /**
     * Recherche les villes d'un département donné dont la population
     * est comprise entre un minimum et un maximum.
     *
     * @param em entity manager
     * @param codeDepartement code du département
     * @param min population minimale
     * @param max population maximale
     */
    public static void rechercherVillesPopulationDepartement(EntityManager em, String codeDepartement, Long min, Long max) {

        String jpql = "SELECT v FROM Ville v WHERE v.departement.code = :codeDepartement AND v.populationTotale BETWEEN :min AND :max ORDER BY v.population";

        TypedQuery<Ville> query = em.createQuery(jpql, Ville.class);
        query.setParameter("codeDepartement", codeDepartement);
        query.setParameter("min", min);
        query.setParameter("max", max);
        List<Ville> villes = query.getResultList();

        for (Ville ville : villes) {
            System.out.println(ville.getNom() + "_" + ville.getPopulationTotale());
        }
    }

    /**
     * Recherche les villes d'une région donnée dont la population
     * est comprise entre un minimum et un maximum.
     *
     * @param em entity manager
     * @param codeRegion code de la région
     * @param min population minimale
     * @param max population maximale
     */
    public static void rechercherVillesPopulationRegion(EntityManager em, String codeRegion, Long min, Long max) {

        String jpql = "SELECT v FROM Ville v WHERE v.departement.code = :codeRegion AND v.population BETWEEN :min AND :max ORDER BY v.population";

        TypedQuery<Ville> query = em.createQuery(jpql, Ville.class);

        query.setParameter("codeRegion", codeRegion);
        query.setParameter("min", min);
        query.setParameter("max", max);

        List<Ville> villes = query.getResultList();

        for (Ville ville :villes) {
            System.out.println(ville.getNom() + "_" + ville.getPopulationTotale());
        }
    }

    /**
     * Recherche les villes de France dont la population
     * est comprise entre un minimum et un maximum.
     *
     * @param em entity manager
     * @param min population minimale
     * @param max population maximale
     */
    public static void rechercherVillesPopulationFrance(EntityManager em, String codeDepartement, Long min, Long max) {

        String jpql = "SELECT v FROM Ville v WHERE v.populationTotale BETWEEN :min AND :max ORDER BY v.population";

        TypedQuery<Ville> query = em.createQuery(jpql, Ville.class);

    }

}


