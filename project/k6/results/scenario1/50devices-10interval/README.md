# Results

```txt
          /\      |‾‾| /‾‾/   /‾‾/   
     /\  /  \     |  |/  /   /  /    
    /  \/    \    |     (   /   ‾‾\  
   /          \   |  |\  \ |  (‾)  | 
  / __________ \  |__| \__\ \_____/ .io

  execution: local
     script: test-suite-1-test.js
     output: csv (results/data.csv)

  scenarios: (100.00%) 3 scenarios, 12 max VUs, 4m40s max duration (incl. graceful stop):
           * subscribe: 1 iterations shared among 1 VUs (maxDuration: 4m0s, exec: subscribe, gracefulStop: 30s)
           * ingestion: 10 iterations for each of 10 VUs (maxDuration: 4m0s, exec: ingestion, startTime: 5s, gracefulStop: 30s)
           * consumption: 1 iterations shared among 1 VUs (maxDuration: 10s, exec: consumption, startTime: 4m0s, gracefulStop: 30s)

INFO[0140] Expected: 500; Actual: 500                    source=console
INFO[0266] Data Units stored: 500                        source=console

running (4m30.3s), 00/12 VUs, 102 complete and 0 interrupted iterations
subscribe   ✓ [======================================] 1 VUs   1m54.3s/4m0s  1/1 shared iters
ingestion   ✓ [======================================] 10 VUs  1m49.3s/4m0s  100/100 iters, 10 per VU
consumption ✓ [======================================] 1 VUs   00.4s/10s     1/1 shared iters

     ✓ status was 202
     ✓ all data units were received
     ✓ correct data units were received
     ✓ data units were all stored

     █ setup

     █ teardown

     checks.........................: 100.00% ✓ 1002     ✗ 0   
     data_received..................: 238 kB  881 B/s
     data_sent......................: 314 kB  1.2 kB/s
     http_req_blocked...............: avg=11.87ms  min=1.4µs    med=12.51µs  max=531.61ms p(90)=18.14µs  p(95)=22.86µs 
     http_req_connecting............: avg=4.91ms   min=0s       med=0s       max=180.94ms p(90)=0s       p(95)=0s      
     http_req_duration..............: avg=179.37ms min=172.91ms med=179.03ms max=230.28ms p(90)=181.19ms p(95)=183.38ms
       { expected_response:true }...: avg=179.37ms min=172.91ms med=179.03ms max=230.28ms p(90)=181.19ms p(95)=183.38ms
     http_req_failed................: 0.00%   ✓ 0        ✗ 504 
     http_req_receiving.............: avg=125.12µs min=18.01µs  med=151.61µs max=398.73µs p(90)=188.4µs  p(95)=208.76µs
     http_req_sending...............: avg=76.92µs  min=9.48µs   med=93µs     max=464.05µs p(90)=127.81µs p(95)=141.58µs
     http_req_tls_handshaking.......: avg=6.93ms   min=0s       med=0s       max=354.1ms  p(90)=0s       p(95)=0s      
     http_req_waiting...............: avg=179.17ms min=172.85ms med=178.78ms max=229.8ms  p(90)=181.03ms p(95)=183.3ms 
     http_reqs......................: 504     1.864413/s
     iteration_duration.............: avg=11.48s   min=357.19ms med=10.89s   max=1m54s    p(90)=14.01s   p(95)=18.11s  
     iterations.....................: 102     0.377322/s
     time_lapse.....................: avg=204.848  min=183      med=190      max=755      p(90)=206      p(95)=210.05  
     vus............................: 0       min=0      max=11
     vus_max........................: 12      min=12     max=12
     ws_connecting..................: avg=359.53ms min=359.53ms med=359.53ms max=359.53ms p(90)=359.53ms p(95)=359.53ms
     ws_msgs_received...............: 501     1.853315/s
     ws_msgs_sent...................: 2       0.007398/s
     ws_session_duration............: avg=1m53s    min=1m53s    med=1m53s    max=1m53s    p(90)=1m53s    p(95)=1m53s   
     ws_sessions....................: 1       0.003699/s
```
