import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class frame extends JFrame implements KeyListener {

    JLabel rock;
    JLabel player;

    int x, y;
    int step = 6;
    boolean upPressed = false, downPressed = false, leftPressed = false, rightPressed = false;
    int moveTime, moveDir;
    int FPS = 60;
    String direction = "down";

    Map<String, ImageIcon> playerImages = new HashMap<>();
    ArrayList<JLabel> obstacles = new ArrayList<>();
    playerMovement playerMovementInstance;

    frame() {
        super("Pumpkin Quest");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1500, 1000);
        setLocationRelativeTo(null);
        setResizable(false);

        BackgroundPanel backgroundPanel = new BackgroundPanel("images/background/forest.png");
        backgroundPanel.setLayout(null);

        loadAndScalePlayerImages();

        player = new JLabel(playerImages.get("downStanding"));
        player.setBounds(0, 0, 100, 200);
        player.setOpaque(false);

        x = player.getX();
        y = player.getY();

        ImageIcon rockIcon = new ImageIcon(new ImageIcon("images/assets/rock.png").getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH));
        rock = new JLabel(rockIcon);
        rock.setBounds(300, 600, 100, 100);
        obstacles.add(rock);

        backgroundPanel.add(rock);
        rock.setOpaque(false);
        backgroundPanel.add(player);

        setContentPane(backgroundPanel);
        addKeyListener(this);
        setVisible(true);

        playerMovementInstance = new playerMovement(player, obstacles, playerImages, x, y, step, FPS, direction, upPressed, downPressed, leftPressed, rightPressed);
        moveDir = 1;
        gameLoop();
    }

    private void loadAndScalePlayerImages() {
        String[] imageNames = {"downStanding", "downFore", "downBack"};
        for (String name : imageNames) {
            ImageIcon icon = new ImageIcon("images/player/" + name + ".png");
            Image image = icon.getImage().getScaledInstance(100, 200, Image.SCALE_SMOOTH);
            playerImages.put(name, new ImageIcon(image));
        }
    }

    private void gameLoop() {
        long previousTime = System.nanoTime();
        double placeholder = 0;
        long currentTime;
        double timePerFrame = 1_000_000_000.0 / FPS;

        while (true) {
            currentTime = System.nanoTime();
            placeholder += (currentTime - previousTime) / timePerFrame;
            previousTime = currentTime;
            if (placeholder >= 1) {
                playerMovementInstance.playerPosition();
                placeholder--;
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W -> playerMovementInstance.setUpPressed(true);
            case KeyEvent.VK_S -> playerMovementInstance.setDownPressed(true);
            case KeyEvent.VK_A -> playerMovementInstance.setLeftPressed(true);
            case KeyEvent.VK_D -> playerMovementInstance.setRightPressed(true);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W -> playerMovementInstance.setUpPressed(false);
            case KeyEvent.VK_S -> playerMovementInstance.setDownPressed(false);
            case KeyEvent.VK_A -> playerMovementInstance.setLeftPressed(false);
            case KeyEvent.VK_D -> playerMovementInstance.setRightPressed(false);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}
}