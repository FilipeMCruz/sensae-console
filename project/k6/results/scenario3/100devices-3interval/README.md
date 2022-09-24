# Results

```txt          /\      |‾‾| /‾‾/   /‾‾/   
     /\  /  \     |  |/  /   /  /    
    /  \/    \    |     (   /   ‾‾\  
   /          \   |  |\  \ |  (‾)  | 
  / __________ \  |__| \__\ \_____/ .io

  execution: local
     script: test-suite-3-test.js
     output: csv (results/data.csv)

  scenarios: (100.00%) 3 scenarios, 22 max VUs, 4m40s max duration (incl. graceful stop):
           * subscribe: 1 iterations shared among 1 VUs (maxDuration: 4m0s, exec: subscribe, gracefulStop: 30s)
           * ingestion: 10 iterations for each of 20 VUs (maxDuration: 4m0s, exec: ingestion, startTime: 5s, gracefulStop: 30s)
           * consumption: 1 iterations shared among 1 VUs (maxDuration: 10s, exec: consumption, startTime: 4m0s, gracefulStop: 30s)

INFO[0089] Received 1000 messages                        source=console
INFO[0089] Expected: 1000; Actual: 1000                  source=console
INFO[0285] Data Units stored: 2000                       source=console

running (4m48.8s), 00/22 VUs, 202 complete and 0 interrupted iterations
subscribe   ✓ [======================================] 1 VUs   0m45.0s/4m0s  1/1 shared iters
ingestion   ✓ [======================================] 20 VUs  0m39.9s/4m0s  200/200 iters, 10 per VU
consumption ✓ [======================================] 1 VUs   00.4s/10s     1/1 shared iters

     ✓ status was 202
     ✓ all data units were received
     ✓ correct data units were received
     ✓ data units were all stored

     █ setup

     █ teardown

     checks.........................: 100.00% ✓ 2002     ✗ 0   
     data_received..................: 463 kB  1.6 kB/s
     data_sent......................: 631 kB  2.2 kB/s
     http_req_blocked...............: avg=11.26ms  min=1.55µs   med=12.65µs  max=562.13ms p(90)=17.91µs  p(95)=21.35µs 
     http_req_connecting............: avg=4.08ms   min=0s       med=0s       max=183.48ms p(90)=0s       p(95)=0s      
     http_req_duration..............: avg=180.74ms min=171.94ms med=179.55ms max=239.97ms p(90)=187.59ms p(95)=189.22ms
       { expected_response:true }...: avg=180.74ms min=171.94ms med=179.55ms max=239.97ms p(90)=187.59ms p(95)=189.22ms
     http_req_failed................: 0.00%   ✓ 0        ✗ 1003
     http_req_receiving.............: avg=150.39µs min=16.88µs  med=163.46µs max=4.16ms   p(90)=210.61µs p(95)=229.36µs
     http_req_sending...............: avg=87.61µs  min=11.13µs  med=96.65µs  max=343.62µs p(90)=128.78µs p(95)=144.81µs
     http_req_tls_handshaking.......: avg=7.16ms   min=0s       med=0s       max=382.34ms p(90)=0s       p(95)=0s      
     http_req_waiting...............: avg=180.5ms  min=171.71ms med=179.33ms max=239.47ms p(90)=187.35ms p(95)=188.94ms
     http_reqs......................: 1003    3.473249/s
     iteration_duration.............: avg=4.19s    min=397.09ms med=3.9s     max=44.97s   p(90)=4.66s    p(95)=6.19s   
     iterations.....................: 202     0.699498/s
     time_lapse.....................: avg=212.532  min=185      med=200      max=780      p(90)=214.1    p(95)=220     
     vus............................: 0       min=0      max=21
     vus_max........................: 22      min=22     max=22
     ws_connecting..................: avg=357.62ms min=357.62ms med=357.62ms max=357.62ms p(90)=357.62ms p(95)=357.62ms
     ws_msgs_received...............: 1001    3.466323/s
     ws_msgs_sent...................: 2       0.006926/s
     ws_session_duration............: avg=44.58s   min=44.58s   med=44.58s   max=44.58s   p(90)=44.58s   p(95)=44.58s  
     ws_sessions....................: 1       0.003463/s
```
