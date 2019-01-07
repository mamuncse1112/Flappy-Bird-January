/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package finalproject;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 *
 * @author User
 */
public class GamePanel1 extends JPanel {
    
    private Bird bird;
    private ArrayList<Rectangle> rects;
    private FlappyBird1 fb1;
    private Font scoreFont, pauseFont;
    public static final Color bg = new Color(65,179,247);
    public static final int PIPE_W = 50, PIPE_H = 30;
    private Image pipeHead, pipeLength;

    public GamePanel1(FlappyBird1 fb1, Bird bird, ArrayList<Rectangle> rects) {
        this.fb1 = fb1;
        this.bird = bird;
        this.rects = rects;
        scoreFont = new Font("Comic Sans MS", Font.BOLD, 18);
        pauseFont = new Font("Arial", Font.BOLD, 48);
        
        try {
                String workingDir = System.getProperty("user.dir");
        	pipeHead = ImageIO.read(new File(workingDir+"\\src\\finalproject\\78.png"));
        	pipeLength = ImageIO.read(new File(workingDir+"\\src\\finalproject\\pipe_part.png"));
        }
        catch(IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void paintComponent(Graphics g) {
        g.setColor(bg);
        g.fillRect(0,0,FlappyBird1.WIDTH,FlappyBird1.HEIGHT);
        bird.update(g);
        g.setColor(Color.RED);
        for(Rectangle r : rects) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setColor(Color.GREEN);
            //g2d.fillRect(r.x, r.y, r.width, r.height);
            AffineTransform old = g2d.getTransform();
            g2d.translate(r.x+PIPE_W/2, r.y+PIPE_H/2);
            if(r.y < FlappyBird.HEIGHT/2) {
                g2d.translate(0, r.height);
                g2d.rotate(Math.PI);
            }
            g2d.drawImage(pipeHead, -PIPE_W/2, -PIPE_H/2, GamePanel.PIPE_W, GamePanel.PIPE_H, null);
            g2d.drawImage(pipeLength, -PIPE_W/2, PIPE_H/2, GamePanel.PIPE_W, r.height, null);
            g2d.setTransform(old);
        }
        g.setFont(scoreFont);
        g.setColor(Color.BLACK);
        g.drawString("Score: "+fb1.getScore(), 10, 20);
        
        if(fb1.paused()) {
            g.setFont(pauseFont);
            g.setColor(new Color(0,0,0,170));
            g.drawString("PAUSED", FlappyBird1.WIDTH/2-100, FlappyBird1.HEIGHT/2-100);
            g.drawString("PRESS SPACE TO BEGIN", FlappyBird1.WIDTH/2-300, FlappyBird1.HEIGHT/2+50);
        }
    }
}
