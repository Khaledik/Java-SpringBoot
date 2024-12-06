package fr.diginamic.hello.mappers;

import fr.diginamic.hello.dtos.DepartementDto;
import fr.diginamic.hello.entites.Departement;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DepartementMapper {

    public DepartementDto toDto(Departement departement) {

        if (departement == null) {
            throw new IllegalArgumentException("Le département ne peut pas être null.");
        }

        DepartementDto dto = new DepartementDto();
        dto.setId(departement.getId());
        dto.setNom(departement.getNom());
        dto.setCode(departement.getCode());

        if (departement.getNbHabitants() != null) {
            dto.setNbHabitants(departement.getNbHabitants());
        }

        return dto;
    }


    public List<DepartementDto> toDto(List<Departement> departements) {

        List<DepartementDto> departementsDto = new ArrayList<>();

        for (Departement departement : departements) {
            departementsDto.add(toDto(departement));
        }

        return departementsDto;
    }


    public Departement toBean(DepartementDto departementDto) {

        Departement departement = new Departement();
        departement.setId(departementDto.getId());
        departement.setNom(departementDto.getNom());
        departement.setCode(departementDto.getCode());
        departement.setNbHabitants(departementDto.getNbHabitants());


        return departement;
    }
}
