/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modules;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import java.awt.Desktop;

import org.apache.lucene.analysis.es.SpanishAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.MatchAllDocsQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import jdk.nashorn.internal.runtime.regexp.joni.ast.QuantifierNode;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author kcorr
 */
public class Searcher {

    public URL url = null;
    public IndexSearcher indexSearcher;
    public TopDocs lastTopSearch;
    public List<Document> top;

    public Searcher(int num) throws IOException {
        this.url = new URL();
        this.indexSearcher = selectIndexSearch(num);
        this.lastTopSearch = null;
        this.top = new ArrayList<>();
    }

    public IndexSearcher selectIndexSearch(int num) throws IOException {
        if (num == 0) { // num 0 es indice P1
            return createSearcher(this.url.indexp1);
        }
        if (num == 1) {// num 1 es indice P2
            return createSearcher(this.url.indexp2);
        }
        if (num == 2) {// num 2 es indice G1
            return createSearcher(this.url.indexg1);
        }
        if (num == 3) {// num 3 es indice G2
            return createSearcher(this.url.indexg2);
        }
        return null;
    }

    public IndexSearcher createSearcher(String url) throws IOException {
        Directory dir = FSDirectory.open(Paths.get(url));
        IndexReader reader = DirectoryReader.open(dir);
        IndexSearcher searcher = new IndexSearcher(reader);
        return searcher;
    }

    public TopDocs search(String datatoSearch) throws Exception {
        QueryParser qp = new QueryParser("texto", new SpanishAnalyzer());
        Query query = qp.parse(datatoSearch);
        TopDocs hits = this.indexSearcher.search(query, 40);
        this.lastTopSearch = hits;
        System.out.println("Total Results :: " + hits.totalHits);
        // for (ScoreDoc sd : hits.scoreDocs)
        // {
        // Document d = this.indexSearcher.doc(sd.doc);

        // //
        // System.out.println("------------------------------------------------------");
        // // System.out.println(String.format(d.get("body")));
        // }

        return hits;
    }

    public void visualizeTopDocs(String label,int quantity) throws IOException {// este metodo pretende imprimir en consola el label
                                                        // de los documetos que se recuperaron
        int cont=1;
        int pos=0;
        if(quantity==40){//para cuanto 
            pos=20;
            cont=21;
        }  
        this.saveDocsRetrieved();
        for (int i=pos; i<this.top.size();i++) {
            if(i<quantity){
                Document d = this.top.get(i);
                System.out.println("------------------------------------------------------" + "\n");
                System.out.print("Top: ");
                System.out.println(cont);
                System.out.println("Documento Numero: " + String.format(d.get("idDocument").toString()));
                System.out.println("Posicion Inicial: " + String.format(d.get("posInicial").toString()));
                System.out.println("Documenti: " + String.format(d.get("identification").toString()));
                System.out.println(String.format(d.get(label).toString()));
                this.top.add(d);
                cont++;

            }

        }
    }

    public void saveDocsRetrieved() throws IOException {
        for (ScoreDoc sd : this.lastTopSearch.scoreDocs) {
            Document d = this.indexSearcher.doc(sd.doc);
            this.top.add(d);
        }
    }



    public void openHTML(int num, int docNumber) throws IOException, URISyntaxException {
        Document d = top.get(num);
        // System.out.println(d.get("posInicial"));
        Integer posInicial = Integer.parseInt(d.get("posInicial"));
        retrieveTempHTML(posInicial, docNumber);

    }

    public void retrieveTempHTML(int posInicial, int docNumber) throws IOException, URISyntaxException {// Estoy
                                                                                                        // haciendo la
        
        // String URL = this.url.getURL(docNumber);
        String doc = new String();
        String line = new String();
        String regex = ".*<\\/html>";
        int cont=0;
        Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE);
        BufferedReader br = new BufferedReader(new FileReader(this.url.getURL(docNumber)));
        for (int i = 0; i < (posInicial - 1); i++){//saltar lineas hasta llegar a la linea deseada
            br.readLine();
        }
        while ((line = br.readLine()) != null) {
            Matcher matcher = pattern.matcher(line);
            if (matcher.find() && cont<=1) {
                cont++;
                File file = new File(this.url.temp);
                file.createNewFile();
                BufferedWriter bw = new BufferedWriter(new FileWriter(file));
                bw.write(doc);
                bw.close();
                //System.out.println(doc);
                URI uri = new URI("file:///" + this.url.temp);
                uri.normalize();
                Desktop.getDesktop().browse(uri);
                return;
            }
            else{
                doc+=line;
            }

        }   
    }

    public TopDocs allDocuments() throws IOException {
        Query query = new MatchAllDocsQuery();
        //IndexSearcher indexS= createSearcher(pathFrom);
        TopDocs hits = this.indexSearcher.search(query, 1000000);
        //TopDocs hits1 = indexS.search(query, 1000000);
        System.out.println("Total Results :: " + hits.totalHits);
        return hits;
    }


       
//end of class
}
