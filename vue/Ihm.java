package vue;

import java.util.Scanner;

public class Ihm {
    private Scanner scanner;

    /**
     * Construit un objet Ihm et initialise le scanner.
     */
    public Ihm() {
        this.scanner = new Scanner(System.in);
    }

    /**
     * Demande à l'utilisateur de saisir le nom d'un joueur.
     **/
    public String demanderNomJoueur(int joueur) {
        System.out.println("Entrez le nom du joueur " + joueur + " : ");
        return scanner.nextLine();
    }

    /**
     * Affiche le plateau de jeu.
     **/
    public void afficherPlateau(char[][] plateau) {
        System.out.println("     A    B    C    D    E    F    G    H");
        for (int i = 0; i < plateau.length; i++) {
            System.out.print(i + 1 + " ");
            for (int j = 0; j < plateau[i].length; j++) {
                switch (plateau[i][j]) {
                    case 'N':
                        System.out.print("\u26AB"); // Pion noir
                        break;
                    case 'B':
                        System.out.print("\u26AA"); // Pion blanc
                        break;
                    default:
                        System.out.print("\uD83D\uDFE9"); // Case verte vide
                        break;
                }
                System.out.print(" ");
            }
            System.out.println();
        }
    }

    /**
     * Demande au joueur courant de saisir son coup.
     **/
    public String demanderCoup(String nomJoueur) {
        System.out.println(nomJoueur + ", à vous de jouer. Saisir une ligne entre 1 et 8 suivie d’une lettre entre A et H (ex : 3 D) ou P pour passer son tour.");
        return scanner.nextLine().toUpperCase().trim();
    }

    /**
     * Affiche un message à l'utilisateur.
     **/
    public void afficherMessage(String message) {
        System.out.println(message);
    }

    /**
     * Demande à l'utilisateur s'il souhaite rejouer une partie.
     **/
    public boolean demanderRejouer() {
        System.out.println("Voulez-vous rejouer une partie ? (O/N)");
        String reponse = scanner.nextLine().toUpperCase();
        return reponse.equals("O");
    }

    /**
     * Demande à l'utilisateur s'il veut jouer contre l'IA.
     **/
    public boolean demanderSiContreIA() {
        System.out.println("Voulez-vous jouer contre l'ordinateur (IA) ? (O/N)");
        String reponse = scanner.nextLine().toUpperCase().trim();
        return reponse.equals("O");
    }


}
