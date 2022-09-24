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
subscribe   ✓ [======================================] 1 VUs   1m54.6s/4m0s  1/1 shared iters
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
     http_req_blocked...............: avg=10.97ms  min=1.26µs   med=3.18µs   max=549.98ms p(90)=9.2µs    p(95)=14.76µs 
     http_req_connecting............: avg=3.93ms   min=0s       med=0s       max=189.84ms p(90)=0s       p(95)=0s      
     http_req_duration..............: avg=179.89ms min=170.6ms  med=179.74ms max=243.21ms p(90)=183.29ms p(95)=185.94ms
       { expected_response:true }...: avg=179.89ms min=170.6ms  med=179.74ms max=243.21ms p(90)=183.29ms p(95)=185.94ms
     http_req_failed................: 0.00%   ✓ 0        ✗ 2004
     http_req_receiving.............: avg=71.75µs  min=15.31µs  med=50.4µs   max=9.73ms   p(90)=128.74µs p(95)=182.61µs
     http_req_sending...............: avg=29.42µs  min=8.06µs   med=22.33µs  max=220.12µs p(90)=53.54µs  p(95)=72.71µs 
     http_req_tls_handshaking.......: avg=7.04ms   min=0s       med=0s       max=365.88ms p(90)=0s       p(95)=0s      
     http_req_waiting...............: avg=179.79ms min=170.51ms med=179.66ms max=243.1ms  p(90)=183.17ms p(95)=185.88ms
     http_reqs......................: 2004    6.174799/s
     iteration_duration.............: avg=10.86s   min=366.54ms med=10.89s   max=1m54s    p(90)=11.89s   p(95)=17.26s  
     iterations.....................: 402     1.238657/s
     time_lapse.....................: avg=210.7515 min=182      med=199      max=772      p(90)=220      p(95)=233.05  
     vus............................: 0       min=0      max=41
     vus_max........................: 42      min=42     max=42
     ws_connecting..................: avg=363.34ms min=363.34ms med=363.34ms max=363.34ms p(90)=363.34ms p(95)=363.34ms
     ws_msgs_received...............: 2001    6.165555/s
     ws_msgs_sent...................: 2       0.006162/s
     ws_session_duration............: avg=1m54s    min=1m54s    med=1m54s    max=1m54s    p(90)=1m54s    p(95)=1m54s   
     ws_sessions....................: 1       0.003081/s
```
