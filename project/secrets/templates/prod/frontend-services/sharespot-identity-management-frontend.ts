export const environment = {
  production: true,
  endpoints: {
    identity: {
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
