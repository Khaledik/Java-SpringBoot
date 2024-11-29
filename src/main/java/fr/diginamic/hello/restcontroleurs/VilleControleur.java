package fr.diginamic.hello.restcontroleurs;

import com.itextpdf.text.*;
import fr.diginamic.hello.dtos.DepartementDto;
import fr.diginamic.hello.dtos.VilleDto;
import fr.diginamic.hello.exceptions.InsertUpdateException;
import fr.diginamic.hello.exceptions.VilleNotFoundException;
import fr.diginamic.hello.services.DepartementService;
import fr.diginamic.hello.services.VilleService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/villes")
public class VilleControleur {


    @Autowired
    private VilleService villeService;

    @Autowired
    private DepartementService departementService;

    //  MÉTHODE GET POUR OBTENIR TOUTES LES VILLES
    @GetMapping
    public List<VilleDto> getAllVilles() throws VilleNotFoundException {
        return villeService.extractVilles();
    }


    //  MÉTHODE GET POUR OBTENIR UNE VILLE PAR SON ID
    @GetMapping(path = "/id/{id}")
    public ResponseEntity<VilleDto> getVilleById(@PathVariable int id) throws VilleNotFoundException {

        VilleDto ville = villeService.extractVilleById(id);


        return ResponseEntity.ok(ville);
    }

    //  MÉTHODE GET POUR OBTENIR UNE VILLE PAR SON NOM
    @GetMapping(path = "/name/{name}")
    public ResponseEntity<VilleDto> getVilleByName(@PathVariable String name) throws VilleNotFoundException {

        VilleDto ville = villeService.extractVilleByName(name);


        return ResponseEntity.ok(ville);
    }


    //  MÉTHODE POST POUR AJOUTER UNE VILLE
    @PostMapping(path = "/add")
    public ResponseEntity<String> addVille(@Valid @RequestBody VilleDto ville, BindingResult bindingResult) throws InsertUpdateException {

        if (ville == null) {
            return ResponseEntity.badRequest().build();
        }

        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors().get(0).getDefaultMessage());
        }

        villeService.insertVille(ville);
        return ResponseEntity.ok("Ville insérée avec succès : " + ville.getNom());
    }


    //  MÉTHODE PUT POUR EDITER UNE VILLE
    @PutMapping(path = "/edit/{id}")
    public ResponseEntity<String> updateVille(@Valid @PathVariable int id, @RequestBody VilleDto ville, BindingResult bindingResult) throws InsertUpdateException {

        if (ville == null) {
            return ResponseEntity.badRequest().build();
        }

        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors().get(0).getDefaultMessage());
        }

        VilleDto villeAModif = villeService.modifierVille(id, ville);

        if (villeAModif == null) {
            return ResponseEntity.notFound().build();
        }


        return ResponseEntity.ok("Ville mise à jour avec succès : " + villeAModif.getNom());
    }


    //  MÉTHODE DELETE POUR SUPPRIMER UNE VILLE
    @DeleteMapping(path = "/remove/{id}")
    public ResponseEntity<String> deleteVille(@PathVariable int id) {

        VilleDto ville = villeService.supprimerVille(id);

        if (ville == null) {
            return ResponseEntity.notFound().build();
        }


        return ResponseEntity.ok("Ville supprimée avec succès : " + ville.getNom());
    }

    // REQUÊTES PERSONNALISÉ REPOSITORY JPA

    // Recherche de toutes les villes dont le nom commence par une chaine de caractères données
    @GetMapping(path = "/start-with/{nom}")
    public ResponseEntity<Object> getVillesStartWith(@PathVariable String nom) throws VilleNotFoundException {

        List<VilleDto> villes = villeService.extractVillesStartWith(nom);


        return ResponseEntity.ok(villes);

    }

    //  Recherche de toutes les villes dont la population est supérieure à min (paramètre de type int)
    @GetMapping(path = "/pop-min")
    public ResponseEntity<Object> getVillesGreaterThan(@RequestParam int min) throws VilleNotFoundException {

        List<VilleDto> villes = villeService.extractVillesGreaterThan(min);


        return ResponseEntity.ok(villes);

    }

    //  Recherche de toutes les villes dont la population est supérieure à min et inférieure à max.
    @GetMapping(path = "/pop-min-max")
    public ResponseEntity<Object> getVillesBetween(@RequestParam int min, @RequestParam int max) throws VilleNotFoundException {


        List<VilleDto> villes = villeService.extractVillesBetween(min, max);


        return ResponseEntity.ok(villes);

    }

    //  Recherche de toutes les villes d’un département dont la population est supérieure à min (paramètre de type int)
    @GetMapping(path = "/departement/{code}/pop-dep-min")
    public ResponseEntity<Object> getVillesByDepartementGreaterThan(@PathVariable String code, @RequestParam int min) throws VilleNotFoundException {

        DepartementDto departement = departementService.extractDepartementByCode(code);
        List<VilleDto> villes = villeService.extractVillesByDepartementGreaterThan(departement.getCode(), min);


        return ResponseEntity.ok(villes);

    }

    //  Recherche de toutes les villes d’un département dont la population est supérieure à min et inférieure à max.
    @GetMapping(path = "/departement/{code}/pop-dep-min-max")
    public ResponseEntity<Object> getVillesByDepartementBetween(@PathVariable String code, @RequestParam int min, @RequestParam int max) throws VilleNotFoundException {

        DepartementDto departement = departementService.extractDepartementByCode(code);
        List<VilleDto> villes = villeService.extractVillesByDepartementBetween(departement.getCode(), min, max);


        return ResponseEntity.ok(villes);

    }

    //  Recherche des n villes les plus peuplées d’un département donné (n est aussi un paramètre)
    @GetMapping(path = "/departement/{code}/top-villes")
    public ResponseEntity<Object> getTopNVillesByDepartement(@PathVariable String code, @RequestParam int n) throws VilleNotFoundException {

        DepartementDto departement = departementService.extractDepartementByCode(code);


        List<VilleDto> villes = villeService.extractTopNVillesByDepartement(departement.getCode(), n);


        return ResponseEntity.ok(villes);
    }

    // METHODE QUI GENERE FICHIER CSV DES VILLES AVEC UNE POPULATION MIN
    @GetMapping(path = "/pop-min/{min}/export-csv")
    public ResponseEntity<byte[]> exportVillesByPopMinToCsv(@PathVariable int min, HttpServletResponse response) throws  VilleNotFoundException {



        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", "villes.csv");

        byte[] csvBytes = villeService.extractVillesGreaterThanToCsv(min);

        return new ResponseEntity<>(csvBytes, headers, HttpStatus.OK);


    }





}
