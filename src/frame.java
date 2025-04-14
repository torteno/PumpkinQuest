import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.io.File;
import java.io.IOException;
import javax.sound.sampled.*;


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
    ArrayList<JLabel> passables = new ArrayList<>();
    playerMovement playerMovementInstance;
    Camera CameraInstance;
    public Point playerWorldPos = new Point(0, 0);
    public Point rockWorldPos = new Point(50, 50);
    public Point chestWorldPos = new Point(1000, 2000);
    JLabel coordinates = new JLabel();
    BackgroundPanel backgroundPanel = new BackgroundPanel("images/background/forest.png");

    JLabel pebble = assets(1000, 1000, 1000, 1000, true, "images/assets/pebble.png");
    Point pebbleWorldPos = new Point(1000, 1000);

    JLabel warp = assets(-1000, 1000, 200, 200, false, "images/assets/warpstone.png");
    Point warpWorldPos = new Point(-1000, 1000);

    JLabel rockTwo = assets(-500, -500, 200, 200, true, "images/assets/rock.png");
    Point rockTwoWorldPos = new Point(-500, -500);




    public void Sequencer() throws UnsupportedAudioFileException, IOException, LineUnavailableException{
        File file = new File("music/korok.wav");
        AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
        Clip clip = AudioSystem.getClip();
        clip.open(audioStream);


        float volume = 1f; //adjust volume here, I wil add a slider later but rn I gotta go to dinner
        FloatControl volumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        float dB = (float) (Math.log10(volume) * 20); // Convert volume (0.0 to 1.0) to decibels
        volumeControl.setValue(dB);


        clip.start();
        clip.loop(10);

    }




    frame() {



        super("Pumpkin Quest");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 800);
        setLocationRelativeTo(null);
        setResizable(false);


        try {
            Sequencer(); // Play the clip when the program starts
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace(); // Handle exceptions
        }


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
        String[] imageNames = {"downStanding", "downFore", "downBack", "upStanding", "upFore", "upBack", "rightStanding", "rightFore", "rightBack", "leftStanding", "leftFore", "leftBack"};
        for (String name : imageNames) {
            ImageIcon icon = new ImageIcon("images/player/" + name + ".png");
            Image image = icon.getImage().getScaledInstance(100, 188, Image.SCALE_DEFAULT);
            playerImages.put(name, new ImageIcon(image));
        }
    }


    public JLabel assets(int x, int y, int width, int height, boolean obstacle, String filePath) {
        ImageIcon Icon = new ImageIcon(new ImageIcon(filePath).getImage().getScaledInstance(width, height, Image.SCALE_DEFAULT));
        JLabel label = new JLabel(Icon);
        label.setBounds(x, y, width, height);
        if(obstacle) {
            obstacles.add(label);
        } else {
            passables.add(label);
        }

        backgroundPanel.add(label);
        label.setOpaque(false);


        return label;
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
                CameraInstance.position = playerWorldPos;
                coordinates.setText(playerWorldPos.getX() + " " + playerWorldPos.getY());



                rock.setLocation(CameraInstance.worldToScreen(rockWorldPos));
                chest.setLocation(CameraInstance.worldToScreen(chestWorldPos));
                pebble.setLocation(CameraInstance.worldToScreen(pebbleWorldPos));
                warp.setLocation(CameraInstance.worldToScreen(warpWorldPos));
                rockTwo.setLocation(CameraInstance.worldToScreen(rockTwoWorldPos));




            }
        }
    }

    public void healthChange(int healthChange) {

        currentHealth += healthChange;
        if (currentHealth > maximumHealth) {
            currentHealth = maximumHealth;
        }
        if (currentHealth <= 0) {
            System.out.print("You died");
            gameOver();
        }
        for (int i = 0; i == maximumHealth; i++) {
            JLabel emptyHeart = assets(10 + (60 * i), 10, 50, 50, false, "images/GUI/emptyHeart.png");
        }
        for (int i = 0; i == currentHealth; i++) {
            JLabel fullHeart = assets(10 + (60 * i), 10, 50, 50, false, "images/GUI/fullHeart.png");
        }


    }


    public void chest() {

        if(playerWorldPos == chestWorldPos) {
            System.out.println("You opened the chest");
            currentHealth = maximumHealth;
        } else {
            System.out.println("You are not close enough to the chest");
        }


    }

    public void gameOver() {
        //do game end code here
    }

    public int create(int x, int y) {
        return 0;
    }

    public void interacting() {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W -> playerMovementInstance.setUpPressed(true);
            case KeyEvent.VK_S -> playerMovementInstance.setDownPressed(true);
            case KeyEvent.VK_A -> playerMovementInstance.setLeftPressed(true);
            case KeyEvent.VK_D -> playerMovementInstance.setRightPressed(true);
            case KeyEvent.VK_Q -> qPressed = true;
            case KeyEvent.VK_E -> interacting();
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


