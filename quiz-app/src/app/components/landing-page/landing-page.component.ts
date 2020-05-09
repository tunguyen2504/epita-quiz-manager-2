/* author: Anh Tu NGUYEN */

import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-landing-page',
  templateUrl: './landing-page.component.html',
  styleUrls: ['./landing-page.component.css']
})
export class LandingPageComponent implements OnInit {

  public loginName: string;
  public isTeacher: string;

  constructor() { }

  ngOnInit() {
    this.loginName = sessionStorage.getItem('loginName');
    this.isTeacher = sessionStorage.getItem('isTeacher');
  }

}
