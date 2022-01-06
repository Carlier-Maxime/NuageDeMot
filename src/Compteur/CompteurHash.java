package Compteur;

import Arbre.Arbre;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;

public class CompteurHash extends Compteur{
    private ArrayList<Arbre<Element>> hashTab;
    private static final int initCapacity = 32000;

    public CompteurHash(String[] taboolist) {
        super(taboolist);
        hashTab = new ArrayList<>(Collections.nCopies(initCapacity, null));
    }

    public CompteurHash() {
        this(new String[]{});
    }

    @Override
    protected ArrayList<Element> getElementInOrder() {
        ArrayList<Element> l = new ArrayList<>();
        for (Arbre<Element> arbre : hashTab){
            if (arbre == null){continue;}
            for (Element e : arbre){
                insertInArraySort(l,e);
            }
        }
        return l;
    }

    @Override
    protected void addElement(String element) {
        Element e = new Element(element);
        int index = Math.abs(e.hashCode())%initCapacity;
        Arbre<Element> arbre = hashTab.get(index);
        if (arbre == null){hashTab.set(index, new Arbre<>(Element::compare,new Element[]{e}));return;}
        if (Objects.equals(arbre.getRootObject().word, e.word)){
            arbre.getRootObject().nb++;
        } else {
            for (Element elem : arbre){
                if (Objects.equals(elem.word, e.word)){
                    elem.nb++;
                    return;
                }
            }
            arbre.add(e);
        }
    }

    public static void main(String[] args) {
        Compteur compteur = new CompteurHash();
        compteur.advancedWordCompter(new File("data/encyclopedie1.txt"), true);
        System.out.println(compteur);
    }
}
