package fr.diginamic.hello.services;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import fr.diginamic.hello.entites.Departement;
import fr.diginamic.hello.entites.Ville;
import fr.diginamic.hello.exceptions.DepartementNotFoundException;
import fr.diginamic.hello.exceptions.InsertUpdateException;
import fr.diginamic.hello.exceptions.VilleNotFoundException;
import fr.diginamic.hello.mappers.VilleMapper;
import fr.diginamic.hello.repositories.DepartementRepository;
import fr.diginamic.hello.repositories.VilleRepository;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class VilleService {

    @Autowired
    private VilleRepository villeRepository;

    @Autowired
    private DepartementRepository departementRepository;

    @Autowired
    private VilleMapper villeMapper;

    @Autowired
    private DepartementService departementService;


    private void validateVille(Ville ville) throws InsertUpdateException {

        if (ville.getNbHabitants() < 10) {
            throw new InsertUpdateException("La ville doit avoir au moins 10 habitants.");
        } else if (ville.getNom().length() < 2) {
            throw new InsertUpdateException("La ville doit avoir un nom contenant au moins 2 lettres.");
        } else if (ville.getDepartement().getCode().length() != 2) {
            throw new InsertUpdateException("Le code département doit être composé de 2 caractères.");
        } else if (isVilleExistInDepartment(ville.getNom(), ville.getDepartement().getCode())) {
            throw new InsertUpdateException("Le nom de la ville doit être unique pour un département donné.");
        }

    }


    @Transactional
    public List<Ville> extractVilles() throws VilleNotFoundException {
        List<Ville> villes = villeRepository.findAll();

        if (villes.isEmpty()) {
            throw new VilleNotFoundException("Aucune ville n'a été trouvée.");
        }


        return villes;
    }

    public Ville extractVilleById(int id) throws VilleNotFoundException {
        Optional<Ville> ville = villeRepository.findById(id);

        if (ville.isEmpty()) {
            throw new VilleNotFoundException("Aucune ville dont l'id est " + id + " n’a été trouvée.");
        } else {
            return ville.get();
        }


    }

    @Transactional
    public Ville extractVilleByName(String nom) throws VilleNotFoundException {
        Ville ville = villeRepository.findByNom(nom);

        if (ville == null) {
            throw new VilleNotFoundException("Aucune ville dont le nom est " + nom + " n’a été trouvée.");
        }

        return ville;
    }

    @Transactional
    public List<Ville> extractVillesStartWith(String nom) throws VilleNotFoundException {


        List<Ville> villes = villeRepository.findAllByNomStartingWith(nom);

        if (villes.isEmpty()) {
            throw new VilleNotFoundException("Aucune ville dont le nom commence par " + nom + " n’a été trouvée.");
        } else {
            return villes;
        }


    }

    @Transactional
    public List<Ville> extractVillesGreaterThan(int min) throws VilleNotFoundException {

        List<Ville> villes = villeRepository.findAllByNbHabitantsGreaterThan(min);

        if (villes.isEmpty()) {
            throw new VilleNotFoundException("Aucune ville n’a une population supérieure à " + min + ".");
        } else {
            return villes;
        }


    }

    @Transactional
    public byte[] exportToCsv() throws VilleNotFoundException {

        List<Ville> villes = extractVilles();

        if (villes.isEmpty()) {
            throw new VilleNotFoundException("Aucune ville n’a éte trouvée.");
        }

        String CSV_HEADER = "NOM;NB_HABITANTS;CODE_DEPARTEMENT;DEPARTEMENT\n";

        StringBuilder csvText = new StringBuilder();
        csvText.append(CSV_HEADER);

        for (Ville ville : villes) {
            csvText.append(ville.getNom() + ";");
            csvText.append(ville.getNbHabitants() + ";");
            csvText.append(ville.getDepartement().getCode() + ";");
            csvText.append(ville.getDepartement().getNom() + "\n");
        }

        return csvText.toString().getBytes();
    }


    @Transactional
    public byte[] extractVillesGreaterThanToCsv(int min) throws VilleNotFoundException {

        List<Ville> villes = extractVillesGreaterThan(min);

        if (villes.isEmpty()) {
            throw new VilleNotFoundException("Aucune ville n’a une population supérieure à " + min + ".");
        }

        String CSV_HEADER = "NOM;NB_HABITANTS;CODE_DEPARTEMENT;DEPARTEMENT\n";

        StringBuilder csvText = new StringBuilder();
        csvText.append(CSV_HEADER);

        for (Ville ville : villes) {
            csvText.append(ville.getNom() + ";");
            csvText.append(ville.getNbHabitants() + ";");
            csvText.append(ville.getDepartement().getCode() + ";");
            csvText.append(ville.getDepartement().getNom() + "\n");
        }

        return csvText.toString().getBytes();
    }

    @Transactional
    public void extractVillesByDepartementToPdf(String code, HttpServletResponse response) throws VilleNotFoundException, DepartementNotFoundException, IOException, DocumentException {

        Departement departementDto = departementService.extractDepartementByCode(code);

        if (departementDto == null) {
            throw new DepartementNotFoundException("Le département avec le code " + code + " n'a pas été trouvé.");
        }

        List<Ville> villes = extractVillesByDepartementCode(code);

        if (villes.isEmpty()) {
            throw new VilleNotFoundException("Aucune ville dans ce département n'a été trouvée.");
        }


        response.setHeader("Content-Disposition", "attachment;filename=\"villes-" + departementDto.getNom() + ".pdf\"");
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());


        document.open();

        document.addTitle("Liste des villes : " + departementDto.getNom() + ".");

        document.newPage();

        BaseFont baseFont = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.WINANSI, BaseFont.EMBEDDED);
        Phrase titre = new Phrase("Liste des villes : " + departementDto.getNom(), new Font(baseFont, 28, 1, new BaseColor(0, 51, 80)));
        document.add(titre);

        for (Ville ville : villes) {
            StringBuilder villeText = new StringBuilder();

            villeText.append("\n\n" + ville.getNom()).append(" - " + ville.getNbHabitants() + " habitants ").append(" - " + ville.getDepartement().getNom()).append(" (" + ville.getDepartement().getCode() + ")").append("\n");

            Phrase ligneVille = new Phrase(villeText.toString(), new Font(baseFont, 13.0f, 1, new BaseColor(0, 51, 80)));

            document.add(ligneVille);

        }


        document.close();

        response.flushBuffer();

    }

    @Transactional
    public List<Ville> extractVillesBetween(int min, int max) throws VilleNotFoundException {

        List<Ville> villes = villeRepository.findAllByNbHabitantsBetween(min, max);

        if (villes.isEmpty()) {
            throw new VilleNotFoundException("Aucune ville n’a une population comprise entre " + min + " et " + max + ".");
        } else {
            return villes;
        }


    }

    @Transactional
    public List<Ville> extractVillesByDepartementCode(String codeDep) throws VilleNotFoundException {

        List<Ville> villes = villeRepository.findAllByDepartementCode(codeDep);

        if (villes.isEmpty()) {
            throw new VilleNotFoundException("Aucune ville n’a une été trouvée dans le département : " + codeDep + ".");
        } else {
            return villes;
        }


    }

    @Transactional
    public List<Ville> extractVillesByDepartementGreaterThan(String codeDep, int min) throws VilleNotFoundException {

        List<Ville> villes = villeRepository.findAllByDepartementIdAndNbHabitantsGreaterThan(codeDep, min);

        if (villes.isEmpty()) {
            throw new VilleNotFoundException("Aucune ville n’a une population supérieure à " + min + " dans le département " + codeDep + ".");
        } else {
            return villes;
        }


    }

    @Transactional
    public List<Ville> extractVillesByDepartementBetween(String codeDep, int min, int max) throws VilleNotFoundException {

        List<Ville> villes = villeRepository.findAllByDepartementCodeAndNbHabitantsBetween(codeDep, min, max);

        if (villes.isEmpty()) {
            throw new VilleNotFoundException("Aucune ville n’a une population comprise entre " + min + " et " + max + " dans le département " + codeDep + ".");
        } else {
            return villes;
        }


    }

    @Transactional
    public List<Ville> extractTopNVillesByDepartement(String codeDep, int n) throws VilleNotFoundException {

        Pageable pageable = PageRequest.of(0, n);
        List<Ville> villes = villeRepository.findTopNVillesByDepartementId(codeDep, pageable);


        if (villes.isEmpty() || villes.size() < n) {
            throw new VilleNotFoundException("Aucune des " + n + " villes les plus peuplées n'a été trouvée dans le département " + codeDep + ".");
        } else {
            return villes;
        }


    }

    @Transactional
    public boolean isVilleExistInDepartment(String nom, String codeDep) {
        Ville ville = villeRepository.findByNomAndDepartementCode(nom, codeDep);

        if (ville == null) {
            return false;
        } else {
            return true;
        }
    }


    @Transactional
    public List<Ville> insertVille(Ville ville) throws InsertUpdateException {

        validateVille(ville);


        if (ville.getDepartement() != null) {
            departementRepository.save(ville.getDepartement());
        }


        villeRepository.save(ville);

        return extractVilles();
    }

    @Transactional
    public Ville modifierVille(int id, Ville ville) throws InsertUpdateException {

        validateVille(ville);


        Ville villeAModif = villeRepository.findById(id).get();

        if (villeAModif == null) {
            throw new VilleNotFoundException("Aucune villes n'a été trouvée avec l'id " + id + ".");
        }

        villeAModif.setNom(ville.getNom());
        villeAModif.setNbHabitants(ville.getNbHabitants());

        villeRepository.save(villeAModif);

        return villeAModif;
    }

    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public Ville supprimerVille(int id) {

        Optional<Ville> ville = villeRepository.findById(id);


        if (ville.isPresent()) {
            villeRepository.deleteById(id);
            return ville.get();
        } else {
            throw new VilleNotFoundException("Aucune villes n'a été trouvée avec l'id " + id + ".");
        }


    }


}
