/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package virusservidor;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import virus.model.CartaDto;
import virus.model.JugadorDto;
import virus.model.PartidaDto;
import virusservidor.util.Hilo;

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
            try {
                System.out.println(partida.getMazo().size() + "carta desechada...........................................");
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

                if (null != mensajeRecibido) {
                    switch (mensajeRecibido) {
                        case "jugador":
                            recibirJugador();
                            break;
                        case "carta":
                            recibirCarta();
                            break;
                        case "pedirCartas":
                            enviarCarta();
                            break;
                        case "desecharCarta":
                            desecharCarta();
                            break;
                        case "cambioTurno":
                            cambiarTurno();
                            break;
                        case "Ladron":
                            ladron();
                            break;
                        case "errorMedico":
                            ErrorMedico();
                            break;
                        case "movimientoJugador":
                            movimientoJuego();
                            break;
                        case "partidaFinalizada":
                            finalizarJuego();
                            break;
                        case "mazoTerminado":
                            mazoTerminado();
                            break;
                        case "Transplante":
                            transplante();
                            break;
                        case "Contagio":
                            contagio();
                            break;
                        default:
                            break;
                    }
                }
            } catch (IOException IO) {
                System.out.println(IO.getMessage());
            }
        }
    }
    
    public static void contagio() {
        try {
            DataInputStream entrada;
            ServerSocket ss = new ServerSocket(44440);
            Socket socket = ss.accept();
            entrada = new DataInputStream(socket.getInputStream());
            String padre1 = entrada.readUTF();
            String hijo1 = entrada.readUTF();
            String padre2 = entrada.readUTF();
            String hijo2 = entrada.readUTF();
            partida.getJugadores().stream().forEach((jugador) -> {
                try {
                    Socket socket2 = new Socket(jugador.getIP(), 44440);
                    DataOutputStream mensaje2 = new DataOutputStream(socket2.getOutputStream());
                    OutputStream outputstream = socket2.getOutputStream();
                    mensaje2.writeUTF("Contagio");
                    mensaje2.writeUTF(padre1);
                    mensaje2.writeUTF(hijo1);
                    mensaje2.writeUTF(padre2);
                    mensaje2.writeUTF(hijo2);
                    socket2.close();
                } catch (IOException e) {
                    System.out.println("Error :" + e.getMessage());
                }
            });
            ss.close();
            socket.close();
        } catch (IOException IO) {
            System.out.println(IO.getMessage());
        }
    }

    public static void transplante() {
        try {
            System.out.println("AAAAAAAAAAAAAAAAAAAAAAa");
            DataInputStream entrada;
            ServerSocket ss = new ServerSocket(44440);
            Socket socket = ss.accept(); 
            entrada = new DataInputStream(socket.getInputStream());
            String padre1 = entrada.readUTF();
            String hijo1 = entrada.readUTF();
            String padre2 = entrada.readUTF();
            String hijo2 = entrada.readUTF();
            ss.close();
            socket.close();
            partida.getJugadores().stream().forEach((jugador) -> {
                try {
                    Socket socket2 = new Socket(jugador.getIP(), 44440);
                    DataOutputStream mensaje2 = new DataOutputStream(socket2.getOutputStream());
                    OutputStream outputstream = socket2.getOutputStream();
                    System.out.println("AAAAAAAAAAAAAAAAAAAAAAa");
                    mensaje2.writeUTF("Transplante");
                    mensaje2.writeUTF(padre1);
                    mensaje2.writeUTF(hijo1);
                    mensaje2.writeUTF(padre2);
                    mensaje2.writeUTF(hijo2);
                    socket2.close();
                } catch (IOException e) {
                    System.out.println("Error :" + e.getMessage());
                }
            });
            
        } catch (IOException IO) {
            System.out.println(IO.getMessage());
        }
    }

    public static void ladron() {
        try {
            DataInputStream entrada;
            ServerSocket ss = new ServerSocket(44440);
            Socket socket = ss.accept(); // blocking call, this will wait until a connection is attempted on this port.
            System.out.println("conexión1");
            entrada = new DataInputStream(socket.getInputStream());
            String padre = entrada.readUTF();
            String hijo = entrada.readUTF();
            String IPJugador = entrada.readUTF();
            System.out.println("conexión2");
            partida.getJugadores().stream().forEach((jugador) -> {
                try {
                    Socket socket2 = new Socket(jugador.getIP(), 44440);
                    System.out.println("conexión3");
                    DataOutputStream mensaje2 = new DataOutputStream(socket2.getOutputStream());
                    OutputStream outputstream = socket2.getOutputStream();
                    mensaje2.writeUTF("Ladron");
                    mensaje2.writeUTF(padre);
                    mensaje2.writeUTF(hijo);
                    mensaje2.writeUTF(IPJugador);
                    System.out.println("conexión4");
                    socket2.close();
                } catch (IOException e) {
                    System.out.println("Error :" + e.getMessage());
                }
            });
            ss.close();
            socket.close();
        } catch (IOException IO) {
            System.out.println(IO.getMessage());
        }
    }

    public static void ErrorMedico() {
        try {
            DataInputStream entrada;
            ServerSocket ss = new ServerSocket(44440);
            Socket socket = ss.accept(); // blocking call, this will wait until a connection is attempted on this port.
            System.out.println("conexión1");
            entrada = new DataInputStream(socket.getInputStream());
            String padre = entrada.readUTF();
            System.out.println("conexión2");
            partida.getJugadores().stream().forEach((jugador) -> {
                try {
                    Socket socket2 = new Socket(jugador.getIP(), 44440);
                    System.out.println("conexión3");
                    DataOutputStream mensaje2 = new DataOutputStream(socket2.getOutputStream());
                    OutputStream outputstream = socket2.getOutputStream();
                    mensaje2.writeUTF("errorMedico");
                    mensaje2.writeUTF(padre);
                    System.out.println("conexión4");
                    socket2.close();
                } catch (IOException e) {
                    System.out.println("Error :" + e.getMessage());
                }
            });
            ss.close();
            socket.close();
        } catch (IOException IO) {
            System.out.println(IO.getMessage());
        }

    }

    private static void finalizarJuego() {
        partida = new PartidaDto();
    }

    public static void mazoTerminado() {
        partida.getJugadores().stream().forEach((jugador) -> {
            try {
                Socket socket2 = new Socket(jugador.getIP(), 44440);
                DataOutputStream mensaje2 = new DataOutputStream(socket2.getOutputStream());
                System.out.println("Connected Text!");
                OutputStream outputstream = socket2.getOutputStream();
                mensaje2.writeUTF("mazoTerminado");
                ObjectOutputStream objectoutputstream = new ObjectOutputStream(outputstream);
                socket2.close();
            } catch (IOException e) {
                System.out.println("Error :" + e.getMessage());
            }
        });
    }

    public static void movimientoJuego() {
        try {
            DataInputStream entrada;
            ServerSocket ss = new ServerSocket(44440);

            System.out.println("Esperando Jugador...");
            Socket socket = ss.accept(); // blocking call, this will wait until a connection is attempted on this port.
            System.out.println("Conexión de " + socket + "!");
            entrada = new DataInputStream(socket.getInputStream());

            // get the input stream from the connected socket
            InputStream inputStream = socket.getInputStream();
            // create a DataInputStream so we can read data from it.
            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);

            CartaDto carta = (CartaDto) objectInputStream.readObject();
            System.out.println(carta.getImagen() + "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");

            /*
            Llega el ID del padre y luego el del hijo
             */
            String padre = entrada.readUTF();
            String hijo = entrada.readUTF();
            /*
            Ip del jugador que recibe
             */
            String IPJugador = entrada.readUTF();
            partida.getJugadores().stream().forEach((jugador) -> {
                try {
                    Socket socket2 = new Socket(jugador.getIP(), 44440);
                    DataOutputStream mensaje2 = new DataOutputStream(socket2.getOutputStream());

                    //DataInputStream respuesta = new DataInputStream(socket2.getInputStream());
                    System.out.println("Connected Text!");
                    OutputStream outputstream = socket2.getOutputStream();
                    mensaje2.writeUTF("movimientoJugador");
                    mensaje2.writeUTF(padre);
                    mensaje2.writeUTF(hijo);
                    mensaje2.writeUTF(IPJugador);
                    ObjectOutputStream objectoutputstream = new ObjectOutputStream(outputstream);
                    objectoutputstream.writeObject(carta);

                    socket2.close();
                } catch (IOException e) {
                    System.out.println("Error :" + e.getMessage());
                }
            });

            System.out.println("Cerrando socket");
            ss.close();
            socket.close();

        } catch (IOException | ClassNotFoundException IO) {
            System.out.println(IO.getMessage());
        }
    }

    public static void cambiarTurno() {

        String IP = partida.cambiarTurno().getIP();
        partida.getJugadores().stream().forEach((jugador) -> {
            try {
                Socket socket2 = new Socket(jugador.getIP(), 44440);
                DataOutputStream mensaje2 = new DataOutputStream(socket2.getOutputStream());

                OutputStream outputstream = socket2.getOutputStream();
                mensaje2.writeUTF("cambioTurno");
                mensaje2.writeUTF(IP);
                socket2.close();
                System.out.println("Mensaje Enviado");
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        });

    }

    public static void desecharCarta() {
        try {
            ServerSocket ss = new ServerSocket(44440);

            System.out.println("Esperando Jugador...");
            Socket socket = ss.accept(); // blocking call, this will wait until a connection is attempted on this port.
            System.out.println("Conexión de " + socket + "!");

            // get the input stream from the connected socket
            InputStream inputStream = socket.getInputStream();
            // create a DataInputStream so we can read data from it.
            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);

            CartaDto carta = (CartaDto) objectInputStream.readObject();

            partida.getJugadores().stream().forEach((jugador) -> {
                try {
                    Socket socket2 = new Socket(jugador.getIP(), 44440);
                    DataOutputStream mensaje2 = new DataOutputStream(socket2.getOutputStream());

                    //DataInputStream respuesta = new DataInputStream(socket2.getInputStream());
                    System.out.println("Connected Text!");
                    OutputStream outputstream = socket2.getOutputStream();
                    mensaje2.writeUTF("cartaDesechada");
                    ObjectOutputStream objectoutputstream = new ObjectOutputStream(outputstream);
                    objectoutputstream.writeObject(carta);

                    socket2.close();
                } catch (IOException e) {
                    System.out.println("Error :" + e.getMessage());
                }
            });

            System.out.println("Cerrando socket");
            ss.close();
            socket.close();

        } catch (IOException | ClassNotFoundException IO) {
            System.out.println(IO.getMessage());
        }
    }

    public static void enviarCarta() {
        try {
            ServerSocket ss = new ServerSocket(44440);
            System.out.println("Esperando Conexion Jugador...");
            Socket socket = ss.accept(); // blocking call, this will wait until a connection is attempted on this port.
            System.out.println("Conexión de " + socket + "!");
            OutputStream outputstream = socket.getOutputStream();
            ObjectOutputStream objectoutputstream = new ObjectOutputStream(outputstream);
            CartaDto carta = partida.getCarta();

            if (carta != null) {
                objectoutputstream.writeObject(carta);
            } else {
                //Envia null para que me devuelva las cartas
                System.out.println("XDDD " + carta);
                objectoutputstream.writeObject(carta);
                InputStream inputStream = socket.getInputStream();
                // create a DataInputStream so we can read data from it.
                ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
                try {
                    ArrayList<CartaDto> cartas = (ArrayList<CartaDto>) objectInputStream.readObject();
                    Collections.shuffle(cartas);
                    Collections.shuffle(cartas);
                    partida.setMazo(cartas);
                    objectoutputstream.writeObject(partida.getCarta());
                } catch (IOException | ClassNotFoundException e) {
                    System.out.println(e.getMessage());
                }
            }

            System.out.println("Cerrando socket");
            ss.close();
            socket.close();
        } catch (IOException IO) {
            System.out.println(IO.getMessage());
        }
    }

    public static void recibirJugador() {
        try {
            ServerSocket ss = new ServerSocket(44440);
            DataOutputStream salida;

            System.out.println("Esperando Jugador...");
            Socket socket = ss.accept(); // blocking call, this will wait until a connection is attempted on this port.
            salida = new DataOutputStream(socket.getOutputStream());
            System.out.println("Conexión de " + socket + "!");

            // get the input stream from the connected socket
            InputStream inputStream = socket.getInputStream();
            // create a DataInputStream so we can read data from it.
            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);

            OutputStream outputstream = socket.getOutputStream();
            OutputStream outputlista = socket.getOutputStream();

            ObjectOutputStream objectoutputstream = new ObjectOutputStream(outputstream);

            JugadorDto Jugador = (JugadorDto) objectInputStream.readObject();

            System.out.println("Mensajes:");
            System.out.println(Jugador.toString());

            ArrayList<CartaDto> mazo = partida.getCartasPorJugador();
            Jugador.setMazo(mazo);
            partida.getJugadores().add(Jugador);

            System.out.println("Entregando Cartas a " + Jugador.getNombre());
            objectoutputstream.writeObject(mazo);

            ObjectOutputStream objectoutlista = new ObjectOutputStream(outputlista);
            VerificarPartida(salida, socket, ss, objectoutlista); // Verificamos la cantidad de jugadores que existen hasta el momento en la partida

            System.out.println("Cerrando socket");
            ss.close();
            socket.close();

        } catch (IOException | ClassNotFoundException IO) {
            System.out.println(IO.getMessage());
        }
    }

    public static void recibirCarta() {
        try {
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
        } catch (Exception IO) {

        }
    }

    public static void VerificarPartida(DataOutputStream salida, Socket socket, ServerSocket serverSocket, ObjectOutputStream objectoutlista) throws IOException {
        if (partida.getJugadores().size() == 6) {
            for (JugadorDto jugador : partida.getJugadores()) {
                socket = new Socket(jugador.getIP(), 44440);
                salida = new DataOutputStream(socket.getOutputStream());
                objectoutlista = new ObjectOutputStream(socket.getOutputStream());
                salida.writeUTF("Partida Lista");
                objectoutlista.writeObject(partida.getJugadores());
                Hilo.finalizado = true;
            }
        } else if (partida.getJugadores().size() == 3) {
            Hilo hilo = new Hilo();
            hilo.correrHilo(salida, socket, serverSocket, partida, objectoutlista);
        }
    }
}
