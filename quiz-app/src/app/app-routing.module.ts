/* author: Anh Tu NGUYEN */

import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { LandingPageComponent } from './components/landing-page/landing-page.component';
import { ExamManagementComponent } from './components/exam-management/exam-management.component';
import { ExamEditComponent } from './components/exam-edit/exam-edit.component';
import { QuestionManagementComponent } from './components/question-management/question-management.component';
import { LoginComponent } from './components/login/login.component';
import { TakeExamComponent } from './components/take-exam/take-exam.component';


const routes: Routes = [
  { path: '', redirectTo: 'index', pathMatch: 'full' },
  { path: 'index', component: LoginComponent },
  { path: 'landing', component: LandingPageComponent },
  { path: 'exam-management', component: ExamManagementComponent },
  { path: 'edit-exam', component: ExamEditComponent },
  { path: 'question-management', component: QuestionManagementComponent },
  { path: 'take-exam', component: TakeExamComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
