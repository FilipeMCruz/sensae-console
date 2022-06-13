import {Injectable} from "@angular/core";
import {SubscribeToNotification} from "@frontend-services/notification-management/services";
import {BehaviorSubject, Observable, Subscription} from "rxjs";
import {Notification} from "@frontend-services/notification-management/model";

@Injectable({
  providedIn: 'root',
})
export class NotificationService {

  private subscription!: Subscription;

  private myObservable = new BehaviorSubject<Notification>(Notification.empty());

  private currentData = this.myObservable.asObservable();

  constructor(private notifications: SubscribeToNotification) {
  }

  private start() {
    if (this.subscription == null || this.subscription.closed)
      this.subscription = this.notifications.getData().subscribe(value => this.myObservable.next(value))
  }

  getCurrentData(): Observable<Notification> {
    this.start();
    return this.currentData;
  }
}
