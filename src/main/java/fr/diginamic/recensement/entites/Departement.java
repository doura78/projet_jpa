package fr.diginamic.recensement.entites;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Représente un département français rattaché à une région.

 * Un département possède un code, un nom et une région de rattachement.
 * Il peut également contenir plusieurs villes.
 */
@Entity
@Table(name = "departement")
public class Departement {

    /**
     * Identifiant technique unique du département.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Code du département.
     * Ce code est unique dans la table.
     */
    @Column(name = "code", nullable = false, unique = true, length = 10)
    private String code;

    /**
     * Nom du département.
     */
    @Column(name = "nom", nullable = false, length = 100)
    private String nom;

    /**
     * Région à laquelle appartient ce département.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "region_id", nullable = false)
    private Region region;

    /**
     * Liste des villes appartenant à ce département.
     */
    @OneToMany(mappedBy = "departement")
    private List<Ville> villes = new ArrayList<>();

    /**
     * Construit un département vide.
     */
    public Departement() {
    }

    /**
     * Construit un département avec son code, son nom et sa région.
     *
     * @param code code du département
     * @param nom nom du département
     * @param region région de rattachement
     */
    public Departement(String code, String nom, Region region) {
        this.code = code;
        this.nom = nom;
        this.region = region;
    }

    /**
     * Retourne le nom du département.
     *
     * @return nom du département
     */
    public String getNom() {
        return nom;
    }

    /**
     * Modifie le nom du département.
     *
     * @param nom nouveau nom du département
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * Retourne l'identifiant du département.
     *
     * @return identifiant technique
     */
    public Long getId() {
        return id;
    }

    /**
     * Modifie l'identifiant du département.
     *
     * @param id identifiant technique
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Retourne le code du département.
     *
     * @return code du département
     */
    public String getCode() {
        return code;
    }

    /**
     * Modifie le code du département.
     *
     * @param code nouveau code du département
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * Retourne la région de rattachement.
     *
     * @return région du département
     */
    public Region getRegion() {
        return region;
    }

    /**
     * Modifie la région de rattachement.
     *
     * @param region nouvelle région du département
     */
    public void setRegion(Region region) {
        this.region = region;
    }

    /**
     * Retourne la liste des villes du département.
     *
     * @return liste des villes
     */
    public List<Ville> getVilles() {
        return villes;
    }

    /**
     * Modifie la liste des villes du département.
     *
     * @param villes nouvelle liste de villes
     */
    public void setVilles(List<Ville> villes) {
        this.villes = villes;
    }

    /**
     * Retourne une représentation textuelle du département.
     *
     * @return texte décrivant le département
     */
    @Override
    public String toString() {
        return  getNom() + " - " + region;
    }
}