package fr.diginamic.hello.services;

import fr.diginamic.hello.dao.DepartementDao;
import fr.diginamic.hello.entites.Departement;
import fr.diginamic.hello.entites.Ville;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartementService {

    @Autowired
    private DepartementDao departementDao;


    public List<Departement> extractDepartements() {
        return departementDao.findAllDepartement();
    }

    public Departement extractDepartement(int id) {
        return departementDao.findDepartementById(id);
    }

    public Departement extractDepartement(String name) {
        return departementDao.findDepartementByName(name);
    }

    public List<Departement> insertDepartement(Departement departement) {
        return departementDao.addToDepartement(departement);
    }

    public List<Departement> modifierDepartement(int idDep, Departement departementModifee) {
        return departementDao.updateDepartement(idDep, departementModifee);
    }

    public List<Departement> supprimerDepartement(int idDep) {
        return departementDao.removeFromDepartements(idDep);
    }

    public List<Ville> getMostPopulatedVilles(int idDep, int nbVilles) {
        return departementDao.getMostPopulatedVille(idDep, nbVilles);
    }

    public List<Ville> extractPopMinMaxFromDep(int idDep ,int min, int max) {
        return departementDao.getPopVilleMinMaxFromDep(idDep, min, max);
    }

}
