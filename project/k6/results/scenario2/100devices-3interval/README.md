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

  scenarios: (100.00%) 3 scenarios, 22 max VUs, 4m40s max duration (incl. graceful stop):
           * subscribe: 1 iterations shared among 1 VUs (maxDuration: 4m0s, exec: subscribe, gracefulStop: 30s)
           * ingestion: 10 iterations for each of 20 VUs (maxDuration: 4m0s, exec: ingestion, startTime: 5s, gracefulStop: 30s)
           * consumption: 1 iterations shared among 1 VUs (maxDuration: 10s, exec: consumption, startTime: 4m0s, gracefulStop: 30s)

INFO[0049] Waiting for 4 minutes for rules to apply      source=console

running (8m43.5s), 01/22 VUs, 200 complete and 0 interrupted iterations
INFO[0530] Notifications stored: 102                     source=console
                                                                         running (9m24.3s), 00/22 VUs, 201 complete and 1 interrupted iterations  subscribe   ✗ [---------] 1 VUs   4m30.0s/4m0s  0/1 shared iters         ingestion   ✓ [=========] 20 VUs  0m40.0s/4m0s  200/200 iters, 10 per VU
consumption ✓ [=========] 1 VUs   00.9s/10s     1/1 shared iters

     ✓ status was 202
     ✓ notifications were all stored

     █ setup

     █ teardown

     checks.........................: 100.00% ✓ 1001     ✗ 0   
     data_received..................: 259 kB  459 B/s
     data_sent......................: 618 kB  1.1 kB/s
     http_req_blocked...............: avg=11.12ms    min=1.26µs   med=8.68µs   max=616.49ms p(90)=17.06µs  p(95)=18.35µs 
     http_req_connecting............: avg=3.87ms     min=0s       med=0s       max=247.32ms p(90)=0s       p(95)=0s      
     http_req_duration..............: avg=191.17ms   min=173.29ms med=182.79ms max=2.75s    p(90)=195.63ms p(95)=205.92ms
       { expected_response:true }...: avg=191.17ms   min=173.29ms med=182.79ms max=2.75s    p(90)=195.63ms p(95)=205.92ms
     http_req_failed................: 0.00%   ✓ 0        ✗ 1001
     http_req_receiving.............: avg=103.48µs   min=14.92µs  med=76.47µs  max=455.16µs p(90)=191.58µs p(95)=212.85µs
     http_req_sending...............: avg=61.21µs    min=7.93µs   med=47.25µs  max=217.85µs p(90)=116.84µs p(95)=130.21µs
     http_req_tls_handshaking.......: avg=7.24ms     min=0s       med=0s       max=393.93ms p(90)=0s       p(95)=0s      
     http_req_waiting...............: avg=191ms      min=173ms    med=182.69ms max=2.75s    p(90)=195.53ms p(95)=205.67ms
     http_reqs......................: 1001    1.77389/s
     iteration_duration.............: avg=5.23s      min=898.74ms med=3.92s    max=4m47s    p(90)=4.66s    p(95)=5.49s   
     iterations.....................: 201     0.356196/s
     time_lapse.....................: avg=334.372549 min=207      med=230      max=3947     p(90)=323      p(95)=371.85  
     time_lapse_alert...............: avg=162.901961 min=100      med=122      max=1367     p(90)=157.8    p(95)=221.5   
     vus............................: 0       min=0      max=21
     vus_max........................: 22      min=22     max=22
     ws_connecting..................: avg=2.05s      min=2.05s    med=2.05s    max=2.05s    p(90)=2.05s    p(95)=2.05s   
     ws_msgs_received...............: 103     0.182528/s
     ws_msgs_sent...................: 2       0.003544/s
     ws_sessions....................: 1       0.001772/s
```
