package nye.hu;

import java.util.Random;
import java.util.Scanner;
import java.io.IOException;

public class Játék {
    private Tábla tábla;
    private char jelenlegiJátékos;
    private String játékosNeve;
    private static final char JÁTÉKOS = 'X'; // Ezek vagyunk mi, a mi szimbólumunk lesz az X.
    private static final char ROBOT = 'O'; // A robot az ellenfelünk, az ő szimbóluma lesz az O.
    private Random random; // Lépés generálása random a robotnak.

    public Játék() {
        tábla = new Tábla();  // Tábla objektum létrehozása itt
        random = new Random();
    }

    public Játék(Tábla tábla, Random random) {
        this.tábla = tábla;
        this.random = random;
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);

        // Betöltjük a táblát fájlból
        System.out.println("Add meg a fájl nevét ahonnad be akarod tölteni a játékot vagy nyomj Entert, ha újat akarsz kezdeni. ");
        String fájlNeve = scanner.nextLine();
        if (!fájlNeve.isEmpty()) {
            try {
                tábla.táblaBetöltése(fájlNeve);
                System.out.println("A tábla betöltődött: " + fájlNeve);
            } catch (IOException e) {
                System.out.println("Hiba történt betöltés közben: " + e.getMessage());
            }
        }

        // A nevünk bekérése.
        System.out.println("Add meg a neved: ");
        játékosNeve = scanner.nextLine();
        jelenlegiJátékos = JÁTÉKOS;

        while (true) {
            tábla.táblaKiírás();

            // Ha mi lépünk, bekér egy oszlopot ahova akarunk rakni.
            if (jelenlegiJátékos == JÁTÉKOS) {
                System.out.println(játékosNeve + " következik. (1-7)");
                int oszlop = scanner.nextInt() - 1;

                if (!tábla.szimbólumLerakása(oszlop, jelenlegiJátékos)) {
                    System.out.println("Érvénytelen lépés!");
                    continue;
                }
            } else { // Ha a robot lép, random rak egy oszlopba.
                int oszlop = randomOszlop();
                tábla.szimbólumLerakása(oszlop, jelenlegiJátékos);
                System.out.println("A robot a " + (oszlop + 1) +". oszlopba rakott."  );
            }

            // Megvizsgáljuk, hogy győzött e valaki.
            if (tábla.győztesLépésE(jelenlegiJátékos)) {
                tábla.táblaKiírás();
                System.out.println(jelenlegiJátékos == JÁTÉKOS ? játékosNeve : "A robot győzött!");
                break;
            }

            // Megvizsgáljuk, hogy tele van e a tábla.
            if (tábla.teleVanE()) {
                tábla.táblaKiírás();
                System.out.println("A játszma döntetlen!");
                break;
            }

            // Következő játékos.
            jelenlegiJátékos = (jelenlegiJátékos == JÁTÉKOS) ? ROBOT : JÁTÉKOS;
        }

        // A nyertes mentése az adatbázisba
        Adatbázis.saveHighScore(játékosNeve, 1); // Mivel egy játékot nyertünk, 1-et adunk a győzelmekhez

        // Kiírjuk a legjobb pontszámokat
        Adatbázis.displayHighScores();

        System.out.println("Add meg a fájl nevét, ahova szeretnéd menteni a játék állását:");
        scanner.nextLine();
        String saveFilename = scanner.nextLine();
        try {
            tábla.táblaMentésFájlba(saveFilename);
            System.out.println("A tábla mentve lett a következő fájlba: " + saveFilename);
        } catch (IOException e) {
            System.out.println("Hiba történt mentés közben: " + e.getMessage());
        }

        scanner.close();
    }



    private int randomOszlop() {
        int oszlop;
        do {
            oszlop = random.nextInt(Tábla.OSZLOPOK); // Random választ egy oszlopot a robot lépéséhez.
        } while (tábla.getFelsőElem(oszlop) != Tábla.ÜRES_MEZŐ); // Ellenőrzi, hogy az oszlop üres-e.
        return oszlop;
    }
}