package Model;

import Helper.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class Ilac extends User{

    ResultSet rs = null;
    Connection con = conn.connDb();

    public Ilac() {
    }
    // İlaç kayıt fonksiyonu
    public boolean addMedicine(String name, double price, int stock) throws SQLException {
        String query = "INSERT INTO medicine (name, price, stock) VALUES (?, ?, ?)";

        try {
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setString(1, name);
            preparedStatement.setDouble(2, price);
            preparedStatement.setInt(3, stock);

            int result = preparedStatement.executeUpdate();
            if (result > 0) {
                return true; // Eğer kayıt başarılıysa true döndür
            }
        } catch (SQLException e) {
            e.printStackTrace(); // SQL hatasını yazdır
        }
        return false; // Eğer kayıt başarısız olursa false döndür
    }

    // İlaç kullanımı durumunda stok azaltma fonksiyonu
    public boolean useMedicine(int medicineId) throws SQLException {
        String selectQuery = "SELECT stock FROM medicine WHERE medicineId = ?";
        String updateQuery = "UPDATE medicine SET stock = stock - 1 WHERE medicineId = ? AND stock > 0";

        try {
            // İlk olarak mevcut stoğu kontrol et
            PreparedStatement selectStatement = con.prepareStatement(selectQuery);
            selectStatement.setInt(1, medicineId);
            rs = selectStatement.executeQuery();

            if (rs.next()) {
                int stock = rs.getInt("stock");
                if (stock > 0) {
                    // Stok varsa, 1 azalt
                    PreparedStatement updateStatement = con.prepareStatement(updateQuery);
                    updateStatement.setInt(1, medicineId);
                    int result = updateStatement.executeUpdate();
                    if (result > 0) {
                        return true; // Stok başarıyla güncellendi
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // Eğer stok azalmazsa veya hata olursa false döndür
    }

    // İlaç fiyatını döndüren fonksiyon
    public double getMedicinePrice(int medicineId) throws SQLException {
        String query = "SELECT price FROM medicine WHERE medicineId = ?";
        double price = -1;

        try {
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setInt(1, medicineId);
            rs = preparedStatement.executeQuery();

            if (rs.next()) {
                price = rs.getDouble("price");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return price; // Fiyat bulunamazsa -1 döndür
    }

    // İlaç adını döndüren fonksiyon
    public String getMedicineName(int medicineId) throws SQLException {
        String query = "SELECT name FROM medicine WHERE medicineId = ?";
        String name = null;

        try {
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setInt(1, medicineId);
            rs = preparedStatement.executeQuery();

            if (rs.next()) {
                name = rs.getString("name");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return name; // Eğer ilaç bulunamazsa null döndür
    }

    // İlaç stok adedini döndüren fonksiyon
    public int getMedicineStock(int medicineId) throws SQLException {
        String query = "SELECT stock FROM medicine WHERE medicineId = ?";
        int stock = -1;

        try {
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setInt(1, medicineId);
            rs = preparedStatement.executeQuery();

            if (rs.next()) {
                stock = rs.getInt("stock");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return stock; // Eğer stok bulunamazsa -1 döndür
    }
    // İlaç fiyatını güncelleyen fonksiyon
    public boolean updateMedicinePrice(int medicineId, double newPrice) throws SQLException {
        String query = "UPDATE medicine SET price = ? WHERE medicineId = ?";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setDouble(1, newPrice);
            preparedStatement.setInt(2, medicineId);

            int result = preparedStatement.executeUpdate();
            return result > 0; // Eğer güncelleme başarılıysa true döndür
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // Güncelleme başarısız olursa false döndür
    }

    // İlaç ismini güncelleyen fonksiyon
    public boolean updateMedicineName(int medicineId, String newName) throws SQLException {
        String query = "UPDATE medicine SET name = ? WHERE medicineId = ?";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setString(1, newName);
            preparedStatement.setInt(2, medicineId);

            int result = preparedStatement.executeUpdate();
            return result > 0; // Eğer güncelleme başarılıysa true döndür
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // Güncelleme başarısız olursa false döndür
    }

    // İlaç stok miktarını güncelleyen fonksiyon
    public boolean updateMedicineStock(int medicineId, int newStock) throws SQLException {
        String query = "UPDATE medicine SET stock = ? WHERE medicineId = ?";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setInt(1, newStock);
            preparedStatement.setInt(2, medicineId);

            int result = preparedStatement.executeUpdate();
            return result > 0; // Eğer güncelleme başarılıysa true döndür
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // Güncelleme başarısız olursa false döndür
    }

    // İlaç silme fonksiyonu
    public boolean deleteMedicine(int medicineId) throws SQLException {
        String query = "DELETE FROM medicine WHERE medicineId = ?";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setInt(1, medicineId);

            int result = preparedStatement.executeUpdate();
            return result > 0; // Eğer silme işlemi başarılıysa true döndür
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // Silme işlemi başarısız olursa false döndür
    }
    public ArrayList<Object> fetchMedicinesFromDB() {
        ArrayList<Object> medicineList = new ArrayList<>();
        try {
            // Veritabanına bağlan
            Ilac ilac = new Ilac(); // Veritabanı bağlantısı Ilac sınıfında
            String query = "SELECT medicineId, name, price, stock FROM medicine";
            PreparedStatement preparedStatement = ilac.con.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            // Veritabanından verileri çek ve Medicine nesnesine aktar
            while (resultSet.next()) {
                int id = resultSet.getInt("medicineId");
                String name = resultSet.getString("name");
                double price = resultSet.getDouble("price");
                int stock = resultSet.getInt("stock");

                // Medicine nesnesini oluştur ve listeye ekle
                medicineList.add(new Object[] {id, name, price, stock});
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return medicineList;
    }
   
}