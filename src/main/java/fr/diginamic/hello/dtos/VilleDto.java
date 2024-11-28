package fr.diginamic.hello.dtos;

import fr.diginamic.hello.entites.Departement;

public class VilleDto {

    private String nom;
    private int nbHabitants;
    private String codeDepartement;
    private String nomDepartement;

    public VilleDto() {}

    /**
     * Getter for nom.
     *
     * @return the value of nom.
     */
    public String getNom() {
        return nom;
    }

    /**
     * Setter for nom.
     *
     * @param value the new value for nom.
     */
    public void setNom(String value) {
        this.nom = value;
    }

    /**
     * Getter for nbHabitants.
     *
     * @return the value of nbHabitants.
     */
    public int getNbHabitants() {
        return nbHabitants;
    }

    /**
     * Setter for nbHabitants.
     *
     * @param value the new value for nbHabitants.
     */
    public void setNbHabitants(int value) {
        this.nbHabitants = value;
    }

    /**
     * Getter for codeDepartement.
     *
     * @return the value of codeDepartement.
     */
    public String getCodeDepartement() {
        return codeDepartement;
    }

    /**
     * Setter for codeDepartement.
     *
     * @param value the new value for codeDepartement.
     */
    public void setCodeDepartement(String value) {
        this.codeDepartement = value;
    }

    /**
     * Getter for nomDepartement.
     *
     * @return the value of nomDepartement.
     */
    public String getNomDepartement() {
        return nomDepartement;
    }

    /**
     * Setter for nomDepartement.
     *
     * @param value the new value for nomDepartement.
     */
    public void setNomDepartement(String value) {
        this.nomDepartement = value;
    }
}
