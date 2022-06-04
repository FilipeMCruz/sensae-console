import {Component, OnInit} from '@angular/core';
import {GetProfile, TenantProfile, UpdateProfile} from "@frontend-services/simple-auth-lib";

@Component({
  selector: 'frontend-services-home',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.scss']
})
export class ProfileComponent implements OnInit {

  profile!: TenantProfile;
  old!: TenantProfile;
  loading = true;

  constructor(private profiler: GetProfile, private profileUpdate: UpdateProfile) {
  }

  ngOnInit(): void {
    this.profiler.fetch()
      .subscribe(next => {
        this.profile = next.copy();
        this.old = next.copy();
        this.loading = false;
      });
  }

  changed() {
    return this.profile.name !== this.old.name || this.profile.phoneNumber !== this.old.phoneNumber;
  }

  valid() {
    return this.profile.name.trim().length !== 0 && this.profile.phoneNumber.match(/\+\d{6,}/);
  }

  updateProfile() {
    this.profileUpdate.mutate(this.profile).subscribe(next => {
      this.profile = next.copy();
      this.old = next.copy();
    })
  }
}
