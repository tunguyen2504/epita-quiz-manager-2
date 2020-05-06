package fr.epita.quizrest.dto;

import java.util.List;

public class QuestionDTO {

	private Long id; 
	private String title;
	private List<MCQChoiceDTO> choices;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<MCQChoiceDTO> getChoices() {
		return choices;
	}

	public void setChoices(List<MCQChoiceDTO> choices) {
		this.choices = choices;
	}

}
