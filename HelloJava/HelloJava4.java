import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JButton;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.FlowLayout;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class HelloJava4
{
    public static void main( String[] args ) {
        JFrame frame = new JFrame( "HelloJava3: The Button Strikes" );

        // add the user's message to our window
        frame.add( new HelloComponent4(args[0]) );
        frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        frame.setSize( 300, 300 );
        frame.setVisible( true );
    }
}

class HelloComponent4
extends JComponent
implements MouseMotionListener, ActionListener, Runnable
{
    // class variables
    String theMessage;
    int messageX = 125, messageY = 95;

    JButton theButton;

    int colorIndex;
    static Color[] someColors = {
        Color.black,
        Color.red,
        Color.green,
        Color.blue,
        Color.magenta
    };

    boolean blinkState;
    // End class variables

    // Constructor
    public HelloComponent4( String message ) {
        theMessage = message;
        theButton = new JButton( "Change Color" );

        setLayout( new FlowLayout() );
        add( theButton );

        addMouseMotionListener( this );
        theButton.addActionListener( this );

        Thread t = new Thread( this );
        t.start();
    }
    // End of Constructor

    // Class methods
    synchronized private void changeColor() {
        // Change the index to the next color, awkwardly.
        if (++colorIndex == someColors.length) 
            colorIndex = 0;
        setForeground( currentColor() );
        repaint();
    }

    synchronized private Color currentColor() {
        return someColors[colorIndex];
    }
    // End of class methods

    // override JComponent method
    public void paintComponent( Graphics g ) {
        g.setColor( blinkState ? getBackground() : currentColor() );
        g.drawString( theMessage, messageX, messageY );
    }

    // MouseMotionListener methods
    public void mouseDragged( MouseEvent e ) {
        messageX = e.getX();
        messageY = e.getY();

        repaint();
    }

    public void mouseMoved( MouseEvent e ) {}
    // End of MouseMotionListener methods

    // ActionListener methods
    public void actionPerformed( ActionEvent e ) {
        // somebody pushed the button!
        if (e.getSource() == theButton)
            changeColor();
    }
    // End of ActionListener methods

    // Runnable methods
    public void run() {
        try {
            while(true) {
                blinkState = !blinkState; // toggle blinkState
                repaint();
                Thread.sleep(300);
            }
        } catch (InterruptedException ie) {}
    }
    // End of Runnable methods
}