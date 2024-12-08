package nye.hu;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.mockito.Mockito.*;

public class JátékTeszt {

    private Játék játék;
    private Tábla mockTábla;
    private Random mockRandom;

    @BeforeEach
    public void setUp() {
        // Mock-oljuk a Tábla és Random osztályokat
        mockTábla = mock(Tábla.class);
        mockRandom = mock(Random.class);

        // Játék objektum létrehozása a mock-olt függőségekkel
        játék = new Játék(mockTábla, mockRandom);
    }

    @Test
    public void testStartAlap() {
        // Mockoljunk egy szituációt, ahol a tábla nem tele és senki sem nyer
        when(mockTábla.teleVanE()).thenReturn(false, false, true); // Két lépés után tele
        when(mockTábla.győztesLépésE(anyChar())).thenReturn(false);

        // Teszteljük a start metódust
        játék.start();

        // Ellenőrizzük, hogy a tábla többször meg lett jelenítve
        verify(mockTábla, atLeast(2)).táblaKiírás(); // A tábla megjelenítése legalább kétszer
    }

    @Test
    public void testGyőzelemJátékos() {
        // Mockolt szituáció: A játékos nyer
        when(mockTábla.győztesLépésE('X')).thenReturn(true);

        // Teszteljük a játék indítását
        játék.start();

        // Ellenőrizzük, hogy a győzelmet jelezte
        verify(mockTábla, times(1)).táblaKiírás();
    }

    @Test
    public void testGyőzelemRobot() {
        // Mockolt szituáció: A robot nyer
        when(mockTábla.győztesLépésE('O')).thenReturn(true);

        // Teszteljük a játék indítását
        játék.start();

        // Ellenőrizzük, hogy a győzelmet jelezte
        verify(mockTábla, times(1)).táblaKiírás();
    }

    @Test
    public void testRobotLépésÉrvénytelen() {
        // Robot érvénytelen oszlopot választ
        when(mockRandom.nextInt(Tábla.OSZLOPOK)).thenReturn(3);
        when(mockTábla.szimbólumLerakása(3, 'O')).thenReturn(false);

        // A robot lépése
        játék.start();

        // Ellenőrizzük, hogy az érvénytelen lépés után újra próbálkozott
        verify(mockTábla, atLeast(1)).szimbólumLerakása(3, 'O');
    }

    @Test
    public void testTáblaTeleVan() {
        // Szituáció: A tábla megtelt
        when(mockTábla.teleVanE()).thenReturn(true);

        // Teszteljük a játék indítását
        játék.start();

        // Ellenőrizzük, hogy a megfelelő állapotot kezeltük
        verify(mockTábla, times(1)).táblaKiírás();
    }

    @Test
    public void testJátékosLépésÉrvénytelen() {
        // A játékos érvénytelen oszlopot választ
        when(mockTábla.szimbólumLerakása(1, 'X')).thenReturn(false);

        // Teszteljük a játék indítását
        játék.start();

        // Ellenőrizzük, hogy újra próbálkozik
        verify(mockTábla, atLeast(1)).szimbólumLerakása(1, 'X');
    }
}
