package view;

import Compteur.*;
import Utils.Utils;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;

public class MainPanel extends JPanel {
    private Compteur compteur;
    private File file;
    private InfoPanel infoPanel;
    private NuageView nuageView;
    private int nbMot;
    private int nbMot5;
    private long time;
    private ArrayList<Element> top10;
    private ArrayList<Element> top10_5;

    public MainPanel() {
        compteur = new CompteurHash();
        file = new File("");
        nbMot = -1;
        nbMot5 = -1;
        time = -1;
        top10 = null;
        top10_5 = null;
        nuageView = new NuageView();
        infoPanel = new InfoPanel(this);

        setLayout(new GridBagLayout());
        setBackground(Color.BLACK);
        GridBagConstraints c = Utils.gridBagConstaints();
        c.insets = new Insets(1,1,1,1);
        add(nuageView,c);
        c.weightx = 0.10;
        add(infoPanel,c);
    }

    public Compteur getCompteur() {
        return compteur;
    }

    public void setCompteur(Compteur compteur) {
        this.compteur = compteur;
        infoPanel.update(this);
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
        infoPanel.update(this);
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

    public ArrayList<Element> getTop10() {
        return top10;
    }

    public ArrayList<Element> getTop10_5() {
        return top10_5;
    }

    public void compter(){
        compteur.advancedWordCompter(file);
        nbMot = compteur.getNbMot();
        nbMot5 = compteur.getNbMot5();
        time = compteur.getTime();
        top10 = compteur.getTop10();
        top10_5 = compteur.getTop10_5();
        infoPanel.update(this);
        nuageView.update(this);
    }
}
