package com.ex.url;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/redirect/*")
public class Redirect extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String[] paths = req.getRequestURI().split("/");
		String shortURL = paths[paths.length - 1];
		
		String originalURL = findURL(shortURL);
		
		resp.sendRedirect(originalURL);
	}

	private String findURL(String shortURL) {
		Connection connection = null;
		PreparedStatement statement = null;
		String originalURL = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/original_urls?user=root&password=root");
			statement = connection.prepareStatement("select * from urls where short_url=?");
			statement.setString(1, shortURL);
			
			ResultSet resultSet = statement.executeQuery();
			
			if(resultSet.next()) {
				originalURL = resultSet.getString("original_url");
			}
			
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
		
		return originalURL;
		
	}

}

