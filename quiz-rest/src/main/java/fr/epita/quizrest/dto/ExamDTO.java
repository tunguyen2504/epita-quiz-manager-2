package fr.epita.quizrest.dto;

import java.util.ArrayList;
import java.util.List;

import fr.epita.quizmanager.datamodel.Exam;
import fr.epita.quizmanager.datamodel.Question;

/**
 * @author Anh Tu
 *
 */
public class ExamDTO {

	private Long id;
	private String title;
	private List<QuestionDTO> questions;

	public ExamDTO() {

	}

	public ExamDTO(Exam exam) {
		this.id = exam.getId();
		this.title = exam.getTitle();
	}

	public ExamDTO(Exam exam, List<Question> questions) {
		this.id = exam.getId();
		this.title = exam.getTitle();
		if (!questions.isEmpty()) {
			List<QuestionDTO> questionDTOs = new ArrayList<QuestionDTO>();
			for (Question question : questions) {
				QuestionDTO questionDTO = new QuestionDTO(question);
				questionDTOs.add(questionDTO);
			}
			this.questions = questionDTOs;
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

	public List<QuestionDTO> getQuestions() {
		return questions;
	}

	public void setQuestions(List<QuestionDTO> questions) {
		this.questions = questions;
	}

}
