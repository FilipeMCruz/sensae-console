export const environment = {
  production: false,
  endpoints: {
    dataDecoder: {
      backend: {
        domain: "localhost:8184",
        http: {
          scheme: "http://",
          path: "/graphql",
        },
      },
    },
  },
};
