export const environment = {
  production: true,
  backendURL: {
    websocket: "ws://localhost/locationsTracking/subscriptions",
    http: "http://localhost/locationTracking/graphql"
  },
  mapbox: {
    accessToken: "pk.eyJ1IjoiZmlsaXBlY3J1eiIsImEiOiJja215bGkxZWcwNTJqMndwa3pueGdjeDhzIn0.L0aoXWF42aUw8RgRWBKnYA",
    style: "mapbox://styles/mapbox/light-v10"
  }
};
