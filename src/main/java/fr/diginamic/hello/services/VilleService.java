package fr.diginamic.hello.services;

import fr.diginamic.hello.entites.Ville;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class VilleService {

    List<Ville> villes = new ArrayList<Ville>();

    public VilleService() {
        villes.add(new Ville("Nice", 343000));
        villes.add(new Ville("Carcassonne", 47800));
        villes.add(new Ville("Narbonne", 53400));
        villes.add(new Ville("Lyon", 484000));
        villes.add(new Ville("Foix", 9700));
        villes.add(new Ville("Pau", 77200));
        villes.add(new Ville("Marseille", 850700));
        villes.add(new Ville("Tarbes", 40600));
    }


    public List<Ville> getVilles() {
        return villes;
    }

    public Ville findVilleById(Ville ville) {

        Ville foundVille = null;

        for (Ville v : villes) {
            if (v.getId() == ville.getId()) {
                ville = v;
            }
        }
        return ville;
    }

    public Ville insertVille(Ville ville) {
        villes.add(ville);
        return ville;
    }

    public Ville updateVille(Ville ville) {
        Ville villeAModif = findVilleById(ville);


        villeAModif.setId(ville.getId());
        villeAModif.setNom(ville.getNom());
        villeAModif.setNbHabitants(ville.getNbHabitants());

        return villeAModif;
    }

    public Ville removeVille(Ville ville) {
        villes.remove(ville);
        return ville;
    }


}
