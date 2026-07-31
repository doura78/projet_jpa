package fr.diginamic.recensement.dao;


import fr.diginamic.recensement.entites.Ville;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;

/**
 * DAO chargé de gérer les opérations d'accès aux données
 * pour l'entité Ville.
 */
public class VilleDao {

    /**
     * EntityManager utilisé pour exécuter les opérations JPA.
     */
    private final EntityManager em;

    /**
     * Construit un DAO pour l'entité Ville.
     *
     * @param em gestionnaire d'entités
     */
    public VilleDao(EntityManager em) {
        this.em = em;
    }

    /**
     * Recherche une ville par nom et code commune.
     * Cela permet de rejouer l'import sans doublon.
     * @param codeCommune code commune
     * @return ville trouvée ou null
     */
    public Ville findByCodeCommune(String codeCommune) {
        TypedQuery<Ville> query = em.createQuery(
                "SELECT v FROM Ville v WHERE v.codeCommune = :codeCommune", Ville.class);
        query.setParameter("codeCommune", codeCommune);

        List<Ville> resultats = query.getResultList();
        return resultats.isEmpty() ? null : resultats.get(0);
    }

    public Ville findByNom(String nomCommune) {
        List<Ville> villes = em.createQuery(
                        "SELECT v FROM Ville v WHERE v.nom = :nom", Ville.class)
                .setParameter("nomCommune", nomCommune)
                .getResultList();
        return villes.isEmpty() ? null : villes.get(0);
    }

    /**
     * Enregistre une ville dans la base de données.
     *
     * @param ville ville à enregistrer
     */
    public Ville save(Ville ville) {
        em.persist(ville);
        return ville;
    }

    public List<Ville> findAllOrderByPopulationDesc() {
        TypedQuery<Ville> query = em.createQuery(
                "SELECT v FROM Ville v ORDER BY v.populationTotale DESC", Ville.class);
        return query.getResultList();
    }

    public List<Ville> findByDepartementOrderByPopulationDesc(String codeDepartement) {
        TypedQuery<Ville> query = em.createQuery(
                "SELECT v FROM Ville v WHERE v.departement.code = :code ORDER BY v.populationTotale DESC",
                Ville.class);
        query.setParameter("code", codeDepartement);
        return query.getResultList();
    }

    public List<Ville> findByRegionOrderByPopulationDesc(String nomRegion) {
        TypedQuery<Ville> query = em.createQuery(
                "SELECT v FROM Ville v WHERE v.departement.region.nom = :nomRegion ORDER BY v.populationTotale DESC", Ville.class);
        query.setParameter("nomRegion", nomRegion);
        return query.getResultList();
    }

    public List<Ville> getPopulationByDepartement(String codeDepartement) {
        TypedQuery<Ville> query = em.createQuery(
                "SELECT SUM(v.populationTotale) FROM Ville v WHERE v.departement.code = :code GROUP BY v.departement.code",
                Ville.class);
        query.setParameter("code", codeDepartement);
        return query.getResultList();
    }

    public List<Ville> getPopulationByRegion(String nomRegion) {
        TypedQuery<Ville> query = em.createQuery(
                "SELECT SUM(v.populationTotale) FROM Ville v WHERE v.departement.region.nom = :code GROUP BY v.departement.region.nom", Ville.class);
        query.setParameter("nom", nomRegion);
        return query.getResultList();
    }


}
