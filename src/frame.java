import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class frame extends JFrame implements KeyListener {

    JLabel player;
    JLabel obstacle;

    int x;
    int y;
    int step = 10;
    double angleStep = 0;
    boolean upPressed = false;
    boolean downPressed = false;
    boolean leftPressed = false;
    boolean rightPressed = false;


    double CoordinateX = 0;
    double CoordinateY = 0;


    //all timer code was chatgpt, as I have never used timers before and I didn't know it was the fix to a problem
    Timer timer;

    frame() {
        super("My Frame!");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1500, 1000);
        setLocationRelativeTo(null);
        setResizable(false);
        System.out.print("hello");


        BackgroundPanel backgroundPanel = new BackgroundPanel("images/forest.png");
        backgroundPanel.setLayout(null);


        player = new JLabel();
        player.setBounds(0, 0, 100, 100);
        player.setBackground(Color.red);
        player.setOpaque(true);

        x = player.getX();
        y = player.getY();

        backgroundPanel.add(player);


        ImageIcon icon = new ImageIcon("images/rock.png");
        Image image = icon.getImage();
        Image scaledImage = image.getScaledInstance(140, 140, Image.SCALE_SMOOTH);
        obstacle = new JLabel(new ImageIcon(scaledImage));
        obstacle.setBounds(300, 600, 100, 100);

        backgroundPanel.add(obstacle);
        backgroundPanel.add(player);

        backgroundPanel.setVisible(true);
        setContentPane(backgroundPanel);




        setContentPane(backgroundPanel);
        addKeyListener(this);
        setVisible(true);



        timer = new Timer(30, e -> playerPosition());
    }

    private boolean isCollision(int x, int y) {
        Rectangle newBounds = new Rectangle(x, y, player.getWidth(), player.getHeight());
        return !newBounds.intersects(obstacle.getBounds());
    }


    private void playerPosition() {

        if (upPressed && leftPressed && isCollision(x - step, y - step)) {
            x -= step / Math.sqrt(2);
            y -= step / Math.sqrt(2);
            player.setIcon(new ImageIcon("images/up_left.png"));
        } else if (upPressed && rightPressed && isCollision(x + step, y - step)) {
            x += step / Math.sqrt(2);
            y -= step / Math.sqrt(2);
            player.setIcon(new ImageIcon("images/up_right.png"));
        } else if (downPressed && leftPressed && isCollision(x - step, y + step)) {
            x -= step / Math.sqrt(2);
            y += step / Math.sqrt(2);
            player.setIcon(new ImageIcon("images/down_left.png"));
        } else if (downPressed && rightPressed && isCollision(x + step, y + step)) {
            x += step / Math.sqrt(2);
            y += step / Math.sqrt(2);
            player.setIcon(new ImageIcon("images/down_right.png"));
        } else if (upPressed && isCollision(x, y - step)) {
            y -= step;
            player.setIcon(new ImageIcon("images/up.png"));
        } else if (downPressed && isCollision(x, y + step)) {
            y += step;
            player.setIcon(new ImageIcon("images/down.png"));
        } else if (leftPressed && isCollision(x - step, y)) {
            x -= step;
            player.setIcon(new ImageIcon("images/left.png"));
        } else if (rightPressed && isCollision(x + step, y)) {
            x += step;
            player.setIcon(new ImageIcon("images/right.png"));
        }

        player.setLocation(x, y);
        System.out.println("X: " + CoordinateX + " Y: " + CoordinateY);
    }

    @Override
    public void keyPressed(KeyEvent e) {
       /* if (e.getKeyCode() == KeyEvent.VK_W) {
            playerPosition();
            upPressed = true;
            System.out.println("You moved Up");
        }
        if (e.getKeyCode() == KeyEvent.VK_A) {
            playerPosition();
            leftPressed = true;
            System.out.println("You moved Left");
        }
        if (e.getKeyCode() == KeyEvent.VK_S) {
            playerPosition();
            downPressed = true;
            System.out.println("You moved down");
        }
        if (e.getKeyCode() == KeyEvent.VK_D) {
            playerPosition();
            rightPressed = true;
            System.out.println("You moved right");
        } */

     /*   if (e.getKeyCode() == KeyEvent.VK_W && e.getKeyCode() == KeyEvent.VK_A) {
            topLeft(e);
            System.out.println("You moved right");
        }

        if (e.getKeyCode() == KeyEvent.VK_W && e.getKeyCode() == KeyEvent.VK_D) {
            topRight(e);
            System.out.println("You moved right");
        }

        if (e.getKeyCode() == KeyEvent.VK_S && e.getKeyCode() == KeyEvent.VK_A) {
            bottomLeft(e);
            System.out.println("You moved right");
        }

        if (e.getKeyCode() == KeyEvent.VK_S && e.getKeyCode() == KeyEvent.VK_D) {
            bottomRight(e);
            System.out.println("You moved right");
        } */

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
        timer.start();

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
            timer.stop();
        }


    }

    @Override
    public void keyTyped(KeyEvent e) {
    }
}





   /* public void moveUp(KeyEvent e) {

            if (isCollision(x, y - step)) {
                System.out.println("You moved forwards!");
               // y -= step;
                player.setLocation(x, y - step);

            }
        }


    public void moveLeft(KeyEvent e) {
            if (isCollision(x - step, y)) {
                System.out.println("You moved forwards!");
              //  x -= step;
                player.setLocation(x - step, y);
            }
        }


    public void moveRight(KeyEvent e) {
            if (isCollision(x + step, y)) {
                System.out.println("You moved forwards!");
               // x += step;
                player.setLocation(x + step, y);
            }
        }


    public void moveDown(KeyEvent e) {
            if (isCollision(x, y + step)) {
                System.out.println("You moved forwards!");
              //  y += step;
                player.setLocation(x, y + step);
            }
        }



    public void topLeft(KeyEvent e) {

        if (isCollision(x - step, y - step)) {
            System.out.println("You moved forwards!");
            // y -= step;
            player.setLocation(x - step, y - step);

        }
    }

    public void topRight(KeyEvent e) {

        if (isCollision(x + step, y - step)) {
            System.out.println("You moved forwards!");
            // y -= step;
            player.setLocation(x + step, y - step);

        }
    }

    public void bottomLeft(KeyEvent e) {

        if (isCollision(x - step, y + step)) {
            System.out.println("You moved forwards!");
            // y -= step;
            player.setLocation(x - step, y + step);

        }
    }

    public void bottomRight(KeyEvent e) {

        if (isCollision(x + step, y + step)) {
            System.out.println("You moved forwards!");
            // y -= step;
            player.setLocation(x + step, y + step);

        }
    }





    }





/* switch(e.getKeyCode()) {
            case KeyEvent.VK_W:
                player.setIcon(new ImageIcon("images/up.png"));
                if (isCollision(x, y - step)) {
                    System.out.println("You moved forwards!");
                    player.setLocation(x, y - step);
                }
                break;
            case KeyEvent.VK_A:
                player.setIcon(new ImageIcon("images/left.png"));
                if (isCollision(x - step, y)) {
                    player.setLocation(x - step, y);
                }
                break;
            case KeyEvent.VK_S:
                player.setIcon(new ImageIcon("images/down.png"));
                if (isCollision(x, y + step)) {
                    player.setLocation(x, y + step);
                }
                break;
            case KeyEvent.VK_D:
                player.setIcon(new ImageIcon("images/right.png"));
                if (isCollision(x + step, y)) {
                    player.setLocation(x + step, y);
                }
                break;
        } */







//