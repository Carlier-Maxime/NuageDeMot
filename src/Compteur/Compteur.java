package Compteur;

import Arbre.Arbre;

import java.io.File;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;
import java.util.regex.Pattern;

public abstract class Compteur {
    //attribute
    // separatorList : liste des séparateur pour l'advanced split
    private static final char[] separatorList = new char[]{
            '.',',','\'','’','(',')','\\','[',']','<','>','"',';',':','\t','_','*','&','|','+','/','-'
    };
    // blacklist : ne compte pas les mots du tableau (desactivable)
    private static final Arbre<String> blacklist = new Arbre<>(CharSequence::compare, new String[]{
            "un", "une", "des", "le", "la", "les", "son", "se", "sa", "ça", "ses", "ces", "cette", "est", "de", "je", "tu", "on", "il",
            "elle", "nous", "vous", "ils", "elles", "iel", "iels", "au", "pour", "et", "ma", "mon", "mes", "en", "du", "par", "dans",
            "que", "sur", "ou", "avec", "qui", "ce", "ne", "ainsi", "si", "comme", "mais", "alors", "--", "aux", "qu", "leur", "pas",
            "où", "lui", "cela", "car", "luy", "sont", "fut", "dont", "ont", "après", "même", "était", "sous", "fait", "avait"
    });
    // blacklistSplit : ne split pas les mots de la liste
    private static final Arbre<String> blacklistSplit = new Arbre<>(CharSequence::compare, new String[]{"aujourd'hui","arc-en-ciel"});
    private Arbre<String> tabooList; // ne compte pas les mots de la liste (personalisable)
    private boolean blacklistActivate;
    private int nbMot;
    private int nbMot5;
    private String fileName;
    private long time;

    //constructor
    public Compteur(String[] taboolist){
        this.tabooList = new Arbre<>(CharSequence::compare, taboolist);
        blacklistActivate = true;
        nbMot = 0;
        nbMot5 = 0;
        fileName = "";
        time =- 1;
    }

    public Compteur(){
        this(new String[]{});
    }

    //getters & setters
    public boolean isBlacklistActivate() {
        return blacklistActivate;
    }

    public void setBlacklistActivate(boolean blacklistActivate) {
        this.blacklistActivate = blacklistActivate;
    }

    //methode
    public void advancedWordCompter(File file, boolean showTime){
        fileName = file.getName();
        long startTime = System.nanoTime();
        try {
            Scanner scnr = new Scanner(file);
            while (scnr.hasNext()){
                String line = scnr.nextLine();
                String[] words = line.split(" ");
                for (String word : words){
                    wordProcessing(word.toLowerCase(Locale.ROOT));
                }
            }
            scnr.close();
        } catch (Exception e){
            System.out.println(e.getMessage()+" in advancedWordCompter !");
            System.exit(1);
        } finally {
            long stopTime = System.nanoTime();
            time = ((stopTime-startTime)/1000000);
            if (showTime) System.out.println(time+" ms");
        }
    }

    public void advancedWordCompter(File file){advancedWordCompter(file, false);}

    private void wordProcessing(String word){
        if (blacklistActivate){
            if (word.length()<=1){return;}
            if (blacklist.get(word) != null){return;}
        }
        if (tabooList.get(word) != null){return;}
        for (char separator : separatorList){
            if (word.contains(Character.toString(separator))){
                advancedWordSplit(word,separator);
                return;
            }
        }
        nbMot++;
        if (word.length()>=5) nbMot5++;
        addElement(word);
    }

    private void advancedWordSplit(String word, char separator){
        if (blacklistSplit.get(word) != null){
            addElement(word);
            return;
        }

        String[] splittedWord = word.split(Pattern.quote(Character.toString(separator)));
        if (splittedWord.length == 0){return;}
        wordProcessing(splittedWord[0]);
        StringBuilder sb = new StringBuilder();
        for (int i=1; i< splittedWord.length; i++){
            sb.append(splittedWord[i]);
            sb.append(separator);
        }
        if (sb.length()>0){sb.deleteCharAt(sb.length()-1);}
        wordProcessing(sb.toString());
    }

    public void addTabooWord(String tabooWord){
        tabooList.add(tabooWord);
    }

    public void removeTabooWord(String tabooWord){
        tabooList.remove(tabooWord);
    }

    public ArrayList<Element> getTop10(){
        ArrayList<Element> l = getElementInOrder();
        ArrayList<Element> r = new ArrayList<>(10);
        for (int i=0; i<10; i++){
            if (i>=l.size()) break;
            r.add(l.get(i));
        }
        return r;
    }

    public ArrayList<Element> getTop10_5(){
        int n=0;
        int i=0;
        ArrayList<Element> l = getElementInOrder();
        ArrayList<Element> r = new ArrayList<>(10);
        while (n<10) {
            if (i >= l.size()) break;
            Element e = l.get(i);
            if (e.word.length() >= 5) {
                r.add(e);
                n++;
            }
            i++;
        }
        return r;
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("Fichier : ").append(fileName).append('\n');
        sb.append("Nombres de mots : ").append(nbMot).append('\n');
        sb.append("Nombre de mots >= 5 : ").append(nbMot5).append('\n');
        sb.append("Top 10 des mots les plus present :\n");
        for (Element e : getTop10()){
            sb.append("- ").append(e.word).append(": ").append(e.nb).append('\n');
        }
        sb.append("Top 10 des mots >=5 :\n");
        for (Element e : getTop10_5()){
            sb.append("- ").append(e.word).append(": ").append(e.nb).append('\n');
        }
        return sb.toString();
    }

    protected void insertInArraySort(ArrayList<Element> l, Element e){
        int limDeb = 0;
        int limFin = l.size()-1;
        int i = limDeb+((limFin-limDeb)/2);
        int lastI = -1;
        if (l.size()==0){l.add(e);return;}
        while (i != lastI && limDeb!=limFin){
            if (e.nb == l.get(i).nb){break;}
            else if (e.nb > l.get(i).nb){
                limFin=i-1;
                if (limFin<0){limFin=0;}
            }
            else {
                limDeb=i+1;
                if (limDeb>l.size()-1){limDeb=l.size()-1;}
            }
            lastI = i;
            i = limDeb+((limFin-limDeb)/2);
        }
        if (i==l.size()-1 && e.nb < l.get(i).nb){l.add(e); return;}
        if (e.nb < l.get(i).nb){l.add(i+1,e); return;}
        l.add(i,e);
    }

    public int getNbMot() {
        return nbMot;
    }

    public int getNbMot5() {
        return nbMot5;
    }

    public long getTime() {
        return time;
    }

    public ArrayList<Element> getElements(){
        return getElementInOrder();
    }

    protected abstract ArrayList<Element> getElementInOrder();
    protected abstract void addElement(String element);
}
