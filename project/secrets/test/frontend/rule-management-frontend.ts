export const environment = {
  production: false,
  endpoints: {
    ruleManagement: {
      backend: {
        domain: "127.0.0.1:8094",
        http: {
          scheme: "http://",
          path: "/graphql",
        },
      },
    },
  },
};
