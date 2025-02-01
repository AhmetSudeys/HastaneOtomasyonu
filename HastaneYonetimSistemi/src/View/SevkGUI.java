package View;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Model.Hasta;
import Model.SevkModul;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.SystemColor;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.awt.event.ActionEvent;

public class SevkGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField tcTF;
	private JTextField hastaneTF;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SevkGUI frame = new SevkGUI();
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
	public SevkGUI() {
		setTitle("Sevk Modül Arayüzü");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 416, 364);
		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.activeCaption);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(SystemColor.activeCaption);
		panel.setBounds(0, 10, 392, 317);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblHastaTc = new JLabel("Hasta T.C:");
		lblHastaTc.setBounds(25, 52, 61, 15);
		lblHastaTc.setFont(new Font("Tahoma", Font.BOLD, 12));
		panel.add(lblHastaTc);
		
		tcTF = new JTextField();
		tcTF.setBounds(175, 51, 159, 19);
		tcTF.setColumns(10);
		panel.add(tcTF);
		
		JLabel lblSevkEdilecekHastane = new JLabel("Sevk Edilecek Hastane");
		lblSevkEdilecekHastane.setBounds(25, 105, 203, 15);
		lblSevkEdilecekHastane.setFont(new Font("Tahoma", Font.BOLD, 12));
		panel.add(lblSevkEdilecekHastane);
		
		hastaneTF = new JTextField();
		hastaneTF.setBounds(175, 104, 159, 19);
		hastaneTF.setColumns(10);
		panel.add(hastaneTF);
		
		JButton gönderBTN = new JButton("Gönder");
		gönderBTN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String tc = tcTF.getText();
				String hastane = hastaneTF.getText();
				int id = 0;
				Hasta xx = new Hasta();
				try {
					 id = xx.getHastaIdByTc(tc);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				SevkModul sevk = new SevkModul(id, hastane);
				String modul = sevk.hastaBilgileriniListele();
				JOptionPane.showMessageDialog(null, modul, "Modül Bilgisi", JOptionPane.INFORMATION_MESSAGE);
			}
		});
		gönderBTN.setFont(new Font("Tahoma", Font.BOLD, 12));
		gönderBTN.setBounds(125, 197, 132, 41);
		panel.add(gönderBTN);
	}
}
