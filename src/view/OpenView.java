package view;

import Utils.Utils;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class OpenView extends JFrame {
    private MainPanel pan;

    public OpenView(MainPanel pan){
        super("Open file (txt)");
        this.pan = pan;

        setLayout(new GridBagLayout());
        setSize(750,50);
        Dimension dimScreen = getToolkit().getScreenSize();
        setLocation(dimScreen.width/2-getSize().width/2,dimScreen.height/2-getSize().height/2);

        GridBagConstraints c = Utils.gridBagConstaints();
        add(new JLabel("Path : "));
        JTextArea textArea = new JTextArea();
        add(textArea,c);
        JButton done = new JButton("Done");
        done.addActionListener((event)->{
            File file = new File(textArea.getText());
            if(file.exists()){
                pan.setFile(file);
                pan.compter();
                dispose();
            } else {
                JOptionPane.showMessageDialog(this,"Fichier introuvable !\nChemin complet : "+file.getAbsolutePath());
            }
        });
        add(done);

        setVisible(true);
    }
}
