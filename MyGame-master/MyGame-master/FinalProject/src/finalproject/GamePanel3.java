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
public class GamePanel3 extends JPanel {
    
    private Bird bird;
    private ArrayList<Rectangle> rects;
    private FlappyBird3 fb3;
    private Font scoreFont, pauseFont;
    public static final Color bg = new Color(65,179,247);
    public static final int PIPE_W = 50, PIPE_H = 30;
    private Image pipeHead, pipeLength;

    public GamePanel3(FlappyBird3 fb3, Bird bird, ArrayList<Rectangle> rects) {
        this.fb3 = fb3;
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
        g.fillRect(0,0,FlappyBird3.WIDTH,FlappyBird3.HEIGHT);
        bird.update(g);
        g.setColor(Color.RED);
        for(Rectangle r : rects) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setColor(Color.GREEN);
            //g2d.fillRect(r.x, r.y, r.width, r.height);
            AffineTransform old = g2d.getTransform();
            g2d.translate(r.x+PIPE_W/2, r.y+PIPE_H/2);
            if(r.y < FlappyBird3.HEIGHT/2) {
                g2d.translate(0, r.height);
                g2d.rotate(Math.PI);
            }
            g2d.drawImage(pipeHead, -PIPE_W/2, -PIPE_H/2, GamePanel3.PIPE_W, GamePanel3.PIPE_H, null);
            g2d.drawImage(pipeLength, -PIPE_W/2, PIPE_H/2, GamePanel3.PIPE_W, r.height, null);
            g2d.setTransform(old);
        }
        g.setFont(scoreFont);
        g.setColor(Color.BLACK);
        g.drawString("Score: "+fb3.getScore(), 10, 20);
        
        if(fb3.paused()) {
            g.setFont(pauseFont);
            g.setColor(new Color(0,0,0,170));
            g.drawString("PAUSED", FlappyBird3.WIDTH/2-100, FlappyBird3.HEIGHT/2-100);
            g.drawString("PRESS SPACE TO BEGIN", FlappyBird3.WIDTH/2-300, FlappyBird3.HEIGHT/2+50);
        }
    }
}
