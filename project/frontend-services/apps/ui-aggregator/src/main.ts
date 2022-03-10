import { loadRemoteEntry } from '@angular-architects/module-federation';

import('./bootstrap').catch((err) => console.error(err));

loadRemoteEntry({
  type: 'module',
  remoteEntry: 'http://localhost:4282/remoteEntry.js',
})
  // eslint-disable-next-line @typescript-eslint/no-unused-vars
  .then((_) => import('./bootstrap').catch((err) => console.error(err)));
loadRemoteEntry({
  type: 'module',
  remoteEntry: 'http://localhost:4283/remoteEntry.js',
})
  // eslint-disable-next-line @typescript-eslint/no-unused-vars
  .then((_) => import('./bootstrap').catch((err) => console.error(err)));
loadRemoteEntry({
  type: 'module',
  remoteEntry: 'http://localhost:4284/remoteEntry.js',
})
  // eslint-disable-next-line @typescript-eslint/no-unused-vars
  .then((_) => import('./bootstrap').catch((err) => console.error(err)));
loadRemoteEntry({
  type: 'module',
  remoteEntry: 'http://localhost:4285/remoteEntry.js',
})
  // eslint-disable-next-line @typescript-eslint/no-unused-vars
  .then((_) => import('./bootstrap').catch((err) => console.error(err)));
