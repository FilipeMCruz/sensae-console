export class TenantProfile {
  constructor(
    public id: string,
    public name: string,
    public email: string,
    public phoneNumber: string
  ) {
  }
  copy(): TenantProfile {
    return new TenantProfile(this.id, this.name, this.email, this.phoneNumber);
  }
}
