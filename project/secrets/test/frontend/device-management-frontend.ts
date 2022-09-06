export const environment = {
  production: false,
  endpoints: {
    deviceInformation: {
      backend: {
        domain: "localhost:8088",
        http: {
          scheme: "http://",
          path: "/graphql",
        },
      },
    },
  },
};
