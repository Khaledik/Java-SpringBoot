package fr.diginamic.hello.services;

import fr.diginamic.hello.dao.DepartementDao;
import fr.diginamic.hello.dao.VilleDao;
import fr.diginamic.hello.entites.Ville;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class VilleService {

    @Autowired
    private VilleDao villeDao;


    public List<Ville> extractVilles() {
        return villeDao.findAllVille();
    }

    public Ville extractVille(int id) {
        return villeDao.findVilleById(id);
    }

    public Ville extractVille(String name) {
        return villeDao.findVilleByName(name);
    }

    public List<Ville> insertVille(Ville ville) {
        return villeDao.addToVilles(ville);
    }

    public List<Ville> modifierVille(int idVille, Ville villeModifiee) {
        return villeDao.updateVille(idVille, villeModifiee);
    }

    public List<Ville> supprimerVille(int idVille) {
        return villeDao.removeFromVilles(idVille);
    }


}
