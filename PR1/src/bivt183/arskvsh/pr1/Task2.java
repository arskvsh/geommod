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
 *
 * @author ArsKvsh
 */
public class Task2 implements GLEventListener {

    public static void main(String[] args) {
        Frame frame = new Frame("PR1 - Zadanie 2");
        GLCanvas canvas = new GLCanvas();

        canvas.addGLEventListener(new Task2());
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
        
        double alpha, da, x, y;
        int n=36;
        da = 2*Math.PI/n;

        gl.glPointSize(3);
        gl.glColor3d(1, 1, 1);
        drawRoundedRect(gl, 1.9, 1.9, 0.1, 36);
        
        gl.glColor3d(0.2, 0.62, 0.90);
        drawRoundedRect(gl, 1.8, 1.8, 0.06, 36);
        
        gl.glColor3d(1, 1, 1);
        drawRoundedTriangle(gl, 1.65, 1.42, 0.12, 0.03, 36);
        
        //зебра
        double zebraLowerWidth = 1.2, zebraUpperWidth = 0.95, zebraHeight = 0.19, zebraPos = -0.55;
        gl.glColor3d(0, 0, 0);
        double dLowerWidth = zebraLowerWidth / 7;
        double dUpperWidth = zebraUpperWidth / 7;
        gl.glBegin(GL.GL_QUADS);
            for(int i = 0; i < 3; i++) {
                gl.glVertex2d(-zebraLowerWidth/2+dLowerWidth*(i*3), zebraPos);
                gl.glVertex2d(-zebraUpperWidth/2+dUpperWidth*(i*3), zebraPos + zebraHeight);
                gl.glVertex2d(-zebraUpperWidth/2+dUpperWidth*(1+i*3), zebraPos + zebraHeight);
                gl.glVertex2d(-zebraLowerWidth/2+dLowerWidth*(1+i*3), zebraPos);
            }
        gl.glEnd();
        
        //голова
        alpha = 0;
        double xPos = 0.02, yPos = 0.48;
        double headR = 0.094;
        gl.glVertex2d(xPos, yPos);
        gl.glBegin(GL.GL_TRIANGLE_FAN);
             while(alpha<=Math.PI*2+da) {
                double R = headR + 0.01*Math.sin((Math.PI*5/6)+alpha*2);
                x = R*Math.cos(alpha);
                y = R*Math.sin(alpha);
                gl.glVertex2d(x+xPos, y+yPos);
                alpha += da;
            }
        gl.glEnd();
        
        //ноги
        double legThickness = 0.057;
        double rFootX = -0.26, lFootX = 0.23, FootY = -0.36, kneeX = 0.17, kneeY = -0.1;
        double rLegRootX = 0, rLegRootY = 0.09, lLegRootX = -0.05, lLegRootY = 0.11;
        drawThickLine(gl, rLegRootX, rLegRootY, rFootX, FootY,  legThickness, 36);
        gl.glColor3d(1, 1, 1);
        drawThickLine(gl, lLegRootX, lLegRootY, kneeX, kneeY, legThickness+0.012, 36);
        gl.glColor3d(0, 0, 0);
        drawThickLine(gl, lLegRootX, lLegRootY, kneeX, kneeY, legThickness, 36);
        drawThickLine(gl, kneeX, kneeY, lFootX, FootY, legThickness, 36);
        
        double armThickness = 0.045;
        double rHandX = -0.25, rHandY = 0.1, rElbowX = -0.25, rElbowY = 0.28, rShoulderX = -0.05, rShoulderY = 0.40;
        drawThickLine(gl, rHandX, rHandY, rElbowX, rElbowY, armThickness, 36);
        drawThickLine(gl, rElbowX, rElbowY, rShoulderX, rShoulderY, armThickness, 36);
        drawThickLine(gl, rShoulderX, rShoulderY, 0.025, 0.36, armThickness, 36);
        drawThickLine(gl, 0, 0.3, 0.2, 0.1, armThickness, 36);
        
        gl.glColor3d(1, 1, 1);
        gl.glBegin(GL.GL_QUADS);
             gl.glVertex2d(0, 0.1);
             gl.glVertex2d(0.09, 0.1);
             gl.glVertex2d(0.09, 0.32);
             gl.glVertex2d(0, 0.3);
        gl.glEnd();
        
        gl.glColor3d(0, 0, 0);
        gl.glBegin(GL.GL_QUADS);
             gl.glVertex2d(-0.11, 0.05);
             gl.glVertex2d(0.07, 0);
             gl.glVertex2d(0.07, 0.32);
             gl.glVertex2d(-0.11, 0.35);
        gl.glEnd();
        //drawThickLine(gl, 0, 0, 0, 0.1, 0.06, 36);
        // Flush all drawing operations to the graphics card
        gl.glFlush();
    }

    void drawRoundedRect(GL gl, double width, double height, double R, int n){
        gl.glBegin(GL.GL_TRIANGLE_FAN);
            double alpha = 0, da, x, y;
            da = 2*Math.PI/n;
            gl.glVertex2d(0, 0);
            while(alpha<=Math.PI/2) {
               x = R*Math.cos(alpha);
               y = R*Math.sin(alpha);
               gl.glVertex2d(x+(width/2)-R, y+(height/2)-R);
               alpha += da;
           }
           alpha = Math.PI/2;
           while(alpha<=Math.PI) {
               x = R*Math.cos(alpha);
               y = R*Math.sin(alpha);
               gl.glVertex2d(x-(width/2)+R, y+(height/2)-R);
               alpha += da;
           }
           alpha = Math.PI;
           while(alpha<=Math.PI*1.5) {
               x = R*Math.cos(alpha);
               y = R*Math.sin(alpha);
               gl.glVertex2d(x-(width/2)+R, y-(height/2)+R);
               alpha += da;
           }
           alpha = Math.PI*1.5;
           while(alpha<=Math.PI*2) {
               x = R*Math.cos(alpha);
               y = R*Math.sin(alpha);
               gl.glVertex2d(x+(width/2)-R, y-(height/2)+R);
               alpha += da;
           }
           gl.glVertex2d(width/2, height/2-R);
        gl.glEnd();
    }
    
    void drawRoundedTriangle(GL gl, double width, double height, double yOffset, double R, int n){
        gl.glBegin(GL.GL_TRIANGLE_FAN);
            double alpha = -Math.PI/2, da, x, y;
            da = 2*Math.PI/n;
            gl.glVertex2d(0, yOffset);
            while(alpha<=Math.PI/6) {
               x = R*Math.cos(alpha);
               y = R*Math.sin(alpha);
               gl.glVertex2d(x+(width/2)-R, yOffset+y-(height/2)+R);
               alpha += da;
           }
           alpha = Math.PI/6;
           while(alpha<=(Math.PI*5/6)) {
               x = R*Math.cos(alpha);
               y = R*Math.sin(alpha);
               gl.glVertex2d(x, yOffset+y+(height/2)-R);
               alpha += da;
           }
           alpha = Math.PI*5/6;
           while(alpha<=(Math.PI*3/2)) {
               x = R*Math.cos(alpha);
               y = R*Math.sin(alpha);
               gl.glVertex2d(x-(width/2)+R, yOffset+y-(height/2)+R);
               alpha += da;
           }
           gl.glVertex2d((width/2)-R, yOffset-(height/2));
        gl.glEnd();
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
               gl.glVertex2d(x+x1, y+y1-R);
               alpha += da;
           }
           alpha = angle+Math.PI/2;
           while(alpha<=angle+(Math.PI*3/2)) {
               x = R*Math.cos(alpha);
               y = R*Math.sin(alpha);
               gl.glVertex2d(x+x2, y+y2-R);
               alpha += da;
           }
           gl.glVertex2d(R*Math.cos(angle-Math.PI/2)+x1, R*Math.sin(angle-Math.PI/2)+y1-R);
        gl.glEnd();
    }
    
    public void displayChanged(GLAutoDrawable drawable, boolean modeChanged, boolean deviceChanged) {
    }
}
