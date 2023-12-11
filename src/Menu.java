import Texture.TextureReader;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCanvas;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;
import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

@SuppressWarnings("InfiniteLoopStatement")
public class Menu extends JFrame {
    public static void main(String[] args) throws UnsupportedAudioFileException,
            IOException, LineUnavailableException, InterruptedException {
        //Put the mazes in place.
        Variables.easy.add(Variables.Maze1);
        Variables.easy.add(Variables.Maze2);
        Variables.easy.add(Variables.Maze3);
        Variables.normal.add(Variables.Maze4);
        Variables.normal.add(Variables.Maze5);
        Variables.normal.add(Variables.Maze6);
        Variables.hard.add(Variables.Maze7);
        Variables.hard.add(Variables.Maze8);
        Variables.hard.add(Variables.Maze9);
        Variables.Mazes.add(Variables.easy);
        Variables.Mazes.add(Variables.normal);
        Variables.Mazes.add(Variables.hard);
        new Menu();
        Thread.sleep(4000); //Let the frame take time to bind so the song begins normally.
        Variables.clip.open(Variables.audioStream);
        Variables.clip.start();
        //loop the clip:
        while (true) {
            if (Variables.clip.getMicrosecondPosition() == Variables.clip.getMicrosecondLength()) {
                Variables.audioStream = AudioSystem.getAudioInputStream(Variables.file);
                Variables.clip = AudioSystem.getClip();
                Variables.clip.open(Variables.audioStream);
                Variables.clip.start();
            }
        }
    }

    Menu() {
        GLCanvas glcanvas = new GLCanvas();
        GLMenu listener = new GLMenu();
        glcanvas.addGLEventListener(listener);
        glcanvas.addMouseListener(listener);
        getContentPane().add(glcanvas, BorderLayout.CENTER);
        setTitle("Game Menu");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 1000);
        setLocationRelativeTo(null);
        setVisible(true);
        setFocusable(true);
        Variables.menu_Animator.add(glcanvas);
        Variables.menu_Animator.start();
        glcanvas.requestFocus();
    }
}

@SuppressWarnings({"ThrowablePrintedToSystemOut", "CallToPrintStackTrace"})
class GLMenu implements GLEventListener, MouseListener {


    //Textures:
    String[] textureNames = {"black-square-clipart-8.png", "R (1).png",
            "InShot_20231205_163202992.png", "InShot_20231205_163220184.png",
            "InShot_20231205_163234143.png", "InShot_20231205_163426471.png",
            "volume_318-757784.png", "mute-png-mute-icon-1600.png",
            "InShot_20231205_163350314.png", "InShot_20231205_163400478.png",
            "InShot_20231205_163410601.png", "InShot_20231208_113813061.png",
            "InShot_20231208_113826914.png", "InShot_20231208_113836297.png"};
    TextureReader.Texture[] texture = new TextureReader.Texture[textureNames.length];
    int[] textures = new int[textureNames.length];

    @Override
    public void init(GLAutoDrawable glAutoDrawable) {
        GL gl = glAutoDrawable.getGL();
        gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);

        gl.glEnable(GL.GL_TEXTURE_2D);
        gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA);
        gl.glGenTextures(textureNames.length, textures, 0);

        for (int i = 0; i < textureNames.length; i++) {
            try {
                texture[i] = TextureReader.readTexture("src" +
                        "//" + textureNames[i], true);
                gl.glBindTexture(GL.GL_TEXTURE_2D, textures[i]);
                new GLU().gluBuild2DMipmaps(
                        GL.GL_TEXTURE_2D,
                        GL.GL_RGBA,
                        texture[i].getWidth(), texture[i].getHeight(),
                        GL.GL_RGBA,
                        GL.GL_UNSIGNED_BYTE,
                        texture[i].getPixels()
                );
            } catch (IOException e) {
                System.out.println(e);
                e.printStackTrace();
            }
        }
    }

    @Override
    public void display(GLAutoDrawable glAutoDrawable) {
        GL gl = glAutoDrawable.getGL();
        gl.glClear(GL.GL_COLOR_BUFFER_BIT);
        gl.glLoadIdentity();
        //Background:
        drawAssets(gl, 0, 0, 0, 1.18);
        //Maze-Image:
        drawAssets(gl, 1, -0.3, 0, 0.5);
        if (!Variables.selected_Mode) {
            //Single
            drawAssets(gl, 2, 0.5, 0.3, 0.15);
            //Multi
            drawAssets(gl, 3, 0.5, 0.1, 0.15);
            //Vs AI
            drawAssets(gl, 4, 0.5, -0.1, 0.15);
        } else {
            //Easy
            drawAssets(gl, 8, 0.5, 0.3, 0.15);
            //Medium
            drawAssets(gl, 9, 0.5, 0.1, 0.15);
            //Hard
            drawAssets(gl, 10, 0.5, -0.1, 0.15);
        }
        //Instructions:
        drawAssets(gl, 5, 0.5, -0.3, 0.15);
        //HighScores ُEasy:
        drawAssets(gl, 11, -0.3, -0.7, 0.15);
        //HighScores ُNormal:
        drawAssets(gl, 12, 0, -0.7, 0.15);
        //HighScores ُHard:
        drawAssets(gl, 13, 0.3, -0.7, 0.15);
        //Sound:
        if (!Variables.muted) {
            //Unmuted:
            drawAssets(gl, 6, -0.5, 0.7, 0.1);
        } else {
            //Muted:
            drawAssets(gl, 7, -0.5, 0.7, 0.1);
        }
        //Game Began.
        if (Variables.levelSelection > -1) {
            Variables.menu_disabled = true;
            Variables.menu_Animator.stop();
        }
    }

    public void drawAssets(GL gl, int i, double x, double y, double scale) {
        gl.glEnable(GL.GL_BLEND);
        gl.glBindTexture(GL.GL_TEXTURE_2D, textures[i]);

        gl.glPushMatrix();
        gl.glTranslated(x, y, 0);
        gl.glScaled(scale, scale, 1);
        gl.glBegin(GL.GL_QUADS);
        gl.glTexCoord2f(0.0f, 0.0f);
        gl.glVertex3f(-1.0f, -1.0f, -1.0f);
        gl.glTexCoord2f(1.0f, 0.0f);
        gl.glVertex3f(1.0f, -1.0f, -1.0f);
        gl.glTexCoord2f(1.0f, 1.0f);
        gl.glVertex3f(1.0f, 1.0f, -1.0f);
        gl.glTexCoord2f(0.0f, 1.0f);
        gl.glVertex3f(-1.0f, 1.0f, -1.0f);
        gl.glEnd();
        gl.glPopMatrix();

        gl.glDisable(GL.GL_BLEND);
    }

    @Override
    public void reshape(GLAutoDrawable glAutoDrawable, int i, int i1, int i2, int i3) {

    }

    @Override
    public void displayChanged(GLAutoDrawable glAutoDrawable, boolean b, boolean b1) {

    }


    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
