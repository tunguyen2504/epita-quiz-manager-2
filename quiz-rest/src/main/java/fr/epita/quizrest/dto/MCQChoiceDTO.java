package fr.epita.quizrest.dto;

import fr.epita.quizmanager.datamodel.MCQChoice;

/**
 * @author Anh Tu
 *
 */
public class MCQChoiceDTO {

	private Long id;
	private String content;
	private Boolean isValid;
	
	public MCQChoiceDTO() {
		
	}

	public MCQChoiceDTO(MCQChoice choice) {
		this.id = choice.getId();
		this.content = choice.getContent();
		this.isValid = choice.getIsValid();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Boolean getIsValid() {
		return isValid;
	}

	public void setIsValid(Boolean isValid) {
		this.isValid = isValid;
	}

}
