package fr.epita.quizmanager.datamodel;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonManagedReference;

/**
 * @author Anh Tu
 *
 */

@Entity
@Table(name = "EXAMS")
public class Exam {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "E_ID")
	private Long id;

	@Column(name = "E_TITLE")
	private String title;

	@JsonManagedReference
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "EXAM_QUESTION", joinColumns = @JoinColumn(name = "E_ID", referencedColumnName = "E_ID"), inverseJoinColumns = @JoinColumn(name = "Q_ID", referencedColumnName = "Q_ID"), uniqueConstraints = @UniqueConstraint(columnNames = {
			"E_ID", "Q_ID" }))
	private List<Question> questions;

	@JsonManagedReference
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "exam")
	private List<MCQAnswer> mcqAnswers;

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

	public List<Question> getQuestions() {
		return questions;
	}

	public void setQuestions(List<Question> questions) {
		this.questions = questions;
	}

	public List<MCQAnswer> getMcqAnswers() {
		return mcqAnswers;
	}

	public void setMcqAnswers(List<MCQAnswer> mcqAnswers) {
		this.mcqAnswers = mcqAnswers;
	}

}
