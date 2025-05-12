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


    JLabel player;


    JLabel startScreen;
    boolean startScreenVisible = true;


    public Point position;
    public int screenWidth, screenHeight;

    int x, y;
    int step = 6;
    boolean upPressed = false, downPressed = false, leftPressed = false, rightPressed = false, qPressed = false, ePressed = false, plusPressed = false, minusPressed = false, spacePressed = false;
    int moveTime, moveDir;
    int FPS = 60;
    double currentHealth = 3.0, maximumHealth = 3.0;
    String direction = "down";
    double distance;
    double slope;
    double b;
    public static float volume = 0f;
    boolean GUIOpen = true;
    boolean NPCInteracted = false;
    int messageDisDelay;
    int playerDamage = 5;

    Map<JLabel, Point> AssetPoint = new HashMap<>();
    Map<JLabel, Point> mobPoint = new HashMap<>();
    Map <UUID, JLabel> mob = new HashMap<>();
    Map<JLabel, UUID> reverseMobMap = new HashMap<>();
    Map <UUID, Integer> MobHealth = new HashMap<>();
    Map <UUID, Integer> MobDamage = new HashMap<>();
    Map <UUID, Integer> MobReach = new HashMap<>();
    Map <UUID, Integer> MobSpeed = new HashMap<>();
    Map <UUID, Integer> MobAttackCooldown = new HashMap<>();
    Map <UUID, Integer> MobFollowDistance = new HashMap<>();



    Map<String, ImageIcon> playerImages = new HashMap<>();
    ArrayList<JLabel> obstacles = new ArrayList<>();
    ArrayList<JLabel> passables = new ArrayList<>();
  //  ArrayList<JLabel> tiles = new ArrayList<>();
    ArrayList<Tile> backgroundTiles = new ArrayList<>();
    playerMovement playerMovementInstance;
    Camera CameraInstance;
    public Point playerWorldPos = new Point(0, 0);

    JLabel coordinates = new JLabel();
    BackgroundPanel backgroundPanel = new BackgroundPanel(null);
    static Clip clip;

    JLabel upAttack = new JLabel();
    JLabel LeftAttack = new JLabel();
    JLabel downAttack = new JLabel();
    JLabel rightAttack = new JLabel();

    String savedDirection;


    int swordUpgrade = 0;

    //JLabel cordBox = assets(20, 20, 75, 75, false, "images/GUI/coordinateBox.png", false);

    JLabel press = GUIassets(175, 600, 640, 160, false, "images/GUI/pressE.png", false, 0);

    JLabel gotApple = GUIassets( 175, 600, 640, 160, false, "images/text/appleFind.png", false, 0);

    JLabel pebble =  assets(1000, 1000, 1000, 1000, true, "images/assets/pebble.png", false, 4);

    JLabel rock =  assets(300, 200, 100, 100, true, "images/assets/rock.png", false, 4);

    JLabel chest =  assets(2000, 1000, 200, 200, false, "images/assets/chest.png", false, 4);

    JLabel warp = assets(-1000, 1000, 200, 200, false, "images/assets/warpstone.png", false, 4);

    JLabel rockTwo = assets(-500, -500, 200, 200, true, "images/assets/rock.png", false, 4);

    JLabel rockThird = assets( 2500, 2500, 200, 200, false, "images/assets/rock.png", false, 4);

    JLabel ghost = mobCreation(1000, -1500, 100, 100, "images/mob/ghost.png", 1, 10, 1, 100, 3, 5000);

    JLabel ghostTwo = mobCreation(750, 200, 100, 100, "images/mob/ghost.png", 1, 10, 1, 100, 3, 5000);

    JLabel ghostThree = mobCreation(-250, 400, 100, 100, "images/mob/ghost.png", 1, 10, 1, 100, 3, 5000);

    JLabel NPC = assets(2100,  -2000, 100, 200, false, "images/NPC/grandma.png", false, 1);


    JLabel waterBarrier = assets(0, 900, 4800, 800, true, "", false, 1);


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
        setResizable(false);





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
                backgroundPanel.setComponentZOrder(tileLabel, 8);

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
        Point chestPoint = new Point(1000, 2000);
        Point rockpoint = new Point(300, 600);

        player = new JLabel(playerImages.get("downStanding"));
        player.setBounds(super.getWidth() / 2 - 50, super.getHeight() / 2 - 100, 100, 188);
        player.setOpaque(false);
        backgroundPanel.setComponentZOrder(player, 0);

        x = player.getX();
        y = player.getY();
        playerWorldPos.setLocation(2360, -678);
        CameraInstance = new Camera(super.getWidth(), super.getHeight(), player.getX(), player.getY());

        upAttack = GUIassets(player.getX(), player.getY() - 100, 100, 100, false, "images/equipment/wood/up_wood.png", false, 1);
        upAttack.setVisible(false);
        LeftAttack = GUIassets(player.getX() - 75,player.getY() + 50, 100, 100, false, "images/equipment/wood/left_wood.png", false, 1);
        LeftAttack.setVisible(false);
        downAttack = GUIassets(player.getX(), player.getY() + 200, 100, 100, false, "images/equipment/wood/down_wood.png", false, 1);
        downAttack.setVisible(false);
        rightAttack = GUIassets(player.getX() + 100, player.getY() + 50, 100, 100, false, "images/equipment/wood/right_wood.png", false, 1);
        rightAttack.setVisible(false);



        backgroundPanel.add(player);

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


    public void WeaponImageAngled(int degrees) {

        if(swordUpgrade == 1) {
            ImageIcon icon = new ImageIcon("images/equipment/right_wood.png");
            ImageIcon image = new ImageIcon(icon.getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT));

        }


    }


    public void createMobPoint(int x, int y, JLabel assetName) {

        Point name = new Point(x, y);

        mobPoint.put(assetName, name);

    }

    public void createAssetPoint(int x, int y, JLabel assetName) {

        Point name = new Point(x, y);

        AssetPoint.put(assetName, name);

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

        Point assetPoint = new Point(x, y);
        AssetPoint.put(label, assetPoint);



        int maxZOrder = backgroundPanel.getComponentCount() - 1; // fixed with gpt
        zOrder = Math.max(0, Math.min(zOrder, maxZOrder)); // fixed with gpt
        backgroundPanel.setComponentZOrder(label, zOrder);


        return label;
    }

    public JLabel GUIassets(int x, int y, int width, int height, boolean obstacle, String filePath, boolean opaque, int zOrder) {
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
                NPCInteraction();
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
                for(Map.Entry<JLabel, Point> entry : mobPoint.entrySet()) { // code similar to geek by geeks post - https://www.geeksforgeeks.org/how-to-iterate-hashmap-in-java/

                    JLabel mobLabel = entry.getKey();
                    Point mobPoints = entry.getValue();

                    UUID mobID = reverseMobMap.get(mobLabel);

                    Integer mobSpeed = MobSpeed.get(mobID);
                    Integer mobFollowDistance = MobFollowDistance.get(mobID);

                    if (mobSpeed == null || mobFollowDistance == null) {  // thanks gpt for this if statement to fix the error
                        continue; // Skip this mob if data is missing
                    }


                        mobPoints = mobMovement((int) mobPoints.getX(), (int) mobPoints.getY(), mobSpeed, mobFollowDistance);
                    mobPoint.put(mobLabel, mobPoints);
                    mobLabel.setLocation(CameraInstance.worldToScreen(mobPoints));
                   // System.out.println("Mob Point: " + mobPoints);
                }


                for (Tile tile : backgroundTiles) {
                    Point screenPos = CameraInstance.worldToScreen(tile.worldPos);
                    tile.label.setLocation(screenPos);
                }

                for(Map.Entry<JLabel, Point> entry : AssetPoint.entrySet()) { // code similar to geek by geeks post - https://www.geeksforgeeks.org/how-to-iterate-hashmap-in-java/

                    JLabel MobLabel = entry.getKey();
                    Point mobPoint = entry.getValue();

                    MobLabel.setLocation(CameraInstance.worldToScreen(mobPoint));
                }


             //   mobAttack();

              //  rock.setLocation(CameraInstance.worldToScreen(rockWorldPos));
              //  chest.setLocation(CameraInstance.worldToScreen(chestWorldPos));
               // pebble.setLocation(CameraInstance.worldToScreen(pebbleWorldPos));
              //  warp.setLocation(CameraInstance.worldToScreen(warpWorldPos));
              //  rockTwo.setLocation(CameraInstance.worldToScreen(rockTwoWorldPos));
              //  rockThird.setLocation(CameraInstance.worldToScreen(rockThirdWorldPos));
               // ghostWorldPos = mobMovement((int) ghostWorldPos.getX(), (int) ghostWorldPos.getY(), 3, 500);
              //  ghost.setLocation(CameraInstance.worldToScreen(ghostWorldPos));
            //    NPC.setLocation(CameraInstance.worldToScreen(NPCWorldPos));





               /* if(spacePressed == false) {
                    upAttack.setVisible(false);
                    LeftAttack.setVisible(false);
                    downAttack.setVisible(false);
                    rightAttack.setVisible(false);


                } */
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



    public boolean mobAlive(int mobHealth, UUID mobId) {
        mobHealth = MobHealth.get(mobId);

        if (mobHealth <= 0) {
            mobRemove(mobId);
            return false;
        } else {
            return true;
        }

    }


    public void mobRemove(UUID mobId) {

        JLabel mobLabel = mob.get(mobId);
        backgroundPanel.remove(mobLabel);
        MobHealth.remove(mobId);
        MobDamage.remove(mobId);
        MobReach.remove(mobId);
        MobSpeed.remove(mobId);
        MobFollowDistance.remove(mobId);
        reverseMobMap.remove(mobLabel);
        mob.remove(mobId);
    }


    public void AttackMob(String direction) {

        switch (direction) {
            case "up" -> {



                for (Map.Entry<UUID, JLabel> entry : mob.entrySet()) { // code similar to geek by geeks post - https://www.geeksforgeeks.org/how-to-iterate-hashmap-in-java/

                    if(upAttack.getBounds().intersects(entry.getValue().getBounds())) {

                        UUID mobID = entry.getKey();
                        JLabel mobLabel = entry.getValue();


                        int mobHealth = MobHealth.get(mobID);

                        mobHealth -= playerDamage;

                        MobHealth.put(mobID, mobHealth);

                        if (mobHealth <= 0) {
                            mobRemove(mobID);
                        } else {
                            System.out.println("Mob Health: " + mobHealth);
                        }


                    }
                }

            }
            case "down" -> {

                for (Map.Entry<UUID, JLabel> entry : mob.entrySet()) { // code similar to geek by geeks post - https://www.geeksforgeeks.org/how-to-iterate-hashmap-in-java/

                    if(downAttack.getBounds().intersects(entry.getValue().getBounds())) {

                        UUID mobID = entry.getKey();
                        JLabel mobLabel = entry.getValue();


                        int mobHealth = MobHealth.get(mobID);

                        mobHealth -= playerDamage;

                        MobHealth.put(mobID, mobHealth);

                        if (mobHealth <= 0) {
                            mobRemove(mobID);
                        } else {
                            System.out.println("Mob Health: " + mobHealth);
                        }


                    }
                }



            }

            case "left" -> {

                for (Map.Entry<UUID, JLabel> entry : mob.entrySet()) { // code similar to geek by geeks post - https://www.geeksforgeeks.org/how-to-iterate-hashmap-in-java/

                    if (LeftAttack.getBounds().intersects(entry.getValue().getBounds())) {

                        UUID mobID = entry.getKey();
                        JLabel mobLabel = entry.getValue();


                        int mobHealth = MobHealth.get(mobID);

                        mobHealth -= playerDamage;

                        MobHealth.put(mobID, mobHealth);

                        if (mobHealth <= 0) {
                            mobRemove(mobID);
                        } else {
                            System.out.println("Mob Health: " + mobHealth);
                        }


                    }
                }

            }

            case "right" -> {

                for (Map.Entry<UUID, JLabel> entry : mob.entrySet()) { // code similar to geek by geeks post - https://www.geeksforgeeks.org/how-to-iterate-hashmap-in-java/

                    if (rightAttack.getBounds().intersects(entry.getValue().getBounds())) {

                        UUID mobID = entry.getKey();
                        JLabel mobLabel = entry.getValue();


                        int mobHealth = MobHealth.get(mobID);

                        mobHealth -= playerDamage;

                        MobHealth.put(mobID, mobHealth);

                        if (mobHealth <= 0) {
                            mobRemove(mobID);
                        } else {
                            System.out.println("Mob Health: " + mobHealth);
                        }


                    }
                }

            }

        }



    }


    public Point mobMovement(int x, int y, int mobSpeed, int followDistance) {
        distance = Math.sqrt(Math.pow(((playerWorldPos.x - 40) - x), 2) + Math.pow(((playerWorldPos.y-50) - y), 2));

        //step -= mobSpeed;

        if (distance <= followDistance && distance >= 100) {

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
        reverseMobMap.put(label, mobID);
        MobDamage.put(mobID, damage);
        MobHealth.put(mobID, health);
        MobReach.put(mobID, range);
        MobSpeed.put(mobID, speed);
        MobFollowDistance.put(mobID, followDistance);

        Point MobPoint = new Point(x, y);
        mobPoint.put(label, MobPoint);


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
                JLabel emptyHeart = GUIassets(10 + (60 * (i - 1)), 10, 50, 50, false, "images/GUI/emptyHeart.png", false, 1);
                backgroundPanel.setComponentZOrder(emptyHeart, 1);
            }
            for (int i = 1; i <= currentHealth; i++) {
                JLabel fullHeart = GUIassets(10 + (60 * (i - 1)), 10, 50, 50, false, "images/GUI/fullHeart.png", false, 0);
                backgroundPanel.setComponentZOrder(fullHeart, 0);
            }
            if (currentHealth % 1.0 != 0) {
                JLabel halfHeart = GUIassets((int) (-20 + (60 * currentHealth)), 10, 50, 50, false, "images/GUI/halfHeart.png", false, 0);
                backgroundPanel.setComponentZOrder(halfHeart, 0);
            }
        }



    }


    public void mobAttack() {

        for(Map.Entry<UUID, JLabel> entry : mob.entrySet()) { // code similar to geek by geeks post - https://www.geeksforgeeks.org/how-to-iterate-hashmap-in-java/

            UUID mobID = entry.getKey();
            JLabel mobLabel = entry.getValue();

            int mobAttackCooldown = MobAttackCooldown.get(mobID);
            int mobReach = MobReach.get(mobID);
            int mobDamage = MobDamage.get(mobID);
            int mobHealth = MobHealth.get(mobID);
            int distance = (int) Math.sqrt(Math.pow(((playerWorldPos.x - 40) - mobLabel.getX()), 2) + Math.pow(((playerWorldPos.y-50) - mobLabel.getY()), 2));



            if(distance <= mobReach) {
                if (mobAttackCooldown <= 0) {
                    currentHealth -= mobDamage;
                    MobAttackCooldown.put(mobID, 1000);
                    System.out.println("You were attacked by a mob");
                } else {
                    mobAttackCooldown -= 1;
                }
            }

        }



    }


    public void chest() {

        if(player.getBounds().intersects(chest.getBounds())) {
            System.out.println("You opened the chest");
            currentHealth = maximumHealth;
        } else {
            System.out.println("You are not close enough to the chest");
        }


    }


    public void test () {

        if(qPressed) {

            playerWorldPos = new Point(0, 0);

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
            playerWorldPos.setLocation(-50, 0);

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

    public void attacking(String direction, boolean spacePressed) {



    if(spacePressed) {
        switch (direction) {
            case "up" -> {
                upAttack.setVisible(true);
                LeftAttack.setVisible(false);
                downAttack.setVisible(false);
                rightAttack.setVisible(false);
                AttackMob("up");
            }
            case "down" -> {
                downAttack.setVisible(true);
                upAttack.setVisible(false);
                rightAttack.setVisible(false);
                LeftAttack.setVisible(false);
                AttackMob("down");
            }
            case "left" ->  {
                LeftAttack.setVisible(true);
                upAttack.setVisible(false);
                downAttack.setVisible(false);
                rightAttack.setVisible(false);
                AttackMob("left");
            }
            case "right" -> {
                rightAttack.setVisible(true);
                upAttack.setVisible(false);
                downAttack.setVisible(false);
                LeftAttack.setVisible(false);
                AttackMob("right");
            }
            default -> {
                upAttack.setVisible(false);
                downAttack.setVisible(false);
                LeftAttack.setVisible(false);
                rightAttack.setVisible(false);
            }

        }
    } else {
       upAttack.setVisible(false);
      downAttack.setVisible(false);
       LeftAttack.setVisible(false);
      rightAttack.setVisible(false);
    }

       /* if(spacePressed) {
            if (direction.equalsIgnoreCase("up")) {

                upAttack.setOpaque(true);
                backgroundPanel.setComponentZOrder(upAttack, 0);

            } else if (direction.equalsIgnoreCase("down")) {

                downAttack.setOpaque(true);
                backgroundPanel.setComponentZOrder(downAttack, 0);

            } else if (direction.equalsIgnoreCase("left")) {

                LeftAttack.setOpaque(true);
                backgroundPanel.setComponentZOrder(LeftAttack, 0);

            } else if (direction.equalsIgnoreCase("right")) {

                rightAttack.setOpaque(true);
                backgroundPanel.setComponentZOrder(rightAttack, 0);


            }

        } else {
            upAttack.setOpaque(false);
            downAttack.setOpaque(false);
            LeftAttack.setOpaque(false);
            rightAttack.setOpaque(false);
        }


        */
    }


    public void NPCInteraction () {

        if (player.getBounds().intersects(NPC.getBounds()) && !NPCInteracted) {
            press.setVisible(true);

            if (player.getBounds().intersects(NPC.getBounds()) && ePressed) {
                NPCInteracted = true;
                press.setVisible(false);
                gotApple.setVisible(true);

                messageDisDelay = 0;


            }
        } else {
            messageDisDelay++;
            //press.setVisible(false);
            if (messageDisDelay >= 60) {
                gotApple.setVisible(false);
            }
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
            case KeyEvent.VK_SPACE -> {
                savedDirection = playerMovementInstance.direction;
                attacking(savedDirection, true);
                spacePressed = true;

            }


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
            case KeyEvent.VK_SPACE -> attacking(savedDirection, false);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}
}


