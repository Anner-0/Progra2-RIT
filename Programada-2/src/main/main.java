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

    public static List<String> analyze(String text, Analyzer analyzer) throws IOException {
        List<String> result = new ArrayList<String>();
        TokenStream tokenStream = analyzer.tokenStream("FIELD_NAME", text);
        tokenStream= new SnowballFilter(tokenStream,"Spanish");
        CharTermAttribute attr = tokenStream.addAttribute(CharTermAttribute.class);
        tokenStream.reset();
        while (tokenStream.incrementToken()) {
            result.add(attr.toString());
        }
        return result;
    }

    public static void main(final String[] args) throws IOException {
        Normalization aux = new Normalization();
        String text = "Los cuentos clásicos son parte de nuestra cultura, ya que enseñan lecciones y consejos a los más pequeños desde hace siglos. Es por ello que Mundo Primaria te trae una selección de los mejores cuentos clásicos, para que tanto tú como tu hijo o hija disfrutéis de estos relatos que contienen una sabiduría tan importante que se ha seguido transmitiendo con el paso de los años";
        URL path = new URL();
        aux.startIndization();

  
        //  // Creamos un arhivo vacio indicando un texto para el inicio y final 
        //  // (prefijo y sufijo) del archivo. Muy util para saber cuales son nuestro
        //  // archivos temporales
        //  File f = File.createTempFile("archivoTemporal_",".lwp");
        //  System.out.println("Path: " + f.getAbsolutePath() );
  
        //  // guardamos texto en el archivo temporal
        //  BufferedWriter bw = new BufferedWriter(new FileWriter(f));
        //  bw.write(linea);
        //  bw.close();
  
        //  // leemos cada linea del archivo temporal
        //  String str;
        //  BufferedReader br = new BufferedReader(new FileReader(f));
        //  while((str = br.readLine()) != null) {
        //      System.out.println("Texto guardado en el archivo temporal: "+str);
        //  }
        //  br.close();
  
        //  // Eliminamos el archivo temporal cuando ya no lo necesitemos
        //  f.delete();
        
        // aux.readText();

        // System.out.println(text);
        // Analyzer analyzer = CustomAnalyzer.builder()
        // .withTokenizer("standard")
        // .addTokenFilter("lowercase")
        // .addTokenFilter("snowballPorter")
        // .build();

        // text=aux.cleanString(text).toLowerCase();
        // System.out.println(text);
        // String lista = aux.eliminateStopWords(text);
        // // System.out.println(lista);
        // List<String> result = analyze(lista, analyzer);

        // Iterator iter= result.iterator();
        // while(iter.hasNext()){
        //     System.out.println(iter.next());
        // }


    }

}
