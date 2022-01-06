package view;

import Compteur.CompteurArbre;
import Compteur.CompteurHash;
import Compteur.CompteurList;
import Compteur.CompteurTableau;
import Utils.Utils;

import javax.swing.*;
import java.awt.*;

public class SettingView extends JFrame {
    private final MainPanel pan;

    public SettingView(MainPanel pan){
        super("Settings");
        this.pan = pan;
        setLayout(new GridBagLayout());
        setSize(250,100);
        Dimension dimScreen = getToolkit().getScreenSize();
        setLocation(dimScreen.width/2-getSize().width/2,dimScreen.height/2-getSize().height/2);

        GridBagConstraints c = Utils.gridBagConstaints();
        c.gridy=0;
        add(new JLabel("Compteur : "),c);
        JComboBox<String> comboBox = new JComboBox<>();
        comboBox.addItem("CompteurTableau");
        comboBox.addItem("CompteurList");
        comboBox.addItem("CompteurArbre");
        comboBox.addItem("CompteurHash");
        comboBox.setSelectedItem(pan.getCompteur().getClass().getSimpleName());
        add(comboBox,c);

        c.gridy=1;
        add(new JPanel(),c);
        c.weightx = 0.5;

        JPanel hbox = new JPanel();
        hbox.setLayout(new GridBagLayout());
        GridBagConstraints constraints = Utils.gridBagConstaints();
        JButton cancel = new JButton("Cancel");
        cancel.addActionListener((event)->dispose());
        hbox.add(cancel,constraints);
        JButton done = new JButton("Done");
        done.addActionListener((event->{
            switch (comboBox.getItemAt(comboBox.getSelectedIndex())){
                case "CompteurTableau":
                    pan.setCompteur(new CompteurTableau()); break;
                case "CompteurList":
                    pan.setCompteur(new CompteurList()); break;
                case "CompteurArbre":
                    pan.setCompteur(new CompteurArbre()); break;
                case "CompteurHash":
                    pan.setCompteur(new CompteurHash()); break;
                default:
            }
            dispose();
        }));
        hbox.add(done,constraints);
        add(hbox,c);

        setVisible(true);
    }
}
