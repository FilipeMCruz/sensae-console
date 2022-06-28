import {Apollo, gql} from 'apollo-angular';
import {EMPTY, Observable} from 'rxjs';
import {Injectable} from '@angular/core';
import {filter, map} from 'rxjs/operators';
import {extract, isNonNull} from '@frontend-services/core';
import {HttpHeaders} from '@angular/common/http';
import {AuthService} from '@frontend-services/simple-auth-lib';
import {Notification, NotificationHistoryQuery} from "@frontend-services/notification-management/model";
import {NotificationHistoryResultDTO} from "@frontend-services/notification-management/dto";
import {OperationsMapper} from "@frontend-services/notification-management/mapper";

@Injectable({
  providedIn: 'root',
})
export class FetchNotificationHistory {
  constructor(private apollo: Apollo, private auth: AuthService) {
  }

  canDo(): boolean {
    return this.auth.isAuthenticated() && this.auth.isAllowed(["notification_management:past_data:read"])
  }

  getData(filters: NotificationHistoryQuery): Observable<Array<Notification>> {
    if (!this.canDo())
      return EMPTY;

    const query = gql`
      query history($filters: HistoryQuery){
        history(filters: $filters){
          id
          reportedAt
          contentType{
            category
            subCategory
            level
          }
          description
          readers{
            oid
            name
          }
        }
      }
    `;

    return this.apollo
      .use('notificationManagement')
      .subscribe<NotificationHistoryResultDTO>({
        query,
        context: {
          headers: new HttpHeaders().set(
            'Authorization',
            'Bearer ' + this.auth.getToken()
          ),
        },
        variables: OperationsMapper.notificationHistoryQuery(filters)
      })
      .pipe(
        map(extract),
        filter(isNonNull),
        map((data: NotificationHistoryResultDTO) => OperationsMapper.notificationHistoryResult(data))
      );
  }
}
