package fr.epita.quizmanager.datamodel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "MCQ_ANSWERS")
public class MCQAnswer {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "A_ID")
	private Long id;

	@Column(name = "A_CONTENT")
	private String content;

	@JsonBackReference
	@ManyToOne
	@JoinColumn(name = "A_QUESTION_FK")
	private Question question;

	@JsonBackReference
	@ManyToOne
	@JoinColumn(name = "A_USER_FK")
	private User user;

	@JsonBackReference
	@ManyToOne
	@JoinColumn(name = "A_EXAM_FK")
	private Exam exam;

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

	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Exam getExam() {
		return exam;
	}

	public void setExam(Exam exam) {
		this.exam = exam;
	}

}
