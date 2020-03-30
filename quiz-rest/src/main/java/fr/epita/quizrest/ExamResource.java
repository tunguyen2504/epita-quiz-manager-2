package fr.epita.quizrest;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

import org.springframework.beans.factory.annotation.Autowired;

import fr.epita.quizmanager.services.business.ExamDataService;

@Path("/exam")
public class ExamResource {

	@Autowired
	ExamDataService examDS;
	
	@POST
	@Path("/answer")
	public void addAnswerToQuestion() {
		System.out.println("testPost");
	}
	
	@GET
	@Path("/answer")
	public String getAnswers() {
		System.out.println("testGET");
		return "testGET";
	}
	
}
