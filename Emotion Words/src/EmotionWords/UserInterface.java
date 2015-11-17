package EmotionWords;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.*;

public class UserInterface {
	
	public static Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
	public static JFrame frame = new JFrame("Emotion Word Counter");
	public static JPanel jpTextBox = new JPanel(new BorderLayout());
	public static JTextField tfTextField = new JTextField();
	
	// create UI
	public UserInterface(String message) {
		
		//set parameters for frame
		frame.setSize(150, 75);
		frame.setLocationRelativeTo(null); //center window
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setBackground(Color.lightGray);		
		
		//set panel parameters
		jpTextBox.setOpaque(false);
		jpTextBox.add(tfTextField);
		
		//set text field parameters
		tfTextField.setText(message);
		tfTextField.setEditable(false);
		
		//add panel to JFrame
		frame.getContentPane().add(jpTextBox);
		
		//last property to set after all other properties have been set
		//make frame visible
		frame.setVisible(true);

	}
	
	public void setText(String message){
		
		//set text field parameters
		tfTextField.setText(message);
	}
}