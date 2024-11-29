package fr.diginamic.hello.services;

import fr.diginamic.hello.dtos.DepartementDto;
import fr.diginamic.hello.entites.Departement;
import fr.diginamic.hello.exceptions.DepartementNotFoundException;
import fr.diginamic.hello.exceptions.InsertUpdateException;
import fr.diginamic.hello.mappers.DepartementMapper;
import fr.diginamic.hello.repositories.DepartementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DepartementService {

    @Autowired
    private DepartementRepository departementRepository;
    @Autowired
    private DepartementMapper departementMapper;

    private void validateDepartement(DepartementDto departementDto) throws InsertUpdateException {

        if (departementDto.getCode().length() < 2 || departementDto.getCode().length() > 3) {
            throw new InsertUpdateException("Le code département fait au maximum 3 caractères et au minimum 2.");
        } else if (departementDto.getNom().length() < 3) {
            throw new InsertUpdateException("Le nom du département est obligatoire et comporte au moins 3 lettres.");
        }

        Departement existingDepartement = departementRepository.findByCode(departementDto.getCode());

        if (existingDepartement != null) {
            throw new InsertUpdateException("Un département avec le code " + departementDto.getCode() + " existe déjà.");
        }

    }

    @Transactional
    public List<DepartementDto> extractDepartements() {

        List<Departement> departements = departementRepository.findAll();
        List<DepartementDto> departementDto = new ArrayList<>();

        for (Departement departement : departements) {
            departementDto.add(departementMapper.toDto(departement));
        }


        return departementDto;
    }


    @Transactional
    public DepartementDto extractDepartementByID(int id) {

        Optional<Departement> departement = departementRepository.findById(id);

        if (departement.isPresent()) {
            DepartementDto departementDto = departementMapper.toDto(departement.get());
            return departementDto;
        } else {
            return null;
        }

    }


    @Transactional
    public DepartementDto extractDepartementByName(String name) {
        Departement departement = departementRepository.findByNom(name);
        return departementMapper.toDto(departement);
    }

    public DepartementDto extractDepartementByCode(String code) throws DepartementNotFoundException {
        Departement departement = departementRepository.findByCode(code);

        if (departement == null) {
            throw new DepartementNotFoundException("Le département avec le code " + code + " n'a pas été trouvé.");
        }

        return departementMapper.toDto(departement);
    }

    public Integer extractNbHabitantsByDepartement(String code) {
        Integer nbHabitants = departementRepository.findNbHabitantsByCode(code);
        return nbHabitants;
    }


    @Transactional
    public DepartementDto insertDepartement(DepartementDto departementDto) throws InsertUpdateException {

        validateDepartement(departementDto);

        Departement departement = departementMapper.toBean(departementDto);

        departementRepository.save(departement);

        return departementDto;
    }

    @Transactional
    public DepartementDto modifierDepartement(DepartementDto departementDto) throws InsertUpdateException {

        validateDepartement(departementDto);

        Departement departement = departementMapper.toBean(departementDto);

        Departement departementAModif = departementRepository.findByCode(departement.getCode());

        departementAModif.setNom(departement.getNom());
        departementAModif.setCode(departement.getCode());

        departementRepository.save(departementAModif);

        return departementDto;
    }

    @Transactional
    public DepartementDto supprimerDepartement(int id) {

        Optional<Departement> departement = departementRepository.findById(id);

        DepartementDto departementDto;

        if (departement.isPresent()) {
            departementRepository.deleteById(id);
            departementDto = departementMapper.toDto(departement.get());
        } else {
            return null;
        }


        return departementDto;
    }

}
