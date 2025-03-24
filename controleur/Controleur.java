package controleur;

import modele.*;
import modele.Coup;
import modele.ModeIA;
import vue.Ihm;

import java.util.ArrayList;

public class Controleur {
    private Ihm ihm;
    private Partie partie;

    public Controleur(Ihm ihm) {
        this.ihm = ihm;
    }

    public void jouer() {
        String nomJoueur1 = ihm.demanderNomJoueur(1);
        boolean contreIA = ihm.demanderSiContreIA();

        Joueur joueur1 = new Joueur(nomJoueur1, 'N');
        Joueur joueur2;

        if (contreIA) {
            int choixIA = ihm.demanderTypeIA();
            ModeIA strategieIA = (choixIA == 1) ? new IAMinimax() : new IANaive();
            joueur2 = new JoueurIA("IA", 'B', strategieIA);
        } else {
            joueur2 = new Joueur(ihm.demanderNomJoueur(2), 'B');
        }

        boolean rejouer = true;
        while (rejouer) {
            partie = new Partie(joueur1, joueur2);
            jouerPartie();
            rejouer = ihm.demanderRejouer();
        }

        afficherResultatsFinaux();
    }

    private void jouerPartie() {
        while (!partie.estTerminee()) {
            ihm.afficherPlateau(partie.getPlateau());
            Joueur joueurCourant = partie.getJoueurCourant();
            ArrayList<int[]> coupsPossibles = new ArrayList<>(partie.getCoupsPossibles(joueurCourant.getCouleur()));

            if (coupsPossibles.isEmpty()) {
                ihm.afficherMessage(joueurCourant.getNom() + " ne peut pas jouer et passe son tour.");
                partie.passerTour();
                continue;
            }

            String coup;
            boolean coupValide = false;

            if (joueurCourant instanceof JoueurIA) {
                JoueurIA ia = (JoueurIA) joueurCourant;
                int[] coupIA = ia.choisirCoup(partie);
                coup = (coupIA[0] + 1) + " " + (char) ('A' + coupIA[1]);

                ihm.afficherMessage("L'IA joue : " + coup);
                coupValide = partie.jouerCoup(coup);
            } else {
                do {
                    coup = ihm.demanderCoup(joueurCourant.getNom());

                    if (!coup.matches("[1-8] [A-H]") && !coup.equals("P")) {
                        ihm.afficherMessage("Format invalide. Exemple : 3 D ou P pour passer.");
                        continue;
                    }

                    if (coup.equals("P")) {
                        partie.passerTour();
                        coupValide = true;
                    } else {
                        coupValide = partie.jouerCoup(coup);
                        if (!coupValide) {
                            ihm.afficherMessage("Coup invalide. Réessayez.");
                        }
                    }
                } while (!coupValide);
            }
            ihm.afficherPlateau(partie.getPlateau());
            partie.changerTour();
        }

        afficherResultatsPartie();
    }

    private void afficherResultatsPartie() {
        Joueur vainqueur = partie.getVainqueur();
        if (vainqueur != null) {
            ihm.afficherMessage("Le vainqueur est " + vainqueur.getNom() + " avec " + partie.compterPions(vainqueur.getCouleur()) + " pions.");
        } else {
            ihm.afficherMessage("Match nul !");
        }
    }

    private void afficherResultatsFinaux() {
        int partiesGagneesJoueur1 = partie.getPartiesGagnees(partie.getJoueur1());
        int partiesGagneesJoueur2 = partie.getPartiesGagnees(partie.getJoueur2());

        if (partiesGagneesJoueur1 > partiesGagneesJoueur2) {
            ihm.afficherMessage("Le vainqueur final est " + partie.getJoueur1().getNom() + " avec " + partiesGagneesJoueur1 + " parties gagnées.");
        } else if (partiesGagneesJoueur2 > partiesGagneesJoueur1) {
            ihm.afficherMessage("Le vainqueur final est " + partie.getJoueur2().getNom() + " avec " + partiesGagneesJoueur2 + " parties gagnées.");
        } else {
            ihm.afficherMessage("Égalité parfaite !");
        }
    }
}
