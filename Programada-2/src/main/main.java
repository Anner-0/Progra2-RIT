/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;


import java.io.IOException;

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

    public static void main(final String[] args) throws Exception {
        Normalization aux = new Normalization();
        Searcher searcher= new Searcher();
        URL path = new URL();
        String pathDirp1 = path.pathp1;
        String pathDirp2 = path.pathp2;
        String pathDirg1 = path.pathg1;
        String pathDirg2 = path.pathg2;
        aux.startIndization(pathDirp2);
        aux.indexer.createIndex(1);
        searcher.searchByFirstName(firstName, searcher)
    }

}
