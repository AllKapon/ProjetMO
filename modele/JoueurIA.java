package modele;


import java.util.ArrayList;
import java.util.Random;

public class JoueurIA extends Joueur {
    private Random random;

    public JoueurIA(String nom, char couleur) {
        super(nom, couleur);
        this.random = new Random();
    }

    public int[] choisirCoup(ArrayList<int[]> coupsPossibles) {
        if (coupsPossibles.isEmpty()) {
            return null; // Aucun coup possible
        }
        return coupsPossibles.get(random.nextInt(coupsPossibles.size()));
    }
}
