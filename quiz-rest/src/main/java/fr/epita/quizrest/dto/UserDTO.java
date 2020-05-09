package fr.epita.quizrest.dto;

import java.util.ArrayList;
import java.util.List;

import fr.epita.quizmanager.datamodel.MCQAnswer;
import fr.epita.quizmanager.datamodel.User;

/**
 * @author Anh Tu
 *
 */
public class UserDTO {

	private Long id;
	private String loginName;
	private List<MCQAnswerDTO> answers;
	private Boolean isTeacher;

	public UserDTO() {
		
	}
	
	public UserDTO(User user) {
		this.id = user.getId();
		this.loginName = user.getLoginName();
		this.isTeacher = user.getIsTeacher();
	}
	
	public UserDTO(User user, List<MCQAnswer> answers) {
		this.loginName = user.getLoginName();
		this.isTeacher = user.getIsTeacher();
		if (!answers.isEmpty()) {
			List<MCQAnswerDTO> answerDTOs = new ArrayList<MCQAnswerDTO>();
			for (MCQAnswer a : answers) {
				MCQAnswerDTO aDTO = new MCQAnswerDTO(a);
				answerDTOs.add(aDTO);
			}
			this.answers = answerDTOs;
		}
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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
