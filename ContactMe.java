package tankwar.ui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Toolkit;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ContactMe extends JFrame {

	private JPanel contentPane;
	public Welcome UpFrame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ContactMe frame = new ContactMe();
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
	public ContactMe() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(ContactMe.class.getResource("/image/火焰.png")));
		setTitle("ContactMe");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 450);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblAuthornbztx = new JLabel("Author：nbztx");
		lblAuthornbztx.setFont(new Font("微软雅黑", Font.PLAIN, 18));
		lblAuthornbztx.setBounds(116, 144, 149, 18);
		contentPane.add(lblAuthornbztx);
		
		JLabel lblEmailnbztxcom = new JLabel("E-mail：nbztx@126.com");
		lblEmailnbztxcom.setFont(new Font("微软雅黑", Font.PLAIN, 18));
		lblEmailnbztxcom.setBounds(116, 189, 234, 18);
		contentPane.add(lblEmailnbztxcom);
		
		JLabel lblQq = new JLabel("QQ：601349735");
		lblQq.setFont(new Font("微软雅黑", Font.PLAIN, 18));
		lblQq.setBounds(116, 233, 159, 18);
		contentPane.add(lblQq);
		
		JLabel lblGithubp = new JLabel("GitHub：PrinterFranklin");
		lblGithubp.setFont(new Font("微软雅黑", Font.PLAIN, 18));
		lblGithubp.setBounds(116, 280, 207, 18);
		contentPane.add(lblGithubp);
		
		JLabel lblNewLabel = new JLabel("Contact Me");
		lblNewLabel.setFont(new Font("微软雅黑", Font.BOLD, 30));
		lblNewLabel.setBounds(198, 61, 188, 52);
		contentPane.add(lblNewLabel);
		
		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ReturnActionPerformed(e);
			}
		});
		btnBack.setBounds(237, 336, 113, 27);
		contentPane.add(btnBack);
		
		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setIcon(new ImageIcon(ContactMe.class.getResource("/image/眼镜.png")));
		lblNewLabel_1.setBounds(401, 189, 101, 52);
		contentPane.add(lblNewLabel_1);
		
		//设置JFrame居中
		this.setLocationRelativeTo(null);
	}

	protected void ReturnActionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		this.setVisible(false);
		UpFrame.setVisible(true);
	}

}
