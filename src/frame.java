import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;


public class frame extends JFrame implements KeyListener {

    JLabel player;
    JLabel obstacle;

    int x;
    int y;
    int step = 6;
    double angleStep = 0;
    boolean upPressed = false;
    boolean downPressed = false;
    boolean leftPressed = false;
    boolean rightPressed = false;
    int animationDown = 1;
    String direction = "down";
    int moveTime, moveDir;
    int FPS = 60;


    double CoordinateX = 0;
    double CoordinateY = 0;

    Image playerImage = new ImageIcon("images/player/standing.png").getImage();
    Image scaledPlayerImage = playerImage.getScaledInstance(100, 200, Image.SCALE_SMOOTH);





    frame() {
        super("My Frame!");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1500, 1000);
        setLocationRelativeTo(null);
        setResizable(false);
        System.out.print("hello");


        BackgroundPanel backgroundPanel = new BackgroundPanel("images/background/forest.png");
        backgroundPanel.setLayout(null);


        player = new JLabel();
        player.setBounds(0, 0, 100, 200);
        player.setBackground(Color.red);
        player.setOpaque(true);

        x = player.getX();
        y = player.getY();

        backgroundPanel.add(player);


        ImageIcon icon = new ImageIcon("images/assests/rock.png");
        Image image = icon.getImage();
        Image scaledImage = image.getScaledInstance(150, 150, Image.SCALE_SMOOTH);
        obstacle = new JLabel(new ImageIcon(scaledImage));
        obstacle.setBounds(300, 600, 100, 100);

        backgroundPanel.add(obstacle);
        backgroundPanel.add(player);



        backgroundPanel.setVisible(true);
        setContentPane(backgroundPanel);



        moveDir = 1;

        addKeyListener(this);
        setVisible(true);



        long previousTime = System.nanoTime();
        double placeholder = 0;
        long currentTime;
        double timePerFrame = 1000000000/FPS;

        while (true) {
            currentTime = System.nanoTime();
            placeholder += (currentTime - previousTime) / timePerFrame;
            previousTime = currentTime;
            if (placeholder >= 1) {
                playerPosition();

                //insert code here
                placeholder--;
                System.out.println(moveDir);
            }
        }


    }

    private boolean isCollision(int x, int y) {
        Rectangle newBounds = new Rectangle(x, y, player.getWidth(), player.getHeight());
        return !newBounds.intersects(obstacle.getBounds());
    }


    private void playerPosition() {




        if (upPressed && leftPressed && isCollision(x - step, y - step)) {
            x -= step / Math.sqrt(2);
            y -= step / Math.sqrt(2);
            direction = "up";
            player.setBackground(Color.blue);
        } else if (upPressed && rightPressed && isCollision(x + step, y - step)) {
            x += step / Math.sqrt(2);
            y -= step / Math.sqrt(2);
            direction = "up";
            player.setBackground(Color.blue);
        } else if (downPressed && leftPressed && isCollision(x - step, y + step)) {
            x -= step / Math.sqrt(2);
            y += step / Math.sqrt(2);
            direction = "down";
            player.setBackground(Color.blue);
        } else if (downPressed && rightPressed && isCollision(x + step, y + step)) {
            x += step / Math.sqrt(2);
            y += step / Math.sqrt(2);
            direction = "down";
            player.setBackground(Color.blue);
        } else if (upPressed && isCollision(x, y - step)) {
            y -= step;
            direction = "up";
            player.setBackground(Color.blue);
        } else if (downPressed && isCollision(x, y + step)) {
            y += step;
            direction = "down";
            player.setBackground(Color.blue);
        } else if (leftPressed && isCollision(x - step, y)) {
            x -= step;
            direction = "left";
            player.setBackground(Color.blue);
        } else if (rightPressed && isCollision(x + step, y)) {
            x += step;
            direction = "right";
            player.setBackground(Color.blue);
        } else {
            player.setIcon(new ImageIcon(scaledPlayerImage));
        }

        switch (direction) {
            case "down":
                if (moveDir == 1) {
                    player.setIcon(new ImageIcon("images/player/standing.png"));

                } else if (moveDir == 2) {
                    player.setIcon(new ImageIcon("images/player/downRight.png"));

                } else if (moveDir == 3) {
                    player.setIcon(new ImageIcon("images/player/downLeft.png"));

                }
                break;

            case "up":
                if (moveDir == 1) {
                    //player.setIcon(new ImageIcon("images/player/standing.png"));

                } else if (moveDir == 2) {
                    //player.setIcon(new ImageIcon("images/player/downRight.png"));

                } else if (moveDir == 3) {
                    //player.setIcon(new ImageIcon("images/player/downLeft.png"));

                }
                break;

            case "left":
                if (moveDir == 1) {
                    //player.setIcon(new ImageIcon("images/player/standing.png"));

                } else if (moveDir == 2) {
                    //player.setIcon(new ImageIcon("images/player/downRight.png"));

                } else if (moveDir == 3) {
                    //player.setIcon(new ImageIcon("images/player/downLeft.png"));

                }
                break;

            case "right":
                if (moveDir == 1) {
                    //player.setIcon(new ImageIcon("images/player/standing.png"));

                } else if (moveDir == 2) {
                    //player.setIcon(new ImageIcon("images/player/downRight.png"));

                } else if (moveDir == 3) {
                    //player.setIcon(new ImageIcon("images/player/downLeft.png"));

                }
                break;

        }
        moveTime++;
        if (upPressed == true || downPressed == true || leftPressed == true || rightPressed == true) {

            if (moveTime >= (FPS / 4)) {
                if (moveDir == 1) {
                    moveDir = 2;
                } else if (moveDir == 2) {
                    moveDir = 3;
                } else if (moveDir == 3) {
                    moveDir = 1;
                }
                moveTime = 0;
            }
        } else if (moveTime >= (FPS / 4)){
                moveDir = 1;
                moveTime = 0;
        }

        player.setLocation(x, y);
        player.repaint();
        System.out.println("X: " + CoordinateX + " Y: " + CoordinateY);
    }

    @Override
    public void keyPressed(KeyEvent e) {

        switch (e.getKeyCode()) {
            case KeyEvent.VK_W:
                upPressed = true;
                //playerPosition();
                break;
            case KeyEvent.VK_S:
                downPressed = true;
                //playerPosition();
                break;
            case KeyEvent.VK_A:
                leftPressed = true;
                //playerPosition();
                break;
            case KeyEvent.VK_D:
                rightPressed = true;
                //playerPosition();
                break;
        }


    }

    @Override
    public void keyReleased(KeyEvent e) {
        System.out.println("You Released the KeyCode:" + e.getKeyCode());
        if (e.getKeyChar() == 'w') {
            System.out.println("You moved forwards!");
        }

        switch (e.getKeyCode()) {
            case KeyEvent.VK_W:
                upPressed = false;
                break;
            case KeyEvent.VK_S:
                downPressed = false;
                break;
            case KeyEvent.VK_A:
                leftPressed = false;
                break;
            case KeyEvent.VK_D:
                rightPressed = false;
                break;
        }

        if (!upPressed && !downPressed && !leftPressed && !rightPressed) {


            player.setIcon(new ImageIcon(scaledPlayerImage));

        } else {
            playerPosition();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }
}

