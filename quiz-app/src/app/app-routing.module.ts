import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { LandingPageComponent } from './components/landing-page/landing-page.component';
import { ExamManagementComponent } from './components/exam-management/exam-management.component';
import { ExamEditComponent } from './components/exam-edit/exam-edit.component';


const routes: Routes = [
  { path: '', redirectTo: 'index', pathMatch: 'full' },
  { path: 'index', component: LandingPageComponent },
  { path: 'exam-management', component: ExamManagementComponent },
  { path: 'edit-exam', component: ExamEditComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
