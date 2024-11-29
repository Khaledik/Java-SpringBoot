package fr.diginamic.hello.restcontroleurs;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import fr.diginamic.hello.dtos.DepartementDto;
import fr.diginamic.hello.dtos.VilleDto;
import fr.diginamic.hello.exceptions.DepartementNotFoundException;
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
@RequestMapping("/api/departements")
public class DepartementControleur {


    @Autowired
    private DepartementService departementService;
    @Autowired
    private VilleService villeService;

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
    public ResponseEntity<DepartementDto> getDepartementByCode(@PathVariable String code) throws DepartementNotFoundException {

        DepartementDto departement = departementService.extractDepartementByCode(code);


        return ResponseEntity.ok(departement);
    }


    //  MÉTHODE POST POUR AJOUTER UN DÉPARTEMENT
    @PostMapping(path = "/add")
    public ResponseEntity<String> addDepartement(@Valid @RequestBody DepartementDto departement, BindingResult bindingResult) throws InsertUpdateException {

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
    public ResponseEntity<String> updateDepartement(@Valid @RequestBody DepartementDto departement, BindingResult bindingResult) throws InsertUpdateException {

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
    public ResponseEntity<String> deleteDepartement(@PathVariable int id) {

        DepartementDto departement = departementService.supprimerDepartement(id);

        if (departement == null) {
            return ResponseEntity.notFound().build();
        }


        return ResponseEntity.ok("Département supprimée avec succès : " + departement.getNom());
    }

    // METHODE QUI GENERE UN FICHIER PDF CONTENANT TOUTES LES VILLES D'UN DÉPARTEMENT
    @GetMapping(path = "/code/{code}/export-pdf")
    public void exportVillesByDepartementCodeToPdf(@PathVariable String code, HttpServletResponse response) throws IOException, DocumentException, VilleNotFoundException, DepartementNotFoundException {

        villeService.extractVillesByDepartementToPdf(code, response);

    }

    // METHODE QUI GENERE UN FICHIER CSV CONTENANT TOUT LES DÉPARTEMENTS
    @GetMapping(path = "/export-csv")
    public ResponseEntity<byte[]> exportToCsv(HttpServletResponse response) throws DepartementNotFoundException {


        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", "all-departements.csv");

        byte[] csvBytes = departementService.exportToCsv();

        return new ResponseEntity<>(csvBytes, headers, HttpStatus.OK);


    }


}
