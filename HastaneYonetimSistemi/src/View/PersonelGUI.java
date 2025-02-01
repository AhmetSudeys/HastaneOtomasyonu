package View;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.border.EmptyBorder;

import Model.Personel;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextPane;
import java.awt.Color;

public class PersonelGUI extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    // Örnek bir personel bilgisiyle başlat
                    Personel samplePersonel = new Personel(1, "Mehmet Kaya", "Teknik Destek");
                    PersonelGUI frame = new PersonelGUI(samplePersonel);
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
    public PersonelGUI(Personel personel) {
    	setTitle("Hastane Yönetim Sistemi");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        // Personel bilgilerini ekrana yazdırma
        JLabel lblWelcome = new JLabel("Hoşgeldiniz, " + personel.getName());
        lblWelcome.setFont(new Font("Tahoma", Font.BOLD, 13));
        lblWelcome.setBounds(10, 10, 400, 30);
        contentPane.add(lblWelcome);

        JLabel lblRole = new JLabel("Göreviniz: " + personel.getRole());
        lblRole.setFont(new Font("Tahoma", Font.BOLD, 13));
        lblRole.setBounds(10, 50, 400, 30);
        contentPane.add(lblRole);
        
        JButton btnNewButton = new JButton("Çıkış Yap");
        btnNewButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		 LoginGUI login = new LoginGUI();
			        login.setVisible(true);
			        dispose();
        	}
        });
        btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 14));
        btnNewButton.setBounds(156, 212, 125, 28);
        contentPane.add(btnNewButton);
        
        JTextPane txtpnBuKsmdaPersoneller = new JTextPane();
        txtpnBuKsmdaPersoneller.setForeground(Color.RED);
        txtpnBuKsmdaPersoneller.setText("Hastane personelinin (hemşire, temizlik personeli, teknik destek vb.) kimlik ve görev bilgilerini içeren bir sınıf. Bu sınıf sayesinde giriş yapabiliyorlar. Daha fazla özelleştirilebilirdi fakat ben bu şekilde bırakıyorum çünkü sürem yetmedi");
        txtpnBuKsmdaPersoneller.setFont(new Font("Tahoma", Font.BOLD, 13));
        txtpnBuKsmdaPersoneller.setBounds(10, 90, 416, 112);
        contentPane.add(txtpnBuKsmdaPersoneller);
    }
}
