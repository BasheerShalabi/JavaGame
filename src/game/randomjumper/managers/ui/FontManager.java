package game.randomjumper.managers.ui;

import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.io.IOException;

public class FontManager {

    public static void loadFont() {
        try {
            File fontFile = new File("resources/fonts/joystix monospace.otf");
            Font customFont = Font.createFont(Font.TRUETYPE_FONT, fontFile);

            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(customFont);

            System.out.println("Font loaded successfully: " + customFont.getFontName());

        } catch (IOException | java.awt.FontFormatException e) {
            System.err.println("Error loading font: " + e.getMessage());
        }
    }
}
