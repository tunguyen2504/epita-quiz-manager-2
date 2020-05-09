/* author: Anh Tu NGUYEN */

import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { HttpClient } from '@angular/common/http';
import { Question } from 'src/app/datamodel/Question';

const QUESTION_API = environment.questionApi;

@Injectable({
  providedIn: 'root'
})
export class QuestionService {

  public CREATE_QUESTION_URL = QUESTION_API.replace('{apiPath}', 'create');
  public UPDATE_QUESTION_URL = QUESTION_API.replace('{apiPath}', '{id}/update');
  public GET_ALL_QUESTION_URL = QUESTION_API.replace('{apiPath}', 'getAllQuestions');
  public apiCall: string;

  constructor(private httpClient: HttpClient) { }

  createQuestion(question: Question): any {
    return this.httpClient.post(this.CREATE_QUESTION_URL, question);
  }

  updateQuestion(question: Question): any {
    this.apiCall = this.UPDATE_QUESTION_URL.replace('{id}', question.id.toString());
    return this.httpClient.put(this.apiCall, question);
  }

  getAllQuestions(): any {
    return this.httpClient.get(this.GET_ALL_QUESTION_URL);
  }
}
