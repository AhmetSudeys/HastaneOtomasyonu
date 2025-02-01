package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import Helper.DBConnection;

public class Personel extends User {
    private int id;
    private String name;
    private String role;

    // Veritabanı bağlantısı
    DBConnection conn = new DBConnection();
    Connection con = conn.connDb();

    public Personel(int id, String name, String role) {
        this.id = id;
        this.name = name;
        this.role = role;
    }

    public Personel() {}

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getRole() {
        return role;
    }

    // Veritabanında personel bilgisi ekleme
    public boolean addPersonel(String name, String role) throws SQLException {
        String query = "INSERT INTO personel (name, role) VALUES (?, ?)";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, role);
            int result = preparedStatement.executeUpdate();
            return result > 0; // Başarılıysa true döner
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // Başarısızsa false döner
    }

    // Veritabanından personel bilgisi güncelleme
    public boolean updatePersonel(int id, String name, String role) throws SQLException {
        String query = "UPDATE personel SET name = ?, role = ? WHERE id = ?";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, role);
            preparedStatement.setInt(3, id);
            int result = preparedStatement.executeUpdate();
            return result > 0; // Başarılıysa true döner
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // Başarısızsa false döner
    }

    // Veritabanından personel bilgisi silme
    public boolean deletePersonel(int id) throws SQLException {
        String query = "DELETE FROM personel WHERE id = ?";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setInt(1, id);
            int result = preparedStatement.executeUpdate();
            return result > 0; // Başarılıysa true döner
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // Başarısızsa false döner
    }

    // Veritabanından personel bilgisi listeleme
    public ResultSet listPersonel() throws SQLException {
        String query = "SELECT * FROM personel";
        try {
            Statement st = con.createStatement();
            return st.executeQuery(query); // ResultSet döner
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
