package fr.epita.quizrest.dto;

import java.util.List;

public class ExamDTO {

	private String title;
	private List<QuestionDTO> questions;
	private List<MCQAnswerDTO> answers;

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

	public List<MCQAnswerDTO> getAnswers() {
		return answers;
	}

	public void setAnswers(List<MCQAnswerDTO> answers) {
		this.answers = answers;
	}

}
