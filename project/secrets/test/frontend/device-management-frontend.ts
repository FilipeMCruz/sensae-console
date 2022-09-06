export const environment = {
  production: false,
  endpoints: {
    deviceInformation: {
      backend: {
        domain: "127.0.0.1:8088",
        http: {
          scheme: "http://",
          path: "/graphql",
        },
      },
    },
  },
};
