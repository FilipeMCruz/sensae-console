import {DataTransformation} from "./DataTransformation";

export class DataTransformationPair {
  constructor(public fresh: DataTransformation, public old: DataTransformation) {
  }
}
