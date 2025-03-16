package modele;

import modele.Coup;

import java.util.List;

public class IAMinimax implements ModeIA{
    private int profondeurMax = 3; // Ajustable

    @Override
    public int[] jouerCoup(Partie partie, char couleur) {
        List<int[]> coupsPossibles = partie.getCoupsPossibles(couleur);
        int[] meilleurCoup = null;
        int meilleureValeur = Integer.MIN_VALUE;

        for (int[] coup : coupsPossibles) {
            Partie copie = partie.copier();
            String coupStr = (coup[0] + 1) + " " + (char) ('A' + coup[1]);
            copie.jouerCoup(coupStr);

            int valeur = minimax(copie, profondeurMax, false, couleur);
            if (valeur > meilleureValeur) {
                meilleureValeur = valeur;
                meilleurCoup = coup;
            }
        }
        return meilleurCoup;
    }

    private int minimax(Partie partie, int profondeur, boolean estMax, char couleur) {
        if (profondeur == 0 || partie.estTerminee()) {
            return evaluerPlateau(partie, couleur);
        }

        List<int[]> coupsPossibles = partie.getCoupsPossibles(couleur);
        if (estMax) {
            int maxEval = Integer.MIN_VALUE;
            for (int[] coup : coupsPossibles) {
                Partie copie = partie.copier();
                String coupStr = (coup[0] + 1) + " " + (char) ('A' + coup[1]);
                copie.jouerCoup(coupStr);

                int eval = minimax(copie, profondeur - 1, false, couleur);
                maxEval = Math.max(maxEval, eval);
            }
            return maxEval;
        } else {
            int minEval = Integer.MAX_VALUE;
            for (int[] coup : coupsPossibles) {
                Partie copie = partie.copier();
                char adversaire = (couleur == 'N') ? 'B' : 'N';
                String coupStr = (coup[0] + 1) + " " + (char) ('A' + coup[1]);
                copie.jouerCoup(coupStr);
                int eval = minimax(copie, profondeur - 1, true, couleur);
                minEval = Math.min(minEval, eval);
            }
            return minEval;
        }
    }

    private int evaluerPlateau(Partie partie, char couleur) {
        return partie.compterPions(couleur);
    }
}
