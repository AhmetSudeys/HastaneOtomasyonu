package Helper;
import java.sql.*;

public class DBConnection {

	Connection c = null;
	
	public DBConnection() {}
	public Connection connDb() {
		try {
            this.c = DriverManager.getConnection("jdbc:mariadb://localhost:3232/hospital?user=root&password=323232");
            return c;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
		return c;
    }
		
}

//Yukarıda yaptığımız işlem, "Helper" paketi altında database bağlantısın sağlanmasıdır.
	
	
