package Compteur;

import List.List;

import java.io.File;
import java.util.ArrayList;
import java.util.Objects;

public class CompteurList extends Compteur{
    private List<Element> list;

    public CompteurList(String[] taboolist) {
        super(taboolist);
        list = new List<Element>();
    }

    public CompteurList() {
        this(null);
    }

    @Override
    protected ArrayList<Element> getElementInOrder() {
        ArrayList<Element> l = new ArrayList<>();
        for (Element e : list){
            insertInArraySort(l,e);
        }
        return l;
    }

    @Override
    protected void addElement(String element) {
        for (Element e : list){
            if (Objects.equals(e.word, element)){
                e.nb++;
                return;
            }
        }

        list.add(new Element(element));
    }

    public static void main(String[] args) {
        Compteur compteur = new CompteurList();
        compteur.advancedWordCompter(new File("data/encyclopedie1.txt"), true);
        System.out.println(compteur);
    }
}
