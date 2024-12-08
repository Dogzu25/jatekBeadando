package nye.hu;

import org.junit.jupiter.api.Test;
import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class TáblaTest {

    @Test
    void tesztÜresTáblaInicializálása() {
        Tábla tábla = new Tábla();
        for (int sor = 0; sor < Tábla.SOROK; sor++) {
            for (int oszlop = 0; oszlop < Tábla.OSZLOPOK; oszlop++) {
                assertEquals(Tábla.ÜRES_MEZŐ, tábla.getFelsőElem(oszlop));
            }
        }
    }

    @Test
    void tesztSzimbólumLerakásaÉrvényes() {
        Tábla tábla = new Tábla();
        assertTrue(tábla.szimbólumLerakása(0, 'X'));
        assertEquals('X', tábla.getFelsőElem(0));
    }

    @Test
    void tesztSzimbólumLerakásaÉrvénytelen() {
        Tábla tábla = new Tábla();
        for (int i = 0; i < Tábla.SOROK; i++) {
            tábla.szimbólumLerakása(0, 'X');
        }
        assertFalse(tábla.szimbólumLerakása(0, 'O')); // Oszlop tele
    }

    @Test
    void tesztGyőztesLépésSorban() {
        Tábla tábla = new Tábla();
        for (int i = 0; i < 4; i++) {
            tábla.szimbólumLerakása(i, 'X');
        }
        assertTrue(tábla.győztesLépésE('X'));
    }

    @Test
    void tesztGyőztesLépésOszlopban() {
        Tábla tábla = new Tábla();
        for (int i = 0; i < 4; i++) {
            tábla.szimbólumLerakása(0, 'X');
        }
        assertTrue(tábla.győztesLépésE('X'));
    }

    @Test
    void tesztGyőztesLépésÁtlóbanBalrólJobbra() {
        Tábla tábla = new Tábla();
        tábla.szimbólumLerakása(0, 'X');
        tábla.szimbólumLerakása(1, 'O'); tábla.szimbólumLerakása(1, 'X');
        tábla.szimbólumLerakása(2, 'O'); tábla.szimbólumLerakása(2, 'O'); tábla.szimbólumLerakása(2, 'X');
        tábla.szimbólumLerakása(3, 'O'); tábla.szimbólumLerakása(3, 'O'); tábla.szimbólumLerakása(3, 'O'); tábla.szimbólumLerakása(3, 'X');
        assertTrue(tábla.győztesLépésE('X'));
    }

    @Test
    void tesztGyőztesLépésÁtlóbanJobbrólBalra() {
        Tábla tábla = new Tábla();
        tábla.szimbólumLerakása(3, 'X');
        tábla.szimbólumLerakása(2, 'O'); tábla.szimbólumLerakása(2, 'X');
        tábla.szimbólumLerakása(1, 'O'); tábla.szimbólumLerakása(1, 'O'); tábla.szimbólumLerakása(1, 'X');
        tábla.szimbólumLerakása(0, 'O'); tábla.szimbólumLerakása(0, 'O'); tábla.szimbólumLerakása(0, 'O'); tábla.szimbólumLerakása(0, 'X');
        assertTrue(tábla.győztesLépésE('X'));
    }

    @Test
    void tesztTeleTábla() {
        Tábla tábla = new Tábla();
        for (int oszlop = 0; oszlop < Tábla.OSZLOPOK; oszlop++) {
            for (int sor = 0; sor < Tábla.SOROK; sor++) {
                tábla.szimbólumLerakása(oszlop, 'X');
            }
        }
        assertTrue(tábla.teleVanE());
    }

    @Test
    void tesztTáblaMentéseÉsBetöltése() throws IOException {
        Tábla tábla = new Tábla();
        tábla.szimbólumLerakása(0, 'X');
        tábla.szimbólumLerakása(1, 'O');

        String tesztFájl = "teszt_tábla.txt";
        tábla.táblaMentésFájlba(tesztFájl);

        Tábla újTábla = new Tábla();
        újTábla.táblaBetöltése(tesztFájl);

        assertEquals('X', újTábla.getFelsőElem(0));
        assertEquals('O', újTábla.getFelsőElem(1));

        // Tesztfájl törlése
        File fájl = new File(tesztFájl);
        assertTrue(fájl.delete());
    }
}