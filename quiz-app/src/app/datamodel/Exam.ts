/* author: Anh Tu NGUYEN */

import { Question } from './Question';

export class Exam {

  public id: number;
  public title: string;
  public questions: Array<Question>;

  constructor(values: Object = {}) {
    Object.assign(this, values);
  }

}
