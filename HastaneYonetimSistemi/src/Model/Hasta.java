package Model;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import Helper.DBConnection;

public class Hasta extends User {
	
	Statement st = null;
	ResultSet rs = null;
	Connection con = conn.connDb();
	PreparedStatement preparedStatment = null;

	public Hasta() {	
	}

	public Hasta(int id, String tcno, String name, String password, String type) {
		super(id, tcno, name, password, type);
		// TODO Auto-generated constructor stub
	}
	
	
	public boolean register(String tcno, String password, String name) throws SQLException {
		int key = 0;
		boolean duplicate = false;
		String query = "INSERT INTO user" + "(tcno,password,name,type) VALUES" + "(?,?,?,?)";

		try {
			st = con.createStatement();
			rs = st.executeQuery("SELECT * FROM user WHERE tcno = '" + tcno + "'");
			
			while (rs.next()) {
				duplicate = true;
				break;
			}
			if (!duplicate) {
			PreparedStatement preparedStatement = con.prepareStatement(query);
			preparedStatement.setString(1, tcno);
			preparedStatement.setString(2, password);
			preparedStatement.setString(3, name);
			preparedStatement.setString(4, "hasta");
			preparedStatement.executeUpdate();
			}
			key = 1;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (key ==1) {
			return true;
		}else {

		return false;
		}
	}

	/*public boolean addAppointment(int doctor_id,int hasta_id,String doctor_name, String hasta_name, String appDate) throws SQLException {
		int key = 0;
		String query = "INSERT INTO appointment" + "(doctor_id,doctor_name,hasta_id,hasta_name,app_date) VALUES" + "(?,?,?,?,?)";

		try {
			
			PreparedStatement preparedStatement = con.prepareStatement(query);
			preparedStatement.setInt(1, doctor_id);
			preparedStatement.setString(2, doctor_name);
			preparedStatement.setInt(3, hasta_id);
			preparedStatement.setString(4, hasta_name);
			preparedStatement.setString(5, appDate);
			preparedStatement.executeUpdate();
			
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (key ==1) {
			return true;
		}else {

		return false;
		}
	}*/
	
	public boolean addAppointment(int doctor_id, int hasta_id, String doctor_name, String hasta_name, String appDate) throws SQLException {
	    String query = "INSERT INTO appointment (doctor_id, doctor_name, hasta_id, hasta_name, app_date) VALUES (?, ?, ?, ?, ?)";

	    try {
	        // PreparedStatement ile sorguyu hazırla
	        PreparedStatement preparedStatement = con.prepareStatement(query);
	        preparedStatement.setInt(1, doctor_id);
	        preparedStatement.setString(2, doctor_name);
	        preparedStatement.setInt(3, hasta_id);
	        preparedStatement.setString(4, hasta_name);
	        preparedStatement.setString(5, appDate);

	        // Etkilenen satır sayısını kontrol et
	        int result = preparedStatement.executeUpdate();
	        if (result > 0) {
	            return true; // Eğer kayıt başarılıysa true döndür
	        }
	    } catch (SQLException e) {
	        // SQL hatasını yazdır
	        e.printStackTrace();
	    }
	    return false; // Eğer bir hata oluşursa veya kayıt başarısız olursa false döndür
	}

	
	
	public boolean updateWhourStatus(int doctor_id,String wdate) throws SQLException {
		int key = 0;
		String query = "UPDATE whour SET status = ? WHERE doctor_id = ? AND wdate = ?";

		try {
			
			PreparedStatement preparedStatement = con.prepareStatement(query);
			preparedStatement.setString(1, "p");
			preparedStatement.setInt(2, doctor_id);
			preparedStatement.setString(3, wdate);
			preparedStatement.executeUpdate();
			
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (key ==1) {
			return true;
		}else {

		return false;
		}
	}
	public  int getHastaIdByTc(String tcno) throws SQLException {
		Connection con = conn.connDb();
		String query = "SELECT id FROM user WHERE tcno = ?";
		int hastaId = -1; // Varsayılan değer (ID bulunamazsa -1 dönecek)

		try {
			PreparedStatement preparedStatement = con.prepareStatement(query);
			preparedStatement.setString(1, tcno);
			ResultSet rs = preparedStatement.executeQuery();

			if (rs.next()) {
				hastaId = rs.getInt("id");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (con != null) {
				con.close();
			}
		}
		return hastaId;
	}

	public  String getTcByHastaId(int id) throws SQLException {
		Connection con = conn.connDb();
		String query = "SELECT tcno FROM user WHERE id = ?";
		String tcno = null; // Varsayılan değer (TC bulunamazsa null dönecek)

		try {
			PreparedStatement preparedStatement = con.prepareStatement(query);
			preparedStatement.setInt(1, id);
			ResultSet rs = preparedStatement.executeQuery();

			if (rs.next()) {
				tcno = rs.getString("tcno");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (con != null) {
				con.close();
			}
		}
		return tcno;
	}
	
	
	
	public  String getSigortaByHastaId(int id) throws SQLException {
		Connection con = conn.connDb();
		String query = "SELECT sigorta FROM user WHERE id = ?";
		String tcno = null; // Varsayılan değer (TC bulunamazsa null dönecek)

		try {
			PreparedStatement preparedStatement = con.prepareStatement(query);
			preparedStatement.setInt(1, id);
			ResultSet rs = preparedStatement.executeQuery();

			if (rs.next()) {
				tcno = rs.getString("sigorta");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (con != null) {
				con.close();
			}
		}
		return tcno;
	}
	
	
	public String getHastaTedaviUcretBilgisi(int hastaId) throws SQLException {
		Tedavi ornek = new Tedavi();
		
		// 1. Hastanın toplam tedavi ücretini al
	    double toplamUcret = ornek.getHastaTedaviUcretleriToplami(hastaId);
	    
	    // 2. Hastanın sigorta tipini al
	    String sigortaTipi = getSigortaByHastaId(hastaId);
	    
	    // 3. Sigorta tipine göre hastanın ödeyeceği tutarı hesapla
	    double hastaOdemeMiktari = toplamUcret; // Varsayılan olarak sigorta katkısı yok
	    double sigortaKapsamMiktari = 0.0;

	    if (sigortaTipi != null) {
	        switch (sigortaTipi.trim().toUpperCase()) {
	            case "TIP 1":
	                sigortaKapsamMiktari = toplamUcret; // %100 sigorta karşılıyor
	                hastaOdemeMiktari = 0.0;
	                break;
	            case "TIP 2":
	                sigortaKapsamMiktari = toplamUcret * 0.75; // %75 sigorta karşılıyor
	                hastaOdemeMiktari = toplamUcret * 0.25;
	                break;
	            case "TIP 3":
	                sigortaKapsamMiktari = toplamUcret * 0.50; // %50 sigorta karşılıyor
	                hastaOdemeMiktari = toplamUcret * 0.50;
	                break;
	            default:
	                // Sigorta tipi geçerli değil, hasta tüm ücreti öder
	                sigortaKapsamMiktari = 0.0;
	                hastaOdemeMiktari = toplamUcret;
	                break;
	        }
	    }

	    // 4. Sonucu formatlı bir String olarak oluştur
	    StringBuilder bilgi = new StringBuilder();
	    bilgi.append("Hasta ID: ").append(hastaId).append("\n");
	    bilgi.append("Toplam Tedavi Ücreti: ").append(String.format("%.2f", toplamUcret)).append(" TL\n");
	    bilgi.append("Sigorta Tipi: ").append(sigortaTipi != null ? sigortaTipi : "Sigorta bilgisi bulunamadı").append("\n");
	    bilgi.append("Sigorta Karşılanan Tutar: ").append(String.format("%.2f", sigortaKapsamMiktari)).append(" TL\n");
	    bilgi.append("Hasta Tarafından Ödenecek Tutar: ").append(String.format("%.2f", hastaOdemeMiktari)).append(" TL\n");

	    return bilgi.toString(); // Bilgi stringini döndür
	}
	
	
}