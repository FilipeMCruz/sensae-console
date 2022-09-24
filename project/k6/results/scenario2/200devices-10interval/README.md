# Results

````txt
          /\      |‾‾| /‾‾/   /‾‾/   
     /\  /  \     |  |/  /   /  /    
    /  \/    \    |     (   /   ‾‾\  
   /          \   |  |\  \ |  (‾)  | 
  / __________ \  |__| \__\ \_____/ .io

  execution: local
     script: test-suite-2-test.js
     output: csv (results/data.csv)

  scenarios: (100.00%) 3 scenarios, 42 max VUs, 4m40s max duration (incl. graceful stop):
           * subscribe: 1 iterations shared among 1 VUs (maxDuration: 4m0s, exec: subscribe, gracefulStop: 30s)
           * ingestion: 10 iterations for each of 40 VUs (maxDuration: 4m0s, exec: ingestion, startTime: 5s, gracefulStop: 30s)
           * consumption: 1 iterations shared among 1 VUs (maxDuration: 10s, exec: consumption, startTime: 4m0s, gracefulStop: 30s)

INFO[0082] Waiting for 4 minutes for rules to apply      source=console
INFO[0562] Notifications stored: 198                     source=console

running (9m57.0s), 00/42 VUs, 401 complete and 1 interrupted iterations
subscribe   ✗ [--------------------------------------] 1 VUs   4m30.0s/4m0s  0/1 shared iters
ingestion   ✓ [======================================] 40 VUs  1m49.7s/4m0s  400/400 iters, 10 per VU
consumption ✓ [======================================] 1 VUs   00.9s/10s     1/1 shared iters

     ✓ status was 202
     ✓ notifications were all stored

     █ setup

     █ teardown

     checks.........................: 100.00% ✓ 2001     ✗ 0   
     data_received..................: 514 kB  861 B/s
     data_sent......................: 1.2 MB  2.1 kB/s
     http_req_blocked...............: avg=10.72ms    min=1.7µs    med=13.42µs  max=569.82ms p(90)=18.18µs  p(95)=21.58µs 
     http_req_connecting............: avg=3.62ms     min=0s       med=0s       max=188.89ms p(90)=0s       p(95)=0s      
     http_req_duration..............: avg=181.16ms   min=168ms    med=179.05ms max=2.6s     p(90)=185.32ms p(95)=190.11ms
       { expected_response:true }...: avg=181.16ms   min=168ms    med=179.05ms max=2.6s     p(90)=185.32ms p(95)=190.11ms
     http_req_failed................: 0.00%   ✓ 0        ✗ 2001
     http_req_receiving.............: avg=159.39µs   min=18.36µs  med=166.31µs max=371.65µs p(90)=207.16µs p(95)=222.71µs
     http_req_sending...............: avg=100.33µs   min=12.78µs  med=98.75µs  max=1.17ms   p(90)=131.39µs p(95)=146.04µs
     http_req_tls_handshaking.......: avg=7.07ms     min=0s       med=0s       max=384.97ms p(90)=0s       p(95)=0s      
     http_req_waiting...............: avg=180.9ms    min=167.8ms  med=178.79ms max=2.6s     p(90)=185.03ms p(95)=189.94ms
     http_reqs......................: 2001    3.351761/s
     iteration_duration.............: avg=11.14s     min=876.05ms med=10.9s    max=5m20s    p(90)=11.42s   p(95)=15.42s  
     iterations.....................: 401     0.671692/s
     time_lapse.....................: avg=263        min=196      med=223      max=1735     p(90)=245.3    p(95)=265.55  
     time_lapse_alert...............: avg=140.525253 min=96       med=117      max=916      p(90)=132      p(95)=144.75  
     vus............................: 0       min=0      max=41
     vus_max........................: 42      min=42     max=42
     ws_connecting..................: avg=1.87s      min=1.87s    med=1.87s    max=1.87s    p(90)=1.87s    p(95)=1.87s   
     ws_msgs_received...............: 199     0.333334/s
     ws_msgs_sent...................: 2       0.00335/s
     ws_sessions....................: 1       0.001675/s
```
