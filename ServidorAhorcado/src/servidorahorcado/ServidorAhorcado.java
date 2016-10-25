/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package servidorahorcado;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Tiburcio
 */
public class ServidorAhorcado {
    final static int PUERTO = 2000;
    final static String FIN = "FIN";
    final static String palabra = "colchoneta";
    static String adivinadoHastaElMomento;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            // TODO code application logic here

            ServerSocket skServer = new ServerSocket(PUERTO);
            System.out.println("Esperando a que se conecte el cliente");
            Socket sk = skServer.accept();
            
            System.out.println("Cliente conectado");
            
            OutputStream os = sk.getOutputStream();
            OutputStreamWriter osw = new OutputStreamWriter(os);
            BufferedWriter bw = new BufferedWriter(osw);
            
            InputStream is = sk.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);

            //Empieza el trasiego de información
            adivinadoHastaElMomento = inicializarAdivinadoHastaElMomento(palabra);
            String datos;
            String textoAEnviar = null;
            do {
                System.out.println("se va a enviar: "+adivinadoHastaElMomento);
                bw.write(adivinadoHastaElMomento);
                bw.newLine();
                bw.flush();
                datos=br.readLine();
                System.out.println("Acaba de recibir:"+datos+".");
                adivinadoHastaElMomento = 
                        actualizarAdivinadoHastaElMomento(palabra,adivinadoHastaElMomento,datos);
                
                if (adivinadoHastaElMomento.equals(palabra)){
                    textoAEnviar = FIN;
                } else {
                    textoAEnviar = adivinadoHastaElMomento;
                }
                bw.write(adivinadoHastaElMomento);
                bw.newLine();
                bw.flush();
            } while(!textoAEnviar.equals(FIN));
            
            bw.write(FIN);
            bw.newLine();
            bw.flush();
            
            sk.close();
        } catch (IOException ex) {
            Logger.getLogger(ServidorAhorcado.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        System.out.println("El cliente adivinó la palabra");
    }
    private static String actualizarAdivinadoHastaElMomento(String palabra, String adivinadoHastaElMomento, String letra){
        String resultado = "";
        for (int i=0;i<palabra.length();i++){
            if(palabra.charAt(i)==letra.charAt(0)){
                resultado += letra.charAt(0);
            } else {
                resultado += adivinadoHastaElMomento.charAt(i);
            } 
        }
        return resultado;
    }
    private static String inicializarAdivinadoHastaElMomento(String palabra){
        String resultado = "";
        for(int i=0; i<palabra.length();i++){
            resultado += "-";
        }
        return resultado;
    }
}
