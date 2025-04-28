import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.io.File;
import java.io.IOException;
import javax.sound.sampled.*;
import java.awt.image.BufferedImage;
import java.util.UUID;


public class frame extends JFrame implements KeyListener {

    JLabel rock;
    JLabel player;
    JLabel chest;

    JLabel startScreen;
    boolean startScreenVisible = true;


    public Point position;
    public int screenWidth, screenHeight;

    int x, y;
    int step = 6;
    boolean upPressed = false, downPressed = false, leftPressed = false, rightPressed = false, qPressed = false, ePressed = false, plusPressed = false, minusPressed = false;
    int moveTime, moveDir;
    int FPS = 60;
    double currentHealth = 3.0, maximumHealth = 3.0;
    String direction = "down";
    double distance;
    double slope;
    double b;
    public static float volume = 1f;
    boolean GUIOpen = true;


    Map <UUID, JLabel> mob = new HashMap<>();
    Map <UUID, Integer> MobHealth = new HashMap<>();
    Map <UUID, Integer> MobDamage = new HashMap<>();
    Map <UUID, Integer> MobReach = new HashMap<>();
    Map <UUID, Integer> MobSpeed = new HashMap<>();
    Map <UUID, Integer> MobFollowDistance = new HashMap<>();


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
    static Clip clip;

    int swordUpgrade = 0;

    //JLabel cordBox = assets(20, 20, 75, 75, false, "images/GUI/coordinateBox.png", false);


    JLabel press = assets(175, 600, 640, 160, false, "images/GUI/pressE.png", false, 1);

    JLabel pebble = assets(1000, 1000, 1000, 1000, true, "images/assets/pebble.png", false, 4);
    Point pebbleWorldPos = new Point(1000, 1000);

    JLabel warp = assets(-1000, 1000, 200, 200, false, "images/assets/warpstone.png", false, 4);
    Point warpWorldPos = new Point(-1000, 1000);

    JLabel rockTwo = assets(-500, -500, 200, 200, true, "images/assets/rock.png", false, 4);
    Point rockTwoWorldPos = new Point(-500, -500);

    JLabel rockThird = assets(2500, 2500, 200, 200, false, "images/assets/rock.png", false, 4);
    Point rockThirdWorldPos = new Point(2500, 2500);

    JLabel ghost = assets(1000, -1500, 100, 100, false, "images/mob/ghost.png", false, 1);
    Point ghostWorldPos = new Point(1000, -1500);


    JLabel ghostTwo = mobCreation(750, 200, 100, 100, "images/mob/ghost.png", 1, 100, 1, 100, 3, 5000);
    Point ghostTwoWorldPos = new Point(750, 200);


    public static void Sequencer(String input) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        File file = new File(input);
        AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
        clip = AudioSystem.getClip();
        clip.open(audioStream);


        //float volume = 1f; // adjust volume here
        volumeChange(0);

        clip.start();
        clip.loop(10);

    }


    frame() {


        super("Pumpkin Quest");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 800);
        setLocationRelativeTo(null);
        setResizable(true);



        Image icon = new ImageIcon("images/mob/ghost").getImage();

        setIconImage(icon);

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
                backgroundPanel.setComponentZOrder(tileLabel, 4);

                Point worldPos = new Point(tileX * tileSize, -tileY * tileSize);
                backgroundTiles.add(new Tile(tileLabel, worldPos));

            }
        }

        startScreen = new JLabel(new ImageIcon(new ImageIcon("images/GUI/startScreen.png").getImage().getScaledInstance(1040, 780, Image.SCALE_DEFAULT)));
        startScreen.setBounds(0,-10, getWidth(), getHeight());
        startScreen.setOpaque(false);
        backgroundPanel.add(startScreen);
        backgroundPanel.setComponentZOrder(startScreen, 0);


        loadAndScalePlayerImages();

        Point playerPoint = new Point(0, 0);
        Point rockpoint = new Point(300, 600);
        Point chestPoint = new Point(1000, 2000);

        player = new JLabel(playerImages.get("downStanding"));
        player.setBounds(super.getWidth() / 2 - 50, super.getHeight() / 2 - 100, 100, 188);
        player.setOpaque(false);
        backgroundPanel.setComponentZOrder(player, 0);

        x = player.getX();
        y = player.getY();
        playerWorldPos.setLocation(2360, -678);
        CameraInstance = new Camera(super.getWidth(), super.getHeight());


        ImageIcon rockIcon = new ImageIcon(new ImageIcon("images/assets/rock.png").getImage().getScaledInstance(150, 150, Image.SCALE_DEFAULT));
        rock = new JLabel(rockIcon);
        rock.setBounds(300, 600, 100, 100);
        obstacles.add(rock);

        backgroundPanel.add(rock);
        backgroundPanel.setComponentZOrder(rock, 2);
        rock.setOpaque(false);


        ImageIcon chestIcon = new ImageIcon(new ImageIcon("images/assets/chest.png").getImage().getScaledInstance(150, 150, Image.SCALE_DEFAULT));
        chest = new JLabel(chestIcon);
        chest.setBounds(1000, 2000, 150, 150);
        //obstacles.add(chest);

        backgroundPanel.add(chest);
        chest.setOpaque(true);
        backgroundPanel.add(player);
        backgroundPanel.setComponentZOrder(chest, 1);

        setContentPane(backgroundPanel);
        addKeyListener(this);
        setVisible(true);

        coordinates.setBounds(900, 700, 100, 100);

        super.add(coordinates);

        backgroundPanel.setComponentZOrder(coordinates, 0);

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


    public JLabel assets(int x, int y, int width, int height, boolean obstacle, String filePath, boolean opaque, int zOrder) {
        ImageIcon Icon = new ImageIcon(new ImageIcon(filePath).getImage().getScaledInstance(width, height, Image.SCALE_DEFAULT));
        JLabel label = new JLabel(Icon);
        label.setBounds(x, y, width, height);
        if (obstacle) {
            obstacles.add(label);
        } else {
            passables.add(label);
        }

        if (opaque) {
            label.setOpaque(true);
        } else {
            label.setOpaque(false);
        }

        backgroundPanel.add(label);


        int maxZOrder = backgroundPanel.getComponentCount() - 1; // fixed with gpt
        zOrder = Math.max(0, Math.min(zOrder, maxZOrder)); // fixed with gpt
        backgroundPanel.setComponentZOrder(label, zOrder);


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
                interacting();
                playerMovementInstance.playerPosition();
                player.setBounds(super.getWidth() / 2 - 50, super.getHeight() / 2 - 100, player.getWidth(), player.getHeight());
                placeholder--;
                CameraInstance.position = playerWorldPos;

                //uncomment this when we want to submit
               // coordinates.setText((int) playerWorldPos.getX() - 2360 + " " + (int) ((playerWorldPos.getY() + 678) * -1));


                //comment this when we want to submit
                coordinates.setText((int) playerWorldPos.getX() + " " + (int) playerWorldPos.getY());
                //healthChange(0);

                //System.out.println(ghostWorldPos);

                if(!GUIOpen) {
                    backgroundPanel.setComponentZOrder(player, 0);
                }


                //mob movement testing :/
                for(Map.Entry<UUID, JLabel> entry : mob.entrySet()) { // code similar to geek by geeks post - https://www.geeksforgeeks.org/how-to-iterate-hashmap-in-java/

                    UUID mobID = entry.getKey();
                    JLabel mobLabel = entry.getValue();

                    int mobSpeed = MobSpeed.get(mobID);
                    int mobFollowDistance = MobFollowDistance.get(mobID);


                    ghostTwoWorldPos = mobMovement((int) ghostTwoWorldPos.getX(), (int) ghostTwoWorldPos.getY(), mobSpeed, mobFollowDistance);
                    ghostTwo.setLocation(CameraInstance.worldToScreen(ghostTwoWorldPos));
                }


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
                ghostWorldPos = mobMovement((int) ghostWorldPos.getX(), (int) ghostWorldPos.getY(), 3, 500);
                ghost.setLocation(CameraInstance.worldToScreen(ghostWorldPos));

                //backgroundPanel.setComponentZOrder(player, 2);
                //backgroundPanel.setComponentZOrder(ghost, 3);
               // backgroundPanel.setComponentZOrder(rock, 3);
                //backgroundPanel.setComponentZOrder(chest, 4);


            }
        }
    }

    public void fadeOutStartScreen() {
        Timer timer = new Timer(30, null);
        final float[] fadeAmount = {1f};
    // credit to gpt for using "BufferedImage" to fade images
        timer.addActionListener(e -> {
            fadeAmount[0] -= 0.05f;
            if (fadeAmount[0] <= 0f) {
                fadeAmount[0] = 0f;
                backgroundPanel.remove(startScreen);
                backgroundPanel.repaint();
                timer.stop();
            }
            startScreen.setIcon(new ImageIcon(getFadedImage("images/GUI/startScreen.png", fadeAmount[0])));
        });

        timer.start();
        GUIOpen = false;
        healthChange(0);
    }

    public Image getFadedImage(String path, float fadeAmount) {
        ImageIcon icon = new ImageIcon(path);
        Image original = icon.getImage();
        BufferedImage faded = new BufferedImage(1040, 780, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = faded.createGraphics();
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, fadeAmount));
        g2d.drawImage(original, 0, 0, 1040, 780, null);
        g2d.dispose();
        return faded;
    }


    public Point mobMovement(int x, int y, int mobSpeed, int followDistance) {
        distance = Math.sqrt(Math.pow((playerWorldPos.x - x), 2) + Math.pow((playerWorldPos.y - y), 2));

        //step -= mobSpeed;

        if (distance <= followDistance && distance >= 200 && playerWorldPos.x != ghostWorldPos.x && playerWorldPos.y - ghostWorldPos.y != 0) {

            double distanceX = playerWorldPos.x - x;
            double distanceY = playerWorldPos.y - y;

            slope = (double) (playerWorldPos.y - y) / (playerWorldPos.x - x);

            b = playerWorldPos.y - slope * playerWorldPos.x;


            if (distanceX != 0) {

                double slope = distanceY / distanceX;
                double b = playerWorldPos.y - slope * playerWorldPos.x;

                if(Math.abs(distanceX) > Math.abs(distanceY)) {
                    if (playerWorldPos.x > x) {
                        x += mobSpeed;
                    } else {
                        x -= mobSpeed;
                    }

                    y = (int) (slope * x + b);
                } else {

                    if (playerWorldPos.y > y) {
                        y += mobSpeed;
                    } else {
                        y -= mobSpeed;
                    }

                    x = (int) ((y - b) / slope);

                }


             //   y = (int) (slope * x + b);



            } else {

                if (playerWorldPos.y > y) {
                    y += mobSpeed;
                } else {
                    y -= mobSpeed;
                }


            }
        }

        return new Point(x, y);
    }



    public JLabel mobCreation(int x, int y, int width, int height, String filePath, int zOrder, int health, int damage, int range, int speed, int followDistance) {
        ImageIcon icon = new ImageIcon(new ImageIcon(filePath).getImage().getScaledInstance(width, height, Image.SCALE_DEFAULT));
        JLabel label = new JLabel(icon);
        label.setBounds(x, y, width, height);
        label.setOpaque(false);
        backgroundPanel.add(label);
        backgroundPanel.setComponentZOrder(label, zOrder); // for mobs try to use either 1 or 2 idk we can change it later if it overlaps better

        UUID mobID = UUID.randomUUID(); // creates a unique UUID for each mob, do not touch this lmao
        mob.put(mobID, label);
        MobDamage.put(mobID, damage);
        MobHealth.put(mobID, health);
        MobReach.put(mobID, range);
        MobSpeed.put(mobID, speed);
        MobFollowDistance.put(mobID, followDistance);
        return label;
    }


    public void armorIncrease(double armorInc) {
        double oldMaximumHealth = maximumHealth;
        maximumHealth += armorInc;
        healthChange(maximumHealth - oldMaximumHealth);
    }


    public void healthChange(double healthChange) {

        if (!GUIOpen) {
            currentHealth += healthChange;
            if (currentHealth > maximumHealth) {
                currentHealth = maximumHealth;
            }
            if (currentHealth <= 0) {
                System.out.print("You died");
                //gameOver();
            }
            for (int i = 1; i <= maximumHealth; i++) {
                JLabel emptyHeart = assets(10 + (60 * (i - 1)), 10, 50, 50, false, "images/GUI/emptyHeart.png", false, 1);
                backgroundPanel.setComponentZOrder(emptyHeart, 1);
            }
            for (int i = 1; i <= currentHealth; i++) {
                JLabel fullHeart = assets(10 + (60 * (i - 1)), 10, 50, 50, false, "images/GUI/fullHeart.png", false, 0);
                backgroundPanel.setComponentZOrder(fullHeart, 0);
            }
            if (currentHealth % 1.0 != 0) {
                JLabel halfHeart = assets((int) (-20 + (60 * currentHealth)), 10, 50, 50, false, "images/GUI/halfHeart.png", false, 0);
                backgroundPanel.setComponentZOrder(halfHeart, 0);
            }
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


    public static void volumeChange(float volumeChange) {

        volume += volumeChange;
        if (volume >= 1f) {
            volume = 1f;
        } else if(volume <= 0f) {
            volume = 0f;
        }

        System.out.println(volume);

        FloatControl volumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        float dB = (float) (Math.log10(volume) * 20); // Convert volume (0.0 to 1.0) to decibels
        volumeControl.setValue(dB);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (startScreenVisible) {
            startScreenVisible = false;
            fadeOutStartScreen();
            return;
        }
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W -> playerMovementInstance.setUpPressed(true);
            case KeyEvent.VK_S -> playerMovementInstance.setDownPressed(true);
            case KeyEvent.VK_A -> playerMovementInstance.setLeftPressed(true);
            case KeyEvent.VK_D -> playerMovementInstance.setRightPressed(true);
            case KeyEvent.VK_Q -> qPressed = true;
            case KeyEvent.VK_E -> ePressed = true;
            case KeyEvent.VK_EQUALS -> volumeChange(0.1f);
            case KeyEvent.VK_MINUS -> volumeChange(-0.1f);
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
            case KeyEvent.VK_EQUALS -> plusPressed = false;
            case KeyEvent.VK_MINUS -> minusPressed = false;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}
}


