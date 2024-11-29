package fr.diginamic.hello.services;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import fr.diginamic.hello.dtos.DepartementDto;
import fr.diginamic.hello.dtos.VilleDto;
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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
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


    private void validateVille(VilleDto ville) throws InsertUpdateException {

        if (ville.getNbHabitants() < 10) {
            throw new InsertUpdateException("La ville doit avoir au moins 10 habitants.");
        } else if (ville.getNom().length() < 2) {
            throw new InsertUpdateException("La ville doit avoir un nom contenant au moins 2 lettres.");
        } else if (ville.getCodeDepartement().length() != 2) {
            throw new InsertUpdateException("Le code département doit être composé de 2 caractères.");
        } else if (isVilleExistInDepartment(ville.getNom(), ville.getCodeDepartement())) {
            throw new InsertUpdateException("Le nom de la ville doit être unique pour un département donné.");
        }

    }


    @Transactional
    public List<VilleDto> extractVilles() throws VilleNotFoundException {
        List<Ville> villes = villeRepository.findAll();

        if (villes.isEmpty()) {
            throw new VilleNotFoundException("Aucune ville n'a été trouvée.");
        }

        List<VilleDto> villesDto = new ArrayList<>();

        for (Ville ville : villes) {
            villesDto.add(villeMapper.toDto(ville));
        }

        return villesDto;
    }

    public VilleDto extractVilleById(int id) throws VilleNotFoundException {
        Optional<Ville> ville = villeRepository.findById(id);

        if (ville.isEmpty()) {
            throw new VilleNotFoundException("Aucune ville dont l'id est " + id + " n’a été trouvée.");
        }

        if (ville.isPresent()) {
            VilleDto villeDto = villeMapper.toDto(ville.get());
            return villeDto;
        } else {
            return null;
        }

    }

    @Transactional
    public VilleDto extractVilleByName(String nom) throws VilleNotFoundException {
        Ville ville = villeRepository.findByNom(nom);

        if (ville == null) {
            throw new VilleNotFoundException("Aucune ville dont le nom est " + nom + " n’a été trouvée.");
        }

        return villeMapper.toDto(ville);
    }

    @Transactional
    public List<VilleDto> extractVillesStartWith(String nom) throws VilleNotFoundException {


        List<Ville> villes = villeRepository.findAllByNomStartingWith(nom);

        if (villes.isEmpty()) {
            throw new VilleNotFoundException("Aucune ville dont le nom commence par " + nom + " n’a été trouvée.");
        }

        List<VilleDto> villesDto = new ArrayList<>();


        for (Ville ville : villes) {
            villesDto.add(villeMapper.toDto(ville));
        }

        return villesDto;
    }

    @Transactional
    public List<VilleDto> extractVillesGreaterThan(int min) throws VilleNotFoundException {

        List<Ville> villes = villeRepository.findAllByNbHabitantsGreaterThan(min);

        if (villes.isEmpty()) {
            throw new VilleNotFoundException("Aucune ville n’a une population supérieure à " + min + ".");
        }

        List<VilleDto> villesDto = new ArrayList<>();

        for (Ville ville : villes) {
            villesDto.add(villeMapper.toDto(ville));
        }

        return villesDto;
    }

    @Transactional
    public byte[] extractVillesGreaterThanToCsv(int min) throws VilleNotFoundException {

        List<VilleDto> villes = extractVillesGreaterThan(min);

        if (villes.isEmpty()) {
            throw new VilleNotFoundException("Aucune ville n’a une population supérieure à " + min + ".");
        }

        String CSV_HEADER = "NOM;NB_HABITANTS;CODE_DEPARTEMENT;DEPARTEMENT\n";

        StringBuilder csvText = new StringBuilder();
        csvText.append(CSV_HEADER);

        for (VilleDto ville : villes) {
            csvText.append(ville.getNom() + ";");
            csvText.append(ville.getNbHabitants() + ";");
            csvText.append(ville.getCodeDepartement() + ";");
            csvText.append(ville.getNomDepartement() + "\n");
        }

        return csvText.toString().getBytes();
    }

    @Transactional
    public void extractVillesByDepartementToPdf(String code, HttpServletResponse response) throws VilleNotFoundException, DepartementNotFoundException, IOException, DocumentException {

        DepartementDto departementDto = departementService.extractDepartementByCode(code);

        if (departementDto == null) {
            throw new DepartementNotFoundException("Le département avec le code " + code + " n'a pas été trouvé.");
        }

        List<VilleDto> villesDtos = extractVillesByDepartementCode(code);

        if (villesDtos.isEmpty()) {
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

        for (VilleDto ville : villesDtos) {
            StringBuilder villeText = new StringBuilder();

            villeText.append("\n\n" + ville.getNom()).append(" - " + ville.getNbHabitants() + " habitants ").append(" - " + ville.getNomDepartement()).append(" (" + ville.getCodeDepartement() + ")").append("\n");

            Phrase ligneVille = new Phrase(villeText.toString(), new Font(baseFont, 13.0f, 1, new BaseColor(0, 51, 80)));

            document.add(ligneVille);

        }


        document.close();

        response.flushBuffer();

    }

    @Transactional
    public List<VilleDto> extractVillesBetween(int min, int max) throws VilleNotFoundException {

        List<Ville> villes = villeRepository.findAllByNbHabitantsBetween(min, max);

        if (villes.isEmpty()) {
            throw new VilleNotFoundException("Aucune ville n’a une population comprise entre " + min + " et " + max + ".");
        }

        List<VilleDto> villesDto = new ArrayList<>();

        for (Ville ville : villes) {
            villesDto.add(villeMapper.toDto(ville));
        }

        return villesDto;
    }

    @Transactional
    public List<VilleDto> extractVillesByDepartementCode(String codeDep) throws VilleNotFoundException {

        List<Ville> villes = villeRepository.findAllByDepartementCode(codeDep);

        if (villes.isEmpty()) {
            throw new VilleNotFoundException("Aucune ville n’a une été trouvée dans le département : " + codeDep + ".");
        }

        List<VilleDto> villesDto = new ArrayList<>();

        for (Ville ville : villes) {
            villesDto.add(villeMapper.toDto(ville));
        }

        return villesDto;


    }

    @Transactional
    public List<VilleDto> extractVillesByDepartementGreaterThan(String codeDep, int min) throws VilleNotFoundException {

        List<Ville> villes = villeRepository.findAllByDepartementIdAndNbHabitantsGreaterThan(codeDep, min);

        if (villes.isEmpty()) {
            throw new VilleNotFoundException("Aucune ville n’a une population supérieure à " + min + " dans le département " + codeDep + ".");
        }

        List<VilleDto> villesDto = new ArrayList<>();

        for (Ville ville : villes) {
            villesDto.add(villeMapper.toDto(ville));
        }

        return villesDto;

    }

    @Transactional
    public List<VilleDto> extractVillesByDepartementBetween(String codeDep, int min, int max) throws VilleNotFoundException {

        List<Ville> villes = villeRepository.findAllByDepartementCodeAndNbHabitantsBetween(codeDep, min, max);

        if (villes.isEmpty()) {
            throw new VilleNotFoundException("Aucune ville n’a une population comprise entre " + min + " et " + max + " dans le département " + codeDep + ".");
        }

        List<VilleDto> villesDto = new ArrayList<>();

        for (Ville ville : villes) {
            villesDto.add(villeMapper.toDto(ville));
        }

        return villesDto;
    }

    @Transactional
    public List<VilleDto> extractTopNVillesByDepartement(String codeDep, int n) throws VilleNotFoundException {

        Pageable pageable = PageRequest.of(0, n);
        List<Ville> villes = villeRepository.findTopNVillesByDepartementId(codeDep, pageable);


        if (villes.isEmpty() || villes.size() < n) {
            throw new VilleNotFoundException("Aucune des " + n + " villes les plus peuplées n'a été trouvée dans le département " + codeDep + ".");
        }


        List<VilleDto> villesDto = new ArrayList<>();

        for (Ville ville : villes) {
            villesDto.add(villeMapper.toDto(ville));
        }

        return villesDto;
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
    public VilleDto insertVille(VilleDto villeDto) throws InsertUpdateException {

        validateVille(villeDto);

        Ville ville = villeMapper.toBean(villeDto);

        if (ville.getDepartement() != null) {
            departementRepository.save(ville.getDepartement());
        }


        Ville villeToInsert = villeRepository.save(ville);

        return villeDto;
    }

    @Transactional
    public VilleDto modifierVille(int id, VilleDto villeDto) throws InsertUpdateException {

        validateVille(villeDto);

        Ville ville = villeMapper.toBean(villeDto);

        Ville villeAModif = villeRepository.findById(id).get();

        villeAModif.setNom(ville.getNom());
        villeAModif.setNbHabitants(ville.getNbHabitants());

        villeRepository.save(villeAModif);

        return villeDto;
    }

    @Transactional
    public VilleDto supprimerVille(int id) {

        Optional<Ville> ville = villeRepository.findById(id);

        VilleDto villeDto;

        if (ville.isPresent()) {
            villeRepository.deleteById(id);
            villeDto = villeMapper.toDto(ville.get());
        } else {
            return null;
        }


        return villeDto;
    }


}
