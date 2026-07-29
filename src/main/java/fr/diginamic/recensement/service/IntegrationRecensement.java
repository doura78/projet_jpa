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


import static java.lang.Long.parseLong;

/**
 * Classe exécutable qui importe le fichier recensement.csv en base.
 */
public class IntegrationRecensement {

    public static void main(String[] args) {

        EntityManager em = JpaUtil.getEntityManagerFactory().createEntityManager();

        RegionDao regionDao = new RegionDao(em);
        DepartementDao departementDao = new DepartementDao(em);
        VilleDao villeDao = new VilleDao(em);

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
                } else if (region.getNom() == null || region.getNom().isBlank()) {
                    region.setNom(nomRegion);
                    regionDao.save(region);
                }

                Departement departement = departementDao.findByCode(codeDepartement);
                if (departement == null) {
                    departement = new Departement();
                    departement.setCode(codeDepartement);
                    departement.setNom(codeDepartement);
                    departement.setRegion(region);
                    departementDao.save(departement);
                } else if (departement.getRegion() == null) {
                    departement.setRegion(region);
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
                } else {
                    villeExistante.setNom(nomCommune);
                    villeExistante.setPopulation(populationTotale);
                    villeExistante.setPopulationMunicipale(populationMunicipale);
                    villeExistante.setPopulationCompteeApart(populationCompteeApart);
                    villeExistante.setPopulationTotale(populationTotale);
                    villeExistante.setCodeCommune(codeCommune);
                    villeExistante.setDepartement(departement);
                }
            }

            em.getTransaction().commit();
            System.out.println("Import terminé avec succès.");

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
     * Convertit une valeur texte du CSV en entier.
     * Les nombres contiennent parfois des espaces, ex: 14 518.
     *
     * @param valeur valeur texte du CSV
     * @return entier parsé
     */
    private static Long parseLong(String valeur) {
        return Long.parseLong(valeur.replace(" ", "").trim());
    }
}