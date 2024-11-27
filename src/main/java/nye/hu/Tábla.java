package nye.hu;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedWriter;
import java.io.FileWriter;


public class Tábla {
    public static final int SOROK = 6;
    public static final int OSZLOPOK = 7;
    public static final char ÜRES_MEZŐ = '.';

    private char[][] tábla;

    // Itt csináljuk meg a táblát.
    public Tábla() {
        tábla = new char[SOROK][OSZLOPOK];
        for (int i = 0; i < SOROK; i++) {
            for (int j = 0; j < OSZLOPOK; j++) {
                tábla[i][j] = ÜRES_MEZŐ;
            }
        }
    }

    // Betölti a táblát egy fájlból
    public void táblaBetöltése(String fájlNeve) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(fájlNeve))) {
            for (int i = 0; i < SOROK; i++) {
                String line = reader.readLine();
                if (line != null) {
                    tábla[i] = line.toCharArray();
                }
            }
        }
    }


    // Kiírjuk a táblát.
    public void táblaKiírás() {
        for (int i = 0; i < SOROK; i++) {
            for (int j = 0; j < OSZLOPOK; j++) {
                System.out.print(tábla[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    public void táblaMentésFájlba(String fájlNeve) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fájlNeve))) {
            for (int i = 0; i < SOROK; i++) {
                for (int j = 0; j < OSZLOPOK; j++) {
                    writer.write(tábla[i][j]);
                }
                writer.newLine(); // Minden sor után új sor
            }
        }
    }

    // Szimbólumunk elhelyezése az oszlopban.
    public boolean szimbólumLerakása(int oszlop, char játékos) {
        if (oszlop < 0 || oszlop >= OSZLOPOK || tábla[0][oszlop] != ÜRES_MEZŐ) {
            return false; // Érvénytelen a lépés, ha a mező nem üres.
        }
        for (int i = SOROK - 1; i >= 0; i--) {
            if (tábla[i][oszlop] == ÜRES_MEZŐ) {
                tábla[i][oszlop] = játékos;
                return true;
            }
        }
        return false;
    }

    // Ezen lehetőségek alapján tudunk nyerni.
    public boolean győztesLépésE(char játékos) {


        // Ellenőrzni, hogy összegyűlt e 4 azonos szimbólum egy sorban.
        for (int sor = 0; sor < SOROK; sor++) {
            for (int oszlop = 0; oszlop < OSZLOPOK - 3; oszlop++) {
                if (tábla[sor][oszlop] == játékos && tábla[sor][oszlop + 1] == játékos &&
                        tábla[sor][oszlop + 2] == játékos && tábla[sor][oszlop + 3] == játékos) {
                    return true;
                }
            }
        }

        // Ellenőrzi, hogy összegyűlt e 4 azonos szimbólum egy oszlopban.
        for (int sor = 0; sor < SOROK - 3; sor++) {
            for (int oszlop = 0; oszlop < OSZLOPOK; oszlop++) {
                if (tábla[sor][oszlop] == játékos && tábla[sor + 1][oszlop] == játékos &&
                        tábla[sor + 2][oszlop] == játékos && tábla[sor + 3][oszlop] == játékos) {
                    return true;
                }
            }
        }

        // Ellenőrzi, hogy balról jobbra felfelé összegyűlt e 4 azonos szimbólum.
        for (int sor = 0; sor < SOROK - 3; sor++) {
            for (int oszlop = 0; oszlop < OSZLOPOK - 3; oszlop++) {
                if (tábla[sor][oszlop] == játékos && tábla[sor + 1][oszlop + 1] == játékos &&
                        tábla[sor + 2][oszlop + 2] == játékos && tábla[sor + 3][oszlop + 3] == játékos) {
                    return true;
                }
            }
        }

        // Ellenőrzi, hogy balról jobbra lefelé összegyűlt e 4 azonos szimbólum.
        for (int sor = 3; sor < SOROK; sor++) {
            for (int oszlop = 0; oszlop < OSZLOPOK - 3; oszlop++) {
                if (tábla[sor][oszlop] == játékos && tábla[sor - 1][oszlop + 1] == játékos &&
                        tábla[sor - 2][oszlop + 2] == játékos && tábla[sor - 3][oszlop + 3] == játékos) {
                    return true;
                }
            }
        }

        return false; // Ebben az esetben még senki sem nyert.
    }

    // Ellenőrzi, hogy a tábla tele van e.
    public boolean teleVanE() {
        for (int i = 0; i < OSZLOPOK; i++) {
            if (tábla[0][i] == ÜRES_MEZŐ) {
                return false;
            }
        }
        return true; // Ebben az esetben minden oszlop betelt.
    }

    // Ez a metódus visszaadja egy oszlopnak a legfelső elemét.
    public char getFelsőElem(int oszlop) {
        return tábla[0][oszlop];
    }
}

