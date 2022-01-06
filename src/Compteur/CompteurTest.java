package Compteur;

import java.io.File;
import java.util.ArrayList;

public class CompteurTest extends Compteur{
    @Override
    protected void addElement(String element) {
        System.out.println(element);
    }

    @Override
    protected ArrayList<Element> getElementInOrder() {
        return new ArrayList<>();
    }

    public static void main(String[] args) {
        Compteur compteur = new CompteurTest();
        compteur.addTabooWord("test");
        compteur.addTabooWord("texte");
        compteur.advancedWordCompter(new File("data/test.txt"));
        compteur.removeTabooWord("texte");
        System.out.println("\nblacklist : "+compteur.isBlacklistActivate()+'\n');
        compteur.setBlacklistActivate(false);
        compteur.advancedWordCompter(new File("data/test.txt"));
        System.out.println('\n'+compteur.toString());
    }
}
