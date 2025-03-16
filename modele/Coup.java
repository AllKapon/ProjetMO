package modele;

public class Coup {
    private int ligne;
    private int colonne;

    /**
     * Construit un objet Coup à partir d'une représentation sous forme de chaîne de caractère.
     **/
    public Coup(String coup) {
        String[] parts = coup.split(" ");
        this.ligne = Integer.parseInt(parts[0]) - 1;
        this.colonne = parts[1].charAt(0) - 'A';
    }

    /**
     * Retourne la ligne du coup.
     **/
    public int getLigne() {
        return ligne;
    }

    /**
     * Retourne la colonne du coup.
     **/
    public int getColonne() {
        return colonne;
    }
}