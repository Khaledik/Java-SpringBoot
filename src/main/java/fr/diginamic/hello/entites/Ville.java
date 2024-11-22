package fr.diginamic.hello.entites;

public class Ville {

    private String nom;
    private int nbHabitants;

    public Ville(String nom, int nbHabitants) {
        this.nom = nom;
        this.nbHabitants = nbHabitants;
    }


    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Ville{");
        sb.append("nom='").append(nom).append('\'');
        sb.append(", nbHabitants=").append(nbHabitants);
        sb.append('}');
        return sb.toString();
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
