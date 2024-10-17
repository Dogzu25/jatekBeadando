package nye.hu;


public class App {
    public static void main(String[] args) {
        Játék játék = new Játék();
        játék.start();

    }
}

/*

Az én connect4 játékom a következőket tudja:
- Fájlból beolvasni egy táblát.
- A robot(számítógép) lépései random generálódnak.
- A program ellenőrzi minden lépés után, hogy nyert e valaki.
- Lehetséges a döntetlen és győzelem a játék során.
- A játék elején elkéri a nevünket és így fog szólítani minket.
- Írja, hogy a robot melyik oszlopba rakott szimbólumot.

 */
