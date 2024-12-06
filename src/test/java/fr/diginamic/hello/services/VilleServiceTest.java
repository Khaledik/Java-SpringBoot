package fr.diginamic.hello.services;

import fr.diginamic.hello.entites.Departement;
import fr.diginamic.hello.entites.Ville;
import fr.diginamic.hello.exceptions.InsertUpdateException;
import fr.diginamic.hello.exceptions.VilleNotFoundException;
import fr.diginamic.hello.repositories.VilleRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@ActiveProfiles("test")
public class VilleServiceTest {

    @Autowired
    private VilleService villeService;

    @MockitoBean
    private VilleRepository villeRepository;

    @Test
    void extractAllVilles() {
        Iterable<Ville> villes = villeService.extractVilles();
        assertTrue(villes.iterator().hasNext());
    }

    @Test
    void extractVilleById() {
        Ville ville = villeService.extractVilleById(14321);
        assertNotNull(ville);
    }

    @Test
    void extractVilleByIdMockito() {
        Mockito.when(villeRepository.findAll()).thenReturn(List.of());

        try {
            Ville ville = villeService.extractVilleById(14321);
            assertNotNull(ville);
            assertEquals(ville.getNom(), "Nantiat");
        } catch (Exception e) {
            assertThatExceptionOfType(VilleNotFoundException.class);
        }
    }

    @Test
    void extractVilleByName() {
        Ville ville = villeService.extractVilleByName("Paris");
        assertNotNull(ville);
    }

    @Test
    void extractVillesStartWith() {
        Iterable<Ville> villes = villeService.extractVillesStartWith("Do");
        assertTrue(villes.iterator().hasNext());
    }

    @Test
    void extractVillesGreaterThan() {
        Iterable<Ville> villes = villeService.extractVillesGreaterThan(500);
        assertTrue(villes.iterator().hasNext());
    }

    @Test
    void extractVillesBetween() {
        Iterable<Ville> villes = villeService.extractVillesBetween(10000, 100000);
        assertTrue(villes.iterator().hasNext());
    }

    @Test
    void extractVillesByDepartementCode() {
        Iterable<Ville> villes = villeService.extractVillesByDepartementCode("30");
        assertTrue(villes.iterator().hasNext());
    }

    @Test
    void extractVillesByDepartementGreaterThan() {
        Iterable<Ville> villes = villeService.extractVillesByDepartementGreaterThan("30", 500);
        assertTrue(villes.iterator().hasNext());
    }

    @Test
    void extractVillesByDepartementBetween() {
        Iterable<Ville> villes = villeService.extractVillesByDepartementBetween("34", 10000, 100000);
        assertTrue(villes.iterator().hasNext());
    }

    @Test
    void extractTopNVillesByDepartement() {
        Iterable<Ville> villes = villeService.extractTopNVillesByDepartement("34", 3);
        assertTrue(villes.iterator().hasNext());
    }

    @Test
    void isVilleExistInDepartment() {
        Boolean ville = villeService.isVilleExistInDepartment("Montpellier", "34");
        assertTrue(ville);
    }

    @Test
    void villeAlreadyExistInDepartment() {
        Departement departement = new Departement("75", "Paris");
        Ville ville = new Ville("Paris", 10, departement);

        try {
            villeService.insertVille(ville);
        } catch (Exception e) {
            assertThatExceptionOfType(InsertUpdateException.class);
            assertEquals(e.getClass(), "Le nom de la ville doit être unique pour un département donné.");
        }
    }


}
