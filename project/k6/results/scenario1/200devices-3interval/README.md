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
subscribe   ✓ [======================================] 1 VUs   0m44.9s/4m0s  1/1 shared iters
ingestion   ✓ [======================================] 40 VUs  0m39.8s/4m0s  400/400 iters, 10 per VU
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
     http_req_blocked...............: avg=11.12ms  min=1.32µs   med=8.65µs   max=553.17ms p(90)=17.41µs  p(95)=20.93µs 
     http_req_connecting............: avg=3.94ms   min=0s       med=0s       max=192.82ms p(90)=0s       p(95)=0s      
     http_req_duration..............: avg=183.64ms min=171.5ms  med=179.92ms max=2.77s    p(90)=189.57ms p(95)=192.35ms
       { expected_response:true }...: avg=183.64ms min=171.5ms  med=179.92ms max=2.77s    p(90)=189.57ms p(95)=192.35ms
     http_req_failed................: 0.00%   ✓ 0        ✗ 2004
     http_req_receiving.............: avg=120.59µs min=14.64µs  med=121µs    max=10.11ms  p(90)=196.2µs  p(95)=220.21µs
     http_req_sending...............: avg=69.61µs  min=8.39µs   med=59.59µs  max=464.19µs p(90)=124.18µs p(95)=144.95µs
     http_req_tls_handshaking.......: avg=7.15ms   min=0s       med=0s       max=370.03ms p(90)=0s       p(95)=0s      
     http_req_waiting...............: avg=183.45ms min=171.36ms med=179.79ms max=2.76s    p(90)=189.37ms p(95)=192.23ms
     http_reqs......................: 2004    6.169005/s
     iteration_duration.............: avg=4.1s     min=414.72ms med=3.91s    max=1m19s    p(90)=4.54s    p(95)=6.07s   
     iterations.....................: 402     1.237495/s
     time_lapse.....................: avg=4357.996 min=187      med=2925.5   max=15695    p(90)=10457.9  p(95)=13100.75
     vus............................: 0       min=0      max=41
     vus_max........................: 42      min=42     max=42
     ws_connecting..................: avg=2.25s    min=2.25s    med=2.25s    max=2.25s    p(90)=2.25s    p(95)=2.25s   
     ws_msgs_received...............: 2001    6.15977/s
     ws_msgs_sent...................: 2       0.006157/s
     ws_session_duration............: avg=41.95s   min=41.95s   med=41.95s   max=41.95s   p(90)=41.95s   p(95)=41.95s  
     ws_sessions....................: 1       0.003078/s
```
