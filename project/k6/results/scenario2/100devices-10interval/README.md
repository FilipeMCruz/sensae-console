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

INFO[0047] Waiting for 8 minutes for rules to apply      source=console
INFO[0768] Notifications stored: 95                      source=console

running (13m22.4s), 00/22 VUs, 201 complete and 1 interrupted iterations
subscribe   ✗ [--------------------------------------] 1 VUs   4m30.0s/4m0s  0/1 shared iters
ingestion   ✓ [======================================] 20 VUs  1m49.5s/4m0s  200/200 iters, 10 per VU
consumption ✓ [======================================] 1 VUs   01.0s/10s     1/1 shared iters

     ✓ status was 202
     ✓ notifications were all stored

     █ setup

     █ teardown

     checks.........................: 100.00% ✓ 1001     ✗ 0   
     data_received..................: 256 kB  320 B/s
     data_sent......................: 618 kB  770 B/s
     http_req_blocked...............: avg=13.28ms    min=1.57µs   med=13.53µs  max=901ms    p(90)=18.01µs  p(95)=22.59µs 
     http_req_connecting............: avg=4.27ms     min=0s       med=0s       max=344.46ms p(90)=0s       p(95)=0s      
     http_req_duration..............: avg=193.21ms   min=172.07ms med=183.82ms max=2.81s    p(90)=202.14ms p(95)=221.49ms
       { expected_response:true }...: avg=193.21ms   min=172.07ms med=183.82ms max=2.81s    p(90)=202.14ms p(95)=221.49ms
     http_req_failed................: 0.00%   ✓ 0        ✗ 1001
     http_req_receiving.............: avg=161.74µs   min=18.97µs  med=167.18µs max=349.49µs p(90)=207.93µs p(95)=223.55µs
     http_req_sending...............: avg=100.82µs   min=11.09µs  med=99.85µs  max=374.43µs p(90)=132.5µs  p(95)=147.22µs
     http_req_tls_handshaking.......: avg=8.98ms     min=0s       med=0s       max=724.62ms p(90)=0s       p(95)=0s      
     http_req_waiting...............: avg=192.95ms   min=171.76ms med=183.63ms max=2.81s    p(90)=201.88ms p(95)=221.21ms
     http_reqs......................: 1001    1.247482/s
     iteration_duration.............: avg=13.05s     min=899.34ms med=10.93s   max=8m45s    p(90)=12s      p(95)=17.91s  
     iterations.....................: 201     0.250493/s
     time_lapse.....................: avg=267.873684 min=208      med=226      max=1697     p(90)=265.4    p(95)=308.5   
     time_lapse_alert...............: avg=120.284211 min=99       med=114      max=643      p(90)=126.6    p(95)=132.3   
     vus............................: 0       min=0      max=21
     vus_max........................: 22      min=22     max=22
     ws_connecting..................: avg=2.26s      min=2.26s    med=2.26s    max=2.26s    p(90)=2.26s    p(95)=2.26s   
     ws_msgs_received...............: 96      0.119639/s
     ws_msgs_sent...................: 2       0.002492/s
     ws_sessions....................: 1       0.001246/s
```
