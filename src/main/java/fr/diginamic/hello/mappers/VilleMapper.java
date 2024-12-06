package fr.diginamic.hello.mappers;

import fr.diginamic.hello.dtos.VilleDto;
import fr.diginamic.hello.entites.Departement;
import fr.diginamic.hello.entites.Ville;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class VilleMapper {

    public VilleDto toDto(Ville ville) {
        VilleDto dto = new VilleDto();

        dto.setId(ville.getId());
        dto.setNom(ville.getNom());
        dto.setNbHabitants(ville.getNbHabitants());

        if (ville.getDepartement() != null) {
            dto.setCodeDepartement(ville.getDepartement().getCode());
            dto.setNomDepartement(ville.getDepartement().getNom());
        }


        return dto;

    }

    public List<VilleDto> toDto(List<Ville> villes) {

        List<VilleDto> dtos = new ArrayList<VilleDto>();

        for (Ville ville : villes) {
            dtos.add(toDto(ville));
        }

        return dtos;
    }

    public Ville toBean(VilleDto dto) {


        Ville ville = new Ville();
        ville.setId(dto.getId());
        ville.setNom(dto.getNom());
        ville.setNbHabitants(dto.getNbHabitants());

        Departement departement = new Departement();
        departement.setCode(dto.getCodeDepartement());
        departement.setNom(dto.getNomDepartement());

        ville.setDepartement(departement);

        return ville;
    }

}
