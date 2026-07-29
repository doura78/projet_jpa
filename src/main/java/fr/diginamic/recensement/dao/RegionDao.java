package fr.diginamic.recensement.dao;

import fr.diginamic.recensement.entites.Region;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;


import java.util.List;

/**
 * DAO chargé de gérer les opérations d'accès aux données
 * pour l'entité Region.
 */
public class RegionDao {

    /**
     * EntityManager utilisé pour exécuter les opérations JPA.
     */
    private final EntityManager em;

    /**
     * Construit un DAO pour l'entité Region.
     *
     * @param em gestionnaire d'entités
     */
    public RegionDao(EntityManager em) {
        this.em = em;
    }

    /**
     * Recherche une région à partir de son code.
     *
     * @param code code de la région recherché
     * @return la région trouvée, ou null si aucune région ne correspond
     */
    public Region findByCode(String code) {
        TypedQuery<Region> query =  em.createQuery(
                        "SELECT r FROM Region r WHERE r.code = :code", Region.class);
                query.setParameter("code", code);
        List<Region> regions = query.getResultList();
        return regions.isEmpty() ? null : regions.get(0);
    }

    /**
     * Enregistre une région dans la base de données.
     *
     * @param region région à enregistrer
     */
    public Region save(Region region) {
        em.persist(region);
        return region;
    }

    public Region findByNom(String nom) {
        TypedQuery<Region> query =  em.createQuery(
                "SELECT r FROM Region r WHERE r.nom = :nom", Region.class);
                query.setParameter("nom", nom);
        List<Region> regions = query.getResultList();
        return regions.isEmpty() ? null : regions.get(0);
    }
}