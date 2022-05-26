export const environment = {
  production: false,
  endpoints: {
    ruleManagement: {
      backend: {
        domain: "localhost:8094",
        http: {
          scheme: "http://",
          path: "/graphql",
        },
      },
    },
  },
};
