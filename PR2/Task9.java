package bivt183.arskvsh.pr2;

import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCanvas;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;
import bivt183.arskvsh.pr2.helpers.Voxel;
import bivt183.arskvsh.pr2.helpers.OpenSimplexNoise;
import java.util.Random;

/**
 * PR1.java <BR>
 * author: Brian Paul (converted to Java by Ron Cemer and Sven Goethel) <P>
 *
 * This version is equal to Brian Paul's version 1.2 1999/10/21
 */
public class Task9 implements GLEventListener {   
    public static void main(String[] args) {
        Frame frame = new Frame("PR2 - Zadanie 7");
        GLCanvas canvas = new GLCanvas();

        canvas.addGLEventListener(new Task9());
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
    
    public long seed = 100;
    public int resolution = 50;
    public double detail = 100;
    public double randomLayerProbability = 0.2;
    public int height = 50;
    public int cameraRot = 35;
    public int section = 100;
    public double scale = 1;
    
    OpenSimplexNoise noise = new OpenSimplexNoise(seed);
    Random random = new Random();

    Voxel[][][] voxels = new Voxel[0][0][0];

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
        
        generateTerrain();
    }
    
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        GL gl = drawable.getGL();
        GLU glu = new GLU();
    }
    
    public void newSeed() {
        noise = new OpenSimplexNoise(seed);
    }
    
    public void generateTerrain() {
        voxels = generateTerrain(noise, random, resolution, detail, randomLayerProbability, height);
    }
    
    public void display(GLAutoDrawable drawable) {
        GL gl = drawable.getGL();
        
        gl.glClearColor(0.5f,0.55f,0.6f,0);
        gl.glClear(GL.GL_COLOR_BUFFER_BIT);
        gl.glClear(GL.GL_DEPTH_BUFFER_BIT);
        gl.glEnable(GL.GL_DEPTH_TEST);
        gl.glLoadIdentity();
        gl.glOrtho(-1,1,-1,1,2,-2);
        
        gl.glRotated(-120, 1, 0, 0);
        gl.glRotated(cameraRot, 0, 0, scale);
        //gl.glTranslated(-1, -1, -1);
        //gl.glViewport( 0, 0, 800, 800 );
        
       
        //Voxel voxel = new Voxel(1,1,1);
        drawVoxels(gl, voxels, resolution, section, scale);
        //drawVoxel(gl, voxel, 0.2, 0, 0, 0);
    }
    
    Voxel[][][] generateTerrain(OpenSimplexNoise noise, Random random, int resolution, double detail, double randomLayerProbability, int height) {
        Voxel[][][] vx = new Voxel[resolution][resolution][resolution];
        double palette[][] = {{0.3,0.3,0.3}, {0.4,0.4,0.4}, {0.5,0.5,0.5}, {0.35,0.3,0.25}, {0.45,0.4,0.3}};
        double[] iColor;
        int actualheight = height * (int)Math.round(detail) / 100;
        for (int i = 0; i < actualheight; i++) {
            if(random.nextDouble() > randomLayerProbability)
                iColor = palette[(int)Math.round((double)i/(double)actualheight * (double)(palette.length-1))];
            else 
                iColor = palette[(int)Math.round(random.nextDouble()*(double)(palette.length-1))];
            vx = generateLayer(vx, noise, i, resolution, detail-10*((double)i/(double)actualheight)*random.nextDouble(), iColor[0], iColor[1], iColor[2]);
        }
        vx = generateLayer(vx, resolution, 0.2, 0.4, 0.2);
        return vx;
    }
    
    Voxel[][][] generateLayer(Voxel[][][] voxels, OpenSimplexNoise noise, int level, int resolution, double detail, double r, double g, double b) {
        Voxel[][][] vx = voxels;
        for (int i = 0; i < resolution; i++) {
            for (int j = 0; j < resolution; j++) {
                for (int l = 0; l < resolution; l++) {
                    if (voxels[i][j][l] == null) {
                        if(noise.eval(i/detail,j/detail,level) > 0.15)
                            vx[i][j][l] = new Voxel(r,g,b);
                        break;
                    }
                }
            }
        }
        return vx;
    }
    
    Voxel[][][] generateLayer(Voxel[][][] voxels, int resolution, double r, double g, double b) {
        Voxel[][][] vx = voxels;
        for (int i = 0; i < resolution; i++) {
            for (int j = 0; j < resolution; j++) {
                for (int l = 0; l < resolution; l++) {
                    if (voxels[i][j][l] == null) {
                        vx[i][j][l] = new Voxel(r,g,b);
                        break;
                    }
                }
            }
        }
        return vx;
    }
    
    void drawVoxel(GL gl, Voxel voxel, double scale, double posX, double posY, double posZ) {
        gl.glBegin(GL.GL_QUADS);
            //top
            gl.glColor3d(voxel.rColor, voxel.gColor, voxel.bColor);
            gl.glVertex3d(-scale+posX, -scale+posY, scale+posZ);
            gl.glVertex3d(scale+posX, -scale+posY, scale+posZ);
            gl.glVertex3d(scale+posX, scale+posY, scale+posZ);
            gl.glVertex3d(-scale+posX, scale+posY, scale+posZ);
            //bottom
            gl.glColor3d(voxel.rColor-0.5, voxel.gColor-0.5, voxel.bColor-0.5);
            gl.glVertex3d(-scale+posX, scale+posY, -scale+posZ);
            gl.glVertex3d(-scale+posX, -scale+posY, -scale+posZ);
            gl.glVertex3d(-scale+posX, scale+posY, -scale+posZ);
            gl.glVertex3d(scale+posX, scale+posY, -scale+posZ);
            //front
            gl.glColor3d(voxel.rColor-0.2, voxel.gColor-0.2, voxel.bColor-0.2);
            gl.glVertex3d(scale+posX, scale+posY, scale+posZ);
            gl.glVertex3d(-scale+posX, scale+posY, scale+posZ);
            gl.glVertex3d(-scale+posX, scale+posY, -scale+posZ);
            gl.glVertex3d(scale+posX, scale+posY, -scale+posZ);
            //back
            gl.glColor3d(voxel.rColor-0.3, voxel.gColor-0.3, voxel.bColor-0.3);
            gl.glVertex3d(scale+posX, -scale+posY, scale+posZ);
            gl.glVertex3d(-scale+posX, -scale+posY, scale+posZ);
            gl.glVertex3d(-scale+posX, -scale+posY, -scale+posZ);
            gl.glVertex3d(scale+posX, -scale+posY, -scale+posZ);
            //left
            gl.glColor3d(voxel.rColor-0.1, voxel.gColor-0.1, voxel.bColor-0.1);
            gl.glVertex3d(scale+posX, -scale+posY, scale+posZ);
            gl.glVertex3d(scale+posX, scale+posY, scale+posZ);
            gl.glVertex3d(scale+posX, scale+posY, -scale+posZ);
            gl.glVertex3d(scale+posX, -scale+posY, -scale+posZ);
            //right
            gl.glColor3d(voxel.rColor-0.05, voxel.gColor-0.05, voxel.bColor-0.05);
            gl.glVertex3d(-scale+posX, -scale+posY, scale+posZ);
            gl.glVertex3d(-scale+posX, scale+posY, scale+posZ);
            gl.glVertex3d(-scale+posX, scale+posY, -scale+posZ);
            gl.glVertex3d(-scale+posX, -scale+posY, -scale+posZ);
        gl.glEnd();
    }
    
    void drawVoxels(GL gl, Voxel[][][] voxels, int resolution, int section, double scale) {
        int sect = (int)Math.round((double)section / 100 * (double)resolution);
        for (int i = 0; i < resolution; i++) {
            for (int j = 0; j < resolution; j++) {
                for (int l = 0; l < sect; l++) {
                     if(voxels[i][j][l] != null) {
                         drawVoxel(gl, voxels[i][j][l], scale/resolution/2, (i-(double)resolution/2)*scale/resolution, (j-(double)resolution/2)*scale/resolution, (l-(double)resolution/2)*scale/resolution);
                     }
                            //
                            //
                            //gl.glVertex3d(i*scale/resolution, j*scale/resolution, l*scale/resolution);
                }
            }
        }
    }
    
    public void displayChanged(GLAutoDrawable drawable, boolean modeChanged, boolean deviceChanged) {
    }
}

