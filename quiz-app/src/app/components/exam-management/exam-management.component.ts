import { Component, OnInit } from '@angular/core';
import { ExamService } from 'src/app/services/exam/exam.service';
import { Exam } from 'src/app/datamodel/Exam';
import { ActivatedRoute, Router } from '@angular/router';
import { Question } from 'src/app/datamodel/Question';
import { QuestionService } from 'src/app/services/question/question.service';

@Component({
  selector: 'app-exam-management',
  templateUrl: './exam-management.component.html',
  styleUrls: ['./exam-management.component.css']
})
export class ExamManagementComponent implements OnInit {

  public exam: Exam;
  public newExam: Exam = new Exam();
  public examList = new Array<Exam>();
  public questionsToAdd = new Array<Question>();
  public questionsChosen = new Array<Question>();
  public showExamDetails = false;
  public step = 0;
  public examCreated: boolean;

  constructor(private examService: ExamService, private questionService: QuestionService, private route: ActivatedRoute, private router: Router) { }

  ngOnInit(): void {
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

  getExamDetailsById(exam: Exam) {
    this.showExamDetails = true
    this.examService.getAllQuestionsByExamId(exam.id).subscribe(res => {
      if (res) {
        this.exam = new Exam(res);
      } else {
        this.exam = null;
      }
    })
  }

  editSelectedExam(exam: Exam) {
    this.router.navigate(['edit-exam'], {
      state: {
        examId: exam.id
      }
    });
  }

  goToCreateExam() {
    this.step++;
  }

  createExam(exam: Exam) {
    this.step++;

    this.getAllQuestion();
  }

  getAllQuestion() {
    this.questionService.getAllQuestions().subscribe(res => {
      if (res) {
        res.forEach(element => {
          this.questionsToAdd.push(new Question(element));
        })
      }
    })
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

  submitExam(exam: Exam) {
    if (exam !== null) {
      this.examService.createExam(exam).subscribe(res => {
        this.examService.addQuestionsToExam(res.id, this.questionsChosen).subscribe(resp => {
          if (resp) {
            this.newExam = new Exam(resp);
            this.examCreated = true;
          } else {
            this.newExam = null;
            this.examCreated = false;
          }
        });
      });
      this.step++;
    }
  }

  reload() {
    window.location.reload();
  }

  back() {
    this.showExamDetails = false;
    this.exam = null;
  }

  backStep() {
    this.step--;
  }

}
