export const environment = {
  production: false,
  endpoints: {
    dataDecoder: {
      backend: {
        domain: "localhost:8084",
        http: {
          scheme: "http://",
          path: "/graphql",
        },
      },
    },
  },
};
