package fr.epita.quizrest.resources;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.RequestBody;

import fr.epita.quizmanager.datamodel.MCQChoice;
import fr.epita.quizmanager.datamodel.Question;
import fr.epita.quizmanager.services.business.QuestionDataService;
import fr.epita.quizmanager.services.dao.MCQChoiceDAO;
import fr.epita.quizmanager.services.dao.QuestionDAO;
import fr.epita.quizrest.dto.MCQChoiceDTO;
import fr.epita.quizrest.dto.QuestionDTO;

@Path("/question")
public class QuestionResource {

	private static final Logger LOGGER = LogManager.getLogger(ExamResource.class);

	@Inject
	QuestionDataService questionDS;

	@Inject
	QuestionDAO questionDAO;

	@Inject
	MCQChoiceDAO mcqChoiceDAO;

	@POST
	@Path("/create")
	@Consumes(value = MediaType.APPLICATION_JSON)
	@Produces(value = MediaType.APPLICATION_JSON)
	public Response createQuestion(@RequestBody QuestionDTO questionDTO) {
		Question question = new Question();
		if (questionDTO.getTitle() == null || questionDTO.getTitle().trim().isEmpty()) {
			try {
				return Response.status(Status.BAD_REQUEST).entity("Question title cannot be empty.").build();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			question.setTitle(questionDTO.getTitle());
		}

		List<MCQChoice> choices = new ArrayList<MCQChoice>();
		for (MCQChoiceDTO choiceDTO : questionDTO.getChoices()) {
			if (choiceDTO.getContent() == null || choiceDTO.getContent().trim().isEmpty()) {
				try {
					return Response.status(Status.BAD_REQUEST).entity("Choice content cannot be empty.").build();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			MCQChoice choice = new MCQChoice();
			choice.setContent(choiceDTO.getContent());
			choice.setIsValid(choiceDTO.getIsValid());
			choices.add(choice);
		}

		try {
			questionDS.createQuestion(question, choices);
			QuestionDTO resQuestionDTO = new QuestionDTO(question);
			return Response.created(new URI("/rest/question/create" + resQuestionDTO.getId())).entity(resQuestionDTO)
					.build();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Response.serverError().build();
	}

	@PUT
	@Path("/{id}/update")
	@Consumes(value = MediaType.APPLICATION_JSON)
	@Produces(value = MediaType.APPLICATION_JSON)
	public Response updateQuestion(@PathParam("id") long id, @RequestBody QuestionDTO questionDTO) {
		Question question = questionDAO.getById(id);
		if (questionDTO.getTitle() == null || questionDTO.getTitle().trim().isEmpty()) {
			try {
				return Response.status(Status.BAD_REQUEST).entity("Question title cannot be empty.").build();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			question.setTitle(questionDTO.getTitle());
		}
		List<MCQChoice> choices = new ArrayList<MCQChoice>();
		if (questionDTO.getChoices() != null && !questionDTO.getChoices().isEmpty()) {
			for (MCQChoiceDTO choiceDTO : questionDTO.getChoices()) {
				if (choiceDTO.getContent() == null || choiceDTO.getContent().trim().isEmpty()) {
					try {
						return Response.status(Status.BAD_REQUEST).entity("Choice content cannot be empty.").build();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				MCQChoice choice = mcqChoiceDAO.getById(choiceDTO.getId());
				choice.setContent(choiceDTO.getContent());
				choice.setIsValid(choiceDTO.getIsValid());
				choices.add(choice);
			}
		} else {
			try {
				choices = questionDS.getAllChoices(question);
			} catch (NullPointerException e) {
				LOGGER.error(e.getMessage());
				return Response.status(Status.NOT_FOUND).entity(e.getMessage()).build();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		try {
			questionDS.updateQuestion(question, choices);
			QuestionDTO resQuestionDTO = new QuestionDTO(question);
			return Response.ok(resQuestionDTO).build();
		} catch (NullPointerException e) {
			LOGGER.error(e.getMessage());
			return Response.status(Status.NOT_FOUND).entity(e.getMessage()).build();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return Response.serverError().build();
	}
	
	@GET
	@Path("/getAllQuestions")
	@Produces(value = MediaType.APPLICATION_JSON)
	public Response getAllQuestions() {
		List<Question> questions = questionDS.getAllQuestions();
		List<QuestionDTO> questionDTOs = new ArrayList<QuestionDTO>();
		for (Question q : questions) {
			QuestionDTO qDTO = new QuestionDTO(q);
			questionDTOs.add(qDTO);
		}
		
		try {
			return Response.ok(questionDTOs).build();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return Response.serverError().build();
	}

//	@DELETE
//	@Path("/{id}/delete")
//	@Produces(value = MediaType.APPLICATION_JSON)
//	public Response deleteQuestion(@PathParam("id") long id) {
//		Question question = questionDAO.getById(id);
//		try {
//			questionDS.deleteQuestion(question);
//			return Response.ok("Question with id " + id + " has been deleted.").build();
//		} catch (NullPointerException e) {
//			LOGGER.error(e.getMessage());
//			return Response.status(Status.NOT_FOUND).entity(e.getMessage()).build();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		
//		return Response.serverError().build();
//	}
}
