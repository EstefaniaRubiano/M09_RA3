import java.io.*;
import java.net.*;
import java.util.Scanner;

public class ClientXat {

    static final String HOST = "localhost";
    static final int PORT = 9999;
    static final String MSG_SORTIR = "sortir";

    private Socket socket;
    private ObjectOutputStream sortida;
    private ObjectInputStream entrada;

    public void connecta() throws IOException {
        socket = new Socket(HOST, PORT);
        System.out.println("Client connectat a " + HOST + ":" + PORT);
        sortida = new ObjectOutputStream(socket.getOutputStream());
        entrada = new ObjectInputStream(socket.getInputStream());
        System.out.println("Flux d'entrada i sortida creat.");
    }

    public void enviarMissatge(String missatge) throws IOException {
        sortida.writeObject(missatge);
        sortida.flush();
    }

    public void tancarClient() throws IOException {
        if (socket != null && !socket.isClosed()) {
            socket.close();
        }
    }

    public static void main(String[] args) throws Exception {
        ClientXat client = new ClientXat();
        client.connecta();

        // Crear i iniciar el fil lector
        FilLectorCX filLector = new FilLectorCX(client.entrada);
        System.out.println("Missatge ('sortir' per tancar): Fil de lectura iniciat");
        filLector.start();

        // Llegir el nom del servidor i enviarlo
        Scanner scanner = new Scanner(System.in);
        String linia;
        while (scanner.hasNextLine()) {
            linia = scanner.nextLine();
            if (linia.equals(MSG_SORTIR)) {
                System.out.println("Enviant missatge: " + linia);
                client.enviarMissatge(linia);
                break;
            }
            System.out.println("Enviant missatge: " + linia);
            client.enviarMissatge(linia);
        }

        scanner.close();
        System.out.println("Tancant client...");
        client.tancarClient();
        System.out.println("Client tancat.");
    }
}