package fr.epita.quizmanager.services.business;

import java.util.List;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import fr.epita.quizmanager.datamodel.MCQChoice;
import fr.epita.quizmanager.datamodel.Question;
import fr.epita.quizmanager.services.dao.MCQChoiceDAO;
import fr.epita.quizmanager.services.dao.QuestionDAO;

public class QuestionDataService {

	@Inject
	QuestionDAO questionDAO;
	
	@Inject
	MCQChoiceDAO mcqChoiceDAO;
	
	@Transactional(value = TxType.REQUIRED)
	public void saveQuestion(Question question, List<MCQChoice> choices) {
		questionDAO.create(question);
		question.setChoices(choices);
		questionDAO.update(question);
		for (MCQChoice choice : choices) {
			choice.setQuestion(question);
			mcqChoiceDAO.create(choice);
		}
	}
}
