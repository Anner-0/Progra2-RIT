/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modules;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;

/**
 *
 * @author kcorr
 */
public class Indexer {
    
    public Indexer(){// el constructor de la clase
    }

    

    public Document documentCreator(String URL,String data) {
        final Document document = new Document();
        document.add(new TextField("URL",URL,Field.Store.YES));
        document.add(new TextField("DATA",data,Field.Store.YES));
        
        return document;
    }   
    
    

    
}
