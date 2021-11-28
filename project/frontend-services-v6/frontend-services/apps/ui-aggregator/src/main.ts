import { loadRemoteEntry } from '@angular-architects/module-federation';

const SHARESPOT_LOCATION_TRACKING_FRONTEND_URL = 'http://localhost:4285/remoteEntry.js';
const SHARESPOT_DEVICE_RECORDS_FRONTEND_URL = 'http://localhost:4284/remoteEntry.js';

const remotes = [
  {
    'remoteEntry': 'http://localhost:4284/remoteEntry.js',
    'remoteName': 'sharespotdevicerecordsfrontend'
  }
];

Promise.all(remotes.map(r => loadRemoteEntry(r.remoteEntry, r.remoteName)))
  .catch(err => console.error('Error loading remote entries', err))
  .then(() => import('./bootstrap'))
  .catch(err => console.error(err));
