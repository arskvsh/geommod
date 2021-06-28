package bivt183.arskvsh.pr1;

import com.sun.opengl.util.Animator;
import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCanvas;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;



/**
 * PR1.java <BR>
 * author: Brian Paul (converted to Java by Ron Cemer and Sven Goethel) <P>
 *
 * This version is equal to Brian Paul's version 1.2 1999/10/21
 */
public class Task1 implements GLEventListener {

    public static void main(String[] args) {
        Frame frame = new Frame("PR1 - Zadanie 1");
        GLCanvas canvas = new GLCanvas();

        canvas.addGLEventListener(new Task1());
        frame.add(canvas);
        frame.setSize(500, 500);
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
               
        gl.glClearColor(0,0,0,0);
        gl.glClear(GL.GL_COLOR_BUFFER_BIT);
        
        double thickness = 0.5, R, alpha, da, x, y;
        int n=36;
        da = 2*Math.PI/n;
        alpha = 0;
        
        gl.glColor3d(1, 0.31, 0);
        gl.glBegin(GL.GL_QUAD_STRIP);
             while(alpha<=Math.PI*2+da) {
                R = 1;
                x = R*Math.cos(alpha);
                y = R*Math.sin(alpha);
                gl.glVertex2d(x, y);
                R = thickness;
                x = R*Math.cos(alpha);
                y = R*Math.sin(alpha);
                gl.glVertex2d(x, y);
                alpha += da;
            }
        gl.glEnd();
        
        gl.glColor3d(1, 1, 1);
        gl.glPointSize(3);
        
        int ptsQ = 500;
        gl.glBegin(GL.GL_POINTS);
            for (int i = 0; i < ptsQ; i++) {
                alpha += Math.random();
                R = (1-thickness)+Math.random()*thickness;
                x = R*Math.cos(alpha);
                y = R*Math.sin(alpha);
                gl.glVertex2d(x, y);
            }
        gl.glEnd();

        // Flush all drawing operations to the graphics card
        gl.glFlush();
    }

    public void displayChanged(GLAutoDrawable drawable, boolean modeChanged, boolean deviceChanged) {
    }
}

