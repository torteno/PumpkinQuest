import javax.swing.*;
import java.awt.Color;
import java.awt.event.*;
import javax.swing.JLabel;
import java.awt.Rectangle;


public class frame extends JFrame implements KeyListener {

    JLabel label;
    JLabel obstacle;

    frame() {
        JFrame frame = new JFrame("My Frame!");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1500,1000);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);

        obstacle = new JLabel();
        obstacle.setBounds(200, 200, 100, 100);
        obstacle.setBackground(Color.green);
        obstacle.setOpaque(true);


        label = new JLabel();
        label.setBounds(0,0,100,100);
        label.setBackground(Color.red);
        label.setOpaque(true);
        frame.add(label);
        frame.add(obstacle);
        frame.getContentPane().setBackground(Color.blue);
        frame.setLayout(null);
        frame.addKeyListener(this);
        frame.setVisible(true);
    }


    private boolean isCollision(int x, int y) {
        Rectangle newBounds = new Rectangle(x, y, label.getWidth(), label.getHeight());
        return !newBounds.intersects(obstacle.getBounds());
    }

    @Override
    public void keyPressed(KeyEvent e) {

        int x = label.getX();
        int y = label.getY();
        int step = 10;

        switch(e.getKeyCode()) {
            case KeyEvent.VK_W:
                if (isCollision(x, y - step)) {
                    System.out.println("You moved forwards!");
                    label.setLocation(x, y - step);
                }
                break;
            case KeyEvent.VK_A:
                if (isCollision(x - step, y)) {
                    label.setLocation(x - step, y);
                }
                break;
            case KeyEvent.VK_S:
                if (isCollision(x, y + step)) {
                    label.setLocation(x, y + step);
                }
                break;
            case KeyEvent.VK_D:
                if (isCollision(x + step, y)) {
                    label.setLocation(x + step, y);
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