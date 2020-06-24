/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.custom.CustomAnalyzer;
import org.apache.lucene.analysis.es.SpanishAnalyzer;
import org.apache.lucene.analysis.snowball.SnowballFilter;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.tartarus.snowball.ext.PorterStemmer;
import org.tartarus.snowball.ext.SpanishStemmer;

import modules.Normalization;
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


    public static void main(final String[] args) throws IOException {
        Normalization aux = new Normalization();
        String text = "Los cuentos clásicos son parte de nuestra cultura, ya que enseñan lecciones y consejos a los más pequeños desde hace siglos. Es por ello que Mundo Primaria te trae una selección de los mejores cuentos clásicos, para que tanto tú como tu hijo o hija disfrutéis de estos relatos que contienen una sabiduría tan importante que se ha seguido transmitiendo con el paso de los años";
        URL path = new URL();
        String pathDir = path.pathp2;
        aux.startIndization(pathDir);
    }

}
