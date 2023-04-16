import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JTextArea;
import java.awt.Font;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

public class Gui extends JFrame {
	private static final long serialVersionUID = 1L;

	static Main m = new Main();

	private JPanel contentPane;
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
	public Gui() {
		setTitle("Augustinus");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 800);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{10, 10, 99, 0, 0, 0, 151, 0};
		gbl_contentPane.rowHeights = new int[]{27, 20, 27, -89, 375, 27, 27, 27, 27, 27, 27, 27, 27, 0};
		gbl_contentPane.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		JLabel LabelClef = new JLabel("Clave:");
		LabelClef.setFont(new Font("Tahoma", Font.PLAIN, 22));
		GridBagConstraints gbc_LabelClef = new GridBagConstraints();
		gbc_LabelClef.fill = GridBagConstraints.BOTH;
		gbc_LabelClef.insets = new Insets(0, 0, 5, 5);
		gbc_LabelClef.gridx = 0;
		gbc_LabelClef.gridy = 0;
		contentPane.add(LabelClef, gbc_LabelClef);
		
		JComboBox<String> iClef = new JComboBox<String>();
		iClef.setFont(new Font("Tahoma", Font.PLAIN, 22));
		iClef.setModel(new DefaultComboBoxModel<String>(new String[] {"C1", "C2", "C3", "C4", "C5"}));
		GridBagConstraints gbc_iClef = new GridBagConstraints();
		gbc_iClef.fill = GridBagConstraints.BOTH;
		gbc_iClef.insets = new Insets(0, 0, 5, 5);
		gbc_iClef.gridx = 1;
		gbc_iClef.gridy = 0;
		contentPane.add(iClef, gbc_iClef);
		
		JLabel LabelNote = new JLabel("Nota:");
		LabelNote.setFont(new Font("Tahoma", Font.PLAIN, 22));
		GridBagConstraints gbc_LabelNote = new GridBagConstraints();
		gbc_LabelNote.fill = GridBagConstraints.BOTH;
		gbc_LabelNote.insets = new Insets(0, 0, 5, 5);
		gbc_LabelNote.gridx = 3;
		gbc_LabelNote.gridy = 0;
		contentPane.add(LabelNote, gbc_LabelNote);
		
		JComboBox<String> iNote = new JComboBox<String> ();
		iNote.setFont(new Font("Tahoma", Font.PLAIN, 22));
		iNote.setModel(new DefaultComboBoxModel<String> (new String[] {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M"}));
		GridBagConstraints gbc_iNote = new GridBagConstraints();
		gbc_iNote.fill = GridBagConstraints.BOTH;
		gbc_iNote.insets = new Insets(0, 0, 5, 5);
		gbc_iNote.gridx = 4;
		gbc_iNote.gridy = 0;
		contentPane.add(iNote, gbc_iNote);
		
		JTextArea iText = new JTextArea();
		iText.setLineWrap(true);
		iText.setFont(new Font("Tahoma", Font.PLAIN, 22));
		iText.setColumns(50);
		iText.setRows(200);
		GridBagConstraints gbc_iText = new GridBagConstraints();
		gbc_iText.fill = GridBagConstraints.BOTH;
		gbc_iText.gridheight = 8;
		gbc_iText.gridwidth = 6;
		gbc_iText.insets = new Insets(0, 0, 5, 0);
		gbc_iText.gridx = 1;
		gbc_iText.gridy = 2;
		contentPane.add(iText, gbc_iText);
		
		JLabel LabelText = new JLabel("Texto:");
		LabelText.setFont(new Font("Tahoma", Font.PLAIN, 22));
		GridBagConstraints gbc_LabelText = new GridBagConstraints();
		gbc_LabelText.insets = new Insets(0, 0, 5, 5);
		gbc_LabelText.gridx = 0;
		gbc_LabelText.gridy = 3;
		contentPane.add(LabelText, gbc_LabelText);
				
						
						JButton Button = new JButton("Gerar");
						Button.setFont(new Font("Tahoma", Font.PLAIN, 22));
						Button.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								iText.setText(Main.func(iText.getText().trim(),iClef.getSelectedItem().toString().toLowerCase(), iNote.getSelectedItem().toString().toLowerCase()));
							}
						});
						GridBagConstraints gbc_Button = new GridBagConstraints();
						gbc_Button.insets = new Insets(0, 0, 5, 5);
						gbc_Button.fill = GridBagConstraints.VERTICAL;
						gbc_Button.gridx = 5;
						gbc_Button.gridy = 11;
						contentPane.add(Button, gbc_Button);
		

	}
}
