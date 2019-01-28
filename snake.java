package snake;

import java.awt.*;
import java.awt.event.*;
import java.util.LinkedList;
import javax.swing.*;

class Apple extends Snake {

    static int x = 15;
    static int y = 10;

    public Apple() {
    }

    public static void replace() {
        ++count;
        do {
            int xx = (int) Math.floor((Math.random() * 22 + 4));
            int yy = (int) Math.floor(Math.random() * 12 + 4);
            x = xx;
            y = yy;
        } while (matrix[y][x] != 0);

    }

}

class CustomKeyListener extends Snake implements KeyListener {

    public void keyTyped(KeyEvent e) {
    }

    public void keyPressed(KeyEvent e) {

        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
                kpd = "U";
                break;
            case KeyEvent.VK_DOWN:
                kpd = "D";
                break;
            case KeyEvent.VK_LEFT:
                kpd = "L";
                break;
            case KeyEvent.VK_RIGHT:
                kpd = "R";
                break;
        }
    }

    public void keyReleased(KeyEvent e) {
    }
}

public class Snake extends JComponent {

    public static long time_elaps = System.currentTimeMillis();
    public static byte[][] matrix = new byte[20][30];
    public static int count = 0;
    public static String kpd = "R";
    public static boolean gameover = false;
    static final int MATRIX_WIDTH = 30;
    static final int MATRIX_HEIGHT = 20;
    static final int PREF_W = 900;
    static final int PREF_H = 700;
    static final int TIMER_DELAY = 500;
    public int rectX = 30;
    public int rectY = 30;
    public int width = 29;
    public int height = 29;
    private static LinkedList<Integer> lili_Y = new LinkedList();
    private static LinkedList<Integer> lili_X = new LinkedList();
    private static int head_X = 8;
    private static int head_Y = 8;

    public Snake() {

        //magic number 2 millisecs
        new Timer(TIMER_DELAY, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent actEvt) {

                long timer = System.currentTimeMillis();
                if (timer - time_elaps < TIMER_DELAY >> 1) {
                    return;
                }
                time_elaps = System.currentTimeMillis();

                if (gameover) {
                    for (int i = 0; i < MATRIX_HEIGHT; ++i) {
                        matrix[i][i] = 2;
                        matrix[i][MATRIX_HEIGHT - i - 1] = 2;
                    }
                    repaint();
                    return;
                }
                matrix[Apple.y][Apple.x] = 3;

                switch (kpd) {
                    case "U":
                        head_Y--;
                        break;
                    case "D":
                        head_Y++;
                        break;
                    case "L":
                        head_X--;
                        break;
                    case "R":
                        head_X++;
                        break;

                }

                if (matrix[head_Y][head_X] == 1) {
                    gameover = true;
                }

                lili_X.add(head_X);
                lili_Y.add(head_Y);

                int size = lili_Y.size();
                for (int i = 0; i < size; ++i) {

                    int y = lili_Y.get(i);
                    int x = lili_X.get(i);
                    if (i == size - 1) {
                        matrix[y][x] = 2;
                    } else {
                        matrix[y][x] = 1;
                    }
                }
                int tail_X = lili_X.get(0);
                int tail_Y = lili_Y.get(0);
                if (head_X == Apple.x && head_Y == Apple.y) {
                    Apple.replace();
                    System.out.println("SNAKE SIZE: " + count);
                } else {
                    lili_X.removeFirst();
                    lili_Y.removeFirst();
                }

                matrix[tail_Y][tail_X] = 0;

                if (count < 400) {

                    repaint();
                } else {
                    ((Timer) actEvt.getSource()).stop();
                }
            }
        }).start();
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(PREF_W, PREF_H);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (int y = 0; y < MATRIX_HEIGHT; ++y) {
            for (int x = 0; x < MATRIX_WIDTH; ++x) {
                if (matrix[y][x] == 1) {
                    g.setColor(Color.red);
                } else if (matrix[y][x] == 2) {
                    g.setColor(Color.yellow);
                } else if (matrix[y][x] == 3) {
                    g.setColor(Color.green);
                } else {
                    g.setColor(Color.black);
                }
                g.drawRect(rectX * x, rectY * y, width, height);
                g.fillRect(rectX * x, rectY * y, width, height);

            }
        }

    }

    public int getRectX() {
        return rectX;
    }

    public void setRectX(int rectX) {
        this.rectX = rectX;
    }

    public int getRectY() {
        return rectY;
    }

    public void setRectY(int rectY) {
        this.rectY = rectY;
    }

    private static void createAndShowGui() {
        Snake mainPanel = new Snake();

        JFrame frame = new JFrame("Snake");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(mainPanel);
        frame.pack();
        frame.setLocationByPlatform(true);
        frame.setVisible(true);
        frame.addKeyListener(new CustomKeyListener());
    }

    public static void main(String[] args) {

        //setup board
        for (int y = 0; y < MATRIX_HEIGHT; ++y) {
            for (int x = 0; x < MATRIX_WIDTH; ++x) {
                if (y == 0 || y == MATRIX_HEIGHT - 1
                        || x == 0 || x == MATRIX_WIDTH - 1) {
                    matrix[y][x] = 1;
                }
                //place random obstables
                int temp = (int) Math.floor(Math.random() * 14);
                if (temp == 1 && y < MATRIX_HEIGHT / 3 || y > MATRIX_HEIGHT * 0.66 && temp == 1) {
                    matrix[y][x] = 1;
                }

            }
        }
        //setup snake
        for (int i = 3; i <= head_X; ++i) {
            lili_Y.add(head_Y);
            lili_X.add(i);
        }

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGui();
            }
        });
    }

}
