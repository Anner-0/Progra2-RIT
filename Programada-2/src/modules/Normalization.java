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
import java.io.FileReader;
import java.io.IOException;
import java.text.Normalizer;


   
public class Normalization {
  ArrayList<String> toIndexTitle = new ArrayList<String>();
  ArrayList<String> toIndexA= new ArrayList<String>();
  ArrayList<String> toIndexH= new ArrayList<String>();
  ArrayList<String> toIndexBody= new ArrayList<String>();

    public Normalization(){

    }


    private String Stem(final String data) {

      return data;

    }

    public List<String> createListStopWords() {
      final List<String> stopWords = new ArrayList<>();
      final URL rutaStopwords = new URL();

      try {
        final File archivo = new File(rutaStopwords.stopwords);
        final Scanner scannercito = new Scanner(archivo);
        while (scannercito.hasNextLine()) {
          final String data = scannercito.nextLine();
          stopWords.add(data);

        }
        scannercito.close();
      } catch (final FileNotFoundException e) {
        System.out.println("An error occurred.");
        e.printStackTrace();
      }
      return stopWords;

    }

    public String eliminateStopWords(final String data) {
      final List<String> stopWords = createListStopWords();
      final List<String> allWords = new ArrayList<>();
      String result="";
      for (final String word : data.split(" ")) {
        allWords.add(word);
      }
      allWords.removeAll(stopWords);
      Iterator iter=allWords.iterator();
      while(iter.hasNext()){
          result+=iter.next().toString()+" ";
      }
      return result;
    }

    public String cleanString(final String line) {
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

    public void readText() throws IOException {
      readTitle();
      // readA();
      // readH();
      // readBody();
    }



    public void readLabel(String label, ArrayList<String> toIndex) throws IOException{
      String text="";
        
      final URL path = new URL();
        // load file
        final File inputFile = new File(path.pathp2);
      // parse file as HTML document
      final Document doc = Jsoup.parse(inputFile, "UTF-8");
      // select element by <title>
      final Elements elements = doc.select(label);
      final Iterator iter = elements.iterator();
      while (iter.hasNext()){
            final String Word=cleanString(iter.next().toString().replaceAll("\\<.*?\\>", "").toLowerCase());
            text+=Word+" ";
      }
      String text1 = eliminateStopWords(text);
      final String regex = "\\s?([A-Za-z]{2,})\\s?";
      final Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE);
      Matcher matcher = pattern.matcher(text1);
      while (matcher.find()) {
        toIndex.add(matcher.group(0));
      }
      toIndex.forEach(System.out::println);
    }

    public void readTitle() throws IOException {
       readLabel("title", toIndexTitle);
    }

    public void readA() throws IOException {
      readLabel("a", toIndexA);
    }

    public void readBody() throws IOException {
      readLabel("body", toIndexBody);
    }


    public void readH() throws IOException {
        String text="";
        
        final URL pathp2 = new URL();
        // load file
        final File inputFile = new File(pathp2.pathp2);
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
            final String regex = "\\s?([A-Za-z]{2,})\\s?";
            final Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE);
            Matcher matcher = pattern.matcher(text1);
            while (matcher.find()) {
              toIndexH.add(matcher.group(0));
            }
        }
        toIndexH.forEach(System.out::println);
    }

    
}