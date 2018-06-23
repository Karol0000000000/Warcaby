package com.java.warcaby;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;
import java.io.IOException;
import java.util.ArrayList;

public class Gra extends JFrame{

    static int greyMovement = 0;
    static int whiteMovement = 0;
    static int greyMovementStar = 0;
    static int whiteMovementStar = 0;
    static int locationXOnScreenStart = 0;
    static int locationXOnScreenEnd = 0;
    static int locationYOnScreenStart = 0;
    static int locationYOnScreenEnd = 0;

    public Gra(){

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        GridLayout layout = new GridLayout(9,9);
        setLayout(layout);
        setLocation(250,0);
        final JButton buttons[] = new JButton[81];
        setSize(720,720);
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Pomoc");
        JMenuItem info = new JMenuItem("O programie");
        menu.add(info);
        info.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(getContentPane(),"Pojedyncze klikniecie lewym - wybor pionka\n\n" +
                        "kolejne klikniecie lewym - wybor miejsca docelowego\n\n" +
                        "pojedyncze klikniecie prawym - usuniecie pionka\n\n" +
                        "przeciagniecie w prawo - dodanie uprzywilejowanego pionka bialego\n\n" +
                        "przeciagniecie w lewo - dodanie uprzywilejowanego pionka szarego\n\n" +
                        "przeciagniecie w dol - dodanie zwykłego pionka białego\n\n" +
                        "przeciagniecie w gore - dodanie zwykłego pionka szarego","O programie",JOptionPane.INFORMATION_MESSAGE);
            }
        });
        menuBar.add(menu);
        setJMenuBar(menuBar);
        int k = -1;
        int i,j;
        char u = (char)73;
        final ImageIcon iconGrey = new ImageIcon(this.getClass().getResource("/pionekSzary.png"));
        final ImageIcon iconWhite = new ImageIcon(this.getClass().getResource("/pionekBialy.png"));
        final ImageIcon iconWhiteStar = new ImageIcon(this.getClass().getResource("/pionekBialyGwiazdka.png"));
        final ImageIcon iconGreyStar = new ImageIcon(this.getClass().getResource("/pionekSzaryGwiazdka.png"));
        final ImageIcon iconApplication = new ImageIcon(this.getClass().getResource("/warcaby.png"));
        setIconImage(Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("/warcaby.png")));

        for(int y=0; y<9; y++) {
            for (int z = 0; z < 9; z++) {
                k++;

                if(y == 0 && z == 0){
                    buttons[k] = new JButton();
                    buttons[k].setName("cos");
                    add(buttons[k]);
                }else if(y == 0){
                    u--;
                    buttons[k] = new JButton(String.valueOf(u));
                    buttons[k].setSize(90,90);
                    add(buttons[k]);
                }else if(z == 0){
                    buttons[k] = new JButton(String.valueOf(y));
                    buttons[k].setSize(90,90);
                    add(buttons[k]);
                }
                else {
                    i = z-1;
                    j = y -1;
                    buttons[k] = new JButton();
                    final int finalY = z;
                    final int finalZ = y*9;
                    final int finalK = k;

                    buttons[k].addMouseMotionListener(new MouseMotionListener() {
                        @Override
                        public void mouseDragged(MouseEvent e) {
                            locationXOnScreenEnd = e.getXOnScreen();
                            locationYOnScreenEnd = e.getYOnScreen();
                            JButton button3 = (JButton) e.getSource();
                            if(locationXOnScreenEnd -80 > locationXOnScreenStart){
                                //System.out.println("prawo");
                                button3.setName("black_white_star");
                                button3.setIcon(iconWhiteStar);
                            }else if(locationXOnScreenEnd +80 < locationXOnScreenStart){
                                //System.out.println("lewo");
                                button3.setName("black_grey_star");
                                button3.setIcon(iconGreyStar);
                            }
                            if(locationYOnScreenEnd -80 > locationYOnScreenStart){
                                //System.out.println("dol");
                                button3.setName("black_white");
                                button3.setIcon(iconWhite);
                            }else if(locationYOnScreenEnd +80 < locationYOnScreenStart){
                                //System.out.println("gora");
                                button3.setName("black_grey");
                                button3.setIcon(iconGrey);
                            }
                        }

                        @Override
                        public void mouseMoved(MouseEvent e) {

                        }
                    });

                    buttons[k].addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                            if(e.getButton() == MouseEvent.BUTTON3){
                                JButton button2 = (JButton) e.getSource();
                                button2.setName("black");
                                button2.setIcon(null);
                            }
                            else if(e.getButton() == MouseEvent.BUTTON1)
                            {
                                JButton button1 = (JButton) e.getSource();
                                if (button1.getName().equals("white")) {
                                } else {
                                    if (greyMovement == 1) {
                                        button1.setName("black_grey");
                                        button1.setIcon(iconGrey);
                                        greyMovement = 0;
                                    } else if (whiteMovement == 1) {
                                        button1.setName("black_white");
                                        button1.setIcon(iconWhite);
                                        whiteMovement = 0;
                                    } else if (whiteMovementStar == 1) {
                                        button1.setName("black_white_star");
                                        button1.setIcon(iconWhiteStar);
                                        whiteMovementStar = 0;
                                    } else if (greyMovementStar == 1) {
                                        button1.setName("black_grey_star");
                                        button1.setIcon(iconGreyStar);
                                        greyMovementStar = 0;
                                    }
                                    else {
                                        if (button1.getName().equals("black_white")) {
                                            whiteMovement = 1;
                                            button1.setName("black");
                                            button1.setIcon(null);
                                        } else if (button1.getName().equals("black_grey")) {
                                            greyMovement = 1;
                                            button1.setName("black");
                                            button1.setIcon(null);
                                        } else if (button1.getName().equals("black_grey_star")) {
                                            greyMovementStar = 1;
                                            button1.setName("black");
                                            button1.setIcon(null);
                                        } else if (button1.getName().equals("black_white_star")) {
                                            whiteMovementStar = 1;
                                            button1.setName("black");
                                            button1.setIcon(null);
                                        }
                                    }
                                }
                            }
                        }
                        @Override
                        public void mousePressed(MouseEvent e) {
                            locationXOnScreenStart = e.getXOnScreen();
                            locationYOnScreenStart = e.getYOnScreen();
                        }
                    });

                    buttons[k].setSize(90, 90);
                    add(buttons[k]);

                    if (j % 2 == 0) {
                        if (i % 2 == 0) {
                            buttons[k].setBackground(Color.WHITE);
                            buttons[k].setName("white");
                        } else {
                            buttons[k].setBackground(Color.BLACK);
                            buttons[k].setName("black");
                            if (j == 5 || j == 6 || j == 7) {
                                buttons[k].setIcon(iconWhite);
                                buttons[k].setName("black_white");
                            } else if (j == 0 || j == 1 || j == 2) {
                                buttons[k].setIcon(iconGrey);
                                buttons[k].setName("black_grey");
                            }
                        }
                    } else {
                        if (i % 2 == 0) {
                            buttons[k].setBackground(Color.BLACK);
                            buttons[k].setName("black");

                            if (j == 0 || j == 1 || j == 2) {
                                buttons[k].setIcon(iconGrey);
                                buttons[k].setName("black_grey");
                            } else if (j == 5 || j == 6 || j == 7) {
                                buttons[k].setIcon(iconWhite);
                                buttons[k].setName("black_white");
                            }
                        } else {
                            buttons[k].setBackground(Color.WHITE);
                            buttons[k].setName("white");
                        }
                    }
                }

            }
        }
        setVisible(true);
    }

    public static void main(String[] args){
        new Gra();
    }


}
