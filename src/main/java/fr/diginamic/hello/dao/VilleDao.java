package fr.diginamic.hello.dao;

import fr.diginamic.hello.entites.Ville;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class VilleDao {

    @PersistenceContext
    private EntityManager em;


    @Transactional
    public List<Ville> findAllVille() {
        return em.createQuery("from Ville", Ville.class).getResultList();
    }

    @Transactional
    public Ville findVilleById(int id) {
        return em.find(Ville.class, id);
    }

    @Transactional
    public Ville findVilleByName(String name) {
        TypedQuery<Ville> query = em.createQuery("select v from Ville v where v.nom = :nom", Ville.class);
        query.setParameter("nom", name);
        return query.getSingleResult();
    }

    @Transactional
    public List<Ville> addToVilles(Ville ville) {
        em.persist(ville);
        return findAllVille();
    }

    @Transactional
    public List<Ville> updateVille(int id, Ville ville) {

        Ville villeToUpdate = findVilleById(id);

        if (villeToUpdate != null) {
            villeToUpdate.setNom(ville.getNom());
            villeToUpdate.setNbHabitants(ville.getNbHabitants());
        }

        return findAllVille();
    }

    @Transactional
    public List<Ville> removeFromVilles(int id) {
        Ville ville = findVilleById(id);
        em.remove(ville);
        return findAllVille();
    }



}
