# Results

``` txt
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

INFO[0188] Waiting for 3 minutes for rules to apply      source=console
INFO[0609] Notifications stored: 492                     source=console

running (10m43.0s), 000/102 VUs, 1001 complete and 1 interrupted iterations
subscribe   ✗ [--------------------------------------] 1 VUs    4m30.0s/4m0s  0/1 shared iters
ingestion   ✓ [======================================] 100 VUs  1m49.5s/4m0s  1000/1000 iters, 10 per VU
consumption ✓ [======================================] 1 VUs    00.9s/10s     1/1 shared iters

     ✓ status was 202
     ✓ notifications were all stored

     █ setup

     █ teardown

     checks.........................: 100.00% ✓ 5001     ✗ 0    
     data_received..................: 1.3 MB  2.0 kB/s
     data_sent......................: 3.1 MB  4.8 kB/s
     http_req_blocked...............: avg=10.65ms    min=1.54µs   med=12.97µs  max=564.52ms p(90)=17.67µs  p(95)=21.12µs 
     http_req_connecting............: avg=3.56ms     min=0s       med=0s       max=195.68ms p(90)=0s       p(95)=0s      
     http_req_duration..............: avg=179.83ms   min=167.09ms med=178.99ms max=2.6s     p(90)=186.36ms p(95)=189.27ms
       { expected_response:true }...: avg=179.83ms   min=167.09ms med=178.99ms max=2.6s     p(90)=186.36ms p(95)=189.27ms
     http_req_failed................: 0.00%   ✓ 0        ✗ 5001 
     http_req_receiving.............: avg=142.8µs    min=17.73µs  med=154.81µs max=455.5µs  p(90)=194.3µs  p(95)=211.69µs
     http_req_sending...............: avg=89.68µs    min=10.39µs  med=95.95µs  max=484.57µs p(90)=129.04µs p(95)=146.36µs
     http_req_tls_handshaking.......: avg=7.06ms     min=0s       med=0s       max=387.37ms p(90)=0s       p(95)=0s      
     http_req_waiting...............: avg=179.6ms    min=166.96ms med=178.74ms max=2.6s     p(90)=186.15ms p(95)=188.99ms
     http_reqs......................: 5001    7.777019/s
     iteration_duration.............: avg=10.79s     min=867.34ms med=10.9s    max=6m6s     p(90)=11.36s   p(95)=16.27s  
     iterations.....................: 1001    1.556648/s
     time_lapse.....................: avg=434.02439  min=199      med=235      max=4029     p(90)=286.6    p(95)=2363.5  
     time_lapse_alert...............: avg=187.711382 min=100      med=132      max=2456     p(90)=161.7    p(95)=366.45  
     vus............................: 0       min=0      max=101
     vus_max........................: 102     min=102    max=102
     ws_connecting..................: avg=1.94s      min=1.94s    med=1.94s    max=1.94s    p(90)=1.94s    p(95)=1.94s   
     ws_msgs_received...............: 493     0.766661/s
     ws_msgs_sent...................: 2       0.00311/s
     ws_sessions....................: 1       0.001555/s
```
