export interface CredentialsDTO {
  name: string,
  secret : string
}

export interface OutcomeDTOQuery {
  credentials: OutcomeDTO
}

export interface OutcomeDTO {
  valid: boolean
}
