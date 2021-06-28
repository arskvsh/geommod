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



/**
 * PR1.java <BR>
 * author: Brian Paul (converted to Java by Ron Cemer and Sven Goethel) <P>
 *
 * This version is equal to Brian Paul's version 1.2 1999/10/21
 */
public class Task7 implements GLEventListener {

    public static void main(String[] args) {
        Frame frame = new Frame("PR2 - Zadanie 7");
        GLCanvas canvas = new GLCanvas();

        canvas.addGLEventListener(new Task7());
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
        
        gl.glClearColor(0,0,0,0);
        
        gl.glClear(GL.GL_COLOR_BUFFER_BIT);
        gl.glClear(GL.GL_DEPTH_BUFFER_BIT);
        gl.glEnable(GL.GL_DEPTH_TEST);
        
        gl.glLoadIdentity();
        gl.glRotated(-120, 1, 0, 0);
        gl.glRotated(90+35, 0, 0, 1);
        
        double thickness = 0.5, R, alpha, da, x, y, xt, yt;
        int n=36;
        da = 2*Math.PI/n;
        double length = 0.8;
        int unitSegments = 20;
        R = 0.1;
        
        int segments = 10;
        double segmentLength = 0.15;
        
        alpha = 0;

        for (int i = 0; i < segments+1; i++) {
            alpha = 0;
            double radius = calcRadius(R, i, segments);
            double radiust = calcRadius(R, i+1, segments);
            gl.glBegin(GL.GL_QUAD_STRIP);
                while(alpha<=Math.PI*2+da) 
                {
                    x = radius*Math.cos(alpha);
                    y = radius*Math.sin(alpha);
                    if(i == segments) {
                         xt = (radiust-0.01)*Math.cos(alpha);
                         yt = (radiust-0.01)*Math.sin(alpha);
                         gl.glColor3d(.65*calcColor(alpha), .4*calcColor(alpha), .2*calcColor(alpha));
                    } else {
                         xt = radiust*Math.cos(alpha);
                         yt = radiust*Math.sin(alpha);
                         gl.glColor3d(.4*calcColor(alpha), .2*calcColor(alpha), .1*calcColor(alpha));
                    }
                    gl.glVertex3d(x, y, (i-3*segments/4)*segmentLength);
                    gl.glVertex3d(xt, yt, (i+1-3*segments/4)*segmentLength);
                    alpha += da;
                }
            gl.glEnd();
        }
        
        gl.glBegin(GL.GL_QUAD_STRIP);
            gl.glVertex3d(length+0.2, 0, 0);
            while(alpha<=Math.PI*2+da) 
            {
               x = R*Math.cos(alpha);
               y = R*Math.sin(alpha);
               gl.glVertex3d(length, x, y);
               alpha += da;
           }
        gl.glEnd();
             
        int hairQ = 1000;
        int hairSeg = 10;
        Random random = new Random();
        for (int i = 0; i < hairQ; i++) {
            alpha += Math.random();
            double rootRadius = Math.random()*calcRadius(R, segments, segments);
            double randCol = -random.nextDouble()*0.1;
            for (int j = 0; j < hairSeg; j++) {
                gl.glBegin(GL.GL_LINE_STRIP);
                x = 0.5*rootRadius*Math.cos(alpha)*(1-(double)j/hairSeg)+calcRadius2(0.05, j, hairSeg)*Math.cos(alpha);
                y = 0.5*rootRadius*Math.sin(alpha)*(1-(double)j/hairSeg)+calcRadius2(0.05, j, hairSeg)*Math.sin(alpha);
                xt = 0.5*rootRadius*Math.cos(alpha)*(1-(double)(j+1)/hairSeg)+calcRadius2(0.05, j+1, hairSeg)*Math.cos(alpha);
                yt = 0.5*rootRadius*Math.sin(alpha)*(1-(double)(j+1)/hairSeg)+calcRadius2(0.05, j+1, hairSeg)*Math.sin(alpha);
                gl.glColor3d(.2*calcColor(alpha)+randCol, .1*calcColor(alpha)+randCol, .05*calcColor(alpha)+randCol);
                gl.glVertex3d(x, y, (segments+1-3*segments/4)*segmentLength+(j)*0.05);
                gl.glVertex3d(xt, yt, (segments+1-3*segments/4)*segmentLength+(j+1)*0.05);
                gl.glEnd();
            }

        }
        
        gl.glFlush();
    }

    double calcRadius(double R, int i, int segments){
        return R * Math.pow(Math.sin(Math.PI/2*i/segments),0.3);
    }
    double calcRadius2(double R, int i, int segments){
        return R * (Math.sin((double)i/segments*Math.PI*2*3/4)+1);
    }
    
    double calcColor(double angle) {
        return 1+Math.sin(angle+Math.PI/3)*0.5;
    }
    public void displayChanged(GLAutoDrawable drawable, boolean modeChanged, boolean deviceChanged) {
    }
}

