import { MCQChoice } from './MCQChoice';

export class Question {

  public id: number;
  public title: string;
  public choices: Array<MCQChoice>;

  constructor(values: Object = {}) {
    Object.assign(this, values);
  }

}
