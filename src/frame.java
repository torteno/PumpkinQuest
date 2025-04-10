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


    public Point position;
    public int screenWidth, screenHeight;

    int x, y;
    int step = 6;
    boolean upPressed = false, downPressed = false, leftPressed = false, rightPressed = false, qPressed = false;
    int moveTime, moveDir;
    int FPS = 60;
    int currentHealth = 3, maximumHealth = 3;
    String direction = "down";

    Map<String, ImageIcon> playerImages = new HashMap<>();
    ArrayList<JLabel> obstacles = new ArrayList<>();
    playerMovement playerMovementInstance;
    Camera CameraInstance;
    public Point playerWorldPos = new Point(0, 0);
    public Point rockWorldPos = new Point(50, 50);
    public Point chestWorldPos = new Point(1000, 2000);
    JLabel coordinates = new JLabel();






    frame() {



        super("Pumpkin Quest");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 800);
        setLocationRelativeTo(null);
        setResizable(false);

        BackgroundPanel backgroundPanel = new BackgroundPanel("images/background/forest.png");
        backgroundPanel.setLayout(null);

        loadAndScalePlayerImages();

        Point playerPoint = new Point(0, 0);
        Point rockpoint = new Point(300, 600);
        Point chestPoint = new Point(1000, 2000);

        player = new JLabel(playerImages.get("downStanding"));
        player.setBounds(super.getWidth()/2 - 50, super.getHeight()/2 - 100, 100, 188 );
        player.setOpaque(false);

        x = player.getX();
        y = player.getY();

        CameraInstance = new Camera(super.getWidth(), super.getHeight());








    ImageIcon rockIcon = new ImageIcon(new ImageIcon("images/assets/rock.png").getImage().getScaledInstance(150, 150, Image.SCALE_DEFAULT));
        rock = new JLabel(rockIcon);
        rock.setBounds(300, 600, 100, 100);
        obstacles.add(rock);

        backgroundPanel.add(rock);
        rock.setOpaque(false);


        ImageIcon chestIcon = new ImageIcon(new ImageIcon("images/assets/chest.png").getImage().getScaledInstance(150, 150, Image.SCALE_DEFAULT));
        chest = new JLabel(chestIcon);
        chest.setBounds(1000, 2000, 150, 150);
        obstacles.add(chest);

        backgroundPanel.add(chest);
        chest.setOpaque(true);
        backgroundPanel.add(player);

        setContentPane(backgroundPanel);
        addKeyListener(this);
        setVisible(true);

        coordinates.setBounds(0, 0, 100, 100);
        super.add(coordinates);

        playerMovementInstance = new playerMovement(player, obstacles, playerImages, x, y, step, FPS, direction, upPressed, downPressed, leftPressed, rightPressed, playerWorldPos);
        moveDir = 1;
        gameLoop();
    }

    private void loadAndScalePlayerImages() {
        String[] imageNames = {"downStanding", "downFore", "downBack", "upStanding", "upFore", "upBack"};
        for (String name : imageNames) {
            ImageIcon icon = new ImageIcon("images/player/" + name + ".png");
            Image image = icon.getImage().getScaledInstance(100, 188, Image.SCALE_DEFAULT);
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
                player.setBounds(super.getWidth()/2 - 50, super.getHeight()/2 - 100, player.getWidth(), player.getHeight());
                placeholder--;
                playerHealth();
                CameraInstance.position = playerWorldPos;
                coordinates.setText(playerWorldPos.getX() + " " + playerWorldPos.getY());
                rock.setLocation(CameraInstance.worldToScreen(rockWorldPos));
                chest.setLocation(CameraInstance.worldToScreen(chestWorldPos));


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

    public int create(int x, int y) {
        return 0;
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


