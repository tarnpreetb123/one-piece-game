
package Window;

import javax.swing.*;

/**
 * This class creates the main window for your application
 * Note that to implement your actual program, you need to create a subclass of LASSJPanel,
 * NOT this class!
 * 
 * The main program must be run using the <code>invokeLater</code> command:
 * 
 * e.g. JFrame myFrame = new LASSJFrame(new LASSJPanel(30));
 *              javax.swing.SwingUtilities.invokeLater(myFrame);
 * 
 * 
 */

public final class LASSJFrame extends JFrame implements Runnable {

        private LASSJPanel mPanel;
        
        /**
         * Constructor:  Create a LASSJFrame
         * 
         * @param panel The LASSJPanel on which all of this window's graphics will be drawn.
         */
        public LASSJFrame(LASSJPanel panel)
        {
                //Create the frame
                super();
                mPanel = panel;
        }

        /**
         * This method, when called, will create and display the window.
         * <p>
         * You should <b>NOT</b> call this method directly.  Instead use:
         * <p>
         * <code>javax.swing.SwingUtilities.invokeLater()</code>
         */
        public void run()
        {
                //Tell the frame to actually close when the user clicks the close button
                setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                //Add our panel to the frame
                add(mPanel);

                //Tell the Frame to arrange its contents and display itself
                pack();
                setVisible(true);
        }

}
 
