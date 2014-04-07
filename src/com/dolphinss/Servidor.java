package com.dolphinss;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Clase principal del servidor. Tiene el m�todo main().
 * 
 * @version 1.0
 * @author mloobo@gmail.com
 *
 */
public class Servidor {
	public static final String nombre="Dolphin Streaming Server";
	public static final String version="0.57";
	public static final String plataforma="Windows";
	public static final String release="XP";
	
	/**
	 * El puerto por el que escucha el servidor y a trav�s del cual los clientes
	 * se deben conectar. Es el puerto por defecto para el protocolo RTSP.
	 */
	public static final int PUERTO=554;
	
	/**
	 * Es el puerto base a partir del cual se "abrir�n" m�s puertos a medida que se contecten
	 * clientes.
	 */
	public static final int PUERTO_BASE=3000;
	/**
	 * Define los 2 puertos (iniciales) que utilizar� el servidor para transmitir los recursos solicitados.
	 * Estos valores iran en aumento (en funci�n del valor de la variable "unidadAumentarPuertos") a 
	 * medida que se conecten m�s clientes.
	 */
	public static int[] PUERTO_RTSP_RTP={PUERTO_BASE, (PUERTO_BASE+1)};
	/**
	 * Define la unidad en la que deben aumentar los puertos a ser abiertos cuando se conectan m�s
	 * clientes.
	 */
	public static final int unidadAumentarPuertos=PUERTO_RTSP_RTP.length;
	
	/**
	 * Define el puerto por el que se transmitir� el audio (en el caso que exista tal pista). 
	 * Este puerto se indicar� al cliente en el SDP, inclu�do en la respuesta a un DESCRIBE.
	 */
	public static final int PUERTO_CLIENTE_AUDIO=2000;
	/**
	 * Define el puerto por el que se transmitir� el v�deo (en el caso que exista tal pista). 
	 * Este puerto se indicar� al cliente en el SDP, inclu�do en la respuesta a un DESCRIBE.
	 */
	public static final int PUERTO_CLIENTE_VIDEO=4000;
	
	/**
	 * Directorio donde se almacenan los archvios a reproducir por parte del servidor.
	 */
	public static final String DIR_MULTIMEDIOS="../";
	
	/**
	 * La IP del servidor. Se carga autom�ticamente al iniciarse.
	 */
	public static String IP_SERVIDOR="";
	
	/**
	 * Constructor.
	 */
	public Servidor(){
		try {
			IP_SERVIDOR="93.156.241.39";
			IP_SERVIDOR=InetAddress.getLocalHost().getHostAddress();
		} 
		catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * M�todo que abre el socket servidor y se mete en un bucle infinito para aceptar 
	 * las peticiones que le lleguen de clientes.
	 * Para cada cliente, se crea un objeto HiloCliente y se lanza (es un Thread).
	 */
	public void run(){
		try {
			ServerSocket socket = new ServerSocket(PUERTO);
			System.out.println("Arranco el servidor");
			
			//lanzamos el icono para la barra del reloj
//			new SystemTrayIcon();
			
			while(true){
				System.out.println("Esperando clientes...");
				Socket s=socket.accept();//para cliente N
				System.out.println("Cliente ha conectado con ip "+s.getInetAddress().getHostAddress());
				HiloCliente h=new HiloCliente(s);
				h.start();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("[Servidor]->Excepcion al abrir el socket");
			e.printStackTrace();
		}
	}
	
	/**
	 * M�todo main para iniciar la aplicaci�n. Instancia un objeto de la clase Servidor y
	 * ejecuta su m�todo run().
	 * @param args
	 */
	public static void main(String[] args){
		Servidor s=new Servidor();
		s.run();
	}
}
