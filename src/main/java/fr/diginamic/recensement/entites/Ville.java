package fr.diginamic.recensement.entites;

import jakarta.persistence.*;

/**
 * Représente une ville issue du fichier de recensement.
 * <p>
 * Une ville possède un nom, une population, un code commune et appartient
 * à un département.
 * </p>
 */
@Entity
@Table(name = "ville")
public class Ville {

    /**
     * Identifiant technique unique de la ville.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Nom de la ville.
     */
    @Column(name = "nom", nullable = false, length = 150)
    private String nom;

    /**
     * Population totale de la ville.
     */
    @Column(name = "population", nullable = false)
    private Long population;

    /**
     * Population municipale.
     */
    @Column(name = "populationMunicipale", nullable = false)
    private Long populationMunicipale;

    /**
     * Population comptée à part de la ville.
     */
    @Column(name = "populationCompteeApart", nullable = false)
    private Long populationCompteeApart;

    /**
     * Population totale de la ville.
     */
    @Column(name = "populationTotale", nullable = false)
    private Long populationTotale;

    /**
     * Code commune de la ville.
     * Ce code est unique dans la table.
     */
    @Column(name = "code_commune", nullable = false, unique = true, length = 10)
    private String codeCommune;

    /**
     * Département auquel appartient la ville.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "departement_id", nullable = false)
    private Departement departement;

    /**
     * Construit une ville vide.
     */
    public Ville() {
    }

    /**
     * Construit une ville avec son nom, sa population, son code commune et son département.
     *
     * @param nom nom de la ville
     * @param population population totale
     * @param codeCommune code commune de la ville
     * @param departement département de rattachement
     */
    public Ville(String nom, Long population, Long populationMunicipale, Long populationCompteeApart, Long populationTotale, String codeCommune, Departement departement) {
        this.nom = nom;
        this.population = population;
        this.populationMunicipale = populationMunicipale;
        this.populationCompteeApart = populationCompteeApart;
        this.populationTotale = populationTotale;
        this.codeCommune = codeCommune;
        this.departement = departement;
    }

    /**
     * Retourne l'identifiant de la ville.
     *
     * @return identifiant technique
     */
    public Long getId() {
        return id;
    }

    /**
     * Modifie l'identifiant de la ville.
     *
     * @param id identifiant technique
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Retourne le nom de la ville.
     *
     * @return nom de la ville
     */
    public String getNom() {
        return nom;
    }

    /**
     * Modifie le nom de la ville.
     *
     * @param nom nouveau nom de la ville
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * Retourne la populationMunicipale de la ville.
     *
     * @return populationMunicipale totale
     */
    public Long getPopulationMunicipale() {
        return populationMunicipale;
    }

    /**
     * Modifie la populationMunicipale de la ville.
     *
     * @param populationMunicipale nouvelle population
     */
    public void setPopulationMunicipale(Long populationMunicipale) {
        this.populationMunicipale = populationMunicipale;
    }
    /**
     * Retourne la populationCompteeAPart de la ville.
     *
     * @return populationCompteeAPart totale
     */
    public Long getPopulationCompteeApart() {
        return populationCompteeApart;
    }

    /**
     * Modifie la populationCompteeAPart de la ville.
     *
     * @param populationCompteeApart nouvelle population
     */
    public void setPopulationCompteeApart(Long populationCompteeApart) {
        this.populationCompteeApart = populationCompteeApart;
    }

    /**
     * Retourne la populationTotale de la ville.
     *
     * @return populationTotale totale
     */
    public Long getPopulationTotale() {
        return populationTotale;
    }

    /**
     * Modifie la populationTotale de la ville.
     *
     * @param populationTotale nouvelle population
     */
    public void setPopulationTotale(Long populationTotale) {
        this.populationTotale = populationTotale;
    }

    /**
     * Retourne la population de la ville.
     *
     * @return population totale
     */
    public Long getPopulation() {
        return population;
    }

    /**
     * Modifie la population de la ville.
     *
     * @param population nouvelle population
     */
    public void setPopulation(Long population) {
        this.population = population;
    }

    /**
     * Retourne le code commune de la ville.
     *
     * @return code commune
     */
    public String getCodeCommune() {
        return codeCommune;
    }

    /**
     * Modifie le code commune de la ville.
     *
     * @param codeCommune nouveau code commune
     */
    public void setCodeCommune(String codeCommune) {
        this.codeCommune = codeCommune;
    }

    /**
     * Retourne le département de rattachement.
     *
     * @return département
     */
    public Departement getDepartement() {
        return departement;
    }

    /**
     * Modifie le département de rattachement.
     *
     * @param departement nouveau département
     */
    public void setDepartement(Departement departement) {
        this.departement = departement;
    }

    /**
     * Retourne une représentation textuelle de la ville.
     *
     * @return texte décrivant la ville
     */
    @Override
    public String toString() {
        return "Ville{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", population=" + population +
                ", populationMunicipale=" + populationMunicipale +
                ", populationCompteeApart=" + populationCompteeApart +
                ", populationTotale=" + populationTotale +
                ", codeCommune='" + codeCommune + '\'' +
                ", departement=" + departement +
                '}';
    }
}