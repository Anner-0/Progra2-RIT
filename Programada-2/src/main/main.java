/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.custom.CustomAnalyzer;
import org.apache.lucene.analysis.es.SpanishAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;

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


    public static List<String> analyze(String text, Analyzer analyzer) throws IOException{
        List<String> result = new ArrayList<String>();
        TokenStream tokenStream = analyzer.tokenStream("FIELD_NAME", text);
        CharTermAttribute attr = tokenStream.addAttribute(CharTermAttribute.class);
        tokenStream.reset();
        while(tokenStream.incrementToken()) {
           result.add(attr.toString());
        }       
        return result;
    }
    public static void main(final String[] args) throws IOException {
        // Normalization aux = new Normalization();
         String text = "Los cuentos clásicos son parte de nuestra cultura, ya que enseñan lecciones y consejos a los más pequeños desde hace siglos. \n"
         +"Es por ello que Mundo Primaria te trae una selección de los mejores cuentos clásicos, para que tanto tú como tu hijo o hija disfrutéis de estos \n"
         +"relatos que contienen una sabiduría tan importante que se ha seguido transmitiendo con el paso de los años";
        // //  aux.eliminateStopWords(text.toLowerCase()).forEach(System.out::println);
        // aux.readText();
        
        Analyzer analyzer = CustomAnalyzer.builder()
        .withTokenizer("standard")
        .addTokenFilter("lowercase")
        .addTokenFilter("snowballPorter")
        .build();


    List<String> result = analyze(text, analyzer);
         
    for(String element : result){
        System.out.println(element);
    }
        }
        
}
    
