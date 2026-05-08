
import java.io.ObjectInputStream;

public class FilServidorXat extends Thread {

    private final ObjectInputStream in;
    private final String nom;

    public FilServidorXat(ObjectInputStream in, String nom) {
        this.in = in;
        this.nom = nom;
    }

    @Override
    public void run() {
        try {
            String msg = "";
            while (!msg.equals(ServidorXat.MSG_SORTIR)) {
                msg = (String) in.readObject();
                System.out.println(nom + ": " + msg);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}