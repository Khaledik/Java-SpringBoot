package fr.diginamic.hello.services;

import fr.diginamic.hello.entites.Departement;
import fr.diginamic.hello.exceptions.DepartementNotFoundException;
import fr.diginamic.hello.exceptions.InsertUpdateException;
import fr.diginamic.hello.mappers.DepartementMapper;
import fr.diginamic.hello.repositories.DepartementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class DepartementService {

    @Autowired
    private DepartementRepository departementRepository;
    @Autowired
    private DepartementMapper departementMapper;

    private void validateDepartement(Departement departement) throws InsertUpdateException {

        if (departement.getCode().length() < 2 || departement.getCode().length() > 3) {
            throw new InsertUpdateException("Le code département fait au maximum 3 caractères et au minimum 2.");
        } else if (departement.getNom().length() < 3) {
            throw new InsertUpdateException("Le nom du département est obligatoire et comporte au moins 3 lettres.");
        }

        Departement existingDepartement = departementRepository.findByCode(departement.getCode());

        if (existingDepartement != null) {
            throw new InsertUpdateException("Un département avec le code " + departement.getCode() + " existe déjà.");
        }

    }

    @Transactional
    public List<Departement> extractDepartements() {

        List<Departement> departements = departementRepository.findAll();


        return departements;
    }


    @Transactional
    public Departement extractDepartementByID(int id) {
        Optional<Departement> departement = departementRepository.findById(id);

        if (departement.isEmpty()) {
            throw new DepartementNotFoundException("Aucun départements dont l'id est " + id + " n’a été trouvé.");
        } else {
            return departement.get();
        }

    }


    @Transactional
    public Departement extractDepartementByName(String name) {
        Departement departement = departementRepository.findByNom(name);

        if (departement == null) {
            throw new DepartementNotFoundException("Aucun départements dont le nom est " + name + " n’a été trouvé.");
        } else {
            return departement;
        }

    }

    public Departement extractDepartementByCode(String code) throws DepartementNotFoundException {
        Departement departement = departementRepository.findByCode(code);

        if (departement == null) {
            throw new DepartementNotFoundException("Le département avec le code " + code + " n'a pas été trouvé.");
        } else {
            return departement;
        }

    }

    public Integer extractNbHabitantsByDepartement(String code) {
        Integer nbHabitants = departementRepository.findNbHabitantsByCode(code);
        return nbHabitants;
    }


    @Transactional
    public List<Departement> insertDepartement(Departement departement) throws InsertUpdateException {

        validateDepartement(departement);


        departementRepository.save(departement);

        return extractDepartements();
    }

    @Transactional
    public Departement modifierDepartement(String code, Departement departement) throws InsertUpdateException {

        validateDepartement(departement);


        Departement departementAModif = departementRepository.findByCode(code);

        if (departementAModif == null) {
            throw new DepartementNotFoundException("Aucun départements n’a été trouvé.");
        }


        departementAModif.setNom(departement.getNom());
        departementAModif.setCode(departement.getCode());

        departementRepository.save(departementAModif);

        return departementAModif;
    }

    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public Departement supprimerDepartement(int id) {

        Optional<Departement> departement = departementRepository.findById(id);


        if (departement.isPresent()) {
            departementRepository.deleteById(id);
            return departement.get();
        } else {
            throw new DepartementNotFoundException("Aucun départements dont l'id est " + id + " n’a été trouvé.");
        }


    }


    @Transactional
    public byte[] exportToCsv() throws DepartementNotFoundException {

        List<Departement> departementDtos = extractDepartements();

        if (departementDtos.isEmpty()) {
            throw new DepartementNotFoundException("Aucun départements n’a été trouvée.");
        }

        String CSV_HEADER = "CODE;NOM\n";

        StringBuilder csvText = new StringBuilder();
        csvText.append(CSV_HEADER);

        for (Departement departement : departementDtos) {
            csvText.append(departement.getCode() + ";");
            csvText.append(departement.getNom() + "\n");
        }

        return csvText.toString().getBytes();
    }


}
