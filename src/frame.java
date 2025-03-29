import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;


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
    int animationDown = 1;


    double CoordinateX = 0;
    double CoordinateY = 0;

    Image playerImage = new ImageIcon("images/standing.png").getImage();
    Image scaledPlayerImage = playerImage.getScaledInstance(100, 200, Image.SCALE_SMOOTH);



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
        player.setBounds(0, 0, 100, 200);
        player.setBackground(Color.red);
        player.setOpaque(true);

        x = player.getX();
        y = player.getY();

        backgroundPanel.add(player);


        ImageIcon icon = new ImageIcon("images/rock.png");
        Image image = icon.getImage();
        Image scaledImage = image.getScaledInstance(150, 150, Image.SCALE_SMOOTH);
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
            player.setBackground(Color.blue);
        } else if (upPressed && rightPressed && isCollision(x + step, y - step)) {
            x += step / Math.sqrt(2);
            y -= step / Math.sqrt(2);
            player.setIcon(new ImageIcon("images/up_right.png"));
            player.setBackground(Color.blue);
        } else if (downPressed && leftPressed && isCollision(x - step, y + step)) {
            x -= step / Math.sqrt(2);
            y += step / Math.sqrt(2);
            player.setIcon(new ImageIcon("images/down_left.png"));
            player.setBackground(Color.blue);
        } else if (downPressed && rightPressed && isCollision(x + step, y + step)) {
            x += step / Math.sqrt(2);
            y += step / Math.sqrt(2);
            player.setIcon(new ImageIcon("images/down_right.png"));
            player.setBackground(Color.blue);
        } else if (upPressed && isCollision(x, y - step)) {
            y -= step;
            player.setIcon(new ImageIcon("images/up.png"));
            player.setBackground(Color.blue);
        } else if (downPressed && isCollision(x, y + step)) {
            y += step;
            player.setIcon(new ImageIcon("images/down.png"));
            player.setBackground(Color.blue);
        } else if (leftPressed && isCollision(x - step, y)) {
            x -= step;
            player.setIcon(new ImageIcon("images/left.png"));
            player.setBackground(Color.blue);
        } else if (rightPressed && isCollision(x + step, y)) {
            x += step;
            player.setIcon(new ImageIcon("images/right.png"));
            player.setBackground(Color.blue);
        } else {
            player.setIcon(new ImageIcon(scaledPlayerImage));
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

            player.setIcon(new ImageIcon(scaledPlayerImage));

        } else {
            playerPosition();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }
}

