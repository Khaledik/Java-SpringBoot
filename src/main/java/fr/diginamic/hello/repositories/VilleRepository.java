package fr.diginamic.hello.repositories;

import fr.diginamic.hello.entites.Ville;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface VilleRepository extends JpaRepository<Ville, Integer> {

    // Recherche d'une ville par son nom
    Ville findByNom(String nom);

    // Recherche de toutes les villes dont le nom commence par
    List<Ville> findAllByNomStartingWith(String nom);

    // Recherche de toutes les villes avec une population supérieure à un min
    List<Ville> findAllByNbHabitantsGreaterThan(int min);

    // Recherche de toutes les villes avec une population entre deux valeurs
    List<Ville> findAllByNbHabitantsBetween(int min, int max);

    // Recherche de toutes les villes d’un département avec une population supérieure à min
    @Query("SELECT v FROM Ville v WHERE v.departement.code = :codeDep AND v.nbHabitants > :min")
    List<Ville> findAllByDepartementIdAndNbHabitantsGreaterThan(@Param("codeDep") String codeDep, @Param("min") int min);

    // Recherche de toutes les villes d’un département avec une population entre deux valeurs
    List<Ville> findAllByDepartementCodeAndNbHabitantsBetween(String codeDep, int min, int max);

    // Recherche des n villes les plus peuplées d’un département
    @Query("SELECT v FROM Ville v WHERE v.departement.code = :codeDep ORDER BY v.nbHabitants DESC")
    List<Ville> findTopNVillesByDepartementId(@Param("codeDep") String codeDep, Pageable pageable);

    Ville findByNomAndDepartementCode(String nom, String codeDep);

}
