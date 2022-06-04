export const environment = {
  production: true,
  endpoints: {
    fleetManagement: {
      backend: {
        domain: "$SENSAE_PROD_PUBLIC_DOMAIN",
        http: {
          scheme: "http://",
          path: "/fleet-management/graphql",
        },
        websocket: {
          scheme: "ws://",
          path: "/fleet-management/subscriptions",
        },
      },
    },
  },
  mapbox: {
    accessToken: "$SENSAE_MAPBOX_ACCESS_TOKEN",
    style: "mapbox://styles/mapbox/light-v10",
  },
};
