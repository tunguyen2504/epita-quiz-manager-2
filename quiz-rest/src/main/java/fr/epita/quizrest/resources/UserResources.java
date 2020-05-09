package fr.epita.quizrest.resources;

import java.net.URI;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.RequestBody;

import fr.epita.quizmanager.datamodel.User;
import fr.epita.quizmanager.services.business.UserDataService;
import fr.epita.quizmanager.services.dao.UserDAO;
import fr.epita.quizrest.dto.UserDTO;

/**
 * @author Anh Tu
 *
 */
@Path("/user")
public class UserResources {
	
	private static final Logger LOGGER = LogManager.getLogger(ExamResource.class);
	
	@Inject
	UserDAO userDAO;
	
	@Inject
	UserDataService userDS;
	
	@POST
	@Path("/create")
	@Consumes(value = MediaType.APPLICATION_JSON)
	@Produces(value = MediaType.APPLICATION_JSON)
	public Response createUser(@RequestBody UserDTO userDTO) {
		User user = new User();
		if (userDTO.getLoginName() == null || userDTO.getLoginName().trim().isEmpty()) {
			try {
				return Response.status(Status.BAD_REQUEST).entity("Username cannot be empty.").build();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			user.setLoginName(userDTO.getLoginName());
			user.setIsTeacher(userDTO.getIsTeacher());
		}
		
		try {
			userDAO.create(user);
			UserDTO resUserDTO = new UserDTO(user);
			return Response.created(new URI("/rest/user/create" + resUserDTO.getId())).entity(resUserDTO).build();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return Response.serverError().build();
	}
	
	@GET
	@Path("/{loginName}/getUser")
	@Produces(value = MediaType.APPLICATION_JSON)
	public Response getUserByLoginName(@PathParam("loginName") String loginName) {
		User user = new User();
		try {
			user = userDS.getUserByLoginName(loginName);
			UserDTO userDTO = new UserDTO(user);
			return Response.ok(userDTO).build();
		} catch (NullPointerException e) {
			LOGGER.error(e.getMessage());
			return Response.status(Status.NOT_FOUND).entity(e.getMessage()).build();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Response.serverError().build();
	}
	
	
}
