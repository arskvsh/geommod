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
import java.util.Calendar;


/**
 * PR1.java <BR>
 * author: Brian Paul (converted to Java by Ron Cemer and Sven Goethel) <P>
 *
 * This version is equal to Brian Paul's version 1.2 1999/10/21
 */
public class Task3 implements GLEventListener {

    public static void main(String[] args) {
        Frame frame = new Frame("PR1 - Zadanie 3");
        GLCanvas canvas = new GLCanvas();

        canvas.addGLEventListener(new Task3());
        frame.add(canvas);
        frame.setSize(500, 500);
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
    }

    public void display(GLAutoDrawable drawable) {
        GL gl = drawable.getGL();
               
        gl.glClearColor(0,0,0,0);
        gl.glClear(GL.GL_COLOR_BUFFER_BIT);
        

        double thickness = 0.5, R, alpha, da, x, y;
        int n=64;
        da = 2*Math.PI/n;
        alpha = 0;
        
        gl.glColor3d(1, 0.31, 0);
        gl.glBegin(GL.GL_TRIANGLE_FAN);
             while(alpha<=Math.PI*2+da) {
                R = 1;
                x = R*Math.cos(alpha);
                y = R*Math.sin(alpha);
                gl.glVertex2d(x, y);
                alpha += da;
            }
        gl.glEnd();
        
        alpha = 0;
        gl.glColor3d(1, 1, 1);
        gl.glBegin(GL.GL_TRIANGLE_FAN);
             while(alpha<=Math.PI*2+da) {
                R = 0.9;
                x = R*Math.cos(alpha);
                y = R*Math.sin(alpha);
                gl.glVertex2d(x, y);
                alpha += da;
            }
        gl.glEnd();
        
        da = 2*Math.PI/12;
        
        alpha = 0;
        gl.glColor3d(0, 0, 0);
        gl.glLineWidth(5);
        while(alpha<=Math.PI*2) {
           R = 0.74;
           drawThickLine(gl, R*Math.cos(alpha), R*Math.sin(alpha), (R+0.12)*Math.cos(alpha), (R+0.12)*Math.sin(alpha), 0.01, 12);
           alpha += da;
        }
        
        Calendar now = Calendar.getInstance();
        //int hour = now.get(Calendar.HOUR_OF_DAY);
        //int minute = now.get(Calendar.MINUTE);
        //int second = now.get(Calendar.SECOND);
        /*double hAngle = -Math.toRadians((time-1)*30+minute*0.5)+Math.PI/2;
        double mAngle = -Math.toRadians(minute*6+second*0.1)+Math.PI/2;
        double sAngle = -Math.toRadians(second*6)+Math.PI/2;*/
        
        int timezone = 3;
        double time = System.currentTimeMillis();
        double seconds = time / 1000;
        double hAngle = -Math.toRadians((seconds/3600+timezone)*30)+Math.PI/2;
        double mAngle = -Math.toRadians(seconds/10)+Math.PI/2;
        double sAngle = -Math.toRadians(seconds*6)+Math.PI/2;
        
        double hR = 0.6, hM = 0.85, hS = 0.85;
        
        drawThickLine(gl, 0, 0, hR*Math.cos(hAngle), hR*Math.sin(hAngle), 0.02, 16);
        drawThickLine(gl, 0, 0, hM*Math.cos(mAngle), hM*Math.sin(mAngle), 0.02, 16);
        gl.glColor3d(1, 0, 0);
        drawThickLine(gl, 0, 0, hS*Math.cos(sAngle), hS*Math.sin(sAngle), 0.005, 16);
        drawThickLine(gl, 0, 0, 0.2*Math.cos(sAngle+Math.PI), 0.2*Math.sin(sAngle+Math.PI), 0.005, 12);
        
        alpha = 0;
        gl.glBegin(GL.GL_TRIANGLE_FAN);
             while(alpha<=Math.PI*2+da) {
                R = 0.03;
                x = R*Math.cos(alpha);
                y = R*Math.sin(alpha);
                gl.glVertex2d(x, y);
                alpha += da;
            }
        gl.glEnd();
        
        // Flush all drawing operations to the graphics card
        gl.glFlush();
    }
    
    void drawThickLine(GL gl, double x1, double y1, double x2, double y2, double R, int n){
        gl.glBegin(GL.GL_TRIANGLE_FAN);
            double alpha, da, x, y;
            double angle = Math.atan2(y1-y2, x1-x2);
            alpha = angle-Math.PI/2;
            da = 2*Math.PI/n;
            gl.glVertex2d((x1+x2)/2, (y1+y2)/2);
            while(alpha<=angle+Math.PI/2) {
               x = R*Math.cos(alpha);
               y = R*Math.sin(alpha);
               gl.glVertex2d(x+x1, y+y1);
               alpha += da;
           }
           alpha = angle+Math.PI/2;
           while(alpha<=angle+(Math.PI*3/2)) {
               x = R*Math.cos(alpha);
               y = R*Math.sin(alpha);
               gl.glVertex2d(x+x2, y+y2);
               alpha += da;
           }
           gl.glVertex2d(R*Math.cos(angle-Math.PI/2)+x1, R*Math.sin(angle-Math.PI/2)+y1);
        gl.glEnd();
    }

    public void displayChanged(GLAutoDrawable drawable, boolean modeChanged, boolean deviceChanged) {
    }
}

