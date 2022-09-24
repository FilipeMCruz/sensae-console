# Result

````txt
          /\      |‾‾| /‾‾/   /‾‾/   
     /\  /  \     |  |/  /   /  /    
    /  \/    \    |     (   /   ‾‾\  
   /          \   |  |\  \ |  (‾)  | 
  / __________ \  |__| \__\ \_____/ .io

  execution: local
     script: test-suite-2-test.js
     output: csv (results/data.csv)

  scenarios: (100.00%) 3 scenarios, 202 max VUs, 4m40s max duration (incl. graceful stop):
           * subscribe: 1 iterations shared among 1 VUs (maxDuration: 4m0s, exec: subscribe, gracefulStop: 30s)
           * ingestion: 10 iterations for each of 200 VUs (maxDuration: 4m0s, exec: ingestion, startTime: 5s, gracefulStop: 30s)
           * consumption: 1 iterations shared among 1 VUs (maxDuration: 10s, exec: consumption, startTime: 4m0s, gracefulStop: 30s)

INFO[0372] Waiting for 7 minutes for rules to apply      source=console
INFO[1033] Notifications stored: 1011                    source=console

running (17m47.3s), 000/202 VUs, 2001 complete and 1 interrupted iterations
subscribe   ✗ [--------------------------------------] 1 VUs    4m30.0s/4m0s  0/1 shared iters
ingestion   ✓ [======================================] 200 VUs  1m49.7s/4m0s  2000/2000 iters, 10 per VU
consumption ✓ [======================================] 1 VUs    00.9s/10s     1/1 shared iters

     ✓ status was 202
     ✓ notifications were all stored

     █ setup

     █ teardown

     checks.........................: 100.00% ✓ 10001    ✗ 0    
     data_received..................: 2.6 MB  2.4 kB/s
     data_sent......................: 6.2 MB  5.8 kB/s
     http_req_blocked...............: avg=10.7ms      min=1.07µs   med=5.72µs   max=561.65ms p(90)=16.5µs   p(95)=19.05µs 
     http_req_connecting............: avg=3.57ms      min=0s       med=0s       max=201.12ms p(90)=0s       p(95)=0s      
     http_req_duration..............: avg=182.12ms    min=168.58ms med=179.62ms max=2.59s    p(90)=186.58ms p(95)=189.23ms
       { expected_response:true }...: avg=182.12ms    min=168.58ms med=179.62ms max=2.59s    p(90)=186.58ms p(95)=189.23ms
     http_req_failed................: 0.00%   ✓ 0        ✗ 10001
     http_req_receiving.............: avg=99.39µs     min=11.53µs  med=84.72µs  max=760.27µs p(90)=180.83µs p(95)=198.39µs
     http_req_sending...............: avg=58.03µs     min=7.58µs   med=39.95µs  max=2.3ms    p(90)=113.24µs p(95)=127.97µs
     http_req_tls_handshaking.......: avg=7.12ms      min=0s       med=0s       max=369.98ms p(90)=0s       p(95)=0s      
     http_req_waiting...............: avg=181.97ms    min=168.4ms  med=179.48ms max=2.59s    p(90)=186.44ms p(95)=189ms   
     http_reqs......................: 10001   9.370475/s
     iteration_duration.............: avg=10.83s      min=871.66ms med=10.91s   max=13m10s   p(90)=11.59s   p(95)=16.31s  
     iterations.....................: 2001    1.874844/s
     time_lapse.....................: avg=1678.274975 min=195      med=241      max=11951    p(90)=6885     p(95)=9727.5  
     time_lapse_alert...............: avg=164.037587  min=79       med=113      max=1941     p(90)=318      p(95)=401     
     vus............................: 0       min=0      max=201
     vus_max........................: 202     min=202    max=202
     ws_connecting..................: avg=2.1s        min=2.1s     med=2.1s     max=2.1s     p(90)=2.1s     p(95)=2.1s    
     ws_msgs_received...............: 1012    0.948197/s
     ws_msgs_sent...................: 2       0.001874/s
     ws_sessions....................: 1       0.000937/s
```
