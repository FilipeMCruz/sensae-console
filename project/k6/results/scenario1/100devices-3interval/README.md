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

  scenarios: (100.00%) 3 scenarios, 22 max VUs, 4m40s max duration (incl. graceful stop):
           * subscribe: 1 iterations shared among 1 VUs (maxDuration: 4m0s, exec: subscribe, gracefulStop: 30s)
           * ingestion: 10 iterations for each of 20 VUs (maxDuration: 4m0s, exec: ingestion, startTime: 5s, gracefulStop: 30s)
           * consumption: 1 iterations shared among 1 VUs (maxDuration: 10s, exec: consumption, startTime: 4m0s, gracefulStop: 30s)

INFO[0089] Received 1000 messages                        source=console
INFO[0089] Expected: 1000; Actual: 1000                  source=console
INFO[0285] Data Units stored: 1000                       source=console

running (4m48.8s), 00/22 VUs, 202 complete and 0 interrupted iterations
subscribe   ✓ [======================================] 1 VUs   0m44.6s/4m0s  1/1 shared iters
ingestion   ✓ [======================================] 20 VUs  0m39.5s/4m0s  200/200 iters, 10 per VU
consumption ✓ [======================================] 1 VUs   00.4s/10s     1/1 shared iters

     ✓ status was 202
     ✓ all data units were received
     ✓ correct data units were received
     ✓ data units were all stored

     █ setup

     █ teardown

     checks.........................: 100.00% ✓ 2002     ✗ 0   
     data_received..................: 474 kB  1.6 kB/s
     data_sent......................: 624 kB  2.2 kB/s
     http_req_blocked...............: avg=11.32ms  min=1.58µs   med=12.48µs  max=545.45ms p(90)=17.01µs  p(95)=20.57µs 
     http_req_connecting............: avg=4.23ms   min=0s       med=0s       max=184.56ms p(90)=0s       p(95)=0s      
     http_req_duration..............: avg=179.59ms min=170.29ms med=179.05ms max=230.15ms p(90)=185.04ms p(95)=188.2ms 
       { expected_response:true }...: avg=179.59ms min=170.29ms med=179.05ms max=230.15ms p(90)=185.04ms p(95)=188.2ms 
     http_req_failed................: 0.00%   ✓ 0        ✗ 1004
     http_req_receiving.............: avg=144.24µs min=17.46µs  med=147.72µs max=11.12ms  p(90)=183.88µs p(95)=199.66µs
     http_req_sending...............: avg=84.05µs  min=9.75µs   med=93.16µs  max=660.43µs p(90)=121.62µs p(95)=137.47µs
     http_req_tls_handshaking.......: avg=7.07ms   min=0s       med=0s       max=362.99ms p(90)=0s       p(95)=0s      
     http_req_waiting...............: avg=179.36ms min=170.02ms med=178.82ms max=230.02ms p(90)=184.82ms p(95)=187.8ms 
     http_reqs......................: 1004    3.47675/s
     iteration_duration.............: avg=4.17s    min=376.22ms med=3.9s     max=44.56s   p(90)=4.87s    p(95)=6s      
     iterations.....................: 202     0.699505/s
     time_lapse.....................: avg=220.557  min=182      med=205      max=767      p(90)=242      p(95)=254.15  
     vus............................: 0       min=0      max=21
     vus_max........................: 22      min=22     max=22
     ws_connecting..................: avg=362.35ms min=362.35ms med=362.35ms max=362.35ms p(90)=362.35ms p(95)=362.35ms
     ws_msgs_received...............: 1001    3.466361/s
     ws_msgs_sent...................: 2       0.006926/s
     ws_session_duration............: avg=44.16s   min=44.16s   med=44.16s   max=44.16s   p(90)=44.16s   p(95)=44.16s  
     ws_sessions....................: 1       0.003463/s
```
