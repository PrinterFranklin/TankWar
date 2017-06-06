package tankwar.ui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import tankwar.entity.TankClient;

import java.awt.Toolkit;
import javax.swing.JButton;
import java.awt.Font;
import javax.swing.JRadioButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.ButtonGroup;

public class Welcome extends JFrame {

	private JPanel contentPane;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JRadioButton rdbtnNoob;
	private JRadioButton rdbtnNormal;
	private JRadioButton rdbtnHard;
	private JRadioButton rdbtnHell;
	private int difficulty=1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Welcome frame = new Welcome();
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
	public Welcome() {
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage(Welcome.class.getResource("/image/火焰.png")));
		setTitle("UltraTankWar");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 450);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnNewButton = new JButton("Start Game");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PlayActionPerformed(e);
			}
		});
		btnNewButton.setFont(new Font("微软雅黑", Font.BOLD, 20));
		btnNewButton.setBounds(204, 240, 162, 50);
		contentPane.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Help");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				HelpActionPerformed(e);
			}
		});
		btnNewButton_1.setFont(new Font("微软雅黑", Font.BOLD, 18));
		btnNewButton_1.setBounds(204, 303, 162, 27);
		contentPane.add(btnNewButton_1);
		
		JButton btnContactMe = new JButton("Contact Me");
		btnContactMe.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ContactActionPerformed(e);
			}
		});
		btnContactMe.setFont(new Font("微软雅黑", Font.BOLD, 18));
		btnContactMe.setBounds(204, 343, 162, 27);
		contentPane.add(btnContactMe);
		
		rdbtnNoob = new JRadioButton("Noob");
		buttonGroup.add(rdbtnNoob);
		rdbtnNoob.setBounds(122, 193, 75, 27);
		contentPane.add(rdbtnNoob);
		
		rdbtnHell = new JRadioButton("Hell");
		buttonGroup.add(rdbtnHell);
		rdbtnHell.setBounds(415, 193, 75, 27);
		contentPane.add(rdbtnHell);
		
		rdbtnHard = new JRadioButton("Hard");
		buttonGroup.add(rdbtnHard);
		rdbtnHard.setBounds(320, 193, 75, 27);
		contentPane.add(rdbtnHard);
		
		rdbtnNormal = new JRadioButton("Normal");
		rdbtnNormal.setSelected(true);
		buttonGroup.add(rdbtnNormal);
		rdbtnNormal.setBounds(213, 193, 84, 27);
		contentPane.add(rdbtnNormal);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(Welcome.class.getResource("/image/UltraTankWar.png")));
		lblNewLabel.setBounds(112, 66, 392, 118);
		contentPane.add(lblNewLabel);
		
		//设置JFrame居中
		this.setLocationRelativeTo(null);
	}

	protected void PlayActionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(rdbtnNormal.isSelected())
			this.difficulty=5;
		else if(rdbtnHard.isSelected())
			this.difficulty=10;
		else if(rdbtnHell.isSelected())
			this.difficulty=15;
		else
			this.difficulty=1;
		this.setVisible(false);
		
		TankClient tc = new TankClient();
		TankClient.difficulty=this.difficulty;
		tc.launchFrame();
	}

	protected void ContactActionPerformed(ActionEvent e) {

		this.setVisible(false);
		ContactMe frame = new ContactMe();
		frame.UpFrame=this;
		frame.setVisible(true);
	}

	protected void HelpActionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		this.setVisible(false);
		Help frame = new Help();
		frame.UpFrame=this;
		frame.setVisible(true);
	}
}
