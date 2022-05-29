import {
  AddresseeConfigMutationInstructionsDTO,
  AddresseeConfigMutationResultDTO, AddresseeConfigResultDTO,
  NotificationHistoryQueryDTO, NotificationHistoryResultDTO, NotificationSubscriptionResultDTO
} from "@frontend-services/notification-management/dto";
import {
  AddresseeConfiguration,
  Notification,
  NotificationHistoryQuery
} from "@frontend-services/notification-management/model";
import {AddresseeMapper} from "./AddresseeMapper";
import {NotificationMapper} from "./NotificationMapper";

export class OperationsMapper {
  static configAddresseeInstructions(model: AddresseeConfiguration): AddresseeConfigMutationInstructionsDTO {
    return {instructions: AddresseeMapper.modelToDto(model)};
  }

  static configAddresseeMutationResult(dto: AddresseeConfigMutationResultDTO): AddresseeConfiguration {
    return AddresseeMapper.dtoToModel(dto.config);
  }

  static configAddresseeResult(dto: AddresseeConfigResultDTO): AddresseeConfiguration {
    return AddresseeMapper.dtoToModel(dto.config);
  }

  static notificationSubscriptionResult(dto: NotificationSubscriptionResultDTO): Notification {
    return NotificationMapper.dtoToModel(dto.notification);
  }

  static notificationHistoryResult(dto: NotificationHistoryResultDTO): Array<Notification> {
    return dto.history.map(NotificationMapper.dtoToModel);
  }

  static notificationHistoryQuery(model: NotificationHistoryQuery): NotificationHistoryQueryDTO {
    return {
      filters: {
        endTime: Math.round(model.end.getTime() / 1000).toString(),
        startTime: Math.round(model.start.getTime() / 1000).toString()
      }
    }
  }
}
