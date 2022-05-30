import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class FindSuspectWindow extends Frame {

	
	private int width = 400;
	private int height = 200;
	JTextField tx, tn;
	JButton findSuspect, visualizeNetwork;

	FindSuspectWindow(Registry registry) {
		
		// Window name
		JFrame firstWindow = new JFrame("Find Suspect");
		
		// New Button
		// make button set position
		findSuspect = new JButton("Find");
		findSuspect.setBounds(195, 35, 130, 30);
		
		//	New Button 
		// make Visualize Network
		visualizeNetwork = new JButton("Visualize Network");
		visualizeNetwork.setBounds(100, 80, 170, 30);
		
		visualizeNetwork.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					firstWindow.dispose();
					new SuspectsNetworkWindow(registry);
				} catch (Exception ex) {
					System.out.println(ex);
				}
			}
		});
		
		// make text enter user
		tn = new JTextField("Please enter suspect's name");
		tn.setBounds(20, 40, 170, 20);

		tn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (tn.getText().equals("Please enter suspect's name"))
					tn.setText("");
			}
		});
		
		/*
		 * Creating find button action
		 * Searching the name in the textArea if is in the list
		 * Incorrect text: appearing a message to change the text
		 * Correct suspect: Enter to second window(SuspectPage)
		 */
		findSuspect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String host = tn.getText();
					boolean flag = true;

					for (Suspect aSuspect : registry.getSuspectsList()) {
						if (aSuspect.getName().equals(host)) {
							flag = false;
							firstWindow.dispose();
							new SuspectPageWindow(aSuspect, registry);
						}
					}
					if (flag) {
						JOptionPane.showMessageDialog(null, " Suspect " + host + " Not Found ");
					}
				} catch (Exception ex) {
					System.out.println(ex);
				}
			}
		});

		// add buttons to window
		firstWindow.add(findSuspect);
		firstWindow.add(tn);
		firstWindow.add(visualizeNetwork);
		

		// close window from x button (top right of window)
		firstWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// set window size
		firstWindow.setSize(width, height);

		// make button style
		firstWindow.setLayout(null);

		// make window visible
		firstWindow.setVisible(true);
				
		// set window in screen's center
		firstWindow.setLocationRelativeTo(null);
	}

}
