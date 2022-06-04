import {Injectable} from '@angular/core';
import {Apollo, gql} from 'apollo-angular';
import {EMPTY, Observable} from 'rxjs';
import {filter, map} from 'rxjs/operators';
import {extract, isNonNull} from '@frontend-services/core';
import {AuthService} from '@frontend-services/simple-auth-lib';
import {Notification} from "@frontend-services/notification-management/model";
import {NotificationSubscriptionResultDTO} from "@frontend-services/notification-management/dto";
import {OperationsMapper} from "@frontend-services/notification-management/mapper";

@Injectable({
  providedIn: 'root',
})
export class SubscribeToNotification {

  constructor(private apollo: Apollo, private auth: AuthService) {
  }

  canDo(): boolean {
    return this.auth.isAuthenticated() && this.auth.isAllowed(["notification_management:live_data:read"]);
  }

  getData(): Observable<Notification> {
    if (!this.canDo())
      return EMPTY;

    const query = gql`
      subscription notification($Authorization: String){
        notification(Authorization: $Authorization){
          id
          reportedAt
          contentType{
            category
            subCategory
            level
          }
          description
        }
      }
    `;

    return this.apollo
      .use('notificationManagement')
      .subscribe<NotificationSubscriptionResultDTO>({
        query,
        variables: {
          Authorization: 'Bearer ' + this.auth.getToken()
        },
      })
      .pipe(
        map(extract),
        filter(isNonNull),
        map((data: NotificationSubscriptionResultDTO) =>
          OperationsMapper.notificationSubscriptionResult(data)
        )
      );
  }
}
