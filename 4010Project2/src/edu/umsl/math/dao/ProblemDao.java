package edu.umsl.math.dao;

import edu.umsl.math.beans.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.UnavailableException;

public class ProblemDao {

	private Connection connection;
	private PreparedStatement results;
	private PreparedStatement probcnt;
	private PreparedStatement assprobcnt;
	private PreparedStatement assresults;
	private PreparedStatement insertprob;
	public ProblemDao() throws Exception {

		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mathprobdb2", "root", "");
			
			results = connection.prepareStatement(
					"SELECT pid, content, order_num " + "FROM problem ORDER BY order_num DESC LIMIT ?, ?");
			
			probcnt = connection.prepareStatement("SELECT COUNT(pid) FROM problem");
			
			assprobcnt = connection.prepareStatement("SELECT COUNT(p.pid) FROM problem AS p, prob_ass_mapping AS pam WHERE p.pid = pam.prob_id AND pam.ass_id = ?");
			
			assresults = connection.prepareStatement(
					"SELECT p.pid, p.content, p.order_num " + "FROM problem AS p, prob_ass_mapping AS pam WHERE p.pid = pam.prob_id AND pam.ass_id = ? ORDER BY p.order_num DESC LIMIT ?, ?");
			
			insertprob = connection.prepareStatement("INSERT INTO problem (pid, content, order_num, del) VALUES (?,?,?,?)");
		} catch (Exception exception) {
			exception.printStackTrace();
			throw new UnavailableException(exception.getMessage());
		}

	}
	
	public int getAssProblemCount(int aid) {
		int cnt = 0;
		
		try {
			assprobcnt.setInt(1, aid);
			
			ResultSet rs = assprobcnt.executeQuery();
			rs.next();
			
			cnt = rs.getInt(1);
		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
		}
		
		return cnt;
	}
	
	public int getProblemCount() {
		int cnt = 0;
		
		try {
			ResultSet rs = probcnt.executeQuery();
			rs.next();
			
			cnt = rs.getInt(1);
		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
		}
		
		return cnt;
	}
	
	public List<Problem> getProblemList() throws SQLException {
		List<Problem> problist = new ArrayList<Problem>();
		
		try {
			ResultSet resultsRS = results.executeQuery();

			while (resultsRS.next()) {
				Problem prob = new Problem();

				prob.setPid(resultsRS.getInt(1));
				prob.setContent(resultsRS.getString(2));
				prob.setOrder_num(resultsRS.getInt(3));

				problist.add(prob);
			}
		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
		}
		
		return problist;
	}
	
	public List<Problem> getProblemListByPage(int pg) throws SQLException {
		List<Problem> problist = new ArrayList<Problem>();
		
		int st = 10 * (pg - 1);
		
		try {
			results.setInt(1, st);
			results.setInt(2, 10);
			
			ResultSet resultsRS = results.executeQuery();

			while (resultsRS.next()) {
				Problem prob = new Problem();

				prob.setPid(resultsRS.getInt(1));
				prob.setContent(resultsRS.getString(2));
				prob.setOrder_num(resultsRS.getInt(3));

				problist.add(prob);
			}
		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
		}
		
		return problist;
	}
	
	public List<Problem> getAssProblemListByPage(int pg, int as) throws SQLException {
		List<Problem> problist = new ArrayList<Problem>();
		
		int st = 10 * (pg - 1);
		
		try {
			assresults.setInt(1, as);
			assresults.setInt(2, st);
			assresults.setInt(3, 10);
			
			ResultSet resultsRS = assresults.executeQuery();

			while (resultsRS.next()) {
				Problem prob = new Problem();

				prob.setPid(resultsRS.getInt(1));
				prob.setContent(resultsRS.getString(2));
				prob.setOrder_num(resultsRS.getInt(3));

				problist.add(prob);
			}
		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
		}
		
		return problist;
	}
	
	public void setProblem(String problem) throws SQLException{
		int count =0;
		int insertcount =0;
		count = getProblemCount();
		insertcount = count +1;
		insertprob.setString(2, problem);
		insertprob.setInt(1, insertcount);
		insertprob.setInt(3, insertcount);
		insertprob.setInt(4, 0);
		insertprob.executeUpdate();
	}

}
