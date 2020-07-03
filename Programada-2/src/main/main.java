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

        // System.out.println("Ingrese la colección que desea ingresar, escribiendo p ó g y el número 1 ó 2");
        // System.out.println("Ejemplo: p1");
        // String data = teclado.nextLine();
        // String pathDir = path.getPath(data); 
        // int numToIndex = path.getNum(data);

        // long startTime = System.nanoTime();
        // aux.startIndization(pathDir,numToIndex);
        // aux.indexer.createIndex(numToIndex);
        // long stopTime = System.nanoTime();
        // long realTime = stopTime - startTime;
        // realTime = TimeUnit.SECONDS.convert(realTime, TimeUnit.NANOSECONDS);
		// if(realTime<=60){
        //     System.out.println("El tiempo de indexación es de: "+realTime+" segundos");
        // }else{
        //     System.out.println("El tiempo de indexación es de: "+TimeUnit.SECONDS.toSeconds(TimeUnit.SECONDS.toMinutes(realTime))+" minutos");
        // }

        System.out.println("Ingrese la colección que desea buscar, escribiendo p ó g y el número 1 ó 2");
        System.out.println("Ejemplo: p1");
        String dataSearch = teclado.nextLine();
        int numToSearch = path.getNum(dataSearch);
        Searcher searcher= new Searcher(numToSearch);

        System.out.println("Ingrese el comando de búsqueda");
        System.out.println("Ejemplo: zetachh");
        String dataConsult = teclado.nextLine();
        searcher.search(dataConsult);
        searcher.visualizeTopDocs("titulo");

        System.out.println("Ingrese numero de documento que desea visualizar en el navegador");
        int docToView = Integer.parseInt(teclado.nextLine()); 
        searcher.openHTML(docToView-1,numToSearch);
        
    }
}
