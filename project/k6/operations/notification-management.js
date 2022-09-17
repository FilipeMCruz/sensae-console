export const subscribeToLiveNotificationsQuery = `     
subscription notification($Authorization: String){
  notification(Authorization: $Authorization){
    id
    reportedAt
    contentType{
      category
      subCategory
      level
    }
    description
  }
}`;
