/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.io.*;
import java.util.Scanner;
import java.util.concurrent.*;

import modules.Normalization;
import modules.Searcher;
import modules.URL;

/**
 *
 * @author kcorr
 */
public class main {

    /**
     * @param args the command line arguments
     * @throws IOException
     */

    
    public static void main(final String[] args) throws Exception {
        Normalization aux = new Normalization();  
        URL path = new URL();
        Scanner teclado = new Scanner(System.in);
        int intro=1;

        while (intro==1 || intro==2) {
            System.out.println("\n1 para indexar\n2 para buscar\n0 para salir");
            intro = Integer.valueOf(teclado.nextLine());
            if(intro==1){
                
                    // Index-------------------------------------------------------
                System.out.println("\nDesea indexar un documento o actualizar un index?\n1 para indexar\n2 para actualizar\n3 para salir");
                int index=Integer.valueOf(teclado.nextLine());
                if(index==1){
                    System.out.println("\nIngrese la colección que desea ingresar, escribiendo p ó g y el número 1 ó 2");
                    System.out.println("Ejemplo: p1");
                    String data = teclado.nextLine();
                    if("p1".equals(data) || "p2".equals(data) ||"g1".equals(data) || "g2".equals(data) ){
                        String pathDir = path.getPath(data); 
                        int numToIndex = path.getNum(data);
    
                        long startTime = System.nanoTime();
                        aux.startIndization(pathDir,numToIndex);
                        aux.indexer.createIndex(numToIndex);
                        long stopTime = System.nanoTime();
                        long realTime = stopTime - startTime;
                        realTime = TimeUnit.SECONDS.convert(realTime, TimeUnit.NANOSECONDS);
                        if(realTime<=60){
                            System.out.println("El tiempo de indexación es de: "+realTime+" segundos");
                        }else{
                            System.out.println("El tiempo de indexación es de: "+TimeUnit.SECONDS.toSeconds(TimeUnit.SECONDS.toMinutes(realTime))+" minutos");
                        }
                    }
                }else if(index==2){
                    System.out.println("\nIngrese la colección que se actualizará\nEjemplo p1 ó g1");
                    String pathTo= teclado.nextLine();
                    System.out.println("\nIngrese la colección que se utilizará para actualizar\nEjemplo p2 ó g2");
                    String pathFrom= teclado.nextLine();

                    long startTime = System.nanoTime();
                    aux.indexer.actualiceIndex(pathFrom, pathTo);
                    long stopTime = System.nanoTime();
                        long realTime = stopTime - startTime;
                        realTime = TimeUnit.SECONDS.convert(realTime, TimeUnit.NANOSECONDS);
                        if(realTime<=60){
                            System.out.println("El tiempo de indexación es de: "+realTime+" segundos");
                        }else{
                            System.out.println("El tiempo de indexación es de: "+TimeUnit.SECONDS.toSeconds(TimeUnit.SECONDS.toMinutes(realTime))+" minutos");
                        }
                }else{
                    return;
                }
                
                
            }else if(intro == 2){
                // Search-------------------------------------------------------
                System.out.println("\nIngrese la colección que desea buscar, escribiendo p ó g y el número 1 ó 2");
                System.out.println("Ejemplo: p1");
                String dataSearch = teclado.nextLine();
                if("p1".equals(dataSearch) || "p2".equals(dataSearch) ||"g1".equals(dataSearch) || "g2".equals(dataSearch) ){
                    int numToSearch = path.getNum(dataSearch);
                    Searcher searcher= new Searcher(numToSearch);

                    System.out.println("\nIngrese el comando de búsqueda");
                    System.out.println("Ejemplo: zetachh");
                    String dataConsult = teclado.nextLine();
                    searcher.search(dataConsult);
                    searcher.visualizeTopDocs("titulo");

                    System.out.println("\nDesea visualizar un documento en el navegador?\n1 = si\n2 = no");
                    int browser = Integer.valueOf(teclado.nextLine());
                    while(browser==1){
                        System.out.println("\nIngrese número de documento que desea visualizar en el navegador");
                        int docToView = Integer.parseInt(teclado.nextLine()); 
                        searcher.openHTML(docToView-1,numToSearch);
                        System.out.println("\nDesea visualizar otro documento en el navegador?\n1 = si\n2 = no");
                        browser = Integer.valueOf(teclado.nextLine());
                    }
                    
                }
                
            }else{
                System.exit(0);
            }
        }
        

       



        
        
    }
}
