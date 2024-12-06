package fr.diginamic.hello.repositories;

import fr.diginamic.hello.entites.Departement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DepartementRepository extends JpaRepository<Departement, Integer> {

    Departement findByNom(String name);


    Departement findByCode(String name);

    Integer findNbHabitantsByCode(String code);
}
