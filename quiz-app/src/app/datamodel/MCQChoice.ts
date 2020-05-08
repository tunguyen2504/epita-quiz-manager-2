export class MCQChoice {

  private id: number;
  private content: string;
  private isValid: boolean;

  constructor(values: Object = {}) {
    Object.assign(this, values);
  }

}
