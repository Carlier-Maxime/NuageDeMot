package view;

import java.awt.*;

public class MainMenuBar extends MenuBar {
    public MainMenuBar(MainPanel pan) {
        // menu file
        Menu file = new Menu("File");
        MenuItem open = new MenuItem("open..");
        file.add(open);
        open.addActionListener((event)->{new OpenView(pan);});
        add(file);
        // menu settings
        Menu settings = new Menu("Settings");
        MenuItem setting = new MenuItem("setting..");
        setting.addActionListener((event) -> {new SettingView(pan);});
        settings.add(setting);
        add(settings);
    }
}
