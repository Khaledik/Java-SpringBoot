package fr.diginamic.hello.dao;


import fr.diginamic.hello.entites.Departement;
import fr.diginamic.hello.entites.Ville;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DepartementDao {

    @PersistenceContext
    private EntityManager em;


    @Transactional
    public List<Departement> findAllDepartement() {
        return em.createQuery("from Departement", Departement.class).getResultList();
    }


    @Transactional
    public Departement findDepartementById(int id) {
        return em.find(Departement.class, id);
    }

    @Transactional
    public Departement findDepartementByName(String name) {
        TypedQuery<Departement> query = em.createQuery("from Departement where nom = :nom", Departement.class);
        query.setParameter("nom", name);
        return query.getSingleResult();
    }

    @Transactional
    public Departement findDepartementsByCode(String code) {
        TypedQuery<Departement> query = em.createQuery("from Departement where code = :code", Departement.class);
        query.setParameter("code", code);
        return query.getSingleResult();
    }

    @Transactional
    public List<Departement> addToDepartement(Departement departement) {
        em.persist(departement);
        return findAllDepartement();
    }

    @Transactional
    public List<Departement> updateDepartement(int id, Departement departement) {

        Departement departementToUpdate = findDepartementById(id);

        if (departementToUpdate != null) {
            departementToUpdate.setNom(departement.getNom());
        }

        return findAllDepartement();

    }

    @Transactional
    public List<Departement> removeFromDepartements(int id) {
        Departement departement = findDepartementById(id);
        em.remove(departement);
        return findAllDepartement();
    }

    @Transactional
    public List<Ville> getMostPopulatedVille(int idDep, int nbVilles) {
        TypedQuery<Ville> query = em.createQuery("SELECT v FROM Ville v LEFT JOIN v.departement d WHERE d.id = :id ORDER BY v.nbHabitants DESC", Ville.class);
        query.setParameter("id", idDep);
        return query.setMaxResults(nbVilles).getResultList();
    }

    @Transactional
    public List<Ville> getPopVilleMinMaxFromDep(int idDep, int min, int max) {
        TypedQuery<Ville> query = em.createQuery("SELECT v FROM Ville v LEFT JOIN v.departement d WHERE d.id = :departementId AND v.nbHabitants BETWEEN :minPopulation AND :maxPopulation", Ville.class);

        query.setParameter("departementId", idDep);
        query.setParameter("minPopulation", min);
        query.setParameter("maxPopulation", max);

        return query.getResultList();
    }
}
