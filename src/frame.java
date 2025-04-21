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
    boolean upPressed = false, downPressed = false, leftPressed = false, rightPressed = false, qPressed = false, ePressed = false;
    int moveTime, moveDir;
    int FPS = 60;
    double currentHealth = 5.5, maximumHealth = 8.0;
    String direction = "down";
    double distance;
    int slope;
    int b;

    Map<String, ImageIcon> playerImages = new HashMap<>();
    ArrayList<JLabel> obstacles = new ArrayList<>();
    ArrayList<JLabel> passables = new ArrayList<>();
    ArrayList<JLabel> tiles = new ArrayList<>();
    ArrayList<Tile> backgroundTiles = new ArrayList<>();
    playerMovement playerMovementInstance;
    Camera CameraInstance;
    public Point playerWorldPos = new Point(0, 0);
    public Point rockWorldPos = new Point(50, 50);
    public Point chestWorldPos = new Point(1000, 2000);
    JLabel coordinates = new JLabel();
    BackgroundPanel backgroundPanel = new BackgroundPanel(null);

    //JLabel cordBox = assets(20, 20, 75, 75, false, "images/GUI/coordinateBox.png", false);


    JLabel press = assets(175, 600, 640, 160, false, "images/GUI/pressE.png", false);

    JLabel pebble = assets(1000, 1000, 1000, 1000, true, "images/assets/pebble.png", false);
    Point pebbleWorldPos = new Point(1000, 1000);

    JLabel warp = assets(-1000, 1000, 200, 200, false, "images/assets/warpstone.png", false);
    Point warpWorldPos = new Point(-1000, 1000);

    JLabel rockTwo = assets(-500, -500, 200, 200, true, "images/assets/rock.png", false);
    Point rockTwoWorldPos = new Point(-500, -500);

    JLabel rockThird = assets(2500, 2500, 200, 200, false, "images/assets/rock.png",false);
    Point rockThirdWorldPos = new Point(2500, 2500);

    JLabel ghost = assets(1000, -1500, 100, 100, false, "images/mob/ghost.png",false);
    Point ghostWorldPos = new Point(1000, -1500);

    public static void Sequencer(String input) throws UnsupportedAudioFileException, IOException, LineUnavailableException{
        File file = new File(input);
        AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
        Clip clip = AudioSystem.getClip();
        clip.open(audioStream);


        float volume = 1f; // adjust volume here
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
            Sequencer("music/korok.wav"); // Play the clip when the program starts
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace(); // Handle exceptions
        }


        backgroundPanel.setLayout(null);

        //Background Tile System - place files named (position)x(position)y.png in the tiles folder
        int tileSize = 1600; // changes how far apart the tiles are placed
        File tileDir = new File("images/background/tiles");
        File[] tileFiles = tileDir.listFiles((dir, name) -> name.matches("\\d+x\\dy\\.png"));
        if (tileFiles != null) { 
            for (File tileFile : tileFiles) {
                String fileName = tileFile.getName();
                String[] parts = fileName.replace(".png", "").split("x|y");
                int tileX = Integer.parseInt(parts[0]);
                int tileY = Integer.parseInt(parts[1]);

                ImageIcon tileIcon = new ImageIcon(new ImageIcon(tileFile.getPath()).getImage().getScaledInstance(tileSize, tileSize, Image.SCALE_DEFAULT));
                JLabel tileLabel = new JLabel(tileIcon);
                tileLabel.setBounds(0, 0, tileSize, tileSize); // position will be updated in gameLoop
                tileLabel.setOpaque(false);
                backgroundPanel.add(tileLabel);
                
                Point worldPos = new Point(tileX * tileSize, -tileY * tileSize);
                backgroundTiles.add(new Tile(tileLabel, worldPos));

            }
        }



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
        //obstacles.add(chest);

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

    class Tile {
        JLabel label;
        Point worldPos;
    
        Tile(JLabel label, Point worldPos) {
            this.label = label;
            this.worldPos = worldPos;
        }
    }

    private void loadAndScalePlayerImages() {
        String[] imageNames = {"downStanding", "downFore", "downBack", "upStanding", "upFore", "upBack", "rightStanding", "rightFore", "rightBack", "leftStanding", "leftFore", "leftBack"};
        for (String name : imageNames) {
            ImageIcon icon = new ImageIcon("images/player/" + name + ".png");
            Image image = icon.getImage().getScaledInstance(100, 188, Image.SCALE_DEFAULT);
            playerImages.put(name, new ImageIcon(image));
        }
    }


    public JLabel assets(int x, int y, int width, int height, boolean obstacle, String filePath, boolean opaque) {
        ImageIcon Icon = new ImageIcon(new ImageIcon(filePath).getImage().getScaledInstance(width, height, Image.SCALE_DEFAULT));
        JLabel label = new JLabel(Icon);
        label.setBounds(x, y, width, height);
        if(obstacle) {
            obstacles.add(label);
        } else {
            passables.add(label);
        }

        if(opaque) {
            label.setOpaque(true);
        } else {
            label.setOpaque(false);
        }

        backgroundPanel.add(label);


        return label;
    }









    private void gameLoop() {
        long previousTime = System.nanoTime();
        double placeholder = 0;
        long currentTime;
        double timePerFrame = 1_000_000_000.0 / FPS;
        healthChange(0);


        while (true) {
            currentTime = System.nanoTime();
            placeholder += (currentTime - previousTime) / timePerFrame;
            previousTime = currentTime;
            if (placeholder >= 1) {
                interacting();
                playerMovementInstance.playerPosition();
                player.setBounds(super.getWidth()/2 - 50, super.getHeight()/2 - 100, player.getWidth(), player.getHeight());
                placeholder--;
                CameraInstance.position = playerWorldPos;
                coordinates.setText(playerWorldPos.getX() + " " + playerWorldPos.getY());
                //healthChange(0);

                for (Tile tile : backgroundTiles) {
                    Point screenPos = CameraInstance.worldToScreen(tile.worldPos);
                    tile.label.setLocation(screenPos);
                }

                rock.setLocation(CameraInstance.worldToScreen(rockWorldPos));
                chest.setLocation(CameraInstance.worldToScreen(chestWorldPos));
                pebble.setLocation(CameraInstance.worldToScreen(pebbleWorldPos));
                warp.setLocation(CameraInstance.worldToScreen(warpWorldPos));
                rockTwo.setLocation(CameraInstance.worldToScreen(rockTwoWorldPos));
                rockThird.setLocation(CameraInstance.worldToScreen(rockThirdWorldPos));
                ghostWorldPos = mobMovement((int) ghostWorldPos.getX(), (int) ghostWorldPos.getY(), 4);
                ghost.setLocation(CameraInstance.worldToScreen(ghostWorldPos));

                backgroundPanel.setComponentZOrder(player, 0);
                backgroundPanel.setComponentZOrder(ghost, 1);
                backgroundPanel.setComponentZOrder(rock, 2);
                backgroundPanel.setComponentZOrder(chest, 3);



            }
        }
    }


    public Point mobMovement(int x, int y, int mobSpeed) {

        distance = (int) Math.sqrt(Math.pow((playerWorldPos.x - x), 2) + Math.pow((playerWorldPos.y - y), 2));

        //step -= mobSpeed;

        if(distance <= 1000 && distance >= 50 && playerWorldPos.x != ghostWorldPos.x) {

            int distanceX = playerWorldPos.x - x;
            int distanceY = playerWorldPos.y - y;

            slope = (playerWorldPos.y - y) / (playerWorldPos.x - x);

            b = playerWorldPos.y - slope * playerWorldPos.x;


            if (distanceX == 0) {
                // Move vertically only
                if (playerWorldPos.y > y) {
                    y += mobSpeed;
                } else if (playerWorldPos.y < y) {
                    y -= mobSpeed;
                }
            } else {
                // Use double to avoid integer rounding
                double slope = (double) distanceY / distanceX;
                double b = playerWorldPos.y - slope * playerWorldPos.x;

                if (playerWorldPos.x > x) {
                    x += mobSpeed;
                } else if (playerWorldPos.x < x) {
                    x -= mobSpeed;
                }

                y = (int) (slope * x + b);
            }
        }

        return new Point(x, y);
    }


    public void armorIncrease(double armorInc) {
        double oldMaximumHealth = maximumHealth;
        maximumHealth += armorInc;
        healthChange(maximumHealth - oldMaximumHealth);
    }


    public void healthChange(double healthChange) {

        currentHealth += healthChange;
        if (currentHealth > maximumHealth) {
            currentHealth = maximumHealth;
        }
        if (currentHealth <= 0) {
            System.out.print("You died");
            //gameOver();
        }
        for (int i = 1; i <= currentHealth; i++) {
            JLabel fullHeart = assets(10 + (60 * (i-1)), 10, 50, 50, false, "images/GUI/fullHeart.png", false);
        }
        if (currentHealth % 1.0 != 0) {
            JLabel halfHeart = assets((int) (-20 + (60 * currentHealth)), 10, 50, 50, false, "images/GUI/halfHeart.png", false);
        }
        for (int i = 1; i <= maximumHealth; i++) {
            JLabel emptyHeart = assets(10 + (60 * (i-1)), 10, 50, 50, false, "images/GUI/emptyHeart.png", false);
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


    public void test () {

        if(qPressed) {

            player.setLocation(ghostWorldPos.x, ghostWorldPos.y);

        }

    }




    public void gameOver() {
        //do game end code here
    }

    public int create(int x, int y) {
        return 0;
    }

    public void interacting() {
        if(player.getBounds().intersects(warp.getBounds())) {
            playerWorldPos.setLocation(0, 0);

        }

        if(player.getBounds().intersects(chest.getBounds())) {

            press.setVisible(true);

            if(ePressed) {
                System.out.println("You opened the chest");

            }


        } else {
            press.setVisible(false);


        }


    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W -> playerMovementInstance.setUpPressed(true);
            case KeyEvent.VK_S -> playerMovementInstance.setDownPressed(true);
            case KeyEvent.VK_A -> playerMovementInstance.setLeftPressed(true);
            case KeyEvent.VK_D -> playerMovementInstance.setRightPressed(true);
            case KeyEvent.VK_Q -> qPressed = true;
            case KeyEvent.VK_E -> ePressed = true;
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
            case KeyEvent.VK_E -> ePressed = false;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}
}


