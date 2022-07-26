package com.credmanager;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class Contact
 */
@WebServlet("/contact")
public class Contact extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {		
		
		
		String uname = request.getParameter("name");
		String uemail = request.getParameter("email");
		String uphone = request.getParameter("phone");
		String umessage = request.getParameter("message");
		
		RequestDispatcher dispacher = null;
		Connection con=null;
		try {

			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/contactus?useSSL=false", "root", "root");
			PreparedStatement pst = con
					.prepareStatement("insert into contactform(uname,uemail,uphone,message) values(?,?,?,?)");
			pst.setString(1, uname);
			pst.setString(2, uemail);
			pst.setString(3, uphone);
			pst.setString(4, umessage);			
			System.out.println(uname+uemail+uphone+umessage);

			int rowCount = pst.executeUpdate();
			dispacher = request.getRequestDispatcher("index.jsp");

			if (rowCount > 0) {
				request.setAttribute("status", "success");

			} else {
				request.setAttribute("status", "failed");
			}
			
			dispacher.forward(request, response);

		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}

	}
       
   
}
