export const environment = {
  production: true,
  endpoints: {
    smartIrrigation: {
      backend: {
        domain: "$SENSAE_PROD_PUBLIC_DOMAIN",
        http: {
          scheme: "http://",
          path: "/smart-irrigation/graphql",
        },
        websocket: {
          scheme: "ws://",
          path: "/smart-irrigation/subscriptions",
        },
      },
    },
  },
  mapbox: {
    accessToken: "$SENSAE_MAPBOX_ACCESS_TOKEN",
    style: "mapbox://styles/mapbox/light-v10",
  },
};
