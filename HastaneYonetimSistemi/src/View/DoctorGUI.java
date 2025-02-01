package View;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import Model.Doctor;

import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JTabbedPane;
import java.awt.SystemColor;
import com.toedter.calendar.JDateChooser;

import Helper.Helper;

import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.awt.event.ActionEvent;

public class DoctorGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel w_pane;
	private static Doctor doctor = new Doctor();
	private JTable table_whour;
	private DefaultTableModel whourModel;
	private Object[] whourData = null;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DoctorGUI frame = new DoctorGUI(doctor);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * @throws SQLException 
	 */
	public DoctorGUI(Doctor doctor) throws SQLException {
		
		whourModel = new DefaultTableModel();
		Object[] colWhour = new Object[2];
		colWhour[0] = "ID";
		colWhour[1] = "Tarih";
		whourModel.setColumnIdentifiers(colWhour);
		whourData = new Object[2];
		for (int i = 0; i < doctor.getWhourList(doctor.getId()).size(); i++) {
		    whourData[0] = doctor.getWhourList(doctor.getId()).get(i).getId();
		    whourData[1] = doctor.getWhourList(doctor.getId()).get(i).getWdate();
		    whourModel.addRow(whourData);
		}

		
		
		setResizable(false);
		setTitle("Hastane Yönetim Sistemi");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 750, 500);
		w_pane = new JPanel();
		w_pane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(w_pane);
		w_pane.setLayout(null);

		JLabel lblNewLabel = new JLabel("Hoşgeldiniz, Sayın " + doctor.getName());
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblNewLabel.setBounds(10, 20, 348, 30);
		w_pane.add(lblNewLabel);

		JButton btnNewButton = new JButton("Çıkış Yap");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LoginGUI login = new LoginGUI();
		        login.setVisible(true);
		        dispose();
			}
		});
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnNewButton.setBounds(601, 21, 125, 28);
		w_pane.add(btnNewButton);

		JTabbedPane w_tab = new JTabbedPane(JTabbedPane.TOP);
		w_tab.setBackground(SystemColor.activeCaption);
		w_tab.setBounds(10, 71, 716, 392);
		w_pane.add(w_tab);

		JPanel w_whour = new JPanel();
		w_whour.setBackground(SystemColor.activeCaption);
		w_tab.addTab("Çalışma Saatleri", null, w_whour, null);
		w_whour.setLayout(null);

		JDateChooser select_date = new JDateChooser();
		select_date.setBounds(10, 10, 130, 26);
		w_whour.add(select_date);

		JComboBox select_time = new JComboBox();
		select_time.setFont(new Font("Tahoma", Font.BOLD, 12));
		select_time.setModel(new DefaultComboBoxModel(new String[] { "10:00", "10:30", "11:00", "11:30", "12:00",
				"13:30", "14:00", "14:30", "15:00", "15:30", "16:00" }));
		select_time.setBounds(150, 10, 64, 26);
		w_whour.add(select_time);

		JButton btn_addWhour = new JButton("Ekle");
		btn_addWhour.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				String date = "";
				try {
					 date = sdf.format(select_date.getDate());
				} catch (Exception e2) {
					// TODO: handle exception
				}
				if (date.length() == 0) {
					Helper.showMsg("Lütfen geçerli bir tarih giriniz !");
				} else {
					String time = " " + select_time.getSelectedItem().toString() + ":00";
					String selectDate = date + time;
					try {
						boolean control = doctor.addWhour(doctor.getId(), doctor.getName(), selectDate);
						if (control) {
							Helper.showMsg("success");
							updateWhourModel(doctor);
						} else {
							Helper.showMsg("error");
						}
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
		btn_addWhour.setFont(new Font("Tahoma", Font.BOLD, 14));
		btn_addWhour.setBounds(224, 10, 110, 26);
		w_whour.add(btn_addWhour);

		JScrollPane w_scrollWhour = new JScrollPane();
		w_scrollWhour.setBounds(0, 36, 711, 319);
		w_whour.add(w_scrollWhour);

		table_whour = new JTable(whourModel);
		w_scrollWhour.setViewportView(table_whour);
		
		JButton btn_deleteWhour = new JButton("Sil");
		btn_deleteWhour.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selRow = table_whour.getSelectedRow();
				if (selRow >= 0) {
				    String selectRow = table_whour.getModel().getValueAt(selRow, 0).toString();
				    int selID = Integer.parseInt(selectRow);
				    boolean control;
				    try {
				        control = doctor.deleteWhour(selID);
				        if (control) {
				            Helper.showMsg("success");
				            updateWhourModel(doctor);
				        }else {
				        	Helper.showMsg("error");
				        }
				    } catch (SQLException e1) {
				        // TODO Auto-generated catch block
				        e1.printStackTrace();
				    }
				}

			}
		});
		btn_deleteWhour.setFont(new Font("Tahoma", Font.BOLD, 14));
		btn_deleteWhour.setBounds(601, 10, 110, 26);
		w_whour.add(btn_deleteWhour);
		
		JLabel lblNewLabel_1 = new JLabel("Bu panelden uygun saatlerinizi ekleyip silebilirsiniz...");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 10));
		lblNewLabel_1.setBounds(336, 10, 267, 26);
		w_whour.add(lblNewLabel_1);
		
		JButton btnTedaviYnetimi = new JButton("Tedavi Yönetimi");
		btnTedaviYnetimi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TedaviYonetimGUI tedavi = new TedaviYonetimGUI(doctor);
				tedavi.setVisible(true);
			}
		});
		btnTedaviYnetimi.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnTedaviYnetimi.setBounds(388, 21, 159, 28);
		w_pane.add(btnTedaviYnetimi);
		
		JButton btnTahlilynetimi = new JButton("Tahlil Yönetimi");
		btnTahlilynetimi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TahlilGUI orn = new TahlilGUI();
				orn.setVisible(true);
			}
		});
		btnTahlilynetimi.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnTahlilynetimi.setBounds(388, 59, 159, 28);
		w_pane.add(btnTahlilynetimi);
		
		JButton btnTedaviYnetimi_1 = new JButton("Sevk Modülü");
		btnTedaviYnetimi_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SevkGUI frm = new SevkGUI();
				frm.setVisible(true);
				
			}
		});
		btnTedaviYnetimi_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnTedaviYnetimi_1.setBounds(567, 59, 159, 28);
		w_pane.add(btnTedaviYnetimi_1);
	}
	
	public void updateWhourModel(Doctor doctor) throws SQLException {
		DefaultTableModel clearModel = (DefaultTableModel) table_whour.getModel();
		clearModel.setRowCount(0);
		for (int i = 0; i < doctor.getWhourList(doctor.getId()).size(); i++) {
		    whourData[0] = doctor.getWhourList(doctor.getId()).get(i).getId();
		    whourData[1] = doctor.getWhourList(doctor.getId()).get(i).getWdate();
		    whourModel.addRow(whourData);
		}
	}
}
