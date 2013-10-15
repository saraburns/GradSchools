/*Name: AboutPanel.java
 * @author Sara Burns and Katherine Kjeer
 * CS 230 Assignment 4
 * Creates a panel with information about the GradSchools program
 * Date: 10/2/2013 
 */
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.applet.*;
import java.net.URL;

public class AboutPanel extends JPanel{
  
  //instance variables
  //all used in the centerPanel
  
  //label containing information and instructions
  private JLabel aboutlabel;
  
  //image containing caps being thrown into the air
  private ImageIcon cap;
  
  //clip of "Pomp and Circumstance"
  private AudioClip clip;
  
  //buttons to play and stop the clip
  private JButton play, stop;
  
  //constructor method
  public AboutPanel() {
    //set the size and layout of the AboutPanel
    setPreferredSize(new Dimension(1000, 400));
    setLayout(new BorderLayout());
    
    //add the various panels that fit with the BorderLayout to the AboutPanel
    add(makeNorthPanel(), BorderLayout.PAGE_START);
    add(makeWestPanel(), BorderLayout.LINE_END);
    add(makeCenterPanel(), BorderLayout.CENTER);
    add(makeEastPanel(), BorderLayout.LINE_START);
    add(makeSouthPanel(), BorderLayout.PAGE_END);
                          
  }
  
  /*
   * makes the north panel.
   * this panel doesn't contain anything
   * except a blue background for aesthetics.
   */
  private JPanel makeNorthPanel () {
    JPanel northPanel = new JPanel();
    northPanel.setBackground(Color.blue);
    return northPanel;
  }
  
  
  /*
   * makes the west panel.
   * this panel doesn't contain anything
   * excpet a blue background for aesthetics.
   */
  private JPanel makeWestPanel () {
    JPanel westPanel = new JPanel();
    westPanel.setBackground(Color.blue);
    return westPanel;
  }
  
  /*
   * makes the center panel.
   * this panel contains the label
   * that gives information and instructions
   * on the GUI.
   */
  private JPanel makeCenterPanel () {
    //set up the center panel
    JPanel centerPanel = new JPanel();
    centerPanel.setBackground(Color.lightGray);
    
    //set up the image
    cap = new ImageIcon("cap2.jpg");
    
    //set up the clip
    URL url = null;
    try {
      url = new URL("file", "localhost", "pomp_loop.wav");
    } catch (Exception exception) {}
    
    clip = JApplet.newAudioClip(url);
    
    //set up play and stop buttons
    play = new JButton("Play");
    stop = new JButton("Stop");
    play.addActionListener(new ButtonListener());
    stop.addActionListener(new ButtonListener());
    
    //create label with text and image, and set label font
    String abouttext = "<html>Choose the Grad School that fits you best!<br>The next killer app designed by SBKK Inc."
                              + "<p>-----<p>Click the buttons to the right to play or stop some inspiring music.<br>" 
                              + "Select the Schools tab to add or delete Schools.<br>Then select the Evaluate tab to"
                              + " evaluate them.<p>Simple, eh?</html>";
    aboutlabel = new JLabel(abouttext, cap, JLabel.LEFT);
    aboutlabel.setFont(new Font("Matura MT Script Capitals", Font.BOLD, 18));
    
    //add label and buttons to panel
    centerPanel.add(aboutlabel);
    centerPanel.add(play);
    centerPanel.add(stop);
    
    return centerPanel;
  }
  
  /*
   * makes the east panel.
   * this panel doesn't contain anything
   * except a blue background for aesthetics.
   */
  private JPanel makeEastPanel () {
    JPanel eastPanel = new JPanel();
    eastPanel.setBackground(Color.blue);
    return eastPanel;
  }
  
  /*
   * makes the south panel.
   * this panel doesn't contain anything
   * except a blue background for aesthetics.
   */
  private JPanel makeSouthPanel () {
    JPanel southPanel = new JPanel();
    southPanel.setBackground(Color.blue);
    return southPanel;
  }
  
  /*
   * ButtonListener class
   * listener for the play and stop buttons
   * plays or stops the clip,
   * depending on which button the user pressed
   */
  private class ButtonListener implements ActionListener {
    public void actionPerformed (ActionEvent event) {
      if (event.getSource() == play) {
        clip.play();
      }
      else {
        if (clip != null)
        clip.stop();
      }
    }
  }
}