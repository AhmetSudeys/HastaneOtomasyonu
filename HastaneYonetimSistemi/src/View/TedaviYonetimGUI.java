package View;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import Model.Doctor;
import Model.Hasta;
import Model.Ilac;
import Model.Tedavi;

public class TedaviYonetimGUI extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField hastatcTF;
    private JTextField aciklamaTF;
    private JTextField ucretTF;
    private JTable tabloTedavi;
    private JTextField ilaclarTF;
    private JButton tedaviOlusturBTN;

    public Doctor doctor = new Doctor();
    private DefaultTableModel tableModel;
    private Object[] tableColumns = {"Hasta TC", "Doktor", "İlaçlar", "Açıklama"};
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            private Doctor doctor;

			@Override
            public void run() {
               
                TedaviYonetimGUI frame = new TedaviYonetimGUI(this.doctor);
                frame.setVisible(true);
            }
        });
    }

    

    public TedaviYonetimGUI(Doctor doctor) {
        this.doctor = doctor;

        setTitle("Tedavi Yönetim Ekranı ");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 800, 600);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JPanel panel = new JPanel();
        panel.setBackground(SystemColor.activeCaption);
        panel.setBounds(0, 0, 800, 600);
        contentPane.add(panel);
        panel.setLayout(null);

        JLabel lblHastaTc = new JLabel("Hasta T.C:");
        lblHastaTc.setFont(new Font("Tahoma", Font.BOLD, 12));
        lblHastaTc.setBounds(10, 20, 100, 30);
        panel.add(lblHastaTc);

        hastatcTF = new JTextField();
        hastatcTF.setBounds(120, 20, 150, 30);
        panel.add(hastatcTF);
        hastatcTF.setColumns(10);

        JLabel lblAciklama = new JLabel("Tedavi Açıklaması:");
        lblAciklama.setFont(new Font("Tahoma", Font.BOLD, 12));
        lblAciklama.setBounds(10, 70, 150, 30);
        panel.add(lblAciklama);

        aciklamaTF = new JTextField();
        aciklamaTF.setBounds(120, 70, 200, 80);
        panel.add(aciklamaTF);
        aciklamaTF.setColumns(10);

        JLabel lblIlaclar = new JLabel("İlaç Listesi:");
        lblIlaclar.setFont(new Font("Tahoma", Font.BOLD, 12));
        lblIlaclar.setBounds(10, 170, 100, 30);
        panel.add(lblIlaclar);

        ilaclarTF = new JTextField();
        ilaclarTF.setBounds(120, 170, 200, 80);
        panel.add(ilaclarTF);
        ilaclarTF.setColumns(10);

        JLabel lblUcret = new JLabel("Tedavi Ücreti:");
        lblUcret.setFont(new Font("Tahoma", Font.BOLD, 12));
        lblUcret.setBounds(10, 270, 100, 30);
        panel.add(lblUcret);

        ucretTF = new JTextField();
        ucretTF.setBounds(120, 270, 150, 30);
        panel.add(ucretTF);
        ucretTF.setColumns(10);

        tedaviOlusturBTN = new JButton("Oluştur");
        tedaviOlusturBTN.setBounds(150, 346, 120, 30);
        panel.add(tedaviOlusturBTN);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(351, 28, 420, 500);
        panel.add(scrollPane);

        tabloTedavi = new JTable();
        tableModel = new DefaultTableModel();
        tableModel.setColumnIdentifiers(tableColumns);
        tabloTedavi.setModel(tableModel);
        tabloTedavi.setDefaultEditor(Object.class, null); // Tablo yazılabilir değil

        scrollPane.setViewportView(tabloTedavi);
        
        JLabel lblNewLabel = new JLabel("Tedavi Listesi");
        lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
        lblNewLabel.setBounds(513, 10, 150, 13);
        panel.add(lblNewLabel);

        tabloTedavi.getTableHeader().setBackground(Color.LIGHT_GRAY);
        tabloTedavi.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 12));

        tabloTedavi.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selectedRow = tabloTedavi.getSelectedRow();
                if (selectedRow >= 0) {
                    String hastaTc = (String) tableModel.getValueAt(selectedRow, 0);
                    String aciklama = (String) tableModel.getValueAt(selectedRow, 3);
                    String ilaclar = (String) tableModel.getValueAt(selectedRow, 2);

                    hastatcTF.setText(hastaTc);
                    aciklamaTF.setText(aciklama);
                    ilaclarTF.setText(ilaclar);
                    ucretTF.setText("500.0"); // Örnek ücret, gerektiğinde güncellenebilir

                    tedaviOlusturBTN.setEnabled(false);
                    
                }
            }
        });

        // Buton İşlevleri
        tedaviOlusturBTN.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Hasta hasta = new Hasta();
                    Tedavi tedavi = new Tedavi();

                    int hastaId = hasta.getHastaIdByTc(hastatcTF.getText());
                    if (hastaId != -1) {
                        String aciklama = aciklamaTF.getText();
                        double ucret = Double.parseDouble(ucretTF.getText());

                        if (tedavi.tedaviOlustur(hastaId, doctor.getId(), aciklama)) {
                            JOptionPane.showMessageDialog(null, "Tedavi başarıyla oluşturuldu.");
                            loadTable();
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Hasta bulunamadı.");
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        loadTable();
    }

    private void loadTable() {
        tableModel.setRowCount(0);
        try {
            Tedavi tedavi = new Tedavi();
            Hasta hasta = new Hasta();
            ArrayList<Object> treatments = tedavi.fetchTreatmentsFromDB();

            for (Object obj : treatments) {
                Object[] treatment = (Object[]) obj;
                int hastaId = (int) treatment[1];
                String hastaTc = hasta.getTcByHastaId(hastaId);
                String doktorAdi = doctor.getName();
                String ilaclar = tedavi.getIlacIsimleri((int) treatment[0]);
                String aciklama = (String) treatment[3];

                tableModel.addRow(new Object[]{hastaTc, doktorAdi, ilaclar, aciklama});
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
   
}
