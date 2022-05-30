import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class SuspectPageWindow extends JComponent {
	private int width = 600;
	private int height = 800;
	JButton backToSearchScreen, findSMS;
	JTextField nameSuspect, codeNameSuspect, phoneNumber;
	JLabel partnersLabel, suspectPhones, sugSuspectLabel;
	JTextArea textPosts, partnersArea, sugSuspects;

	SuspectPageWindow(Suspect suspect, Registry registry) {

		// Window name
		JFrame window = new JFrame("Suspect Page");

		// New Button
		// make button set position
		backToSearchScreen = new JButton("Back to Search Screen");
		backToSearchScreen.setBounds(200, 700, 200, 30);

		backToSearchScreen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				window.dispose();
				new FindSuspectWindow(registry);
			}
		});

		// make text have suspect name
		nameSuspect = new JTextField(suspect.getName());
		nameSuspect.setBounds(20, 5, 150, 20);

		// make text have suspect code name
		codeNameSuspect = new JTextField(suspect.getCodeName());
		codeNameSuspect.setBounds(180, 5, 170, 20);

		// make phone Labels
		for (int i = 0; i < suspect.getPhoneList().size(); i++) {
			suspectPhones = new JLabel(suspect.toStringSuspects(i));
			suspectPhones.setBounds(380, -90 + (i * 15), 300, 200);
			window.add(suspectPhones);

		}

		// place phone number
		phoneNumber = new JTextField("");
		phoneNumber.setBounds(20, 100, 150, 20);

		// SMS Post Area
		textPosts = new JTextArea();
		textPosts.setBounds(195, 100, 250, 200);

		// New Button
		// make button set position
		findSMS = new JButton("Find SMS");
		findSMS.setBounds(450, 100, 100, 30);

		/*
		 * Creating "Find SMS" action: Testing if text in textArea is empty or
		 * incorrect. If it is correct: I cross the suspect's phones and get the
		 * messages between the suspects by calling getMessagesBetween I have create in
		 * registry
		 */
		findSMS.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String host = phoneNumber.getText();
					if (!(host.equals(""))) {// pressing the button with nothing in text area
						int count = 0;
						for (int i = 0; i < suspect.getPhoneList().size(); i++) {
							ArrayList<SMS> listOfMessages = registry.getMessagesBetween(suspect.toStringSuspects(i),
									host);

							System.out.println("list oF messages : " + listOfMessages);

							for (SMS sms : listOfMessages)
								textPosts.getDocument().insertString(0, sms.getSms() + "\n", null);

							System.out.println("List size " + listOfMessages.size());
							if (listOfMessages.isEmpty()) {// That means the user type something that is // not in the
															// list
								count++;
							}
						}
						// if count is more than suspect's phone numbers, that means phone is no valid
						// or there is no contact with this number
						if (count > suspect.getPhoneList().size() - 1)
							JOptionPane.showMessageDialog(null, "There are no results for this number");
					} else {
						JOptionPane.showMessageDialog(null, "FAILED:\nPlease enter a number in the field");
					}

				} catch (Exception ex) {
					System.out.println(ex);
				}
			}
		});

		// Partners label
		partnersLabel = new JLabel("Partners");
		partnersLabel.setBounds(120, 350, 300, 200);

		// Partners Area
		Collections.sort(suspect.getProbableSuspectList());
		partnersArea = new JTextArea();
		String stringWithSuspects = suspect.getSuspects();
		partnersArea.append(stringWithSuspects);
		partnersArea.setBounds(195, 360, 250, 200);

		// Suggest Suspects
		suspect.suggestSuspects();
		sugSuspects = new JTextArea(suspect.toStringSuggestFriend());
		sugSuspects.setBounds(200, 580, 370, 115);

		// Suggest Suspects label
		sugSuspectLabel = new JLabel("Suggested Partners ---->");
		sugSuspectLabel.setBounds(10, 580, 370, 115);

		// add buttons to the window
		window.add(backToSearchScreen);
		window.add(nameSuspect);
		window.add(codeNameSuspect);
		window.add(phoneNumber);
		window.add(findSMS);
		window.add(textPosts);
		window.add(partnersLabel);
		window.add(partnersArea);
		window.add(sugSuspects);
		window.add(sugSuspectLabel);

		// close window when press X on the top of the window
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// set window size
		window.setSize(width, height);

		// make button style
		window.setLayout(null);

		// make window visible
		window.setVisible(true);

		// set window in screen's center
		window.setLocationRelativeTo(null);
	}

}
