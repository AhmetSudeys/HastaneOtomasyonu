package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Tahlil extends User{

    Statement st = null;
    ResultSet rs = null;
    Connection con = conn.connDb();
    PreparedStatement preparedStatement = null;

    public Tahlil() {
    }

    // Tahlil ekleme fonksiyonu
    public boolean addTahlil(int hastaId, String testTürü, String sonuç) throws SQLException {
        String query = "INSERT INTO tahlil (hastaId, testTürü, sonuç) VALUES (?, ?, ?)";

        try {
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setInt(1, hastaId);
            preparedStatement.setString(2, testTürü);
            preparedStatement.setString(3, sonuç);

            int result = preparedStatement.executeUpdate();
            if (result > 0) {
                return true; // Eğer kayıt başarılıysa true döndür
            }
        } catch (SQLException e) {
            e.printStackTrace(); // SQL hatasını yazdır
        }
        return false; // Eğer kayıt başarısız olursa false döndür
    }

    // Tahlil hastaId'sini döndüren fonksiyon
    public int getHastaId(int tahlilId) throws SQLException {
        String query = "SELECT hastaId FROM tahlil WHERE id = ?";
        int hastaId = -1;

        try {
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setInt(1, tahlilId);
            rs = preparedStatement.executeQuery();

            if (rs.next()) {
                hastaId = rs.getInt("hastaId");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return hastaId; // Eğer tahlil bulunamazsa -1 döndür
    }

    // Tahlil test türünü döndüren fonksiyon
    public String getTestTürü(int tahlilId) throws SQLException {
        String query = "SELECT testTürü FROM tahlil WHERE id = ?";
        String testTürü = null;

        try {
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setInt(1, tahlilId);
            rs = preparedStatement.executeQuery();

            if (rs.next()) {
                testTürü = rs.getString("testTürü");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return testTürü; // Eğer tahlil bulunamazsa null döndür
    }

    // Tahlil sonucunu döndüren fonksiyon
    public String getSonuç(int tahlilId) throws SQLException {
        String query = "SELECT sonuç FROM tahlil WHERE id = ?";
        String sonuç = null;

        try {
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setInt(1, tahlilId);
            rs = preparedStatement.executeQuery();

            if (rs.next()) {
                sonuç = rs.getString("sonuç");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sonuç; // Eğer tahlil bulunamazsa null döndür
    }

	 public ArrayList<Object>  fetchTahlillerFromDB() {
	        ArrayList<Object> treatmentList = new ArrayList<>();
	        try {
	            // Veritabanına bağlan
	            Tahlil tahlil = new Tahlil(); // Veritabanı bağlantısı Tedavi sınıfında
	            String query = "SELECT tahlilId, hastaId,  testTürü, sonuç FROM tahlil";
	            PreparedStatement preparedStatement = tahlil.con.prepareStatement(query);
	            ResultSet resultSet = preparedStatement.executeQuery();

	            // Veritabanından verileri çek ve listeye aktar
	            while (resultSet.next()) {
	                int hastaId = resultSet.getInt("hastaId");
	                Hasta xxx = new Hasta();
	                String tc = xxx.getTcByHastaId(hastaId);
	                String testTuru = resultSet.getString("testTürü");
	                String sonuç = resultSet.getString("sonuç");

	                // Tedavi bilgilerini bir dizi olarak listeye ekle
	                treatmentList.add(new Object[] { tc, testTuru, sonuç});
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return treatmentList;
	    }
	    
}