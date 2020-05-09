package fr.epita.quizrest.dto;

/**
 * @author Anh Tu
 *
 */
public class GradeDTO {
	
	private String grade;
	
	public GradeDTO() {
		
	}
	
	public GradeDTO(String grade) {
		this.grade = grade;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}
	
}
