package game.randomjumper.managers.ui;

import game.randomjumper.config.GameConfig;
import game.randomjumper.core.GamePanel;

import javax.swing.*;
import java.awt.*;

public class MenuManager {
    private static final JFrame window = new JFrame();
    private static JPanel menuPanel;

    public static void startMenu() {
        window.setTitle("Random Jumper");
        window.setSize(GameConfig.SCREEN_WIDTH, GameConfig.SCREEN_HEIGHT);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setLocationRelativeTo(null);
        window.setResizable(false);

        menuPanel = new JPanel(new GridBagLayout());
        menuPanel.setBackground(Color.BLACK);

        JButton startButton = createButton("Start Game");
        JButton exitButton = createButton("Exit");

        GridBagConstraints gbc = createGBC();
        menuPanel.add(startButton, gbc);
        menuPanel.add(exitButton, gbc);

        startButton.addActionListener(e -> startGame());
        exitButton.addActionListener(e -> System.exit(0));

        window.setContentPane(menuPanel);
        window.setVisible(true);
    }

    private static void startGame() {
        GamePanel gamePanel = new GamePanel();

        window.setContentPane(gamePanel);
        window.revalidate();
        window.repaint();

        SwingUtilities.invokeLater(gamePanel::requestFocusInWindow);
    }

    public static void tryAgainMenu(GamePanel oldGamePanel) {
        JFrame window = (JFrame) SwingUtilities.getWindowAncestor(oldGamePanel);
        if (window == null) return;

        oldGamePanel.stopGame();

        oldGamePanel.removeAll();
        oldGamePanel.setLayout(new GridBagLayout());

        JButton tryAgainButton = createButton("Try Again");
        JButton exitButton = createButton("Exit");

        GridBagConstraints gbc = createGBC();
        oldGamePanel.add(tryAgainButton, gbc);
        oldGamePanel.add(exitButton, gbc);

        tryAgainButton.addActionListener(e -> {
            GamePanel newGamePanel = new GamePanel();
            window.setContentPane(newGamePanel);
            window.revalidate();
            window.repaint();
            SwingUtilities.invokeLater(newGamePanel::requestFocusInWindow);
        });

        exitButton.addActionListener(e -> System.exit(0));

        oldGamePanel.revalidate();
        oldGamePanel.repaint();
    }

    private static JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(200, 50));
        button.setBackground(new Color(70, 70, 70));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        return button;
    }

    private static GridBagConstraints createGBC() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.NONE;
        gbc.insets = new Insets(10, 0, 10, 0);
        return gbc;
    }
}