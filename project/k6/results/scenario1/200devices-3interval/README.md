# Result

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

INFO[0106] Received 1000 messages                        source=console
INFO[0125] Received 2000 messages                        source=console
INFO[0125] Expected: 2000; Actual: 2000                  source=console
INFO[0321] Data Units stored: 2000                       source=console

running (5m24.6s), 00/42 VUs, 402 complete and 0 interrupted iterations
subscribe   ✓ [======================================] 1 VUs   0m45.0s/4m0s  1/1 shared iters
ingestion   ✓ [======================================] 40 VUs  0m39.5s/4m0s  400/400 iters, 10 per VU
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
     http_req_blocked...............: avg=10.99ms  min=1.37µs   med=5.67µs   max=552.91ms p(90)=16.22µs  p(95)=19.26µs 
     http_req_connecting............: avg=3.89ms   min=0s       med=0s       max=184.87ms p(90)=0s       p(95)=0s      
     http_req_duration..............: avg=180.8ms  min=170.09ms med=179.28ms max=370.71ms p(90)=185.81ms p(95)=189.25ms
       { expected_response:true }...: avg=180.8ms  min=170.09ms med=179.28ms max=370.71ms p(90)=185.81ms p(95)=189.25ms
     http_req_failed................: 0.00%   ✓ 0        ✗ 2004
     http_req_receiving.............: avg=96.58µs  min=12.67µs  med=76.81µs  max=1.82ms   p(90)=174.79µs p(95)=195.04µs
     http_req_sending...............: avg=58.98µs  min=9.12µs   med=39.4µs   max=691.03µs p(90)=111.59µs p(95)=125.41µs
     http_req_tls_handshaking.......: avg=7.08ms   min=0s       med=0s       max=372.36ms p(90)=0s       p(95)=0s      
     http_req_waiting...............: avg=180.65ms min=169.91ms med=179.15ms max=370.46ms p(90)=185.69ms p(95)=189.05ms
     http_reqs......................: 2004    6.173321/s
     iteration_duration.............: avg=4.1s     min=376.1ms  med=3.9s     max=1m19s    p(90)=4.51s    p(95)=6.12s   
     iterations.....................: 402     1.238361/s
     time_lapse.....................: avg=700.2375 min=186      med=714.5    max=1497     p(90)=1144.1   p(95)=1284    
     vus............................: 0       min=0      max=41
     vus_max........................: 42      min=42     max=42
     ws_connecting..................: avg=367.43ms min=367.43ms med=367.43ms max=367.43ms p(90)=367.43ms p(95)=367.43ms
     ws_msgs_received...............: 2001    6.16408/s
     ws_msgs_sent...................: 2       0.006161/s
     ws_session_duration............: avg=44.66s   min=44.66s   med=44.66s   max=44.66s   p(90)=44.66s   p(95)=44.66s  
     ws_sessions....................: 1       0.00308/s
```
