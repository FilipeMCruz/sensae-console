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

  scenarios: (100.00%) 3 scenarios, 102 max VUs, 4m40s max duration (incl. graceful stop):
           * subscribe: 1 iterations shared among 1 VUs (maxDuration: 4m0s, exec: subscribe, gracefulStop: 30s)
           * ingestion: 10 iterations for each of 100 VUs (maxDuration: 4m0s, exec: ingestion, startTime: 5s, gracefulStop: 30s)
           * consumption: 1 iterations shared among 1 VUs (maxDuration: 10s, exec: consumption, startTime: 4m0s, gracefulStop: 30s)

INFO[0216] Received 1000 messages                        source=console
INFO[0238] Received 2000 messages                        source=console
INFO[0259] Received 3000 messages                        source=console
INFO[0281] Received 4000 messages                        source=console
INFO[0303] Received 5000 messages                        source=console
INFO[0303] Expected: 5000; Actual: 5000                  source=console
INFO[0429] Data Units stored: 10000                      source=console

running (7m12.4s), 000/102 VUs, 1002 complete and 0 interrupted iterations
subscribe   ✓ [======================================] 1 VUs    1m54.9s/4m0s  1/1 shared iters
ingestion   ✓ [======================================] 100 VUs  1m49.7s/4m0s  1000/1000 iters, 10 per VU
consumption ✓ [======================================] 1 VUs    00.4s/10s     1/1 shared iters

     ✓ status was 202
     ✓ all data units were received
     ✓ correct data units were received
     ✓ data units were all stored

     █ setup

     █ teardown

     checks.........................: 100.00% ✓ 10002     ✗ 0    
     data_received..................: 2.3 MB  5.3 kB/s
     data_sent......................: 3.1 MB  7.3 kB/s
     http_req_blocked...............: avg=10.82ms  min=1.28µs   med=4.78µs   max=562.04ms p(90)=15.78µs  p(95)=18.1µs  
     http_req_connecting............: avg=3.68ms   min=0s       med=0s       max=201.81ms p(90)=0s       p(95)=0s      
     http_req_duration..............: avg=180.34ms min=170.18ms med=179.58ms max=258.12ms p(90)=186.56ms p(95)=189.14ms
       { expected_response:true }...: avg=180.34ms min=170.18ms med=179.58ms max=258.12ms p(90)=186.56ms p(95)=189.14ms
     http_req_failed................: 0.00%   ✓ 0         ✗ 5003 
     http_req_receiving.............: avg=96.41µs  min=10.43µs  med=70.25µs  max=4.67ms   p(90)=183.99µs p(95)=203.35µs
     http_req_sending...............: avg=55.64µs  min=8.23µs   med=35.22µs  max=676.86µs p(90)=112.24µs p(95)=125.81µs
     http_req_tls_handshaking.......: avg=7.11ms   min=0s       med=0s       max=375.51ms p(90)=0s       p(95)=0s      
     http_req_waiting...............: avg=180.19ms min=170.11ms med=179.45ms max=257.89ms p(90)=186.37ms p(95)=188.93ms
     http_reqs......................: 5003    11.570374/s
     iteration_duration.............: avg=10.72s   min=366.64ms med=10.9s    max=3m7s     p(90)=11.52s   p(95)=16.5s   
     iterations.....................: 1002    2.317313/s
     time_lapse.....................: avg=225.0958 min=184      med=209      max=921      p(90)=241      p(95)=257     
     vus............................: 0       min=0       max=101
     vus_max........................: 102     min=102     max=102
     ws_connecting..................: avg=369.07ms min=369.07ms med=369.07ms max=369.07ms p(90)=369.07ms p(95)=369.07ms
     ws_msgs_received...............: 5001    11.565749/s
     ws_msgs_sent...................: 2       0.004625/s
     ws_session_duration............: avg=1m54s    min=1m54s    med=1m54s    max=1m54s    p(90)=1m54s    p(95)=1m54s   
     ws_sessions....................: 1       0.002313/s
```
