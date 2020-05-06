package fr.epita.quizrest.dto;

public class MCQAnswerDTO {

	private String content;
	private QuestionDTO question;
	private UserDTO user;
	private ExamDTO exam;

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
