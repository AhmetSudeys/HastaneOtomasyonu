package View;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

import Model.*;
import Model.*;

import java.awt.SystemColor;
import java.sql.SQLException;

import javax.swing.JLabel;
import javax.swing.JMenuItem;

import java.awt.Font;
import java.awt.Point;

import javax.swing.JButton;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.ActionEvent;
import Helper.*;
import java.awt.Color;
import javax.swing.SwingConstants;
import javax.swing.JTextPane;
import javax.swing.JComboBox;
import javax.swing.JTextArea;
import javax.swing.JEditorPane;

public class BashekimGUI extends JFrame {

	static Bashekim bashekim = new Bashekim();
	Clinic clinic = new Clinic();
	private static final long serialVersionUID = 1L;
	private JPanel w_pane;
	private JTextField fld_dName;
	private JTextField fld_dTcno;
	private JTextField fld_dPass;
	private JTextField fld_doctorID;
	private JTable table_doctor;
	private DefaultTableModel doctorModel = null;
	private Object[] doctorData = null;
	private JTable table_clinic;
	private JTextField fld_clinicName;
	private DefaultTableModel clinicModel = null;
	private Object[] clinicData = null;
	private JPopupMenu clinicMenu;
	private JTable table_worker;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BashekimGUI frame = new BashekimGUI(bashekim);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * 
	 * @throws SQLException
	 */
	public BashekimGUI(Bashekim bashekim) throws SQLException {

		// doktor model
		doctorModel = new DefaultTableModel();
		Object[] colDoctorName = new Object[4];
		colDoctorName[0] = "ID";
		colDoctorName[1] = "Ad Soyad";
		colDoctorName[2] = "TC NO";
		colDoctorName[3] = "Şifre";
		doctorModel.setColumnIdentifiers(colDoctorName);
		doctorData = new Object[4];
		for (int i = 0; i < bashekim.getDoctorList().size(); i++) {
			doctorData[0] = bashekim.getDoctorList().get(i).getId();
			doctorData[1] = bashekim.getDoctorList().get(i).getName();
			doctorData[2] = bashekim.getDoctorList().get(i).getTcno();
			doctorData[3] = bashekim.getDoctorList().get(i).getPassword();
			doctorModel.addRow(doctorData);
		}

		// Clinic model
		clinicModel = new DefaultTableModel();
		Object[] colClinic = new Object[2];
		colClinic[0] = "ID";
		colClinic[1] = "Poliklinik Adı";
		clinicModel.setColumnIdentifiers(colClinic);
		clinicData = new Object[2];
		for (int i = 0; i < clinic.getList().size(); i++) {
			clinicData[0] = clinic.getList().get(i).getId();
			clinicData[1] = clinic.getList().get(i).getName();
			clinicModel.addRow(clinicData);
		}
		
		// WorkerModel
		DefaultTableModel workerModel = new DefaultTableModel();
		Object[] colWorker = new Object[2];
		colWorker[0] = "ID";
		colWorker[1] = "Ad Soyad";
		workerModel.setColumnIdentifiers(colWorker);
		Object[] workerData = new Object[2];

		
		setBackground(SystemColor.window);
		setForeground(SystemColor.window);
		setTitle("Hastane Yönetim Sistemi");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 752, 508);
		w_pane = new JPanel();
		w_pane.setBackground(SystemColor.window);
		w_pane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(w_pane);
		w_pane.setLayout(null);

		JLabel lblNewLabel = new JLabel("Hoşgeldiniz, Sayın " + bashekim.getName());
		lblNewLabel.setBounds(25, 31, 348, 30);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
		w_pane.add(lblNewLabel);

		JButton btnNewButton = new JButton("Çıkış Yap");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 LoginGUI login = new LoginGUI();
			        login.setVisible(true);
			        dispose();
			}
		});
		btnNewButton.setBounds(588, 32, 125, 28);
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 14));
		w_pane.add(btnNewButton);

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBackground(SystemColor.activeCaption);
		tabbedPane.setBounds(10, 71, 716, 392);
		w_pane.add(tabbedPane);

		JPanel panel = new JPanel();
		panel.setBackground(SystemColor.activeCaption);
		tabbedPane.addTab("Doktor Yönetimi", null, panel, null);
		panel.setLayout(null);

		fld_dName = new JTextField();
		fld_dName.setBounds(499, 30, 202, 29);
		panel.add(fld_dName);
		fld_dName.setColumns(10);

		JLabel lblNewLabel_1 = new JLabel("Ad Soyad");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel_1.setBounds(499, 7, 202, 23);
		panel.add(lblNewLabel_1);

		fld_dTcno = new JTextField();
		fld_dTcno.setColumns(10);
		fld_dTcno.setBounds(499, 93, 202, 29);
		panel.add(fld_dTcno);

		JLabel lblNewLabel_1_1 = new JLabel("T.C. No");
		lblNewLabel_1_1.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel_1_1.setBounds(499, 70, 202, 23);
		panel.add(lblNewLabel_1_1);

		fld_dPass = new JTextField();
		fld_dPass.setColumns(10);
		fld_dPass.setBounds(499, 155, 202, 29);
		panel.add(fld_dPass);

		JLabel lblNewLabel_1_2 = new JLabel("Şifre");
		lblNewLabel_1_2.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel_1_2.setBounds(499, 132, 202, 23);
		panel.add(lblNewLabel_1_2);

		JButton btnNewButton_1 = new JButton("Ekle");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (fld_dName.getText().length() == 0 || fld_dTcno.getText().length() == 0
						|| fld_dPass.getText().length() == 0) {
					Helper.showMsg("fill");

				} else {
					try {
						boolean control = bashekim.addDoctor(fld_dTcno.getText(), fld_dPass.getText(),
								fld_dName.getText());
						if (control) {
							Helper.showMsg("success");
							fld_dName.setText(null);
							fld_dTcno.setText(null);
							fld_dPass.setText(null);
							updateDoctorModel();
						}
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
		btnNewButton_1.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnNewButton_1.setBounds(554, 194, 108, 29);
		panel.add(btnNewButton_1);

		JLabel lblNewLabel_1_2_1 = new JLabel("Kullanıcı ID");
		lblNewLabel_1_2_1.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel_1_2_1.setBounds(499, 253, 202, 23);
		panel.add(lblNewLabel_1_2_1);

		fld_doctorID = new JTextField();
		fld_doctorID.setEnabled(false);
		fld_doctorID.setColumns(10);
		fld_doctorID.setBounds(499, 276, 202, 29);
		panel.add(fld_doctorID);

		JButton btnNewButton_1_1 = new JButton("Sil");
		btnNewButton_1_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (fld_doctorID.getText().length() == 0) {
					Helper.showMsg("Lütfen geçerli bir doktor seçiniz!");
				} else {
					if (Helper.confirm("sure")) {
						int selectID = Integer.parseInt(fld_doctorID.getText());
						try {
							boolean control = bashekim.deleteDoctor(selectID);
							if (control) {
								Helper.showMsg("success");
								fld_doctorID.setText(null);
								updateDoctorModel();

							}
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				}

			}
		});
		btnNewButton_1_1.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnNewButton_1_1.setBounds(554, 313, 108, 29);
		panel.add(btnNewButton_1_1);

		JScrollPane w_scrollDoctor = new JScrollPane();
		w_scrollDoctor.setBounds(10, 7, 470, 348);
		panel.add(w_scrollDoctor);

		table_doctor = new JTable(doctorModel);
		w_scrollDoctor.setViewportView(table_doctor);

		JLabel lblNewLabel_2 = new JLabel("silmek istediğiniz kişiye tıklayıp sil butonuna basın ");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblNewLabel_2.setForeground(new Color(255, 0, 0));
		lblNewLabel_2.setBounds(482, 342, 242, 23);
		panel.add(lblNewLabel_2);

		table_doctor.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				try {
					fld_doctorID.setText(table_doctor.getValueAt(table_doctor.getSelectedRow(), 0).toString());
				} catch (Exception ex) {
					// TODO: handle exception
				}
			}
		});

		table_doctor.getModel().addTableModelListener(new TableModelListener() {

			@Override
			public void tableChanged(TableModelEvent e) {
				if (e.getType() == TableModelEvent.UPDATE) {
					int selectID = Integer
							.parseInt(table_doctor.getValueAt(table_doctor.getSelectedRow(), 0).toString());
					String selectName = table_doctor.getValueAt(table_doctor.getSelectedRow(), 1).toString();
					String selectTcno = table_doctor.getValueAt(table_doctor.getSelectedRow(), 2).toString();
					String selectPass = table_doctor.getValueAt(table_doctor.getSelectedRow(), 3).toString();

					try {
						boolean control = bashekim.updateDoctor(selectID, selectTcno, selectPass, selectName);
						if (control) {
							Helper.showMsg("Güncelleme sağlandı");
						}
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

				}
			}
		});

		JPanel w_clinic = new JPanel();
		w_clinic.setBackground(SystemColor.activeCaption);
		tabbedPane.addTab("Poliklinikler", null, w_clinic, null);
		w_clinic.setLayout(null);

		JScrollPane w_scrollClinic = new JScrollPane();
		w_scrollClinic.setBounds(10, 15, 272, 345);
		w_clinic.add(w_scrollClinic);

		clinicMenu = new JPopupMenu();
		JMenuItem updateMenu = new JMenuItem("Güncelle");
		JMenuItem deleteMenu = new JMenuItem("Sil");
		clinicMenu.add(updateMenu);
		clinicMenu.add(deleteMenu);

		updateMenu.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int selID = Integer.parseInt(table_clinic.getValueAt(table_clinic.getSelectedRow(), 0).toString());
				Clinic selectClinic = clinic.getFetch(selID);
				UpdateClinicGUI updateGUI = new UpdateClinicGUI(selectClinic);
				updateGUI.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				updateGUI.setVisible(true);
				updateGUI.addWindowListener(new WindowAdapter() {

					@Override
					public void windowClosed(WindowEvent e) {
						try {
							updateClinicModel();
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				});

			}
		});

		deleteMenu.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int selID = Integer.parseInt(table_clinic.getValueAt(table_clinic.getSelectedRow(), 0).toString());
				try {
					if (clinic.deleteClinic(selID)) {
						Helper.showMsg("success");
						updateClinicModel();
					} else {
						Helper.showMsg("error");
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}

		});

		table_clinic = new JTable(clinicModel);
		table_clinic.setComponentPopupMenu(clinicMenu); // sağ tık yapılınca güncelle ve sil gözükür
		table_clinic.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				Point point = e.getPoint();
				int selectedRow = table_clinic.rowAtPoint(point);
				table_clinic.setRowSelectionInterval(selectedRow, selectedRow);

			}
		});

		w_scrollClinic.setViewportView(table_clinic);

		JLabel lblNewLabel_1_3 = new JLabel("Poliklinik Adı");
		lblNewLabel_1_3.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel_1_3.setBounds(292, 10, 202, 23);
		w_clinic.add(lblNewLabel_1_3);

		fld_clinicName = new JTextField();
		fld_clinicName.setColumns(10);
		fld_clinicName.setBounds(292, 33, 136, 29);
		w_clinic.add(fld_clinicName);

		JButton btn_addClinic = new JButton("Ekle");
		btn_addClinic.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (fld_clinicName.getText().length() == 0) {
					Helper.showMsg("fill");
				} else {
					try {
						if (clinic.addClinic(fld_clinicName.getText())) {
							Helper.showMsg("success");
							fld_clinicName.setText(null);
							updateClinicModel();
						}
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}

			}
		});
		btn_addClinic.setFont(new Font("Tahoma", Font.BOLD, 15));
		btn_addClinic.setBounds(292, 72, 136, 29);
		w_clinic.add(btn_addClinic);

		JScrollPane w_scrollWorker = new JScrollPane();
		w_scrollWorker.setBounds(438, 15, 263, 345);
		w_clinic.add(w_scrollWorker);
		
		table_worker = new JTable();
		w_scrollWorker.setViewportView(table_worker);

		JLabel lblNewLabel_3 = new JLabel("Poliklinikler");
		lblNewLabel_3.setForeground(Color.RED);
		lblNewLabel_3.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_3.setBounds(10, 0, 192, 13);
		w_clinic.add(lblNewLabel_3);

		JLabel lblNewLabel_3_1 = new JLabel("Seçili Poliklinikteki Doktorlar");
		lblNewLabel_3_1.setForeground(Color.RED);
		lblNewLabel_3_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_3_1.setBounds(438, 0, 263, 13);
		w_clinic.add(lblNewLabel_3_1);
		
		JComboBox select_doctor = new JComboBox();
		select_doctor.setBounds(292, 277, 136, 39);
		for(int i=0; i<bashekim.getDoctorList().size(); i++) {
			select_doctor.addItem(new Item(bashekim.getDoctorList().get(i).getId(), bashekim.getDoctorList().get(i).getName()));
		}
		select_doctor.addActionListener(e -> {
			JComboBox c = (JComboBox) e.getSource();
			Item item = (Item) c.getSelectedItem();
			System.out.println(item.getKey() + " : " + item.getValue());
		});
		
		w_clinic.add(select_doctor);
		
		JButton btn_addWorker = new JButton("Ekle");
		btn_addWorker.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
	
				    int selRow = table_clinic.getSelectedRow();
				    if (selRow >= 0) {
				        String selClinic = table_clinic.getModel().getValueAt(selRow, 0).toString();
				        int selClinicID = Integer.parseInt(selClinic);
				        Item doctorItem = (Item) select_doctor.getSelectedItem();
				        try {
				            boolean control = bashekim.addWorker(doctorItem.getKey(), selClinicID);
				            
				            if (control) {
				                Helper.showMsg("success");
				                DefaultTableModel clearModel = (DefaultTableModel) table_worker.getModel();
						        clearModel.setRowCount(0);
						        for (int i = 0; i < bashekim.getClinicDoctorList(selClinicID).size(); i++) {
					                workerData[0] = bashekim.getClinicDoctorList(selClinicID).get(i).getId();
					                workerData[1] = bashekim.getClinicDoctorList(selClinicID).get(i).getName();
					                workerModel.addRow(workerData);
					            }
						        table_worker.setModel(workerModel);
						        
				            } else {
				                Helper.showMsg("error");
				            }
				        } catch (SQLException e1) {
				            // TODO Auto-generated catch block
				            e1.printStackTrace();
				        }
				    } else {
				        Helper.showMsg("Lütfen bir poliklinik seçiniz !");
				    }
				}
			
		});
		btn_addWorker.setFont(new Font("Tahoma", Font.BOLD, 15));
		btn_addWorker.setBounds(292, 326, 136, 29);
		w_clinic.add(btn_addWorker);
		
		JButton btn_workerSelect = new JButton("Seç");
		btn_workerSelect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				    int selRow = table_clinic.getSelectedRow();
				    if (selRow >= 0) {
				        String selClinic = table_clinic.getModel().getValueAt(selRow, 0).toString();
				        int selClinicID = Integer.parseInt(selClinic);
				        DefaultTableModel clearModel = (DefaultTableModel) table_worker.getModel();
				        clearModel.setRowCount(0);

				        try {
				            for (int i = 0; i < bashekim.getClinicDoctorList(selClinicID).size(); i++) {
				                workerData[0] = bashekim.getClinicDoctorList(selClinicID).get(i).getId();
				                workerData[1] = bashekim.getClinicDoctorList(selClinicID).get(i).getName();
				                workerModel.addRow(workerData);
				            }
				        } catch (SQLException e1) {
				            // TODO Auto-generated catch block
				            e1.printStackTrace();
				        }
				        table_worker.setModel(workerModel);
				    } else {
				        Helper.showMsg("Lütfen bir poliklinik seçiniz !");
				    }
				}

		});
		
		btn_workerSelect.setFont(new Font("Tahoma", Font.BOLD, 15));
		btn_workerSelect.setBounds(292, 174, 136, 29);
		w_clinic.add(btn_workerSelect);
		
		JEditorPane dtrpnSolTaraftanPoliklinik = new JEditorPane();
		dtrpnSolTaraftanPoliklinik.setFont(new Font("Arial", Font.BOLD, 11));
		dtrpnSolTaraftanPoliklinik.setBackground(SystemColor.activeCaption);
		dtrpnSolTaraftanPoliklinik.setForeground(SystemColor.desktop);
		dtrpnSolTaraftanPoliklinik.setText("Sol taraftan poliklinik       seçip butona basarak      doktorları görebilirsiniz");
		dtrpnSolTaraftanPoliklinik.setBounds(292, 126, 153, 53);
		w_clinic.add(dtrpnSolTaraftanPoliklinik);
		
		JEditorPane dtrpnBirPoliklinikVe = new JEditorPane();
		dtrpnBirPoliklinikVe.setText("Bir poliklinik ve aşağıdan da bir doktor seçip            ekleme işlemi yapın");
		dtrpnBirPoliklinikVe.setForeground(SystemColor.desktop);
		dtrpnBirPoliklinikVe.setFont(new Font("Arial", Font.BOLD, 11));
		dtrpnBirPoliklinikVe.setBackground(SystemColor.activeCaption);
		dtrpnBirPoliklinikVe.setBounds(292, 225, 153, 53);
		w_clinic.add(dtrpnBirPoliklinikVe);
		
		JButton btnIlaYnetimi = new JButton("İlaç Yönetimi");
		btnIlaYnetimi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				IlacYonetimGUI frm = new IlacYonetimGUI();
				frm.setVisible(true);
				
			}
		});
		btnIlaYnetimi.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnIlaYnetimi.setBounds(432, 33, 145, 28);
		w_pane.add(btnIlaYnetimi);

	}

	public void updateDoctorModel() throws SQLException {
		DefaultTableModel clearModel = (DefaultTableModel) table_doctor.getModel();
		clearModel.setRowCount(0);
		for (int i = 0; i < bashekim.getDoctorList().size(); i++) {
			doctorData[0] = bashekim.getDoctorList().get(i).getId();
			doctorData[1] = bashekim.getDoctorList().get(i).getName();
			doctorData[2] = bashekim.getDoctorList().get(i).getTcno();
			doctorData[3] = bashekim.getDoctorList().get(i).getPassword();
			doctorModel.addRow(doctorData);
		}
	}

	public void updateClinicModel() throws SQLException {
		DefaultTableModel clearModel = (DefaultTableModel) table_clinic.getModel();
		clearModel.setRowCount(0);
		for (int i = 0; i < clinic.getList().size(); i++) {
			clinicData[0] = clinic.getList().get(i).getId();
			clinicData[1] = clinic.getList().get(i).getName();
			clinicModel.addRow(clinicData);
		}
	}
}
