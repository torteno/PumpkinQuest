package com.example.pumpkinquest;

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
    boolean upPressed = false, downPressed = false, leftPressed = false, rightPressed = false, qPressed = false, ePressed = false, plusPressed = false, minusPressed = false, spacePressed = false, kPressed = false, lPressed = false, onePressed = false, twoPressed = false, threePressed = false, fourPressed = false, fivePressed = false, sixPressed = false, sevenPressed = false, eightPressed = false, ninePressed = false, pPressed = false, enterPressed = false, escPressed = false;
    int moveTime, moveDir;
    int tortlesMoveTime;
    int tortlesMoveDir;
    //FPS variable that can be changed (things are written in terms of FPS)
    int FPS = 60;
    double currentHealth = 3.0, maximumHealth = 3.0;
    String direction = "down";
    double distance;
    double slope;
    double b;
    public static float volume = 0f;
    boolean GUIOpen = true;
    boolean [] NPCInteracted = new boolean [3];
    int NPCNumber = 0;
    boolean[] chestLooted = new boolean[14];
    boolean[] pressChestOn = new boolean[14];
    int messageDisDelay;
    int playerDamage = 3;
    String moveDirection = "down";
    int tortlesMoveDirection = 0;
    int tortlesDirection = 0;
    boolean textDisappear = false;
    int swordNumber = 0;
    int textDisappearTime = 0;
    int messageDisappearNumber = -1;


    int grandmaDialogueIndex = -1;
    int wizardDialogueIndex = -1;
    int villagerDialogueIndex = -1;

    boolean dialogueActive = false;
    JLabel[] grandmaDialogueImages;
    JLabel[] wizardDialogueImages;
    JLabel[] villagerDialogueImages;


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
    Map<UUID, Integer> projectileArrayPoint  = new HashMap<>();
    Map<JLabel, String[]> mobFrameAnimationFrames = new HashMap<>();
    ArrayList<Point> playerPastPositions = new ArrayList<>();







    ArrayList<Point> playerPastPoints = new ArrayList<>();
    Map<String, ImageIcon> playerImages = new HashMap<>();
    Map<String, ImageIcon> tortlesImages = new HashMap<>();
    ArrayList<JLabel> obstacles = new ArrayList<>();
    ArrayList<JLabel> passables = new ArrayList<>();

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
    int setRespawnScreenCooldown = 0;

    //JLabel [] upAttack = new JLabel[7];
    //JLabel [] leftAttack = new JLabel[7];
    //JLabel [] downAttack = new JLabel[7];
    //JLabel [] rightAttack = new JLabel[7];

    String savedDirection;


    boolean debugMode = true; // false to enable, true to disable
    boolean placeCooldown = false;
    int swordUpgrade = 0;

    //JLabel cordBox = assets(20, 20, 75, 75, false, "images/GUI/coordinateBox.png", false);


    double tortlesAttackTwo = 3;
    double tortlesAttackThree = 1.5;


    JLabel press = GUIassets(125, 700, 760, 40, false, "images/GUI/pressE.png", false, 0, false);
    JLabel pressChest = GUIassets(125, 700, 760, 40, false, "images/GUI/pressE.png", false, 0, false);

    JLabel pressrespawn = GUIassets(75, 450, 860, 350, false, "images/text/respawn.png", false, 0, false);
    JLabel respawnSet = GUIassets(75, 0, 860, 350, false, "images/text/respawnSet.png", false, 0, false);




    JLabel iceSpikeOne = GUIassets( 430, 345, 125, 125, false, "images/mob/tortles/iceSpikeSmall.png", false, 1, false);
    JLabel iceSpikeTwo = GUIassets( 430, 345, 125, 125, false, "images/mob/tortles/iceSpikeBig.png", false, 1, false);

    JLabel gotApple = GUIassets( 130, 600, 1280, 320, false, "images/text/appleFind.png", false, 0, false);


    JLabel armourFound = GUIassets( 130, 600, 1280, 320, false, "images/equipment/ArmourFound.png", false, 0, false);


    //JLabel chest =  assets(2000, 1000, 200, 200, false, "images/assets/chest.png", false, 8, true);


   JLabel warp = assets(-1000, 1000, 100, 200, false, "images/assets/warpstone.png", false, 8, true);

    JLabel treebarrier = assets(2775, -5800, 590, 7500, debugMode, "images/assets/manymanytrees.png", false, 8, true);


    JLabel[] respawnPoints = new JLabel[] {
           assets( 2550, -1250, 150, 200, false, "images/assets/RespawnPoint.png", false, 8, true),
           assets(2811 , -7825, 150, 200,  false, "images/assets/respawnPoint.png", false, 8, true),
           assets(13943 , -7747, 150, 200,  false, "images/assets/respawnPoint.png", false, 8, true),
            assets(7283 , -645, 150, 200,  false, "images/assets/respawnPoint.png", false, 8, true),
           assets(14796 , -585, 150, 200,  false, "images/assets/respawnPoint.png", false, 8, true)
};

    //An array of all the chest images. The order is important (in relation to the chest method)
    JLabel[] chestImages = new JLabel[] {
        assets(3880, -525, 150, 150, false, "images/assets/chest.png", false, 8, true), // Apple
        assets(1708 , -2861, 150, 150,  false, "images/assets/chest.png", false, 8, true), // Stone
        assets(6000, -465, 150, 150, false, "images/assets/chest.png", false, 8, true), // Iron
        assets(10600, -495, 150, 150, false, "images/assets/chest.png", false, 8, true),  // Gold
        assets(15260, -1430, 150, 150, false, "images/assets/chest.png", false, 8, true),  // Ruby
        assets(15240, -6200, 150, 150, false, "images/assets/chest.png", false, 8, true),  // Emerald
        assets(3220, -7720, 150, 150, false, "images/assets/chest.png", false, 8, true),  // Diamond
        assets(9090, -4200, 150, 150, false, "images/assets/chest.png", false, 8, true),  // Apple
        assets(14950, -3820, 150, 150, false, "images/assets/chest.png", false, 8, true),  // Apple
        assets(11345, -7470, 150, 150, false, "images/assets/chest.png", false, 8, true),  // Apple
        assets(3675, -7610, 150, 150, false, "images/assets/chest.png", false, 8, true),  // Apple
        assets(8600, -2470, 150, 150, false, "images/assets/chest.png", false, 8, true),  // Armour
        assets(15540, -6890, 150, 150, false, "images/assets/chest.png", false, 8, true),  // Armour
        assets(4220, -7240, 150, 150, false, "images/assets/chest.png", false, 8, true)  // Armour

};


// mobs for the first section of the game
    JLabel ghost1 = mobCreation(2250, -3000, 200, 200, "images/mob/ghostLeft.png", 2, 20, 0.5, 300, 3, 600, 1);
    JLabel ghost2 = mobCreation(2250, -3600, 200, 200, "images/mob/ghostRight.png", 2, 20, 0.5, 300, 3, 600,1);
    JLabel ghost3 = mobCreation(2500, -4450, 200, 200, "images/mob/ghostLeft.png", 2, 20, 0.5, 300, 3, 600,1);
    JLabel ghost4 = mobCreation(2100, 5500, 200, 200, "images/mob/ghostRight.png", 2, 20, 0.5, 300, 3, 600,1);
    JLabel ghost5 = mobCreation(2800, -6300, 200, 200, "images/mob/ghostRight.png", 2, 20, 0.5, 300, 3, 600,1);
    JLabel ghost6 = mobCreation(4100, -6000, 200, 200, "images/mob/ghostLeft.png", 2, 20, 0.5, 300, 3, 600,1);
    JLabel ghost7 = mobCreation(4400, -5100, 200, 200, "images/mob/ghostRight.png", 2, 20, 0.5, 300, 3, 600,1);
    JLabel ghost8 = mobCreation(4450, -4150, 200, 200, "images/mob/ghostLeft.png", 2, 20, 0.5, 300, 3, 600,1);
    JLabel ghost9 = mobCreation(5000, -2800, 200, 200, "images/mob/ghostRight.png", 2, 20, 0.5, 300, 3, 600,1);
    JLabel ghost10 = mobCreation(4800, -2000, 200, 200, "images/mob/ghostLeft.png", 2, 20, 0.5, 300, 3, 600,1);
    JLabel ghost11 = mobCreation(4005, -2645, 200, 200, "images/mob/ghostLeft.png", 2, 20, 0.5, 300, 3, 600,1);
    JLabel ghost12 = mobCreation(4085, -1700, 200, 200, "images/mob/ghostLeft.png", 2, 20, 0.5, 300, 3, 600,1);
    JLabel slime1= mobCreation(4685, -655, 80, 65, "images/mob/littleslime.png", 2, 10, 0.5, 150, 5, 800,1);
    JLabel slime2= mobCreation(4810, -655, 80, 65, "images/mob/littleslime.png", 2, 10, 0.5, 150, 5, 800,1);
    JLabel slime3= mobCreation(5105, -455, 80, 65, "images/mob/littleslime.png", 2, 10, 0.5, 150, 5, 800,1);
    JLabel slime4= mobCreation(5350, -555, 80, 65, "images/mob/littleslime.png", 2, 10, 0.5, 150, 5, 800,1);
    JLabel slime5= mobCreation(5560, -655, 80, 65, "images/mob/littleslime.png", 2, 10, 0.5, 150, 5, 800,1);
    JLabel slime6= mobCreation(5750, -555, 80, 65, "images/mob/littleslime.png", 2, 10, 0.5, 150, 5, 800,1);
    JLabel slime7= mobCreation(4785, -655, 80, 65, "images/mob/littleslime.png", 2, 10, 0.5, 150, 5, 800,1);
    JLabel slime8= mobCreation(4910, -655, 80, 65, "images/mob/littleslime.png", 2, 10, 0.5, 150, 5, 800,1);
    JLabel slime9= mobCreation(5205, -455, 80, 65, "images/mob/littleslime.png", 2, 10, 0.5, 150, 5, 800,1);
    JLabel slime10= mobCreation(5450, -555, 80, 65, "images/mob/littleslime.png", 2, 10, 0.5, 150, 5, 800,1);
    JLabel slime11= mobCreation(5660, -655, 80, 65, "images/mob/littleslime.png", 2, 10, 0.5, 150, 5, 800,1);

    //this is the thing that kills you on the riddle path
    JLabel deadEnd= mobCreation(13300, -6700, 200, 165, "", 2, 10, 3, 300, 5, 500,1);

    //in the village
    JLabel zombie1= mobCreation(7760, -880, 80, 160, "images/mob/zombie.png", 2, 50, 1, 200, 2, 1000, 2);
    JLabel zombie2= mobCreation(7430, -2070, 80, 160, "images/mob/zombie.png", 2, 50, 1, 200, 2, 1000, 2);
    JLabel zombie3= mobCreation(7570, -3130, 80, 160, "images/mob/zombie.png", 2, 50, 1, 200, 2, 1000, 2);
    JLabel zombie4= mobCreation(7920, -3870, 80, 160, "images/mob/zombie.png", 2, 50, 1, 200, 2, 1000, 2);
    JLabel zombie5= mobCreation(8000, -4910, 80, 160, "images/mob/zombie.png", 2, 50, 1, 200, 2, 1000, 2);
    JLabel zombie6= mobCreation(9120, -3770, 80, 160, "images/mob/zombie.png", 2, 50, 1, 200, 2, 1000, 2);
    JLabel zombie7= mobCreation(9090, -2850, 80, 160, "images/mob/zombie.png", 2, 50, 1, 200, 2, 1000, 2);
    JLabel zombie8= mobCreation(9200, -1950, 80, 160, "images/mob/zombie.png", 2, 50, 1, 200, 2, 1000, 2);
    JLabel zombie9= mobCreation(10070, -110, 80, 160, "images/mob/zombie.png", 2, 50, 1, 200, 2, 1000, 2);

    //after the maze
    JLabel spider1= mobCreation(15260, -1330, 160, 80, "images/mob/spider.png", 2, 20, 0.5, 200, 5, 600, 1);
    JLabel spider2= mobCreation(15270, -2400, 160, 80, "images/mob/spider.png", 2, 20, 0.5, 200, 5, 600, 1);
    JLabel spider3= mobCreation(15450, -3280, 160, 80, "images/mob/spider.png", 2, 20, 0.5, 200, 5, 600, 1);
    JLabel spider4= mobCreation(15000, -4400, 160, 80, "images/mob/spider.png", 2, 20, 0.5, 200, 5, 600, 1);
    JLabel spider5= mobCreation(15000, -6040, 160, 80, "images/mob/spider.png", 2, 20, 0.5, 200, 5, 600, 1);
    JLabel spider6= mobCreation(15560, -6950, 160, 80, "images/mob/spider.png", 2, 20, 0.5, 200, 5, 600, 1);
    JLabel spider7= mobCreation(15000, -6040, 160, 80, "images/mob/spider.png", 2, 20, 0.5, 200, 5, 600, 1);
    JLabel spider8= mobCreation(15260, -5200, 160, 80, "images/mob/spider.png", 2, 20, 0.5, 200, 5, 600, 1);

//last section
    JLabel spider9= mobCreation(11920, -7720, 160, 80, "images/mob/spider.png", 2, 20, 0.5, 200, 5, 600, 1);
    JLabel zombie10= mobCreation(11020, -7040, 80, 160, "images/mob/zombie.png", 2, 50, 1, 200, 2, 1000, 2);
    JLabel ghost13= mobCreation(9430, -6930, 200, 200, "images/mob/ghostRight.png", 2, 20, 0.5, 300, 3, 600,1);
    JLabel slime12= mobCreation(8820, -5770, 80, 65, "images/mob/littleslime.png", 2, 10, 0.5, 150, 5, 800,1);
    JLabel slime13= mobCreation(8570, -6250, 80, 65, "images/mob/littleslime.png", 2, 10, 0.5, 150, 5, 800,1);
    JLabel spider10= mobCreation(7510, -6450, 160, 80, "images/mob/spider.png", 2, 20, 0.5, 200, 5, 600, 1);
    JLabel ghost14= mobCreation(6670, -7100, 200, 200, "images/mob/ghostRight.png", 2, 20, 0.5, 200, 5, 600, 1);
    JLabel zombie11= mobCreation(5540, -7270, 80, 160, "images/mob/zombie.png", 2, 50, 1, 200, 2, 1000, 2);
    JLabel zombie12= mobCreation(4720, -7470, 80, 160, "images/mob/zombie.png", 2, 50, 1, 200, 2, 1000, 2);
    JLabel zombie13= mobCreation(3650, -7590, 80, 160, "images/mob/zombie.png", 2, 50, 1, 200, 2, 1000, 2);
    JLabel spider11= mobCreation(3640, -7590, 160, 80, "images/mob/spider.png", 2, 20, 0.5, 200, 5, 600, 1);
   


    JLabel tortles = mobCreation(21600, -2272, 200,376, "images/mob/tortles/downStanding.png", 2, 1000, 1, 150, 2, 800,1);

// three npcs
    JLabel NPC = assets(2100,  -2000, 100, 200, false, "images/NPC/Grandma/grandma.png", false, 2, true);
    JLabel NPC3 = assets(9200,  -1600, 126, 238, false, "images/NPC/Maze/secondnpc.png", false, 2, true);
    JLabel NPC2 = assets(14450,  -7100, 116, 228, false, "images/NPC/Wizard/wizard.png", false, 2, true);


// lake barriers
    JLabel waterBarrier = assets(0, 900, 20000, 800, debugMode, "", false, 3 , false);
    JLabel lakeBarrier1 = assets(5400, -5875, 1975, 600, debugMode, "",false, 3, false);
    JLabel lakeBarrier2 = assets(5200, -5225, 2175,250, debugMode, "", false, 3, false);
    JLabel lakeBarrier3 = assets(5000, -5475, 1550,2000, debugMode, "", false, 3, false);
    JLabel lakeBarrier4 = assets(5400, -3875, 1650,2200, debugMode, "", false, 3, false);
    JLabel lakeBarrier5 = assets(5600, -1875, 1550,760, debugMode, "", false, 3, false);
    JLabel lakeBarrier6 = assets(6450, -4475, 700,560, debugMode, "", false, 3, false);


    //JLabel waterBarrier = assets(0, 900, 4800, 800, true, "", false, 3 , false);

    JLabel SScredits = GUIassets(0, 0, 1040, 780,false, "images/GUI/creditsScreen.png", false, 2, true);
    JLabel startMenu = GUIassets(0,0, 1000, 1000, false, "images/GUI/placeHolderStart.png", false, 2, true);
    JLabel startCredits = GUIassets(100, 300, 400, 40, false, "images/GUI/startScreenCredits.png", false, 1, true);
    JLabel startPlay = GUIassets(100, 200, 400, 40, false, "images/GUI/startScreenNew.png", false, 1, true);
    JLabel startQuit = GUIassets(100, 400, 400, 40, false, "images/GUI/startScreenQuit.png", false, 1, true);
    JLabel currentSelection = GUIassets(25, 192, 60, 60, false, "images/GUI/selectionarrow.png", false, 1, true);

//Background for the NPC Dialogue
    JLabel NPCBackground = GUIassets(-170, 370, 1320, 500, false, "images/NPC/NPCDialogueBackground.png", false, 2, false);

    /*
    JLabel NPCScroller1 = GUIassets(-150, 590, 1000, 30, false, "images/NPC/coverDialogue.png", false, 0, false);
    JLabel NPCScroller2 = GUIassets(-150, 615, 1000, 30, false, "images/NPC/coverDialogue.png", false, 0, false);
    JLabel NPCScroller3 = GUIassets(-150, 640, 1000, 30, false, "images/NPC/coverDialogue.png", false, 0, false);
    JLabel NPCScroller4 = GUIassets(-150, 665, 1000, 30, false, "images/NPC/coverDialogue.png", false, 0, false);
    */

    JLabel warpBack = assets(21553 , -4705, 100, 200,  false, "images/assets/warpStone.png", false, 8, true);
    JLabel warpArena = assets(2502 , -7564, 100, 200,  false, "images/assets/warpStone.png", false, 8, true);
    JLabel rockThird = assets( 2500, 2500, 200, 200, false, "images/assets/rock.png", false, 8, true);

    // lots of tress


    JLabel Rock274736 = assets(2353 , -7107, 200, 200,  debugMode, "images/assets/rock.png", false, 8, true);
    JLabel Rock3320042 = assets(2278 , -7257, 200, 200,  debugMode, "images/assets/rock.png", false, 8, true);
    JLabel Rock2848022 = assets(2178 , -7432, 200, 200,  debugMode, "images/assets/rock.png", false, 8, true);
    JLabel Rock4690770 = assets(2178 , -7557, 200, 200,  debugMode, "images/assets/rock.png", false, 8, true);
    JLabel Rock8165025 = assets(2178 , -7732, 200, 200,  debugMode, "images/assets/rock.png", false, 8, true);
    JLabel Rock9260882 = assets(2178 , -7832, 200, 200,  debugMode, "images/assets/rock.png", false, 8, true);
    JLabel Rock2335392 = assets(2278 , -7932, 200, 200,  debugMode, "images/assets/rock.png", false, 8, true);
    JLabel Rock754881 = assets(2403 , -8007, 200, 200,  debugMode, "images/assets/rock.png", false, 8, true);
    JLabel Rock1726032 = assets(1577 , -8031, 200, 200,  debugMode, "images/assets/rock.png", false, 8, true);
    JLabel Rock8461454 = assets(1577 , -7856, 200, 200,  debugMode, "images/assets/rock.png", false, 8, true);
    JLabel Rock4868639 = assets(1577 , -7681, 200, 200,  debugMode, "images/assets/rock.png", false, 8, true);
    JLabel Rock3939617 = assets(1577 , -7531, 200, 200,  debugMode, "images/assets/rock.png", false, 8, true);
    JLabel Rock8602846 = assets(1577 , -7381, 200, 200,  debugMode, "images/assets/rock.png", false, 8, true);
    JLabel Rock1661273 = assets(1577 , -7281, 200, 200,  debugMode, "images/assets/rock.png", false, 8, true);
    JLabel Rock8212042 = assets(1577 , -7131, 200, 200,  debugMode, "images/assets/rock.png", false, 8, true);
    JLabel Rock1011629 = assets(1577 , -7081, 200, 200,  debugMode, "images/assets/rock.png", false, 8, true);
    JLabel Rock4685957 = assets(1577 , -6856, 200, 200,  debugMode, "images/assets/rock.png", false, 8, true);
    JLabel Rock7075964 = assets(1577 , -6856, 200, 200,  debugMode, "images/assets/rock.png", false, 8, true);
    JLabel Rock1051545 = assets(1577 , -6956, 200, 200,  debugMode, "images/assets/rock.png", false, 8, true);
    JLabel Rock9392083 = assets(1702 , -8056, 200, 200,  debugMode, "images/assets/rock.png", false, 8, true);
    JLabel Rock5679939 = assets(1877 , -8056, 200, 200,  debugMode, "images/assets/rock.png", false, 8, true);
    JLabel Rock7836519 = assets(2052 , -8056, 200, 200,  debugMode, "images/assets/rock.png", false, 8, true);
    JLabel Rock487015 = assets(2227 , -8056, 200, 200,  debugMode, "images/assets/rock.png", false, 8, true);
    JLabel Rock939617 = assets(2377 , -8056, 200, 200,  debugMode, "images/assets/rock.png", false, 8, true);
    JLabel Tree539529 = assets(1643 , -6687, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree5996628 = assets(1743 , -6687, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree3455053 = assets(1868 , -6687, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree571772 = assets(2043 , -6687, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree9495548 = assets(2193 , -6687, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree8499027 = assets(2318 , -6687, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree8911215 = assets(2563 , -7082, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree8792634 = assets(2438 , -6932, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree2059171 = assets(2438 , -6807, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree8359082 = assets(14414 , -5342, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree3251671 = assets(14414 , -5217, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree1580588 = assets(14414 , -5092, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree6470467 = assets(14414 , -4967, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree9209422 = assets(14339 , -4842, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree8579339 = assets(14339 , -4692, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree2103638 = assets(14082 , -4615, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree4363909 = assets(14455 , -5873, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree7076734 = assets(14455 , -5748, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree5424698 = assets(14230 , -5598, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree2776118 = assets(14608 , -3961, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree8798578 = assets(14925 , -3643, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree4504854 = assets(14925 , -3518, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree5445049 = assets(14925 , -3393, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree2239979 = assets(14925 , -3243, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree8474601 = assets(14925 , -3043, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree9757249 = assets(14925 , -2843, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree5980306 = assets(15025 , -2743, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree4769336 = assets(15025 , -2493, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree5627789 = assets(14711 , -2029, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree3613034 = assets(14711 , -1904, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree8319634 = assets(14711 , -1829, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree2728386 = assets(14711 , -1704, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree3240836 = assets(14711 , -1454, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree9062171 = assets(10211 , -1574, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree2281341 = assets(9663 , -1001, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree3859802 = assets(10360 , -448, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree5495403 = assets(14062 , -435, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree7999274 = assets(14312 , -435, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree2077032 = assets(14137 , 215, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree1140736 = assets(14103 , 516, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree894129 = assets(14382 , 100, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree3243961 = assets(14549 , -267, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree7425187 = assets(14624 , 358, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree2328316 = assets(14399 , 583, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Rock7881951 = assets(14841 , -3794, 100, 100,  debugMode, "images/assets/rock.png", false, 8, true);
    JLabel Tree1256711 = assets(13379 , -6657, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree8185935 = assets(13071 , -6574, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree3513786 = assets(12746 , -6574, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree3509063 = assets(12403 , -6491, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree772701 = assets(12101 , -6314, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree407964 = assets(11710 , -6618, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree6014213 = assets(11897 , -6980, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree6703167 = assets(12175 , -7133, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree5242107 = assets(12494 , -7202, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree6610135 = assets(12719 , -7202, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree8752945 = assets(11797 , -6325, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree9056618 = assets(11617 , -6895, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree2443879 = assets(13240 , -6343, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree3364519 = assets(13215 , -6038, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree8914757 = assets(14065 , -6873, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree3961646 = assets(15897 , -110, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree8767579 = assets(15897 , 15, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree7782009 = assets(15897 , 140, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree6676541 = assets(15897 , 265, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree1055690 = assets(15897 , 390, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree3913108 = assets(15897 , 540, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree1477942 = assets(15897 , 715, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree151925 = assets(15889 , 622, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree2827591 = assets(12140 , 288, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree5664389 = assets(12742 , 275, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree5457556 = assets(13822 , 285, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree8607514 = assets(14196 , -129, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree7259531 = assets(15290 , 450, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree1911749 = assets(15426 , -69, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree9294134 = assets(7084 , -6067, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree4546444 = assets(7184 , -6017, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree6302609 = assets(7284 , -5967, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree8082771 = assets(7484 , -5967, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree8322091 = assets(7384 , -5967, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree5190735 = assets(7559 , -5967, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree5154436 = assets(7659 , -5967, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree8806342 = assets(7784 , -5967, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree185952 = assets(7909 , -5967, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree2399591 = assets(8059 , -5967, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree6375606 = assets(8184 , -5792, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree5183165 = assets(8134 , -5842, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree2632178 = assets(8284 , -5717, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree4750703 = assets(8384 , -5717, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree8452285 = assets(8509 , -5717, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree954059 = assets(8634 , -5717, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree3913763 = assets(8734 , -5717, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree2658908 = assets(8859 , -5717, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree8890663 = assets(9009 , -5717, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree6101518 = assets(9134 , -5717, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree2812298 = assets(9259 , -5717, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree4056913 = assets(9384 , -5717, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree5220054 = assets(9484 , -5717, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree6976626 = assets(9609 , -5717, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree8729470 = assets(9834 , -5717, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree5125693 = assets(9759 , -5717, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree3384917 = assets(9934 , -5717, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree7912554 = assets(10059 , -5717, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree3559740 = assets(10184 , -5717, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree7837572 = assets(10284 , -5717, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree1922105 = assets(10384 , -5717, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree467559 = assets(10509 , -5717, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree6128170 = assets(10659 , -5717, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree3662865 = assets(10784 , -5717, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree2048156 = assets(10934 , -5717, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree2934467 = assets(11084 , -5717, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree6862194 = assets(11234 , -5717, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree691378 = assets(11384 , -5717, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree8322462 = assets(11559 , -5717, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree4560341 = assets(11734 , -5717, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree5063331 = assets(11909 , -5717, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree9632926 = assets(11884 , -5717, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree1905667 = assets(11984 , -5717, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree6039112 = assets(12084 , -5717, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree3654647 = assets(12184 , -5717, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree2686439 = assets(12309 , -5717, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree8598639 = assets(12459 , -5717, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree271638 = assets(12559 , -5717, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree6602032 = assets(12409 , -5717, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree8105100 = assets(12634 , -5717, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree6825928 = assets(12734 , -5717, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree5131757 = assets(12859 , -5717, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree2342103 = assets(13009 , -5717, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree2435384 = assets(13109 , -5717, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree6518572 = assets(13105 , -5578, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree3033592 = assets(13105 , -5428, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree2496826 = assets(13180 , -5303, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree1870690 = assets(13280 , -5303, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree8454682 = assets(13380 , -5303, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree464437 = assets(13480 , -5303, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree8914726 = assets(13605 , -5303, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree3935739 = assets(13705 , -5303, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree5032229 = assets(13865 , -5838, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree4329269 = assets(13790 , -5638, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree3650788 = assets(13790 , -5513, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree805723 = assets(13790 , -5388, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree7676965 = assets(13783 , -5306, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree947007 = assets(14005 , -6083, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree5881947 = assets(14130 , -6083, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree5964617 = assets(14255 , -6083, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree3129453 = assets(14355 , -6083, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree7845320 = assets(14455 , -6083, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree6952321 = assets(14008 , -6011, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree6619962 = assets(14583 , -6036, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree708655 = assets(14683 , -6036, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree8119923 = assets(15372 , -5905, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree9752098 = assets(15472 , -5905, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree5316796 = assets(15597 , -5905, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree5315600 = assets(15772 , -5905, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree2055909 = assets(15722 , -5905, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree1456271 = assets(14341 , -1744, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree5531316 = assets(10686 , -1673, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree6419097 = assets(14720 , -1852, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree952845 = assets(14720 , -2002, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree9284153 = assets(14720 , -2152, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree7675243 = assets(14720 , -2402, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree1556548 = assets(14414 , -5301, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree39616467 = assets(15897 , -110, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree5648767579 = assets(15897 , 15, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree77820097 = assets(15897 , 140, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree6656476541 = assets(15897 , 265, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree410555650 = assets(15897 , 390, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree39134108 = assets(15897 , 540, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree5345151925 = assets(15889 , 622, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree6995224 = assets(8996 , -8185, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree2594009 = assets(9096 , -8185, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree1769738 = assets(9221 , -8185, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree7569855 = assets(9371 , -8185, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree4509706 = assets(9546 , -8185, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree5822404 = assets(9446 , -8185, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree545418 = assets(9721 , -8185, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree4735210 = assets(9621 , -8185, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree5051639 = assets(9846 , -8185, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree1619688 = assets(9946 , -8185, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree90474 = assets(10071 , -8185, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree2023031 = assets(10196 , -8185, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree2925140 = assets(10321 , -8185, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree6036291 = assets(10446 , -8185, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree3199280 = assets(10596 , -8185, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree7128280 = assets(10771 , -8185, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree4875937 = assets(10721 , -8185, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree6461475 = assets(10896 , -8185, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree6163797 = assets(11021 , -8185, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree9189558 = assets(11196 , -8185, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree3910251 = assets(11071 , -8185, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree5792930 = assets(11371 , -8185, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree2652457 = assets(11246 , -8185, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree2885247 = assets(11496 , -8185, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree7385978 = assets(11621 , -8185, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree2333556 = assets(11746 , -8185, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree489446 = assets(11896 , -8185, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree2096077 = assets(12021 , -8185, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree9642619 = assets(12121 , -8185, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree5420588 = assets(12221 , -8185, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree5160370 = assets(12346 , -8185, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree9394205 = assets(12496 , -8185, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree2258626 = assets(12596 , -8185, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree8130165 = assets(12693 , -8182, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree9348828 = assets(12793 , -8182, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree7863003 = assets(12943 , -8182, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree5659619 = assets(13068 , -8182, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree50792 = assets(13193 , -8182, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree6099386 = assets(13343 , -8182, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree7308405 = assets(13493 , -8182, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree4248191 = assets(13618 , -8182, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree5082069 = assets(13793 , -8182, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree4306000 = assets(13918 , -8182, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree7791825 = assets(14018 , -8182, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree3485773 = assets(14143 , -8182, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree1490437 = assets(14243 , -8182, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree9140949 = assets(14368 , -8182, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree9596250 = assets(14518 , -8182, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree1429318 = assets(14643 , -8182, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree4041571 = assets(14768 , -8182, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree6767830 = assets(14893 , -8182, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree231319 = assets(15018 , -8182, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree9110307 = assets(15118 , -8182, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree929468 = assets(15368 , -8182, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree2223194 = assets(15268 , -8182, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree5889756 = assets(15593 , -8182, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree3840864 = assets(15893 , -8182, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree1435134 = assets(15743 , -8182, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree1704949 = assets(15493 , -8182, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree9206715 = assets(2474 , -8198, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree4085487 = assets(2549 , -8198, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree3507432 = assets(2674 , -8198, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree434540 = assets(2849 , -8198, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree3708324 = assets(2974 , -8198, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree3834086 = assets(3199 , -8198, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree15040 = assets(3099 , -8198, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree4611611 = assets(3324 , -8198, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree9698629 = assets(3524 , -8198, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree1870970 = assets(3524 , -8198, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree3447164 = assets(3399 , -8198, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree6230488 = assets(3649 , -8198, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree9182955 = assets(3774 , -8198, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree4344606 = assets(3924 , -8198, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree2442910 = assets(4099 , -8198, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree5103322 = assets(4224 , -8198, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree6573016 = assets(4224 , -8198, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree6429359 = assets(4324 , -8198, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree9243609 = assets(4524 , -8198, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree7909173 = assets(4449 , -8198, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree4628434 = assets(4699 , -8198, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree3345029 = assets(4624 , -8198, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree8642269 = assets(4799 , -8198, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree5351563 = assets(4924 , -8198, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree1190198 = assets(5074 , -8198, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree1898330 = assets(5174 , -8198, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree7893570 = assets(5274 , -8198, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree9182584 = assets(5424 , -8198, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree326971 = assets(5549 , -8198, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree2273167 = assets(5674 , -8198, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree9686776 = assets(5824 , -8198, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree1872221 = assets(5899 , -8198, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree2931180 = assets(5999 , -8198, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree3478232 = assets(6124 , -8198, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree1856167 = assets(6274 , -8198, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree1789368 = assets(6424 , -8198, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree6030323 = assets(6524 , -8198, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree4854558 = assets(6674 , -8198, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree6888383 = assets(6849 , -8198, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree7161483 = assets(6974 , -8198, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree7119951 = assets(7124 , -8198, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree352324 = assets(7274 , -8198, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree1483349 = assets(7424 , -8198, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree2792651 = assets(7549 , -8198, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree9678301 = assets(7649 , -8198, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree1905339 = assets(7749 , -8198, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree1435172 = assets(7899 , -8198, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree4545246 = assets(8099 , -8198, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree8688573 = assets(8024 , -8198, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree3163284 = assets(8149 , -8198, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree9921715 = assets(8249 , -8198, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree7928291 = assets(8349 , -8198, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree473342 = assets(8499 , -8198, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree5085912 = assets(8424 , -8198, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree7926294 = assets(8624 , -8198, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree8785962 = assets(8749 , -8198, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree9436639 = assets(8874 , -8198, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree8458095 = assets(15894 , -5224, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree9496351 = assets(15894 , -5116, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree9687084 = assets(15894 , -5008, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree8936391 = assets(15894 , -4912, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree2461937 = assets(15894 , -4792, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree9124816 = assets(15894 , -4708, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree5268518 = assets(15894 , -4624, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree5413058 = assets(15894 , -4534, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree6138448 = assets(15894 , -4432, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree3354822 = assets(15894 , -4342, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree1895213 = assets(15894 , -4240, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree5899497 = assets(15894 , -4150, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree1009735 = assets(15894 , -4042, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree5732778 = assets(15894 , -3952, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree7177675 = assets(15894 , -3874, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree1981486 = assets(15894 , -3724, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree1967982 = assets(15894 , -3628, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree7692972 = assets(15894 , -3514, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree5133889 = assets(15894 , -3394, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree8729518 = assets(15894 , -3292, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree4339900 = assets(15894 , -3202, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree2166631 = assets(15894 , -3082, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree6213314 = assets(15894 , -2944, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree5538868 = assets(15894 , -2806, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree4505246 = assets(15894 , -2674, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree8831994 = assets(15894 , -2536, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree628553 = assets(15894 , -2386, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree1876186 = assets(15894 , -2236, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree6469648 = assets(15894 , -2104, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree7558457 = assets(15894 , -1966, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree8845061 = assets(15894 , -1804, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree9256199 = assets(15894 , -1654, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree1645444 = assets(15894 , -1522, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree1216927 = assets(15894 , -1348, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree3089102 = assets(15894 , -1180, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree6797450 = assets(15894 , -1030, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree6760275 = assets(15894 , -868, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree8960257 = assets(15894 , -706, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree1556352 = assets(15894 , -550, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree4043895 = assets(15894 , -406, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree5291694 = assets(15894 , -250, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree6321688 = assets(15531 , -1492, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree2638378 = assets(15386 , -1752, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree1389579 = assets(15670 , -2576, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree1117048 = assets(15670 , -3020, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree9035911 = assets(15591 , -3730, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree9931432 = assets(14969 , -3858, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree5406515 = assets(14367 , -4042, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree9171430 = assets(14297 , -4374, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree3356492 = assets(14349 , -4768, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree51817 = assets(14695 , -5675, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree3651251 = assets(15343 , -6503, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree6822505 = assets(15471 , -6841, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree6825045 = assets(14241 , -7189, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree4354294 = assets(13084 , -7367, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree1113516 = assets(13626 , -7807, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree1399506 = assets(13404 , -7951, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree9941675 = assets(13120 , -8063, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree2534819 = assets(12855 , -8083, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree8370739 = assets(12543 , -8083, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree3854757 = assets(11083 , -7073, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree992857 = assets(10159 , -7478, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree796117 = assets(9653 , -7278, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree9725106 = assets(9053 , -6918, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree5889562 = assets(8746 , -6401, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree3412042 = assets(8158 , -6593, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree9764398 = assets(7760 , -6633, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree8125214 = assets(7590 , -6799, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree8042317 = assets(7415 , -7107, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree7479968 = assets(7215 , -7387, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree7667771 = assets(6995 , -7677, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree2512278 = assets(6687 , -7777, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree9215234 = assets(6241 , -7889, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree5915733 = assets(5669 , -7869, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree4889782 = assets(2798 , -6603, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree9823986 = assets(2990 , -6603, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree2257050 = assets(3446 , -6492, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree3531412 = assets(2606 , -7302, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree4317160 = assets(2691 , -7282, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree6214228 = assets(2769 , -7303, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree7714919 = assets(2880 , -7252, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree641695 = assets(2988 , -7252, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree8584396 = assets(3102 , -7252, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree2992229 = assets(3222 , -7252, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree7997346 = assets(3342 , -7252, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree6868632 = assets(3390 , -7183, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree8823881 = assets(3486 , -7183, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree9384217 = assets(3612 , -7183, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree7521373 = assets(3738 , -7138, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree4432626 = assets(3858 , -7093, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree1369880 = assets(4014 , -7048, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree9374114 = assets(4196 , -7080, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree6561023 = assets(4124 , -7080, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree9620677 = assets(3950 , -7080, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree7597664 = assets(4276 , -7040, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree4897421 = assets(4434 , -7039, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree9855444 = assets(4382 , -7071, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree5370506 = assets(4522 , -7091, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree7482388 = assets(4618 , -7139, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree2091158 = assets(4736 , -7134, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree9563150 = assets(4816 , -7079, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree6195529 = assets(4996 , -7079, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree1550825 = assets(4922 , -7107, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree4562546 = assets(5096 , -7107, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree3177897 = assets(5188 , -7139, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree5863615 = assets(5394 , -7099, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree1612034 = assets(5304 , -7099, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree2492666 = assets(5502 , -7099, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree1640866 = assets(5616 , -7099, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree8252251 = assets(5700 , -7135, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree6678393 = assets(5790 , -7147, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree4057316 = assets(5892 , -7120, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree2815489 = assets(6000 , -7120, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree9625092 = assets(6138 , -7090, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree6826323 = assets(6278 , -7080, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree6125837 = assets(6422 , -7080, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree735368 = assets(6526 , -6995, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree6209566 = assets(6710 , -6930, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree4396413 = assets(6834 , -6760, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree4489681 = assets(6884 , -6597, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree1592387 = assets(6920 , -6342, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree4402511 = assets(6936 , -6208, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree8895579 = assets(6952 , -6110, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree9975981 = assets(6888 , -6433, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree3301276 = assets(6744 , -6775, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree2190578 = assets(6597 , -6883, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree9239238 = assets(6320 , -6969, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree5717459 = assets(6198 , -7057, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree6658485 = assets(5999 , -7020, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree9444391 = assets(5845 , -7028, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree4559396 = assets(5710 , -7040, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree2578459 = assets(5542 , -7040, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree2607422 = assets(5350 , -7040, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree1630222 = assets(5194 , -7040, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree2704923 = assets(5062 , -7040, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree4277952 = assets(4888 , -7040, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree1928685 = assets(4716 , -7030, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree6180223 = assets(4606 , -7010, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree3773204 = assets(4156 , -7010, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree1103873 = assets(3474 , -7210, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree793133 = assets(3294 , -7210, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree3271991 = assets(3114 , -7210, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree4537717 = assets(2946 , -7210, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree1279115 = assets(2826 , -7210, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree8047198 = assets(2712 , -7210, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree3090544 = assets(2576 , -7266, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree8473991 = assets(2506 , -7286, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree2018106 = assets(4401 , -7003, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree7844071 = assets(6111 , -7018, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree9780511 = assets(6425 , -6933, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree5236789 = assets(6242 , -6948, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree8103832 = assets(6891 , -6307, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree4290940 = assets(6807 , -6613, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree8243648 = assets(6657 , -6787, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree2904261 = assets(4015 , -7028, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree4418733 = assets(3375 , -7138, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree3151693 = assets(3539 , -7116, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree3696520 = assets(3201 , -7165, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree6080178 = assets(2554 , -7211, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree9418599 = assets(1999 , -6179, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree9764304 = assets(2215 , -5087, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree4471253 = assets(1962 , -4612, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree8636901 = assets(2216 , -4053, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree2557818 = assets(1690 , -3446, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree5265643 = assets(2298 , -3403, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree7954813 = assets(2071 , -2780, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree8448378 = assets(1895 , -2085, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree5986250 = assets(1514 , -6702, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree6342155 = assets(1509 , -6583, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree8500856 = assets(1431 , -6523, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree7363945 = assets(1251 , -6523, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree8974681 = assets(1341 , -6523, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree1278418 = assets(1197 , -6523, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree7723548 = assets(1083 , -6523, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree7560305 = assets(975 , -6523, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree6311823 = assets(861 , -6523, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree5381580 = assets(729 , -6523, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree3252481 = assets(577 , -6539, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree5582701 = assets(451 , -6539, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree4778675 = assets(319 , -6539, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree4178348 = assets(217 , -6539, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree8467457 = assets(121 , -6539, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree7727334 = assets(37 , -6539, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree3333056 = assets(-67 , -6577, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree6048703 = assets(-67 , -6511, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree942262 = assets(-67 , -6427, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree9798835 = assets(-67 , -6325, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree9553856 = assets(-67 , -6199, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree3019335 = assets(-67 , -6055, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree8819895 = assets(-67 , -5929, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree4010428 = assets(-67 , -5773, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree437711 = assets(-67 , -5635, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree773776 = assets(-67 , -5515, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree9692262 = assets(-67 , -5407, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree202250 = assets(-67 , -5275, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree1120178 = assets(-67 , -5137, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree7479147 = assets(-67 , -5005, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree5756555 = assets(-67 , -4873, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree4498280 = assets(-67 , -4729, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree7447033 = assets(-67 , -4591, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree3148577 = assets(-67 , -4465, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree6530406 = assets(-67 , -4339, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree2047667 = assets(-67 , -4195, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree3487278 = assets(-67 , -4069, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree8689980 = assets(-67 , -3925, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree5730096 = assets(-67 , -3781, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree4334159 = assets(-67 , -3643, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree3058002 = assets(-67 , -3487, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree6536043 = assets(-67 , -3343, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree2050908 = assets(-67 , -3217, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree5687754 = assets(-67 , -3061, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree5122238 = assets(-67 , -2929, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree2806883 = assets(-67 , -2791, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree4535955 = assets(-67 , -2647, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree8095523 = assets(-67 , -2503, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree6524478 = assets(-67 , -2359, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree4326009 = assets(-67 , -2215, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree1979602 = assets(-67 , -2059, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree8184004 = assets(-67 , -1915, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree1944513 = assets(-67 , -1783, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree2491834 = assets(-67 , -1639, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree2383839 = assets(-67 , -1489, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree5333429 = assets(-67 , -1333, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree178805 = assets(-67 , -1171, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree1141 = assets(-67 , -1003, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree2870748 = assets(-67 , -853, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree5284431 = assets(-67 , -697, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree7899588 = assets(-67 , -547, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree5727540 = assets(-67 , -373, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree2651477 = assets(-67 , -181, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree9310792 = assets(-67 , -25, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree6895424 = assets(-67 , 137, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree7677106 = assets(-67 , 299, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree1661864 = assets(-67 , 449, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree5057161 = assets(-67 , 599, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree6832098 = assets(15559 , -3380, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree8512689 = assets(15200 , -4458, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree6232756 = assets(15042 , -5068, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree5832627 = assets(14460 , -5068, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree5234340 = assets(14425 , -5462, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree7437695 = assets(14205 , -5818, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree1536450 = assets(13914 , -6004, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree5320075 = assets(13960 , -6560, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree6819830 = assets(14334 , -6415, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree2447868 = assets(14918 , -6639, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree498763 = assets(15318 , -7357, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree1356730 = assets(15283 , -7991, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree8516220 = assets(14767 , -7661, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree2705686 = assets(15893 , -8163, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree5119799 = assets(15893 , -8073, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree3481253 = assets(15893 , -7995, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree815187 = assets(15893 , -7923, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree9308511 = assets(15893 , -7791, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree3771774 = assets(15893 , -7695, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree2656600 = assets(15893 , -7593, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree3299039 = assets(15893 , -7473, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree572142 = assets(15893 , -7347, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree3726092 = assets(15941 , -7203, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree2806551 = assets(15949 , -7022, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree1360115 = assets(15949 , -6890, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree6097244 = assets(15961 , -6755, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree88208 = assets(15961 , -6629, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree9100371 = assets(15961 , -6527, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree6693837 = assets(15961 , -6413, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree7641452 = assets(15961 , -6311, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree1811973 = assets(15956 , -6204, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree3044499 = assets(15931 , -6047, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree8531071 = assets(15931 , -5939, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree220284 = assets(15896 , -5838, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree7506151 = assets(15896 , -5742, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree3746810 = assets(15896 , -5646, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree4772677 = assets(15896 , -5544, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree2572910 = assets(15908 , -5445, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree8599361 = assets(15908 , -5361, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);

    JLabel House6307983 = assets(7843 , -1214, 600, 600,  debugMode, "images/assets/houses/house.png", false, 8, true);
    JLabel House6347268 = assets(7877 , -2268, 600, 600,  debugMode, "images/assets/houses/house.png", false, 8, true);
    JLabel House5613608 = assets(7715 , -3102, 600, 600,  debugMode, "images/assets/houses/house.png", false, 8, true);
    JLabel HouseTwo9396074 = assets(7628, -3758, 600, 600,  debugMode, "images/assets/houses/houseTwo.png", false, 8, true);
    JLabel HouseTwo6064984 = assets(7907 , -4356, 600, 600,  debugMode, "images/assets/houses/houseTwo.png", false, 8, true);
    JLabel HouseTwo924866 = assets(8508 , -1141, 600, 600,  debugMode, "images/assets/houses/houseTwo.png", false, 8, true);
    JLabel HouseTwo7756268 = assets(1808 , -2580, 400, 400,  debugMode, "images/assets/houses/houseTwo.png", false, 8, true);


// ALL for the maze (its very big yes)
    JLabel LittleBush4476318 = assets(10904 , -486, 100, 100,  debugMode, "images/assets/littlebush.png", false, 8, true);
    JLabel LittleBush4131510 = assets(10904 , -906, 100, 100,  debugMode, "images/assets/littlebush.png", false, 8, true);
    JLabel LittleBush2396163 = assets(10904 , -1386, 100, 100,  debugMode, "images/assets/littlebush.png", false, 8, true);
    JLabel warpOne = assets(10906 , -1194, 100, 200,  false, "images/assets/warpStone.png", false, 8, true);
    JLabel warpTwo = assets(10906 , -738, 100, 200,  false, "images/assets/warpStone.png", false, 8, true);
    JLabel LittleBush8318242 = assets(11540 , -738, 100, 100,  debugMode, "images/assets/littlebush.png", false, 8, true);
    JLabel LittleBush2976735 = assets(11544 , -1150, 100, 100,  debugMode, "images/assets/littlebush.png", false, 8, true);
    JLabel LittleBush5659796 = assets(11544 , -1618, 100, 100,  debugMode, "images/assets/littlebush.png", false, 8, true);
    JLabel LittleBush9842894 = assets(11544 , -370, 100, 100,  debugMode, "images/assets/littlebush.png", false, 8, true);
    JLabel warpThree = assets(11546 , -622, 100, 200,  false, "images/assets/warpStone.png", false, 8, true);
    JLabel warpFour = assets(11546 , -1006, 100, 200,  false, "images/assets/warpStone.png", false, 8, true);
    JLabel warpFive = assets(11546 , -1438, 100, 200,  false, "images/assets/warpStone.png", false, 8, true);
    JLabel LittleBush3659183 = assets(12184 , -1009, 100, 100,  debugMode, "images/assets/littlebush.png", false, 8, true);
    JLabel LittleBush3050567 = assets(12184 , -1357, 100, 100,  debugMode, "images/assets/littlebush.png", false, 8, true);
    JLabel LittleBush9963417 = assets(12184 , -1741, 100, 100,  debugMode, "images/assets/littlebush.png", false, 8, true);
    JLabel LittleBush505283 = assets(12184 , -637, 100, 100,  debugMode, "images/assets/littlebush.png", false, 8, true);
    JLabel warpSix = assets(12173 , -857, 100, 200,  false, "images/assets/warpStone.png", false, 8, true);
    JLabel warpSeven = assets(12173 , -1241, 100, 200,  false, "images/assets/warpStone.png", false, 8, true);
    JLabel warpEight = assets(12173 , -1601, 100, 200,  false, "images/assets/warpStone.png", false, 8, true);
    JLabel LittleBush7556671 = assets(12836 , -1052, 100, 100,  debugMode, "images/assets/littlebush.png", false, 8, true);
    JLabel LittleBush4248156 = assets(12836 , -1400, 100, 100,  debugMode, "images/assets/littlebush.png", false, 8, true);
    JLabel LittleBush1221163 = assets(12836 , -668, 100, 100,  debugMode, "images/assets/littlebush.png", false, 8, true);
    JLabel LittleBush8010436 = assets(12836 , -296, 100, 100,  debugMode, "images/assets/littlebush.png", false, 8, true);
    JLabel warpNine = assets(12826 , -524, 100, 200,  false, "images/assets/warpStone.png", false, 8, true);
    JLabel warpTen = assets(12826 , -932, 100, 200,  false, "images/assets/warpStone.png", false, 8, true);
    JLabel warpEleven = assets(12826 , -1280, 100, 200,  false, "images/assets/warpStone.png", false, 8, true);
    JLabel LittleBush1556035 = assets(13366 , -1586, 100, 100,  debugMode, "images/assets/littlebush.png", false, 8, true);
    JLabel LittleBush5714479 = assets(13366 , -1226, 100, 100,  debugMode, "images/assets/littlebush.png", false, 8, true);
    JLabel LittleBush961166 = assets(13366 , -854, 100, 100,  debugMode, "images/assets/littlebush.png", false, 8, true);
    JLabel LittleBush2378646 = assets(13366 , -482, 100, 100,  debugMode, "images/assets/littlebush.png", false, 8, true);
    JLabel warpTwelve = assets(13356 , -722, 100, 200,  false, "images/assets/warpStone.png", false, 8, true);
    JLabel warpThirteen = assets(13356 , -1094, 100, 200,  false, "images/assets/warpStone.png", false, 8, true);
    JLabel warpFourteen = assets(13356 , -1466, 100, 200,  false, "images/assets/warpStone.png", false, 8, true);
    JLabel LittleBush8287394 = assets(13966 , -1028, 100, 100,  debugMode, "images/assets/littlebush.png", false, 8, true);
    JLabel LittleBush2024410 = assets(13966 , -560, 100, 100,  debugMode, "images/assets/littlebush.png", false, 8, true);
    JLabel LittleBush3074532 = assets(13966 , -1388, 100, 100,  debugMode, "images/assets/littlebush.png", false, 8, true);
    JLabel warpFifteen = assets(13956 , -1280, 100, 200,  false, "images/assets/warpStone.png", false, 8, true);
    JLabel warpSixteen = assets(13956 , -848, 100, 200,  false, "images/assets/warpStone.png", false, 8, true);
    JLabel Tree7750002 = assets(11015 , -1690, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree7407216 = assets(11255 , -1726, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree8980819 = assets(11675 , -1834, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree8703687 = assets(11903 , -1870, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree3678105 = assets(12335 , -1906, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree422279 = assets(12599 , -1714, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree7713566 = assets(12947 , -1714, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree2765583 = assets(13163 , -1882, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree1945038 = assets(13559 , -1774, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree186322 = assets(13867 , -1701, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree1759215 = assets(14174 , -1619, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree8930861 = assets(13803 , -482, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree6533175 = assets(13506 , -413, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree1579725 = assets(13242 , -353, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree7035561 = assets(12987 , -421, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree9230897 = assets(12609 , -331, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree6804403 = assets(12363 , -475, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree4852611 = assets(12060 , -495, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree1130434 = assets(11847 , -390, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree3735295 = assets(11595 , -318, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree1249126 = assets(11295 , -386, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree8594725 = assets(11046 , -425, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree4914318 = assets(10617 , -404, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree73634 = assets(10233 , -1212, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree9810489 = assets(9897 , -1068, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree5666911 = assets(10465 , -1600, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree6784751 = assets(9481 , -1060, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree6622075 = assets(10157 , -502, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree3075148 = assets(9467 , -702, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree8464097 = assets(11487 , -2103, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree5416818 = assets(11931 , -2247, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree4440599 = assets(12763 , -2065, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree1866024 = assets(13191 , -2337, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree5514514 = assets(13691 , -2031, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree3544453 = assets(14519 , -1815, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree6337614 = assets(14533 , -1278, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree1329190 = assets(14483 , -588, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree4867098 = assets(15147 , -772, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);
    JLabel Tree2260184 = assets(15307 , -1244, 200, 300,  debugMode, "images/assets/tree.png", false, 8, true);

    JLabel WorldBarrier1 = assets(19200 , -4800, 2160, 600,  debugMode, "", false, 8, true);
    JLabel worldBarrier2 = assets(21660 , -4800, 2260, 600,  debugMode, "", false, 8, true);
    JLabel worldBarrier3 = assets(19200 , -4000, 1200, 1400,  debugMode, "", false, 8, true);
    JLabel worldBarrier4 = assets(18000 , -2500, 500, 400,  debugMode, "", false, 8, true);
    JLabel worldBarrier5 = assets(19200 , -1900, 700, 1200,  debugMode, "", false, 8, true);
    JLabel worldBarrier6 = assets(20000 , -820, 1400, 600,  debugMode, "", false, 8, true);
    JLabel worldBarrier7 = assets(21300 , -920, 1600, 600,  debugMode, "", false, 8, true);
    JLabel worldBarrier8 = assets(22900 , -2000, 1400, 1200,  debugMode, "", false, 8, true);
    JLabel worldBarrier9 = assets(23800 , -2600, 300, 600,  debugMode, "", false, 8, true);
    JLabel worldBarrier10 = assets(23600 , -3900, 400, 1300,  debugMode, "", false, 8, true);
    JLabel worldBarrier11 = assets(23200 , -4100, 400, 200,  debugMode, "", false, 8, true);

// sword received text
    JLabel stoneSword = GUIassets( 80, 600, 896, 224, false, "images/assets/SwordText/CharacterSword1.png", false, 0, false);
    JLabel ironSword = GUIassets( 130, 600, 896, 224, false, "images/assets/SwordText/CharacterSword2.png", false, 0, false);
    JLabel goldSword = GUIassets( 130, 600, 896, 224, false, "images/assets/SwordText/CharacterSword3.png", false, 0, false);
    JLabel rubySword = GUIassets( 130, 600, 896, 224, false, "images/assets/SwordText/CharacterSword4.png", false, 0, false);
    JLabel emeraldSword = GUIassets( 130, 600, 896, 224, false, "images/assets/SwordText/CharacterSword5.png", false, 0, false);
    JLabel diamondSword = GUIassets( 130, 600, 896, 224, false, "images/assets/SwordText/CharacterSword6.png", false, 0, false);



//all the images for the up attacks (different sword types)
    JLabel[] upAttack = new JLabel[]{

            GUIassets(100, 100, 100, 100, false, "images/equipment/wood/up_wood.png", false, 1, false),
            GUIassets(100, 100, 100, 100, false, "images/equipment/stone/up_stone.png", false, 1, false),
            GUIassets(100, 100, 100, 100, false, "images/equipment/iron/up_iron.png", false, 1, false),
            GUIassets(100, 100, 100, 100, false, "images/equipment/gold/up_gold.png", false, 1, false),
            GUIassets(100, 100, 100, 100, false, "images/equipment/ruby/up_ruby.png", false, 1, false),
            GUIassets(100, 100, 100, 100, false, "images/equipment/emerald/up_emerald.png", false, 1, false),
            GUIassets(100, 100, 100, 100, false, "images/equipment/diamond/up_diamond.png", false, 1, false)

    };
    //all the images for the left attacks (different sword types)
    JLabel[] leftAttack = new JLabel[]{
            GUIassets(100, 100, 100, 100, false, "images/equipment/wood/left_wood.png", false, 1, false),
            GUIassets(100, 100, 100, 100, false, "images/equipment/stone/stone_left.png", false, 1, false),
            GUIassets(100, 100, 100, 100, false, "images/equipment/iron/left_iron.png", false, 1, false),
            GUIassets(100, 100, 100, 100, false, "images/equipment/gold/left_gold.png", false, 1, false),
            GUIassets(100, 100, 100, 100, false, "images/equipment/ruby/left_ruby.png", false, 1, false),
            GUIassets(100, 100, 100, 100, false, "images/equipment/emerald/left_emerald.png", false, 1, false),
            GUIassets(100, 100, 100, 100, false, "images/equipment/diamond/left_diamond.png", false, 1, false)


    };
    //all the images for the down attacks (different sword types)
    JLabel[] downAttack = new JLabel[]{


            GUIassets(100, 100, 100, 100, false, "images/equipment/wood/down_wood.png", false, 1, false),
            GUIassets(100, 100, 100, 100, false, "images/equipment/stone/down_stone.png", false, 1, false),
            GUIassets(100, 100, 100, 100, false, "images/equipment/iron/down_iron.png", false, 1, false),
            GUIassets(100, 100, 100, 100, false, "images/equipment/gold/down_gold.png", false, 1, false),
            GUIassets(100, 100, 100, 100, false, "images/equipment/ruby/down_ruby.png", false, 1, false),
            GUIassets(100, 100, 100, 100, false, "images/equipment/emerald/down_emerald.png", false, 1, false),
            GUIassets(100, 100, 100, 100, false, "images/equipment/diamond/down_diamond.png", false, 1, false)

    };
    //all the images for the right attacks (different sword types)
    JLabel[] rightAttack = new JLabel[]{
            GUIassets(100, 100, 100, 100, false, "images/equipment/wood/right_wood.png", false, 1, false),
            GUIassets(100, 100, 100, 100, false, "images/equipment/stone/right_stone.png", false, 1, false),
            GUIassets(100, 100, 100, 100, false, "images/equipment/iron/right_iron.png", false, 1, false),
            GUIassets(100, 100, 100, 100, false, "images/equipment/gold/right_gold.png", false, 1, false),
            GUIassets(100, 100, 100, 100, false, "images/equipment/ruby/right_ruby.png", false, 1, false),
            GUIassets(100, 100, 100, 100, false, "images/equipment/emerald/right_emerald.png", false, 1, false),
            GUIassets(100, 100, 100, 100, false, "images/equipment/diamond/right_diamond.png", false, 1, false)

    };



    public static void Sequencer(String input, int numRepeat, float volume) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        File file = new File(input);
        AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
        clip = AudioSystem.getClip();
        clip.open(audioStream);


        //float volume = 1f; // adjust volume here
        volumeChange(volume);

        clip.start();
        clip.loop(numRepeat);

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
            Sequencer("music/start.wav", 100, 1f); // Play the clip when the program starts
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException j) {
            j.printStackTrace(); // Handle exceptions
        }


        backgroundPanel.setLayout(null);

        //Background Tile System - place files named (position)x(position)y.png in the tiles folder
        int tileSize = 1600; // changes how far apart the tiles are placed
        File tileDir = new File("images/background/tiles");
       // this was AI but it essentially reads files and then gives me back the x and y values
        File[] tileFiles = tileDir.listFiles((dir, name) -> name.matches("\\d+x\\dy\\.png"));
        if (tileFiles != null) {
            for (File tileFile : tileFiles) {
                String fileName = tileFile.getName();
                // splits the file name into the x and the y value and adds it to a list
                String[] parts = fileName.replace(".png", "").split("x|y");
                int tileX = Integer.parseInt(parts[0]);
                int tileY = Integer.parseInt(parts[1]);
// (end of AI assistance) Loads and scales the image to the correct size: tileSize by tileSize
                ImageIcon tileIcon = new ImageIcon(new ImageIcon(tileFile.getPath()).getImage().getScaledInstance(tileSize, tileSize, Image.SCALE_DEFAULT));
// Creates the JLabel (which lets us see the image), and sets it to the specific image and the specific scaling, position, etc
                JLabel tileLabel = new JLabel(tileIcon);
                tileLabel.setBounds(0, 0, tileSize, tileSize); 
                tileLabel.setOpaque(false);
                backgroundPanel.setComponentZOrder(tileLabel, 10);
                backgroundPanel.add(tileLabel);

//Sets the world position to tileX/Y (file name of image) * size of tile, then adds the tile to the background.
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
        loadAndScaleTortlesImages();

        Point playerPoint = new Point(0, 0);
        Point chestPoint = new Point(1000, 2000);
        Point rockpoint = new Point(300, 600);

        player = new JLabel(playerImages.get("downStanding"));
        player.setBounds(super.getWidth() / 2 - 50, super.getHeight() / 2 - 100, 100, 188);
        player.setOpaque(false);
        backgroundPanel.setComponentZOrder(player, 2);

        x = player.getX();
        y = player.getY();
        playerWorldPos.setLocation(2360, -678);
        CameraInstance = new Camera(super.getWidth(), super.getHeight(), player.getX(), player.getY());

        for (int i = 0; i < 7; i++) {

            //sets the swords to a location based on the player's location (I had to do this separately from the initialization as we didn't have player locations yet)
        rightAttack[i].setBounds(player.getX() + 100, player.getY() + 50, 100, 100);
        leftAttack[i].setBounds(player.getX() - 75,player.getY() + 50, 100, 100);
        upAttack[i].setBounds(player.getX(), player.getY() - 100, 100, 100);
        downAttack[i].setBounds(player.getX(), player.getY() + 200, 100, 100);

        }





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
        // Essentially gets all the files for each of the different directions and puts them in a large list
        String[] imageNames = {"downStanding", "downFore", "downBack", "upStanding", "upFore", "upBack", "rightStanding", "rightFore", "rightBack", "leftStanding", "leftFore", "leftBack"};
        // These are the end parts of all the file names
        for (String name : imageNames) {
            ImageIcon icon = new ImageIcon("images/player/" + name + ".png");
            // These are the front of the file names
            Image image = icon.getImage().getScaledInstance(100, 188, Image.SCALE_DEFAULT);
            playerImages.put(name, new ImageIcon(image));
            // This makes sure that the images are formatted correctly
        }
    }

    private void loadAndScaleTortlesImages() {
        String[] imageNames = {"downStanding", "downFore", "downBack", "upStanding", "upFore", "upBack", "rightStanding", "rightFore", "rightBack", "leftStanding", "leftFore", "leftBack"};
        for (String name : imageNames) {
            ImageIcon icon = new ImageIcon("images/mob/tortles/" + name + ".png");
            Image image = icon.getImage().getScaledInstance(200, 376, Image.SCALE_DEFAULT);
            tortlesImages.put(name, new ImageIcon(image));
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

//Main game loop: All game code is run in this method (called once at start)
    private void gameLoop() {
        //Creates a timer system for the FPS system (using nanoTime to be precise)
        long previousTime = System.nanoTime();
        double placeholder = 0;
        long currentTime;
        double timePerFrame = 1_000_000_000.0 / FPS;


//All game code is run in this loop. Whenever the time per frame is reached (currently 1/60th of a second), the code is run.
        while(true) {
            //Counts how much time has passed
            currentTime = System.nanoTime();
            placeholder += (currentTime - previousTime) / timePerFrame;
            previousTime = currentTime;
            //If one frame has passed it runs code
            if (placeholder >= 1) {
                interacting();


                //player.setBounds(super.getWidth() / 2 - 50, super.getHeight() / 2 - 100, player.getWidth(), player.getHeight());


                //uncomment this when we want to submit
                // coordinates.setText((int) playerWorldPos.getX() - 2360 + " " + (int) ((playerWorldPos.getY() + 678) * -1));

                iceSpikeOne.repaint();
                iceSpikeTwo.repaint();
                //comment this when we want to submit
                coordinates.setText((int) playerWorldPos.getX() + " " + (int) playerWorldPos.getY());


                //System.out.println(ghostWorldPos);

                //For the dialogue system: runs when current active dialogue
                if (dialogueActive) {


                    //Makes sure E has to be unpressed and pressed again to run
                    if (ePressed) {
                        ePressed = false;

                        //If the NPC being interacted with is the grandma
                        if (NPCNumber == 1) {
                        // Hide current image
                        if (grandmaDialogueIndex < grandmaDialogueImages.length) {
                            grandmaDialogueImages[grandmaDialogueIndex].setVisible(false);
                        }

                        grandmaDialogueIndex++;

                        // Show next image or end dialogue
                        if (grandmaDialogueIndex < grandmaDialogueImages.length) {
                            grandmaDialogueImages[grandmaDialogueIndex].setVisible(true);


                        } else {
                            // End of dialogue
                            dialogueActive = false;
                            NPCInteracted[NPCNumber - 1] = true;
                            NPCBackground.setVisible(false);


                        }
                        //If the NPC being interacted with is the wizard
                    } else if (NPCNumber == 2) {
                            // Hide current image

                            if (wizardDialogueIndex < wizardDialogueImages.length) {
                                wizardDialogueImages[wizardDialogueIndex].setVisible(false);
                            }

                            wizardDialogueIndex++;

                            // Show next image or end dialogue
                            if (wizardDialogueIndex < wizardDialogueImages.length) {
                                wizardDialogueImages[wizardDialogueIndex].setVisible(true);


                            } else {
                                // End of dialogue
                                dialogueActive = false;
                                NPCInteracted[NPCNumber - 1] = true;
                                NPCBackground.setVisible(false);


                            }
                        }
                        //If the NPC being interacted with is the villager (carpenter)
                        else if (NPCNumber == 3) {
                            // Hide current image
                            if (villagerDialogueIndex < villagerDialogueImages.length) {
                                villagerDialogueImages[villagerDialogueIndex].setVisible(false);
                            }

                            villagerDialogueIndex++;

                            // Show next image or end dialogue
                            if (villagerDialogueIndex < villagerDialogueImages.length) {
                                villagerDialogueImages[villagerDialogueIndex].setVisible(true);


                            } else {
                                // End of dialogue
                                dialogueActive = false;
                                NPCInteracted[NPCNumber - 1] = true;
                                NPCBackground.setVisible(false);


                            }
                        }
                    }
                }



                if (!GUIOpen) {
                    backgroundPanel.setComponentZOrder(player, 0);
                }



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

                    if(mobLabel == tortles) {
                        mobPoints = TortlesMovement((int) mobPoints.getX(), (int) mobPoints.getY(), mobSpeed, mobFollowDistance, mobSpawnpoint);
                    } else {
                        mobPoints = mobMovement((int) mobPoints.getX(), (int) mobPoints.getY(), mobSpeed, mobFollowDistance, mobSpawnpoint);
                    }
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

                placeholder--; //Resets counter back once frame complete
                

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

                    if(upAttack[swordNumber].getBounds().intersects(entry.getValue().getBounds())) {

                        UUID mobID = entry.getKey();
                        JLabel mobLabel = entry.getValue();


                        int mobHealth = MobHealth.get(mobID);

                        mobHealth -= playerDamage;

                        MobHealth.put(mobID, mobHealth);

                        if (mobHealth <= 0) {
                            mobRemove(mobID);
                        } else {

                        }


                    }
                }

            }
            case "down" -> {

                for (Map.Entry<UUID, JLabel> entry : mob.entrySet()) { // code similar to geek by geeks post - https://www.geeksforgeeks.org/how-to-iterate-hashmap-in-java/

                    if(downAttack[swordNumber].getBounds().intersects(entry.getValue().getBounds())) {

                        UUID mobID = entry.getKey();
                        JLabel mobLabel = entry.getValue();


                        int mobHealth = MobHealth.get(mobID);

                        mobHealth -= playerDamage;

                        MobHealth.put(mobID, mobHealth);

                        if (mobHealth <= 0) {
                            mobRemove(mobID);
                        } else {

                        }


                    }
                }



            }

            case "left" -> {

                for (Map.Entry<UUID, JLabel> entry : mob.entrySet()) { // code similar to geek by geeks post - https://www.geeksforgeeks.org/how-to-iterate-hashmap-in-java/

                    if (leftAttack[swordNumber].getBounds().intersects(entry.getValue().getBounds())) {

                        UUID mobID = entry.getKey();
                        JLabel mobLabel = entry.getValue();


                        int mobHealth = MobHealth.get(mobID);

                        mobHealth -= playerDamage;

                        MobHealth.put(mobID, mobHealth);

                        if (mobHealth <= 0) {
                            mobRemove(mobID);
                        } else {
                           // System.out.println("Mob Health: " + mobHealth);
                        }


                    }
                }

            }

            case "right" -> {

                for (Map.Entry<UUID, JLabel> entry : mob.entrySet()) { // code similar to geek by geeks post - https://www.geeksforgeeks.org/how-to-iterate-hashmap-in-java/

                    if (rightAttack[swordNumber].getBounds().intersects(entry.getValue().getBounds())) {

                        UUID mobID = entry.getKey();
                        JLabel mobLabel = entry.getValue();


                        int mobHealth = MobHealth.get(mobID);

                        mobHealth -= playerDamage;

                        MobHealth.put(mobID, mobHealth);

                        if (mobHealth <= 0) {
                            mobRemove(mobID);
                        } else {
                           // System.out.println("Mob Health: " + mobHealth);
                        }


                    }
                }

            }

        }



    }


    public Point mobMovement(int x, int y, int mobSpeed, int followDistance, Point spawnPoint) {
        distance = Math.sqrt(Math.pow(((playerWorldPos.x - 40) - x), 2) + Math.pow(((playerWorldPos.y-50) - y), 2));




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


    public Point TortlesMovement(int x, int y, int mobSpeed, int followDistance, Point spawnPoint) {
        distance = Math.sqrt(Math.pow(((playerWorldPos.x - 40) - x), 2) + Math.pow(((playerWorldPos.y-50) - y), 2));



        double distanceX = playerWorldPos.x - x;
        double distanceY = playerWorldPos.y - y;

        if (distance <= followDistance && distance >= 100) {

            distanceX = playerWorldPos.x - x;
            distanceY = playerWorldPos.y - y;

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





            } else {

                if (spawnPoint.y > y) {
                    y += mobSpeed;
                } else {
                    y -= mobSpeed;
                }


            }
        }

      //  if(distanceX < 100 || distanceY < 100) {

         //   moveDirection = 0; // Not moving

    //    } else {

            if (Math.abs(distanceX) >= Math.abs(distanceY)) {
                if (distanceX > 0) {
                    moveDirection = "right"; // Moving right
                } else {
                    moveDirection = "left"; // Moving left
                }
            } else {
                if (distanceY > 0) {
                    moveDirection = "down"; // Moving down
                } else {
                    moveDirection = "up"; // Moving up
                }
            }
      //  }


        String imageTortlesName;
        if (tortlesMoveDirection == 1) {
             imageTortlesName = moveDirection + "Standing";
        } else if (tortlesMoveDirection == 2) {
            imageTortlesName = moveDirection + "Fore";
        } else if (tortlesMoveDirection == 3) {
            imageTortlesName = moveDirection + "Standing";
        } else {
            imageTortlesName = moveDirection + "Back";
        }


        tortles.setIcon(tortlesImages.get(imageTortlesName));
        tortlesMoveTime++;

        if (distance > 100 || distance < -100) {
            if (tortlesMoveTime >= (FPS / 5)) {
                tortlesMoveDirection = (tortlesMoveDirection % 4) + 1;
                tortlesMoveTime = 0;
            }
        } else if (tortlesMoveTime >= (FPS / 5)) {
            tortlesMoveDirection = 1;
            tortlesMoveTime = 0;
        }


        tortles.repaint();



        return new Point(x, y);
    }

    public void tortlesAttack() {

        distance = Math.sqrt(Math.pow(((playerWorldPos.x - 40) - tortles.getX()), 2) + Math.pow(((playerWorldPos.y - 50) - tortles.getY()), 2));

        if(distance <= 800 ) {

            playerPastPositions.add(playerWorldPos.getLocation());

            playerPastPositions.size();


            slope = (double) (playerWorldPos.y - tortles.getY()) / (playerWorldPos.x - player.getX());

            b = playerWorldPos.y - slope * playerWorldPos.x;


            JLabel iceball = assets((int) playerWorldPos.getX(), (int) playerWorldPos.getY(), 50, 50, false, "images/mob/tortles/iceball.png", false, 0, true);






        }


    }



    public Point ProjectTile(int x, int y, int projectileSpeed, int followDistance, Point playerPoint) {
        distance = Math.sqrt(Math.pow(((playerPoint.x - 40) - x), 2) + Math.pow(((playerPoint.y - 50) - y), 2));

        //step -= mobSpeed;

        if (distance <= followDistance) {

            double distanceX = playerPoint.x - x;
            double distanceY = playerPoint.y - y;

            slope = (double) (playerPoint.y - y) / (playerPoint.x - x);

            b = playerPoint.y - slope * playerPoint.x;


            if (distanceX != 0) {

                double slope = distanceY / distanceX;
                double b = playerPoint.y - slope * playerPoint.x;


                if(Math.abs(distanceX) > Math.abs(distanceY)) {
                    if (playerPoint.x > x) {
                        x += projectileSpeed;

                    } else {
                        x -= projectileSpeed;
                    }

                    y = (int) (slope * x + b);
                } else {

                    if (playerPoint.y > y) {
                        y += projectileSpeed;
                    } else {
                        y -= projectileSpeed;
                    }

                    x = (int) ((y - b) / slope);

                }


            } else {

                if (playerPoint.y > y) {
                    y += projectileSpeed;
                } else {
                    y -= projectileSpeed;
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

    public JLabel mobCreation(int x, int y, int width, int height, String filePath, int zOrder, int health, double damage, int range, int speed, int followDistance, int attackCooldown, boolean isTortles) {
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

//If armor is found in a chest, it increases health and maximum health by that amount
    public void armorIncrease(double armorInc) {
        maximumHealth += armorInc;
        healthChange(armorInc);
    }

//The health increase system: Increases the health by the amount given
    public void healthChange(double healthChange) {

        if (!GUIOpen) {
            //Increases current health variable
            currentHealth += healthChange;
            //Ensures health doesn't go over maximum
            if (currentHealth > maximumHealth) {
                currentHealth = maximumHealth;
            }

            //If player's health goes below 0, they are teleported to the start and health is reset
            if (currentHealth <= 0) {
                playerWorldPos.setLocation(SpawnPoint);
                healthChange(maximumHealth);
            }
            //It refreshed the shown hearts: Maximum health is drawn first (the blank hearts)
            for (int i = 1; i <= maximumHealth; i++) {
                //For the maximum health amount a new blank heart is created and displaced 60 units to the right
                JLabel emptyHeart = GUIassets(10 + (60 * (i - 1)), 10, 50, 50, false, "images/GUI/emptyHeart.png", false, 1, true);
                backgroundPanel.setComponentZOrder(emptyHeart, 1);
                emptyHeart.repaint();
            }
            //It refreshed the shown hearts: Current health is drawn second (the full hearts)
            for (int i = 1; i <= currentHealth; i++) {
                //For the current health amount a new full heart is created and displaced 60 units to the right
                JLabel fullHeart = GUIassets(10 + (60 * (i - 1)), 10, 50, 50, false, "images/GUI/fullHeart.png", false, 0, true);
                backgroundPanel.setComponentZOrder(fullHeart, 0);
                fullHeart.repaint();
            }
            //It refreshed the shown hearts: Half health is drawn last (the half hearts)
            if (currentHealth % 1.0 != 0) {
                //If the health is a decimal (we only really have 0.5 currently), it places its position relative to current health
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
                    try {
                        Sequencer("music/swipe.wav", 0, 0.7f);
                    } catch (UnsupportedAudioFileException | IOException | LineUnavailableException j) {
                        j.printStackTrace(); // Handle exceptions
                    }
                    healthChange(-mobDamage);
                    TimeMobAttacked.put(mobID, LocalDateTime.now());
                    timeSinceAttack = LocalDateTime.now();
                //    System.out.println("You were attacked by a mob");
                    TimeMobAttacked.put(mobID, LocalDateTime.now());
                    MobAttackCurrentCoolDown.put(mobID, 0L);
                }
            }
            else {
               TimeMobAttacked.put(mobID, LocalDateTime.now());
                MobAttackCurrentCoolDown.put(mobID, 0L);
            }
        }
    }



    public void interacting() {
//All the interacting code (methods) is put in here for ease of use and run in the game loop
        chest();
        NPCInteraction();
        portalInteraction();
        respawnPoint();

        TextDisappearing();


        if(player.getBounds().intersects(warp.getBounds())) {
            playerWorldPos.setLocation(-50, 0);

        }




    }

//Main NPC interaction method that is called each frame
    public void NPCInteraction () {

        //If the player is touching the grandma and hasn't been interacted with, the code runs
        if (player.getBounds().intersects(NPC.getBounds()) && !NPCInteracted[0]) {
            press.setVisible(true);

            if (ePressed && !dialogueActive) {
                NPCInteracted[0] = true; //Ensures they can't be interacted with twice
                NPCNumber = 1; //Sets the current NPC being interacted with
                press.setVisible(false); //Hides "Press E"
                startDialogue(); // Start dialogue images
                ePressed = false; // Prevent skipping first image
            }
            //Else if the player is touching the wizard and hasn't been interacted with, the code runs
        } else if (player.getBounds().intersects(NPC2.getBounds()) && !NPCInteracted[1]) {
            press.setVisible(true);

            if (ePressed && !dialogueActive) {
                NPCInteracted[1] = true; //Ensures they can't be interacted with twice
                NPCNumber = 2;  //Sets the current NPC being interacted with
                press.setVisible(false);  //Hides "Press E"
                startDialogue(); // Start dialogue images
                ePressed = false; // Prevent skipping first image
            }
            //Else if the player is touching the villager and hasn't been interacted with, the code runs
        } else if (player.getBounds().intersects(NPC3.getBounds()) && !NPCInteracted[2]) {
            press.setVisible(true);

            if (ePressed && !dialogueActive) {
                NPCInteracted[2] = true; //Ensures they can't be interacted with twice
                NPCNumber = 3; //Sets the current NPC being interacted with
                press.setVisible(false); //Hides "Press E"
                startDialogue(); // Start dialogue images
                ePressed = false; // Prevent skipping first image
            }
        } else {
            press.setVisible(false); //If no NPC is being touched (or are already interacted with) it hides "Press E"
        }

    }


    public void portalInteraction() {

        //start 10425 -787

        if (player.getBounds().intersects(warpOne.getBounds())) {

            playerWorldPos.setLocation(10425, -787);
            try {
                clip.stop();
                Sequencer("music/warp.wav", 0, 0.6f);
            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
                e.printStackTrace();
            }
        }  else if (player.getBounds().intersects(warpTwo.getBounds())) {
            playerWorldPos.setLocation(11265, -973);
            try {
                clip.stop();
                Sequencer("music/warp.wav", 0, 0.6f);
            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
                e.printStackTrace();
            }
        } else if (player.getBounds().intersects(warpThree.getBounds())) {
            playerWorldPos.setLocation(11908, -1169);
            try {
                clip.stop();
                Sequencer("music/warp.wav", 0, 0.6f);
            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
                e.printStackTrace();
            }
        } else if (player.getBounds().intersects(warpFour.getBounds())) {
            playerWorldPos.setLocation(10425, -787);
            try {
                clip.stop();
                Sequencer("music/warp.wav", 0, 0.6f);
            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
                e.printStackTrace();
            }
        } else if (player.getBounds().intersects(warpFive.getBounds())) {
            playerWorldPos.setLocation(10425, -787);
            try {
                clip.stop();
                Sequencer("music/warp.wav", 0, 0.6f);
            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
                e.printStackTrace();
            }
        } else if (player.getBounds().intersects(warpSix.getBounds())) {
            playerWorldPos.setLocation(12544, -857);
            try {
                clip.stop();
                Sequencer("music/warp.wav", 0, 0.6f);
            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
                e.printStackTrace();
            }
        } else if (player.getBounds().intersects(warpSeven.getBounds())) {
            playerWorldPos.setLocation(10425, -787);
            try {
                clip.stop();
                Sequencer("music/warp.wav", 0, 0.6f);
            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
                e.printStackTrace();
            }
        } else if (player.getBounds().intersects(warpEight.getBounds())) {
            playerWorldPos.setLocation(10425, -787);
            try {
                clip.stop();
                Sequencer("music/warp.wav", 0, 0.6f);
            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
                e.printStackTrace();
            }
        } else if (player.getBounds().intersects(warpNine.getBounds())) {
            playerWorldPos.setLocation(10425, -787);
            try {
                clip.stop();
                Sequencer("music/warp.wav", 0, 0.6f);
            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
                e.printStackTrace();
            }

        } else if (player.getBounds().intersects(warpTen.getBounds())) {
            playerWorldPos.setLocation(10425, -787);
            try {
                clip.stop();
                Sequencer("music/warp.wav", 0, 0.6f);
            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
                e.printStackTrace();
            }
        } else if (player.getBounds().intersects(warpEleven.getBounds())) {
            playerWorldPos.setLocation(13132, -1073);
            try {
                clip.stop();
                Sequencer("music/warp.wav", 0, 0.6f);
            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
                e.printStackTrace();
            }
        } else if (player.getBounds().intersects(warpTwelve.getBounds())) {
            playerWorldPos.setLocation(10425, -787);
            try {
                clip.stop();
                Sequencer("music/warp.wav", 0, 0.6f);
            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
                e.printStackTrace();
            }
        } else if (player.getBounds().intersects(warpThirteen.getBounds())) {
            playerWorldPos.setLocation(13721, -1044);
            try {
                clip.stop();
                Sequencer("music/warp.wav", 0, 0.6f);
            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
                e.printStackTrace();
            }
        } else if (player.getBounds().intersects(warpFourteen.getBounds())) {
            playerWorldPos.setLocation(10425, -787);
            try {
                clip.stop();
                Sequencer("music/warp.wav", 0, 0.6f);
            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
                e.printStackTrace();
            }
        } else if (player.getBounds().intersects(warpFifteen.getBounds())) {
            playerWorldPos.setLocation(10425, -787);
            try {
                clip.stop();
                Sequencer("music/warp.wav", 0, 0.6f);
            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
                e.printStackTrace();
            }
        } else if (player.getBounds().intersects(warpSixteen.getBounds())) {
            playerWorldPos.setLocation(14555, -789);
            try {
                clip.stop();
                Sequencer("music/warp.wav", 0, 0.6f);
            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
                e.printStackTrace();
            }

        } else if(player.getBounds().intersects(warpArena.getBounds())) {
            playerWorldPos.setLocation(21546, -4278);

            try {
                clip.stop();
                Sequencer("music/warp.wav", 0, 0.6f);
            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
                e.printStackTrace();
            }

            try {
                clip.stop();
                Sequencer("music/tortlesFight.wav", 100, 0.6f);
            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
                e.printStackTrace();
            }




        } else if(player.getBounds().intersects(warpBack.getBounds())) {
            playerWorldPos.setLocation(2861, -7500);

            try {
                clip.stop();
                Sequencer("music/korok.wav", 100, 0.5f);
            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
                e.printStackTrace();
            }

            try {
                clip.stop();
                Sequencer("music/warp.wav", 0, 0.6f);
            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
                e.printStackTrace();
            }


        }

    }


    public void respawnPoint() {

        boolean interacting = false;
        pressrespawn.setVisible(false);
        respawnSet.setVisible(false);

        for(int i = 0; i < respawnPoints.length; i++) {

            if (player.getBounds().intersects((respawnPoints[i]).getBounds()) && ePressed) {

                interacting = true;
                pressrespawn.setVisible(true);

                switch(i) {

                    case 0: {

                        SpawnPoint.setLocation(2354, -672);

                        interacting = true;
                        respawnSet.setVisible(true);

                        break;
                    }
                    case 1: {

                        SpawnPoint.setLocation(2886, -7475);

                        interacting = true;
                        respawnSet.setVisible(true);
                        break;
                    }
                    case 2: {

                        SpawnPoint.setLocation(14011, -7375);
                        interacting = true;
                        respawnSet.setVisible(true);
                        break;
                    }
                    case 3: {

                        SpawnPoint.setLocation(7385, -185);
                        interacting = true;
                        respawnSet.setVisible(true);
                        break;
                    }
                    case 4: {

                        SpawnPoint.setLocation(14836, -786);
                        interacting = true;
                        respawnSet.setVisible(true);
                        break;
                    }


                }

                if(!interacting) {
                    pressrespawn.setVisible(false);
                    interacting = false;
                    respawnSet.setVisible(false);

                }




            } else if(player.getBounds().intersects((respawnPoints[i].getBounds()))) {
                pressrespawn.setVisible(true);

            } else if(respawnSet.isVisible() && interacting == false) {
                pressrespawn.setVisible(false);
            }







        }

    }


//Chest interaction system
    public void chest() {

        //checks if the player is touching each chest individually for the number of chests in the chestImages array
        for (int i = 0; i < chestImages.length; i++) {

// They must be touching, not having looted, and ePressed for that specific chest
            if (player.getBounds().intersects((chestImages[i]).getBounds()) && !chestLooted[i] && ePressed) {

                switch (i) {

                    //Different thing for each chest
                    case 0 : {
                        pressChest.setVisible(false); //Hides "Press E"
                        gotApple.setVisible(true); //In this case is shows "you got an apple"
                        textDisappear = true; //Makes the textDisappear after 3 seconds
                        messageDisappearNumber = 0; //For the textDisapper method
                        healthChange(3); //Increases health by 3
                        chestLooted[0] = true; //Chest can not be looted anymore

                        break;
                    }
                    case 1 : {
                        //This one receives a sword and increases damage
                        pressChest.setVisible(false);
                        textDisappear = true;
                        swordNumber = 1;
                        messageDisappearNumber = 1;
                        stoneSword.setVisible(true);
                        playerDamage = 4;
                        chestLooted[1] = true;

                        break;
                    }
                    case 2 : {
                        //This one receives a sword and increases damage
                        pressChest.setVisible(false);
                        ironSword.setVisible(true);
                        textDisappear = true;
                        swordNumber = 2;
                        messageDisappearNumber = 2;
                        playerDamage = 5;
                        chestLooted[2] = true;

                        break;
                    }
                    case 3 : {
                        //This one receives a sword and increases damage
                        pressChest.setVisible(false);
                        goldSword.setVisible(true);
                        textDisappear = true;
                        swordNumber = 3;
                        messageDisappearNumber = 3;
                        playerDamage = 6;
                        chestLooted[3] = true;

                        break;
                    }
                    case 4 : {
                        //This one receives a sword and increases damage
                        pressChest.setVisible(false);
                        rubySword.setVisible(true);
                        textDisappear = true;
                        swordNumber = 4;
                        messageDisappearNumber = 4;
                        playerDamage = 7;
                        chestLooted[4] = true;

                        break;
                    }
                    case 5 : {
                        //This one receives a sword and increases damage
                        pressChest.setVisible(false);
                        emeraldSword.setVisible(true);
                        textDisappear = true;
                        swordNumber = 5;
                        messageDisappearNumber = 5;
                        playerDamage = 8;
                        chestLooted[5] = true;

                        break;
                    }
                    case 6 : {
                        //This one receives a sword and increases damage
                        pressChest.setVisible(false);
                        diamondSword.setVisible(true);
                        textDisappear = true;
                        swordNumber = 6;
                        messageDisappearNumber = 6;
                        playerDamage = 10;
                        chestLooted[6] = true;

                        break;
                    }
                    case 7 : {
                        //This one gets an apple and heals
                        pressChest.setVisible(false);
                        gotApple.setVisible(true);
                        textDisappear = true;
                        messageDisappearNumber = 7;
                        healthChange(3);
                        chestLooted[7] = true;

                        break;
                    }
                    case 8 : {
                        //This one gets an apple and heals
                        pressChest.setVisible(false);
                        gotApple.setVisible(true);
                        textDisappear = true;
                        messageDisappearNumber = 8;
                        healthChange(3);
                        chestLooted[8] = true;

                        break;
                    }
                    case 9 : {
                        //This one gets an apple and heals
                        pressChest.setVisible(false);
                        gotApple.setVisible(true);
                        textDisappear = true;
                        messageDisappearNumber = 9;
                        healthChange(3);
                        chestLooted[9] = true;

                        break;
                    }
                    case 10 : {
                        //This one gets an apple and heals
                        pressChest.setVisible(false);
                        gotApple.setVisible(true);
                        textDisappear = true;
                        messageDisappearNumber = 10;
                        healthChange(3);
                        chestLooted[10] = true;

                        break;
                    }
                
                 case 11 : {
                     //This one gains armor: increasing health and max health
                        pressChest.setVisible(false);
                        armourFound.setVisible(true);
                        textDisappear = true;
                        messageDisappearNumber = 11;
                        armorIncrease(1);
                        chestLooted[11] = true;

                        break;
                    }

                 case 12 : {
                     //This one gains armor: increasing health and max health
                        pressChest.setVisible(false);
                        armourFound.setVisible(true);
                        textDisappear = true;
                        messageDisappearNumber = 12;
                        armorIncrease(1);
                        chestLooted[12] = true;

                        break;
                    }
                    case 13 : {
                        //This one gains armor: increasing health and max health
                        pressChest.setVisible(false);
                        armourFound.setVisible(true);
                        textDisappear = true;
                        messageDisappearNumber = 13;
                        armorIncrease(1);
                        chestLooted[13] = true;

                        break;
                    }
                }

            } else if (player.getBounds().intersects((chestImages[i]).getBounds()) && !chestLooted[i] && !pressChestOn[i]) {
                //If the chest hasn't been looted and is touched then it shows "PressE"
                pressChest.setVisible(true);
                pressChestOn[i] = true;
            }
            else if (!(player.getBounds().intersects((chestImages[i]).getBounds())) && !chestLooted[i] && pressChestOn[i]) {
                //If the player isn't touching the chest this "Press E" is hidden
                pressChest.setVisible(false);
                pressChestOn[i] = false;
            }


        }

    }

//Method that makes the chest recieved text disappear after 3 seconds
    public void TextDisappearing () {


        if (textDisappear) {

            //similar to animation code: waits for 3 seconds to pass
            if (textDisappearTime >= (FPS * 3)) {

                textDisappearTime = 0;

//Hides the image of whatever chest was just opened
                switch (messageDisappearNumber) {
                    case 0: {
                        gotApple.setVisible(false);
                        break;
                    }
                    case 1: {
                        stoneSword.setVisible(false);
                        break;
                    }
                    case 2: {
                        ironSword.setVisible(false);
                        break;
                    }
                    case 3: {
                        goldSword.setVisible(false);
                        break;
                    }
                    case 4: {
                        rubySword.setVisible(false);
                        break;
                    }
                    case 5: {
                        emeraldSword.setVisible(false);
                        break;
                    }
                    case 6: {
                        diamondSword.setVisible(false);
                        break;
                    }
                    case 7: {
                        gotApple.setVisible(false);
                        break;
                    }
                    case 8: {
                        gotApple.setVisible(false);
                        break;
                    }
                    case 9: {
                        gotApple.setVisible(false);
                        break;
                    }
                    case 10: {
                        gotApple.setVisible(false);
                        break;
                    }
                    case 11: {
                        armourFound.setVisible(false);
                        break;
                    }
                    case 12: {
                        armourFound.setVisible(false);
                        break;
                    }
                    case 13: {
                        armourFound.setVisible(false);
                        break;
                    }
                }
            } else {
                //If 3 seconds hasn't passed it increases the time passed
                textDisappearTime++;
            }
        }
    }


    public void attacking(String direction, boolean spacePressed) {



    if(spacePressed) {
        switch (direction) {
            case "up" -> {
                upAttack[swordNumber].setVisible(true);
                leftAttack[swordNumber].setVisible(false);
                downAttack[swordNumber].setVisible(false);
                rightAttack[swordNumber].setVisible(false);
                AttackMob("up");
            }
            case "down" -> {
                downAttack[swordNumber].setVisible(true);
                upAttack[swordNumber].setVisible(false);
                rightAttack[swordNumber].setVisible(false);
                leftAttack[swordNumber].setVisible(false);
                AttackMob("down");
            }
            case "left" ->  {
                leftAttack[swordNumber].setVisible(true);
                upAttack[swordNumber].setVisible(false);
                downAttack[swordNumber].setVisible(false);
                rightAttack[swordNumber].setVisible(false);
                AttackMob("left");
            }
            case "right" -> {
                rightAttack[swordNumber].setVisible(true);
                upAttack[swordNumber].setVisible(false);
                downAttack[swordNumber].setVisible(false);
                leftAttack[swordNumber].setVisible(false);
                AttackMob("right");
            }
            default -> {
                upAttack[swordNumber].setVisible(false);
                downAttack[swordNumber].setVisible(false);
                leftAttack[swordNumber].setVisible(false);
                rightAttack[swordNumber].setVisible(false);
            }

        }
    } else {
       upAttack[swordNumber].setVisible(false);
      downAttack[swordNumber].setVisible(false);
       leftAttack[swordNumber].setVisible(false);
      rightAttack[swordNumber].setVisible(false);
    }

    
    }


    public String AnimationCore(String[] arrayImages, int timeBetweenImages) {
        String currentImage = "";

        int maxArrayLenength = arrayImages.length - 1;

        LocalDateTime lastImageSwitchTime = LocalDateTime.now();

        Duration timeSinceLastImage = Duration.between(lastImageSwitchTime, LocalDateTime.now());

     

        for(int i = 0; i < arrayImages.length; i++) {



        }



        return currentImage;
    }



//Main dialogue method for NPC interaction
    public void startDialogue() {

//Makes dialogue active in game loop
        dialogueActive = true;

//Depending on what NPC it is currently
        switch (NPCNumber) {
            case 1 : {
                //Grandma Dialogue number is set to the first one
                grandmaDialogueIndex = 0;
                NPCBackground.setVisible(true); //Dialogue background is set as visible

//All grandma dialogue images are created
                grandmaDialogueImages = new JLabel[] {
                        GUIassets(50, 500, 900, 300, false, "images/NPC/Grandma/GrandmaNPCDialogue1.png", false, 1, false),
                        GUIassets(50, 500, 900, 300, false, "images/NPC/Grandma/GrandmaNPCDialogue2.png", false, 1, false),
                        GUIassets(50, 500, 900, 300, false, "images/NPC/Grandma/GrandmaNPCDialogue3.png", false, 1, false),
                        GUIassets(50, 500, 900, 300, false, "images/NPC/Grandma/GrandmaNPCDialogue4.png", false, 1, false),
                        GUIassets(50, 500, 900, 300, false, "images/NPC/Grandma/GrandmaNPCDialogue5.png", false, 1, false),
                        GUIassets(50, 500, 900, 300, false, "images/NPC/Grandma/GrandmaNPCDialogue6.png", false, 1, false),
                        GUIassets(50, 500, 900, 300, false, "images/NPC/Grandma/GrandmaNPCDialogue7.png", false, 1, false),
                        GUIassets(50, 500, 900, 300, false, "images/NPC/Grandma/GrandmaNPCDialogue8.png", false, 1, false),
                        GUIassets(50, 500, 900, 300, false, "images/NPC/Grandma/GrandmaNPCDialogue9.png", false, 1, false),
                        GUIassets(50, 500, 900, 300, false, "images/NPC/Grandma/GrandmaNPCDialogue10.png", false, 1, false),
                        GUIassets(50, 500, 900, 300, false, "images/NPC/Grandma/GrandmaNPCDialogue11.png", false, 1, false),
                        GUIassets(50, 500, 900, 300, false, "images/NPC/Grandma/GrandmaNPCDialogue12.png", false, 1, false)

                };

                for (JLabel label : grandmaDialogueImages) {
                    label.setVisible(false);

                } // Sets them all to not visible (not sure if its needed)

                grandmaDialogueImages[0].setVisible(true); //First one is set as visible and it continues in game loop
                break;

            }
            case 2 : {
//Wizard Dialogue number is set to the first one
                wizardDialogueIndex = 0;
                NPCBackground.setVisible(true); //Dialogue background is set as visible

//All wizard dialogue images are created
                wizardDialogueImages = new JLabel[] {
                        GUIassets(50, 500, 900, 300, false, "images/NPC/Wizard/WizardNPCDialogue1.png", false, 1, false),
                        GUIassets(50, 500, 900, 300, false, "images/NPC/Wizard/WizardNPCDialogue2.png", false, 1, false),
                        GUIassets(50, 500, 900, 300, false, "images/NPC/Wizard/WizardNPCDialogue3.png", false, 1, false)

                };

                for (JLabel label2 : wizardDialogueImages) {
                    label2.setVisible(false);

                } // Sets them all to not visible (not sure if its needed)

                wizardDialogueImages[0].setVisible(true); //First one is set as visible and it continues in game loop
                break;


            }
            case 3 : {
                villagerDialogueIndex = 0; //Villager Dialogue number is set to the first one
                NPCBackground.setVisible(true); //Dialogue background is set as visible

//All villager dialogue images are created
                villagerDialogueImages = new JLabel[] {
                        GUIassets(50, 500, 900, 300, false, "images/NPC/Maze/VillagerNPCDialogue.png", false, 1, false)

                };

                for (JLabel label3 : villagerDialogueImages) {
                    label3.setVisible(false);

                } // Sets them all to not visible (not sure if its needed)

                villagerDialogueImages[0].setVisible(true); //First one is set as visible and it continues in game loop
                break;
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
    JLabel debugChest = GUIassets(1000/2, 800/2, 150, 150,  false, "images/assets/chest.png", false, 8, false);
    JLabel debugRock = GUIassets(1000/2, 800/2, 100, 100,  false, "images/assets/rock.png", false, 8, false);
    JLabel debugwarpStone = GUIassets(1000/2, 800/2, 200, 200, false, "images/assets/warpstone.png", false, 8, false);
    JLabel debugRespawnPoint = GUIassets(1000/2, 800/2,150, 200, false, "images/assets/RespawnPoint.png", false, 8 , false);
    JLabel debugTree = GUIassets(1000/2, 800/2, 200, 300, false, "images/assets/tree.png", false, 8, false);
    JLabel debugHouse = GUIassets(1000/2, 800/2, 400, 400, false, "images/assets/houses/house.png", false, 8, false);
    JLabel debugHouseTwo = GUIassets(1000/2, 800/2, 400, 400, false, "images/assets/houses/houseTwo.png", false, 8, false);
    JLabel debugLittleBush = GUIassets(1000/2, 800/2, 100, 100, false, "images/assets/littlebush.png", false, 8, false);


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
            System.out.println("JLabel Rock" + (int) (Math.random() * (10000000 - 1) + 1) + " = assets(" + (int) playerWorldPos.getX() + " , " + (int) playerWorldPos.getY() + ", 100, 100,  debugMode, \"images/assets/rock.png\", false, 8, true);");
        } else if(pPressed && twoPressed && !placeCooldown) {
            placeCooldown = true;
            JLabel debugChest = assets((int) playerWorldPos.getX(), (int) playerWorldPos.getY(), 150, 150,  false, "images/assets/chest.png", false, 8, true);
            System.out.println("JLabel Chest" + (int) (Math.random() * (10000000 - 1) + 1) + " = assets(" + (int) playerWorldPos.getX() + " , " + (int) playerWorldPos.getY() + ", 200, 200,  debugMode, \"images/assets/chest.png\", false, 8, true);");

        } else if(pPressed && threePressed && !placeCooldown) {
            placeCooldown = true;
            JLabel debugRespawnPoint = assets((int) playerWorldPos.getX(), (int) playerWorldPos.getY(), 150, 200,  false, "images/assets/RespawnPoint.png", false, 8, true);
            System.out.println("JLabel RespawnPoint" + (int) (Math.random() * (10000000 - 1) + 1) + " = assets(" + (int) playerWorldPos.getX() + " , " + (int) playerWorldPos.getY() + ", 150, 200,  debugMode, \"images/assets/respawnPoint.png\", false, 8, false);");

        } else if(pPressed && fourPressed && !placeCooldown) {
            placeCooldown = true;
            JLabel debugwarpStone = assets((int) playerWorldPos.getX(), (int) playerWorldPos.getY(), 200, 200,  false, "images/assets/warpStone.png", false, 8, true);
            System.out.println("JLabel Stone" + (int) (Math.random() * (10000000 - 1) + 1) + " = assets(" + (int) playerWorldPos.getX() + " , " + (int) playerWorldPos.getY() + ", 100, 100,  debugMode, \"images/assets/warpStone.png\", false, 8, true);");

        } else if(pPressed && fivePressed && !placeCooldown) {
            placeCooldown = true;
            JLabel debugTree = assets((int) playerWorldPos.getX(), (int) playerWorldPos.getY(), 200, 300,  false, "images/assets/tree.png", false, 8, true);
            System.out.println("JLabel Tree" + (int) (Math.random() * (10000000 - 1) + 1) + " = assets(" + (int) playerWorldPos.getX() + " , " + (int) playerWorldPos.getY() + ", 200, 300,  debugMode, \"images/assets/tree.png\", false, 8, true);");
        } else if(pPressed && sixPressed && !placeCooldown) {
            placeCooldown = true;
            JLabel debugHouse = assets((int) playerWorldPos.getX(), (int) playerWorldPos.getY(), 400, 400,  false, "images/assets/houses/house.png", false, 8, true);
            System.out.println("JLabel House" + (int) (Math.random() * (10000000 - 1) + 1) + " = assets(" + (int) playerWorldPos.getX() + " , " + (int) playerWorldPos.getY() + ", 400, 400,  debugMode, \"images/assets/houses/house.png\", false, 8, true);");
        } else if(pPressed && sevenPressed && !placeCooldown) {
            placeCooldown = true;
            JLabel debugHouseTwo = assets((int) playerWorldPos.getX(), (int) playerWorldPos.getY(), 400, 400,  false, "images/assets/houses/houseTwo.png", false, 8, true);
            System.out.println("JLabel House Two" + (int) (Math.random() * (10000000 - 1) + 1) + " = assets(" + (int) playerWorldPos.getX() + " , " + (int) playerWorldPos.getY() + ", 400, 400,  debugMode, \"images/assets/houses/houseTwo.png\", false, 8, true);");
        } else if(pPressed && eightPressed && !placeCooldown) {
            placeCooldown = true;
            JLabel debugLittleBush = assets((int) playerWorldPos.getX(), (int) playerWorldPos.getY(), 100, 100,  false, "images/assets/littlebush.png", false, 8, true);
            System.out.println("JLabel Little Bush" + (int) (Math.random() * (10000000 - 1) + 1) + " = assets(" + (int) playerWorldPos.getX() + " , " + (int) playerWorldPos.getY() + ", 100, 100,  debugMode, \"images/assets/littlebush.png\", false, 8, true);");
        }

        if(onePressed) {

            debugRock.setVisible(true);
            debugChest.setVisible(false);
            debugRespawnPoint.setVisible(false);
            debugwarpStone.setVisible(false);
            debugTree.setVisible(false);
            debugHouse.setVisible(false);
            debugHouseTwo.setVisible(false);
            debugLittleBush.setVisible(false);

        } else if(twoPressed) {

            debugChest.setVisible(true);
            debugRock.setVisible(false);
            debugRespawnPoint.setVisible(false);
            debugwarpStone.setVisible(false);
            debugTree.setVisible(false);
            debugHouse.setVisible(false);
            debugHouseTwo.setVisible(false);
            debugLittleBush.setVisible(false);
        } else if(threePressed) {

            debugRespawnPoint.setVisible(true);
            debugChest.setVisible(false);
            debugRock.setVisible(false);
            debugwarpStone.setVisible(false);
            debugTree.setVisible(false);
            debugHouse.setVisible(false);
            debugHouseTwo.setVisible(false);
            debugLittleBush.setVisible(false);
        } else if(fourPressed) {
            debugwarpStone.setVisible(true);
            debugChest.setVisible(false);
            debugRock.setVisible(false);
            debugRespawnPoint.setVisible(false);
            debugTree.setVisible(false);
            debugHouse.setVisible(false);
            debugHouseTwo.setVisible(false);
            debugLittleBush.setVisible(false);
        } else if(fivePressed) {
            debugTree.setVisible(true);
            debugChest.setVisible(false);
            debugRock.setVisible(false);
            debugRespawnPoint.setVisible(false);
            debugwarpStone.setVisible(false);
            debugHouse.setVisible(false);
            debugHouseTwo.setVisible(false);
            debugLittleBush.setVisible(false);
        } else if(sixPressed) {
            debugHouse.setVisible(true);
            debugHouseTwo.setVisible(false);
            debugRock.setVisible(false);
            debugChest.setVisible(false);
            debugRespawnPoint.setVisible(false);
            debugwarpStone.setVisible(false);
            debugTree.setVisible(false);
            debugLittleBush.setVisible(false);
        } else if(sevenPressed) {
            debugHouseTwo.setVisible(true);
            debugHouse.setVisible(false);
            debugRock.setVisible(false);
            debugChest.setVisible(false);
            debugRespawnPoint.setVisible(false);
            debugwarpStone.setVisible(false);
            debugTree.setVisible(false);
            debugLittleBush.setVisible(false);

        } else if(eightPressed) {
            debugLittleBush.setVisible(true);
            debugHouseTwo.setVisible(false);
            debugHouse.setVisible(false);
            debugRock.setVisible(false);
            debugChest.setVisible(false);
            debugRespawnPoint.setVisible(false);
            debugwarpStone.setVisible(false);
            debugTree.setVisible(false);

        }



    }



    int startSelection = 1;
    boolean menuAlreadyChanged = false;

    public void startGame() {

        gameStarted = true;
        gameLoop();

    }
// This is the loop that the game control engine runs on.
    @Override
    public void keyPressed(KeyEvent e) {
        // Until the start screen is no longer visible, the movement code will not run, and this code will
        if (startScreenVisible) {
        int key = e.getKeyCode();


// This is where the menu switching occurs: if the down key is pressed, then it will increase the selection. 
        if (key == KeyEvent.VK_DOWN && !menuAlreadyChanged) {
            startSelection++;

            if (startSelection > 3) { 
                startSelection = 1;
//It then sets the menuAlreadyChanged boolean to true, which stops it from running again until the key has been released.
            menuAlreadyChanged = true;
            }
// The same thing happens if the up key is pressed.
        } if (key == KeyEvent.VK_UP && !menuAlreadyChanged) {
            startSelection--;

            if (startSelection < 1) {
                startSelection = 3;
            menuAlreadyChanged = true;
        }
    }

// The arrow that indicates what selection you are on goes to a specific spot depending on what the startSelection variable is
        if (startSelection == 1) {
            currentSelection.setLocation(25,192);
        } else if (startSelection == 2) {
            currentSelection.setLocation(25,292);
        } else if (startSelection == 3) {
            currentSelection.setLocation(25,392);
        }
        
    // This controls the actions that occur by checking if the space key is pressed and then what the selection is.
        if (key == KeyEvent.VK_SPACE) {
            switch (startSelection) {
                case 1:
// Sets all the menu items to not visible
                    startPlay.setVisible(false);
                    startCredits.setVisible(false);
                    startQuit.setVisible(false);
                    startMenu.setVisible(false);
                    SScredits.setVisible(false);
                    currentSelection.setVisible(false);
                    startScreenVisible = false;
                    // Starts the game loop
                    fadeOutStartScreen();
                    try {
                        clip.stop();
                        Sequencer("music/korok.wav", 100, 0.5f); // Play the clip when the program starts
                    } catch (UnsupportedAudioFileException | IOException | LineUnavailableException j) {
                        j.printStackTrace(); // Handle exceptions
                    }

                    return;
 // This is all for the credits menu, which can be toggled on or off by pressing the spacebar
                case 2: 
                // Checks whether or not the credits are already on the screen
                    if (SScredits.isVisible() == false) {
                    // Sets credit menu items to visible and the rest to invisible
                        SScredits.setVisible(true);
                        startMenu.setVisible(false);
                        startPlay.setVisible(false);
                        startCredits.setVisible(false);
                        startQuit.setVisible(false);
                        currentSelection.setVisible(false);
                    } else {
                    // Does the reverse of the one before (removes the credits and puts up the regular start screen menu
                        SScredits.setVisible(false);
                        startMenu.setVisible(true);
                        startPlay.setVisible(true);
                        startCredits.setVisible(true);
                        startQuit.setVisible(true);
                        currentSelection.setVisible(true);
                    }
                    
                    return;
                    // Case 3 is when they have selected quit
                case 3: 
                    System.exit(0);
                    // Closes the game

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
                fivePressed = false;
                sixPressed = false;
                sevenPressed = false;
                eightPressed = false;
                ninePressed = false;
                debug();

            }
            case KeyEvent.VK_2 -> {
                onePressed = false;
                twoPressed = true;
                threePressed = false;
                fourPressed = false;
                fivePressed = false;
                sixPressed = false;
                sevenPressed = false;
                eightPressed = false;
                ninePressed = false;
                debug();

            }
            case KeyEvent.VK_3 -> {
                onePressed = false;
                twoPressed = false;
                threePressed = true;
                fourPressed = false;
                fivePressed = false;
                sixPressed = false;
                sevenPressed = false;
                eightPressed = false;
                ninePressed = false;
                debug();

            }
            case KeyEvent.VK_4 -> {
                onePressed = false;
                twoPressed = false;
                threePressed = false;
                fourPressed = true;
                fivePressed = false;
                sixPressed = false;
                sevenPressed = false;
                eightPressed = false;
                ninePressed = false;
                debug();

            }
            case KeyEvent.VK_5 -> {
                onePressed = false;
                twoPressed = false;
                threePressed = false;
                fourPressed = false;
                fivePressed = true;
                sixPressed = false;
                sevenPressed = false;
                eightPressed = false;
                ninePressed = false;
                debug();

            }
            case KeyEvent.VK_6 -> {
                onePressed = false;
                twoPressed = false;
                threePressed = false;
                fourPressed = false;
                fivePressed = false;
                sixPressed = true;
                sevenPressed = false;
                eightPressed = false;
                ninePressed = false;
                debug();

            }
            case KeyEvent.VK_7 -> {
                onePressed = false;
                twoPressed = false;
                threePressed = false;
                fourPressed = false;
                fivePressed = false;
                sixPressed = false;
                sevenPressed = true;
                eightPressed = false;
                ninePressed = false;
                debug();

            }
            case KeyEvent.VK_8 -> {
                onePressed = false;
                twoPressed = false;
                threePressed = false;
                fourPressed = false;
                fivePressed = false;
                sixPressed = false;
                sevenPressed = false;
                eightPressed = true;
                ninePressed = false;
                debug();

            }
            case KeyEvent.VK_9 -> {
                onePressed = false;
                twoPressed = false;
                threePressed = false;
                fourPressed = false;
                fivePressed = false;
                sixPressed = false;
                sevenPressed = false;
                eightPressed = false;
                ninePressed = true;
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
// If one of the keys is released, then the menuAlreadyChanged variable is set to false, ensuring that it doesn’t loop too quickly.
        int key = e.getKeyCode();
            if (key == KeyEvent.VK_DOWN || key == KeyEvent.VK_UP) {
                // Changes the menu to false so you can press keys again
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
 


    }
    }
    @Override
    public void keyTyped(KeyEvent e) {}
}


