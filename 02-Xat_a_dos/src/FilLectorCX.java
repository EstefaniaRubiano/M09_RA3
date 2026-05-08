import java.io.ObjectInputStream;

public class FilLectorCX extends Thread {

    private ObjectInputStream in;

    public FilLectorCX(ObjectInputStream in) {
        this.in = in;
    }

    @Override
    public void run() {
        try {
            String msg = "";
            while (!msg.equals(ServidorXat.MSG_SORTIR)) {
                msg = (String) in.readObject();
                System.out.println("Servidor: " + msg);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
