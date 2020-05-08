import { MCQChoice } from './MCQChoice';

export class Question {

  private id: number;
  private title: string;
  private choices: Array<MCQChoice>;

  constructor(values: Object = {}) {
    Object.assign(this, values);
  }

}
