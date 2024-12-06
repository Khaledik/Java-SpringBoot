package fr.diginamic.hello.entites;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Objects;

@Entity
@Table(name = "villes")
public class Ville {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;

    @NotNull
    @Size(min = 2, max = 100)
    @Column(name = "nom", nullable = false, length = 100)
    private String nom;

    @Min(1)
    @Column(name = "nb_habitants", nullable = false)
    private int nbHabitants;


    // RELATION VILLE -> DEPARTEMENT
    @ManyToOne
    @JoinColumn(name = "id_departement")
    private Departement departement;

    public Ville(String nom, int nbHabitants, Departement departement) {
        this.nom = nom;
        this.nbHabitants = nbHabitants;
        this.departement = departement;
    }

    public Ville() {
    }


    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Ville{");
        sb.append("id=").append(id);
        sb.append(", nom='").append(nom).append('\'');
        sb.append(", nbHabitants=").append(nbHabitants);
        sb.append(", departement=").append(departement);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ville ville = (Ville) o;
        return id == ville.id && nbHabitants == ville.nbHabitants && Objects.equals(nom, ville.nom);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nom, nbHabitants);
    }

    /**
     * Getter for id.
     *
     * @return the value of id.
     */
    public int getId() {
        return id;
    }

    /**
     * Setter for id.
     *
     * @param value the new value for id.
     */
    public void setId(int value) {
        this.id = value;
    }

    /**
     * Getter for nom.
     *
     * @return the value of nom.
     */
    public @NotNull @Size(min = 2, max = 100) String getNom() {
        return nom;
    }

    /**
     * Setter for nom.
     *
     * @param value the new value for nom.
     */
    public void setNom(@NotNull @Size(min = 2, max = 100) String value) {
        this.nom = value;
    }

    /**
     * Getter for nbHabitants.
     *
     * @return the value of nbHabitants.
     */
    @Min(1)
    public int getNbHabitants() {
        return nbHabitants;
    }

    /**
     * Setter for nbHabitants.
     *
     * @param value the new value for nbHabitants.
     */
    public void setNbHabitants(@Min(1) int value) {
        this.nbHabitants = value;
    }

    /**
     * Getter for departement.
     *
     * @return the value of departement.
     */
    public Departement getDepartement() {
        return departement;
    }

    /**
     * Setter for departement.
     *
     * @param value the new value for departement.
     */
    public void setDepartement(Departement value) {
        this.departement = value;
    }
}
