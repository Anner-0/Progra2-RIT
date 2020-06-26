/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modules;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.es.SpanishAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.store.FSDirectory;

/**
 *
 * @author kcorr
 */
public class Indexer {
    URL url;
    List<Document> documents= new ArrayList<>();
    
    public Indexer(){// el constructor de la clase
        this.url=new URL();
    }

    

    public void createDocument(String labelName,String data) {// this method set documents that will be stored on the index
        System.out.println(data);
        Document document = new Document();// crea el documento
        document.add(new TextField(labelName,data,Field.Store.YES));// crea los bloques que va a tener el documento
        //document.add(new TextField("DATA",data,Field.Store.YES));
        this.documents.add(document);
    }   

    
    public IndexWriter selectIndexWriter(int num) throws IOException {
        if(num==0){ // num 0 es indice P1
            return createWriter(this.url.indexp1);
        }
        if(num==1){// num 1 es indice P2
            return createWriter(this.url.indexp2);
        }
        if(num==2){//num 2 es indice G1
            return createWriter(this.url.indexg1);
        }
        if(num==3){// num 3 es indice G2
            return createWriter(this.url.indexg2);
        }
        return null;
    }

    private IndexWriter createWriter(String pUrl) throws IOException {// this method creates the program that will store the documents and create the index
    FSDirectory dir = FSDirectory.open(Paths.get(pUrl));
    Analyzer analyzer = new SpanishAnalyzer();
    IndexWriterConfig config = new IndexWriterConfig(analyzer);
    IndexWriter writer = new IndexWriter(dir, config);
    return writer;
    }


    public void createIndex(int num) throws Exception {
        IndexWriter writer = this.selectIndexWriter(num);
        writer.deleteAll();
        writer.addDocuments(this.documents);
        writer.commit();
        writer.close();
        System.out.println("Index Created With: "+documents.size()+" documents");
    }
    

    
}
