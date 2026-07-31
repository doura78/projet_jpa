package fr.diginamic.recensement.service;

import fr.diginamic.recensement.dao.DepartementDao;
import fr.diginamic.recensement.dao.RegionDao;
import fr.diginamic.recensement.dao.VilleDao;
import fr.diginamic.recensement.entites.Departement;
import fr.diginamic.recensement.entites.Region;
import fr.diginamic.recensement.entites.Ville;
import fr.diginamic.recensement.util.JpaUtil;
import jakarta.persistence.EntityManager;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

/**
 * Importe les données du recensement en base de données.
 * Cette classe lit le fichier Communes.csv.
 * Elle utilise aussi le fichier Departements.csv pour récupérer le nom des départements.
 */
public class IntegrationRecensement {

    /**
     * Lance l'import des régions, départements et villes.
     *
     * @param args arguments de la ligne de commande
     */
    public static void main(String[] args) {

        EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();

        RegionDao regionDao = new RegionDao(em);
        DepartementDao departementDao = new DepartementDao(em);
        VilleDao villeDao = new VilleDao(em);

        try {
            Map<String, String> mapDepartements = chargerDepartements("src/main/resources/Departements.csv");

            try (BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/Communes.csv"))) {

                em.getTransaction().begin();

                String ligne = reader.readLine();

                while ((ligne = reader.readLine()) != null) {

                    String[] tokens = ligne.split(";");

                    if (tokens.length < 10) {
                        continue;
                    }

                    String codeRegion = tokens[0].trim();
                    String nomRegion = tokens[1].trim();
                    String codeDepartement = tokens[2].trim();
                    String codeCommune = tokens[5].trim();
                    String nomCommune = tokens[6].trim();

                    Long populationMunicipale = parseLong(tokens[7]);
                    Long populationCompteeApart = parseLong(tokens[8]);
                    Long populationTotale = parseLong(tokens[9]);

                    Region region = regionDao.findByCode(codeRegion);
                    if (region == null) {
                        region = new Region(codeRegion, nomRegion);
                        regionDao.save(region);
                        System.out.println("Nouvelle région : " + region.getCode() + " - " + region.getNom());
                    } else if (region.getNom() == null || region.getNom().isBlank()) {
                        region.setNom(nomRegion);
                        regionDao.save(region);
                        System.out.println("Région mise à jour : " + region.getCode() + " - " + region.getNom());
                    }

                    String nomDepartement = mapDepartements.getOrDefault(codeDepartement, codeDepartement);

                    Departement departement = departementDao.findByCode(codeDepartement);
                    if (departement == null) {
                        departement = new Departement();
                        departement.setCode(codeDepartement);
                        departement.setNom(nomDepartement);
                        departement.setRegion(region);
                        departementDao.save(departement);
                        System.out.println("Nouveau département : " + departement.getCode() + " - " + departement.getNom());
                    } else {
                        if (departement.getNom() == null || departement.getNom().isBlank() || departement.getNom().equals(departement.getCode())) {
                            departement.setNom(nomDepartement);
                        }
                        if (departement.getRegion() == null) {
                            departement.setRegion(region);
                        }
                        departementDao.save(departement);
                        System.out.println("Département mis à jour : " + departement.getCode() + " - " + departement.getNom());
                    }

                    Ville villeExistante = villeDao.findByCodeCommune(codeCommune);
                    if (villeExistante == null) {
                        Ville ville = new Ville();
                        ville.setNom(nomCommune);
                        ville.setPopulation(populationTotale);
                        ville.setPopulationMunicipale(populationMunicipale);
                        ville.setPopulationCompteeApart(populationCompteeApart);
                        ville.setPopulationTotale(populationTotale);
                        ville.setCodeCommune(codeCommune);
                        ville.setDepartement(departement);
                        villeDao.save(ville);
                        System.out.println("Nouvelle ville : " + ville.getNom() + " / " + ville.getCodeCommune());
                    } else {
                        villeExistante.setNom(nomCommune);
                        villeExistante.setPopulation(populationTotale);
                        villeExistante.setPopulationMunicipale(populationMunicipale);
                        villeExistante.setPopulationCompteeApart(populationCompteeApart);
                        villeExistante.setPopulationTotale(populationTotale);
                        villeExistante.setCodeCommune(codeCommune);
                        villeExistante.setDepartement(departement);
                        villeDao.save(villeExistante);
                        System.out.println("Ville mise à jour : " + villeExistante.getNom() + " / " + villeExistante.getCodeCommune());
                    }

                }

                em.getTransaction().commit();
                System.out.println("Import terminé avec succès.");
            }

        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    /**
     * Charge les départements depuis un fichier CSV.
     * Associe chaque code département à son nom.
     *
     * @param cheminFichier chemin du fichier Departements.csv
     * @return map contenant le code du département et son nom
     * @throws Exception erreur de lecture du fichier
     */
    private static Map<String, String> chargerDepartements(String cheminFichier) throws Exception {
        Map<String, String> map = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(cheminFichier))) {
            String ligne = reader.readLine();

            while ((ligne = reader.readLine()) != null) {
                String[] tokens = ligne.split(";");

                if (tokens.length < 2) {
                    continue;
                }

                String codeDepartement = tokens[0].trim();
                String nomDepartement = tokens[1].trim();

                map.put(codeDepartement, nomDepartement);
            }
        }

        return map;
    }

    /**
     * Convertit une chaîne de caractères en Long.
     * Supprime les espaces présents dans les nombres.
     *
     * @param valeur valeur à convertir
     * @return valeur convertie en Long
     * @throws NumberFormatException si la valeur n'est pas un nombre valide
     */
    private static Long parseLong(String valeur) {
        return Long.parseLong(valeur.replace(" ", "").trim());
    }
}