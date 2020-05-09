/* author: Anh Tu NGUYEN */

export class MCQChoice {

  public id: number;
  public content: string;
  public isValid: boolean;

  constructor(values: Object = {}) {
    Object.assign(this, values);
  }

}
