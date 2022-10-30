export const subscribeToLiveGPSDataQuery = `     
subscription locations($Authorization: String) {
  locations(Authorization: $Authorization) {
    dataId
    device {
      id
    }
    reportedAt
  }
}`;
