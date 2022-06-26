import {Apollo, gql} from "apollo-angular";
import {AuthService} from "@frontend-services/simple-auth-lib";
import {Injectable} from "@angular/core";
import {EMPTY} from "rxjs";
import {CommandDevice} from "@frontend-services/device-management/dto";
import {HttpHeaders} from "@angular/common/http";
import {CommandMapper} from "@frontend-services/device-management/mapper";
import {filter, map} from "rxjs/operators";
import {extract, isNonNull} from "@frontend-services/core";
import {Device, DeviceCommand} from "@frontend-services/device-management/model";

@Injectable({
  providedIn: 'root',
})
export class CommandDeviceService {
  constructor(private apollo: Apollo, private auth: AuthService) {
  }

  canDo() {
    return this.auth.isAuthenticated() && this.auth.isAllowed(["device_management:device:command"])
  }

  command(event: DeviceCommand, device: Device) {
    if (!this.canDo())
      return EMPTY;

    const mutation = gql`
      mutation deviceCommand($instructions: CommandInput) {
        deviceCommand(instructions: $instructions) {
          deviceId
          commandId
        }
      }
    `;
    return this.apollo
      .use('deviceInformation')
      .mutate<CommandDevice>({
        mutation,
        context: {
          headers: new HttpHeaders().set(
            'Authorization',
            'Bearer ' + this.auth.getToken()
          ),
        },
        variables: {instructions: CommandMapper.modelToDto(event, device)},
      })
      .pipe(
        map(extract),
        filter(isNonNull),
        map((value: CommandDevice) =>
          CommandMapper.dtoToModel(value.deviceCommand)
        )
      );
  }
}
