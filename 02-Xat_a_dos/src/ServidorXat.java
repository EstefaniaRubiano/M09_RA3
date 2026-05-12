import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ServidorXat {
    public static final int PORT = 9999;
    public static final String HOST = "localhost";
    public static final String MSG_SORTIR = "sortir";
    private ServerSocket serverSocket;

    public void iniciarServidor() throws IOException {
        serverSocket = new ServerSocket(PORT);
        System.out.println("Servidor iniciat a " + HOST + ":" + PORT);
    }

    public void pararServidor() throws IOException {
        if (serverSocket != null && !serverSocket.isClosed()) {
            serverSocket.close();
        }
    }

    public String getNom(ObjectInputStream entrada, ObjectOutputStream sortida) throws IOException, ClassNotFoundException {
        sortida.writeObject("Escriu el teu nom:");
        sortida.flush();
        return (String) entrada.readObject();
    }

    public static void main(String[] args) throws Exception {

        ServidorXat servidor = new ServidorXat();
        servidor.iniciarServidor();

        Socket clientSocket = servidor.serverSocket.accept();
        System.out.println("Client connectat: " + clientSocket.getInetAddress());

        ObjectOutputStream sortida = new ObjectOutputStream(clientSocket.getOutputStream());
        ObjectInputStream entrada = new ObjectInputStream(clientSocket.getInputStream());

        String nom = servidor.getNom(entrada, sortida);
        System.out.println("Nom rebut: " + nom);

        // Crear i iniciar el fil del servidor
        FilServidorXat fil = new FilServidorXat(entrada, nom);
        System.out.println("Fil de xat creat.");
        fil.start();
        System.out.println("Fil de " + nom + " iniciat");

        // Enviar missatges desde la consola
        BufferedReader consola = new BufferedReader(new InputStreamReader(System.in));
        String missatge;
        while (!(missatge = consola.readLine()).equals(MSG_SORTIR)) {
            System.out.println("Missatge ('sortir' per tancar): " + missatge);
            sortida.writeObject(missatge);
            sortida.flush();
        }
        System.out.println("Missatge ('sortir' per tancar): " + missatge);
        sortida.writeObject(MSG_SORTIR);
        sortida.flush();

        // Esperar que el fil acabi
        fil.join();
        System.out.println("Fil de xat finalitzat.");
        System.out.println(missatge);

        clientSocket.close();
        servidor.pararServidor();
        System.out.println("Servidor aturat.");
    }
}
