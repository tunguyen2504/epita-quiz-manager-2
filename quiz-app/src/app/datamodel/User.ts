/* author: Anh Tu NGUYEN */

export class User {

  public id: number;
  public loginName: string;
  public isTeacher: boolean;

  constructor(values: Object = {}) {
    Object.assign(this, values);
  }

}
