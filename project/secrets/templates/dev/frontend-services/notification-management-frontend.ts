export const environment = {
  production: false,
  endpoints: {
    notificationManagement: {
      backend: {
        domain: "localhost:8096",
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
