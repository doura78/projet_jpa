package fr.diginamic.recensement.entites;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Représente une région française.
 * Une région possède un code, un nom et regroupe plusieurs départements.
 */
@Entity
@Table(name = "region")
public class Region {

    /**
     * Identifiant technique unique de la région.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Code de la région.
     * Ce code est unique dans la table.
     */
    @Column(name = "code", nullable = false, unique = true, length = 10)
    private String code;

    /**
     * Nom de la région.
     */
    @Column(name = "nom", nullable = false, length = 100)
    private String nom;

    /**
     * Liste des départements rattachés à cette région.
     */
    @OneToMany(mappedBy = "region")
    private List<Departement> departements = new ArrayList<>();

    /**
     * Construit une région vide.
     */
    public Region() {
    }

    /**
     * Construit une région avec son code et son nom.
     *
     * @param code code de la région
     * @param nom nom de la région
     */
    public Region(String code, String nom) {
        this.code = code;
        this.nom = nom;
    }

    /**
     * Retourne l'identifiant de la région.
     *
     * @return identifiant technique
     */
    public Long getId() {
        return id;
    }

    /**
     * Modifie l'identifiant de la région.
     *
     * @param id identifiant technique
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Retourne le code de la région.
     *
     * @return code de la région
     */
    public String getCode() {
        return code;
    }

    /**
     * Modifie le code de la région.
     *
     * @param code nouveau code de la région
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * Retourne le nom de la région.
     *
     * @return nom de la région
     */
    public String getNom() {
        return nom;
    }

    /**
     * Modifie le nom de la région.
     *
     * @param nom nouveau nom de la région
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * Retourne la liste des départements de la région.
     *
     * @return liste des départements
     */
    public List<Departement> getDepartements() {
        return departements;
    }

    /**
     * Modifie la liste des départements de la région.
     *
     * @param departements nouvelle liste de départements
     */
    public void setDepartements(List<Departement> departements) {
        this.departements = departements;
    }

    /**
     * Retourne une représentation textuelle de la région.
     *
     * @return texte décrivant la région
     */
    @Override
    public String toString() {
        return nom ;
    }
}