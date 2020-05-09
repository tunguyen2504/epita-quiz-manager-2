/* author: Anh Tu NGUYEN */

import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { HttpClient } from '@angular/common/http';
import { Exam } from '../../datamodel/Exam';
import { Question } from 'src/app/datamodel/Question';
import { MCQAnswer } from 'src/app/datamodel/MCQAnswer';
import { User } from 'src/app/datamodel/User';

const EXAM_API: string = environment.examApi;

@Injectable({
  providedIn: 'root'
})
export class ExamService {

  public CREATE_EXAM_URL = EXAM_API.replace('{apiPath}', 'create');
  public ADD_QUESTION_URL = EXAM_API.replace('{apiPath}', '{id}/addQuestion');
  public REMOVE_QUESTION_URL = EXAM_API.replace('{apiPath}', '{id}/removeQuestion');
  public UPDATE_EXAM_URL = EXAM_API.replace('{apiPath}', '{id}/update')
  public GET_ALL_QUESTION_URL = EXAM_API.replace('{apiPath}', '{id}/getAllQuestions');
  public GET_ALL_EXAM_URL = EXAM_API.replace('{apiPath}', 'getAllExams');
  public GET_NOT_INCLUDED_QUESTIONS_URL = EXAM_API.replace('{apiPath}', '{id}/getNotIncludedQuestions');
  public ANSWER_TO_QUESTION_URL = EXAM_API.replace('{apiPath}', 'answerQuestion');
  public GET_GRADE_URL = EXAM_API.replace('{apiPath}', '{examId}/getGrade/{userId}')
  apiCall: string;

  constructor(private httpClient: HttpClient) { }

  createExam(exam: Exam): any {
    return this.httpClient.post(this.CREATE_EXAM_URL, exam);
  }

  addQuestionsToExam(examId: number, questionList: Array<Question>): any {
    this.apiCall = this.ADD_QUESTION_URL.replace('{id}', examId.toString());
    let body = {'list': questionList};
    return this.httpClient.put(this.apiCall, body);
  }

  removeQuestionsFromExam(examId: number, questionList: Array<Question>): any {
    this.apiCall = this.REMOVE_QUESTION_URL.replace('{id}', examId.toString());
    let body = {'list': questionList};
    return this.httpClient.put(this.apiCall, body);
  }

  updateExam(exam: Exam): any {
    this.apiCall = this.UPDATE_EXAM_URL.replace('{id}', exam.id.toString());
    return this.httpClient.put(this.apiCall, exam);
  }

  getAllQuestionsByExamId(examId: number): any {
    this.apiCall = this.GET_ALL_QUESTION_URL.replace('{id}', examId.toString());
    return this.httpClient.get(this.apiCall);
  }

  getAllExams(): any {
    return this.httpClient.get(this.GET_ALL_EXAM_URL);
  }

  getNotIncludedQuestionsByExamId(examId: number): any {
    this.apiCall = this.GET_NOT_INCLUDED_QUESTIONS_URL.replace('{id}', examId.toString());
    return this.httpClient.get(this.apiCall);
  }

  answerToQuestion(answer: MCQAnswer) {
    return this.httpClient.post(this.ANSWER_TO_QUESTION_URL, answer);
  }

  getExamGrade(exam: Exam, user: User) {
    this.apiCall = this.GET_GRADE_URL.replace('{examId}', exam.id.toString());
    this.apiCall = this.apiCall.replace('{userId}', user.id.toString());
    return this.httpClient.get(this.apiCall);
  }
}
