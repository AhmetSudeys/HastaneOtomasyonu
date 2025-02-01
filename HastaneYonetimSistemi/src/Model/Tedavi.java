package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Tedavi extends User{

    Statement st = null;
    ResultSet rs = null;
    Connection con = conn.connDb();
    PreparedStatement preparedStatement = null;

    // Tedavi oluşturma fonksiyonu
    public boolean tedaviOlustur(int hastaId, int doktorId, String tedaviAciklamasi) throws SQLException {
        String query = "INSERT INTO tedavi (hastaId, doktorId, tedaviAciklamasi, tedaviUcreti) VALUES (?, ?, ?, ?)";

        try {
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setInt(1, hastaId);
            preparedStatement.setInt(2, doktorId);
            preparedStatement.setString(3, tedaviAciklamasi);
            preparedStatement.setDouble(4, 500.0); // Başlangıç tedavi ücreti

            int result = preparedStatement.executeUpdate();
            return result > 0; // Eğer tedavi başarılı bir şekilde oluşturulursa true döndür
        } catch (SQLException e) {
            e.printStackTrace(); // SQL hatasını yazdır
        }
        return false; // Eğer tedavi oluşturulamazsa false döndür
    }

    // İlaç ekleme fonksiyonu
    public boolean ilacEkle(int tedaviId, int ilacId) throws SQLException {
        Ilac ilac = new Ilac();
        double ilacFiyati = ilac.getMedicinePrice(ilacId); // İlaç fiyatını al
        if (ilacFiyati <= 0) {
            return false; // Eğer ilaç fiyatı geçersizse ekleme yapma
        }

        String updateQuery = "UPDATE tedavi SET tedaviUcreti = tedaviUcreti + ?, ilac1Id = COALESCE(ilac1Id, ?), " +
                "ilac2Id = CASE WHEN ilac1Id IS NOT NULL AND ilac2Id IS NULL THEN ? ELSE ilac2Id END, " +
                "ilac3Id = CASE WHEN ilac1Id IS NOT NULL AND ilac2Id IS NOT NULL AND ilac3Id IS NULL THEN ? ELSE ilac3Id END WHERE id = ?";

        try {
            PreparedStatement preparedStatement = con.prepareStatement(updateQuery);
            preparedStatement.setDouble(1, ilacFiyati);
            preparedStatement.setInt(2, ilacId);
            preparedStatement.setInt(3, ilacId);
            preparedStatement.setInt(4, ilacId);
            preparedStatement.setInt(5, tedaviId);

            int result = preparedStatement.executeUpdate();
            return result > 0; // Eğer ilaç başarıyla eklenirse true döndür
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // Eğer ilaç eklenemezse false döndür
    }

    // İlaç isimlerini getiren fonksiyon
    public String getIlacIsimleri(int tedaviId) throws SQLException {
        String query = "SELECT i1.name AS ilac1, i2.name AS ilac2, i3.name AS ilac3 " +
                "FROM tedavi t " +
                "LEFT JOIN medicine i1 ON t.ilac1Id = i1.id " +
                "LEFT JOIN medicine i2 ON t.ilac2Id = i2.id " +
                "LEFT JOIN medicine i3 ON t.ilac3Id = i3.id " +
                "WHERE t.id = ?";

        StringBuilder ilacIsimleri = new StringBuilder();
        try {
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setInt(1, tedaviId);
            rs = preparedStatement.executeQuery();

            if (rs.next()) {
                String ilac1 = rs.getString("ilac1");
                String ilac2 = rs.getString("ilac2");
                String ilac3 = rs.getString("ilac3");

                if (ilac1 != null) ilacIsimleri.append(ilac1);
                if (ilac2 != null) ilacIsimleri.append(ilacIsimleri.length() > 0 ? "," + ilac2 : ilac2);
                if (ilac3 != null) ilacIsimleri.append(ilacIsimleri.length() > 0 ? "," + ilac3 : ilac3);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ilacIsimleri.toString(); // İlaç isimlerini x,y,z formatında döndür
    }

    // Tedavi fiyatını güncelleyen fonksiyon
    public boolean tedaviFiyatGuncelle(int tedaviId, double yeniFiyat) throws SQLException {
        String query = "UPDATE tedavi SET tedaviUcreti = ? WHERE tedaviId = ?";

        try {
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setDouble(1, yeniFiyat);
            preparedStatement.setInt(2, tedaviId);

            int result = preparedStatement.executeUpdate();
            return result > 0; // Eğer güncelleme başarılıysa true döndür
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // Güncelleme başarısız olursa false döndür
    }

    // Tedavi silme fonksiyonu
    public boolean tedaviSil(int hastaID) throws SQLException {
        String query = "DELETE FROM tedavi WHERE hastaId = ?";

        try {
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setInt(1, hastaID);

            int result = preparedStatement.executeUpdate();
            return result > 0; // Eğer silme işlemi başarılıysa true döndür
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // Silme işlemi başarısız olursa false döndür
    }
    
    

    // Tedavi fiyatını döndüren fonksiyon
    public double getTedaviFiyati(int tedaviId) throws SQLException {
        String query = "SELECT tedaviUcreti FROM tedavi WHERE tedaviId = ?";
        double tedaviFiyati = -1; // Eğer tedavi bulunamazsa -1 döndür

        try {
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setInt(1, tedaviId);
            rs = preparedStatement.executeQuery();

            if (rs.next()) {
                tedaviFiyati = rs.getDouble("tedaviUcreti");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tedaviFiyati;
    }

    // Doktor ID'sini döndüren fonksiyon
    public int getDoktorId(int tedaviId) throws SQLException {
        String query = "SELECT doktorId FROM tedavi WHERE tedaviId = ?";
        int doktorId = -1; // Eğer tedavi bulunamazsa -1 döndür

        try {
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setInt(1, tedaviId);
            rs = preparedStatement.executeQuery();

            if (rs.next()) {
                doktorId = rs.getInt("doktorId");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return doktorId;
    }

    // Kullanılan ilaçların toplam fiyatını döndüren fonksiyon
    public double getIlacFiyatlariToplami(int tedaviId) throws SQLException {
        String query = "SELECT ilac1Id, ilac2Id, ilac3Id FROM tedavi WHERE tedaviId = ?";
        double toplamFiyat = 0.0;

        try {
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setInt(1, tedaviId);
            rs = preparedStatement.executeQuery();

            if (rs.next()) {
                Ilac ilac = new Ilac();

                // İlaç ID'lerini al
                int ilac1Id = rs.getInt("ilac1Id");
                int ilac2Id = rs.getInt("ilac2Id");
                int ilac3Id = rs.getInt("ilac3Id");

                // Eğer ilaç ID mevcutsa, fiyatını al ve toplama ekle
                if (ilac1Id > 0) toplamFiyat += ilac.getMedicinePrice(ilac1Id);
                if (ilac2Id > 0) toplamFiyat += ilac.getMedicinePrice(ilac2Id);
                if (ilac3Id > 0) toplamFiyat += ilac.getMedicinePrice(ilac3Id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return toplamFiyat; // İlaç fiyatlarının toplamını döndür
    }
    public ArrayList<Object> fetchTreatmentsFromDB() {
        ArrayList<Object> treatmentList = new ArrayList<>();
        try {
            // Veritabanına bağlan
            Tedavi tedavi = new Tedavi(); // Veritabanı bağlantısı Tedavi sınıfında
            String query = "SELECT tedaviId, hastaId, doktorId, tedaviAciklamasi, tedaviUcreti FROM tedavi";
            PreparedStatement preparedStatement = tedavi.con.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            // Veritabanından verileri çek ve listeye aktar
            while (resultSet.next()) {
                int id = resultSet.getInt("tedaviId");
                int hastaId = resultSet.getInt("hastaId");
                int doktorId = resultSet.getInt("doktorId");
                String tedaviAciklamasi = resultSet.getString("tedaviAciklamasi");
                double tedaviUcreti = resultSet.getDouble("tedaviUcreti");
                String ilacIsimleri = this.getIlacIsimleri(id);

                // Tedavi bilgilerini bir dizi olarak listeye ekle
                treatmentList.add(new Object[] {id, hastaId, doktorId, tedaviAciklamasi, tedaviUcreti});
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return treatmentList;
    }
    
    
 // Hasta ID üzerinden tedavi ücretlerinin toplamını getiren fonksiyon
    public double getHastaTedaviUcretleriToplami(int hastaId) throws SQLException {
        String query = "SELECT tedaviUcreti FROM tedavi WHERE hastaId = ?";
        double toplamUcret = 0.0;

        try {
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setInt(1, hastaId);
            rs = preparedStatement.executeQuery();

            if (rs.next()) {
                toplamUcret = rs.getDouble("tedaviUcreti");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return toplamUcret; // Toplam tedavi ücretini döndür
    }

}