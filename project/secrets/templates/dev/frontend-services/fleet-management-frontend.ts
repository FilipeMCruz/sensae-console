export const environment = {
  production: false,
  endpoints: {
    fleetManagement: {
      backend: {
        domain: "localhost:8800",
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
    style: "mapbox://styles/mapbox/light-v10",
  },
};
