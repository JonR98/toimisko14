package app.dao;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import app.model.Ehdokas;

//made by Joona & Joni
public class Dao {

	private Connection conn;

	// When new instance is created, also DB-connection is created
	public Dao() {
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			conn=java.sql.DriverManager.getConnection("jdbc:mysql://localhost:3306/vaalikone", "pena", "kukkuu");
		} catch (SQLException | InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// Manually close DB-connection for releasing resource
	public void close() {
		try {
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	// Tallentaa ehdokkaat tietokantaan syötetyt tiedot tietokantaan
	public int saveEhdokas(Ehdokas ehdokas) {
		Statement stmt=null;
		int count=0;
		try {
			stmt = conn.createStatement();
			count=stmt.executeUpdate("insert into ehdokkaat(sukunimi, etunimi, puolue, kotipaikkakunta, ika, miksi_eduskuntaan, mita_asioita_haluat_edistaa, ammatti) values('"+ehdokas.getSukunimi()+"','"+ehdokas.getEtunimi()+"','"+ehdokas.getPuolue()+"','"+ehdokas.getKotipaikkakunta()+"','"+ehdokas.getIka()+"','"+ehdokas.getMiksi_eduskuntaan()+"','"+ehdokas.getMita_asioita_haluat_edistaa()+"','"+ehdokas.getAmmatti()+"')");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}
	
	
	// Lukee ehdokkaat tauluun laitetut tiedot ja lisää ne arraylistiin
	public ArrayList<Ehdokas> readAllEhdokas() {
		ArrayList<Ehdokas> list=new ArrayList<>();
		Statement stmt=null;
		int count=0;
		try {
			stmt = conn.createStatement();
			ResultSet rs=stmt.executeQuery("select * from ehdokkaat");
			while (rs.next()) {
				Ehdokas ehdokas=new Ehdokas();
				ehdokas.setId(rs.getInt("Ehdokas_id"));
				ehdokas.setSukunimi(rs.getString("Sukunimi"));
				ehdokas.setEtunimi(rs.getString("Etunimi"));
				ehdokas.setPuolue(rs.getString("Puolue"));
				ehdokas.setKotipaikkakunta(rs.getString("Kotipaikkakunta"));
				ehdokas.setIka(rs.getInt("Ika"));
				ehdokas.setMiksi_eduskuntaan(rs.getString("Miksi_eduskuntaan"));
				ehdokas.setMita_asioita_haluat_edistaa(rs.getString("Mita_asioita_haluat_edistaa"));
				ehdokas.setAmmatti(rs.getString("Ammatti"));
				list.add(ehdokas);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	//Päivittää tietokantaan ehdokkaat syötetyt tiedot
	public int updateEhdokas(Ehdokas ehdokas) {
		int count = 0;
		String sql = "update ehdokkaat set etunimi = ?, sukunimi = ?, puolue = ?, kotipaikkakunta = ?, ika = ?, miksi_eduskuntaan = ?, mita_asioita_haluat_edistaa = ?, ammatti = ? where ehdokas_id = ?";
		try {
			PreparedStatement stmt = conn.prepareStatement(sql);
			
			//stmt.setInt(1, ehdokas.getId());
			stmt.setString(1, ehdokas.getEtunimi());
			stmt.setString(2, ehdokas.getSukunimi());
			stmt.setString(3, ehdokas.getPuolue());
			stmt.setString(4, ehdokas.getKotipaikkakunta());
			stmt.setInt(5, ehdokas.getIka());
			stmt.setString(6, ehdokas.getMiksi_eduskuntaan());
			stmt.setString(7, ehdokas.getMita_asioita_haluat_edistaa());
			stmt.setString(8, ehdokas.getAmmatti());
			stmt.setInt(9, ehdokas.getId());

			
			count = stmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}
	//Näyttää tietokannan tiedot
	public Ehdokas getEhdokasInfo(int id) {
		Ehdokas result = null;
		String sql = "select * from ehdokkaat where ehdokas_id = ?";
		try {
			PreparedStatement stmt = conn.prepareStatement(sql);
						
			stmt.setInt(1, id);
			
			ResultSet resultset = stmt.executeQuery();
			
			if (resultset.next()) {
				result = new Ehdokas();
				result.setId(resultset.getInt("ehdokas_id"));
				result.setSukunimi(resultset.getString("sukunimi"));
				result.setEtunimi(resultset.getString("etunimi"));
				result.setPuolue(resultset.getString("puolue"));
				result.setKotipaikkakunta(resultset.getString("kotipaikkakunta"));
				result.setIka(resultset.getInt("ika"));
				result.setMiksi_eduskuntaan(resultset.getString("miksi_eduskuntaan"));
				result.setMita_asioita_haluat_edistaa(resultset.getString("mita_asioita_haluat_edistaa"));
				result.setAmmatti(resultset.getString("ammatti"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
		//Poistaa ehdokkaan tietokannasta metodi
		public void Poistaehdokas(int ehdokas_id) {
	        String sql = "delete from ehdokkaat where ehdokas_id=?";
	        try {

	        PreparedStatement statement = conn.prepareStatement(sql);

	            statement.setInt(1, ehdokas_id);
	            statement.executeUpdate();
	        } catch (SQLException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        }

	}
}

