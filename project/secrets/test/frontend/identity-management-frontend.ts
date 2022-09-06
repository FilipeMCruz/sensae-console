export const environment = {
  production: false,
  endpoints: {
    identity: {
      backend: {
        domain: "localhost:8086",
        http: {
          scheme: "http://",
          path: "/graphql",
        },
      },
    },
  },
};
