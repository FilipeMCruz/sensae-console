import {
  ContentTypeDTO,
  NotificationLevelDTO
} from "@frontend-services/notification-management/dto";
import {
  ContentType, NotificationSeverityLevel
} from "@frontend-services/notification-management/model";

export class ContentTypeMapper {
  static dtoToModel(dto: ContentTypeDTO): ContentType {
    return new ContentType(dto.category, dto.subCategory, this.dtoToModelLevel(dto.level))
  }

  static modelToDto(model: ContentType): ContentTypeDTO {
    return {category: model.category, subCategory: model.subCategory, level: this.modelToDtoLevel(model.severity)}
  }

  private static modelToDtoLevel(model: NotificationSeverityLevel): NotificationLevelDTO {
    switch (model) {
      case NotificationSeverityLevel.CRITICAL:
        return NotificationLevelDTO.CRITICAL;
      case NotificationSeverityLevel.ADVISORY:
        return NotificationLevelDTO.ADVISORY;
      case NotificationSeverityLevel.WARNING:
        return NotificationLevelDTO.WATCH;
      case NotificationSeverityLevel.WATCH:
        return NotificationLevelDTO.WARNING;
      case NotificationSeverityLevel.INFORMATION:
        return NotificationLevelDTO.INFORMATION;
    }
  }

  private static dtoToModelLevel(dto: NotificationLevelDTO): NotificationSeverityLevel {
    switch (dto) {
      case NotificationLevelDTO.CRITICAL:
        return NotificationSeverityLevel.CRITICAL;
      case NotificationLevelDTO.ADVISORY:
        return NotificationSeverityLevel.ADVISORY;
      case NotificationLevelDTO.WARNING:
        return NotificationSeverityLevel.WARNING;
      case NotificationLevelDTO.WATCH:
        return NotificationSeverityLevel.WATCH;
      case NotificationLevelDTO.INFORMATION:
        return NotificationSeverityLevel.INFORMATION;
    }
  }
}
