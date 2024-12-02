package fr.diginamic.hello.mappers;

import fr.diginamic.hello.dtos.VilleDto;
import fr.diginamic.hello.entites.Departement;
import fr.diginamic.hello.entites.Ville;
import org.springframework.stereotype.Component;

@Component
public class VilleMapper {

    public VilleDto toDto(Ville ville) {
        VilleDto dto = new VilleDto();

        dto.setNom(ville.getNom());
        dto.setNbHabitants(ville.getNbHabitants());

        if (ville.getDepartement() != null) {
            dto.setCodeDepartement(ville.getDepartement().getCode());
            dto.setNomDepartement(ville.getDepartement().getNom());
        }


        return dto;

    }

    public Ville toBean(VilleDto dto) {


        Ville ville = new Ville();
        ville.setNom(dto.getNom());
        ville.setNbHabitants(dto.getNbHabitants());

        Departement departement = new Departement();
        departement.setCode(dto.getCodeDepartement());
        departement.setNom(dto.getNomDepartement());

        ville.setDepartement(departement);

        return ville;
    }

}
