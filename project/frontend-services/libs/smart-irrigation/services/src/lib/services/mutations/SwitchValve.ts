import {Apollo, gql} from 'apollo-angular';
import {EMPTY, Observable} from 'rxjs';
import {Injectable} from '@angular/core';
import {filter, map} from 'rxjs/operators';
import {extract, isNonNull} from '@frontend-services/core';
import {HttpHeaders} from '@angular/common/http';
import {AuthService} from '@frontend-services/simple-auth-lib';
import {SwitchValveResultDTO} from "@frontend-services/smart-irrigation/dto";
import {OperationsMapper} from "@frontend-services/smart-irrigation/mapper";
import {Device} from "@frontend-services/smart-irrigation/model";

@Injectable({
  providedIn: 'root',
})
export class SwitchValve {
  constructor(private apollo: Apollo, private auth: AuthService) {
  }

  execute(device: Device): Observable<Device> {
    if (!this.canDo())
      return EMPTY;

    const query = gql`
      mutation switchValve($instructions: ValvesToSwitch){
        switchValve(instructions: $instructions)
      }
    `;

    return this.apollo
      .use('smartIrrigation')
      .subscribe<SwitchValveResultDTO>({
        query,
        context: {
          headers: new HttpHeaders().set(
            'Authorization',
            'Bearer ' + this.auth.getToken()
          ),
        },
        variables: OperationsMapper.switchValve(device.id.value)
      })
      .pipe(
        map(extract),
        filter(isNonNull),
        map((data: SwitchValveResultDTO) => {
          if (data.switchValve) {
            device.switchQueued = true;
          }
          return device;
        })
      );
  }

  canDo() {
    return this.auth.isAuthenticated() && this.auth.isAllowed(["smart_irrigation:valve:control"]);
  }
}
