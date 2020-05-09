/* author: Anh Tu NGUYEN */

import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router, RoutesRecognized } from '@angular/router';
import { Exam } from 'src/app/datamodel/Exam';
import { ExamService } from 'src/app/services/exam/exam.service';
import { Location } from '@angular/common';
import { Question } from 'src/app/datamodel/Question';

@Component({
  selector: 'app-exam-edit',
  templateUrl: './exam-edit.component.html',
  styleUrls: ['./exam-edit.component.css']
})
export class ExamEditComponent implements OnInit {

  public examId: number;
  public exam: Exam;
  public questionList = new Array<Question>();
  public questionsToAdd = new Array<Question>();
  public questionsChosen = new Array<Question>();
  public notIncludedQuestionList = new Array<Question>();
  public showNotIncludedList = false;
  public loaded = false;

  constructor(private examService: ExamService, private router: Router, private location: Location) {
    this.examId = this.router.getCurrentNavigation().extras.state.examId;
  }

  ngOnInit(): void {
    this.getExamDetailsById(this.examId);
  }

  getExamDetailsById(examId: number) {
    this.examService.getAllQuestionsByExamId(examId).subscribe(res => {
      if (res) {
        this.exam = new Exam(res);
        this.questionList = this.exam.questions ? this.exam.questions : [];
      } else {
        this.exam = null;
      }
    })
  }

  showQuestions(examId: number) {
    if (this.loaded == false) {
      this.examService.getNotIncludedQuestionsByExamId(examId).subscribe(res => {
        if (res) {
          this.notIncludedQuestionList = this.notIncludedQuestionList.concat(res);
          this.loaded = true;
        }
      })
    }
    this.showNotIncludedList = true;
  }

  updateQuestionsToAddList(question: Question) {
    let index = this.questionsChosen.indexOf(question)
    if (index == -1) {
      this.questionsChosen.push(question);
    } else {
      this.questionsChosen.splice(index, 1);
    }
    console.log(this.questionsChosen);
  }

  addQuestions(questionList1: Array<Question>, questionList2: Array<Question>) {
    this.questionList = questionList1.concat(questionList2);
    this.notIncludedQuestionList = this.notIncludedQuestionList.filter(q => !questionList2.includes(q));
    console.log(this.questionList);
    this.showNotIncludedList = false;
  }

  removeQuestion(question: Question) {
    let index = this.questionList.indexOf(question)
    if (index > -1) {
      this.questionList.splice(index, 1);
      this.notIncludedQuestionList.push(question);
    }
  }

  saveExam() {
    this.exam.questions = this.questionList;
    this.examService.updateExam(this.exam).subscribe(res => {
      if (res) {
        this.exam = res;
        this.questionList = this.exam.questions;
        this.loaded = false;
        alert('Exam has been successfully updated!');
      }
    });
  }

  back() {
    this.location.back();
  }

}
