export const environment = {
  production: false,
  endpoints: {
    dataProcessor: {
      backend: {
        domain: "$SENSAE_PROD_PUBLIC_DOMAIN",
        http: {
          scheme: "http://",
          path: "/graphql",
        },
      },
    },
  },
};
