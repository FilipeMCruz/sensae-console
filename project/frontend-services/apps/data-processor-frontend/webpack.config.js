const ModuleFederationPlugin = require('webpack/lib/container/ModuleFederationPlugin');
const mf = require('@angular-architects/module-federation/webpack');
const path = require('path');
const share = mf.share;

/**
 * We use the NX_TSCONFIG_PATH environment variable when using the @nrwl/angular:webpack-browser
 * builder as it will generate a temporary tsconfig file which contains any required remappings of
 * shared libraries.
 * A remapping will occur when a library is buildable, as webpack needs to know the location of the
 * built files for the buildable library.
 * This NX_TSCONFIG_PATH environment variable is set by the @nrwl/angular:webpack-browser and it contains
 * the location of the generated temporary tsconfig file.
 */
const tsConfigPath =
  process.env.NX_TSCONFIG_PATH ??
  path.join(__dirname, '../../tsconfig.base.json');

const workspaceRootPath = path.join(__dirname, '../../');
const sharedMappings = new mf.SharedMappings();
sharedMappings.register(
  tsConfigPath,
  [
    '@frontend-services/simple-auth-lib',
    '@frontend-services/mutual',
    '@frontend-services/data-processor-model',
    '@frontend-services/data-processor-services',
  ],
  workspaceRootPath
);

module.exports = {
  experiments: {
    outputModule: true,
  },
  output: {
    uniqueName: 'sharespotdataprocessorfrontend',
    publicPath: 'auto',
  },
  optimization: {
    runtimeChunk: false,
  },
  resolve: {
    alias: {
      ...sharedMappings.getAliases(),
    },
  },
  plugins: [
    new ModuleFederationPlugin({
      library: {type: 'module'},
      name: 'sharespotdataprocessorfrontend',
      filename: 'remoteEntry.js',
      exposes: {
        './Module':
          'apps/data-processor-frontend/src/app/remote-entry/entry.module.ts',
      },
      shared: share({
        '@angular/animations': {
          singleton: true,
          strictVersion: true,
          requiredVersion: 'auto',
        },
        '@angular/cdk': {
          singleton: true,
          strictVersion: true,
          requiredVersion: 'auto',
        },
        '@angular/common': {
          singleton: true,
          strictVersion: true,
          requiredVersion: 'auto',
        },
        '@angular/compiler': {
          singleton: true,
          strictVersion: true,
          requiredVersion: 'auto',
        },
        '@angular/core': {
          singleton: true,
          strictVersion: true,
          requiredVersion: 'auto',
        },
        '@angular/forms': {
          singleton: true,
          strictVersion: true,
          requiredVersion: 'auto',
        },
        '@angular/material': {
          singleton: true,
          strictVersion: true,
          requiredVersion: 'auto',
        },
        '@angular/platform-browser': {
          singleton: true,
          strictVersion: true,
          requiredVersion: 'auto',
        },
        '@angular/platform-browser-dynamic': {
          singleton: true,
          strictVersion: true,
          requiredVersion: 'auto',
        },
        '@angular/router': {
          singleton: true,
          strictVersion: true,
          requiredVersion: 'auto',
        },
        rxjs: {singleton: true, strictVersion: true, requiredVersion: 'auto'},
        'rxjs/operators': {
          singleton: true,
          strictVersion: true,
          requiredVersion: '~6.6.0',
        },
        '@apollo/client': {
          singleton: true,
          strictVersion: true,
          requiredVersion: 'auto',
        },
        'apollo-angular': {
          singleton: true,
          strictVersion: true,
          requiredVersion: 'auto',
        },
        graphql: {
          singleton: true,
          strictVersion: true,
          requiredVersion: 'auto',
        },
        '@angular/common/http': {
          singleton: true,
          strictVersion: true,
          requiredVersion: 'auto',
        },
        ...sharedMappings.getDescriptors(),
      }),
    }),
    sharedMappings.getPlugin(),
  ],
};