/*Name: GradSchoolsGUI.java
 * @author Sara Burns and Katherine Kjeer
 * Date: 10/2/2013
 * Creates a frame with tabbed panes functioning according to GradSchools.java */
import javax.swing.*;

public class GradSchoolsGUI {
  public static void main(String[] args){
  //create new Frame
  JFrame frame = new JFrame("Grad Schools");
  frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  
  //create GradSchools object, Tabbed Pane and add tabs
  GradSchools gs = new GradSchools();
  JTabbedPane tp = new JTabbedPane();
  tp.addTab("About", new AboutPanel());
  tp.addTab("Add School", new AddSchoolPanel(gs));
  tp.addTab("Evaluate", new EvalPanel(gs));
  
  frame.getContentPane().add(tp);
  
  frame.pack();
  frame.setVisible(true);
}
}