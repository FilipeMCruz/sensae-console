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

  scenarios: (100.00%) 3 scenarios, 42 max VUs, 4m40s max duration (incl. graceful stop):
           * subscribe: 1 iterations shared among 1 VUs (maxDuration: 4m0s, exec: subscribe, gracefulStop: 30s)
           * ingestion: 10 iterations for each of 40 VUs (maxDuration: 4m0s, exec: ingestion, startTime: 5s, gracefulStop: 30s)
           * consumption: 1 iterations shared among 1 VUs (maxDuration: 10s, exec: consumption, startTime: 4m0s, gracefulStop: 30s)

INFO[0105] Received 1000 messages                        source=console
INFO[0125] Received 2000 messages                        source=console
INFO[0125] Expected: 2000; Actual: 2000                  source=console
INFO[0321] Data Units stored: 4000                       source=console

running (5m24.4s), 00/42 VUs, 402 complete and 0 interrupted iterations
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
     data_received..................: 924 kB  2.8 kB/s
     data_sent......................: 1.3 MB  3.9 kB/s
     http_req_blocked...............: avg=10.93ms  min=1.48µs   med=7.33µs   max=561.98ms p(90)=16.9µs   p(95)=19.5µs  
     http_req_connecting............: avg=3.82ms   min=0s       med=0s       max=187.09ms p(90)=0s       p(95)=0s      
     http_req_duration..............: avg=180.71ms min=170.07ms med=179.69ms max=240.75ms p(90)=187.38ms p(95)=189.68ms
       { expected_response:true }...: avg=180.71ms min=170.07ms med=179.69ms max=240.75ms p(90)=187.38ms p(95)=189.68ms
     http_req_failed................: 0.00%   ✓ 0        ✗ 2003
     http_req_receiving.............: avg=110.95µs min=13.11µs  med=99.71µs  max=3.24ms   p(90)=188.31µs p(95)=207.64µs
     http_req_sending...............: avg=66.02µs  min=9.6µs    med=51.31µs  max=708.43µs p(90)=120.07µs p(95)=139.24µs
     http_req_tls_handshaking.......: avg=7.1ms    min=0s       med=0s       max=378.11ms p(90)=0s       p(95)=0s      
     http_req_waiting...............: avg=180.53ms min=170ms    med=179.53ms max=240.56ms p(90)=187.12ms p(95)=189.42ms
     http_reqs......................: 2003    6.175108/s
     iteration_duration.............: avg=4.09s    min=376.33ms med=3.9s     max=1m19s    p(90)=4.56s    p(95)=6.05s   
     iterations.....................: 402     1.239338/s
     time_lapse.....................: avg=213.552  min=180      med=200      max=789      p(90)=224      p(95)=235     
     vus............................: 0       min=0      max=41
     vus_max........................: 42      min=42     max=42
     ws_connecting..................: avg=351.01ms min=351.01ms med=351.01ms max=351.01ms p(90)=351.01ms p(95)=351.01ms
     ws_msgs_received...............: 2001    6.168942/s
     ws_msgs_sent...................: 2       0.006166/s
     ws_session_duration............: avg=44.49s   min=44.49s   med=44.49s   max=44.49s   p(90)=44.49s   p(95)=44.49s  
     ws_sessions....................: 1       0.003083/s
```
