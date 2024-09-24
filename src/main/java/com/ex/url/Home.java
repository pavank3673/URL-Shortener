package com.ex.url;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/")
public class Home extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		List<URL> urls = fetchAllURLS();
		
		req.setAttribute("urls", urls);
		req.getRequestDispatcher("Home.jsp").forward(req, resp);
	}

	private List<URL> fetchAllURLS() {
		List<URL> urls = new ArrayList<>();
		Connection connection = null;
		PreparedStatement statement = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/original_urls?user=root&password=root");
			statement = connection.prepareStatement("select * from urls");
			
			ResultSet resultSet = statement.executeQuery();
			
			while(resultSet.next()) {
				URL url = new URL();
				url.setOriginal_url(resultSet.getString("original_url"));
				url.setShort_url("http://localhost:8080/URL-Shortener/redirect/" + resultSet.getString("short_url"));
				
				urls.add(url);
			}
			
		} 
		catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		finally {
			if(connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(statement != null) {
				try {
					statement.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
		return urls;
	}
	
	
	
}
