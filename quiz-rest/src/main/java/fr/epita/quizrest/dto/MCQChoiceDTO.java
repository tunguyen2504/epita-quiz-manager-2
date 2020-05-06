package fr.epita.quizrest.dto;

public class MCQChoiceDTO {

	private String content;
	private Boolean isValid;
	
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
