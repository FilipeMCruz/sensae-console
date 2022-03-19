import {DataDecoder} from "./DataDecoder";

export class DataDecoderPair {
  constructor(
    public fresh: DataDecoder,
    public old: DataDecoder
  ) {
  }
}
