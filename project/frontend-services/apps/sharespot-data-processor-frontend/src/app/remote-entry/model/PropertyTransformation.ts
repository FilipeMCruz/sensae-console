import {PropertyName} from "./PropertyName";

export class PropertyTransformation {
  constructor(public oldPath: string, public newPath: PropertyName) {
  }

  static empty() {
    return new PropertyTransformation('', PropertyName.INVALID);
  }

  isValid() {
    return this.newPath !== PropertyName.INVALID && this.oldPath.trim().length !== 0;
  }
}
