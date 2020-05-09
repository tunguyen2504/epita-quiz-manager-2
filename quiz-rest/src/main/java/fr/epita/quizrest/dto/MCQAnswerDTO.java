package fr.epita.quizrest.dto;

import fr.epita.quizmanager.datamodel.MCQAnswer;

/**
 * @author Anh Tu
 *
 */
public class MCQAnswerDTO {

	private String id;
	private String content;
	private QuestionDTO question;
	private UserDTO user;
	private ExamDTO exam;
	
	public MCQAnswerDTO() {
		
	}
	
	public MCQAnswerDTO(MCQAnswer answer) {
		this.content = answer.getContent();
		this.question = new QuestionDTO(answer.getQuestion());
		this.user = new UserDTO(answer.getUser());
		this.exam = new ExamDTO(answer.getExam());
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public QuestionDTO getQuestion() {
		return question;
	}

	public void setQuestion(QuestionDTO question) {
		this.question = question;
	}

	public UserDTO getUser() {
		return user;
	}

	public void setUser(UserDTO user) {
		this.user = user;
	}

	public ExamDTO getExam() {
		return exam;
	}

	public void setExam(ExamDTO exam) {
		this.exam = exam;
	}

}
