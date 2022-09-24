# Results

Ingestion:

```txt
          /\      |‾‾| /‾‾/   /‾‾/   
     /\  /  \     |  |/  /   /  /    
    /  \/    \    |     (   /   ‾‾\  
   /          \   |  |\  \ |  (‾)  | 
  / __________ \  |__| \__\ \_____/ .io

  execution: local
     script: test-suite-1-test.js
     output: csv (results/data.csv)

  scenarios: (100.00%) 3 scenarios, 42 max VUs, 4m40s max duration (incl. graceful stop):
           * subscribe: 1 iterations shared among 1 VUs (maxDuration: 4m0s, exec: subscribe, gracefulStop: 30s)
           * ingestion: 10 iterations for each of 40 VUs (maxDuration: 4m0s, exec: ingestion, startTime: 5s, gracefulStop: 30s)
           * consumption: 1 iterations shared among 1 VUs (maxDuration: 10s, exec: consumption, startTime: 4m0s, gracefulStop: 30s)

INFO[0141] Received 1000 messages                        source=console
INFO[0195] Received 2000 messages                        source=console
INFO[0195] Expected: 2000; Actual: 2000                  source=console
INFO[0321] Data Units stored: 2000                       source=console

running (5m24.7s), 00/42 VUs, 402 complete and 0 interrupted iterations
subscribe   ✓ [======================================] 1 VUs   1m54.5s/4m0s  1/1 shared iters
ingestion   ✓ [======================================] 40 VUs  1m49.5s/4m0s  400/400 iters, 10 per VU
consumption ✓ [======================================] 1 VUs   00.4s/10s     1/1 shared iters

     ✓ status was 202
     ✓ all data units were received
     ✓ correct data units were received
     ✓ data units were all stored

     █ setup

     █ teardown

     checks.........................: 100.00% ✓ 4002     ✗ 0   
     data_received..................: 946 kB  2.9 kB/s
     data_sent......................: 1.2 MB  3.8 kB/s
     http_req_blocked...............: avg=11.05ms  min=1.39µs   med=12.58µs  max=555.24ms p(90)=17.51µs  p(95)=20.95µs 
     http_req_connecting............: avg=3.94ms   min=0s       med=0s       max=188.27ms p(90)=0s       p(95)=0s      
     http_req_duration..............: avg=179.71ms min=169.68ms med=179.17ms max=240.23ms p(90)=183.9ms  p(95)=186.49ms
       { expected_response:true }...: avg=179.71ms min=169.68ms med=179.17ms max=240.23ms p(90)=183.9ms  p(95)=186.49ms
     http_req_failed................: 0.00%   ✓ 0        ✗ 2004
     http_req_receiving.............: avg=133.48µs min=16.94µs  med=151.1µs  max=666.2µs  p(90)=188.97µs p(95)=204.99µs
     http_req_sending...............: avg=84.81µs  min=10.13µs  med=94.1µs   max=302.66µs p(90)=124.61µs p(95)=142.27µs
     http_req_tls_handshaking.......: avg=7.09ms   min=0s       med=0s       max=367.39ms p(90)=0s       p(95)=0s      
     http_req_waiting...............: avg=179.49ms min=169.61ms med=178.93ms max=239.72ms p(90)=183.68ms p(95)=186.28ms
     http_reqs......................: 2004    6.172584/s
     iteration_duration.............: avg=10.78s   min=367.09ms med=10.9s    max=1m54s    p(90)=11.55s   p(95)=16.49s  
     iterations.....................: 402     1.238213/s
     time_lapse.....................: avg=207.8785 min=181      med=194      max=773      p(90)=213      p(95)=221.05  
     vus............................: 0       min=0      max=41
     vus_max........................: 42      min=42     max=42
     ws_connecting..................: avg=356.86ms min=356.86ms med=356.86ms max=356.86ms p(90)=356.86ms p(95)=356.86ms
     ws_msgs_received...............: 2001    6.163344/s
     ws_msgs_sent...................: 2       0.00616/s
     ws_session_duration............: avg=1m54s    min=1m54s    med=1m54s    max=1m54s    p(90)=1m54s    p(95)=1m54s   
     ws_sessions....................: 1       0.00308/s
```
