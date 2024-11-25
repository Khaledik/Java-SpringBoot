package fr.diginamic.hello.services;

import fr.diginamic.hello.entites.Ville;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class VilleService {

    List<Ville> villes = new ArrayList<Ville>();

    public VilleService() {
        int id = 1;
        villes.add(new Ville(id++, "Nice", 343000));
        villes.add(new Ville(id++, "Carcassonne", 47800));
        villes.add(new Ville(id++, "Narbonne", 53400));
        villes.add(new Ville(id++, "Lyon", 484000));
        villes.add(new Ville(id++, "Foix", 9700));
        villes.add(new Ville(id++, "Pau", 77200));
        villes.add(new Ville(id++, "Marseille", 850700));
        villes.add(new Ville(id++, "Tarbes", 40600));
    }


    public List<Ville> getVilles() {
        return villes;
    }

    public Ville insertVille(Ville ville) {
        villes.add(ville);
        return ville;
    }

    public Ville removeVille(Ville ville) {
        villes.remove(ville);
        return ville;
    }


}
