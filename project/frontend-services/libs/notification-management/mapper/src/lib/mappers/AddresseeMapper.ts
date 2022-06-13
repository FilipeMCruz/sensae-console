import {
  AddresseeConfiguration,
  AddresseeConfigurationEntry,
  DeliveryType
} from "@frontend-services/notification-management/model";
import {AddresseeDTO, DeliveryTypeDTO} from "@frontend-services/notification-management/dto";
import {ContentTypeMapper} from "./ContentTypeMapper";

export class AddresseeMapper {
  static dtoToModel(dto: AddresseeDTO): AddresseeConfiguration {
    const entries = dto.entries.map(conf => new AddresseeConfigurationEntry(this.dtoToModelDeliveryType(conf.deliveryType), ContentTypeMapper.dtoToModel(conf.contentType), conf.mute));
    return new AddresseeConfiguration(entries);
  }

  static modelToDto(model: AddresseeConfiguration): AddresseeDTO {
    const entries = model.entries.map(conf => ({
      mute: conf.mute,
      deliveryType: this.modelToDtoDeliveryType(conf.deliveryType),
      contentType: ContentTypeMapper.modelToDto(conf.contentType)
    }));
    return {entries};
  }

  private static modelToDtoDeliveryType(model: DeliveryType): DeliveryTypeDTO {
    switch (model) {
      case DeliveryType.NOTIFICATION:
        return DeliveryTypeDTO.NOTIFICATION;
      case DeliveryType.SMS:
        return DeliveryTypeDTO.SMS;
      case DeliveryType.EMAIL:
        return DeliveryTypeDTO.EMAIL;
      case DeliveryType.UI:
        return DeliveryTypeDTO.UI;
    }
  }

  private static dtoToModelDeliveryType(dto: DeliveryTypeDTO): DeliveryType {
    switch (dto) {
      case DeliveryTypeDTO.NOTIFICATION:
        return DeliveryType.NOTIFICATION;
      case DeliveryTypeDTO.SMS:
        return DeliveryType.SMS;
      case DeliveryTypeDTO.EMAIL:
        return DeliveryType.EMAIL;
      case DeliveryTypeDTO.UI:
        return DeliveryType.UI;
    }
  }
}
