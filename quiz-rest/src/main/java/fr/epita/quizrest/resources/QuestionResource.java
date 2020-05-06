package fr.epita.quizrest.resources;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.web.bind.annotation.RequestBody;

import fr.epita.quizmanager.datamodel.MCQChoice;
import fr.epita.quizmanager.datamodel.Question;
import fr.epita.quizmanager.services.business.QuestionDataService;
import fr.epita.quizrest.dto.MCQChoiceDTO;
import fr.epita.quizrest.dto.QuestionDTO;

@Path("/question")
public class QuestionResource {

	@Inject
	QuestionDataService questionDS;

	@POST
	@Path("/save")
	@Consumes(value = MediaType.APPLICATION_JSON)
	@Produces(value = MediaType.APPLICATION_JSON)
	public Response saveQuestion(@RequestBody QuestionDTO questionDTO) {
		Question question = new Question();
		question.setTitle(questionDTO.getTitle());
		List<MCQChoice> choices = new ArrayList<MCQChoice>();
		for (MCQChoiceDTO choiceDTO : questionDTO.getChoices()) {
			MCQChoice choice = new MCQChoice();
			choice.setContent(choiceDTO.getContent());
			choice.setIsValid(choiceDTO.getIsValid());
			choices.add(choice);
		}
		questionDS.saveQuestion(question, choices);
		try {
			return Response.ok(question).build();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Response.serverError().build();
	}

}
