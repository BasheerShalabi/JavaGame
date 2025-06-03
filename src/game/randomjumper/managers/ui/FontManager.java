package game.randomjumper.managers.ui;

import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.io.IOException;
import java.io.InputStream;

public class FontManager {

    public static void loadFont() {
        try {
            InputStream fontFile = FontManager.class.getResourceAsStream("/fonts/joystix monospace.otf");
            assert fontFile != null;
            Font customFont = Font.createFont(Font.TRUETYPE_FONT, fontFile);

            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(customFont);

        } catch (IOException | java.awt.FontFormatException e) {
            System.err.println("Error loading font: " + e.getMessage());
        }
    }
}
