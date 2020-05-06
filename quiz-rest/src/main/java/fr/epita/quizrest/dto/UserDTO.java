package fr.epita.quizrest.dto;

import java.util.List;

public class UserDTO {

	private String loginName;
	private List<MCQAnswerDTO> answers;
	private Boolean isTeacher;

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public List<MCQAnswerDTO> getAnswers() {
		return answers;
	}

	public void setAnswers(List<MCQAnswerDTO> answers) {
		this.answers = answers;
	}

	public Boolean getIsTeacher() {
		return isTeacher;
	}

	public void setIsTeacher(Boolean isTeacher) {
		this.isTeacher = isTeacher;
	}

}
