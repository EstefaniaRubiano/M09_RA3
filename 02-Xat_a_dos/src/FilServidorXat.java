import java.io.*;

public class FilServidorXat extends Thread {

    private ObjectInputStream entrada;
    private String nom;

    public FilServidorXat(ObjectInputStream entrada, String nom) {
        this.entrada = entrada;
        this.nom = nom;
    }

    @Override
    public void run() {
        try {
            String missatge;
            while (!(missatge = (String) entrada.readObject()).equals(ServidorXat.MSG_SORTIR)) {
                System.out.println("Missatge ('sortir' per tancar): Rebut: " + missatge);
                System.out.println("Hola " + nom + "!");
            }
            System.out.println("Missatge ('sortir' per tancar): Rebut: " + missatge);
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Connexió amb el client tancada.");
        }
    }
}