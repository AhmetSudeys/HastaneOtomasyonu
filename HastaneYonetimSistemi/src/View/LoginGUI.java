package View;

import java.awt.EventQueue;
import javax.swing.ImageIcon;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.Box;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import java.awt.SystemColor;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;
import Helper.*;
import Model.Bashekim;
import Model.Doctor;
import Model.Hasta;
import Model.Personel;

public class LoginGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel w_pane;
	private JTextField fld_hastaTc;
	private JTextField fld_doctorTc;
	private JPasswordField fld_doctorPass;
	private DBConnection conn = new DBConnection();
	private JPasswordField fld_hastaPass;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginGUI frame = new LoginGUI();
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
	public LoginGUI() {
		setResizable(false);
		setTitle("Hastane Yönetim Sistemi");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 500, 400);
		w_pane = new JPanel();
		w_pane.setBackground(new Color(255, 255, 255));
		w_pane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(w_pane);
		w_pane.setLayout(null);
		
		//JLabel lbl_logo = new JLabel(new ImageIcon(getClass().getResource("logom.png")));
		JLabel lbl_logo = new JLabel(new ImageIcon(getClass().getResource("/HastaneYonetimSistemi/logom.png")));

		

		lbl_logo.setBounds(187, 10, 100, 82);
		w_pane.add(lbl_logo);
		
		JLabel lblNewLabel = new JLabel("Hastane Yönetim Sistemine Hoşgeldiniz");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblNewLabel.setBounds(79, 102, 397, 21);
		w_pane.add(lblNewLabel);
		
		JTabbedPane w_tabpane = new JTabbedPane(JTabbedPane.TOP);
		w_tabpane.setBackground(Color.WHITE);
		w_tabpane.setBounds(10, 133, 466, 220);
		w_pane.add(w_tabpane);
		
		JPanel w_hastaLogin = new JPanel();
		w_hastaLogin.setBackground(SystemColor.activeCaption);
		w_tabpane.addTab("Hasta Girişi", null, w_hastaLogin, null);
		w_tabpane.setBackgroundAt(0, Color.WHITE);
		w_hastaLogin.setLayout(null);
		
		JLabel lblTc = new JLabel("T.C Numarası:");
		lblTc.setBounds(38, 42, 127, 21);
		lblTc.setFont(new Font("Tahoma", Font.BOLD, 15));
		w_hastaLogin.add(lblTc);
		
		JLabel lblifre = new JLabel("Şifre:");
		lblifre.setBounds(38, 84, 127, 21);
		lblifre.setFont(new Font("Tahoma", Font.BOLD, 15));
		w_hastaLogin.add(lblifre);
		
		fld_hastaTc = new JTextField();
		fld_hastaTc.setFont(new Font("Sansation", Font.PLAIN, 11));
		fld_hastaTc.setBounds(169, 42, 183, 22);
		w_hastaLogin.add(fld_hastaTc);
		fld_hastaTc.setColumns(10);
		
		JButton btn_register = new JButton("Kayıt Ol");
		btn_register.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RegisterGUI rGUI = new RegisterGUI();
				rGUI.setVisible(true);
				dispose();
			}
		});
		btn_register.setFont(new Font("Tahoma", Font.BOLD, 15));
		btn_register.setBounds(27, 147, 183, 36);
		w_hastaLogin.add(btn_register);
		
		JButton btn_hastaLogin = new JButton("Giriş Yap");
		btn_hastaLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (fld_hastaTc.getText().length() == 0 || fld_hastaPass.getText().length() == 0) {
				    Helper.showMsg("fill");
				} else {
					boolean key = true;

					try {
						Connection con = conn.connDb();
						Statement st = con.createStatement();
						ResultSet rs = st.executeQuery("SELECT * FROM User");
						while(rs.next()) {
							if(fld_hastaTc.getText().equals(rs.getString("tcno")) && fld_hastaPass.getText().equals(rs.getString("password"))) {
								if(rs.getString("type").equals("hasta")) {
									
									Hasta hasta = new Hasta();
									hasta.setId(rs.getInt("id"));
									hasta.setPassword(rs.getString("password"));
									hasta.setTcno(rs.getString("tcno"));
									hasta.setName(rs.getString("name"));
									hasta.setType(rs.getString("type"));
									HastaGUI hGUI = new HastaGUI(hasta);
									hGUI.setVisible(true);
									dispose();
									key = false;
									
								}									
							}
						}
					} catch (SQLException e1) {	
						e1.printStackTrace();
					}
					
					if (key) {
						Helper.showMsg("Böyle bir hasta bulunamadı lütfen kayıt olunuz");
					}
				}

			}
		});
		btn_hastaLogin.setFont(new Font("Tahoma", Font.BOLD, 15));
		btn_hastaLogin.setBounds(254, 147, 183, 36);
		w_hastaLogin.add(btn_hastaLogin);
		
		fld_hastaPass = new JPasswordField();
		fld_hastaPass.setBounds(169, 87, 183, 21);
		w_hastaLogin.add(fld_hastaPass);
		
		JPanel w_doctorLogin = new JPanel();
		w_doctorLogin.setBackground(SystemColor.activeCaption);
		w_tabpane.addTab("Doktor ve Personel Girişi", null, w_doctorLogin, null);
		w_doctorLogin.setLayout(null);
		
		JLabel lblTc_1 = new JLabel("T.C Numarası:");
		lblTc_1.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblTc_1.setBounds(38, 42, 127, 21);
		w_doctorLogin.add(lblTc_1);
		
		fld_doctorTc = new JTextField();
		fld_doctorTc.setFont(new Font("Sansation", Font.PLAIN, 11));
		fld_doctorTc.setColumns(10);
		fld_doctorTc.setBounds(169, 42, 183, 22);
		w_doctorLogin.add(fld_doctorTc);
		
		JLabel lblifre_1 = new JLabel("Şifre:");
		lblifre_1.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblifre_1.setBounds(38, 84, 127, 21);
		w_doctorLogin.add(lblifre_1);
		
		JButton btn_doctorLogin = new JButton("Giriş Yap");
		btn_doctorLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				//bu butona tıklandığında fieldlar ve database'de bu verilerle ilgili bir kayıt var mı yok mu diye kontrol edilmeli
				
				if (fld_doctorTc.getText().length()==0||fld_doctorPass.getText().length()==0) {
					Helper.showMsg("fill");
				}else { //girilen değer veritabanında var mı yok mu
					try {
						Connection con = conn.connDb();
						Statement st = con.createStatement();
						ResultSet rs = st.executeQuery("SELECT * FROM User");
						while(rs.next()) {
							if(fld_doctorTc.getText().equals(rs.getString("tcno")) && fld_doctorPass.getText().equals(rs.getString("password"))) {
								if(rs.getString("type").equals("bashekim")) {
									
									Bashekim bhekim = new Bashekim();
									bhekim.setId(rs.getInt("id"));
									bhekim.setPassword(rs.getString("password"));
									bhekim.setTcno(rs.getString("tcno"));
									bhekim.setName(rs.getString("name"));
									bhekim.setType(rs.getString("type"));
									BashekimGUI bGUI = new BashekimGUI(bhekim);
									bGUI.setVisible(true);
									dispose();
								}
								
								if(rs.getString("type").equals("doktor")) {
									Doctor doctor = new Doctor();
									doctor.setId(rs.getInt("id"));
									doctor.setPassword(rs.getString("password"));
									doctor.setTcno(rs.getString("tcno"));
									doctor.setName(rs.getString("name"));
									doctor.setType(rs.getString("type"));
									DoctorGUI dGUI = new DoctorGUI(doctor);
									dGUI.setVisible(true);
									dispose();
									
								}
								
								 if (rs.getString("type").equals("personel")) {
			                            Personel personel = new Personel(
			                                rs.getInt("id"),
			                                rs.getString("name"),
			                                rs.getString("type")
			                            );
			                            PersonelGUI pGUI = new PersonelGUI(personel);
			                            pGUI.setVisible(true);
			                            dispose(); // Mevcut Login ekranını kapat
			                            break; // Döngüden çık
			                        }
								
							}
						}
					} catch (SQLException e1) {	
						e1.printStackTrace();
					}
				}
				
			}
		});
		btn_doctorLogin.setFont(new Font("Tahoma", Font.BOLD, 15));
		btn_doctorLogin.setBounds(27, 147, 396, 36);
		w_doctorLogin.add(btn_doctorLogin);
		
		fld_doctorPass = new JPasswordField();
		fld_doctorPass.setBounds(169, 86, 183, 21);
		w_doctorLogin.add(fld_doctorPass);
		
		JLabel lblNewLabel_1 = new JLabel("Dökümantasyondaki listede yer alan doktor veya başhekim bilgileriyle giriş yapmalısınız.");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 8));
		lblNewLabel_1.setBounds(22, 10, 401, 36);
		w_doctorLogin.add(lblNewLabel_1);
	}
}
