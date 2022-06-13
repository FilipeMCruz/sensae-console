export const environment = {
  production: true,
  endpoints: {
    deviceInformation: {
      backend: {
        domain: "$SENSAE_PROD_PUBLIC_DOMAIN",
        http: {
          scheme: "https://",
          path: "/device-management/graphql",
        },
      },
    },
    dataProcessor: {
      backend: {
        domain: "$SENSAE_PROD_PUBLIC_DOMAIN",
        http: {
          scheme: "https://",
          path: "/data-processor/graphql",
        },
      },
    },
    dataDecoder: {
      backend: {
        domain: "$SENSAE_PROD_PUBLIC_DOMAIN",
        http: {
          scheme: "https://",
          path: "/data-decoder/graphql",
        },
      },
    },
    fleetManagement: {
      backend: {
        domain: "$SENSAE_PROD_PUBLIC_DOMAIN",
        http: {
          scheme: "https://",
          path: "/fleet-management/graphql",
        },
        websocket: {
          scheme: "wss://",
          path: "/fleet-management/subscriptions",
        },
      },
    },
    identity: {
      backend: {
        domain: "$SENSAE_PROD_PUBLIC_DOMAIN",
        http: {
          scheme: "https://",
          path: "/identity-management/graphql",
        },
      },
    },
    smartIrrigation: {
      backend: {
        domain: "$SENSAE_PROD_PUBLIC_DOMAIN",
        http: {
          scheme: "https://",
          path: "/smart-irrigation/graphql",
        },
        websocket: {
          scheme: "wss://",
          path: "/smart-irrigation/subscriptions",
        },
      },
    },
    ruleManagement: {
      backend: {
        domain: "$SENSAE_PROD_PUBLIC_DOMAIN",
        http: {
          scheme: "https://",
          path: "/rule-management/graphql",
        },
      },
    },
    notificationManagement: {
      backend: {
        domain: "localhost",
        http: {
          scheme: "https://",
          path: "/notification-management/graphql",
        },
        websocket: {
          scheme: "wss://",
          path: "/notification-management/subscriptions",
        },
      },
    },
  },
  auth: {
    microsoft: {
      clientId: "$SENSAE_AUTH_EXTERNAL_MICROSOFT_AUDIENCE",
    },
    google: {
      clientId: '$SENSAE_AUTH_EXTERNAL_GOOGLE_AUDIENCE',
    },
  },
  domain: "$SENSAE_PROD_PUBLIC_DOMAIN",
};
