package edu.umsl.math.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.UnavailableException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.umsl.math.dao.AssignmentDao;
import edu.umsl.math.dao.ProblemDao;


/**
 * Servlet implementation class InsertMathServlet
 */
@WebServlet("/InsertMathServlet")
public class InsertMathServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	private PreparedStatement addtomapping, findTitle, findQuestion, mapcount;
	private Connection connection;
	
	public void init(ServletConfig config) throws ServletException {
		try {
			
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mathprobdb2", "root", "");
			mapcount = connection.prepareStatement("SELECT COUNT(psmid) FROM prob_ass_mapping");
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

	

	protected void doPost(HttpServletRequest request,  HttpServletResponse response) throws ServletException, IOException { 
		ProblemDao probdao;
		AssignmentDao assdao;
		String Problem = request.getParameter("problem");
		String Category = request.getParameter("category");
		response.setContentType("text/html");
	      PrintWriter out = response.getWriter();
		  if(Problem !=null && Problem != "" ) {
				try {  
					   
					
					probdao = new ProblemDao();
					assdao = new AssignmentDao();
					List<String> catelist = new ArrayList();
					catelist = assdao.getCateList();
					
					  probdao.setProblem(Problem);
					
					  
					   if(!isCateExists(Category, catelist)) {
						  assdao.setCate(Category);
					   }
					   int aID, qID, count, insertcount;
						String questionparam = request.getParameter("problem");
						String titleparam = request.getParameter("category");
						
						//find the assignment title in assignmentTable
						String findtitlequery = "SELECT aid FROM assignment WHERE title = " +"'"+ titleparam+"'";
						findTitle = connection.prepareStatement(findtitlequery);
						ResultSet rs = findTitle.executeQuery();
						rs.next();
						aID = rs.getInt(1);
						count = geCount();
						insertcount = count +1;
						//find the question in questions (table)
						String findquestionquery = "SELECT pid FROM problem WHERE content = "+"'"+ questionparam+"'";
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
						
						
			                      
				} 
				catch (Exception e) { 
					e.printStackTrace(); 
				}
	
		}
		// start XHTML document

	      out.println("<?xml version = \"1.0\"?>");
	      out.println("<!DOCTYPE html PUBLIC \"-//W3C//DTD "
	            + "XHTML 1.0 Strict//EN\" \"http://www.w3.org"
	            + "/TR/xhtml1/DTD/xhtml1-strict.dtd\">");
	      out.println("<title>Thank you!</title>");
	         out.println("</head>");
	         out.println("<body>");
	         out.println("<p>Thank you for participating.");
	         out.println("<br />Results:</p><pre>");
		  //response.sendRedirect("/list.jsp");
		 
				    
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	
	}
	
	public  boolean isCateExists(String cate, List<String> catlist) {
		boolean isCateExists = false;//password checking flag, if matching set as true else false
		
		if (catlist!= null) {
			//iterating password LinkedList
			for (int i=0;i<catlist.size();i++) {
				
				if (catlist.get(i).equals(cate)) {
					isCateExists = true;//If it is matched flag set as true
				
					break;//terminating the loop as already matched
				}
			}
		}
		//returning flag
		return isCateExists ;
	}
	
	
	
	
} 
	
	

