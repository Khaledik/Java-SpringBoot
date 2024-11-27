package fr.diginamic.hello.services;

import fr.diginamic.hello.entites.Departement;
import fr.diginamic.hello.entites.Ville;
import fr.diginamic.hello.repositories.DepartementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DepartementService {

    @Autowired
    private DepartementRepository departementRepository;


    public List<Departement> extractDepartements() {
        return departementRepository.findAll();
    }


    public Optional<Departement> extractDepartementByID(int id) {
        return departementRepository.findById(id);
    }


    public Departement extractDepartementByName(String name) {
        return departementRepository.findByNom(name);
    }

    public Departement extractDepartementByCode(String code) {
        return departementRepository.findByCode(code);
    }


    public Departement insertDepartement(Departement departement) {
        if (departement != null) {
            departementRepository.save(departement);
        }
        return departement;
    }

    public Departement modifierDepartement(Departement departement) {

        Departement departementAModif = departementRepository.findByCode(departement.getCode());

        departementAModif.setNom(departement.getNom());
        departementAModif.setCode(departement.getCode());

        return departementRepository.save(departementAModif);
    }

    public Optional<Departement> supprimerDepartement(int id) {

        Optional<Departement> departement = departementRepository.findById(id);

        departementRepository.deleteById(id);

        return departement;
    }

}
