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
import Model.Tahlil;

public class TahlilGUI extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField hastatcTF;
    private JTextField aciklamaTF;
    private JTextField testturuTF;
    private JTable tabloTahlil;
    private JButton tahlilOlusturBTN;

    private DefaultTableModel tableModel;
    private Object[] tableColumns = {"Hasta TC", "Test Türü", "Sonuç"};
    private JLabel lblNewLabel;

    public TahlilGUI() {

        setTitle("Tahlil Yönetim Ekranı");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 800, 489);
        contentPane = new JPanel();
        contentPane.setBackground(SystemColor.activeCaption);
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblHastaTc = new JLabel("Hasta T.C:");
        lblHastaTc.setFont(new Font("Tahoma", Font.BOLD, 12));
        lblHastaTc.setBounds(10, 20, 100, 30);
        contentPane.add(lblHastaTc);

        hastatcTF = new JTextField();
        hastatcTF.setBounds(120, 20, 150, 30);
        contentPane.add(hastatcTF);
        hastatcTF.setColumns(10);

        JLabel lblAciklama = new JLabel("Tahlil Açıklaması:");
        lblAciklama.setFont(new Font("Tahoma", Font.BOLD, 12));
        lblAciklama.setBounds(10, 70, 150, 30);
        contentPane.add(lblAciklama);

        aciklamaTF = new JTextField();
        aciklamaTF.setBounds(120, 70, 200, 80);
        contentPane.add(aciklamaTF);
        aciklamaTF.setColumns(10);

        JLabel lblTestTuru = new JLabel("Test Türü:");
        lblTestTuru.setFont(new Font("Tahoma", Font.BOLD, 12));
        lblTestTuru.setBounds(10, 170, 100, 30);
        contentPane.add(lblTestTuru);

        testturuTF = new JTextField();
        testturuTF.setBounds(120, 170, 150, 30);
        contentPane.add(testturuTF);
        testturuTF.setColumns(10);

        tahlilOlusturBTN = new JButton("Oluştur");
        tahlilOlusturBTN.setBounds(120, 292, 120, 30);
        contentPane.add(tahlilOlusturBTN);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(356, 28, 420, 321);
        contentPane.add(scrollPane);

        tabloTahlil = new JTable();
        tableModel = new DefaultTableModel();
        tableModel.setColumnIdentifiers(tableColumns);
        tabloTahlil.setModel(tableModel);
        tabloTahlil.setDefaultEditor(Object.class, null); // Tablo yazılamaz

        scrollPane.setViewportView(tabloTahlil);
        
        lblNewLabel = new JLabel("Tahlil Listesi");
        lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
        lblNewLabel.setBounds(485, 10, 168, 13);
        contentPane.add(lblNewLabel);

        tabloTahlil.getTableHeader().setBackground(Color.LIGHT_GRAY);
        tabloTahlil.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 12));

        tabloTahlil.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selectedRow = tabloTahlil.getSelectedRow();
                if (selectedRow >= 0) {
                    hastatcTF.setText((String) tableModel.getValueAt(selectedRow, 0));
                    testturuTF.setText((String) tableModel.getValueAt(selectedRow, 1));
                    aciklamaTF.setText((String) tableModel.getValueAt(selectedRow, 2));

                    tahlilOlusturBTN.setEnabled(false);
                  
                }
            }
        });

        tahlilOlusturBTN.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Hasta hasta = new Hasta();
                    Tahlil tahlil = new Tahlil();

                    int hastaId = hasta.getHastaIdByTc(hastatcTF.getText());
                    if (hastaId != -1) {
                        String testTuru = testturuTF.getText();
                        String sonuc = aciklamaTF.getText();

                        if (tahlil.addTahlil(hastaId, testTuru, sonuc)) {
                            JOptionPane.showMessageDialog(null, "Tahlil başarıyla oluşturuldu.");
                            loadTable();
                        } else {
                            JOptionPane.showMessageDialog(null, "Tahlil oluşturulamadı.");
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Hasta bulunamadı.");
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        loadTable();
    }

    private void loadTable() {
        tableModel.setRowCount(0);
        Tahlil tahlil = new Tahlil();
		Hasta hasta = new Hasta();
		ArrayList<Object> tahliller = tahlil.fetchTahlillerFromDB();

		for (Object obj : tahliller) {
		    Object[] treatment = (Object[]) obj;
		    String hastaTc = (String) treatment[0];
		    String testTuru = (String) treatment[1];

		    String sonuc = (String) treatment[2];

		    tableModel.addRow(new Object[]{hastaTc, testTuru, sonuc});
		}
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                TahlilGUI frame = new TahlilGUI();
                frame.setVisible(true);
            }
        });
    }
}
