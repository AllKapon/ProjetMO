package controleur;

import modele.Joueur;
import modele.JoueurIA;
import modele.Partie;
import vue.Ihm;

import java.util.ArrayList;
import java.util.List;

public class Controleur {
    private Ihm ihm;
    private Partie partie;

    public Controleur(Ihm ihm) {
        this.ihm = ihm;
    }

    public void jouer() {
        String nomJoueur1 = ihm.demanderNomJoueur(1);
        boolean contreIA = ihm.demanderSiContreIA(); // Ajout d'une méthode pour choisir le mode IA ou 2 joueurs

        Joueur joueur1 = new Joueur(nomJoueur1, 'N');
        Joueur joueur2 = contreIA ? new JoueurIA("IA", 'B') : new Joueur(ihm.demanderNomJoueur(2), 'B');

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

            // Vérifier s'il y a des coups possibles
            if (coupsPossibles.isEmpty()) {
                ihm.afficherMessage(joueurCourant.getNom() + " ne peut pas jouer et passe son tour.");
                partie.passerTour();
                continue;
            }

            String coup;
            boolean coupValide = false;

            if (joueurCourant instanceof JoueurIA) {
                // L'IA choisit un coup automatiquement
                JoueurIA ia = (JoueurIA) joueurCourant;
                int[] coupIA = ia.choisirCoup(coupsPossibles);
                coup = (coupIA[0] + 1) + " " + (char) ('A' + coupIA[1]);

                ihm.afficherMessage("L'IA joue : " + coup);
                coupValide = partie.jouerCoup(coup);
            } else {
                // Demande du coup au joueur humain
                do {
                    coup = ihm.demanderCoup(joueurCourant.getNom());

                    // Vérification du format du coup (ligne et colonne séparées)
                    if (!coup.matches("[1-8] [A-H]") && !coup.equals("P")) {
                        ihm.afficherMessage("Format du coup invalide. Exemple attendu : 3 D ou P pour passer.");
                        continue;
                    }

                    if (coup.equals("P")) {
                        partie.passerTour();
                        coupValide = true;
                    } else {
                        coupValide = partie.jouerCoup(coup);
                        if (!coupValide) {
                            ihm.afficherMessage("Coup invalide. Veuillez réessayer.");
                        }
                    }
                } while (!coupValide);
            }

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
