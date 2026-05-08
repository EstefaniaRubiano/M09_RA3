import java.io.*;
import java.net.*;

public class ClientXat {

    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;

    public void connecta() throws IOException {
        socket = new Socket(ServidorXat.HOST, ServidorXat.PORT);
        out = new ObjectOutputStream(socket.getOutputStream());
        in = new ObjectInputStream(socket.getInputStream());
        System.out.println("Connectat al servidor");
    }

    public void enviarMissatge(String msg) throws IOException {
        out.writeObject(msg);
        out.flush();
    }

    public void tancarClient() throws IOException {
        if (socket != null && !socket.isClosed()) {
            socket.close();
        }
    }

    public static void main(String[] args) {

        ClientXat client = new ClientXat();

        try {
            client.connecta();
            
            client.tancarClient();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
