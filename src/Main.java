import javax.swing.*;
import java.awt.*;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        //define a new window
        JFrame window = new JFrame();

        //set window parameters and settings
        window.setTitle("Random Platformer");
        window.setSize(GameConfig.SCREEN_WIDTH, GameConfig.SCREEN_HEIGHT);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setLocationRelativeTo(null);
        window.setResizable(false);

        // Create menu panel with layout
        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new GridBagLayout());
        menuPanel.setBackground(Color.BLACK);

        // Create buttons with styling
        JButton startButton = new JButton("Start Game");
        JButton exitButton = new JButton("Exit");

        // Style buttons
        Dimension buttonSize = new Dimension(200, 50);
        startButton.setPreferredSize(buttonSize);
        exitButton.setPreferredSize(buttonSize);

        startButton.setBackground(new Color(70, 70, 70));
        exitButton.setBackground(new Color(70, 70, 70));
        startButton.setForeground(Color.WHITE);
        exitButton.setForeground(Color.WHITE);
        startButton.setFocusPainted(false);
        exitButton.setFocusPainted(false);

        // Layout constraints
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.NONE;
        gbc.insets = new Insets(10, 0, 10, 0);

        // Add buttons to menu panel
        menuPanel.add(startButton, gbc);
        menuPanel.add(exitButton, gbc);

        // Add action listeners
        startButton.addActionListener(e -> {
            window.remove(menuPanel);
            GamePanel panel = new GamePanel();
            window.add(panel);
            window.revalidate();
            window.repaint();
            window.setFocusable(false);
            window.setFocusable(true);
            panel.requestFocus();
        });

        exitButton.addActionListener(e -> System.exit(0));

        // Add menu panel to window
        window.add(menuPanel);

        window.setVisible(true);

        // ahmad was here
    }
}