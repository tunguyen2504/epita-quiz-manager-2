/* author: Anh Tu NGUYEN */

import { Question } from './Question';
import { User } from './User';
import { Exam } from './Exam';

export class MCQAnswer {

  public id: number;
  public content: string;
  public question: Question;
  public user: User;
  public exam: Exam;

  constructor(values: Object = {}) {
    Object.assign(this, values);
  }

}
