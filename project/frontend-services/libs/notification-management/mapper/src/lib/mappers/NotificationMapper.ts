import {NotificationDTO} from "@frontend-services/notification-management/dto";
import {Notification} from "@frontend-services/notification-management/model";
import {ContentTypeMapper} from "./ContentTypeMapper";

export class NotificationMapper {

  static dtoToModel(dto: NotificationDTO): Notification {
    return new Notification(dto.id, new Date(Number(dto.reportedAt)), ContentTypeMapper.dtoToModel(dto.contentType), dto.description);
  }
}
