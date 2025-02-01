package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SevkModul extends User {

    private int hastaId;
    private String sevkEdilecekHastane;
    private Connection con = conn.connDb();

    public SevkModul(int hastaId, String sevkEdilecekHastane) {
        this.hastaId = hastaId;
        this.sevkEdilecekHastane = sevkEdilecekHastane;
    }

    public String hastaBilgileriniListele() {
        StringBuilder bilgiToplami = new StringBuilder();
        try {
            // Hasta bilgilerini al
            String hastaQuery = "SELECT * FROM user WHERE id = ?";
            PreparedStatement hastaStatement = con.prepareStatement(hastaQuery);
            hastaStatement.setInt(1, hastaId);
            ResultSet hastaResult = hastaStatement.executeQuery();

            if (hastaResult.next()) {
                bilgiToplami.append("Hasta Bilgileri:\n");
                bilgiToplami.append("ID: ").append(hastaResult.getInt("id")).append("\n");
                bilgiToplami.append("TC No: ").append(hastaResult.getString("tcno")).append("\n");
                bilgiToplami.append("Adı: ").append(hastaResult.getString("name")).append("\n");
                bilgiToplami.append("Hastanın sevk edileceği hastane: ").append(sevkEdilecekHastane).append("\n\n");
            }

            // Tedavi bilgilerini al
            String tedaviQuery = "SELECT * FROM tedavi WHERE hastaId = ?";
            PreparedStatement tedaviStatement = con.prepareStatement(tedaviQuery);
            tedaviStatement.setInt(1, hastaId);
            ResultSet tedaviResult = tedaviStatement.executeQuery();

            bilgiToplami.append("Tedavi Bilgileri:\n");
            while (tedaviResult.next()) {
                bilgiToplami.append("Doktor ID: ").append(tedaviResult.getInt("doktorId")).append("\n");
                bilgiToplami.append("Tedavi Açıklaması: ").append(tedaviResult.getString("tedaviAciklamasi")).append("\n");
                bilgiToplami.append("Tedavi Ücreti: ").append(tedaviResult.getDouble("tedaviUcreti")).append("\n");

                // Tedavide kullanılan ilaç bilgilerini al
                ArrayList<Integer> ilacIds = new ArrayList<>();
                ilacIds.add(tedaviResult.getInt("ilac1Id"));
                ilacIds.add(tedaviResult.getInt("ilac2Id"));
                ilacIds.add(tedaviResult.getInt("ilac3Id"));

                bilgiToplami.append("Kullanılan İlaçlar:\n");
                for (int ilacId : ilacIds) {
                    if (ilacId > 0) { // Geçerli bir ilaç ID'si var mı
                        String ilacQuery = "SELECT * FROM medicine WHERE medicineId = ?";
                        PreparedStatement ilacStatement = con.prepareStatement(ilacQuery);
                        ilacStatement.setInt(1, ilacId);
                        ResultSet ilacResult = ilacStatement.executeQuery();

                        if (ilacResult.next()) {
                            bilgiToplami.append("  İsim: ").append(ilacResult.getString("name")).append("\n");
                            bilgiToplami.append("  Fiyat: ").append(ilacResult.getDouble("price")).append("\n");
                            bilgiToplami.append("  Stok: ").append(ilacResult.getInt("stock")).append("\n");
                        }
                    }
                }
                bilgiToplami.append("\n");
            }

            // Tahlil bilgilerini al
            Tahlil tahlil = new Tahlil();
            ArrayList<Object> tahlilListesi = tahlil.fetchTahlillerFromDB();
            bilgiToplami.append("Tahlil Bilgileri:\n");
            for (Object tahlilBilgi : tahlilListesi) {
                Object[] tahlilBilgiArray = (Object[]) tahlilBilgi;
                String tc = (String) tahlilBilgiArray[0];
                String testTuru = (String) tahlilBilgiArray[1];
                String sonuc = (String) tahlilBilgiArray[2];

                if (tc != null && testTuru != null && sonuc != null) {
                    bilgiToplami.append("- TC No: ").append(tc).append("\n");
                    bilgiToplami.append("  Test Türü: ").append(testTuru).append("\n");
                    bilgiToplami.append("  Sonuç: ").append(sonuc).append("\n");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return "Hata: Bilgiler alınamadı!";
        }

        return bilgiToplami.toString();
    }
}