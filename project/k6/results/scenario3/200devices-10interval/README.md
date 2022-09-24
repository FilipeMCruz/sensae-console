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

INFO[0140] Received 1000 messages                        source=console
INFO[0194] Received 2000 messages                        source=console
INFO[0194] Expected: 2000; Actual: 2000                  source=console
INFO[0321] Data Units stored: 4000                       source=console

running (5m24.4s), 00/42 VUs, 402 complete and 0 interrupted iterations
subscribe   ✓ [======================================] 1 VUs   1m54.2s/4m0s  1/1 shared iters
ingestion   ✓ [======================================] 40 VUs  1m49.0s/4m0s  400/400 iters, 10 per VU
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
     http_req_blocked...............: avg=10.99ms  min=1.66µs   med=12.66µs  max=558.67ms p(90)=17.87µs  p(95)=21.44µs 
     http_req_connecting............: avg=3.84ms   min=0s       med=0s       max=187.35ms p(90)=0s       p(95)=0s      
     http_req_duration..............: avg=181ms    min=171.01ms med=179.86ms max=220.1ms  p(90)=187.57ms p(95)=189.1ms 
       { expected_response:true }...: avg=181ms    min=171.01ms med=179.86ms max=220.1ms  p(90)=187.57ms p(95)=189.1ms 
     http_req_failed................: 0.00%   ✓ 0        ✗ 2003
     http_req_receiving.............: avg=149.39µs min=17.43µs  med=160.6µs  max=7.88ms   p(90)=214.67µs p(95)=234.41µs
     http_req_sending...............: avg=86.73µs  min=11.4µs   med=95.38µs  max=448.44µs p(90)=127.91µs p(95)=146.47µs
     http_req_tls_handshaking.......: avg=7.12ms   min=0s       med=0s       max=376.89ms p(90)=0s       p(95)=0s      
     http_req_waiting...............: avg=180.77ms min=170.79ms med=179.64ms max=219.62ms p(90)=187.34ms p(95)=188.84ms
     http_reqs......................: 2003    6.173568/s
     iteration_duration.............: avg=10.81s   min=357.39ms med=10.91s   max=1m54s    p(90)=11.82s   p(95)=15.85s  
     iterations.....................: 402     1.239029/s
     time_lapse.....................: avg=207.3235 min=182      med=193      max=770      p(90)=211      p(95)=220     
     vus............................: 0       min=0      max=41
     vus_max........................: 42      min=42     max=42
     ws_connecting..................: avg=359.21ms min=359.21ms med=359.21ms max=359.21ms p(90)=359.21ms p(95)=359.21ms
     ws_msgs_received...............: 2001    6.167404/s
     ws_msgs_sent...................: 2       0.006164/s
     ws_session_duration............: avg=1m53s    min=1m53s    med=1m53s    max=1m53s    p(90)=1m53s    p(95)=1m53s   
     ws_sessions....................: 1       0.003082/s
```
