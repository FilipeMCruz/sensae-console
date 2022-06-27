import {NotificationDTO, NotificationReadDTO} from "@frontend-services/notification-management/dto";
import {Notification, Reader} from "@frontend-services/notification-management/model";
import {ContentTypeMapper} from "./ContentTypeMapper";

export class NotificationMapper {

  static dtoToModel(dto: NotificationDTO): Notification {
    const readers = dto.readers ? dto.readers.map(r => new Reader(r.oid, r.name)) : [];
    return new Notification(dto.id, new Date(Number(dto.reportedAt)), ContentTypeMapper.dtoToModel(dto.contentType), dto.description, readers);
  }

  static modelToDto(model: Notification): NotificationReadDTO {
    return {id: model.id};
  }
}
