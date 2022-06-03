export interface AuthenticateResultDTO {
  authenticate: OutcomeDTO;
}

export interface RefreshResultDTO {
  refresh: OutcomeDTO;
}

export interface OutcomeDTO {
  token: string;
}

export interface TenantIdentity {
  email: string;
  name: string;
  oid: string;
  domains: string[];
  permissions: string[];
}
