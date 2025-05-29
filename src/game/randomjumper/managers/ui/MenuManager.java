package game.randomjumper.managers.ui;

import game.randomjumper.config.GameConfig;
import game.randomjumper.core.GamePanel;

import javax.swing.*;
import java.awt.*;

public class MenuManager {
    private static final JFrame window = new JFrame();
    private static final JPanel menuPanel = new JPanel();
    private static final JButton startButton = new JButton("Start Game");
    private static final JButton exitButton = new JButton("Exit");
    private static final Dimension buttonSize = new Dimension(200, 50);
    private static final GridBagConstraints gbc = new GridBagConstraints();
    private static final GamePanel panel = new GamePanel();


    public static void startGame() {


        //set window parameters and settings
        window.setTitle("Random Platformer");
        window.setSize(GameConfig.SCREEN_WIDTH, GameConfig.SCREEN_HEIGHT);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setLocationRelativeTo(null);
        window.setResizable(false);


        // Create a menu panel with layout
        menuPanel.setLayout(new GridBagLayout());
        menuPanel.setBackground(Color.BLACK);

        // Create buttons with styling

        // Style buttons
        startButton.setPreferredSize(buttonSize);
        exitButton.setPreferredSize(buttonSize);

        startButton.setBackground(new Color(70, 70, 70));
        exitButton.setBackground(new Color(70, 70, 70));
        startButton.setForeground(Color.WHITE);
        exitButton.setForeground(Color.WHITE);
        startButton.setFocusPainted(false);
        exitButton.setFocusPainted(false);

        // Layout constraints
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.NONE;
        gbc.insets = new Insets(10, 0, 10, 0);

        // Add buttons to menu panel
        menuPanel.add(startButton, gbc);
        menuPanel.add(exitButton, gbc);

        // Add action listeners
        startButton.addActionListener(e ->  {
            window.remove(menuPanel);
            window.add(panel);
            window.revalidate();
            window.repaint();
            window.setFocusable(false);
            window.setFocusable(true);
            panel.requestFocus();
        });

        exitButton.addActionListener(e -> System.exit(0));

        // Add a menu panel to the window
        window.add(menuPanel);

        window.setVisible(true);

    }
}
