/* author: Anh Tu NGUYEN */

import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms'
import { HttpClientModule } from '@angular/common/http'

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { ExamService } from './services/exam/exam.service';
import { QuestionService } from './services/question/question.service';
import { LandingPageComponent } from './components/landing-page/landing-page.component';
import { ExamManagementComponent } from './components/exam-management/exam-management.component';
import { ExamEditComponent } from './components/exam-edit/exam-edit.component';
import { QuestionManagementComponent } from './components/question-management/question-management.component';
import { LoginComponent } from './components/login/login.component';
import { UserService } from './services/user/user.service';
import { TakeExamComponent } from './components/take-exam/take-exam.component';

@NgModule({
  declarations: [
    AppComponent,
    LandingPageComponent,
    ExamManagementComponent,
    ExamEditComponent,
    QuestionManagementComponent,
    LoginComponent,
    TakeExamComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule
  ],
  providers: [ExamService, QuestionService, UserService],
  bootstrap: [AppComponent]
})
export class AppModule { }
