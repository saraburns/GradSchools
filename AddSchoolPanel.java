/*Name: AddSchoolPanel.java
 * @author Sara Burns and Katherine Kjeer
 * CS 230 Assignment 4
 * Creates a panel that allows the user to add a school
 * Date: 10/2/2013 */
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class AddSchoolPanel extends JPanel{
  //instance variables
  
  //north panel
  //label containing instructions on the AddSchoolPanel
  private JLabel instructions;
  
  //center panel
  //labels for each of the components
  private JLabel schoollabel, acadlabel, reslabel, pubslabel;
  //textfield to enter the name of a School
  private JTextField school;
  //combo boxes for each of the ranking factors
  private JComboBox acad, res, pubs;
  //buttons to add and delete a School
  private JButton addschool, deleteschool;
  
  //south panel
  //label containing information about the Schools entered by the user
  private JLabel infolabel;
  
  //grad Schools object
  private GradSchools gradschools;
  //integers used to create a School object to add to the GradSchools school array
  private int a, r, p;
  
  public AddSchoolPanel(GradSchools gs) {
    //assign gradschools, set the layout, background color, and size
    gradschools = gs;
    setLayout(new GridLayout(3,1,20,20));
    setBackground(Color.blue);
    setPreferredSize(new Dimension(1000,400));
    
    //add the three panels
    add(makeNorthPanel());
    add(makeCenterPanel());
    add(makeSouthPanel());
  }
  
  /*
   * makes the north panel.
   * this panel contains the instructions for the AddSchoolPanel.
   */
  private JPanel makeNorthPanel() {
    //creates the panel and sets the background color
    JPanel northPanel = new JPanel();
    northPanel.setBackground(Color.cyan);
    //creates and adds instructions label
    instructions = new JLabel("Fill in the information to add a school, then click \"Add School\"");
    northPanel.add(instructions);
    
    return northPanel;
  }
  
  /*
   * makes the center panel.
   * this panel contains the components used to enter a School.
   */
  private JPanel makeCenterPanel() {
    JPanel centerPanel = new JPanel();
    centerPanel.setBackground(Color.lightGray);
    //creates options the combo boxes
    String[] ratings = new String[] {"...","1","2","3","4","5"};
    
    
    //create School field and label
    schoollabel = new JLabel("School: ");
    school = new JTextField(8);
    centerPanel.add(schoollabel);
    centerPanel.add(school);
    
    //create each factor and its combo box
    acadlabel = new JLabel("Academics: ");
    acad = new JComboBox(ratings);
    acad.addActionListener(new ComboListener());
    centerPanel.add(acadlabel);
    centerPanel.add(acad);
    
    reslabel = new JLabel("Research: ");
    res = new JComboBox(ratings);
    res.addActionListener(new ComboListener());
    centerPanel.add(reslabel);
    centerPanel.add(res);
    
    pubslabel = new JLabel("Publications: ");
    pubs = new JComboBox(ratings);
    pubs.addActionListener(new ComboListener());
    centerPanel.add(pubslabel);
    centerPanel.add(pubs);
    
    //create and add the addschools button
    addschool = new JButton("Add School");
    addschool.addActionListener(new ButtonListener());
    centerPanel.add(addschool);
    
    //create and add the deleteschools button
    deleteschool = new JButton("Delete School");
    deleteschool.addActionListener(new ButtonListener());
    centerPanel.add(deleteschool);
    
    return centerPanel;
  }
  
  /*
   * makes the south panel.
   * this panel displays the Schools entered by the user.
   */
  private JPanel makeSouthPanel() {
    JPanel southPanel = new JPanel();
    southPanel.setBackground(Color.white);
    
    infolabel = new JLabel("Information on the new school will appear here.");
    southPanel.add(infolabel);
    
    return southPanel;
  }
  
  
  /*
   * ComboListener
   * Listener for the combo boxes of the factors (academics, etc)
   * sets the instance variables used to create the School
   * to the values entered into the combo boxes
   */
  private class ComboListener implements ActionListener {
    public void actionPerformed (ActionEvent event) {
      //determine which combo box the user selected a value from
      //(Academics, Research, or Publications)
      //and set the appropriate instance variable to the value that the user selected
      //if the user did not select a value,
      //that instance variable is set to 1
      if (event.getSource() == acad) { 
        if (acad.getSelectedItem().toString().equals("...")) 
          a = 1;
        else 
          a = Integer.parseInt(acad.getSelectedItem().toString());
        
      } else if (event.getSource() == res) {
        if (res.getSelectedItem().toString().equals("...")) 
          r = 1;
        else 
          r = Integer.parseInt(res.getSelectedItem().toString());
        
      } else if (event.getSource() == pubs) {
        if (pubs.getSelectedItem().toString().equals("..."))
          p = 1;
        else
          p = Integer.parseInt(pubs.getSelectedItem().toString());
      }
    }
    
  } //end of class ComboListener
  
  /*
   * ButtonListener
   * Listener for the addschool and deleteschool buttons
   * Creates a School object using the instance variables set by the textfield and combo boxes
   * and adds the School to the GradSchools object.
   * Sets the infolabel to print out the all the Schools in gradschools.
   * if the user evaluates the Schools and then goes back to the AddSchoolPanel,
   * the Schools will be displayed in ranked order.
   * otherwise, the Schools will be displayed in the order that they were added.
   */
  private class ButtonListener implements ActionListener {
    public void actionPerformed (ActionEvent event) {
      //initialize the text that the infolabel displays
      String infolabeltext = "";
      
      //initlialize a name String, used to create a School to add
      String name = school.getText();
      
      if (event.getSource() == addschool) { //user pressed the Add School button
        //if the user did not select a value for a factor, set that factor to 1
        if (acad.getSelectedItem().toString().equals("...")) {
          a = 1;
        }
        if (res.getSelectedItem().toString().equals("...")) {
          r = 1;
        }
        if (pubs.getSelectedItem().toString().equals("...")) {
          p = 1;
        }
        
        //if the user has already entered a School with that name,
        //the School changes its factors to the current values of the combo boxes
        //and no School is added.
        //otherwise, adds the user's School to the GradSchools object.
        if (!name.equals("")) {//check to make sure user has entered a name
          
          //check whether a School with this name has already been entered
          int nameIndex = gradschools.findName(name);
          if (nameIndex == -1) //no School with this name has been entered
            gradschools.addSchool(name, a, r, p);
          else { //there's already a School with this name
            gradschools.getSchools()[nameIndex].setRateAcademics(a);
            gradschools.getSchools()[nameIndex].setRateResearch(r);
            gradschools.getSchools()[nameIndex].setRatePubs(p);
          }

        }
      } else { //the user pressed the Delete School button
        if (!name.equals("")) {//check to make sure user has entered a name
          //delete the School from the gradschools School []
          gradschools.deleteSchool(school.getText());
          
        } 
      }
      
      for (int i = gradschools.getNumSchools()-1; i >= 0; i--) {
            //if an element of the gradschools School [] is not null,
            //add it to the text that the infolabel displays 
              infolabeltext += "<html>" + gradschools.getSchools()[i].toString() + "<br><html>";
            //if (school.getText().equals("")) 
          }
      if (school.getText().equals("")) 
              infolabeltext += "Please enter the name of a school.";
      
      //set the infolabel text
      infolabel.setText(infolabeltext);
    }
  } //end of class ButtonListener
  
} //end of class AddSchoolPanel
