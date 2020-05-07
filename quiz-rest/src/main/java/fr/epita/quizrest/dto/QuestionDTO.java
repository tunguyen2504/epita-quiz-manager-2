package fr.epita.quizrest.dto;

import java.util.ArrayList;
import java.util.List;

import fr.epita.quizmanager.datamodel.MCQChoice;
import fr.epita.quizmanager.datamodel.Question;

public class QuestionDTO {

	private Long id;
	private String title;
	private List<MCQChoiceDTO> choices;

	public QuestionDTO() {

	}

	public QuestionDTO(Question question) {
		this.id = question.getId();
		this.title = question.getTitle();
		if (question.getChoices() != null && !question.getChoices().isEmpty()) {
			List<MCQChoiceDTO> choiceDTOs = new ArrayList<MCQChoiceDTO>();
			for (MCQChoice choice : question.getChoices()) {
				MCQChoiceDTO choiceDTO = new MCQChoiceDTO(choice);
				choiceDTOs.add(choiceDTO);
			}
			this.choices = choiceDTOs;
		}
	}

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
