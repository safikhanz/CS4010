package edu.umsl.math.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.UnavailableException;

import edu.umsl.math.beans.Assignment;

public class AssignmentDao {
	
	private Connection connection;
	private PreparedStatement allass;
	private PreparedStatement allassinsert;
	private PreparedStatement catecnt;
	List<Assignment> asslist = new ArrayList<Assignment>();
	List<String> catelist = new ArrayList<String>();
	public AssignmentDao() throws Exception {
		String sql="INSERT INTO `assignment`(`aid`, `title`, `del`) VALUES (?,?,?)"; 
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mathprobdb2", "root", "");
			
			allass = connection.prepareStatement("SELECT * FROM assignment");
			catecnt = connection.prepareStatement("SELECT COUNT(aid) FROM assignment");
			allassinsert = connection.prepareStatement(sql);
		} catch (Exception exception) {
			exception.printStackTrace();
			throw new UnavailableException(exception.getMessage());
		}

	}
	
	public List<Assignment> getAssignmentList() throws SQLException {
		
		
		try {
			ResultSet resultsRS = allass.executeQuery();

			while (resultsRS.next()) {
				Assignment ass = new Assignment();

				ass.setAid(resultsRS.getInt(1));
				ass.setTitle(resultsRS.getString(2));

				asslist.add(ass);
			}
		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
		}
		
		return asslist;
	}
	
public List<String> getCateList() throws SQLException {
		
		
		try {
			ResultSet resultsRS = allass.executeQuery();

			while (resultsRS.next()) {
		String ass;

				
				ass=resultsRS.getString(2);

				catelist.add(ass);
			}
		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
		}
		
		return catelist;
	}
	
	public int getCateCount() {
		int cnt = 0;
		
		try {
			ResultSet rs = catecnt.executeQuery();
			rs.next();
			
			cnt = rs.getInt(1);
		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
		}
		
		return cnt;
	}
	
	
	
	public void setCate(String category) throws SQLException{
		int count =0;
		int insertcount =0;
		count = getCateCount();
		insertcount = count +1;
		allassinsert.setString(2, category);
		allassinsert.setInt(1, insertcount);
		allassinsert.setInt(3, 0);
	
		allassinsert.executeUpdate();
	}

	
	
	
	

}
