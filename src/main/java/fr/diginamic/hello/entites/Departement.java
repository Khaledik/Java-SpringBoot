package fr.diginamic.hello.entites;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.Range;

import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "departements")
public class Departement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;

    @NotNull
    @Size(min = 2, max = 100)
    @Column(name = "nom", length = 100, nullable = false)
    private String nom;

    @NotNull
    @Column(name = "code", length = 10, nullable = false)
    private String code;

    @Column(name = "nb_habitants")
    private Integer nbHabitants;

    // RELATION DEPARTEMENT -> VILLE
    @OneToMany(mappedBy = "departement")
    private Set<Ville> villes;

    {
        villes = new HashSet<>();
    }

    public Departement(String nom, String code) {
        this.nom = nom;
        this.code = code;
    }

    public Departement() {
    }


    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Departement{");
        sb.append("id=").append(id);
        sb.append(", nom='").append(nom).append('\'');
        sb.append(", code='").append(code).append('\'');
        sb.append(", nbHabitants=").append(nbHabitants);
        sb.append(", villes=").append(villes);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Departement that = (Departement) o;
        return id == that.id && code == that.code && Objects.equals(nom, that.nom);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nom, code);
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
     * Getter for code.
     *
     * @return the value of code.
     */
    public @NotNull String getCode() {
        return code;
    }

    /**
     * Setter for code.
     *
     * @param value the new value for code.
     */
    public void setCode(@NotNull String value) {
        this.code = value;
    }

    /**
     * Getter for nbHabitants.
     *
     * @return the value of nbHabitants.
     */
    public Integer getNbHabitants() {
        return nbHabitants;
    }

    /**
     * Setter for nbHabitants.
     *
     * @param value the new value for nbHabitants.
     */
    public void setNbHabitants(Integer value) {
        this.nbHabitants = value;
    }

    /**
     * Getter for villes.
     *
     * @return the value of villes.
     */
    public Set<Ville> getVilles() {
        return villes;
    }

    /**
     * Setter for villes.
     *
     * @param value the new value for villes.
     */
    public void setVilles(Set<Ville> value) {
        this.villes = value;
    }
}
