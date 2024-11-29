package fr.diginamic.hello.repositories;

import fr.diginamic.hello.entites.Departement;
import fr.diginamic.hello.entites.Ville;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DepartementRepository extends JpaRepository<Departement, Integer> {

    Departement findByNom(String name);


    Departement findByCode(String name);

    Integer findNbHabitantsByCode(String code);
}
