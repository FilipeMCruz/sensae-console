import {
  DeviceLedgerHistoryEntryDTO,
  DeviceTypeDTO,
  SensorDataHistoryDTO
} from "@frontend-services/smart-irrigation/dto";
import {
  Device,
  DeviceLedgerHistoryEntry,
  DeviceType,
  RecordEntry,
  SensorDataHistory
} from "@frontend-services/smart-irrigation/model";
import {DataDetailsMapper} from "./DataDetailsMapper";

export class DataHistoryMapper {

  static dtoToModel(dto: SensorDataHistoryDTO): SensorDataHistory {
    const ledger = dto.ledger.map(l => DataHistoryMapper.ledgerDtoToModel(l, dto.type));
    switch (dto.type) {
      case DeviceTypeDTO.PARK_SENSOR:
        return new SensorDataHistory(dto.id, DeviceType.PARK_SENSOR, ledger);
      case DeviceTypeDTO.STOVE_SENSOR:
        return new SensorDataHistory(dto.id, DeviceType.STOVE_SENSOR, ledger);
      case DeviceTypeDTO.VALVE:
        return new SensorDataHistory(dto.id, DeviceType.VALVE, ledger);
    }
  }

  static ledgerDtoToModel(dto: DeviceLedgerHistoryEntryDTO, type: DeviceTypeDTO): DeviceLedgerHistoryEntry {
    const data = dto.data.map(d => DataDetailsMapper.historyDtoToModel(d, type));
    return new DeviceLedgerHistoryEntry(dto.name,
      DataDetailsMapper.gpsDtoToModel(dto.gps),
      dto.records.map(d => new RecordEntry(d.label, d.content)),
      data);
  }
}
