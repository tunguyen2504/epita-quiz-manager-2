package fr.epita.quizrest.resources;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
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

import org.springframework.web.bind.annotation.RequestBody;

import fr.epita.quizmanager.datamodel.Exam;
import fr.epita.quizmanager.datamodel.MCQAnswer;
import fr.epita.quizmanager.datamodel.MCQChoice;
import fr.epita.quizmanager.datamodel.Question;
import fr.epita.quizmanager.services.business.ExamDataService;
import fr.epita.quizmanager.services.dao.ExamDAO;
import fr.epita.quizmanager.services.dao.MCQAnswerDAO;
import fr.epita.quizmanager.services.dao.QuestionDAO;
import fr.epita.quizrest.dto.AnswerDTO;
import fr.epita.quizrest.dto.ExamDTO;
import fr.epita.quizrest.dto.GenericListDTO;
import fr.epita.quizrest.dto.MCQChoiceDTO;
import fr.epita.quizrest.dto.QuestionDTO;

@Path("/exam")
public class ExamResource {

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
		examDAO.create(exam);

		try {
			return Response.ok(exam).build();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Response.serverError().build();
	}

	@PUT
	@Path("/{id}/addQuestion")
	@Consumes(value = MediaType.APPLICATION_JSON)
	@Produces(value = MediaType.APPLICATION_JSON)
	public Response addQuestionToExam(@PathParam("id") long examId, @RequestBody GenericListDTO<QuestionDTO> list) {
		Exam exam = examDAO.getById(examId);

		List<Question> questions = new ArrayList<Question>();
		List<QuestionDTO> questionDTOs = list.getList();
		
		for (QuestionDTO questionDTO : questionDTOs) {
			Question question = questionDAO.getById(questionDTO.getId());
			questions.add(question);
		}
		examDS.addQuestionListToExam(exam, questions);
		try {
			return Response.ok(exam).build();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return Response.serverError().build();
	}

	@POST
	@Path("/answer")
	public Response addAnswerToQuestion(@RequestBody AnswerDTO answerDTO) {
		// dummy code
		MCQAnswer answer = new MCQAnswer();
		answer.setContent(answerDTO.getContent());
		answer.setId(1l);
		// TODO call examDS on this answer
		try {
			return Response.created(new URI("/exam/answer/" + answer.getId())).build();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		return Response.serverError().build();
	}

	@GET
	@Path("/answer/{id}")
	@Produces(value = MediaType.APPLICATION_JSON)
	public Response getAnswerById(@PathParam("id") long answerId) {

		// beginning dummy implementation
		MCQAnswer answer = new MCQAnswer();
		answer.setContent("This is a sampleAnswer with id = " + answerId);
		answer.setId(answerId);

		return Response.ok(answer).build();
	}

	@GET
	@Path("/answer")
	@Produces(value = MediaType.APPLICATION_JSON)
	public Response getAnswers() {

		// beginning dummy implementation
		MCQAnswer answer = new MCQAnswer();
		answer.setContent("This is a sampleAnswer");

		AnswerDTO answerDTO = new AnswerDTO(answer);

		return Response.ok(Arrays.asList(answerDTO)).build();
	}

	@DELETE
	@Path("/answer")
	@Produces(value = MediaType.APPLICATION_JSON)
	public Response deleteAnswer(@RequestBody AnswerDTO answerDTO) {

		// beginning dummy implementation
		MCQAnswer answer = new MCQAnswer();
		answer.setContent(answerDTO.getContent());
		answer.setId(1L); // should get id from DTO
		answerDAO.delete(answer);

		return Response.ok(Arrays.asList(answerDTO)).build();
	}

}
