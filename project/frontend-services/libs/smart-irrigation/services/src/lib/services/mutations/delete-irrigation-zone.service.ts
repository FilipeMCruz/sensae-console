import {Apollo, gql} from 'apollo-angular';
import {EMPTY, Observable} from 'rxjs';
import {Injectable} from '@angular/core';
import {filter, map} from 'rxjs/operators';
import {extract, isNonNull} from '@frontend-services/core';
import {HttpHeaders} from '@angular/common/http';
import {AuthService} from '@frontend-services/simple-auth-lib';
import {
  DeleteIrrigationZoneCommand,
  IrrigationZone,
} from "@frontend-services/smart-irrigation/model";
import {DeleteIrrigationZoneResultDTO} from "@frontend-services/smart-irrigation/dto";
import {OperationsMapper} from "@frontend-services/smart-irrigation/mapper";

@Injectable({
  providedIn: 'root',
})
export class DeleteIrrigationZone {
  constructor(private apollo: Apollo, private auth: AuthService) {
  }

  canDo() {
    return this.auth.isAuthenticated() && this.auth.isAllowed(["smart_irrigation:garden:delete"]);
  }

  getData(command: DeleteIrrigationZoneCommand): Observable<IrrigationZone> {
    if (!this.canDo())
      return EMPTY;

    const query = gql`
      mutation deleteIrrigationZone($instructions: DeleteIrrigationZoneCommand){
        deleteIrrigationZone(instructions: $instructions){
          id
          name
          area{
            position
            longitude
            latitude
            altitude
          }
        }
      }
    `;

    return this.apollo
      .use('smartIrrigation')
      .subscribe<DeleteIrrigationZoneResultDTO>({
        query,
        context: {
          headers: new HttpHeaders().set(
            'Authorization',
            'Bearer ' + this.auth.getToken()
          ),
        },
        variables: OperationsMapper.deleteIrrigationZoneInstructions(command)
      })
      .pipe(
        map(extract),
        filter(isNonNull),
        map((data: DeleteIrrigationZoneResultDTO) => OperationsMapper.deleteIrrigationZoneDtoToModel(data))
      );
  }
}
