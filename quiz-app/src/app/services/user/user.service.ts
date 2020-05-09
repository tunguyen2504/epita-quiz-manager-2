/* author: Anh Tu NGUYEN */

import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { HttpClient } from '@angular/common/http';
import { User } from 'src/app/datamodel/User';

const USER_API = environment.userApi;

@Injectable({
  providedIn: 'root'
})
export class UserService {

  public CREATE_USER_API = USER_API.replace('{apiPath}', 'create');
  public GET_USER_INFO_API = USER_API.replace('{apiPath}', '{loginName}/getUser');
  public apiCall: string;

  constructor(private httpClient: HttpClient) { }

  createUser(user: User) {
    return this.httpClient.post(this.CREATE_USER_API, user);
  }

  getUserByLoginName(loginName: string) {
    this.apiCall = this.GET_USER_INFO_API.replace('{loginName}', loginName);
    return this.httpClient.get(this.apiCall);
  }

}
