export const environment = {
  production: false,
  endpoints: {
    dataDecoder: {
      backend: {
        domain: "127.0.0.1:8184",
        http: {
          scheme: "http://",
          path: "/graphql",
        },
      },
    },
  },
};
