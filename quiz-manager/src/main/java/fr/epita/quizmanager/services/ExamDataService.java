package fr.epita.quizmanager.services;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import fr.epita.quizmanager.datamodel.Answer;
import fr.epita.quizmanager.datamodel.Question;

public class ExamDataService {

	@Inject
	QuestionDAO questionDAO;

	@Inject
	AnswerDAO answerDAO;

	public void saveAnswer(Question question, Answer answer) throws Exception {
		if (question.getId() != null) {
			if (questionDAO.getById(question.getId()) != null) {
				if (answer.getId() == null) {
					answerDAO.create(answer);
				}
				List<Answer> answers = new ArrayList<Answer>();
				if (question.getAnswers() != null) {
					answers = question.getAnswers();
					answers.add(answer);
				} else {
					answers.add(answer);
				}
				question.setAnswers(answers);
				questionDAO.update(question);
			} else {
				throw new NullPointerException("Question is not found.");
			}
		} else {
			throw new NullPointerException("Question is not found.");
		}
	}

}
