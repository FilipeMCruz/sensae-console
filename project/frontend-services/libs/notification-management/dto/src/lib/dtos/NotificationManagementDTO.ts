export interface NotificationSubscriptionResultDTO {
  notification: NotificationDTO
}

export interface AddresseeConfigMutationInstructionsDTO {
  instructions: AddresseeDTO
}

export interface AddresseeConfigMutationResultDTO {
  config: AddresseeDTO
}

export interface NotificationHistoryQueryDTO {
  filters: HistoryQueryDTO
}

export interface NotificationHistoryResultDTO {
  history: NotificationDTO[]
}

export interface ReadNotificationInstructionsDTO {
  read: NotificationReadDTO
}

export interface ReadNotificationResultDTO {
  register: NotificationReadDTO
}

export interface AddresseeConfigResultDTO {
  config: AddresseeDTO
}

export interface NotificationDTO {
  id: string
  reportedAt: string
  contentType: ContentTypeDTO
  description: string
  readers: ReaderDTO[]
}

export interface ReaderDTO {
  oid: string
  name: string
}

export enum NotificationLevelDTO {
  INFORMATION = "INFORMATION",
  WATCH = "WATCH",
  ADVISORY = "ADVISORY",
  WARNING = "WARNING",
  CRITICAL = "CRITICAL"
}

export interface ContentTypeDTO {
  category: string
  subCategory: string
  level: NotificationLevelDTO
}

export interface AddresseeDTO {
  entries: AddresseeConfigDTO[]
}

export interface AddresseeConfigDTO {
  deliveryType: DeliveryTypeDTO
  contentType: ContentTypeDTO
  mute: boolean
}

export enum DeliveryTypeDTO {
  SMS = "SMS",
  EMAIL = "EMAIL",
  NOTIFICATION = "NOTIFICATION",
  UI = "UI"
}

export interface HistoryQueryDTO {
  startTime: string
  endTime: string
}

export interface NotificationReadDTO {
  id: string
}
