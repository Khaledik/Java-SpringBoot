package fr.diginamic.hello.services;

import fr.diginamic.hello.entites.Departement;
import fr.diginamic.hello.entites.Ville;
import fr.diginamic.hello.repositories.DepartementRepository;
import fr.diginamic.hello.repositories.VilleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VilleService {

    @Autowired
    private VilleRepository villeRepository;

    @Autowired
    private DepartementRepository departementRepository;


    public List<Ville> extractVilles() {
        return villeRepository.findAll();
    }

    public Optional<Ville> extractVilleById(int id) {
        return villeRepository.findById(id);
    }

    public Ville extractVilleByName(String name) {
        return villeRepository.findByNom(name);
    }

    public List<Ville> extractVillesStartWith(String nom) {
        return villeRepository.findAllByNomStartingWith(nom);
    }

    public List<Ville> extractVillesGreaterThan(int min) {
        return villeRepository.findAllByNbHabitantsGreaterThan(min);
    }

    public List<Ville> extractVillesBetween(int min, int max) {
        return villeRepository.findAllByNbHabitantsBetween(min, max);
    }

    public List<Ville> extractVillesByDepartementGreaterThan(int id, int min) {
        return villeRepository.findAllByDepartementIdAndNbHabitantsGreaterThan(id, min);
    }

    public List<Ville> extractVillesByDepartementBetween(int id, int min, int max) {
        return villeRepository.findAllByDepartementIdAndNbHabitantsBetween(id, min, max);
    }

    public List<Ville> extractTopNVillesByDepartement(int id, int n) {
        Pageable pageable = PageRequest.of(0, n);
        return villeRepository.findTopNVillesByDepartementId(id, pageable);
    }

    public Ville insertVille(Ville ville) {
        if (ville.getDepartement() != null) {
            departementRepository.save(ville.getDepartement());
        }
        return villeRepository.save(ville);
    }

    public Ville modifierVille(int id, Ville ville) {
        Ville villeAModif = villeRepository.findById(id).get();
        villeAModif.setNom(ville.getNom());
        villeAModif.setNbHabitants(ville.getNbHabitants());

        villeRepository.save(villeAModif);

        return villeAModif;
    }

    public Optional<Ville> supprimerVille(int id) {
        Optional<Ville> ville = villeRepository.findById(id);

        villeRepository.deleteById(id);

        return ville;
    }


}
