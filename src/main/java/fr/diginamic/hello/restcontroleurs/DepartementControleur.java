package fr.diginamic.hello.restcontroleurs;

import fr.diginamic.hello.dtos.DepartementDto;
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
    public List<DepartementDto> getAllDepartements() {
        return departementService.extractDepartements();
    }

    //  MÉTHODE GET POUR OBTENIR UN DEPARTEMENT PAR SON ID
    @GetMapping(path = "/id/{id}")
    public ResponseEntity<DepartementDto> getDepartementById(@PathVariable int id) {

        DepartementDto departement = departementService.extractDepartementByID(id);

        if (departement == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(departement);
    }

    //  MÉTHODE GET POUR OBTENIR UN DEPARTEMENT PAR SON NOM
    @GetMapping(path = "/name/{name}")
    public ResponseEntity<DepartementDto> getDepartementByName(@PathVariable String name) {

        DepartementDto departement = departementService.extractDepartementByName(name);

        if (departement == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(departement);
    }

    //  MÉTHODE GET POUR OBTENIR UN DEPARTEMENT PAR SON CODE
    @GetMapping(path = "/code/{code}")
    public ResponseEntity<DepartementDto> getDepartementByCode(@PathVariable String code) {

        DepartementDto departement = departementService.extractDepartementByCode(code);

        if (departement == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(departement);
    }


    //  MÉTHODE POST POUR AJOUTER UN DÉPARTEMENT
    @PostMapping(path = "/add")
    public ResponseEntity<String> addDepartement(@Valid @RequestBody DepartementDto departement, BindingResult bindingResult) {

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
    public ResponseEntity<String> updateDepartement(@Valid @RequestBody DepartementDto departement, BindingResult bindingResult) {

        if (departement == null) {
            return ResponseEntity.badRequest().build();
        }

        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors().get(0).getDefaultMessage());
        }

        DepartementDto departementAModif = departementService.modifierDepartement(departement);

        if (departementAModif == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok("Département mise à jour avec succès : " + departementAModif.getNom());

    }

    //  MÉTHODE DELETE POUR SUPPRIMER UN DEPARTEMENT
    @DeleteMapping(path = "/remove/{id}")
    public ResponseEntity<String> deleteVille(@PathVariable int id) {

        DepartementDto departement = departementService.supprimerDepartement(id);

        if (departement == null) {
            return ResponseEntity.notFound().build();
        }


        return ResponseEntity.ok("Département supprimée avec succès : " + departement.getNom());
    }


}
