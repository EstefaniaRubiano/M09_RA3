import java.io.*;

public class FilLectorCX extends Thread {

    private ObjectInputStream entrada;

    public FilLectorCX(ObjectInputStream entrada) {
        this.entrada = entrada;
    }

    @Override
    public void run() {
        try {
            String missatge;
            while (!(missatge = (String) entrada.readObject()).equals(ClientXat.MSG_SORTIR)) {
                System.out.println("Missatge ('sortir' per tancar): Rebut: " + missatge);
            }
            System.out.println("Missatge ('sortir' per tancar): Rebut: " + missatge);
            System.out.println("El servidor ha tancat la connexió.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("El servidor ha tancat la connexió.");
        }
    }
}