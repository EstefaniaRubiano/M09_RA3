import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class ServidorXat {
    public static final int PORT = 9999;
    public static final String HOST = "localhost";
    public static final String MSG_SORTIR = "sortir";
    private ServerSocket serverSocket;

    public void iniciarServidor() throws IOException {
        serverSocket = new ServerSocket(PORT);
        System.out.println("Servidor iniciat...");
    }

    public void pararServidor() throws IOException {
        if (serverSocket != null && !serverSocket.isClosed()) {
            serverSocket.close();
        }
    }

    public String getNom(ObjectInputStream in) throws IOException, ClassNotFoundException {
        return (String) in.readObject();
    }

    public static void main(String[] args) {

        ServidorXat servidor = new ServidorXat();

        try {
            servidor.iniciarServidor();
            Socket clientSocket = servidor.serverSocket.accept();

            System.out.println("Client connectat");
            ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());

            String nomClient = servidor.getNom(in);
            System.out.println("Nom client: " + nomClient);
            FilServidorXat fil = new FilServidorXat(in, nomClient);
            fil.start();
            Scanner scanner = new Scanner(System.in);

            String msg = "";
            while (!msg.equals(MSG_SORTIR)) {
                msg = scanner.nextLine();
                out.writeObject(msg);
                out.flush();
            }

            fil.join();
            clientSocket.close();
            servidor.pararServidor();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
