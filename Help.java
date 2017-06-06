package tankwar.ui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextArea;
import java.awt.Toolkit;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Help extends JFrame {

	private JPanel contentPane;
	public Welcome UpFrame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Help frame = new Help();
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
	public Help() {
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage(Help.class.getResource("/image/火焰.png")));
		setTitle("Help");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 450);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Help");
		lblNewLabel.setFont(new Font("微软雅黑", Font.BOLD, 30));
		lblNewLabel.setBounds(240, 71, 79, 40);
		contentPane.add(lblNewLabel);
		
		JLabel lblMove = new JLabel("Move：↑↓←→");
		lblMove.setFont(new Font("微软雅黑", Font.PLAIN, 18));
		lblMove.setBounds(212, 157, 163, 18);
		contentPane.add(lblMove);
		
		JLabel lblNewLabel_1 = new JLabel("Attack：Space");
		lblNewLabel_1.setFont(new Font("微软雅黑", Font.PLAIN, 18));
		lblNewLabel_1.setBounds(212, 188, 139, 24);
		contentPane.add(lblNewLabel_1);
		
		JButton btnNewButton = new JButton("Back");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ReturnActionPerformed(e);
			}
		});
		btnNewButton.setBounds(217, 322, 113, 27);
		contentPane.add(btnNewButton);
		
		JLabel lblNewLabel_2 = new JLabel("UltraAttack：Ctrl");
		lblNewLabel_2.setFont(new Font("微软雅黑", Font.PLAIN, 18));
		lblNewLabel_2.setBounds(212, 225, 163, 18);
		contentPane.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("Cure：F2");
		lblNewLabel_3.setFont(new Font("微软雅黑", Font.PLAIN, 18));
		lblNewLabel_3.setBounds(212, 257, 124, 18);
		contentPane.add(lblNewLabel_3);
		
		//设置JFrame居中
		this.setLocationRelativeTo(null);
	}

	protected void ReturnActionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		this.setVisible(false);
		UpFrame.setVisible(true);
	}
}
