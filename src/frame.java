import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class frame extends JFrame implements KeyListener {

    JLabel rock;
    JLabel player;
    JLabel chest;

    int x, y;
    int step = 6;
    boolean upPressed = false, downPressed = false, leftPressed = false, rightPressed = false, qPressed = false;
    int moveTime, moveDir;
    int FPS = 60;
    int currentHealth = 3, maximumHealth = 3;
    String direction = "down";

    Map<String, ImageIcon> playerImages = new HashMap<>();
    Map<String, ImageIcon> assetsImages = new HashMap<>();
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
        player.setOpaque(true);

        x = player.getX();
        y = player.getY();

        ImageIcon rockIcon = new ImageIcon(new ImageIcon("images/assets/rock.png").getImage().getScaledInstance(150, 150, Image.SCALE_DEFAULT));
        rock = new JLabel(rockIcon);
        rock.setBounds(300, 600, 90, 90);
        obstacles.add(rock);


        chest = new JLabel();
        chest.setBounds(500, 500, 150, 150);
        ImageIcon chestIcon = new ImageIcon(new ImageIcon("images/assets/chest.png").getImage().getScaledInstance(chest.getWidth(), chest.getHeight(), Image.SCALE_SMOOTH));
        chest.setIcon(chestIcon);
        obstacles.add(chest);

        backgroundPanel.add(rock);
        rock.setOpaque(true);

        backgroundPanel.add(chest);
        chest.setOpaque(true);
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
            Image image = icon.getImage().getScaledInstance(100, 200, Image.SCALE_DEFAULT);
            playerImages.put(name, new ImageIcon(image));
        }
    }

    /*private void loadAndScaleAssetImages() {
        String[] imageNames = {"chest", "rock"};
        for (String name : imageNames) {
            ImageIcon icon = new ImageIcon("images/assets/" + name + ".png");
            Image image = icon.getImage().getScaledInstance(100, 200, Image.SCALE_SMOOTH);
            assetsImages.put(name, new ImageIcon(image));
        }
    } */

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
                playerHealth();
                displayHeart();
            }
        }
    }

    public void playerHealth() {
        if (currentHealth <= 0) {
            System.out.print("You died");
            gameOver();
        }
    }

    public void apple() {
        currentHealth = maximumHealth;
        System.out.println("You consumed an apple");
    }

    public void gameOver() {
        //do game end code here
    }

    public void displayHeart() {


        ImageIcon heartIcon = new ImageIcon("images/GUI/fullHeart.png");
        heartIcon = new ImageIcon(heartIcon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH));
        JLabel heartLabel = new JLabel(heartIcon);
        heartLabel.setBounds(10, 10, 50, 50);
        add(heartLabel);
        heartLabel.setOpaque(false);


        if(currentHealth % 2 == 0) {

            for(int i = 0; i <= currentHealth / 2; i++) {






            }




        } else {


        }



        revalidate();
        repaint();
    }



    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W -> playerMovementInstance.setUpPressed(true);
            case KeyEvent.VK_S -> playerMovementInstance.setDownPressed(true);
            case KeyEvent.VK_A -> playerMovementInstance.setLeftPressed(true);
            case KeyEvent.VK_D -> playerMovementInstance.setRightPressed(true);
            case KeyEvent.VK_Q -> qPressed = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W -> playerMovementInstance.setUpPressed(false);
            case KeyEvent.VK_S -> playerMovementInstance.setDownPressed(false);
            case KeyEvent.VK_A -> playerMovementInstance.setLeftPressed(false);
            case KeyEvent.VK_D -> playerMovementInstance.setRightPressed(false);
            case KeyEvent.VK_Q -> qPressed = false;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}
}