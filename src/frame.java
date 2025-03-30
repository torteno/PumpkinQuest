import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.Map;

public class frame extends JFrame implements KeyListener {

    JLabel player;
    JLabel obstacle;

    int x, y;
    int step = 6;
    boolean upPressed = false, downPressed = false, leftPressed = false, rightPressed = false;
    int moveTime, moveDir;
    int FPS = 60;
    String direction = "down";

    Map<String, ImageIcon> playerImages = new HashMap<>();

    frame() {
        super("gameFrame");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1500, 1000);
        setLocationRelativeTo(null);
        setResizable(false);
        System.out.print("hello");

        BackgroundPanel backgroundPanel = new BackgroundPanel("images/background/forest.png");
        backgroundPanel.setLayout(null);

        loadAndScalePlayerImages();

        player = new JLabel(playerImages.get("downStanding"));
        player.setBounds(0, 0, 100, 200);
        player.setOpaque(false);

        x = player.getX();
        y = player.getY();



        ImageIcon rockIcon = new ImageIcon(new ImageIcon("images/assets/rock.png").getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH));
        obstacle = new JLabel(rockIcon);
        obstacle.setBounds(300, 600, 100, 100);

        backgroundPanel.add(obstacle);
        obstacle.setOpaque(false);
        backgroundPanel.add(player);

        setContentPane(backgroundPanel);
        addKeyListener(this);
        setVisible(true);

        moveDir = 1;
        gameLoop();
    }

    private boolean isCollision(int x, int y) {
        Rectangle playerBounds = player.getBounds();
        Rectangle obstacleBounds = obstacle.getBounds();
        playerBounds.setLocation(x, y);
        return playerBounds.intersects(obstacleBounds);
    }
    private void loadAndScalePlayerImages() {
        String[] imageNames = {"downStanding", "downRight", "downLeft"};
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
                playerPosition();
                placeholder--;
            }
        }
    }

    private void playerPosition() {

        int newX = x, newY = y;

        if (upPressed && leftPressed && isCollision(x - step, y + step)) {
            newX -= step / Math.sqrt(2);
            newY -= step / Math.sqrt(2);
            direction = "up";
        } else if (upPressed && rightPressed) {
            newX += step / Math.sqrt(2);
            newY -= step / Math.sqrt(2);
            direction = "up";
        } else if (downPressed && leftPressed) {
            newX -= step / Math.sqrt(2);
            newY += step / Math.sqrt(2);
            direction = "down";
        } else if (downPressed && rightPressed) {
            newX += step / Math.sqrt(2);
            newY += step / Math.sqrt(2);
            direction = "down";
        } else if (upPressed) {
            newY -= step;
            direction = "up";
        } else if (downPressed) {
            newY += step;
            direction = "down";
        } else if (leftPressed) {
            newX -= step;
            direction = "left";
        } else if (rightPressed) {
            newX += step;
            direction = "right";
        }
        if (!isCollision(newX, newY)) {
            x = newX;
            y = newY;
        }

        String imageName;
        if (moveDir == 1) {
            imageName = direction+"Standing";
        } else if (moveDir == 2) {
            imageName = direction + "Right";
        } else if (moveDir == 3) {
            imageName = direction + "Standing";
        } else {
            imageName = direction + "Left";
}

player.setIcon(playerImages.get(imageName));

        moveTime++;
        if (moveTime >= (FPS / 6)) {
            moveDir = (moveDir % 4) + 1;
            moveTime = 0;
        }

        player.setLocation(x, y);
        player.repaint();
    

    if (!upPressed && !downPressed && !leftPressed && !rightPressed) {
        player.setIcon(playerImages.get(direction + "Standing"));
    }
}

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W -> upPressed = true;
            case KeyEvent.VK_S -> downPressed = true;
            case KeyEvent.VK_A -> leftPressed = true;
            case KeyEvent.VK_D -> rightPressed = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W -> upPressed = false;
            case KeyEvent.VK_S -> downPressed = false;
            case KeyEvent.VK_A -> leftPressed = false;
            case KeyEvent.VK_D -> rightPressed = false;
        }

        
    }

    @Override
    public void keyTyped(KeyEvent e) {}
}
