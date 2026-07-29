package fr.diginamic.recensement.dao;

import fr.diginamic.recensement.entites.Departement;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;

/**
 * DAO chargé de gérer les opérations d'accès aux données
 * pour l'entité Departement.
 */
public class DepartementDao {

    /**
     * EntityManager utilisé pour exécuter les opérations JPA.
     */
    private final EntityManager em;

    /**
     * Construit un DAO pour l'entité Departement.
     *
     * @param em gestionnaire d'entités
     */
    public DepartementDao(EntityManager em) {
        this.em = em;
    }

    /**
     * Recherche un département à partir de son code.
     *
     * @param code code du département recherché
     * @return le département trouvée, ou null si aucun département ne correspond
     */

    public Departement findByCode(String code) {
        TypedQuery<Departement> query = em.createQuery(
                "SELECT d FROM Departement d WHERE d.code = :code", Departement.class);
        query.setParameter("code", code);
        List<Departement> departements = query.getResultList();
        return departements.isEmpty() ? null : departements.get(0);
    }

    /**
     * Enregistre un département dans la base de données.
     *
     * @param departement departement à enregistrer
     */
    public Departement save(Departement departement) {
        em.persist(departement);
        return departement;
    }

    public Departement findByNom(String nom) {
        TypedQuery<Departement> query = em.createQuery(
                "SELECT d FROM Departement d WHERE d.nom = :nom", Departement.class);
        query.setParameter("nom", nom);
        List<Departement> departements = query.getResultList();
        return departements.isEmpty() ? null : departements.get(0);
    }

}
