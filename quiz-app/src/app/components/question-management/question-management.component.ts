import { Component, OnInit } from '@angular/core';
import { QuestionService } from 'src/app/services/question/question.service';
import { ActivatedRoute, Router } from '@angular/router';
import { Question } from 'src/app/datamodel/Question';
import { MCQChoice } from 'src/app/datamodel/MCQChoice';

@Component({
  selector: 'app-question-management',
  templateUrl: './question-management.component.html',
  styleUrls: ['./question-management.component.css']
})
export class QuestionManagementComponent implements OnInit {

  public question: Question;
  public editingQuestion: Question;
  public newQuestion: Question = new Question();
  public newChoices = [
    { content: '', isValid: ''},
    { content: '', isValid: ''},
    { content: '', isValid: '' },
    { content: '', isValid: '' },
  ]
  public questionList = new Array<Question>();
  public createStep = false;
  public editStep = false

  constructor(private questionService: QuestionService, private route: ActivatedRoute, private router: Router) { }

  ngOnInit(): void {
    this.getAllQuestions();
  }

  getAllQuestions(): any {
    this.questionService.getAllQuestions().subscribe(res => {
      if (res) {
        res.forEach(element => {
          this.questionList.push(new Question(element));
        })
      } else {
        this.questionList = null;
      }
    })
  }

  goToCreateQuestion(): any {
    this.createStep = true;
  }

  createQuestion(question: Question): any {
    let choices = new Array<MCQChoice>();
    for (let i=0; i<this.newChoices.length; i++) {
      let c = new MCQChoice();
      c.content = this.newChoices[i].content;
      c.isValid = this.newChoices[i].isValid === 'true';
      choices.push(c);
    }
    question.choices = choices;
    this.questionService.createQuestion(question).subscribe(res => {
      if (res) {
        this.createStep = false;
        alert('Question has been successfully created.');
        this.getAllQuestions();
      }
    });
  }

  editQuestion(question: Question): any {
    this.editStep = true;
    this.editingQuestion = question;
  }

  updateQuestion(question: Question): any {
    this.questionService.updateQuestion(question).subscribe(res => {
      if (res) {
        this.editStep = false;
        alert('Question has been successfully updated.');
        this.getAllQuestions();
      }
    })
  }

  backStep(): any{
    this.createStep = false;
    this.editStep = false;
  }
}
