package View;

import Model.Ilac;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.SQLException;
import java.util.ArrayList;

public class IlacYonetimGUI extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField ilacidTF;
    private JTextField ilacisimTF;
    private JTextField ilacfiyatTF;
    private JTextField ilacstokTF;
    private JTable ilacTablo;
    private JButton ilacOlusturBTN;
    private JButton ilacGuncelleBTN;
    private JButton ilacSilBTN;
    private DefaultTableModel tableModel;
    private Ilac ilac;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                IlacYonetimGUI frame = new IlacYonetimGUI();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public IlacYonetimGUI() {
        ilac = new Ilac();

        setResizable(false);
        setTitle("İlaç Yönetimi Arayüzü");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 544, 550);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JPanel panel = new JPanel();
        panel.setBackground(SystemColor.activeCaption);
        panel.setBounds(0, 0, 530, 513);
        contentPane.add(panel);
        panel.setLayout(null);

        JLabel lblId = new JLabel("ID Numarası:");
        lblId.setFont(new Font("Tahoma", Font.BOLD, 12));
        lblId.setBounds(10, 44, 83, 30);
        panel.add(lblId);

        JLabel lblIlaIsim = new JLabel("İlaç İsim:");
        lblIlaIsim.setFont(new Font("Tahoma", Font.BOLD, 12));
        lblIlaIsim.setBounds(10, 108, 83, 30);
        panel.add(lblIlaIsim);

        JLabel lblFiyat = new JLabel("İlaç Fiyat:");
        lblFiyat.setFont(new Font("Tahoma", Font.BOLD, 12));
        lblFiyat.setBounds(10, 176, 83, 30);
        panel.add(lblFiyat);

        JLabel lblIlaStok = new JLabel("İlaç Stok:");
        lblIlaStok.setFont(new Font("Tahoma", Font.BOLD, 12));
        lblIlaStok.setBounds(10, 248, 83, 30);
        panel.add(lblIlaStok);

        ilacidTF = new JTextField();
        ilacidTF.setEditable(false);
        ilacidTF.setBounds(103, 51, 117, 23);
        panel.add(ilacidTF);
        ilacidTF.setColumns(10);

        ilacisimTF = new JTextField();
        ilacisimTF.setBounds(103, 115, 117, 23);
        panel.add(ilacisimTF);
        ilacisimTF.setColumns(10);

        ilacfiyatTF = new JTextField();
        ilacfiyatTF.setBounds(103, 183, 117, 23);
        panel.add(ilacfiyatTF);
        ilacfiyatTF.setColumns(10);

        ilacstokTF = new JTextField();
        ilacstokTF.setBounds(103, 255, 117, 23);
        panel.add(ilacstokTF);
        ilacstokTF.setColumns(10);

        ilacOlusturBTN = new JButton("Ekle");
        ilacOlusturBTN.setFont(new Font("Tahoma", Font.BOLD, 12));
        ilacOlusturBTN.setBounds(45, 329, 127, 30);
        panel.add(ilacOlusturBTN);

        ilacSilBTN = new JButton("Sil");
        ilacSilBTN.setFont(new Font("Tahoma", Font.BOLD, 12));
        ilacSilBTN.setBounds(45, 379, 127, 30);
        ilacSilBTN.setEnabled(false);
        panel.add(ilacSilBTN);

        ilacGuncelleBTN = new JButton("Güncelle");
        ilacGuncelleBTN.setFont(new Font("Tahoma", Font.BOLD, 12));
        ilacGuncelleBTN.setBounds(45, 432, 127, 30);
        ilacGuncelleBTN.setEnabled(false);
        panel.add(ilacGuncelleBTN);

        JLabel lblIlalar = new JLabel("İlaçlar");
        lblIlalar.setFont(new Font("Tahoma", Font.BOLD, 12));
        lblIlalar.setBounds(343, 10, 83, 30);
        panel.add(lblIlalar);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(230, 44, 290, 418);
        panel.add(scrollPane);

        tableModel = new DefaultTableModel(new Object[]{"ID", "İsim", "Fiyat", "Stok"}, 0) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        ilacTablo = new JTable(tableModel);
        JTableHeader header = ilacTablo.getTableHeader();
        header.setBackground(Color.LIGHT_GRAY);
        header.setFont(new Font("Tahoma", Font.BOLD, 12));
        scrollPane.setViewportView(ilacTablo);

        ilacTablo.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && ilacTablo.getSelectedRow() != -1) {
                int selectedRow = ilacTablo.getSelectedRow();
                ilacidTF.setText(tableModel.getValueAt(selectedRow, 0).toString());
                ilacisimTF.setText(tableModel.getValueAt(selectedRow, 1).toString());
                ilacfiyatTF.setText(tableModel.getValueAt(selectedRow, 2).toString());
                ilacstokTF.setText(tableModel.getValueAt(selectedRow, 3).toString());
                ilacOlusturBTN.setEnabled(false);
                ilacGuncelleBTN.setEnabled(true);
                ilacSilBTN.setEnabled(true);
            }
        });

        ilacisimTF.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                ilacOlusturBTN.setEnabled(true);
                ilacGuncelleBTN.setEnabled(false);
                ilacSilBTN.setEnabled(false);
            }
        });

        ilacOlusturBTN.addActionListener(e -> {
            try {
                String name = ilacisimTF.getText();
                double price = Double.parseDouble(ilacfiyatTF.getText());
                int stock = Integer.parseInt(ilacstokTF.getText());
                if (ilac.addMedicine(name, price, stock)) {
                    JOptionPane.showMessageDialog(null, "İlaç eklendi!");
                    loadMedicines();
                } else {
                    JOptionPane.showMessageDialog(null, "İlaç eklenemedi!");
                }
            } catch (SQLException | NumberFormatException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Hata: " + ex.getMessage());
            }
        });

        ilacGuncelleBTN.addActionListener(e -> {
            try {
                int id = Integer.parseInt(ilacidTF.getText());
                double price = Double.parseDouble(ilacfiyatTF.getText());
                int stock = Integer.parseInt(ilacstokTF.getText());
                if (ilac.updateMedicinePrice(id, price) && ilac.updateMedicineStock(id, stock)) {
                    JOptionPane.showMessageDialog(null, "İlaç güncellendi!");
                    loadMedicines();
                } else {
                    JOptionPane.showMessageDialog(null, "Güncelleme başarısız!");
                }
            } catch (SQLException | NumberFormatException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Hata: " + ex.getMessage());
            }
        });

        ilacSilBTN.addActionListener(e -> {
            try {
                int id = Integer.parseInt(ilacidTF.getText());
                if (ilac.deleteMedicine(id)) {
                    JOptionPane.showMessageDialog(null, "İlaç silindi!");
                    loadMedicines();
                } else {
                    JOptionPane.showMessageDialog(null, "Silme işlemi başarısız!");
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Hata: " + ex.getMessage());
            }
        });

        loadMedicines();
    }
    public void loadMedicines() {
        Ilac ornek = new Ilac();
        ArrayList<Object> ilaclar = ornek.fetchMedicinesFromDB();

        try {
            tableModel.setRowCount(0); // Tabloyu sıfırla
            
            // Veritabanından çekilen ilaçları tabloya ekle
            for (Object obj : ilaclar) {
                Object[] row = (Object[]) obj; // Liste öğelerini Object[] olarak al
                tableModel.addRow(row);       // Satırı tabloya ekle
            }

            // Eğer dummy data gerekiyorsa aşağıdaki gibi ekleyebilirsin
            // tableModel.addRow(new Object[]{1, "Parol", 10.5, 100});
            // tableModel.addRow(new Object[]{2, "Aspirin", 8.75, 200});
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}