package fr.diginamic.hello.dtos;

public class DepartementDto {

    private String code;
    private String nom;
    private int nbHabitants;

    public DepartementDto() {

    }

    /**
     * Getter for code.
     *
     * @return the value of code.
     */
    public String getCode() {
        return code;
    }

    /**
     * Setter for code.
     *
     * @param value the new value for code.
     */
    public void setCode(String value) {
        this.code = value;
    }

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
}
