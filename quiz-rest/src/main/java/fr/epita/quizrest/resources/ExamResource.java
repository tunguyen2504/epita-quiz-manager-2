package fr.epita.quizrest.resources;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
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

import fr.epita.quizmanager.datamodel.Exam;
import fr.epita.quizmanager.datamodel.Question;
import fr.epita.quizmanager.services.business.ExamDataService;
import fr.epita.quizmanager.services.dao.ExamDAO;
import fr.epita.quizmanager.services.dao.MCQAnswerDAO;
import fr.epita.quizmanager.services.dao.QuestionDAO;
import fr.epita.quizrest.dto.ExamDTO;
import fr.epita.quizrest.dto.GenericListDTO;
import fr.epita.quizrest.dto.QuestionDTO;

@Path("/exam")
public class ExamResource {

	private static final Logger LOGGER = LogManager.getLogger(ExamResource.class);

	@Inject
	ExamDataService examDS;

	@Inject
	MCQAnswerDAO answerDAO;

	@Inject
	QuestionDAO questionDAO;

	@Inject
	ExamDAO examDAO;

	@POST
	@Path("/create")
	@Consumes(value = MediaType.APPLICATION_JSON)
	@Produces(value = MediaType.APPLICATION_JSON)
	public Response createExam(@RequestBody ExamDTO examDTO) {
		Exam exam = new Exam();
		exam.setTitle(examDTO.getTitle());
		
		try {
			examDAO.create(exam);
			ExamDTO resExamDTO = new ExamDTO(exam);
			return Response.created(new URI("/rest/exam/create" + resExamDTO.getId())).entity(resExamDTO).build();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Response.serverError().build();
	}

	@PUT
	@Path("/{id}/addQuestion")
	@Consumes(value = MediaType.APPLICATION_JSON)
	@Produces(value = MediaType.APPLICATION_JSON)
	public Response addQuestionToExam(@PathParam("id") long id, @RequestBody GenericListDTO<QuestionDTO> list) {
		Exam exam = examDAO.getById(id);

		List<Question> questions = new ArrayList<Question>();
		List<QuestionDTO> questionDTOs = list.getList();
		List<Question> allQuestions = new ArrayList<Question>();
		
		for (QuestionDTO questionDTO : questionDTOs) {
			Question question = questionDAO.getById(questionDTO.getId());
			questions.add(question);
		}

		try {
			examDS.addQuestionListToExam(exam, questions);
			allQuestions = examDS.getAllQuestions(exam);
			ExamDTO examDTO = new ExamDTO(exam, allQuestions);
			return Response.ok(examDTO).build();
		} catch (NullPointerException e) {
			LOGGER.error(e.getMessage());
			return Response.status(Status.NOT_FOUND).entity(e.getMessage()).build();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return Response.serverError().build();
	}

	@PUT
	@Path("/{id}/removeQuestion")
	@Consumes(value = MediaType.APPLICATION_JSON)
	@Produces(value = MediaType.APPLICATION_JSON)
	public Response removeQuestionFromExam(@PathParam("id") long id, @RequestBody GenericListDTO<QuestionDTO> list) {
		Exam exam = examDAO.getById(id);

		List<Question> questions = new ArrayList<Question>();
		List<QuestionDTO> questionDTOs = list.getList();
		List<Question> allQuestions = new ArrayList<Question>();
		
		for (QuestionDTO questionDTO : questionDTOs) {
			Question question = questionDAO.getById(questionDTO.getId());
			questions.add(question);
		}
		try {
			examDS.removeQuestionsFromExam(exam, questions);
			allQuestions = examDS.getAllQuestions(exam);
			ExamDTO examDTO = new ExamDTO(exam, allQuestions);
			return Response.ok(examDTO).build();
		} catch (NullPointerException e) {
			LOGGER.error(e.getMessage());
			return Response.status(Status.NOT_FOUND).entity(e.getMessage()).build();
		} catch (Exception e) {
			e.printStackTrace();
		}
	
		return Response.serverError().build();
	}

	@GET
	@Path("/{id}/getAllQuestions")
	@Produces(value = MediaType.APPLICATION_JSON)
	public Response getAllQuestionsByExamId(@PathParam("id") long id) {
		Exam exam = examDAO.getById(id);
		List<Question> allQuestions = new ArrayList<Question>();

		try {
			allQuestions = examDS.getAllQuestions(exam);
			ExamDTO examDTO = new ExamDTO(exam, allQuestions);
			return Response.ok(examDTO).build();
		} catch (NullPointerException e) {
			LOGGER.error(e.getMessage());
			return Response.status(Status.NOT_FOUND).entity(e.getMessage()).build();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return Response.serverError().build();
	}
	
}
