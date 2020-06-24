/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modules;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.analysis.es.SpanishAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

/**
 *
 * @author kcorr
 */
public class Searcher {
    
    URL url = null;
    IndexSearcher searcher=null;

    
    public Searcher() throws IOException {
        this.url= new URL();
        this.searcher= createSearcher();
    }


    public IndexSearcher createSearcher()throws IOException{// crea el objeto que realiza los queries 
        Directory dir = FSDirectory.open(Paths.get(this.url.url));
        IndexReader reader = DirectoryReader.open(dir);
        IndexSearcher searcher = new IndexSearcher(reader);
        return searcher;
    }

    public TopDocs searchByLabel (String dataToFInd, IndexSearcher searcher) throws Exception {//Busca en el label que recibe 
        QueryParser qp = new QueryParser("body", new SpanishAnalyzer());
        Query idQuery = qp.parse(dataToFInd);
        TopDocs hits = searcher.search(idQuery, 20);
        return hits;
    }

    

    public void visualizeTopDocs(TopDocs top,String label) throws IOException {// este metodo pretende imprimir en consola el label de los documetos que se recuperaron
        for (ScoreDoc sd : top.scoreDocs) 
        {
            Document d = this.searcher.doc(sd.doc);
            System.out.println(String.format(d.get(label);
        }
    }







    
    
}
