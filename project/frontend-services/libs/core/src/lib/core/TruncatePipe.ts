import {Pipe, PipeTransform} from '@angular/core';

@Pipe({
  name: 'truncate'
})
export class TruncatePipe implements PipeTransform {
  transform(value: string | null, limit = 25, completeWords = false, ellipsis = '...') {
    if (value == null) {
      return ""
    }
    if (completeWords) {
      limit = value.substring(0, limit).lastIndexOf(' ');
    }
    const spacesToFill = Array(limit - value.length);
    return value.length > limit ? value.substring(0, limit) + ellipsis + " | " : value + spacesToFill.join(" ") + " | ";
  }
}
