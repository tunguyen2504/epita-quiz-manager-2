import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router, RoutesRecognized } from '@angular/router';
import { Exam } from 'src/app/datamodel/Exam';
import { ExamService } from 'src/app/services/exam/exam.service';
import { Location } from '@angular/common';

@Component({
  selector: 'app-exam-edit',
  templateUrl: './exam-edit.component.html',
  styleUrls: ['./exam-edit.component.css']
})
export class ExamEditComponent implements OnInit {

  public examId: number;
  public exam: Exam;

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
      } else {
        this.exam = null;
      }
    })
  }

  saveExam() {
    // this.exam
  }

  back() {
    this.location.back();
  }

}
