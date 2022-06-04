export const environment = {
  production: true,
  endpoints: {
    deviceInformation: {
      backend: {
        domain: "$SENSAE_PROD_PUBLIC_DOMAIN",
        http: {
          scheme: "http://",
          path: "/device-management/graphql",
        },
      },
    },
  },
};
