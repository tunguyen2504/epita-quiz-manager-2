/* author: Anh Tu NGUYEN */

import { Component, OnInit } from '@angular/core';
import { User } from 'src/app/datamodel/User';
import { Exam } from 'src/app/datamodel/Exam';
import { ExamService } from 'src/app/services/exam/exam.service';
import { MCQAnswer } from 'src/app/datamodel/MCQAnswer';
import { MCQChoice } from 'src/app/datamodel/MCQChoice';
import { Question } from 'src/app/datamodel/Question';
import { Grade } from 'src/app/datamodel/Grade';

@Component({
  selector: 'app-take-exam',
  templateUrl: './take-exam.component.html',
  styleUrls: ['./take-exam.component.css']
})
export class TakeExamComponent implements OnInit {

  public user: User;
  public exam: Exam;
  public examList = new Array<Exam>();
  public answerList = new Array<MCQAnswer>();
  public result: Grade;
  public isTakingExam = false;
  public hasFinished = false;

  constructor(private examService: ExamService) { }

  ngOnInit(): void {
    this.user = new User();
    this.user.id = Number(sessionStorage.getItem('userId'));
    this.user.loginName = sessionStorage.getItem('loginName');
    this.examList = new Array<Exam>();
    this.answerList = new Array<MCQAnswer>();
    this.getAllExams();
  }

  getAllExams() {
    this.examService.getAllExams().subscribe(res => {
      if (res) {
        res.forEach(element => {
          this.examList.push(new Exam(element));
        })
      } else {
        this.examList = null;
      }
    })
  }

  takeExam(exam: Exam) {
    this.isTakingExam = true;
    this.examService.getAllQuestionsByExamId(exam.id).subscribe(res => {
      if (res) {
        this.exam = new Exam(res);
      } else {
        this.exam = null;
      }
    })
  }

  back() {
    this.isTakingExam = false;
    this.hasFinished = false;
    this.ngOnInit();
  }

  addChoiceToAnswerList(exam: Exam, question: Question, choice: MCQChoice) {
    let a = new MCQAnswer();
    a.content = choice.id.toString();
    a.question = question;
    a.exam = exam;
    a.user = this.user;

    let index = this.answerList.indexOf(a)
    if (index == -1) {
      this.answerList.push(a);
    } else {
      this.answerList.splice(index, 1);
    }
  }

  submitAnswers(answerList: Array<MCQAnswer>, exam: Exam) {
    for (let answer of answerList) {
      this.examService.answerToQuestion(answer).subscribe(res => {
        console.log(res);
      });
    }
    this.isTakingExam = false;
    this.hasFinished = true;
  }

  getExamGrade(exam: Exam, user: User) {
    this.examService.getExamGrade(exam, user).subscribe(res => {
      if (res) {
        this.result = new Grade(res);
      }
    });
    this.ngOnInit();
  }
}
