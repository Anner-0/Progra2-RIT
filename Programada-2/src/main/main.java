/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.io.IOException;

import modules.Normalization;

/**
 *
 * @author kcorr
 */
public class main {

    /**
     * @param args the command line arguments
     * @throws IOException
     */
    public static void main(final String[] args) throws IOException {
        Normalization aux = new Normalization();
        String text = "Yo amo este telefono, es super veloz y tiene muchas cosas frescas y nuevas como jelly bean..... pero como es reciente, le he. encontrado algunos bugs";
        //  aux.eliminateStopWords(text.toLowerCase()).forEach(System.out::println);
        aux.readText();
    }
    
}
