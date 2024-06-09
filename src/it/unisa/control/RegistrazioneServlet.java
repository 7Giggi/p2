package it.unisa.control;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.unisa.model.*;

/**
 * Servlet implementation class RegistraziomeServlet
 */
@WebServlet("/Registrazione")
public class RegistrazioneServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);

	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		UserDao dao = new UserDao();
		String nome = request.getParameter("nome");
		String cognome = request.getParameter("cognome");
		String email = request.getParameter("email");
		String dataNascita = request.getParameter("nascita");
		String username = request.getParameter("us");
		String pwd = hashPassword(request.getParameter("pw"));

        String[] parti = dataNascita.split("-");
        dataNascita = parti[2] + "-" + parti[1] + "-" + parti[0];
		
		try {
			
			UserBean user = new UserBean();
			user.setNome(nome);
			user.setCognome(cognome);
			user.setEmail(email);
			user.setDataDiNascita(Date.valueOf(dataNascita));
			user.setUsername(username);
			user.setPassword(pwd);
			user.setAmministratore(false);
			user.setCap(null);
			user.setIndirizzo(null);
			user.setCartaDiCredito(null);
			dao.doSave(user);
			
		}catch(SQLException e) {
			System.out.println("Error:" + e.getMessage());
		}
				
		response.sendRedirect(request.getContextPath() + "/Home.jsp");

	}
	private String hashPassword(String password) {
        try {
            // Create an instance of MessageDigest with SHA-256 algorithm
            MessageDigest digest = MessageDigest.getInstance("SHA-256");

            // Get the bytes of the password
            byte[] passwordBytes = password.getBytes();

            // Update the digest with the password bytes
            byte[] hashedBytes = digest.digest(passwordBytes);

            // Convert the hashed bytes to a hexadecimal string
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashedBytes) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }

            // Return the hexadecimal string representation of the hashed password
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            // Handle NoSuchAlgorithmException if needed
            e.printStackTrace();
            return null;
        }
	}

}
