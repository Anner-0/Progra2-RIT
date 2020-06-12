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
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
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
    
    public Indexer(){// el constructor de la clase
        this.url=new URL();
    }

    

    public Document documentCreator(String URL,String data) {// this method set documents that will be stored on the index
        final Document document = new Document();
        document.add(new TextField("LABEL",URL,Field.Store.YES));
        document.add(new TextField("DATA",data,Field.Store.YES));
        return document;
    }   

    private IndexWriter createWriter() throws IOException {// this method creates the program that will store the documents and create the index
    FSDirectory dir = FSDirectory.open(Paths.get(url.url));
    IndexWriterConfig config = new IndexWriterConfig(new StandardAnalyzer());
    IndexWriter writer = new IndexWriter(dir, config);
    return writer;
    }


    public void createIndex(int indexNumber,List<Document> documents) throws Exception {
        IndexWriter writer = this.createWriter();

        writer.deleteAll();
        writer.addDocuments(documents);
        writer.commit();
        writer.close();
    }
    

    
}