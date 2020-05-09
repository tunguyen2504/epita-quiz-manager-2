package fr.epita.quizmanager.services.dao;

import java.util.Map;

import fr.epita.quizmanager.datamodel.User;

/**
 * @author Anh Tu
 *
 */
public class UserDAO extends GenericDAO<User, Long> {

	@Override
	public String getQuery() {
		return "from User where loginName = :pLoginName";
	}

	@Override
	public void setParameters(Map<String, Object> parameters, User criteria) {
		parameters.put("pLoginName", criteria.getLoginName());
		
	}

	@Override
	public Class<User> getEntityClass() {
		return User.class;
	}
	
	
}
