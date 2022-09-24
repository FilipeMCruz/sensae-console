# Results

```txt
          /\      |‾‾| /‾‾/   /‾‾/   
     /\  /  \     |  |/  /   /  /    
    /  \/    \    |     (   /   ‾‾\  
   /          \   |  |\  \ |  (‾)  | 
  / __________ \  |__| \__\ \_____/ .io

  execution: local
     script: test-suite-2-test.js
     output: csv (results/data.csv)

  scenarios: (100.00%) 3 scenarios, 102 max VUs, 4m40s max duration (incl. graceful stop):
           * subscribe: 1 iterations shared among 1 VUs (maxDuration: 4m0s, exec: subscribe, gracefulStop: 30s)
           * ingestion: 10 iterations for each of 100 VUs (maxDuration: 4m0s, exec: ingestion, startTime: 5s, gracefulStop: 30s)
           * consumption: 1 iterations shared among 1 VUs (maxDuration: 10s, exec: consumption, startTime: 4m0s, gracefulStop: 30s)

INFO[0200] Waiting for 3 minutes for rules to apply      source=console
INFO[0621] Notifications stored: 516                     source=console

running (10m55.3s), 000/102 VUs, 1001 complete and 1 interrupted iterations
subscribe   ✗ [--------------------------------------] 1 VUs    4m30.0s/4m0s  0/1 shared iters
ingestion   ✓ [======================================] 100 VUs  0m40.4s/4m0s  1000/1000 iters, 10 per VU
consumption ✓ [======================================] 1 VUs    00.9s/10s     1/1 shared iters

     ✓ status was 202
     ✓ notifications were all stored

     █ setup

     █ teardown

     checks.........................: 100.00% ✓ 5001     ✗ 0    
     data_received..................: 1.3 MB  2.0 kB/s
     data_sent......................: 3.1 MB  4.7 kB/s
     http_req_blocked...............: avg=10.95ms      min=1.05µs   med=2.51µs   max=633.2ms  p(90)=5.87µs   p(95)=9.36µs  
     http_req_connecting............: avg=3.68ms       min=0s       med=0s       max=248.1ms  p(90)=0s       p(95)=0s      
     http_req_duration..............: avg=200.28ms     min=169.98ms med=183.78ms max=2.89s    p(90)=248.18ms p(95)=297.88ms
       { expected_response:true }...: avg=200.28ms     min=169.98ms med=183.78ms max=2.89s    p(90)=248.18ms p(95)=297.88ms
     http_req_failed................: 0.00%   ✓ 0        ✗ 5001 
     http_req_receiving.............: avg=42.55µs      min=10.74µs  med=34.15µs  max=367.84µs p(90)=80.11µs  p(95)=98.75µs 
     http_req_sending...............: avg=20.48µs      min=6.15µs   med=16.04µs  max=278.62µs p(90)=33.96µs  p(95)=47.35µs 
     http_req_tls_handshaking.......: avg=7.26ms       min=0s       med=0s       max=450.09ms p(90)=0s       p(95)=0s      
     http_req_waiting...............: avg=200.22ms     min=169.89ms med=183.73ms max=2.89s    p(90)=248.15ms p(95)=297.84ms
     http_reqs......................: 5001    7.63136/s
     iteration_duration.............: avg=4.27s        min=898.11ms med=3.92s    max=6m18s    p(90)=4.74s    p(95)=6.21s   
     iterations.....................: 1001    1.527493/s
     time_lapse.....................: avg=10019.056202 min=1188     med=11094    max=19080    p(90)=14004.5  p(95)=15025.75
     time_lapse_alert...............: avg=355.767442   min=144      med=300.5    max=2688     p(90)=461.5    p(95)=641.75  
     vus............................: 0       min=0      max=101
     vus_max........................: 102     min=102    max=102
     ws_connecting..................: avg=2.25s        min=2.25s    med=2.25s    max=2.25s    p(90)=2.25s    p(95)=2.25s   
     ws_msgs_received...............: 517     0.788925/s
     ws_msgs_sent...................: 2       0.003052/s
     ws_sessions....................: 1       0.001526/s
```
