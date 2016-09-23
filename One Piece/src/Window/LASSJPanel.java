package Window;

import java.awt.*;

import java.util.*;
import javax.swing.*;
import javax.imageio.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import java.net.URL;

/**
 * This is the part of the LASS Swing framework that actually draws graphics
 * You should create a subclass of this to implement your project, and implement
 * the required methods 
 * 
 * @author K. Lewin
 * @version 1.0
 * @since Jan 1, 2016
 */


public abstract class LASSJPanel extends JPanel implements ActionListener, KeyListener, MouseListener, MouseMotionListener {

        //**************************************************************************************
        //THESE ARE THE METHODS THAT YOU MUST IMPLEMENT WHEN YOU CREATE YOUR OWN
        //SUBCLASS OF LASSJPanel

        /**
         * YOU MUST IMPLEMENT THIS METHOD IN YOUR SUBCLASS
         * <p>
         * Here is where you will actually draw what you want to show in the panel
         * This method should ONLY contain drawing commands (as much as possible)
         * <p>
         * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
         */
        public abstract void paintComponent(Graphics g);

        /**
         * YOU MUST IMPLEMENT THIS METHOD IN YOUR SUBCLASS
         * <p>
         * This method is in charge of generating the next frame of animation
         * The framework will automatically handle the timing to achieve your requested
         * framerate.  Assume that each time this is called, one frame's worth of time has
         * passed by.  The time, in seconds is 1/FPS
         */
        public abstract void updateFrame();

        //Initalizing method
        public abstract void initial();
        
        //**************************************************************************************

        //These fields allow LASSJPanel to correctly time calls to updateFrame()
        //Note that paintComponent() can get called at any time, and we have no control
        //over that
        private long mLastUpdate;       //The time at which we last finished a frame update
        private int mFPS;                       //Frames per second
        private long mNanoPerFrame;     //How many nanoseconds each frame of animation represents
        private long mNanoError;        //How far behind are we (in nanoseconds) 


        //Remember what's going on with the mouse
        Point mMouseLocation;
        int[] mMouseButtons;

        //This is a buffer that we'll use to keep track of all of the characters that have been pressed
        private Deque<CharacterInfo> mCharBuffer;

        //Maintain a collection of all of the images that have been loaded, or are being loaded
        //We will use a map, so that we can associate filenames with the Image objects.  We will use the Image as the key
        //since we will more often be searching for an Image than a filename (the only time we care about the filename is when
        //another image is trying to load)
        Map<Image, String> mImagesLoading;
        Map<Image, String> mImagesLoaded;

        /**
         * Constructor.  Creates a full-screen panel with the specified framerate.
         * <p>
         * If the framerate specified is zero or negative, a then 1 FPS will be used by default.
         * 
         * @param fps Target framerate for the LASSJPanel, measured in frames-per-second.
         */
        public LASSJPanel(int fps)
        {
                this(-1,-1, fps);
        }

        /**
         * Constructor.  Creates a panel with a specific preferred size (in pixels), and a specific framerate.
         * <p>
         * If the framerate specified is zero or negative, a then 1 FPS will be used by default.
         * 
         * @param width The preferred width of the LASSJPanel, in pixels.
         * @param height The preferred height of the LASSJPanel, in pixels.
         * @param fps Target framerate for the LASSJPanel, measured in frames-per-second. 
         */
        public LASSJPanel(int width, int height, int fps) {
                // TODO Auto-generated constructor stub
                //Call the parent constructor
                super();

                Dimension size;

                //Check for a full-screen request
                if ((width<=0)||(height<=0))
                {
                        size = Toolkit.getDefaultToolkit().getScreenSize();
                }
                else
                {
                        size = new Dimension (width,height);                    
                }

                //Tell our container what size we'd like to be set to
                setPreferredSize(size);

                //Set up the mouse and keyboard state
                mMouseLocation = new Point(0,0);
                mMouseButtons = new int[]{0,0,0};

                mCharBuffer = new ArrayDeque<CharacterInfo>();

                //Add ourselves as an event listener
                addKeyListener(this);
                addMouseListener(this);
                addMouseMotionListener(this);
                setFocusable(true);

                //Set up the Image collections
                mImagesLoading = new HashMap<Image, String>();
                mImagesLoaded = new HashMap<Image, String>();

                //Set the framerate (minimum 1 FPS)
                setFPS(fps);
                mNanoError = 0;
                mLastUpdate = System.nanoTime();
                
            	//Set the initial parameters by calling the initial method
        		initial();
        		
                //Create a swing timer; we need to specify a delay in ms, and an object that will
                //receive timer events
                javax.swing.Timer animationTimer = new javax.swing.Timer((int)(mNanoPerFrame/1000000L), this);

                //Start the timer
                animationTimer.start();
        }

        /**
         * Get the framerate of this object
         * 
         * @return Framerate, in frames-per-second
         */
        public int getFPS()
        {
                return mFPS;
        }

        /**
         * Attempt to set the current framerate (in frames per second).
         * <p>
         * Minimum is 1 FPS.
         * 
         * @param fps Desired framerate, in frames-per-second
         */
        public void setFPS(int fps)
        {
                mFPS = Math.max(1, fps);
                mNanoPerFrame = 1000000000L / mFPS;
        }

        /**
         * Check whether or not a character is available to be read
         * 
         * @return True if there is a character in the buffer that has not yet been retrieved, otherwise false
         */
        public boolean isCharAvail()
        {
                return !mCharBuffer.isEmpty();
        }

        /**
         * Check whether or not the next available character is a special character
         * A special character is one that is not a printable character.  For example,
         * the arrow keys and the function keys (F1-F12) would all be special characters.
         * 
         * @return Returns false if the character is not special, or if no character is available.
         */
        public boolean isSpecialChar()
        {
                if (mCharBuffer.isEmpty())
                {
                        return false;
                }

                return mCharBuffer.getFirst().isSpecial();
        }

        /**
         * Get the next available character and remove it from the character buffer.
         * If the character is a special character, you should use <code>getKeyCode()</code> instead.
         * <p>
         * If no character is available, it will wait.
         * 
         * @return The next available character.
         */
        public char getChar()
        {
                while (mCharBuffer.isEmpty())
                {
                        try
                        {
                                Thread.sleep(10);                               
                        }
                        catch (Exception e)
                        {
                                e.printStackTrace();
                        }
                }

                return (char)mCharBuffer.removeFirst().getValue();
        }

        /**
         * Get the virtual key code associated with a special character
         * <p>
         * For example, the arrow keys would return the codes <code>VK_LEFT, VK_RIGHT, VK_UP</code> and <code>VK_DOWN</code>
         * <p>
         * On the keypad they are <code>VK_KP_LEFT, VK_KP_RIGHT, VK_KP_UP</code> and <code>VK_KP_DOWN</code>
         * <p>
         * The function keys are <code>VK_1</code> to <code>VK_12</code>
         * <p>
         * Technically there are key codes for letters and numbers too (e.g. <code>VK_A, VK_B</code> etc., but there is no guarantee that
         * <p>
         * this method will return a meaningful value for printable characters.
         * <p>
         * If no character is available, it will wait.
         * 
         * @return The keycode for the next available character, if the character is a virtual character.
         * @see java.awt.event.KeyEvent#field_summary
         */
        public int getKeyCode()
        {
                while (mCharBuffer.isEmpty())
                {
                        try
                        {
                                Thread.sleep(10);                               
                        }
                        catch (Exception e)
                        {
                                e.printStackTrace();
                        }
                }

                return mCharBuffer.removeFirst().getValue();
        }

        /**
         * Get the location of the mouse pointer in a <code>Point</code> object.
         * <p>
         * You can then use Point.x and Point.y to see the x and y coordinates.
         * 
         * @see java.awt.Point
         * 
         * @return A <code>Point</code> object containing the location of the mouse pointer
         */
        public Point getMouseLocation()
        {
                return new Point(mMouseLocation.x, mMouseLocation.y);
        }

        /**
         * Determine which mouse buttons are being pressed.
         * 
         * @return an array containing the number of clicks for each button on the mouse, in the order {LEFT, MIDDLE, RIGHT}.
         * For example, double-clicking the left button would result in the array {2,0,0}
         */
        public int[] getMouseButtons()
        {
                int[] result = new int[mMouseButtons.length];

                for (int i=0; i<result.length; i++)
                {
                        result[i] = mMouseButtons[i];
                }

                return result;
        }

        /**
         * Unlike the LASSConsole, this method does NOT guarantee that the image will be loaded
         * right away.  This is because the LASSJPanel is on the same thread as GUI events,
         * and waiting for the image to load would freeze the whole GUI.
         * <p>
         * Instead use <code>isImageReady()</code> to check if the image has loaded or not, before
         * you try to draw it.
         * <p>
         * You can then draw the image using any of the <code>drawImage()</code> methods defined in
         * the <code>Graphics</code> class.
         * 
         * @see java.awt.Graphics#drawImage
         * 
         * @param name the Filename or URL of the image to load.  If it's a file, you must specify the path relative to the project folder.
         * 
         * @return An Image object which can be used to draw, or get other information about the Image once it's ready.
         */
        public Image loadImage (String name)
        {
                /*
                 //This method is simpler, and synchronous (it loads images right away)
                 //The disadvantage is that it's a bit slower, and your program actually has to wait
                 //for it to finish

                BufferedImage img = null;
                try
                {
                        img = ImageIO.read(new File(name));
                }
                catch (Exception e)
                {
                        e.printStackTrace();
                }
                
                if (img != null)
                {
                        mImagesLoaded.put(img, name);
                }
                */

                //First, make sure this image is not already loading, or loaded         
                Set<Map.Entry<Image, String>> duplicateSearch = null;
                if (mImagesLoading.containsValue(name))
                {
                        duplicateSearch = mImagesLoading.entrySet();
                }
                else if (mImagesLoaded.containsValue(name))
                {
                        duplicateSearch = mImagesLoaded.entrySet();
                }

                //If a duplicate was found, iterate through the set of entries to find the duplicate and return that Image
                if (duplicateSearch != null)
                {
                        for (Map.Entry<Image, String> entry : duplicateSearch)
                        {
                                if (entry.getValue().equals(name))
                                {
                                        return entry.getKey();
                                }
                        }
                }

                //Start loading the image
                //First, check if it's a file or a URL
                Image img = null;
                File imageFile = new File(name);
                if (imageFile.exists())
                {
                        img = Toolkit.getDefaultToolkit ().getImage (name);
                }
                else
                {
                         try
                         {
                                 img = Toolkit.getDefaultToolkit ().getImage (new URL(name));   
                         }
                         catch (Exception e)
                         {
                                 e.printStackTrace();
                         }
                }
                

                //Add it to the loading list
                mImagesLoading.put(img, name);

                //Check the image width, just as a way to make the loader start, since
                //we don't want to try drawing
                img.getWidth (this);

                //Done!
                return img;
        }

        /**
         * Release an Image from memory, once it is no longer needed.
         * <p>
         * After calling this method you will no longer be able to draw this Image.
         * If you need it again later, you will have to make another call to <code>loadImage()</code>
         * 
         * @param img The image object that was given to you when you called <code>loadImage()</code>, that you wish to release.
         */
        public void unloadImage(Image img)
        {
                //If the image is loading or loaded, stop.  If it was loading, the loading process will still continue,
                //but once completed it will not get added to the "loaded" list
                mImagesLoaded.remove(img);
                mImagesLoading.remove(img);
        }

        /**
         * Determine whether an Image that you're trying to load has actually been successfully loaded or not
         * 
         * @param img The Image object that you received when you loaded the picture
         * 
         * @return true if the image is loaded and ready to draw, otherwise false.
         */
        public boolean isImageLoaded(Image img)
        {
                return mImagesLoaded.containsKey(img);
        }

        //This callback is used to turn off the image loading flag once an image
        //has been completely loaded, or an error has occurred
        public boolean imageUpdate (Image img, int infoflags, int x, int y, int w, int h)
        {
                if (((infoflags & ALLBITS) == 0) && ((infoflags & ERROR) == 0))
                {
                        //Still loading
                        return true;
                }
                else if ((infoflags&ALLBITS) != 0)
                {                       
                        //Successfully completed
                        String name = mImagesLoading.get(img);

                        if (name != null)
                        {
                                //Add this entry to the loaded list
                                mImagesLoaded.put(img, name);

                        }
                }
                else    
                {
                        String name = mImagesLoading.get(img);
                        if (name==null)
                        {
                                throw new RuntimeException("Image loader error!");
                        }
                        else
                        {
                                throw new RuntimeException("Error loading image: " + name);
                        }

                }

                //At this point loading has finished (either successfully or not.  In either case we removve it from
                //the loading list and return false
                mImagesLoading.remove(img);
                return false;
        }

        /************************
         * ActionListener Methods
         ***********************/

        //This event is called by the Timer whenever it pings
        //This kind of method, which we NEVER call ourselves, but is called by some
        //external class is referred to as a "callback" method
        public void actionPerformed(ActionEvent e)
        {
                //Update the timing error
                //Calculate how long it's been since the last frame, subtract how long we
                //SHOULD have waited
                mNanoError += System.nanoTime() - mLastUpdate - mNanoPerFrame;

                //If the error is greater than one frame, skip a frame
                while (mNanoError > mNanoPerFrame)
                {
                        updateFrame();
                        mNanoError -= mNanoPerFrame;
                }

                //Now do the scheduled upate
                updateFrame();

                //Update the time
                mLastUpdate = System.nanoTime();

                //Ask for the screen to be withdrawn
                repaint();
        }

        /************************
         * KeyListener Methods
         ***********************/
        public void keyPressed(KeyEvent e) {
                //Only use this to store non-printable keys
                if (e.getKeyChar()==KeyEvent.CHAR_UNDEFINED)
                {
                        mCharBuffer.add(new CharacterInfo(e.getKeyCode(), true));
                }
        }

        public void keyReleased(KeyEvent e) {
        }

        public void keyTyped(KeyEvent e) {
                //Use this to store printable keys
                if (e.getKeyChar() != KeyEvent.CHAR_UNDEFINED)
                {
                        mCharBuffer.add(new CharacterInfo(e.getKeyChar(), false));
                }
        }

        /************************
         * MouseListener Methods
         ***********************/
        public void mouseClicked(MouseEvent e) {
                // TODO Auto-generated method stub              
        }

        public void mouseEntered(MouseEvent e) {
                // TODO Auto-generated method stub
                mMouseLocation = new Point (e.getX(), e.getY());
        }

        public void mouseExited(MouseEvent e) {
                // TODO Auto-generated method stub
                mMouseLocation = new Point (e.getX(), e.getY());
        }

        public void mousePressed(MouseEvent e) {
                // TODO Auto-generated method stub
                switch(e.getButton())
                {
                case MouseEvent.BUTTON1: mMouseButtons[0] = e.getClickCount();break;
                case MouseEvent.BUTTON2: mMouseButtons[1] = e.getClickCount();break;
                case MouseEvent.BUTTON3: mMouseButtons[2] = e.getClickCount();break;
                }
        }

        public void mouseReleased(MouseEvent e) {
                switch(e.getButton())
                {
                case MouseEvent.BUTTON1: mMouseButtons[0] = 0;break;
                case MouseEvent.BUTTON2: mMouseButtons[1] = 0;break;
                case MouseEvent.BUTTON3: mMouseButtons[2] = 0;break;
                }
        }

        /************************
         * MouseMotionListener Methods
         ***********************/
        public void mouseMoved(MouseEvent e)
        {
                mMouseLocation = new Point (e.getX(), e.getY());
        }

        public void mouseDragged(MouseEvent e)
        {
                mMouseLocation = new Point (e.getX(), e.getY());
        }

        private class CharacterInfo
        {
                private int mValue;
                private boolean mSpecial;

                public CharacterInfo(int v, boolean s)
                {
                        mValue = v;
                        mSpecial = s;
                }

                public int getValue()
                {
                        return mValue;
                }

                public boolean isSpecial()
                {
                        return mSpecial;
                }
        }
}

