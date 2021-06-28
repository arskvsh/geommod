package bivt183.arskvsh.pr2;

import com.sun.opengl.util.Animator;
import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Random;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCanvas;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;
import javax.media.opengl.glu.GLUquadric;



/**
 * PR1.java <BR>
 * author: Brian Paul (converted to Java by Ron Cemer and Sven Goethel) <P>
 *
 * This version is equal to Brian Paul's version 1.2 1999/10/21
 */
public class Task8 implements GLEventListener {
    GLU glu = new GLU();
    
    public static void main(String[] args) {
        Frame frame = new Frame("PR2 - Zadanie 6");
        GLCanvas canvas = new GLCanvas();

        canvas.addGLEventListener(new Task8());
        frame.add(canvas);
        frame.setSize(800, 800);
        frame.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                // Run this on another thread than the AWT event queue to
                // make sure the call to Animator.stop() completes before
                // exiting
                new Thread(new Runnable() {

                    public void run() {
                        System.exit(0);
                    }
                }).start();
            }
        });
        // Center frame
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
   

    public void init(GLAutoDrawable drawable) {
        // Use debug pipeline
        // drawable.setGL(new DebugGL(drawable.getGL()));
        
        GL gl = drawable.getGL();
        System.err.println("INIT GL IS: " + gl.getClass().getName());

        // Enable VSync
        gl.setSwapInterval(1);

        // Setup the drawing area and shading mode
        gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        gl.glShadeModel(GL.GL_SMOOTH); // try setting this to GL_FLAT and see what happens.
    }
    
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        GL gl = drawable.getGL();
        GLU glu = new GLU();
    }

    public void display(GLAutoDrawable drawable) {
        GL gl = drawable.getGL();
        GLU glu = new GLU();
        
        gl.glClearColor(0,0,0,0);
        
        gl.glClear(GL.GL_COLOR_BUFFER_BIT);
        gl.glClear(GL.GL_DEPTH_BUFFER_BIT);
        gl.glEnable(GL.GL_DEPTH_TEST);
        
        gl.glPolygonMode(GL.GL_FRONT_AND_BACK, GL.GL_FILL);
        
        gl.glLoadIdentity();
        
        gl.glOrtho(-1,1,-1,1,5,-5);
        
        gl.glRotated(-120, 1, 0, 0);
        gl.glRotated(35, 0, 0, 1);
        
        GLUquadric q;
        
        gl.glTranslated(-0.35, 0, -0.5);
        
        //основание
        q = glu.gluNewQuadric();
       
        drawSide(gl, q);
        
        gl.glLoadIdentity();
        gl.glRotated(-120, 1, 0, 0);
        gl.glRotated(35, 0, 0, 1);
        
        gl.glRotated(180, 0, 0, 1);
        gl.glTranslated(-0.35, 0, -0.5);
                
        drawSide(gl, q);
        
        gl.glColor3d(0.85, 0.85, 0.85);
        gl.glPushMatrix();
        gl.glTranslated(0, 0.1, 0.72);
        gl.glScaled(7, 0.1, 0.1);
        gl.glRotated(90, 0, 1, 0);
        glu.gluCylinder(q, 0.1f, 0.1f, 0.1f, 16, 1);
        gl.glPopMatrix();
    }

    void drawSide(GL gl, GLUquadric q) {
        gl.glColor3d(0.1, 0.1, 0.8);
        gl.glPushMatrix();
        gl.glScaled(1.1, 4, 0.2);
        gl.glRotated(45, 0, 0, 1);
        glu.gluCylinder(q, 0.1f, 0.1f, 0.1f, 4, 1);
        gl.glPopMatrix();
        
        gl.glColor3d(0.1, 0.1, 0.8);
        gl.glPushMatrix();
        gl.glTranslated(0, 0, 0.02);
        gl.glScaled(1.1, 4, 0.2);
        gl.glRotated(45, 0, 0, 1);
        glu.gluDisk(q, 0, 0.1f, 4, 1);
        gl.glPopMatrix();
        
        //опора
        gl.glColor3d(0.1, 0.1, 0.9);
        gl.glPushMatrix();
        gl.glScaled(0.9, 1, 0.9);
        gl.glRotated(45, 0, 0, 1);
        glu.gluCylinder(q, 0.1f, 0.1f, 1f, 4, 1);
        gl.glPopMatrix();
        
        gl.glColor3d(0.9, 0.9, 0.9);
        //балка для педалей
        gl.glPushMatrix();
        gl.glTranslated(0.1, 0, 0.1);
        gl.glScaled(0.3, 0.4, 0.6);
        gl.glRotated(45, 0, 0, 1);
        glu.gluCylinder(q, 0.1f, 0.1f, 1f, 4, 1);
        gl.glPopMatrix();
        
        //крепление балки
        gl.glColor3d(0.85, 0.85, 0.85);
        gl.glPushMatrix();
        gl.glTranslated(0.065, 0, 0.7);
        gl.glScaled(0.06, 0.4, 0.4);
        gl.glRotated(90, 0, 1, 0);
        glu.gluCylinder(q, 0.1f, 0.1f, 1f, 16, 1);
        gl.glPopMatrix();
        
        gl.glColor3d(0.2, 0.2, 0.2);
        
        gl.glPushMatrix();
        gl.glTranslated(0.13, 0, 0.7);
        gl.glScaled(0.065, 0.3, 0.3);
        gl.glRotated(90, 0, 1, 0);
        glu.gluDisk(q, 0, 0.1f, 16, 1);
        gl.glPopMatrix();
        
        gl.glColor3d(0.9, 0.9, 0.9);
        //крепление педали
        gl.glPushMatrix();
        gl.glTranslated(0.135, 0, 0.1);
        gl.glScaled(0.8, 1, 0.03);
        gl.glRotated(45, 0, 0, 1);
        glu.gluCylinder(q, 0.1f, 0.1f, 1f, 4, 1);
        glu.gluDisk(q, 0, 0.1f, 4, 1);
        gl.glPopMatrix();
        
        gl.glColor3d(0.2, 0.2, 0.2);
        gl.glPushMatrix();
        gl.glTranslated(0.21, 0, 0.13);
        gl.glScaled(0.9, 1.7, 0.03);
        gl.glRotated(45, 0, 0, 1);
        glu.gluCylinder(q, 0.1f, 0.1f, 1f, 4, 1);
        gl.glColor3d(0.17, 0.17, 0.17);
        glu.gluDisk(q, 0, 0.1f, 4, 1);
        gl.glPopMatrix();
    }
    
    public void displayChanged(GLAutoDrawable drawable, boolean modeChanged, boolean deviceChanged) {
    }
}

