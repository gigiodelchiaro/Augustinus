
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JTextArea;
import java.awt.Font;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Gui extends JFrame {
	static Main m = new Main();

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Gui frame = new Gui();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
	}

	/**
	 * Create the frame.
	 */
	public Gui() {
		setTitle("Augustinus");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 500, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Clave:");
		lblNewLabel.setBounds(25, 13, 54, 22);
		contentPane.add(lblNewLabel);
		
		JComboBox iClef = new JComboBox();
		iClef.setModel(new DefaultComboBoxModel(new String[] {"C1", "C2", "C3", "C4", "C5"}));
		iClef.setBounds(25, 32, 40, 22);
		contentPane.add(iClef);
		
		JLabel lblNewLabel_1 = new JLabel("Nota:");
		lblNewLabel_1.setBounds(25, 72, 54, 22);
		contentPane.add(lblNewLabel_1);
		
		JComboBox iNote = new JComboBox();
		iNote.setModel(new DefaultComboBoxModel(new String[] {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M"}));
		iNote.setBounds(25, 93, 40, 22);
		contentPane.add(iNote);
		
		JLabel lblNewLabel_2 = new JLabel("Texto:");
		lblNewLabel_2.setBounds(89, 17, 46, 14);
		contentPane.add(lblNewLabel_2);
		
		JTextArea iText = new JTextArea();
		iText.setLineWrap(true);
		iText.setFont(new Font("Tahoma", Font.PLAIN, 11));
		iText.setColumns(22);
		iText.setRows(120);
		iText.setBounds(89, 34, 385, 120);
		contentPane.add(iText);
		
		JButton Button = new JButton("Gerar");
		Button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				iText.setText(m.func(iText.getText(),iClef.getSelectedItem().toString().toLowerCase(), iNote.getSelectedItem().toString().toLowerCase()));
			}
		});
		Button.setBounds(232, 165, 84, 23);
		contentPane.add(Button);
	}
}
