export const environment = {
  production: false,
  endpoints: {
    identity: {
      backend: {
        domain: "127.0.0.1:8086",
        http: {
          scheme: "http://",
          path: "/graphql",
        },
      },
    },
  },
};
