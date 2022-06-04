export const environment = {
  production: true,
  endpoints: {
    notificationManagement: {
      backend: {
        domain: "$SENSAE_PROD_PUBLIC_DOMAIN",
        http: {
          scheme: "http://",
          path: "/notification-management/graphql",
        },
        websocket: {
          scheme: "ws://",
          path: "/notification-management/subscriptions",
        },
      },
    },
  },
};
