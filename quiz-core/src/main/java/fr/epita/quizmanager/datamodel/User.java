package fr.epita.quizmanager.datamodel;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "USERS")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "U_ID")
	private Long id;
	
	@Column(name = "U_LOGIN", unique = true)
	private String loginName;
	
	@JsonManagedReference
	@OneToMany(mappedBy = "user") // ALL: Strong aggregation, no cascade: weak aggregation
	private List<MCQAnswer> answers;
	
	@Column(name = "IS_TEACHER")
	private Boolean isTeacher;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public List<MCQAnswer> getAnswers() {
		return answers;
	}

	public void setAnswers(List<MCQAnswer> answers) {
		this.answers = answers;
	}

	public Boolean getIsTeacher() {
		return isTeacher;
	}

	public void setIsTeacher(Boolean isTeacher) {
		this.isTeacher = isTeacher;
	}
}
