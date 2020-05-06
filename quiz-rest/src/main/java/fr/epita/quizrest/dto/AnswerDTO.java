package fr.epita.quizrest.dto;

import fr.epita.quizmanager.datamodel.MCQAnswer;

public class AnswerDTO {

	private String content;

	public AnswerDTO() {
		super();
	}

	public AnswerDTO(MCQAnswer entity) {
		this.content = entity.getContent();
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
}
