package Compteur;

import Arbre.Arbre;

import java.io.File;
import java.util.ArrayList;

public class CompteurArbre extends Compteur{
    private Arbre<Element> arbre;

    public CompteurArbre(String[] taboolist) {
        super(taboolist);
        this.arbre = new Arbre<>(Element::compare);
    }

    public CompteurArbre() {
        this(new String[]{});
    }

    @Override
    protected ArrayList<Element> getElementInOrder() {
        ArrayList<Element> l = new ArrayList<>();
        for (Element e : arbre){
            insertInArraySort(l,e);
        }
        return l;
    }

    @Override
    protected void addElement(String element) {
        Element e = arbre.get(new Element(element));
        if (e != null){
            e.nb++;
        } else {
            arbre.add(new Element(element));
        }
    }

    public static void main(String[] args) {
        Compteur compteur = new CompteurArbre();
        compteur.advancedWordCompter(new File("data/constitutionFrancaise.txt"), true);
        System.out.println(compteur);
    }
}
