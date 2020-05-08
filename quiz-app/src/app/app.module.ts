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

@NgModule({
  declarations: [
    AppComponent,
    LandingPageComponent,
    ExamManagementComponent,
    ExamEditComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule
  ],
  providers: [ExamService, QuestionService],
  bootstrap: [AppComponent]
})
export class AppModule { }
