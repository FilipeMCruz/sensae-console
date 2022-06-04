import {Apollo, gql} from 'apollo-angular';
import {EMPTY, Observable} from 'rxjs';
import {Injectable} from '@angular/core';
import {filter, map} from 'rxjs/operators';
import {extract, isNonNull} from '@frontend-services/core';
import {HttpHeaders} from '@angular/common/http';
import {AuthService} from '@frontend-services/simple-auth-lib';
import {
  AddresseeConfiguration,
  NotificationHistoryQuery
} from "@frontend-services/notification-management/model";
import {AddresseeConfigResultDTO} from "@frontend-services/notification-management/dto";
import {OperationsMapper} from "@frontend-services/notification-management/mapper";

@Injectable({
  providedIn: 'root',
})
export class FetchConfiguration {
  constructor(private apollo: Apollo, private auth: AuthService) {
  }

  canDo(): boolean {
    return this.auth.isAuthenticated() && this.auth.isAllowed(["notification_management:config:read"])
  }

  getData(): Observable<AddresseeConfiguration> {
    if (!this.canDo())
      return EMPTY;

    const query = gql`
      query config{
        config{
          entries{
            deliveryType
            contentType{
              category
              subCategory
              level
            }
            mute
          }
        }
      }
    `;

    return this.apollo
      .use('notificationManagement')
      .subscribe<AddresseeConfigResultDTO>({
        query,
        context: {
          headers: new HttpHeaders().set(
            'Authorization',
            'Bearer ' + this.auth.getToken()
          ),
        },
      })
      .pipe(
        map(extract),
        filter(isNonNull),
        map((data: AddresseeConfigResultDTO) => OperationsMapper.configAddresseeResult(data))
      );
  }
}
