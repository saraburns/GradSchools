/*Name: EvalPanel.java
 * @author Sara Burns and Katherine Kjeer
 * Date: 10/2/2013
 * Creates a panel which allows the user to evaluate a school based on certain criteria. */
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.io.*;
public class EvalPanel extends JPanel {
  //instance variables
  
  //splitPanes contain two panels each
  //split contains sliderPanel and resultPanel
  //resultPanel contains saveFilePanel and rankedSchoolsPanel
  private JSplitPane splitPane, resultPanel;
  
  //the individual panels that make up the splitPanes
  //sliderPanel contains the sliders
  //rankedSchoolPanel contains a label with the Schools in ranked order
  //saveFile contains componenets that allow the user to write the ranked Schools to a file
  private JPanel sliderPanel, rankedSchoolsPanel, saveFilePanel;
  
  //for the sliderPanel:
  //sliders and labels for each of the ranking factors
  private JSlider acadSlider, resSlider, pubsSlider;
  private JLabel acadLabel, resLabel, pubsLabel;
  
  //for the rankedSchoolsPanel:
  private JLabel resultLabel;
  
  //for the saveFilePanel
  private JLabel fileNameLabel;
  private JTextField fileName;
  private JButton save;
  
  //GradSchools variable
  private GradSchools grad;
  
  //constructor
  public EvalPanel (GradSchools gs) {
    //set the GradSchools grad variable
    grad = gs;
    
    //set up the panel containing the sliders
    sliderPanel = new JPanel();
    sliderPanel.setLayout(new BoxLayout(sliderPanel, BoxLayout.X_AXIS));
    sliderPanel.setBackground(Color.white);
    
    //set up each of the sliders (and their labels):
    
    //set up the academics slider
    acadLabel = new JLabel("Academics: ");
    acadSlider = new JSlider(JSlider.HORIZONTAL, 0, 5, 0);
    acadSlider.setMajorTickSpacing(1);
    acadSlider.setPaintTicks(true);
    acadSlider.setPaintLabels(true);
    acadSlider.setAlignmentX(Component.LEFT_ALIGNMENT);
    
    //set up the research slider
    resLabel = new JLabel("Research: ");
    resSlider = new JSlider(JSlider.HORIZONTAL, 0, 5, 0);
    resSlider.setMajorTickSpacing(1);
    resSlider.setPaintTicks(true);
    resSlider.setPaintLabels(true);
    resSlider.setAlignmentX(Component.LEFT_ALIGNMENT);
    
    //set up the publications slider
    pubsLabel = new JLabel("Publications: ");
    pubsSlider = new JSlider(JSlider.HORIZONTAL, 0, 5, 0);
    pubsSlider.setMajorTickSpacing(1);
    pubsSlider.setPaintTicks(true);
    pubsSlider.setPaintLabels(true);
    pubsSlider.setAlignmentX(Component.LEFT_ALIGNMENT);
    
    //add listeners to the three sliders
    SliderListener listener = new SliderListener();
    acadSlider.addChangeListener(listener);
    resSlider.addChangeListener(listener);
    pubsSlider.addChangeListener(listener);
    
    //add the sliders to the sliderPanel
    //use rigidAreas to control the appearance of the sliderPanel
    sliderPanel.add(acadLabel);
    sliderPanel.add(acadSlider);
    sliderPanel.add(Box.createRigidArea(new Dimension(20, 0)));
    sliderPanel.add(resLabel);
    sliderPanel.add(resSlider);
    sliderPanel.add(Box.createRigidArea(new Dimension(20, 0)));
    sliderPanel.add(pubsLabel);
    sliderPanel.add(pubsSlider);
    
    //set up the rankedSchools panel
    rankedSchoolsPanel = new JPanel();
    rankedSchoolsPanel.setBackground(Color.cyan);

    
    //set up the result label
    resultLabel = new JLabel();
    resultLabel.setText("The top school(s) will be displayed here.");
    
    //set up the label, textfield and button used to print to a file
    fileName = new JTextField(8);
    save = new JButton("Save");
    fileNameLabel = new JLabel("File Name: ");
    save.addActionListener(new ButtonListener());
    

    //add the result label to the rankedSchools panel
    rankedSchoolsPanel.add(resultLabel);
    
    //set up the saveFile panel
    saveFilePanel = new JPanel();
    saveFilePanel.setBackground(Color.blue);
    
    //add the save components to the saveFile panel
    saveFilePanel.add(fileNameLabel);
    saveFilePanel.add(fileName);
    saveFilePanel.add(save);
    
    //set up the panel containing the evaluation results
    resultPanel = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
    resultPanel.setBackground(Color.blue);
    resultPanel.setBottomComponent(rankedSchoolsPanel);
    resultPanel.setTopComponent(saveFilePanel);
    
    
    //set up the split pane
    splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
    splitPane.setTopComponent(sliderPanel);
    splitPane.setBottomComponent(resultPanel);
    splitPane.setPreferredSize(new Dimension(1000, 400));
    
    //add the split pane to the EvalPanel
    add(splitPane);
    
    
  }
  
  /*
   * SliderListener
   * listener for the three sliders
   * uses the weights given by the slider values
   * to rank the Schools and prints the Schools
   * in ranked order.
   */
  private class SliderListener implements ChangeListener {
    public void stateChanged (ChangeEvent event) {
      //find the weights that the user entered using the sliders
      int weightAcad = acadSlider.getValue();
      int weightRes = resSlider.getValue();
      int weightPubs = pubsSlider.getValue();
      
      //use the above weights to rank the Schools,
      //using methods from the GradSchools class
      grad.computeRatings(weightAcad, weightRes, weightPubs);
      grad.rankSchools("Overall");
      
      //set the result label to display the ranked Schools:
      //if the user entered less than three Schools,
      //only display the top School.
      //if the user entered at least three Schools,
      //display the top three Schools, with the top School displayed first.
      String resultLabelText = "";
      if (grad.getNumSchools() < 3) {
      resultLabelText = "The top school is: " + grad.getTop().toString();
      } else {
        resultLabelText = "<html>The top three schools are:<p>";
        for (int i = 0; i < 3; i++) {
          resultLabelText += "<html><p>" + grad.getTopThree()[i].toString() + "<p><html>";
        }
      }
      resultLabel.setText(resultLabelText);
    }
    
  } //end of class SliderListener
  
  /*
   * ButtonListener
   * listener for the save button
   * writes the Schools in ranked order
   * to a file with fileName specified in the 
   * fileName textField.
   */
  private class ButtonListener implements ActionListener {
    public void actionPerformed (ActionEvent event) {
      try {
        PrintWriter writer = new PrintWriter(fileName.getText());
        //writes an introductory statement
        writer.println("These are the schools in ranked order, using the following weights" + "\n");
        writer.println("Academic Weight: " + acadSlider.getValue() + "   Research Weight: " + resSlider.getValue() +
                       "   Publications Weight: " + pubsSlider.getValue() + "\n");
        //writes each School, in ranked order
        for (int i = 0; i < grad.getNumSchools(); i++) {
          writer.println(grad.getSchools()[i].toString());
        }
        writer.close();
      } catch (FileNotFoundException e) {}
        
    }
  }
  
  
  
  
} //end of class EvalPanel