export const environment = {
  production: true,
  endpoints: {
    dataProcessor: {
      backend: {
        domain: "$SENSAE_PROD_PUBLIC_DOMAIN",
        http: {
          scheme: "http://",
          path: "/data-processor/graphql",
        },
      },
    },
  },
};
