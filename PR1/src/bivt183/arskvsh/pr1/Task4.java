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
public class Task4 implements GLEventListener {
    static int w = 800, h = 800;
    
    public static void main(String[] args) {
        Frame frame = new Frame("PR1 - Zadanie 4");
        GLCanvas canvas = new GLCanvas();

        canvas.addGLEventListener(new Task4());
        frame.add(canvas);
        frame.setSize(w, h);
        final Animator animator = new Animator(canvas);
        frame.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                // Run this on another thread than the AWT event queue to
                // make sure the call to Animator.stop() completes before
                // exiting
                new Thread(new Runnable() {

                    public void run() {
                        animator.stop();
                        System.exit(0);
                    }
                }).start();
            }
        });
        // Center frame
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        animator.start();
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
        w = width;
        h = height;
    }

    public void display(GLAutoDrawable drawable) {
        GL gl = drawable.getGL();
        
        double thickness = 0.5, R = 0.7, iR = 0.3, tR = 0.85, alpha, da, x, y;
        double maxX = 0, minX = 0, maxY = 0, minY = 0;
        int n=15;
        da = 2*Math.PI/(n*4);
        double aOffset = da*0.5;
        alpha = 0;
        
        //вид сверху
        gl.glViewport(0, 0, w/2, h/2);
        gl.glScissor(0, 0, w/2, h/2);
        gl.glEnable(GL.GL_SCISSOR_TEST);
        gl.glDisable(GL.GL_LINE_STIPPLE);
        gl.glClearColor(1,1,1,0);
        gl.glClear(GL.GL_COLOR_BUFFER_BIT);

        gl.glColor3d(0, 0, 1);
        gl.glLineWidth(3);

        gl.glBegin(GL.GL_LINE_STRIP);
             while(alpha<Math.PI*2) {
                for(int i = 0; i < 4; i++) {
                    double radius = R;
                    if (i > 1)
                        radius = tR;
                    x = radius*Math.cos(alpha+aOffset);
                    y = radius*Math.sin(alpha+aOffset);
                    maxX = Math.max(maxX, x);
                    minX = Math.min(minX, x);
                    maxY = Math.max(maxY, x);
                    minY = Math.min(minY, x);
                    gl.glVertex2d(x, y);
                    alpha += da;
                }
            }
        gl.glEnd();
        
        alpha = 0;
        gl.glBegin(GL.GL_LINE_STRIP);
             while(alpha<Math.PI*2) {
                x = iR*Math.cos(alpha);
                y = iR*Math.sin(alpha);
                gl.glVertex2d(x, y);
                alpha += da;
            }
        gl.glEnd();
        
        gl.glEnable(GL.GL_LINE_STIPPLE);
        gl.glColor3d(1, 0.5, 0);
        gl.glLineWidth(1);
        gl.glLineStipple(3, (short)0xFF2F);
        gl.glBegin(GL.GL_LINES);
             gl.glVertex2d(0.95, 0);
             gl.glVertex2d(-0.95, 0);
             gl.glVertex2d(0, 0.95);
             gl.glVertex2d(0, -0.95);
        gl.glEnd();

        //фронтальный вид
        gl.glViewport(0, h/2, w/2, h/2);
        gl.glScissor(0, h/2, w/2, h/2);
        gl.glEnable(GL.GL_SCISSOR_TEST);
        gl.glClearColor(1,1,1,0);
        gl.glClear(GL.GL_COLOR_BUFFER_BIT);
        
        gl.glColor3d(0, 0, 1);
        gl.glLineWidth(3);
        
        gl.glLineStipple(1, (short)0xFFF0);
        
        alpha = 0;
        gl.glBegin(GL.GL_LINES);
            while(alpha<Math.PI*2) {
               for(int i = 0; i < 4; i++) {
                   double radius = R;
                   if (i > 1)
                       radius = tR;
                   x = radius*Math.cos(alpha+aOffset);
                   y = radius*Math.sin(alpha+aOffset);
                   if(y >= 0) {
                       gl.glVertex2d(x, thickness/2);
                       gl.glVertex2d(x, -thickness/2);
                   }
                   alpha += da;
               }
           }
        gl.glEnd();
        
        gl.glBegin(GL.GL_LINES);
           gl.glVertex2d(-iR, thickness/2);
           gl.glVertex2d(-iR, -thickness/2);
           gl.glVertex2d(iR, thickness/2);
           gl.glVertex2d(iR, -thickness/2);
        gl.glEnd();
        
        gl.glDisable(GL.GL_LINE_STIPPLE);
        
        alpha = 0;
        gl.glBegin(GL.GL_LINES);
            while(alpha<Math.PI*2) {
               for(int i = 0; i < 4; i++) {
                   double radius = R;
                   if (i > 1)
                       radius = tR;
                   x = radius*Math.cos(alpha+aOffset);
                   y = radius*Math.sin(alpha+aOffset);
                   if(y < 0) {
                       gl.glVertex2d(x, thickness/2);
                       gl.glVertex2d(x, -thickness/2);
                   }
                   alpha += da;
               }
           }
        gl.glEnd();
        
        gl.glBegin(GL.GL_LINES);
           gl.glVertex2d(minX, thickness/2);
           gl.glVertex2d(maxX, thickness/2);
           gl.glVertex2d(minX, -thickness/2);
           gl.glVertex2d(maxX, -thickness/2);
        gl.glEnd();
        
        gl.glEnable(GL.GL_LINE_STIPPLE);
        gl.glColor3d(1, 0.5, 0);
        gl.glLineWidth(1);
        gl.glLineStipple(3, (short)0xFF2F);
        gl.glBegin(GL.GL_LINES);
             gl.glVertex2d(0, 0.5);
             gl.glVertex2d(0, -0.5);
        gl.glEnd();
        
        //боковой вид
        gl.glViewport(w/2, h/2, w/2, h/2);
        gl.glScissor(w/2, h/2, w/2, h/2);
        gl.glEnable(GL.GL_SCISSOR_TEST);
        gl.glClearColor(1,1,1,0);
        gl.glClear(GL.GL_COLOR_BUFFER_BIT);
        
        gl.glColor3d(0, 0, 1);
        gl.glLineWidth(3);
        
        gl.glLineStipple(1, (short)0xFFF0);
        
        alpha = 0;
        gl.glBegin(GL.GL_LINES);
            while(alpha<Math.PI*2) {
               for(int i = 0; i < 4; i++) {
                   double radius = R;
                   if (i > 1)
                       radius = tR;
                   x = radius*Math.cos(alpha+aOffset);
                   y = radius*Math.sin(alpha+aOffset);
                   if(x >= 0) {
                       gl.glVertex2d(-y, thickness/2);
                       gl.glVertex2d(-y, -thickness/2);
                   }
                   alpha += da;
               }
           }
        gl.glEnd();
        
        gl.glBegin(GL.GL_LINES);
           gl.glVertex2d(-iR, thickness/2);
           gl.glVertex2d(-iR, -thickness/2);
           gl.glVertex2d(iR, thickness/2);
           gl.glVertex2d(iR, -thickness/2);
        gl.glEnd();
        
        gl.glDisable(GL.GL_LINE_STIPPLE);
        
        alpha = 0;
        gl.glBegin(GL.GL_LINES);
            while(alpha<Math.PI*2) {
               for(int i = 0; i < 4; i++) {
                   double radius = R;
                   if (i > 1)
                       radius = tR;
                   x = radius*Math.cos(alpha+aOffset);
                   y = radius*Math.sin(alpha+aOffset);
                   if(x < 0) {
                       gl.glVertex2d(-y, thickness/2);
                       gl.glVertex2d(-y, -thickness/2);
                   }
                   alpha += da;
               }
           }
        gl.glEnd();
        
        gl.glBegin(GL.GL_LINES);
           gl.glVertex2d(minY, thickness/2);
           gl.glVertex2d(maxY, thickness/2);
           gl.glVertex2d(minY, -thickness/2);
           gl.glVertex2d(maxY, -thickness/2);
        gl.glEnd();
        
        gl.glEnable(GL.GL_LINE_STIPPLE);
        gl.glColor3d(1, 0.5, 0);
        gl.glLineWidth(1);
        gl.glLineStipple(3, (short)0xFF2F);
        gl.glBegin(GL.GL_LINES);
             gl.glVertex2d(0, 0.5);
             gl.glVertex2d(0, -0.5);
        gl.glEnd();
        
        // Flush all drawing operations to the graphics card
        gl.glFlush();
    }

    public void displayChanged(GLAutoDrawable drawable, boolean modeChanged, boolean deviceChanged) {
    }
}

