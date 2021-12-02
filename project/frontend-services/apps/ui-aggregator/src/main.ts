import {loadRemoteEntry} from '@angular-architects/module-federation';

const remotes = [
  {
    'remoteEntry': 'http://localhost:4283/remoteEntry.js',
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
