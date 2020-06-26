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
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
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
    
    public URL url = null;
    public IndexSearcher indexSearcher;
    
    public Searcher(int num) throws IOException {
        this.url= new URL();
        this.indexSearcher=selectIndexSearch(num);
        
    }


    public IndexSearcher selectIndexSearch(int num) throws IOException {
        if(num==0){ // num 0 es indice P1
            return createSearcher(this.url.indexp1);
        }
        if(num==1){// num 1 es indice P2
            return createSearcher(this.url.indexp2);
        }
        if(num==2){//num 2 es indice G1
            return createSearcher(this.url.indexg1);
        }
        if(num==3){// num 3 es indice G2
            return createSearcher(this.url.indexg2);
        }
        return null;
    }


    public IndexSearcher createSearcher(String url) throws IOException 
    {
        Directory dir = FSDirectory.open(Paths.get(url));
        IndexReader reader = DirectoryReader.open(dir);
        IndexSearcher searcher = new IndexSearcher(reader);
        return searcher;
    } 


    public TopDocs search(String datatoSearch) throws Exception{
        QueryParser qp = new QueryParser("body", new SpanishAnalyzer());
        Query query = qp.parse(datatoSearch);
        TopDocs hits = this.indexSearcher.search(query, 3);
        System.out.println("Total Results :: " + hits.totalHits);
        for (ScoreDoc sd : hits.scoreDocs) 
        {
            Document d = this.indexSearcher.doc(sd.doc);

            System.out.println("------------------------------------------------------");
            System.out.println(String.format(d.get("body")));
        }
        
        return hits;
    }
    // public TopDocs searchByLabel (String dataToFInd, IndexSearcher searcher) throws Exception {//Busca en el label que recibe 
    //     QueryParser qp = new QueryParser("body", new SpanishAnalyzer());
    //     Query idQuery = qp.parse(dataToFInd);
    //     TopDocs hits = searcher.search(idQuery, 20);

    //     for (ScoreDoc sd : foundDocs.scoreDocs) 
    //     {
    //         Document d = searcher.doc(sd.doc);
    //         System.out.println(String.format(d.get("firstName")));
    //     }


    //     return hits;
    // }


    

    // public void visualizeTopDocs(TopDocs top,String label) throws IOException {// este metodo pretende imprimir en consola el label de los documetos que se recuperaron
    //     for (ScoreDoc sd : top.scoreDocs) 
    //     {
    //         Document d = this.searcher.doc(sd.doc);
    //         System.out.println(String.format(d.get(label);
    //     }
    // }







    
    
}
