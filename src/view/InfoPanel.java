package view;

import Compteur.Element;
import Utils.Utils;

import javax.swing.*;
import java.awt.*;

public class InfoPanel extends JPanel {
    private JLabel labelNameCompteur;
    private JLabel labelFile;
    private JLabel labelNbMot;
    private JLabel labelNbMot5;
    private JLabel[] labelTop10;
    private JLabel[] labelTop10_5;
    private JLabel labelTime;

    public InfoPanel(MainPanel pan) {
        labelNameCompteur = new JLabel("Compteur : ???");
        labelFile = new JLabel("File : ???");
        labelNbMot = new JLabel("Nombre de mots : ???");
        labelNbMot5 = new JLabel("Nombre de mots >= 5 : ???");
        labelTop10 = new JLabel[10];
        labelTop10_5 = new JLabel[10];
        labelTime = new JLabel("temps execution : ??? ms");
        labelTime.setToolTipText("Le temps utilisée pour afficher les info n'est pas pris en compte !");

        setLayout(new GridBagLayout());
        GridBagConstraints c = Utils.gridBagConstaints();
        c.gridx=0;
        add(labelNameCompteur,c);
        add(labelFile,c);
        add(labelNbMot,c);
        add(labelNbMot5,c);

        JLabel top10 = new JLabel("TOP 10 des mots :");
        top10.setToolTipText("Top 10 des mots les plus present");
        add(top10,c);
        for (int i=0; i<10; i++){
            labelTop10[i] = new JLabel("    - ???");
            add(labelTop10[i],c);
        }
        JLabel top10_5 = new JLabel("TOP 10 des mots >= 5 :");
        top10_5.setToolTipText("Top 10 des mots les plus present ayant une longueur supérieur a 5");
        add(top10_5,c);
        for (int i=0; i<10; i++){
            labelTop10_5[i] = new JLabel("    - ???");
            add(labelTop10_5[i],c);
        }

        add(labelTime,c);
        update(pan);
    }

    public void update(MainPanel pan){
        labelNameCompteur.setText("Compteur : "+pan.getCompteur().getClass().getSimpleName());
        String nameFile = pan.getFile().getName();
        labelFile.setText("File : "+(nameFile.equals("") ?"???":nameFile));
        labelFile.setToolTipText(pan.getFile().getAbsolutePath());
        String nbMot = Integer.toString(pan.getNbMot());
        labelNbMot.setText("Nombre de mots : "+(nbMot.equals("-1") ?"???":nbMot));
        String nbMot5 = Integer.toString(pan.getNbMot5());
        labelNbMot5.setText("Nombre de mots >= 5 : "+(nbMot5.equals("-1")?"???":nbMot5));

        for (int i=0; i<10; i++){
            try {
                Element e = pan.getTop10().get(i);
                labelTop10[i].setText("    - "+e.word+" : "+e.nb);
            } catch (Exception e) {labelTop10[i].setText("    - ???");}
            try {
                Element e5 = pan.getTop10_5().get(i);
                labelTop10_5[i].setText("    - "+e5.word+" : "+e5.nb);
            } catch (Exception e) {labelTop10_5[i].setText("    - ???");}
        }

        String time = Long.toString(pan.getTime());
        labelTime.setText("temps execution : "+(time.equals("-1")?"???":time)+" ms");
    }
}
