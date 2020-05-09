package fr.epita.quizmanager.services.business;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.inject.Inject;
import javax.sql.DataSource;

import fr.epita.quizmanager.datamodel.User;
import fr.epita.quizmanager.services.dao.UserDAO;

/**
 * @author Anh Tu
 *
 */
public class UserDataService {

	@Inject
	DataSource ds;

	@Inject
	UserDAO userDAO;

	public User getUserByLoginName(String loginName) {
		User user = new User();
		try (Connection connection = ds.getConnection();
				PreparedStatement stmt = connection.prepareStatement("SELECT U_ID FROM USERS WHERE U_LOGIN = ?");) {
			stmt.setString(1, loginName);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				Long id = rs.getLong("U_ID");
				user = userDAO.getById(id);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (user != null) {
			return user;
		} else {
			throw new NullPointerException("User is not found.");
		}
	}
}
