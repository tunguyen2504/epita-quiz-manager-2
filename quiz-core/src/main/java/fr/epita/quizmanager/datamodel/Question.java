package fr.epita.quizmanager.datamodel;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

/**
 * @author Anh Tu
 *
 */
@Entity
@Table(name = "QUESTIONS")
public class Question {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "Q_ID")
	private Long id;
	
	@Column(name = "Q_TITLE")
	private String title;
	
	@JsonManagedReference
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "question") // ALL: Strong aggregation, no cascade: weak aggregation
	private List<MCQAnswer> answers;
	
	@JsonManagedReference
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "question")
	private List<MCQChoice> choices;
	
	@JsonBackReference
	@ManyToMany(fetch = FetchType.EAGER, mappedBy="questions")
	private List<Exam> exams;

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

	public List<MCQAnswer> getAnswers() {
		return answers;
	}

	public void setAnswers(List<MCQAnswer> answers) {
		this.answers = answers;
	}

	public List<MCQChoice> getChoices() {
		return choices;
	}

	public void setChoices(List<MCQChoice> choices) {
		this.choices = choices;
	}

	public List<Exam> getExams() {
		return exams;
	}

	public void setExams(List<Exam> exams) {
		this.exams = exams;
	}
	
}
