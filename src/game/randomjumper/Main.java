package game.randomjumper;

import game.randomjumper.managers.audio.SoundManager;
import game.randomjumper.managers.image.ImageManager;
import game.randomjumper.managers.ui.FontManager;
import game.randomjumper.managers.ui.MenuManager;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {

    static{
        SoundManager.preloadSounds();
        ImageManager.preLoadImages();
        FontManager.loadFont();
    }

    public static void main(String[] args)  {
        //start

        MenuManager.startMenu();

        // ahmad was here
    }
}