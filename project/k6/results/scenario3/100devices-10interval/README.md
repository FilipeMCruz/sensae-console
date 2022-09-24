# Results

```txt
          /\      |‾‾| /‾‾/   /‾‾/   
     /\  /  \     |  |/  /   /  /    
    /  \/    \    |     (   /   ‾‾\  
   /          \   |  |\  \ |  (‾)  | 
  / __________ \  |__| \__\ \_____/ .io

  execution: local
     script: test-suite-3-test.js
     output: csv (results/data.csv)

  scenarios: (100.00%) 3 scenarios, 22 max VUs, 4m40s max duration (incl. graceful stop):
           * subscribe: 1 iterations shared among 1 VUs (maxDuration: 4m0s, exec: subscribe, gracefulStop: 30s)
           * ingestion: 10 iterations for each of 20 VUs (maxDuration: 4m0s, exec: ingestion, startTime: 5s, gracefulStop: 30s)
           * consumption: 1 iterations shared among 1 VUs (maxDuration: 10s, exec: consumption, startTime: 4m0s, gracefulStop: 30s)

INFO[0159] Received 1000 messages                        source=console
INFO[0159] Expected: 1000; Actual: 1000                  source=console
INFO[0285] Data Units stored: 2000                       source=console

running (4m48.5s), 00/22 VUs, 202 complete and 0 interrupted iterations
subscribe   ✓ [======================================] 1 VUs   1m54.6s/4m0s  1/1 shared iters
ingestion   ✓ [======================================] 20 VUs  1m49.6s/4m0s  200/200 iters, 10 per VU
consumption ✓ [======================================] 1 VUs   00.4s/10s     1/1 shared iters

     ✓ status was 202
     ✓ all data units were received
     ✓ correct data units were received
     ✓ data units were all stored

     █ setup

     █ teardown

     checks.........................: 100.00% ✓ 2002     ✗ 0   
     data_received..................: 463 kB  1.6 kB/s
     data_sent......................: 631 kB  2.2 kB/s
     http_req_blocked...............: avg=11.25ms  min=1.95µs   med=12.78µs  max=555.32ms p(90)=18.11µs  p(95)=22.16µs 
     http_req_connecting............: avg=4.11ms   min=0s       med=0s       max=184.88ms p(90)=0s       p(95)=0s      
     http_req_duration..............: avg=181.73ms min=172.13ms med=179.45ms max=264.97ms p(90)=188.12ms p(95)=190.46ms
       { expected_response:true }...: avg=181.73ms min=172.13ms med=179.45ms max=264.97ms p(90)=188.12ms p(95)=190.46ms
     http_req_failed................: 0.00%   ✓ 0        ✗ 1003
     http_req_receiving.............: avg=172.81µs min=30.52µs  med=172.24µs max=9.79ms   p(90)=217.49µs p(95)=233.95µs
     http_req_sending...............: avg=96.28µs  min=15.59µs  med=99.55µs  max=323.39µs p(90)=131.42µs p(95)=145.43µs
     http_req_tls_handshaking.......: avg=7.11ms   min=0s       med=0s       max=371.17ms p(90)=0s       p(95)=0s      
     http_req_waiting...............: avg=181.46ms min=171.87ms med=179.21ms max=264.71ms p(90)=187.87ms p(95)=190.33ms
     http_reqs......................: 1003    3.476346/s
     iteration_duration.............: avg=11.06s   min=396.14ms med=10.91s   max=1m54s    p(90)=12.39s   p(95)=17.27s  
     iterations.....................: 202     0.700122/s
     time_lapse.....................: avg=206.385  min=182      med=193      max=765      p(90)=205      p(95)=215.05  
     vus............................: 0       min=0      max=21
     vus_max........................: 22      min=22     max=22
     ws_connecting..................: avg=359.07ms min=359.07ms med=359.07ms max=359.07ms p(90)=359.07ms p(95)=359.07ms
     ws_msgs_received...............: 1001    3.469414/s
     ws_msgs_sent...................: 2       0.006932/s
     ws_session_duration............: avg=1m54s    min=1m54s    med=1m54s    max=1m54s    p(90)=1m54s    p(95)=1m54s   
     ws_sessions....................: 1       0.003466/s
```
