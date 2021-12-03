import {loadRemoteEntry} from '@angular-architects/module-federation';
import {environment} from "./environments/environment";
//
// const remotes = [
//   {
//     'remoteEntry': environment.production ? 'http://micro-frontends/sharespot-device-records-frontend/remoteEntry.js' : 'http://localhost:4283/remoteEntry.js',
//     'remoteName': 'sharespotdevicerecordsfrontend'
//   },
//   {
//     'remoteEntry': environment.production ? 'http://micro-frontends/sharespot-location-tracking-frontend/remoteEntry.js' : 'http://localhost:4284/remoteEntry.js',
//     'remoteName': 'sharespotlocationtrackingfrontend'
//   }
// ];

const remotes = [
  {
    'remoteEntry':'http://localhost:4283/remoteEntry.js',
    'remoteName': 'sharespotdevicerecordsfrontend'
  },
  {
    'remoteEntry': 'http://localhost:4284/remoteEntry.js',
    'remoteName': 'sharespotlocationtrackingfrontend'
  }
];
Promise.all(remotes.map(r => loadRemoteEntry(r.remoteEntry, r.remoteName)))
  .catch(err => console.error('Error loading remote entries', err))
  .then(() => import('./bootstrap'))
  .catch(err => console.error(err));
