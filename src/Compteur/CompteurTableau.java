package Compteur;

import java.io.File;
import java.util.ArrayList;
import java.util.Objects;

public class CompteurTableau extends Compteur{
    //attribute
    private static final int TAILLE_INITIAL = 100;
    private int nbMot;
    private Element[] tab;

    //constructor
    public CompteurTableau(String[] taboolist) {
        super(taboolist);
        tab = new Element[TAILLE_INITIAL];
        nbMot = 0;
    }

    public CompteurTableau() {
        this(new String[]{});
    }

    //methode
    @Override
    protected void addElement(String element) {
        for (int i=0; i<nbMot; i++){
            Element e = tab[i];
            if (Objects.equals(e.word, element)){
                e.nb++;
                return;
            }
        }

        tab[nbMot] = new Element(element);
        nbMot++;

        if (nbMot>= tab.length){
            Element[] newTab = new Element[tab.length*2];
            System.arraycopy(tab, 0, newTab, 0, nbMot);
            tab = newTab;
        }
    }

    @Override
    protected ArrayList<Element> getElementInOrder() {
        ArrayList<Element> l = new ArrayList<>();
        for (int i=0; i<nbMot; i++){
            insertInArraySort(l,tab[i]);
        }
        return l;
    }

    public static void main(String[] args) {
        Compteur compteur = new CompteurTableau();
        compteur.advancedWordCompter(new File("data/encyclopedie1.txt"), true);
        System.out.println(compteur);
    }
}
