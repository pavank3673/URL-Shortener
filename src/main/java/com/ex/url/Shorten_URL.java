package com.ex.url;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/shorten")
public class Shorten_URL extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String originalURL = req.getParameter("url");
		
		Random random = new Random();
		String short_url = String.valueOf(random.nextInt(9999));
		
		saveURL(originalURL, short_url);
		
		resp.sendRedirect("/URL-Shortener/");
		
		
	}

	private void saveURL(String originalURL, String short_url) {
		Connection connection = null;
		PreparedStatement statement = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/original_urls?user=root&password=root");
			statement = connection.prepareStatement("insert into urls values(?,?)");
			statement.setString(1, short_url);
			statement.setString(2, originalURL);
			
			statement.executeUpdate();
			
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}finally {
			if(statement != null) {
				try {
					statement.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			
			if(connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
 		
	}
}
