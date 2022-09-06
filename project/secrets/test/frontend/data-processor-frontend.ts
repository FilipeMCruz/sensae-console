export const environment = {
  production: false,
  endpoints: {
    dataProcessor: {
      backend: {
        domain: "127.0.0.1:8082",
        http: {
          scheme: "http://",
          path: "/graphql",
        },
      },
    },
  },
};
