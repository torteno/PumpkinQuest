import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.io.File;
import java.io.IOException;
import javax.sound.sampled.*;
import java.util.UUID;
import java.time.*;


public class frame extends JFrame implements KeyListener {


    JLabel player;


    JLabel startScreen;
    boolean startScreenVisible = true;


    public Point position;
    public int screenWidth, screenHeight;

    int x, y;
    int step = 6;
    boolean upPressed = false, downPressed = false, leftPressed = false, rightPressed = false, qPressed = false, ePressed = false, plusPressed = false, minusPressed = false, spacePressed = false, kPressed = false, lPressed = false, onePressed = false, twoPressed = false, threePressed = false, fourPressed = false, pPressed = false, enterPressed = false, escPressed = false;
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

    /*
    int scrollTime = 0;
    int lineComplete = 0;
    int lineNumber = 1;
    int scrollPixels = 30;  // instead of 30
    */


    int currentDialogueIndex = -1;
    boolean dialogueActive = false;
    JLabel[] dialogueImages;

    Map<UUID, Point> mobSpawnPoint = new HashMap<>();
    Map<JLabel, Point> AssetPoint = new HashMap<>();
    Map<JLabel, Point> mobPoint = new HashMap<>();
    Map <UUID, JLabel> mob = new HashMap<>();
    Map<JLabel, UUID> reverseMobMap = new HashMap<>();
    Map <UUID, Integer> MobHealth = new HashMap<>();
    Map <UUID, Double> MobDamage = new HashMap<>();
    Map <UUID, Integer> MobReach = new HashMap<>();
    Map <UUID, Integer> MobSpeed = new HashMap<>();
    Map <UUID, Integer> MobAttackCooldown = new HashMap<>();
    Map <UUID, Integer> MobFollowDistance = new HashMap<>();
    Map <UUID, Double> MobDistance = new HashMap<>();
    Map <UUID, Long> MobAttackCurrentCoolDown = new HashMap<>();
    Map <UUID, LocalDateTime> TimeMobAttacked = new HashMap<>();
    Map <UUID, Duration> TimeSinceMobAttacked = new HashMap<>();








    Map<String, ImageIcon> playerImages = new HashMap<>();
    ArrayList<JLabel> obstacles = new ArrayList<>();
    ArrayList<JLabel> passables = new ArrayList<>();
  //  ArrayList<JLabel> tiles = new ArrayList<>();
    ArrayList<Tile> backgroundTiles = new ArrayList<>();
    playerMovement playerMovementInstance;
    Camera CameraInstance;
    public Point playerWorldPos = new Point(0, 0);
    public Point SpawnPoint = new Point(2360, -678);
    public Point debugPoint = new Point(0, 0);

    JLabel coordinates = new JLabel();
    BackgroundPanel backgroundPanel = new BackgroundPanel(null);
    static Clip clip;
    boolean gameStarted = false;

    JLabel upAttack = new JLabel();
    JLabel LeftAttack = new JLabel();
    JLabel downAttack = new JLabel();
    JLabel rightAttack = new JLabel();

    String savedDirection;


    boolean debugMode = false;
    boolean placeCooldown = false;
    int swordUpgrade = 0;

    //JLabel cordBox = assets(20, 20, 75, 75, false, "images/GUI/coordinateBox.png", false);

   

    JLabel press = GUIassets(125, 700, 760, 40, false, "images/GUI/pressE.png", false, 1, false);

    JLabel gotApple = GUIassets( 175, 700, 640, 160, false, "images/text/appleFind.png", false, 2, false);

   JLabel chest =  assets(2000, 1000, 200, 200, false, "images/assets/chest.png", false, 8, true);


   JLabel warp = assets(-1000, 1000, 200, 200, false, "images/assets/warpstone.png", false, 8, true);

    JLabel treebarrier = assets(2900, -5800, 600, 7500, true, "images/assets/manymanytrees.png", false, 8, true);
    JLabel respawnPointOne = assets( 2550, -1250, 150, 200, false, "images/assets/RespawnPoint.png", false, 8, true);

    JLabel ghost1 = mobCreation(2250, -3000, 200, 200, "images/mob/ghostLeft.png", 2, 10, 0.5, 200, 3, 600, 3);
    JLabel ghost2 = mobCreation(2250, -3600, 200, 200, "images/mob/ghostRight.png", 2, 10, 0.5, 200, 3, 600,3);
    JLabel ghost3 = mobCreation(2500, -4450, 200, 200, "images/mob/ghostLeft.png", 2, 10, 0.5, 200, 3, 600,3);
    JLabel ghost4 = mobCreation(2100, 5500, 200, 200, "images/mob/ghostRight.png", 2, 10, 0.5, 200, 3, 600,3);
    JLabel ghost5 = mobCreation(2800, -6300, 200, 200, "images/mob/ghostRight.png", 2, 10, 0.5, 200, 3, 600,3);
    JLabel ghost6 = mobCreation(4100, -6000, 200, 200, "images/mob/ghostLeft.png", 2, 10, 0.5, 200, 3, 600,3);
    JLabel ghost7 = mobCreation(4400, -5100, 200, 200, "images/mob/ghostRight.png", 2, 10, 0.5, 200, 3, 600,3);
    JLabel ghost8 = mobCreation(4650, -4150, 200, 200, "images/mob/ghostLeft.png", 2, 10, 0.5, 200, 3, 600,3);
    JLabel ghost9 = mobCreation(5000, -2800, 200, 200, "images/mob/ghostRight.png", 2, 10, 0.5, 200, 3, 600,3);
    JLabel ghost10 = mobCreation(4800, -2000, 200, 200, "images/mob/ghostLeft.png", 2, 10, 0.5, 200, 3, 600,3);

    JLabel NPC = assets(2100,  -2000, 100, 200, false, "images/NPC/Grandma/grandma.png", false, 2, true);

    JLabel waterBarrier = assets(0, 900, 4800, 800, true, "", false, 3 , false);
 
    JLabel SScredits = GUIassets(0, 0, 1040, 780,false, "images/GUI/creditsScreen.png", false, 2, true);
    JLabel startMenu = GUIassets(0,0, 1000, 1000, false, "images/GUI/placeHolderStart.png", false, 2, true);
    JLabel startCredits = GUIassets(100, 300, 400, 40, false, "images/GUI/startScreenCredits.png", false, 1, true);
    JLabel startPlay = GUIassets(100, 200, 400, 40, false, "images/GUI/startScreenNew.png", false, 1, true);
    JLabel startQuit = GUIassets(100, 400, 400, 40, false, "images/GUI/startScreenQuit.png", false, 1, true);
    JLabel currentSelection = GUIassets(25, 192, 60, 60, false, "images/GUI/selectionarrow.png", false, 1, true);

    JLabel NPCBackground = GUIassets(-150, 470, 1200, 370, false, "images/NPC/NPCDialogueBackground.png", false, 2, false);

    /*
    JLabel NPCScroller1 = GUIassets(-150, 590, 1000, 30, false, "images/NPC/coverDialogue.png", false, 0, false);
    JLabel NPCScroller2 = GUIassets(-150, 615, 1000, 30, false, "images/NPC/coverDialogue.png", false, 0, false);
    JLabel NPCScroller3 = GUIassets(-150, 640, 1000, 30, false, "images/NPC/coverDialogue.png", false, 0, false);
    JLabel NPCScroller4 = GUIassets(-150, 665, 1000, 30, false, "images/NPC/coverDialogue.png", false, 0, false);
    */


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
        int tileSize = 1600; // changes how far apart the tiles are placeda
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
                backgroundPanel.setComponentZOrder(tileLabel, 10);
                backgroundPanel.add(tileLabel);


                Point worldPos = new Point(tileX * tileSize, -tileY * tileSize);
                backgroundTiles.add(new Tile(tileLabel, worldPos));

            }
        }

        startScreen = new JLabel(new ImageIcon(new ImageIcon("").getImage().getScaledInstance(1040, 780, Image.SCALE_DEFAULT)));
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

        upAttack = GUIassets(player.getX(), player.getY() - 100, 100, 100, false, "images/equipment/wood/up_wood.png", false, 1, false);
        LeftAttack = GUIassets(player.getX() - 75,player.getY() + 50, 100, 100, false, "images/equipment/wood/left_wood.png", false, 1, false);
        downAttack = GUIassets(player.getX(), player.getY() + 200, 100, 100, false, "images/equipment/wood/down_wood.png", false, 1, false);
        rightAttack = GUIassets(player.getX() + 100, player.getY() + 50, 100, 100, false, "images/equipment/wood/right_wood.png", false, 1, false);





        backgroundPanel.add(player);

        setContentPane(backgroundPanel);
        addKeyListener(this);
        setVisible(true);

        coordinates.setBounds(900, 700, 100, 100);

        super.add(coordinates);

        backgroundPanel.setComponentZOrder(coordinates, 0);

        playerMovementInstance = new playerMovement(player, obstacles, playerImages, x, y, step, FPS, direction, upPressed, downPressed, leftPressed, rightPressed, playerWorldPos);
        moveDir = 1;

        SScredits.setVisible(false);




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


    public JLabel assets(int x, int y, int width, int height, boolean obstacle, String filePath, boolean opaque, int zOrder, boolean visible) {
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

        if(visible) {
            label.setVisible(true);
        } else {
            label.setVisible(false);
        }

        int maxZOrder = backgroundPanel.getComponentCount() - 1; // fixed with gpt
        zOrder = Math.max(0, Math.min(zOrder, maxZOrder)); // fixed with gpt
        backgroundPanel.setComponentZOrder(label, zOrder);


        return label;
    }

    public JLabel GUIassets(int x, int y, int width, int height, boolean obstacle, String filePath, boolean opaque, int zOrder, boolean visible) {
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

        if(visible) {
            label.setVisible(true);
        } else {
            label.setVisible(false);
        }

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



        while(true) {
            currentTime = System.nanoTime();
            placeholder += (currentTime - previousTime) / timePerFrame;
            previousTime = currentTime;
            if (placeholder >= 1) {
                interacting();
                NPCInteraction();
                //player.setBounds(super.getWidth() / 2 - 50, super.getHeight() / 2 - 100, player.getWidth(), player.getHeight());


                //uncomment this when we want to submit
                // coordinates.setText((int) playerWorldPos.getX() - 2360 + " " + (int) ((playerWorldPos.getY() + 678) * -1));


                //comment this when we want to submit
                coordinates.setText((int) playerWorldPos.getX() + " " + (int) playerWorldPos.getY());
                //healthChange(0);

                //System.out.println(ghostWorldPos);

                if (dialogueActive) {


                    //press.setVisible(false);
                    if (ePressed) {
                        ePressed = false;

                        // Hide current image
                        if (currentDialogueIndex < dialogueImages.length) {
                            dialogueImages[currentDialogueIndex].setVisible(false);
                        }

                        currentDialogueIndex++;

                        // Show next image or end dialogue
                        if (currentDialogueIndex < dialogueImages.length) {
                            dialogueImages[currentDialogueIndex].setVisible(true);


                        } else {
                            // End of dialogue
                            dialogueActive = false;
                            NPCInteracted = true;
                            NPCBackground.setVisible(false);

                            /* NPCScroller1.setVisible(false);
                            NPCScroller2.setVisible(false);
                            NPCScroller3.setVisible(false);
                            NPCScroller4.setVisible(false);

                             */
                        }
                    }
                }



                if (!GUIOpen) {
                    backgroundPanel.setComponentZOrder(player, 0);
                }


                //mob movement testing :/
                for (Map.Entry<JLabel, Point> entry : mobPoint.entrySet()) { // code similar to geek by geeks post - https://www.geeksforgeeks.org/how-to-iterate-hashmap-in-java/

                    JLabel mobLabel = entry.getKey();
                    Point mobPoints = entry.getValue();

                    UUID mobID = reverseMobMap.get(mobLabel);

                    Integer mobSpeed = MobSpeed.get(mobID);
                    Integer mobFollowDistance = MobFollowDistance.get(mobID);

                    Point mobSpawnpoint = mobSpawnPoint.get(mobID);

                    if (mobSpeed == null || mobFollowDistance == null) {  // thanks gpt for this if statement to fix the error
                        continue; // Skip this mob if data is missing
                    }
                    mobPoints = mobMovement((int) mobPoints.getX(), (int) mobPoints.getY(), mobSpeed, mobFollowDistance, mobSpawnpoint);
                    mobPoint.put(mobLabel, mobPoints);
                    mobLabel.setLocation(CameraInstance.worldToScreen(mobPoints));
                    // System.out.println("Mob Point: " + mobPoints);
                }


                for (Tile tile : backgroundTiles) {
                    Point screenPos = CameraInstance.worldToScreen(tile.worldPos);
                    tile.label.setLocation(screenPos);
                }

                for (Map.Entry<JLabel, Point> entry : AssetPoint.entrySet()) { // code similar to geek by geeks post - https://www.geeksforgeeks.org/how-to-iterate-hashmap-in-java/

                    JLabel MobLabel = entry.getKey();
                    Point mobPoint = entry.getValue();

                    MobLabel.setLocation(CameraInstance.worldToScreen(mobPoint));
                }

                try {
                    mobAttack();
                } catch (Exception e) {
                    e.printStackTrace();
                }


                playerMovementInstance.playerPosition();
                CameraInstance.position = playerWorldPos;

                placeholder--;
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
    
        GUIOpen = false;
      healthChange(0);
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
        mobLabel.setVisible(false);
        mobLabel.repaint();
        backgroundPanel.remove(mobLabel);
        MobHealth.remove(mobId);
        MobDamage.remove(mobId);
        MobReach.remove(mobId);
        MobSpeed.remove(mobId);
        MobFollowDistance.remove(mobId);
        reverseMobMap.remove(mobLabel);
        mob.remove(mobId);
        MobAttackCooldown.remove(mobId);
        MobAttackCurrentCoolDown.remove(mobId);
        MobDistance.remove(mobId);
        TimeMobAttacked.remove(mobId);
        TimeSinceMobAttacked.remove(mobId);
        mobSpawnPoint.remove(mobId);

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


    public Point mobMovement(int x, int y, int mobSpeed, int followDistance, Point spawnPoint) {
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
        } else if(distance >= 200) {
            double distanceSpawnPointX = spawnPoint.x - x;
            double distanceSpawnPointY = spawnPoint.y - y;

            slope = (double) (spawnPoint.y - y) / (spawnPoint.x - x);

            b = spawnPoint.y - slope * spawnPoint.x;


            if (distanceSpawnPointX != 0) {

                double slope = distanceSpawnPointY / distanceSpawnPointX;
                double b = spawnPoint.y - slope * spawnPoint.x;


                if (Math.abs(distanceSpawnPointX) > Math.abs(distanceSpawnPointY)) {
                    if (spawnPoint.x > x) {
                        x += mobSpeed;

                    } else {
                        x -= mobSpeed;
                    }

                    y = (int) (slope * x + b);
                } else {

                    if (spawnPoint.y > y) {
                        y += mobSpeed;
                    } else {
                        y -= mobSpeed;
                    }

                    x = (int) ((y - b) / slope);

                }


                //   y = (int) (slope * x + b);


            } else {

                if (spawnPoint.y > y) {
                    y += mobSpeed;
                } else {
                    y -= mobSpeed;
                }


            }
        }

        return new Point(x, y);
    }



    public JLabel mobCreation(int x, int y, int width, int height, String filePath, int zOrder, int health, double damage, int range, int speed, int followDistance, int attackCooldown) {
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
        MobAttackCooldown.put(mobID, attackCooldown);

        Point MobPoint = new Point(x, y);
        mobPoint.put(label, MobPoint);

        mobSpawnPoint.put(mobID, new Point(MobPoint));


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
                playerWorldPos.setLocation(SpawnPoint);
                healthChange(maximumHealth);
                //gameOver();
            }
            for (int i = 1; i <= maximumHealth; i++) {
                JLabel emptyHeart = GUIassets(10 + (60 * (i - 1)), 10, 50, 50, false, "images/GUI/emptyHeart.png", false, 1, true);
                backgroundPanel.setComponentZOrder(emptyHeart, 1);
                emptyHeart.repaint();
            }
            for (int i = 1; i <= currentHealth; i++) {
                JLabel fullHeart = GUIassets(10 + (60 * (i - 1)), 10, 50, 50, false, "images/GUI/fullHeart.png", false, 0, true);
                backgroundPanel.setComponentZOrder(fullHeart, 0);
                fullHeart.repaint();
            }
            if (currentHealth % 1.0 != 0) {
                JLabel halfHeart = GUIassets((int) (-20 + (60 * currentHealth)), 10, 50, 50, false, "images/GUI/halfHeart.png", false, 0, true);
                backgroundPanel.setComponentZOrder(halfHeart, 0);
                halfHeart.repaint();
            }
        }



    }


    public void mobAttack() {

        for (Map.Entry<UUID, JLabel> entry : mob.entrySet()) { // code similar to geek by geeks post - https://www.geeksforgeeks.org/how-to-iterate-hashmap-in-java/

            UUID mobID = entry.getKey();
            JLabel mobLabel = entry.getValue();

            int mobAttackCooldown = MobAttackCooldown.getOrDefault(mobID, 0);
            int mobReach = MobReach.getOrDefault((Object) mobID, 0);
            Double mobDamage = MobDamage.getOrDefault(mobID, 0.0);
            long mobCooldown = MobAttackCurrentCoolDown.getOrDefault(mobID, Long.valueOf(0));
            LocalDateTime timeSinceAttack = TimeMobAttacked.getOrDefault(mobID, LocalDateTime.MIN);
            double mobDistance = MobDistance.getOrDefault(mobID, Double.MAX_VALUE);
            Duration durationMobAttack = TimeSinceMobAttacked.getOrDefault(mobID, Duration.ZERO);

            Point mobWorldPos = mobPoint.get(mobLabel);
            double distance = Math.sqrt(Math.pow(((playerWorldPos.x - 40) - mobWorldPos.getX()), 2) + Math.pow(((playerWorldPos.y - 50) - mobWorldPos.getY()), 2));


            mobDistance = distance;
            MobDistance.put(mobID, mobDistance);
            //System.out.println("Mob Distance: " + mobDistance + "Mob Cooldown: " + mobCooldown);
          //  System.out.println("Mob Distance: " + mobDistance + "Mob Cooldown: " + mobCooldown);


            durationMobAttack = Duration.between(timeSinceAttack, LocalDateTime.now());

            mobCooldown = (Math.abs(durationMobAttack.get(ChronoUnit.SECONDS)));

            MobAttackCurrentCoolDown.put(mobID, mobCooldown);


            if (mobDistance <= mobReach) {
                durationMobAttack = Duration.between(timeSinceAttack, LocalDateTime.now());
                mobCooldown = (Math.abs(durationMobAttack.get(ChronoUnit.SECONDS)));
                if (mobCooldown >= (long) mobAttackCooldown) {
                    healthChange(-mobDamage);
                    TimeMobAttacked.put(mobID, LocalDateTime.now());
                    timeSinceAttack = LocalDateTime.now();
                    System.out.println("You were attacked by a mob");
                    TimeMobAttacked.put(mobID, LocalDateTime.now());
                    MobAttackCurrentCoolDown.put(mobID, 0L);
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


        if(player.getBounds().intersects(respawnPointOne.getBounds())) {



            if(ePressed) {

                SpawnPoint.setLocation(2360, -678);

            }

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

            if (ePressed && !dialogueActive) {
                NPCInteracted = true;
                press.setVisible(false);
                startDialogue(1); // Start FSM
                ePressed = false; // Prevent skipping first image
            }
        }

    }


    public void startDialogue(int NPCNumber) {
        /*
        NPCScroller1.setVisible(true);
        NPCScroller2.setVisible(true);
        NPCScroller3.setVisible(true);
        NPCScroller4.setVisible(true);

         */

        dialogueActive = true;
        currentDialogueIndex = 0;

        switch (NPCNumber) {
            case 1 : {
                NPCBackground.setVisible(true);


                dialogueImages = new JLabel[] {
                        GUIassets(50, 500, 825, 300, false, "images/NPC/Grandma/GrandmaNPCDialogue1.png", false, 1, false),
                        GUIassets(50, 500, 825, 300, false, "images/NPC/Grandma/GrandmaNPCDialogue2.png", false, 1, false)
                };

                for (JLabel label : dialogueImages) {
                    label.setVisible(false);

                }

                dialogueImages[0].setVisible(true);

            }
        }
    }


/*
    JLabel[] NPCscrollers = {NPCScroller1, NPCScroller2, NPCScroller3, NPCScroller4};

    public void NPCDialogueScroll() {
        if (lineNumber < 1 || lineNumber > 4) return;

        JLabel currentScroller = NPCscrollers[lineNumber - 1];

        if (lineComplete <= 31) {
            if (scrollTime >= (FPS / 5)) {
                int fullWidth = 1000;
                int pixelsHidden = scrollPixels * lineComplete;
                int newWidth = Math.max(0, fullWidth - pixelsHidden);

                currentScroller.setBounds(-100 + pixelsHidden, 590 + ((lineNumber - 1) * 25), newWidth, 30);  // move X right

                scrollTime = 0;
                lineComplete++;
            } else {
                scrollTime++;
            }

        } else {
            currentScroller.setVisible(false);
            lineComplete = 0;
            lineNumber++;
            if (lineNumber > 4) lineNumber = 1;
        }
    }
*/

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
    JLabel debugChest = GUIassets(1000/2, 800/2, 150, 150,  false, "images/assets/chest.png", false, 8, false);
    JLabel debugRock = GUIassets(1000/2, 800/2, 100, 100,  false, "images/assets/rock.png", false, 8, false);
    JLabel debugwarpStone = GUIassets(1000/2, 800/2, 200, 200, false, "images/assets/warpstone.png", false, 8, false);
    JLabel debugRespawnPoint = GUIassets(1000/2, 800/2,150, 200, false, "images/assets/RespawnPoint.png", false, 8 , false);


    public void debug() {


        if(kPressed) {
            System.out.println("First Position: " + playerWorldPos);
            debugPoint.setLocation(playerWorldPos);
        }


        if(lPressed) {
            System.out.println(Math.abs(playerWorldPos.getX() + debugPoint.getY()) + "," + Math.abs(playerWorldPos.getY() + debugPoint.getX()));
        }

        if(pPressed && onePressed && !placeCooldown) {
            placeCooldown = true;
            JLabel debugRock = assets((int) playerWorldPos.getX(), (int) playerWorldPos.getY(), 100, 100,  false, "images/assets/rock.png", false, 8, true);
            System.out.println("JLabel Rock" + (int) (Math.random() * (10000000 - 1) + 1) + " = assets(" + (int) playerWorldPos.getX() + " , " + (int) playerWorldPos.getY() + ", 100, 100,  true, \"images/assets/rock.png\", false, 8, true);");
        } else if(pPressed && twoPressed && !placeCooldown) {
            placeCooldown = true;
            JLabel debugChest = assets((int) playerWorldPos.getX(), (int) playerWorldPos.getY(), 150, 150,  false, "images/assets/chest.png", false, 8, true);
            System.out.println("JLabel Chest" + (int) (Math.random() * (10000000 - 1) + 1) + " = assets(" + (int) playerWorldPos.getX() + " , " + (int) playerWorldPos.getY() + ", 200, 200,  true, \"images/assets/chest.png\", false, 8, true);");

        } else if(pPressed && threePressed && !placeCooldown) {
            placeCooldown = true;
            JLabel debugRespawnPoint = assets((int) playerWorldPos.getX(), (int) playerWorldPos.getY(), 150, 200,  false, "images/assets/RespawnPoint.png", false, 8, true);
            System.out.println("JLabel RespawnPoint" + (int) (Math.random() * (10000000 - 1) + 1) + " = assets(" + (int) playerWorldPos.getX() + " , " + (int) playerWorldPos.getY() + ", 150, 200,  true, \"images/assets/respawnPoint.png\", false, 8, false);");

        } else if(pPressed && fourPressed && !placeCooldown) {
            placeCooldown = true;
            JLabel debugwarpStone = assets((int) playerWorldPos.getX(), (int) playerWorldPos.getY(), 200, 200,  false, "images/assets/warpStone.png", false, 8, true);
            System.out.println("JLabel Stone" + (int) (Math.random() * (10000000 - 1) + 1) + " = assets(" + (int) playerWorldPos.getX() + " , " + (int) playerWorldPos.getY() + ", 100, 100,  true, \"images/assets/warpStone.png\", false, 8, true);");

        }

        if(onePressed) {

            debugRock.setVisible(true);
            debugChest.setVisible(false);
            debugRespawnPoint.setVisible(false);
            debugwarpStone.setVisible(false);


        } else if(twoPressed) {

            debugChest.setVisible(true);
            debugRock.setVisible(false);
            debugRespawnPoint.setVisible(false);
            debugwarpStone.setVisible(false);

        } else if(threePressed) {

            debugRespawnPoint.setVisible(true);
            debugChest.setVisible(false);
            debugRock.setVisible(false);
            debugwarpStone.setVisible(false);

        } else if(fourPressed) {
            debugwarpStone.setVisible(true);
            debugChest.setVisible(false);
            debugRock.setVisible(false);
            debugRespawnPoint.setVisible(false);

        }



    }



    int startSelection = 1;
    boolean menuAlreadyChanged = false;

    public void startGame() {

        gameStarted = true;
        gameLoop();

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (startScreenVisible) {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_DOWN && !menuAlreadyChanged) {
            startSelection++;

            if (startSelection > 3) { 
                startSelection = 1;
            menuAlreadyChanged = true;
            }
        } if (key == KeyEvent.VK_UP && !menuAlreadyChanged) {
            startSelection--;

            if (startSelection < 1) {
                startSelection = 3;
            menuAlreadyChanged = true;
        }
    }


        if (startSelection == 1) {
            currentSelection.setLocation(25,192);
        } else if (startSelection == 2) {
            currentSelection.setLocation(25,292);
        } else if (startSelection == 3) {
            currentSelection.setLocation(25,392);
        }
        
    
        if (key == KeyEvent.VK_SPACE) {
            switch (startSelection) {
                case 1:
                  //  gameStarted = true;
                    startPlay.setVisible(false);
                    startCredits.setVisible(false);
                    startQuit.setVisible(false);
                    startMenu.setVisible(false);
                    SScredits.setVisible(false);
                    currentSelection.setVisible(false);
                    startScreenVisible = false;
                    fadeOutStartScreen();
                 //   gameLoop();
                    return;
                case 2: 
                    if (SScredits.isVisible() == false) {
                        SScredits.setVisible(true);
                        startMenu.setVisible(false);
                        startPlay.setVisible(false);
                        startCredits.setVisible(false);
                        startQuit.setVisible(false);
                        currentSelection.setVisible(false);
                    } else {
                        SScredits.setVisible(false);
                        startMenu.setVisible(true);
                        startPlay.setVisible(true);
                        startCredits.setVisible(true);
                        startQuit.setVisible(true);
                        currentSelection.setVisible(true);
                    }
                    
                    return;
                case 3: 
                    System.exit(0);

                    break;
            }
        }

        return;
    

        } else {
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
            case KeyEvent.VK_ESCAPE -> {

            }

            case KeyEvent.VK_K -> {
                kPressed = true;
                debug();
            }
            case KeyEvent.VK_L -> {
                lPressed = true;
                debug();
            }

            case KeyEvent.VK_1 -> {
                onePressed = true;
                twoPressed = false;
                threePressed = false;
                fourPressed = false;
                debug();

            }
            case KeyEvent.VK_2 -> {
                onePressed = false;
                twoPressed = true;
                threePressed = false;
                fourPressed = false;
                debug();

            }
            case KeyEvent.VK_3 -> {
                onePressed = false;
                twoPressed = false;
                threePressed = true;
                fourPressed = false;
                debug();

            }
            case KeyEvent.VK_4 -> {
                onePressed = false;
                twoPressed = false;
                threePressed = false;
                fourPressed = true;
                debug();

            }
            case KeyEvent.VK_P -> {
                pPressed = true;
                debug();

            }


        }
    }
    }
    @Override
    public void keyReleased(KeyEvent e) {

        int key = e.getKeyCode();
            if (key == KeyEvent.VK_DOWN || key == KeyEvent.VK_UP) {
            menuAlreadyChanged = false;
    }
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
            case KeyEvent.VK_K -> kPressed = false;
            case KeyEvent.VK_L -> lPressed = false;
            case KeyEvent.VK_P -> {
                debug();
                pPressed = false;
                placeCooldown = false;
            }
         /*   case KeyEvent.VK_1 -> {
                placeCooldown = false;
                debug();
            }
            case KeyEvent.VK_2 -> {
                placeCooldown = false;
                debug();
            }
            case KeyEvent.VK_3 -> {
                placeCooldown = false;
                debug();
            }
            case KeyEvent.VK_4 -> {
                placeCooldown = false;
                debug();
            } */


    }
    }
    @Override
    public void keyTyped(KeyEvent e) {}
}


