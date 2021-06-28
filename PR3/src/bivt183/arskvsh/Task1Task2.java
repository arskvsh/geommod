package bivt183.arskvsh;

import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCanvas;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;
import java.nio.FloatBuffer;
import java.util.Random;

/**
 * PR3.java <BR>
 * author: Brian Paul (converted to Java by Ron Cemer and Sven Goethel) <P>
 *
 * This version is equal to Brian Paul's version 1.2 1999/10/21
 */
public class Task1Task2 implements GLEventListener {

    public static void main(String[] args) {
        Frame frame = new Frame("PR3 - Zadanie 1, 2");
        GLCanvas canvas = new GLCanvas();

        canvas.addGLEventListener(new Task1Task2());
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

        // Clear the drawing area
        gl.glClear(GL.GL_COLOR_BUFFER_BIT);

        // Reset the current matrix to the "identity"
        gl.glLoadIdentity();
        gl.glColor3d(1, 1, 1);
        
        //gl.glRotated(60, 0, 1, 0);
        
        float palette[] =
        {
            0, 0.65f, 0,
            0.8f, 0.65f, 0,
            0.8f, 0.25f, 0,
            0.8f, 0.15f, 0,
            0.7f, 0.1f, 0,
            0.55f, 0.05f, 0,
            0.55f, -0.02f, 0,
            0.56f, -0.14f, 0,
            0.7f, -0.1f, 0,
            0.95f, 0f, 0,
            0.78f, -0.3f, 0,
            0.6f, -0.55f, 0,
            0f, -0.55f, 0,
            -0.7f, -0.5f, 0,
            -0.7f, 0f, 0,
            -0.7f, 0.6f, 0,
            0f, 0.65f, 0,
            0f, 0f, 0,
        };
        
        float hole[] =
        {
            0.5f, 0.37f, 0,
            0.62f, 0.37f, 0,
            0.62f, 0.28f, 0,
            0.62f, 0.2f, 0,
            0.5f, 0.2f, 0,
            0.4f, 0.2f, 0,
            0.4f, 0.27f, 0,
            0.4f, 0.37f, 0,
            0.5f, 0.37f, 0,
            0f, 0f, 0,
        };
        
        float paint[] =
        {
            -0.6f, -0.6f, 0, -0.3f, -0.9f, 0,
            0.3f, -0.9f, 0, 0.6f, -0.6f, 0,
            
            -0.9f, -0.3f, 0, -0.2f, -0.2f, 0.5f,
            0.2f, -0.2f, 0.5f, 0.9f, -0.3f, 0,
            
            -0.9f, 0.3f, 0, -0.2f, 0.2f, 0.5f,
            0.2f, 0.2f, 0.5f, 0.9f, 0.3f, 0,
            
            -0.6f, 0.6f, 0, -0.3f, 0.9f, 0,
            0.3f, 0.9f, 0, 0.6f, 0.6f, 0,
        };
        
        drawSpline(gl, palette);
        drawSpline(gl, hole);
        

        gl.glDisable(GL.GL_MAP1_VERTEX_3);
        
        gl.glPushMatrix();
        double paintSize = 0.14;
        gl.glTranslated(0.3, -0.32, 0);
        gl.glScaled(paintSize, paintSize, paintSize);
        drawSurface(gl, paint);
        gl.glPopMatrix();
        gl.glPushMatrix();
        gl.glTranslated(0, -0.35, 0);
        gl.glScaled(paintSize, paintSize, paintSize);
        drawSurface(gl, paint);
        gl.glPopMatrix();
        gl.glPushMatrix();
        gl.glTranslated(-0.3, -0.29, 0);
        gl.glScaled(paintSize, paintSize, paintSize);
        drawSurface(gl, paint);
        gl.glPopMatrix();
        gl.glPushMatrix();
        gl.glTranslated(-0.5, -0.05, 0);
        gl.glScaled(paintSize, paintSize, paintSize);
        drawSurface(gl, paint);
        gl.glPopMatrix();
        gl.glPushMatrix();
        gl.glTranslated(-0.45, 0.24, 0);
        gl.glScaled(paintSize, paintSize, paintSize);
        drawSurface(gl, paint);
        gl.glPopMatrix();
        gl.glPushMatrix();
        gl.glTranslated(-0.2, 0.41, 0);
        gl.glScaled(paintSize, paintSize, paintSize);
        drawSurface(gl, paint);
        gl.glPopMatrix();
        gl.glPushMatrix();
        gl.glTranslated(0.1, 0.45, 0);
        gl.glScaled(paintSize, paintSize, paintSize);
        drawSurface(gl, paint);
        gl.glPopMatrix();
    }

    
    void drawSpline(GL gl, float[] spline) {
        for (int i = 0; i < spline.length/6-1; i++)
        {
            float[] cp = new float[9];
            for (int j = 0; j < 9; j++)
            {
                cp[j] = spline[i*6+j];
            }
            FloatBuffer fb;
            fb = FloatBuffer.wrap(cp);

            gl.glMap1f(GL.GL_MAP1_VERTEX_3, 0, 1, 3, 3, fb);
            gl.glEnable(GL.GL_MAP1_VERTEX_3);
            
            gl.glMapGrid1f(10, 0, 1);
            gl.glEvalMesh1(GL.GL_LINE, 0, 10);
        }
    }
    
    void drawSurface(GL gl, float[] spline) {
        Random rnd = new Random();
        gl.glColor3d(0.1+0.9*rnd.nextDouble(), 0.1+0.9*rnd.nextDouble(), 0.1+0.9*rnd.nextDouble());
        //gl.glColor3d(Math.round(rnd.nextDouble())*0.8+0.2, Math.round(rnd.nextDouble())*0.8+0.2, Math.round(rnd.nextDouble())*0.8+0.2);
        
        FloatBuffer fb;
        fb = FloatBuffer.wrap(spline);

        gl.glMap2f(GL.GL_MAP2_VERTEX_3, 0, 1, 3, 4, 0, 1, 12, 4, fb);
        gl.glEnable(GL.GL_MAP2_VERTEX_3);

        gl.glMapGrid2f(5, 0, 1, 5, 0, 1);
        gl.glEvalMesh2(GL.GL_FILL, 0, 5, 0, 5);
        gl.glColor3d(0, 0, 0);
        gl.glEvalMesh2(GL.GL_LINE, 0, 5, 0, 5);
    }
    
    public void displayChanged(GLAutoDrawable drawable, boolean modeChanged, boolean deviceChanged) {
    }
}

