package kitpvp.MySQL;

import java.sql.Connection;
import java.sql.SQLException;

import MySQL.MySQL;
import kitpvp.Main;

public class MySQLManager {
	
	private MySQL db;
	private Connection c;
	private boolean isMySQL = Main.getConfigFile().getBoolean("MySQL.enabled");
	private String hostname = Main.getConfigFile().getString("MySQL.hostname");
	private String port = Main.getConfigFile().getString("MySQL.port");
	private String database = Main.getConfigFile().getString("MySQL.database");
	private String username = Main.getConfigFile().getString("MySQL.username");
	private String password = Main.getConfigFile().getString("MySQL.password");
	
	public void setupDataBase(){
		if(isMySQL){
			this.db = new MySQL(hostname, port, database, username, password);
			try {
				c = db.openConnection();
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public MySQL getDatabase() {
		return db;
	}
	
	public Connection getConnection(){
		return c;
	}
}
