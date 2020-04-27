/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package virusservidor;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import static javafx.beans.binding.Bindings.and;
import virus.model.CartaDto;
import virus.model.JugadorDto;
import virus.model.PartidaDto;

/**
 *
 * @author Jose Pablo Bermudez
 */
public class VirusServidor {

    /**
     * @param args the command line arguments
     */
    public static PartidaDto partida;
    public static void main(String[] args) { 
        // En este método vamos a esperar una señal del cliente para saber que estamos recibiendo(Jugador, Carta)
        DataInputStream entrada;
        DataOutputStream salida;
        Socket socket;
        ServerSocket serverSocket;
        String mensajeRecibido;
        partida = new PartidaDto();
        while (true) {
            try{
            serverSocket = new ServerSocket(44440);
            System.out.println("Esperando una conexión...");
            socket = serverSocket.accept();
            System.out.println("Un cliente se ha conectado...");
            // Para los canales de entrada y salida de datos
            entrada = new DataInputStream(socket.getInputStream());
            
            salida = new DataOutputStream(socket.getOutputStream());
            
            System.out.println("Confirmando conexion al cliente....");
            
            // Para recibir el mensaje
            mensajeRecibido = entrada.readUTF();
            System.out.println(mensajeRecibido);
            salida.writeUTF("Recibido");
            serverSocket.close();
            
            if("jugador".equals(mensajeRecibido)){
                recibirJugador();   
            }
            else if("carta".equals(mensajeRecibido)){
                recibirCarta();
            }
            
            }catch(Exception IO){
            }
        }
    }

    public static void recibirJugador(){
        try{
            ServerSocket ss = new ServerSocket(44440);
            System.out.println("Esperando Jugador...");
            Socket socket = ss.accept(); // blocking call, this will wait until a connection is attempted on this port.
            System.out.println("Conexión de " + socket + "!");

            // get the input stream from the connected socket
            InputStream inputStream = socket.getInputStream();
            // create a DataInputStream so we can read data from it.
            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);

            JugadorDto Jugador = (JugadorDto) objectInputStream.readObject();
            System.out.println("Mensajes:");
            System.out.println(Jugador.toString());
            
            partida.getJugadores().add(Jugador);
            System.out.println("Cerrando socket");
            ss.close();
            socket.close();
        }catch(Exception IO){

        }
    }
    
    public static void recibirCarta(){
        try{
            ServerSocket ss = new ServerSocket(44440);
            System.out.println("Esperando Carta...");
            Socket socket = ss.accept(); // blocking call, this will wait until a connection is attempted on this port.
            System.out.println("Conexión de " + socket + "!");

            // get the input stream from the connected socket
            InputStream inputStream = socket.getInputStream();
            // create a DataInputStream so we can read data from it.
            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);

            CartaDto Carta = (CartaDto) objectInputStream.readObject();
            System.out.println("Mensajes:");
            System.out.println(Carta.toString());
            
            partida.getMazo().add(Carta);
            System.out.println("Cerrando socket");
            ss.close();
            socket.close();
        }catch(Exception IO){
        }
    }
}