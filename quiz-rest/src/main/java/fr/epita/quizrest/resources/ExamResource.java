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
import fr.epita.quizmanager.datamodel.MCQAnswer;
import fr.epita.quizmanager.datamodel.Question;
import fr.epita.quizmanager.datamodel.User;
import fr.epita.quizmanager.services.business.ExamDataService;
import fr.epita.quizmanager.services.business.QuestionDataService;
import fr.epita.quizmanager.services.dao.ExamDAO;
import fr.epita.quizmanager.services.dao.MCQAnswerDAO;
import fr.epita.quizmanager.services.dao.QuestionDAO;
import fr.epita.quizmanager.services.dao.UserDAO;
import fr.epita.quizrest.dto.ExamDTO;
import fr.epita.quizrest.dto.GenericListDTO;
import fr.epita.quizrest.dto.GradeDTO;
import fr.epita.quizrest.dto.MCQAnswerDTO;
import fr.epita.quizrest.dto.QuestionDTO;

/**
 * @author Anh Tu
 *
 */
@Path("/exam")
public class ExamResource {

	private static final Logger LOGGER = LogManager.getLogger(ExamResource.class);

	@Inject
	ExamDataService examDS;

	@Inject
	QuestionDataService questionDS;

	@Inject
	MCQAnswerDAO answerDAO;

	@Inject
	QuestionDAO questionDAO;

	@Inject
	ExamDAO examDAO;

	@Inject
	UserDAO userDAO;

	@POST
	@Path("/create")
	@Consumes(value = MediaType.APPLICATION_JSON)
	@Produces(value = MediaType.APPLICATION_JSON)
	public Response createExam(@RequestBody ExamDTO examDTO) {
		Exam exam = new Exam();
		if (examDTO.getTitle() == null || examDTO.getTitle().trim().isEmpty()) {
			try {
				return Response.status(Status.BAD_REQUEST).entity("Exam title cannot be empty.").build();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			exam.setTitle(examDTO.getTitle());
		}

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

	@PUT
	@Path("/{id}/update")
	@Consumes(value = MediaType.APPLICATION_JSON)
	@Produces(value = MediaType.APPLICATION_JSON)
	public Response updateExam(@PathParam("id") long id, @RequestBody ExamDTO examDTO) {
		Exam exam = examDAO.getById(id);
		if (examDTO.getTitle() == null || examDTO.getTitle().trim().isEmpty()) {
			try {
				return Response.status(Status.BAD_REQUEST).entity("Exam title cannot be empty.").build();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			exam.setTitle(examDTO.getTitle());
		}
		List<Question> questions = new ArrayList<Question>();
		List<QuestionDTO> questionDTOs = examDTO.getQuestions();
		for (QuestionDTO questionDTO : questionDTOs) {
			Question question = questionDAO.getById(questionDTO.getId());
			questions.add(question);
		}
		exam.setQuestions(questions);

		try {
			examDAO.update(exam);
			ExamDTO resExamDTO = new ExamDTO(exam, exam.getQuestions());
			return Response.ok(resExamDTO).build();
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

	@GET
	@Path("/getAllExams")
	@Produces(value = MediaType.APPLICATION_JSON)
	public Response getAllExams() {
		List<Exam> exams = examDS.getAllExams();
		List<ExamDTO> examDTOs = new ArrayList<ExamDTO>();
		for (Exam e : exams) {
			ExamDTO eDTO = new ExamDTO(e, e.getQuestions());
			examDTOs.add(eDTO);
		}

		try {
			return Response.ok(examDTOs).build();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return Response.serverError().build();
	}

	@GET
	@Path("/{id}/getNotIncludedQuestions")
	@Produces(value = MediaType.APPLICATION_JSON)
	public Response getNotIncludedQuestions(@PathParam("id") long id) {
		List<Question> allQuestions = questionDS.getAllQuestions();
		List<QuestionDTO> questionNotIncludedDTOs = new ArrayList<QuestionDTO>();

		for (Question q : allQuestions) {
			boolean isIncluded = false;
			for (Exam e : q.getExams()) {
				System.out.println(e.getId());
				if (e.getId() == id) {
					isIncluded = true;
				}
			}
			if (isIncluded) {
				continue;
			} else {
				QuestionDTO qDTO = new QuestionDTO(q);
				questionNotIncludedDTOs.add(qDTO);
			}
		}

		try {
			return Response.ok(questionNotIncludedDTOs).build();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return Response.serverError().build();
	}

	@POST
	@Path("/answerQuestion")
	@Consumes(value = MediaType.APPLICATION_JSON)
	@Produces(value = MediaType.APPLICATION_JSON)
	public Response answerToQuestion(@RequestBody MCQAnswerDTO mcqAnswerDTO) {
		MCQAnswer answer = new MCQAnswer();
		Exam exam = examDAO.getById(mcqAnswerDTO.getExam().getId());
		Question question = questionDAO.getById(mcqAnswerDTO.getQuestion().getId());
		User user = userDAO.getById(mcqAnswerDTO.getUser().getId());
		answer.setContent(mcqAnswerDTO.getContent());

		try {
			examDS.saveAnswerToQuestion(user, exam, question, answer);
			MCQAnswerDTO resAnswerDTO = new MCQAnswerDTO(answer);
			return Response.ok(resAnswerDTO).build();
		} catch (NullPointerException e) {
			return Response.status(Status.NOT_FOUND).entity(e.getMessage()).build();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return Response.serverError().build();
	}
	
	@GET
	@Path("/{examId}/getGrade/{userId}")
	@Produces(value = MediaType.APPLICATION_JSON)
	public Response answerToQuestion(@PathParam("examId") long examId, @PathParam("userId") long userId) {
		Exam exam = examDAO.getById(examId);
		User user = userDAO.getById(userId);
		try {
			String result = examDS.getExamGradeByUser(user, exam);
			GradeDTO gradeDTO = new GradeDTO(result);
			return Response.ok(gradeDTO).build();
		} catch (NullPointerException e) {
			return Response.status(Status.NOT_FOUND).entity(e.getMessage()).build();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return Response.serverError().build();
	}

}
