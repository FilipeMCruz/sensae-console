export const environment = {
  production: true,
  endpoints: {
    deviceRecords: {
      backend: {
        domain: "$SENSAE_PROD_PUBLIC_DOMAIN",
        http: {
          scheme: "http://",
          path: "/graphql",
        },
      },
    },
    dataProcessor: {
      backend: {
        domain: "$SENSAE_PROD_PUBLIC_DOMAIN",
        http: {
          scheme: "http://",
          path: "/graphql",
        },
      },
    },
    dataDecoder: {
      backend: {
        domain: "$SENSAE_PROD_PUBLIC_DOMAIN",
        http: {
          scheme: "http://",
          path: "/graphql",
        },
      },
    },
    fleetManagement: {
      backend: {
        domain: "$SENSAE_PROD_PUBLIC_DOMAIN",
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
    identity: {
      backend: {
        domain: "$SENSAE_PROD_PUBLIC_DOMAIN",
        http: {
          scheme: "http://",
          path: "/graphql",
        },
      },
    },
    smartIrrigation: {
      backend: {
        domain: "$SENSAE_PROD_PUBLIC_DOMAIN",
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
    ruleManagement: {
      backend: {
        domain: "$SENSAE_PROD_PUBLIC_DOMAIN",
        http: {
          scheme: "http://",
          path: "/graphql",
        },
      },
    },
  },
  notificationManagement: {
    backend: {
      domain: "localhost",
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
  auth: {
    clientId: "$SENSAE_AUTH_EXTERNAL_AUDIENCE",
  },
  domain: "$SENSAE_PROD_PUBLIC_DOMAIN",
};
