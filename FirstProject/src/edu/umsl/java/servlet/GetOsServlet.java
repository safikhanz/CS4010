package edu.umsl.java.servlet;
import java.io.IOException;

import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/getos")

public class GetOsServlet extends HttpServlet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		ServletContext ctx = this.getServletContext();
		
		String cities = ctx.getInitParameter("cities");
		String rates = ctx.getInitParameter("tax rates");
		
		System.out.println("Cities: " + cities);
		System.out.println("Tax rates: " + rates);
		
		//Cities now  separated
		String[] cityArr = cities.split(",");
		
		request.setAttribute("cities", cityArr);
		
	
		//foward to jsp depending on OS
		String userAgent = request.getHeader("User-Agent");
		if (userAgent.contains("Macintosh")) {
			RequestDispatcher view = request.getRequestDispatcher("inputMac.jsp");
			view.forward(request, response);
		}
		else if (userAgent.contains("Windows")) {
				RequestDispatcher view = request.getRequestDispatcher("inputWin.jsp");
				view.forward(request, response);	
		}
		
			
	}


}
