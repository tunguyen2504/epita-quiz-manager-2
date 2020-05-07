package fr.epita.quizmanager.services.business;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import fr.epita.quizmanager.datamodel.Exam;
import fr.epita.quizmanager.datamodel.MCQChoice;
import fr.epita.quizmanager.datamodel.Question;
import fr.epita.quizmanager.services.dao.MCQChoiceDAO;
import fr.epita.quizmanager.services.dao.QuestionDAO;

public class QuestionDataService {

	@Inject
	QuestionDAO questionDAO;
	
	@Inject
	MCQChoiceDAO mcqChoiceDAO;
	
	@Inject
	ExamDataService examDS;
	
	@Transactional(value = TxType.REQUIRED)
	public void createQuestion(Question question, List<MCQChoice> choices) {
		questionDAO.create(question);
		question.setChoices(choices);
		questionDAO.update(question);
		for (MCQChoice choice : choices) {
			choice.setQuestion(question);
			mcqChoiceDAO.create(choice);
		}
	}
	
	@Transactional(value = TxType.REQUIRED)
	public void updateQuestion(Question question, List<MCQChoice> choices) {
		if (question == null || question.getId() == null || questionDAO.getById(question.getId()) == null) {
			throw new NullPointerException("Question is not found.");
		}
		question.setChoices(choices);
		questionDAO.update(question);
		for (MCQChoice choice : choices) {
			choice.setQuestion(question);
			mcqChoiceDAO.update(choice);
		}
	}
	
	public List<MCQChoice> getAllChoices(Question question) {
		if (question == null || question.getId() == null || questionDAO.getById(question.getId()) == null) {
			throw new NullPointerException("Question is not found.");
		}
		return question.getChoices();
	}
	
//	@Transactional(value = TxType.REQUIRED)
//	public void deleteQuestion(Question question) {
//		if (question == null || question.getId() == null || questionDAO.getById(question.getId()) == null) {
//			throw new NullPointerException("Question is not found.");
//		}
//		for (MCQChoice choice : question.getChoices()) {
//			mcqChoiceDAO.delete(choice);
//		}
//		for (Exam exam : question.getExams()) {
//			List<Question> questions = new ArrayList<Question>();
//			questions.add(question);
//			examDS.removeQuestionsFromExam(exam, questions);
//		}
//		questionDAO.delete(question);
//	}
}
