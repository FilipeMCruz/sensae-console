export const environment = {
  production: false,
  endpoints: {
    smartIrrigation: {
      backend: {
        domain: "localhost:8801",
        http: {
          scheme: "http://",
          path: "/graphql",
        },
        websocket: {
          scheme: "ws://",
          path: "/subscriptions",
        },
      },
    },
  },
  mapbox: {
    accessToken: "$SENSAE_MAPBOX_ACCESS_TOKEN",
    simpleStyle: "mapbox://styles/mapbox/light-v10",
    satelliteStyle: "mapbox://styles/mapbox/light-v10",
  },
};
