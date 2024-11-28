package fr.diginamic.hello.services;

import fr.diginamic.hello.dtos.DepartementDto;
import fr.diginamic.hello.entites.Departement;
import fr.diginamic.hello.entites.Ville;
import fr.diginamic.hello.mappers.DepartementMapper;
import fr.diginamic.hello.repositories.DepartementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DepartementService {

    @Autowired
    private DepartementRepository departementRepository;
    @Autowired
    private DepartementMapper departementMapper;


    public List<DepartementDto> extractDepartements() {

        List<Departement> departements = departementRepository.findAll();
        List<DepartementDto> departementDto = new ArrayList<>();

        for (Departement departement : departements) {
            departementDto.add(departementMapper.toDto(departement));
        }


        return departementDto;
    }


    public DepartementDto extractDepartementByID(int id) {

        Optional<Departement> departement = departementRepository.findById(id);

        if (departement.isPresent()) {
            DepartementDto departementDto = departementMapper.toDto(departement.get());
            return departementDto;
        } else {
            return null;
        }

    }


    public DepartementDto extractDepartementByName(String name) {
        Departement departement = departementRepository.findByNom(name);
        return departementMapper.toDto(departement);
    }

    public DepartementDto extractDepartementByCode(String code) {
        Departement departement = departementRepository.findByCode(code);
        return departementMapper.toDto(departement);
    }


    public DepartementDto insertDepartement(DepartementDto departementDto) {

        Departement departement = departementMapper.toBean(departementDto);

        if (departement != null) {
            departementRepository.save(departement);
        }

        return departementDto;
    }

    public DepartementDto modifierDepartement(DepartementDto departementDto) {

        Departement departement = departementMapper.toBean(departementDto);

        Departement departementAModif = departementRepository.findByCode(departement.getCode());

        departementAModif.setNom(departement.getNom());
        departementAModif.setCode(departement.getCode());

        departementRepository.save(departementAModif);

        return departementDto;
    }

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
