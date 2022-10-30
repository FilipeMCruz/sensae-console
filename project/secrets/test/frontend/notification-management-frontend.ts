export const environment = {
  production: false,
  endpoints: {
    notificationManagement: {
      backend: {
        domain: "127.0.0.1:8096",
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
};
