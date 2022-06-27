import {Injectable} from "@angular/core";
import {Apollo, gql} from "apollo-angular";
import {AuthService} from "@frontend-services/simple-auth-lib";
import {Notification} from "@frontend-services/notification-management/model";
import {EMPTY, Observable} from "rxjs";
import {ReadNotificationResultDTO} from "@frontend-services/notification-management/dto";
import {HttpHeaders} from "@angular/common/http";
import {OperationsMapper} from "@frontend-services/notification-management/mapper";
import {filter, map} from "rxjs/operators";
import {extract, isNonNull} from "@frontend-services/core";

@Injectable({
  providedIn: 'root',
})
export class ReadNotification {
  constructor(private apollo: Apollo, private auth: AuthService) {
  }

  canDo() {
    return this.auth.isAuthenticated() &&
      (this.auth.isAllowed(["notification_management:live_data:read"]) ||
        this.auth.isAllowed(["notification_management:past_data:read"]));
  }

  execute(command: Notification): Observable<Notification> {
    if (!this.canDo())
      return EMPTY;

    const query = gql`
      mutation register($read: ReadNotificationInput){
        register(read: $read){
          id
        }
      }
    `;

    return this.apollo
      .use('notificationManagement')
      .subscribe<ReadNotificationResultDTO>({
        query,
        context: {
          headers: new HttpHeaders().set(
            'Authorization',
            'Bearer ' + this.auth.getToken()
          ),
        },
        variables: OperationsMapper.notificationRead(command)
      })
      .pipe(
        map(extract),
        filter(isNonNull),
        map((data: ReadNotificationResultDTO) => OperationsMapper.notificationReadResult(data.register))
      );
  }
}
