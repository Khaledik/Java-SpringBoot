package fr.diginamic.hello.restcontroleurs;

import fr.diginamic.hello.entites.Departement;
import fr.diginamic.hello.repositories.DepartementRepository;
import fr.diginamic.hello.services.DepartementService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/departements")
public class DepartementControleur {


    @Autowired
    private DepartementService departementService;

    //  MÉTHODE GET POUR OBTENIR TOUT LES DEPARTEMENT
    @GetMapping
    public List<Departement> getAllDepartements() {
        return departementService.extractDepartements();
    }

    //  MÉTHODE GET POUR OBTENIR UN DEPARTEMENT PAR SON ID
    @GetMapping(path = "/id/{id}")
    public ResponseEntity<Departement> getDepartementById(@PathVariable int id) {

        Optional<Departement> departement = departementService.extractDepartementByID(id);

        if (departement.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(departement.get());
    }

    //  MÉTHODE GET POUR OBTENIR UN DEPARTEMENT PAR SON NOM
    @GetMapping(path = "/name/{name}")
    public ResponseEntity<Departement> getDepartementByName(@PathVariable String name) {

        Departement departement = departementService.extractDepartementByName(name);

        if (departement == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(departement);
    }

    //  MÉTHODE GET POUR OBTENIR UN DEPARTEMENT PAR SON CODE
    @GetMapping(path = "/code/{code}")
    public ResponseEntity<Object> getDepartementByCode(@PathVariable String code) {

        Departement departement = departementService.extractDepartementByCode(code);

        if (departement == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(departement);
    }


    //  MÉTHODE POST POUR AJOUTER UN DÉPARTEMENT
    @PostMapping(path = "/add")
    public ResponseEntity<String> addDepartement(@Valid @RequestBody Departement departement, BindingResult bindingResult) {

        if (departement == null) {
            return ResponseEntity.badRequest().build();
        }

        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors().get(0).getDefaultMessage());
        }

        departementService.insertDepartement(departement);
        return ResponseEntity.ok("Département insérée avec succès : " + departement.getNom());

    }

    //  MÉTHODE PUT POUR EDITER UN DEPARTEMENT
    @PutMapping(path = "/edit")
    public ResponseEntity<String> updateDepartement(@Valid @RequestBody Departement departement, BindingResult bindingResult) {

        if (departement == null) {
            return ResponseEntity.badRequest().build();
        }

        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors().get(0).getDefaultMessage());
        }

        Departement departementAModif = departementService.modifierDepartement(departement);

        if (departementAModif == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok("Département mise à jour avec succès : " + departementAModif.getNom());

    }

    //  MÉTHODE DELETE POUR SUPPRIMER UN DEPARTEMENT
    @DeleteMapping(path = "/remove/{id}")
    public ResponseEntity<String> deleteVille(@PathVariable int id) {

        Optional<Departement> departement = departementService.supprimerDepartement(id);

        if (departement == null) {
            return ResponseEntity.notFound().build();
        }


        return ResponseEntity.ok("Département supprimée avec succès : " + departement.get().getNom());
    }


}
