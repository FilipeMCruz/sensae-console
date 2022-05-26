export const environment = {
  production: false,
  endpoints: {
    deviceRecords: {
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
