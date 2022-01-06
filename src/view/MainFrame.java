package view;

import javax.swing.*;

public class MainFrame extends JFrame {

    public MainFrame(){
        super("Nuages de mots");
        setSize(getToolkit().getScreenSize());
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        MainPanel pan = new MainPanel();
        setMenuBar(new MainMenuBar(pan));
        add(pan);
        setVisible(true);
    }
}
