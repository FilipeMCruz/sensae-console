const ModuleFederationPlugin = require("webpack/lib/container/ModuleFederationPlugin");
const mf = require("@angular-architects/module-federation/webpack");
const path = require("path");
const share = mf.share;

const sharedMappings = new mf.SharedMappings();
sharedMappings.register(
  path.join(__dirname, '../../tsconfig.json'),
  [/* mapped paths to share */]);

module.exports = {
  output: {
    uniqueName: "uiAggregator",
    publicPath: "auto"
  },
  optimization: {
    runtimeChunk: false
  },
  resolve: {
    alias: {
      ...sharedMappings.getAliases(),
    }
  },
  plugins: [
    new ModuleFederationPlugin({

      remotes: {
        // 'sharespotDeviceRecordsFrontend': "sharespotDeviceRecordsFrontend@http://localhost:7084/remoteEntry.js",
        // 'sharespotLocationTrackingFrontend': "sharespotLocationTrackingFrontend@http://localhost:7085/remoteEntry.js",
      },
      shared: share({
        "@angular/animations": {singleton: true, strictVersion: true, requiredVersion: 'auto'},
        "@angular/cdk": {singleton: true, strictVersion: true, requiredVersion: 'auto'},
        "@angular/common": {singleton: true, strictVersion: true, requiredVersion: 'auto'},
        "@angular/compiler": {singleton: true, strictVersion: true, requiredVersion: 'auto'},
        "@angular/core": {singleton: true, strictVersion: true, requiredVersion: 'auto'},
        "@angular/forms": {singleton: true, strictVersion: true, requiredVersion: 'auto'},
        "@angular/material": {singleton: true, strictVersion: true, requiredVersion: 'auto'},
        "@angular/platform-browser": {singleton: true, strictVersion: true, requiredVersion: 'auto'},
        "@angular/platform-browser-dynamic": {singleton: true, strictVersion: true, requiredVersion: 'auto'},
        "@apollo/client": {singleton: true, strictVersion: true, requiredVersion: 'auto'},
        "apollo-angular": {singleton: true, strictVersion: true, requiredVersion: 'auto'},
        "graphql": {singleton: true, strictVersion: true, requiredVersion: 'auto'},
        "subscriptions-transport-ws": {singleton: true, strictVersion: true, requiredVersion: 'auto', eager: true},

        ...sharedMappings.getDescriptors()
      })

    }),
    sharedMappings.getPlugin()
  ],
};
