# Restful service

@GET("/service/json/v1/event")
Response<List<Event>> getEventList(String uuid, String appName, String version);

request
- url
- method
- header
- action
- query
- body

event_list.rs
```
name=getEventList
method=get
action=/service/json/v1/event/{event_id}
query=uuid:String,appName:String,version:String
body=event:com.active.event.data.Event
response=eventList:com.active.event.RsResponse<List<com.active.event.data.Event>>
```

```java
public interface EventRs {
    @GET("/service/json/v1/event/{event_id}")
    Call<RsResponse<List<Event>>> getEventList(@Query("uuid") String uuid, @Query("appName") String appName, @Query("version") String version);
}

public class RsResponse<T> {
    private boolean success;
    private T data;
    private String errorMessage;
    private Integer errorCode;
}
```