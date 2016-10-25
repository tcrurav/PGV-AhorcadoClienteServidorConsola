/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package clienteahorcado;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Tiburcio
 */
public class ClienteAhorcado {
    static String HOST = "192.168.1.15";
    static int PUERTO =  2000;
    final static String FIN = "FIN";
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            // TODO code application logic here
            Scanner teclado = new Scanner(System.in);
            Socket sk = new Socket(HOST,PUERTO);
            
            OutputStream os = sk.getOutputStream();
            OutputStreamWriter osw = new OutputStreamWriter(os);
            BufferedWriter bw = new BufferedWriter(osw);
            
            InputStream is = sk.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            
            String datos;
            String captura;
            do{
                System.out.println("Esperando a lo que me diga el Servidor");
                datos = br.readLine();
                if(!datos.equals(FIN)){
                    System.out.println("La palabra a adivinar: "+datos);
                    System.out.println("Dígame la próxima letra");
                    captura = teclado.nextLine();
                    bw.write(captura.charAt(0));
                    bw.newLine();
                    bw.flush();
                    datos = br.readLine();
                    System.out.println("Ha adivinado hasta el momento: "+datos);
                }
            } while(!datos.equals(FIN));
            
        } catch (UnknownHostException ex) {
            Logger.getLogger(ClienteAhorcado.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ClienteAhorcado.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("¡Adivinó la palabra!");
    }
}
