export const environment = {
  production: false,
  endpoints: {
    deviceRecords: {
      backend: {
        domain: "localhost:8088",
        http: {
          scheme: "http://",
          path: "/graphql",
        },
      },
    },
    dataProcessor: {
      backend: {
        domain: "localhost:8082",
        http: {
          scheme: "http://",
          path: "/graphql",
        },
      },
    },
    dataDecoder: {
      backend: {
        domain: "localhost:8084",
        http: {
          scheme: "http://",
          path: "/graphql",
        },
      },
    },
    fleetManagement: {
      backend: {
        domain: "localhost:8800",
        http: {
          scheme: "http://",
          path: "/graphql",
        },
        websocket: {
          scheme: "ws://",
          path: "/subscriptions",
        },
      },
    },
    identity: {
      backend: {
        domain: "localhost:8086",
        http: {
          scheme: "http://",
          path: "/graphql",
        },
      },
    },
    smartIrrigation: {
      backend: {
        domain: "localhost:8801",
        http: {
          scheme: "http://",
          path: "/graphql",
        },
        websocket: {
          scheme: "ws://",
          path: "/subscriptions",
        },
      },
    },
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
  domain: "localhost",
};
