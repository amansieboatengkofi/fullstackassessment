package com.pebblepost.todo;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class canvas1 extends JPanel{

    static Graphics2D g2;
    static canvas1 canvas1;
    static int xcursor=0;
    static int ycursor=0;
    static int headerHeight=20;

    static int windowWidth = 240;
    static int windowHeight = 400;
    static int letterWidth = 12;
    static int lettterHeight= 20;
    static int maxLineLetterCount = 20;
//    static String resStr = "12345678901234567890";
static String resStr = "";
    public void paint(Graphics g) {
        g2 = (Graphics2D)g;
        g2.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        //Font font = new Font("Serif", Font.PLAIN, 20);
        Font font = new Font("Courier", Font.PLAIN, 20);
        g2.setFont(font);
        format(g2, resStr);
        //g2.drawString(format(resStr), 0, 20);
        g2.fill3DRect(xcursor, ycursor, 1, 20,true);
    }

    public void format(Graphics2D g2, String resStr){
       int yindex= headerHeight;
        while(resStr.length()>maxLineLetterCount){

            String s= resStr.substring(0,maxLineLetterCount);
            resStr=resStr.substring(maxLineLetterCount);
            g2.drawString(s, 0, yindex);
            yindex+=lettterHeight;
            System.out.println(s);
        }
        g2.drawString(resStr, 0, yindex);

        ////g2.drawString(sb.toString(), 0, 20);
    }

    public static int getIndex(int xcursor, int ycursor){
        ycursor = ycursor - headerHeight;
        int lines = ycursor/20;
        System.out.println("lines is " +lines);
        int res = (lines+1)* maxLineLetterCount + xcursor/letterWidth;
        System.out.println("index is " +res);
        return res;
    }
    public static void main(String[] args) {
        JFrame f = new JFrame();
        canvas1  = new canvas1();
        f.getContentPane().add(canvas1);
        f.setSize(windowWidth, windowHeight);
        f.setVisible(true);
        f.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                System.out.println("pressed");
                if(g2 ==null){
                    System.out.println("g2 is null");
                }

                else{

                      if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
                          System.out.println("xcursor before is "+ xcursor);
                        int index = getIndex(xcursor,ycursor);
                        System.out.println("backspace called");
                        System.out.println("index for delete is "+ index);
                        String before = resStr.substring(0,index-1);
                        String after =  resStr.substring(index);
                        resStr = before + after;
                        System.out.println("resStr is "+ resStr);


                          if(xcursor-letterWidth<0){
                              ycursor = Math.max(headerHeight,ycursor-lettterHeight);
                              System.out.println("ycursor changed");
                          }

                          xcursor = (xcursor-letterWidth)%windowWidth;
                          System.out.println("xcursor after is "+ xcursor);
                          canvas1.repaint();
                          return;

                    }
                    //g2.drawString("updated",0,0);
                    if(e.isActionKey() ){
                        System.out.println("isAction");

                        int keyCode = e.getKeyCode();
                        if (keyCode == KeyEvent.VK_UP) {
                            ycursor = Math.max(0,ycursor- lettterHeight);
                            System.out.println("Up Arrrow-Key is pressed!");
                            canvas1.repaint();
                        }
                        else if (keyCode == KeyEvent.VK_DOWN) {
                            ycursor = ycursor+ lettterHeight;
                            System.out.println("Down Arrrow-Key is pressed!");
                            canvas1.repaint();
                        }
                        else if (keyCode == KeyEvent.VK_LEFT) {
                            if(xcursor- letterWidth <0){
                                ycursor = Math.min(headerHeight,ycursor - lettterHeight);
                            }
                            //xcursor = Math.max(0,xcursor- letterWidth);
                            xcursor = (windowWidth+xcursor- letterWidth)%windowWidth;
                            System.out.println("Left Arrrow-Key is pressed!");
                            canvas1.repaint();
                        }
                        else if (keyCode == KeyEvent.VK_RIGHT) {
                            if(xcursor+ letterWidth >=windowWidth){
                                ycursor=ycursor+ lettterHeight;
                            }
                            xcursor = (xcursor+ letterWidth ) %windowWidth ;
                            System.out.println("Right Arrrow-Key is pressed!");
                            canvas1.repaint();
                        }
                    }
                    else{

                        System.out.println("isnotaction");
                        //fires a unicode character
                        f.getContentPane().remove(canvas1);
                        canvas1 = new canvas1();
                        f.getContentPane().add(canvas1);
                        f.getContentPane().revalidate();

                        // get the index where we should insert the character
                        int index = getIndex(xcursor,ycursor);
                        String before = resStr.substring(0,index);
                        String after =  e.getKeyChar()+resStr.substring(index);
                        resStr = before + after;

//                        resStr+=" modified";

                        if(xcursor+letterWidth>=windowWidth){
                            ycursor = ycursor+lettterHeight;
                            System.out.println("ycursor changed");
                        }
                        xcursor = (xcursor+letterWidth)%windowWidth;
                        canvas1.repaint();

                        //f.setSize(400, 400);
                        f.setVisible(true);
                    }

                }
            }
        });

        f.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                //On Click:
                System.out.println("x is "+ e.getX());
                System.out.println("y is "+ e.getY());
                xcursor = (e.getX()/letterWidth)*letterWidth;
                ycursor = (e.getY()/20)*20 -headerHeight;
                canvas1.repaint();

            }
        });
    }
}
