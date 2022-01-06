package view;

import Compteur.Element;
import Utils.Utils;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;

public class NuageView extends JPanel {
    public NuageView() {
        setMinimumSize(new Dimension(0,0));
        Dimension screenSize = getToolkit().getScreenSize();
        setMaximumSize(new Dimension((int) (screenSize.width*0.8), screenSize.height));
        setPreferredSize(new Dimension((int) (screenSize.width*0.8), screenSize.height-200));
    }

    public void update(MainPanel pan){
        removeAll();
        repaint();
        ArrayList<Element> l = pan.getCompteur().getElements();
        int n = l.get(0).nb;
        Collections.shuffle(l);
        for (Element e : l){
            JLabel label = new JLabel(e.word);
            label.setOpaque(false);
            label.setFont(label.getFont().deriveFont((float) (e.nb*90)/n+10));
            add(label);
        }
    }
}
