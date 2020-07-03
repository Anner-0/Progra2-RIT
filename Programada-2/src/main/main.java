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


    public static void main(String[] args) throws Exception {
        // Normalization aux = new Normalization();  
        // URL path = new URL();
        // String pathDirp1 = path.pathp1;
        // String pathDirp2 = path.pathp2;
        // String pathDirg1 = path.pathg1;
        // String pathDirg2 = path.pathg2;
        // aux.startIndization(pathDirp2,1);
        // aux.indexer.createIndex(1);
         Searcher searcher= new Searcher(1);
        searcher.search("Zetachh");
         searcher.visualizeTopDocs("titulo");
        searcher.openHTML(0,1);
        
    }

}
