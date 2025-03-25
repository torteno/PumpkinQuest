import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.JLabel;


public class frame extends JFrame implements KeyListener {

    JLabel player;
    JLabel obstacle;

    frame() {
        JFrame frame = new JFrame("My Frame!");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1500,1000);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);


        ImageIcon icon = new ImageIcon("images/rock.png");
        Image image = icon.getImage();
        Image scaledImage = image.getScaledInstance(140, 140, Image.SCALE_SMOOTH);
        obstacle = new JLabel(new ImageIcon(scaledImage));
        obstacle.setBounds(200, 200, 100, 100);


        player = new JLabel();
        player.setBounds(0,0,100,100);
        player.setBackground(Color.red);
        player.setOpaque(true);
        frame.add(player);
        frame.add(obstacle);
        frame.getContentPane().setBackground(Color.blue);
        frame.setLayout(null);
        frame.addKeyListener(this);
        frame.setVisible(true);
    }


    private boolean isCollision(int x, int y) {
        Rectangle newBounds = new Rectangle(x, y, player.getWidth(), player.getHeight());
        return !newBounds.intersects(obstacle.getBounds());
    }

    @Override
    public void keyPressed(KeyEvent e) {

        int x = player.getX();
        int y = player.getY();
        int step = 10;

        switch(e.getKeyCode()) {
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
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // System.out.println("You Released the Keychar:" + e.getKeyChar());
        System.out.println("You Released the KeyCode:" + e.getKeyCode());
        if(e.getKeyChar() == 'w') {
            System.out.println("You moved forwards!");
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // System.out.println("You Typed the Keychar:" + e.getKeyChar());
        // System.out.println("You Typed the KeyCode:" + e.getKeyCode());
    }


}