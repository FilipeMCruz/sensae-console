export const subscribeToLiveDataQuery = `     
subscription data($filters: LiveDataFilter, $Authorization: String){
  data(filters: $filters, Authorization: $Authorization){
    dataId
    device{
      id
      name
    }
    reportedAt
  }
}`;
