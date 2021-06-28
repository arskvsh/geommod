package bivt183.arskvsh.pr2;

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
public class Task6 implements GLEventListener {

    public static void main(String[] args) {
        Frame frame = new Frame("PR2 - Zadanie 6");
        GLCanvas canvas = new GLCanvas();

        canvas.addGLEventListener(new Task6());
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
        
        gl.glClearColor(0.2f,0.2f,0.2f,0);
        
        gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_TEST);
        
        gl.glEnable(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_TEST);
        //gl.glPolygonMode(GL.GL_FRONT_AND_BACK, GL.GL_LINE);
        
        gl.glLoadIdentity();
        gl.glRotated(-120, 1, 0, 0);
        gl.glRotated(90+35, 0, 0, 1);
        
        double thickness = 0.5, R, alpha, da, x, y;
        int n=36;
        da = 2*Math.PI/n;
        double length = 0.8;
        int unitSegments = 20;
        R = 0.025;
        
        gl.glColor3d(.3, .3, .3);
        gl.glLineWidth(1);
        for (int i = 0; i < unitSegments+1; i++)
        {
            gl.glBegin(GL.GL_LINES);
                gl.glVertex3d(length, length*2/unitSegments*(i-unitSegments/2), 0);
                gl.glVertex3d(-length, length*2/unitSegments*(i-unitSegments/2), 0);
                gl.glVertex3d(length*2/unitSegments*(i-unitSegments/2), length, 0);
                gl.glVertex3d(length*2/unitSegments*(i-unitSegments/2), -length, 0);
            gl.glEnd();
        }
        gl.glLineWidth(3);
        gl.glColor3d(1, 0, 0);
        drawAxis(gl, length, unitSegments, R);
        gl.glRotated(-90, 0, 0, 1);
        gl.glColor3d(0, 1, 0);
        drawAxis(gl, length, unitSegments, R);
        gl.glRotated(-90, 0, 1, 0);
        gl.glColor3d(.1, .3, .9);
        drawAxis(gl, length, unitSegments, R);
        
        gl.glLoadIdentity();
        gl.glColor3d(1, 0, 0);
        drawLetter(gl, 'x', -0.5, -0.47, .03);
        gl.glColor3d(0, 1, 0);
        drawLetter(gl, 'y', 0.75, -0.35, .03);
        gl.glColor3d(.1, .3, .9);
        drawLetter(gl, 'z', 0.09, 0.75, .03);
        // Flush all drawing operations to the graphics card
        gl.glFlush();
    }
    
    void drawAxis(GL gl, double length, int unitSegments, double R) {
        gl.glBegin(GL.GL_LINES);
            gl.glVertex3d(length, 0, 0);
            gl.glVertex3d(-length, 0, 0);
        gl.glEnd();
        
        for (int i = 0; i < unitSegments-1; i++)
        {
            gl.glBegin(GL.GL_LINES);
                if(i != unitSegments/2 - 1) {
                    gl.glVertex3d((length*2)/unitSegments*(i-unitSegments/2+1), 0.02, 0);
                    gl.glVertex3d((length*2)/unitSegments*(i-unitSegments/2+1), -0.02, 0); 
                }
            gl.glEnd();
        }
        
        double alpha, da, x, y;
        da = 2*Math.PI/16;
        
        gl.glBegin(GL.GL_TRIANGLE_FAN);
            alpha = 0;
            gl.glVertex3d(length+0.2, 0, 0);
             while(alpha<=Math.PI*2+da) 
             {
                x = R*Math.cos(alpha);
                y = R*Math.sin(alpha);
                gl.glVertex3d(length, x, y);
                alpha += da;
            }
        gl.glEnd();
    }
    
    void drawLetter(GL gl, char letter, double xPos, double yPos, double scale) {
        gl.glBegin(GL.GL_LINES);
        switch (letter) {
            case ('x'):
                gl.glVertex3d(-scale+xPos, scale+yPos, 0);
                gl.glVertex3d(scale+xPos, -scale+yPos, 0);
                gl.glVertex3d(-scale+xPos, -scale+yPos, 0);
                gl.glVertex3d(scale+xPos, scale+yPos, 0);
                break;
            case ('y'):
                gl.glVertex3d(scale+xPos, scale+yPos, 0);
                gl.glVertex3d(-scale+xPos, -scale+yPos, 0);
                gl.glVertex3d(-scale+xPos, scale+yPos, 0);
                gl.glVertex3d(xPos, yPos, 0);
                break;
            case ('z'):
                gl.glVertex3d(scale+xPos, scale+yPos, 0);
                gl.glVertex3d(-scale+xPos, scale+yPos, 0);
                gl.glVertex3d(-scale+xPos, -scale+yPos, 0);
                gl.glVertex3d(scale+xPos, scale+yPos, 0);
                gl.glVertex3d(scale+xPos, -scale+yPos, 0);
                gl.glVertex3d(-scale+xPos, -scale+yPos, 0);
                break;
        }
        gl.glEnd();
    }
    public void displayChanged(GLAutoDrawable drawable, boolean modeChanged, boolean deviceChanged) {
    }
}

