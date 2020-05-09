/* author: Anh Tu NGUYEN */

import { Component, OnInit } from '@angular/core';
import { User } from 'src/app/datamodel/User';
import { UserService } from 'src/app/services/user/user.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  public user: User;
  public loginName: string;
  public isTeacher: boolean;

  constructor(private userService: UserService, private router: Router) { }

  ngOnInit(): void {
    sessionStorage.clear();
  }

  login(loginName: string) {
    this.userService.getUserByLoginName(loginName).subscribe(res => {
      if (res) {
        let resUser = new User(res);
        if (resUser.id == null) {
          this.user = new User();
          this.user.loginName = loginName;
          this.user.isTeacher = this.isTeacher ? this.isTeacher : false;
          this.userService.createUser(this.user).subscribe(resp => {
            if (resp) {
              let respUser = new User(resp)
              sessionStorage.setItem('userId', respUser.id.toString());
              sessionStorage.setItem('loginName', respUser.loginName);
              sessionStorage.setItem('isTeacher', respUser.isTeacher ? respUser.isTeacher.toString() : false.toString());
            }
          })
        } else {
          sessionStorage.setItem('userId', resUser.id.toString());
          sessionStorage.setItem('loginName', resUser.loginName);
          sessionStorage.setItem('isTeacher', resUser.isTeacher ? resUser.isTeacher.toString() : false.toString());
        }
        this.router.navigate(['landing']);
      }
    })
  }

}
