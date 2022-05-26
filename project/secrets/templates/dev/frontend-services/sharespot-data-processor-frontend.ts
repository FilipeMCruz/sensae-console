export const environment = {
  production: false,
  endpoints: {
    dataProcessor: {
      backend: {
        domain: "localhost:8082",
        http: {
          scheme: "http://",
          path: "/graphql",
        },
      },
    },
  },
};
