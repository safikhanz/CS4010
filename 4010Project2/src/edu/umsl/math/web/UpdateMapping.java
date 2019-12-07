package edu.umsl.math.web;

import javax.servlet.RequestDispatcher;
import javax.servlet.UnavailableException;
import java.sql.*;
import java.io.IOException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/UpdateMapping")
public class UpdateMapping  extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Connection connection;
	private PreparedStatement addtomapping, findTitle, findQuestion, mapcount;
 
	public void init(ServletConfig config) throws ServletException {
		try {
			
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mathprobdb2", "root", "");
			mapcount = connection.prepareStatement("SELECT COUNT(psmid) FROM assignment");
			String insertmappingQuery = "INSERT INTO prob_ass_mapping(psmid,prob_id, ass_id, del) values(?, ?, ?, ?)";
			addtomapping = connection.prepareStatement(insertmappingQuery);	//insert id mapping of keyword and question
			
		}
		catch (Exception exception) {
			exception.printStackTrace();
			throw new UnavailableException(exception.getMessage());
		}
	}
	
	public void destroy() {
		try {
			addtomapping.close();
			connection.close();
			findTitle.close();
			findQuestion.close();
		}
		catch(SQLException SQLException) {
			SQLException.printStackTrace();
		}
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}
	
	public int geCount() {
		int cnt = 0;
		
		try {
			ResultSet rs = mapcount.executeQuery();
			rs.next();
			
			cnt = rs.getInt(1);
		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
		}
		
		return cnt;
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("list.jsp");
		try {
			int aID, qID, count, insertcount;
			String questionparam = request.getParameter("problem");
			String titleparam = request.getParameter("category");
			
			//find the assignment title in assignmentTable
			String findtitlequery = "SELECT aid FROM assignmentTable WHERE title = " +"'"+ titleparam+"'";
			findTitle = connection.prepareStatement(findtitlequery);
			ResultSet rs = findTitle.executeQuery();
			rs.next();
			aID = rs.getInt(1);
			count = geCount();
			insertcount = count +1;
			//find the question in questions (table)
			String findquestionquery = "SELECT pid FROM questions WHERE question = "+"'"+ questionparam+"'";
			findQuestion = connection.prepareStatement(findquestionquery);
			ResultSet qrs = findQuestion.executeQuery();
			qrs.next();
			qID = qrs.getInt(1);
			
			//add qID / aID to questionAssignmentMapping (table)
			addtomapping.setInt(1,insertcount);
			addtomapping.setInt(2,qID);
			addtomapping.setInt(3,aID);
			addtomapping.setInt(4,0);
			addtomapping.executeUpdate();
			
			rs.close();
			qrs.close();
			
			RequestDispatcher view = request.getRequestDispatcher("GetQuestionServlet");
			view.forward(request,response);
		}
		catch(SQLException sqlException) {
			sqlException.printStackTrace();
		}
		
		dispatcher.forward(request, response);
	}

}
