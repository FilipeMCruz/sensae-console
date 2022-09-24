# Results

```txt
          /\      |‾‾| /‾‾/   /‾‾/   
     /\  /  \     |  |/  /   /  /    
    /  \/    \    |     (   /   ‾‾\  
   /          \   |  |\  \ |  (‾)  | 
  / __________ \  |__| \__\ \_____/ .io

  execution: local
     script: test-suite-1.js
     output: csv (results/data.csv)

  scenarios: (100.00%) 3 scenarios, 102 max VUs, 4m40s max duration (incl. graceful stop):
           * subscribe: 1 iterations shared among 1 VUs (maxDuration: 4m0s, exec: subscribe, gracefulStop: 30s)
           * ingestion: 10 iterations for each of 100 VUs (maxDuration: 4m0s, exec: ingestion, startTime: 5s, gracefulStop: 30s)
           * consumption: 1 iterations shared among 1 VUs (maxDuration: 10s, exec: consumption, startTime: 4m0s, gracefulStop: 30s)

INFO[0254] Received 1000 messages                        source=console
INFO[0267] Received 2000 messages                        source=console
INFO[0276] Received 3000 messages                        source=console
INFO[0284] Received 4000 messages                        source=console
INFO[0292] Received 5000 messages                        source=console
INFO[0292] Expected: 5000; Actual: 5000                  source=console
INFO[0439] Data Units stored: 5000                       source=console

running (7m23.2s), 000/102 VUs, 1002 complete and 0 interrupted iterations
subscribe   ✓ [======================================] 1 VUs    1m33.6s/4m0s  1/1 shared iters
ingestion   ✓ [======================================] 100 VUs  0m40.6s/4m0s  1000/1000 iters, 10 per VU
consumption ✓ [======================================] 1 VUs    00.4s/10s     1/1 shared iters

     ✓ status was 202
     ✓ all data units were received
     ✓ correct data units were received
     ✓ data units were all stored

     █ setup

     █ teardown

     checks.........................: 100.00% ✓ 10002     ✗ 0    
     data_received..................: 2.4 MB  5.3 kB/s
     data_sent......................: 3.1 MB  7.0 kB/s
     http_req_blocked...............: avg=12.1ms     min=1.18µs   med=12.37µs  max=1.31s    p(90)=17.14µs  p(95)=20.04µs 
     http_req_connecting............: avg=4.21ms     min=0s       med=0s       max=320.19ms p(90)=0s       p(95)=0s      
     http_req_duration..............: avg=188.03ms   min=170.62ms med=182.23ms max=831.2ms  p(90)=202.12ms p(95)=214.48ms
       { expected_response:true }...: avg=188.03ms   min=170.62ms med=182.23ms max=831.2ms  p(90)=202.12ms p(95)=214.48ms
     http_req_failed................: 0.00%   ✓ 0         ✗ 5004 
     http_req_receiving.............: avg=121.34µs   min=9.81µs   med=135.9µs  max=1.74ms   p(90)=189.58µs p(95)=210.97µs
     http_req_sending...............: avg=74.53µs    min=8.52µs   med=87.19µs  max=1.18ms   p(90)=117.78µs p(95)=131.15µs
     http_req_tls_handshaking.......: avg=7.87ms     min=0s       med=0s       max=1.01s    p(90)=0s       p(95)=0s      
     http_req_waiting...............: avg=187.84ms   min=170.52ms med=182.01ms max=830.92ms p(90)=201.93ms p(95)=214.28ms
     http_reqs......................: 5004    11.29097/s
     iteration_duration.............: avg=4.15s      min=384.42ms med=3.93s    max=3m18s    p(90)=4.78s    p(95)=6.29s   
     iterations.....................: 1002    2.260902/s
     time_lapse.....................: avg=42903.8864 min=1775     med=47202    max=48928    p(90)=48153.1  p(95)=48342.1 
     vus............................: 0       min=0       max=101
     vus_max........................: 102     min=102     max=102
     ws_connecting..................: avg=4.14s      min=4.14s    med=4.14s    max=4.14s    p(90)=4.14s    p(95)=4.14s   
     ws_msgs_received...............: 5001    11.284201/s
     ws_msgs_sent...................: 2       0.004513/s
     ws_session_duration............: avg=1m33s      min=1m33s    med=1m33s    max=1m33s    p(90)=1m33s    p(95)=1m33s   
     ws_sessions....................: 1       0.002256/s
```
