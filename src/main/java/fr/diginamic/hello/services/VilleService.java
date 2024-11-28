package fr.diginamic.hello.services;

import fr.diginamic.hello.dtos.VilleDto;
import fr.diginamic.hello.entites.Ville;
import fr.diginamic.hello.mappers.VilleMapper;
import fr.diginamic.hello.repositories.DepartementRepository;
import fr.diginamic.hello.repositories.VilleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class VilleService {

    @Autowired
    private VilleRepository villeRepository;

    @Autowired
    private DepartementRepository departementRepository;

    @Autowired
    private VilleMapper villeMapper;


    @Transactional
    public List<VilleDto> extractVilles() {
        List<Ville> villes = villeRepository.findAll();
        List<VilleDto> villesDto = new ArrayList<>();

        for (Ville ville : villes) {
            villesDto.add(villeMapper.toDto(ville));
        }

        return villesDto;
    }

    public VilleDto extractVilleById(int id) {
        Optional<Ville> ville = villeRepository.findById(id);

        if (ville.isPresent()) {
            VilleDto villeDto = villeMapper.toDto(ville.get());
            return villeDto;
        } else {
            return null;
        }

    }

    @Transactional
    public VilleDto extractVilleByName(String name) {
        Ville ville = villeRepository.findByNom(name);
        return villeMapper.toDto(ville);
    }

    @Transactional
    public List<VilleDto> extractVillesStartWith(String nom) {

        List<Ville> villes = villeRepository.findAllByNomStartingWith(nom);
        List<VilleDto> villesDto = new ArrayList<>();

        for (Ville ville : villes) {
            villesDto.add(villeMapper.toDto(ville));
        }

        return villesDto;
    }

    @Transactional
    public List<VilleDto> extractVillesGreaterThan(int min) {

        List<Ville> villes = villeRepository.findAllByNbHabitantsGreaterThan(min);
        List<VilleDto> villesDto = new ArrayList<>();

        for (Ville ville : villes) {
            villesDto.add(villeMapper.toDto(ville));
        }

        return villesDto;
    }

    @Transactional
    public List<VilleDto> extractVillesBetween(int min, int max) {

        List<Ville> villes = villeRepository.findAllByNbHabitantsBetween(min, max);
        List<VilleDto> villesDto = new ArrayList<>();

        for (Ville ville : villes) {
            villesDto.add(villeMapper.toDto(ville));
        }

        return villesDto;
    }

    @Transactional
    public List<VilleDto> extractVillesByDepartementGreaterThan(String codeDep, int min) {

        List<Ville> villes = villeRepository.findAllByDepartementIdAndNbHabitantsGreaterThan(codeDep, min);
        List<VilleDto> villesDto = new ArrayList<>();

        for (Ville ville : villes) {
            villesDto.add(villeMapper.toDto(ville));
        }

        return villesDto;

    }

    @Transactional
    public List<VilleDto> extractVillesByDepartementBetween(String codeDep, int min, int max) {

        List<Ville> villes = villeRepository.findAllByDepartementCodeAndNbHabitantsBetween(codeDep, min, max);
        List<VilleDto> villesDto = new ArrayList<>();

        for (Ville ville : villes) {
            villesDto.add(villeMapper.toDto(ville));
        }

        return villesDto;
    }

    @Transactional
    public List<VilleDto> extractTopNVillesByDepartement(String codeDep, int n) {

        Pageable pageable = PageRequest.of(0, n);
        List<Ville> villes = villeRepository.findTopNVillesByDepartementId(codeDep, pageable);
        List<VilleDto> villesDto = new ArrayList<>();

        for (Ville ville : villes) {
            villesDto.add(villeMapper.toDto(ville));
        }

        return villesDto;
    }

    @Transactional
    public VilleDto insertVille(VilleDto villeDto) {

        Ville ville = villeMapper.toBean(villeDto);

        if (ville.getDepartement() != null) {
            departementRepository.save(ville.getDepartement());
        }

        Ville villeToInsert = villeRepository.save(ville);

        return villeDto;
    }

    @Transactional
    public VilleDto modifierVille(int id, VilleDto villeDto) {

        Ville ville = villeMapper.toBean(villeDto);

        Ville villeAModif = villeRepository.findById(id).get();

        villeAModif.setNom(ville.getNom());
        villeAModif.setNbHabitants(ville.getNbHabitants());

        villeRepository.save(villeAModif);

        return villeDto;
    }

    @Transactional
    public VilleDto supprimerVille(int id) {

        Optional<Ville> ville = villeRepository.findById(id);

        VilleDto villeDto;

        if (ville.isPresent()) {
            villeRepository.deleteById(id);
            villeDto = villeMapper.toDto(ville.get());
        } else {
            return null;
        }


        return villeDto;
    }


}
