package nye.hu;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Random;

import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;

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
    public void testStart() {
        // Mockoljunk egy szituációt, ahol a tábla nem tele és nem nyert senki
        when(mockTábla.teleVanE()).thenReturn(false);
        when(mockTábla.győztesLépésE(anyChar())).thenReturn(false);

        // Teszteljük a start metódust
        játék.start();

        // Ellenőrizzük, hogy a játék lépései megtörténtek
        verify(mockTábla, times(1)).táblaKiírás(); // A tábla kiírása
    }

    @Test
    public void testGyőzelem() {
        // A győztes lépés ellenőrzése, ahol a játékos nyer
        when(mockTábla.győztesLépésE(anyChar())).thenReturn(true);

        // A játék indítása
        játék.start();

        // Ellenőrizzük, hogy a győzelmet megfelelően kijelezte
        verify(mockTábla, times(1)).táblaKiírás();
    }

    @Test
    public void testRobotLépés() {
        // Mockoljunk egy robot lépést
        when(mockRandom.nextInt(Tábla.OSZLOPOK)).thenReturn(3); // Robot választhatja a 4-es oszlopot
        when(mockTábla.szimbólumLerakása(3, 'O')).thenReturn(true);

        // A robot lépése
        játék.start();

        // Ellenőrizzük, hogy a robot lépése megtörtént
        verify(mockTábla, times(1)).szimbólumLerakása(3, 'O');
    }

    @Test
    public void testJátékosLépés() {
        // A játékos lépésének tesztelése
        when(mockTábla.szimbólumLerakása(1, 'X')).thenReturn(true);

        // Játékos lépése
        játék.start();

        // Ellenőrizzük, hogy a játékos a megfelelő oszlopba rakott
        verify(mockTábla, times(1)).szimbólumLerakása(1, 'X');
    }
}