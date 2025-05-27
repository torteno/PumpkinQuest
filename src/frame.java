import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.Array;
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
    int FPS = 60;
    double currentHealth = 3.0, maximumHealth = 3.0;
    String direction = "down";
    double distance;
    double slope;
    double b;
    public static float volume = 0f;
    boolean GUIOpen = true;
    boolean NPCInteracted = false;
    boolean[] chestLooted = new boolean[7];
    boolean[] pressChestOn = new boolean[7];
    int messageDisDelay;
    int playerDamage = 5;
    String moveDirection = "down";
    int tortlesMoveDirection = 0;
    int tortlesDirection = 0;

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
    Map<JLabel, String[]> mobFrameAnimationFrames = new HashMap<>();
    ArrayList<Point> playerPastPositions = new ArrayList<>();







    ArrayList<Point> playerPastPoints = new ArrayList<>();
    Map<String, ImageIcon> playerImages = new HashMap<>();
    Map<String, ImageIcon> tortlesImages = new HashMap<>();
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


    boolean debugMode = false; // false to enable, true to disable
    boolean placeCooldown = false;
    int swordUpgrade = 0;

    //JLabel cordBox = assets(20, 20, 75, 75, false, "images/GUI/coordinateBox.png", false);

   

    JLabel press = GUIassets(125, 700, 760, 40, false, "images/GUI/pressE.png", false, 2, false);
    JLabel pressChest = GUIassets(125, 700, 760, 40, false, "images/GUI/pressE.png", false, 2, false);


    JLabel gotApple = GUIassets( 130, 600, 1280, 320, false, "images/text/appleFind.png", false, 2, false);

   //JLabel chest =  assets(2000, 1000, 200, 200, false, "images/assets/chest.png", false, 8, true);


   JLabel warp = assets(-1000, 1000, 200, 200, false, "images/assets/warpstone.png", false, 8, true);

    JLabel treebarrier = assets(2775, -5800, 590, 7500, debugMode, "images/assets/manymanytrees.png", false, 8, true);
    JLabel respawnPointOne = assets( 2550, -1250, 150, 200, false, "images/assets/RespawnPoint.png", false, 8, true);

    JLabel[] chestImages = new JLabel[] {
        assets(3880, -525, 150, 150, false, "images/assets/chest.png", false, 8, true),
        assets(1708 , -2861, 200, 200,  false, "images/assets/chest.png", false, 8, true),
        assets(6000, -465, 150, 150, false, "images/assets/chest.png", false, 8, true)


};

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
    
    JLabel tortles = mobCreation(0, 0, 200,376, "images/mob/tortles/downStanding.png", 2, 100, 0.5, 150, 2, 800,1);


    JLabel NPC = assets(2100,  -2000, 100, 200, false, "images/NPC/Grandma/grandma.png", false, 2, true);

    JLabel waterBarrier = assets(0, 900, 4800, 800, debugMode, "", false, 3 , false);
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


    JLabel NPCBackground = GUIassets(-170, 370, 1320, 500, false, "images/NPC/NPCDialogueBackground.png", false, 2, false);

    /*
    JLabel NPCScroller1 = GUIassets(-150, 590, 1000, 30, false, "images/NPC/coverDialogue.png", false, 0, false);
    JLabel NPCScroller2 = GUIassets(-150, 615, 1000, 30, false, "images/NPC/coverDialogue.png", false, 0, false);
    JLabel NPCScroller3 = GUIassets(-150, 640, 1000, 30, false, "images/NPC/coverDialogue.png", false, 0, false);
    JLabel NPCScroller4 = GUIassets(-150, 665, 1000, 30, false, "images/NPC/coverDialogue.png", false, 0, false);
    */

    JLabel rockThird = assets( 2500, 2500, 200, 200, false, "images/assets/rock.png", false, 8, true);

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

    JLabel House6307983 = assets(7843 , -1214, 600, 600,  debugMode, "images/assets/houses/house.png", false, 8, true);
    JLabel House6347268 = assets(7877 , -2268, 600, 600,  debugMode, "images/assets/houses/house.png", false, 8, true);
    JLabel House5613608 = assets(7715 , -3102, 600, 600,  debugMode, "images/assets/houses/house.png", false, 8, true);
    JLabel HouseTwo9396074 = assets(7628, -3758, 600, 600,  debugMode, "images/assets/houses/houseTwo.png", false, 8, true);
    JLabel HouseTwo6064984 = assets(7907 , -4356, 600, 600,  debugMode, "images/assets/houses/houseTwo.png", false, 8, true);
    JLabel HouseTwo924866 = assets(8508 , -1141, 600, 600,  debugMode, "images/assets/houses/houseTwo.png", false, 8, true);
    JLabel HouseTwo7756268 = assets(1808 , -2580, 400, 400,  debugMode, "images/assets/houses/houseTwo.png", false, 8, true);


// ALL for the maze
    JLabel LittleBush4476318 = assets(10904 , -486, 100, 100,  debugMode, "images/assets/littlebush.png", false, 8, true);
    JLabel LittleBush4131510 = assets(10904 , -906, 100, 100,  debugMode, "images/assets/littlebush.png", false, 8, true);
    JLabel LittleBush2396163 = assets(10904 , -1386, 100, 100,  debugMode, "images/assets/littlebush.png", false, 8, true);
    JLabel Stone5177209 = assets(10856 , -1194, 100, 100,  debugMode, "images/assets/warpStone.png", false, 8, true);
    JLabel Stone9797965 = assets(10856 , -738, 100, 100,  debugMode, "images/assets/warpStone.png", false, 8, true);
    JLabel LittleBush8318242 = assets(11540 , -738, 100, 100,  debugMode, "images/assets/littlebush.png", false, 8, true);
    JLabel LittleBush2976735 = assets(11544 , -1150, 100, 100,  debugMode, "images/assets/littlebush.png", false, 8, true);
    JLabel LittleBush5659796 = assets(11544 , -1618, 100, 100,  debugMode, "images/assets/littlebush.png", false, 8, true);
    JLabel LittleBush9842894 = assets(11544 , -370, 100, 100,  debugMode, "images/assets/littlebush.png", false, 8, true);
    JLabel Stone6845185 = assets(11496 , -622, 100, 100,  debugMode, "images/assets/warpStone.png", false, 8, true);
    JLabel Stone9568183 = assets(11496 , -1006, 100, 100,  debugMode, "images/assets/warpStone.png", false, 8, true);
    JLabel Stone6220174 = assets(11496 , -1438, 100, 100,  debugMode, "images/assets/warpStone.png", false, 8, true);
    JLabel LittleBush3659183 = assets(12184 , -1009, 100, 100,  debugMode, "images/assets/littlebush.png", false, 8, true);
    JLabel LittleBush3050567 = assets(12184 , -1357, 100, 100,  debugMode, "images/assets/littlebush.png", false, 8, true);
    JLabel LittleBush9963417 = assets(12184 , -1741, 100, 100,  debugMode, "images/assets/littlebush.png", false, 8, true);
    JLabel LittleBush505283 = assets(12184 , -637, 100, 100,  debugMode, "images/assets/littlebush.png", false, 8, true);
    JLabel Stone7637997 = assets(12123 , -857, 100, 100,  debugMode, "images/assets/warpStone.png", false, 8, true);
    JLabel Stone8246206 = assets(12123 , -1241, 100, 100,  debugMode, "images/assets/warpStone.png", false, 8, true);
    JLabel Stone72906 = assets(12123 , -1601, 100, 100,  debugMode, "images/assets/warpStone.png", false, 8, true);
    JLabel LittleBush7556671 = assets(12836 , -1052, 100, 100,  debugMode, "images/assets/littlebush.png", false, 8, true);
    JLabel LittleBush4248156 = assets(12836 , -1400, 100, 100,  debugMode, "images/assets/littlebush.png", false, 8, true);
    JLabel LittleBush1221163 = assets(12836 , -668, 100, 100,  debugMode, "images/assets/littlebush.png", false, 8, true);
    JLabel LittleBush8010436 = assets(12836 , -296, 100, 100,  debugMode, "images/assets/littlebush.png", false, 8, true);
    JLabel Stone2224303 = assets(12776 , -524, 100, 100,  debugMode, "images/assets/warpStone.png", false, 8, true);
    JLabel Stone1680669 = assets(12776 , -932, 100, 100,  debugMode, "images/assets/warpStone.png", false, 8, true);
    JLabel Stone2808549 = assets(12776 , -1280, 100, 100,  debugMode, "images/assets/warpStone.png", false, 8, true);
    JLabel LittleBush1556035 = assets(13366 , -1586, 100, 100,  debugMode, "images/assets/littlebush.png", false, 8, true);
    JLabel LittleBush5714479 = assets(13366 , -1226, 100, 100,  debugMode, "images/assets/littlebush.png", false, 8, true);
    JLabel LittleBush961166 = assets(13366 , -854, 100, 100,  debugMode, "images/assets/littlebush.png", false, 8, true);
    JLabel LittleBush2378646 = assets(13366 , -482, 100, 100,  debugMode, "images/assets/littlebush.png", false, 8, true);
    JLabel Stone9152590 = assets(13306 , -722, 100, 100,  debugMode, "images/assets/warpStone.png", false, 8, true);
    JLabel Stone3491455 = assets(13306 , -1094, 100, 100,  debugMode, "images/assets/warpStone.png", false, 8, true);
    JLabel Stone613528 = assets(13306 , -1466, 100, 100,  debugMode, "images/assets/warpStone.png", false, 8, true);
    JLabel LittleBush8287394 = assets(13966 , -1028, 100, 100,  debugMode, "images/assets/littlebush.png", false, 8, true);
    JLabel LittleBush2024410 = assets(13966 , -560, 100, 100,  debugMode, "images/assets/littlebush.png", false, 8, true);
    JLabel LittleBush3074532 = assets(13966 , -1388, 100, 100,  debugMode, "images/assets/littlebush.png", false, 8, true);
    JLabel Stone2795917 = assets(13906 , -1280, 100, 100,  debugMode, "images/assets/warpStone.png", false, 8, true);
    JLabel Stone2287913 = assets(13906 , -848, 100, 100,  debugMode, "images/assets/warpStone.png", false, 8, true);
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
        loadAndScaleTortlesImages();

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


    public Point TortlesMovement(int x, int y, int mobSpeed, int followDistance, Point spawnPoint) {
        distance = Math.sqrt(Math.pow(((playerWorldPos.x - 40) - x), 2) + Math.pow(((playerWorldPos.y-50) - y), 2));

        //step -= mobSpeed;

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

      //  if(distanceX < 100 || distanceY < 100) {

         //   moveDirection = 0; // Not moving

    //    } else {

            if (Math.abs(distanceX) >= Math.abs(distanceY)) {
                if (playerWorldPos.getX() >= tortles.getX()) {
                    moveDirection = "right"; // Moving right
                } else {
                    moveDirection = "left"; // Moving left
                }
            } else {
                if (playerWorldPos.getY() >= tortles.getY()) {
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



    public Point ProjectTile(int x, int y, int projectileSpeed, int followDistance, Point playerPoint) {
        distance = Math.sqrt(Math.pow(((playerPoint.x - 40) - x), 2) + Math.pow(((playerPoint.y-50) - y), 2));

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

                if (playerWorldPos.y > y) {
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
            else {
               TimeMobAttacked.put(mobID, LocalDateTime.now());
                MobAttackCurrentCoolDown.put(mobID, 0L);
            }
        }
    }



    public void interacting() {

        chest();
        NPCInteraction();


        if(player.getBounds().intersects(warp.getBounds())) {
            playerWorldPos.setLocation(-50, 0);

        }


        if(player.getBounds().intersects(respawnPointOne.getBounds())) {
            if(ePressed) {
                SpawnPoint.setLocation(2360, -678);
            }
        }

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
        } else {
            press.setVisible(false);
        }

    }



    public void chest () {

        for (int i = 0; i < chestImages.length; i++) {


            if (player.getBounds().intersects((chestImages[i]).getBounds()) && !chestLooted[i] && ePressed) {

                switch (i) {

                    case 0 : {
                        pressChest.setVisible(false);
                        gotApple.setVisible(true);
                        healthChange(3);
                        chestLooted[0] = true;

                        break;
                    }
                    case 1 : {
                        pressChest.setVisible(false);
                        System.out.println("You found a stone sword");
                        playerDamage = 2;
                        chestLooted[1] = true;

                        break;
                    }
                    case 2 : {
                        pressChest.setVisible(false);

                        System.out.println("You found a iron sword");
                        playerDamage = 3;
                        chestLooted[2] = true;

                        break;
                    }
                    case 3 : {
                        pressChest.setVisible(false);

                        System.out.println("You found a gold sword");
                        playerDamage = 4;
                        chestLooted[3] = true;

                        break;
                    }
                    case 4 : {
                        pressChest.setVisible(false);

                        System.out.println("You found a ruby sword");
                        playerDamage = 6;
                        chestLooted[4] = true;

                        break;
                    }
                    case 5 : {
                        pressChest.setVisible(false);

                        System.out.println("You found a emerald sword");
                        playerDamage = 8;
                        chestLooted[5] = true;

                        break;
                    }
                    case 6 : {
                        pressChest.setVisible(false);

                        System.out.println("You found a diamond sword");
                        playerDamage = 10;
                        chestLooted[6] = true;

                        break;
                    }
                }

            } else if (player.getBounds().intersects((chestImages[i]).getBounds()) && !chestLooted[i] && !pressChestOn[i]) {
                pressChest.setVisible(true);
                pressChestOn[i] = true;
            }
            else if (!(player.getBounds().intersects((chestImages[i]).getBounds())) && !chestLooted[i] && pressChestOn[i]) {
                pressChest.setVisible(false);
                pressChestOn[i] = false;
            }


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


    public String AnimationCore(String[] arrayImages, int timeBetweenImages) {
        String currentImage = "";

        int maxArrayLenength = arrayImages.length - 1;

        LocalDateTime lastImageSwitchTime = LocalDateTime.now();

        Duration timeSinceLastImage = Duration.between(lastImageSwitchTime, LocalDateTime.now());

      //  mobCooldown = (Math.abs(durationMobAttack.get(ChronoUnit.SECONDS)));



        for(int i = 0; i < arrayImages.length; i++) {



        }



        return currentImage;
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


