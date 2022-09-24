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

  scenarios: (100.00%) 3 scenarios, 12 max VUs, 4m40s max duration (incl. graceful stop):
           * subscribe: 1 iterations shared among 1 VUs (maxDuration: 4m0s, exec: subscribe, gracefulStop: 30s)
           * ingestion: 10 iterations for each of 10 VUs (maxDuration: 4m0s, exec: ingestion, startTime: 5s, gracefulStop: 30s)
           * consumption: 1 iterations shared among 1 VUs (maxDuration: 10s, exec: consumption, startTime: 4m0s, gracefulStop: 30s)

INFO[0028] Waiting for 3 minutes for rules to apply      source=console
INFO[0449] Notifications stored: 47                      source=console

running (8m04.2s), 00/12 VUs, 101 complete and 1 interrupted iterations
subscribe   ✗ [--------------------------------------] 1 VUs   4m30.0s/4m0s  0/1 shared iters
ingestion   ✓ [======================================] 10 VUs  1m49.8s/4m0s  100/100 iters, 10 per VU
consumption ✓ [======================================] 1 VUs   00.9s/10s     1/1 shared iters

     ✓ status was 202
     ✓ notifications were all stored

     █ setup

     █ teardown

     checks.........................: 100.00% ✓ 501      ✗ 0   
     data_received..................: 129 kB  266 B/s
     data_sent......................: 310 kB  640 B/s
     http_req_blocked...............: avg=11.23ms    min=2µs      med=13.25µs  max=561.26ms p(90)=18.55µs  p(95)=23.53µs 
     http_req_connecting............: avg=3.98ms     min=0s       med=0s       max=191.13ms p(90)=0s       p(95)=0s      
     http_req_duration..............: avg=187.29ms   min=174.22ms med=180.21ms max=2.72s    p(90)=188.87ms p(95)=192.94ms
       { expected_response:true }...: avg=187.29ms   min=174.22ms med=180.21ms max=2.72s    p(90)=188.87ms p(95)=192.94ms
     http_req_failed................: 0.00%   ✓ 0        ✗ 501 
     http_req_receiving.............: avg=172.51µs   min=33.36µs  med=169.36µs max=482.79µs p(90)=222.35µs p(95)=251.24µs
     http_req_sending...............: avg=103.18µs   min=16.24µs  med=98.88µs  max=349.54µs p(90)=130.64µs p(95)=144.65µs
     http_req_tls_handshaking.......: avg=7.2ms      min=0s       med=0s       max=377.17ms p(90)=0s       p(95)=0s      
     http_req_waiting...............: avg=187.02ms   min=173.94ms med=179.95ms max=2.72s    p(90)=188.57ms p(95)=192.66ms
     http_reqs......................: 501     1.034619/s
     iteration_duration.............: avg=12.31s     min=899.33ms med=10.91s   max=3m27s    p(90)=11.91s   p(95)=17.11s  
     iterations.....................: 101     0.208576/s
     time_lapse.....................: avg=242.234043 min=203      med=219      max=1220     p(90)=236.8    p(95)=242.4   
     time_lapse_alert...............: avg=120.829787 min=101      med=112      max=523      p(90)=122.2    p(95)=126.7   
     vus............................: 0       min=0      max=11
     vus_max........................: 12      min=12     max=12
     ws_connecting..................: avg=2.04s      min=2.04s    med=2.04s    max=2.04s    p(90)=2.04s    p(95)=2.04s   
     ws_msgs_received...............: 48      0.099125/s
     ws_msgs_sent...................: 2       0.00413/s
     ws_sessions....................: 1       0.002065/s
```
