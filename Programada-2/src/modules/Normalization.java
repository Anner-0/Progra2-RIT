package modules;

import java.io.File; // Import the File class
import java.io.FileNotFoundException; // Import this class to handle errors
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner; // Import the Scanner class to read text files
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.lang.model.element.Element;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.Normalizer;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.custom.CustomAnalyzer;
import org.apache.lucene.analysis.es.SpanishAnalyzer;
import org.apache.lucene.analysis.snowball.SnowballFilter;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.tartarus.snowball.ext.PorterStemmer;
import org.tartarus.snowball.ext.SpanishStemmer;


   
public class Normalization {
  String toIndexTitle = new String();
  String toIndexA= new String();
  String toIndexH= new String();
  String toIndexBody= new String();
  public Indexer indexer=null;
  
    public Normalization() throws IOException {
      this.indexer=new Indexer(); 

    }


    private String Stem(String data) {

      return data;

    }

    public List<String> createListStopWords() {
      List<String> stopWords = new ArrayList<>();
      URL rutaStopwords = new URL();

      try {
        File archivo = new File(rutaStopwords.stopwords);
        Scanner scannercito = new Scanner(archivo);
        while (scannercito.hasNextLine()) {
          String data = scannercito.nextLine();
          stopWords.add(data);

        }
        scannercito.close();
      } catch (FileNotFoundException e) {
        System.out.println("An error occurred.");
        e.printStackTrace();
      }
      return stopWords;

    }

    public String eliminateStopWords(final String data) {
       List<String> stopWords = createListStopWords();
      List<String> allWords = new ArrayList<>();
      String result="";
      for (String word : data.split(" ")) {
        allWords.add(word);
      }
      allWords.removeAll(stopWords);
      Iterator iter=allWords.iterator();
      while(iter.hasNext()){
          result+=iter.next().toString()+" ";
      }
      return result;
    }

    public String cleanString(String line) {
      String cleanText ="";
      if (line !=null) {
          // Normalizar texto para eliminar acentos, dieresis, cedillas y tildes
          cleanText = Normalizer.normalize(line, Normalizer.Form.NFD);
          // Quitar caracteres no ASCII excepto la enie, interrogacion que abre, exclamacion que abre, grados, U con dieresis.
          cleanText = cleanText.replaceAll("[^\\p{ASCII}(N\u0303)(n\u0303)(\u00A1)(\u00BF)(\u00B0)(U\u0308)(u\u0308)]", "");
          // Regresar a la forma compuesta, para poder comparar la enie con la tabla de valores
          cleanText = Normalizer.normalize(cleanText, Normalizer.Form.NFC);
      }
      return cleanText;
    }

    public static String analyze(String text) throws IOException {
      String result = new String();
      Analyzer analyzer = CustomAnalyzer.builder().withTokenizer("standard").addTokenFilter("snowballPorter").build();
      TokenStream tokenStream = analyzer.tokenStream("FIELD_NAME", text);
      tokenStream= new SnowballFilter(tokenStream,"Spanish");
      CharTermAttribute attr = tokenStream.addAttribute(CharTermAttribute.class);
      tokenStream.reset();
      while (tokenStream.incrementToken()) {
          result+=(String)attr.toString();
      }
      return result;
  }

    public void readText(String path,Integer posInicial,int numpath, int numDoc) throws IOException {
      readTitle(path);
      readA(path);
      readH(path);
      readBody(path);
      this.indexer.createDocument(posInicial, this.toIndexBody,this.toIndexA,this.toIndexTitle, this.toIndexH,numpath, numDoc);
    }

    public String getPattern(String text, String toIndex){
      final String regex = "\\s?([A-Za-zñ]{2,})\\s?";
      final Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE);
      Matcher matcher = pattern.matcher(text);
      while (matcher.find()) {
        toIndex+=(String)matcher.group(0)+" ";
      }
      return toIndex;
    }

    public String getPatternToAnalize(String text){
      final String regex = "\\s?([A-Za-zñ]{2,})\\s?";
      final Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE);
      String text1="";
      Matcher matcher = pattern.matcher(text);
      while (matcher.find()) {
        text1+=(String)matcher.group(0)+" ";
      }
      return text1;
    }

    

    public void readLabel(String label, String path) throws IOException{
      String text="";  
      String toIndex="";
      // load file
      final File inputFile = new File(path);
      // parse file as HTML document
      final Document doc = Jsoup.parse(inputFile, "UTF-8");
      // select element by <title>
      final Elements elements = doc.select(label);
      final Iterator iter = elements.iterator();
      while (iter.hasNext()){
            final String Word=cleanString(iter.next().toString().replaceAll("\\<.*?\\>", "").toLowerCase());
            text+=Word+" ";
      }
      //String text1 = eliminateStopWords(text);
      toIndex=getPattern(text, toIndex);
      if(label=="a"){
        this.toIndexA=toIndex;
      }
      if(label=="title"){
        this.toIndexTitle=toIndex;
      }
    }

    public void readTitle(String path) throws IOException {
       readLabel("title", path);
    }

    public void readA(String path) throws IOException {
      readLabel("a", path);
    }

    public void readBody(String path) throws IOException {
      String text="";
        // load file
      final File inputFile = new File(path);
      // parse file as HTML document
      final Document doc = Jsoup.parse(inputFile, "UTF-8");
      // select element by <body>
      final Elements elements = doc.select("body");
      final Iterator iter = elements.iterator();
      while (iter.hasNext()){
            final String Word=cleanString(iter.next().toString().replaceAll("\\<.*?\\>", "").toLowerCase());
            text+=Word+" ";
      }
      String text1 = eliminateStopWords(text);
      text="";
      text=getPatternToAnalize(text1);
      // String result = analyze(text);
      toIndexBody=text;
      //indexer.createDocument("texto",toIndexBody);
      //System.out.println(toIndexBody);
    }

    public void readH(String path) throws IOException {
        String text="";
        String result="";
        toIndexH="";
        // load file
        final File inputFile = new File(path);
        // parse file as HTML document
        final Document doc = Jsoup.parse(inputFile, "UTF-8");
        for(int i=1; i<=5; i++){
            String h="h"+i;
            // select element by <h?>
            final Elements elements = doc.select(h);
            final Iterator iter = elements.iterator();
            while (iter.hasNext()){
                  final String Word=cleanString(iter.next().toString().replaceAll("\\<.*?\\>", "").toLowerCase());
                  text+=Word+" ";
            }
            String text1 = eliminateStopWords(text);
            text="";
            text=getPatternToAnalize(text1);
            result+=text;
        }
        toIndexH+=text;
        //indexer.createDocument("encab",text);
    }

    public void createTempFile(String text,Integer inicial,int numpath, int numDoc) throws IOException {
      final URL path = new URL();
      File file = new File(path.temp);
      file.createNewFile();
      BufferedWriter bw = new BufferedWriter(new FileWriter(file));
      bw.write(text);
      bw.close();
      readText(file.getAbsolutePath(),inicial,numpath, numDoc);
      file.delete();

    }

    /**
     * 
     * @param path the path of the txt file
     * @param num the number of the index folder path
     * @throws IOException
     */
    public void startIndization(String path,int num) throws IOException {
      final File inputFile = new File(path);
      FileReader  fr = new FileReader (inputFile);
      BufferedReader  br1 = new BufferedReader(fr);
      String text="";
      // Lectura del fichero
      String line;
      int i=1000000;
      int con=1;
      int lineaInicial=1;
      int cuentalineas=1;
      String regex = ".*<\\/html>";
      Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE);
      while((line=br1.readLine())!=null){
        Matcher matcher = pattern.matcher(line);
        if(matcher.find() && con<=i){ 
          createTempFile(text,lineaInicial,num, con);
          //System.out.println("Aquí termina"+"->"+con+" ");
          con++;
          text="";
          line="";
          cuentalineas=cuentalineas+1;
          lineaInicial=cuentalineas;
        }else{
          text+=line;
          cuentalineas++;
        }
      }

      }

    
}