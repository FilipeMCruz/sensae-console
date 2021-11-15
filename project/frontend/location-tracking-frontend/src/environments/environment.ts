/**
 * List of properties dev environment.
 */
export const environment = {
  production: false,
  backendURL: {
    websocket: "ws://localhost:8082/subscriptions",
    http: "http://localhost:8082/graphql"
  },
  mapbox: {
    accessToken: "pk.eyJ1IjoiZmlsaXBlY3J1eiIsImEiOiJja215bGkxZWcwNTJqMndwa3pueGdjeDhzIn0.L0aoXWF42aUw8RgRWBKnYA",
    style: "mapbox://styles/mapbox/light-v10"
  }
};
