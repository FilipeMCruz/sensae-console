# Result

```txt
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

INFO[0387] Waiting for 9 minutes for rules to apply      source=console
INFO[1168] Notifications stored: 943                     source=console

running (20m01.8s), 000/202 VUs, 2001 complete and 1 interrupted iterations
subscribe   ✗ [--------------------------------------] 1 VUs    4m30.0s/4m0s  0/1 shared iters
ingestion   ✓ [======================================] 200 VUs  0m42.6s/4m0s  2000/2000 iters, 10 per VU
consumption ✓ [======================================] 1 VUs    01.0s/10s     1/1 shared iters

     ✗ status was 202
      ↳  92% — ✓ 9271 / ✗ 729
     ✓ notifications were all stored

     █ setup

     █ teardown

     checks.........................: 92.71% ✓ 9272     ✗ 729  
     data_received..................: 2.6 MB 2.2 kB/s
     data_sent......................: 6.2 MB 5.1 kB/s
     http_req_blocked...............: avg=12.54ms      min=1.1µs    med=2.8µs    max=1.7s   p(90)=15.22µs  p(95)=17.73µs 
     http_req_connecting............: avg=4.18ms       min=0s       med=0s       max=1.19s  p(90)=0s       p(95)=0s      
     http_req_duration..............: avg=245.48ms     min=168.87ms med=198.88ms max=2.95s  p(90)=395.32ms p(95)=483.19ms
       { expected_response:true }...: avg=248.78ms     min=168.87ms med=199.15ms max=2.95s  p(90)=409.01ms p(95)=491.3ms 
     http_req_failed................: 7.28%  ✓ 729      ✗ 9272 
     http_req_receiving.............: avg=71.23µs      min=10.43µs  med=34µs     max=13.5ms p(90)=153.5µs  p(95)=176.07µs
     http_req_sending...............: avg=41.45µs      min=7.1µs    med=17.42µs  max=5.53ms p(90)=99.12µs  p(95)=112.59µs
     http_req_tls_handshaking.......: avg=8.34ms       min=0s       med=0s       max=1.51s  p(90)=0s       p(95)=0s      
     http_req_waiting...............: avg=245.37ms     min=168.76ms med=198.78ms max=2.95s  p(90)=395.27ms p(95)=483.14ms
     http_reqs......................: 10001  8.321932/s
     iteration_duration.............: avg=4.61s        min=936.58ms med=4.07s    max=15m25s p(90)=5.29s    p(95)=6.91s   
     iterations.....................: 2001   1.665052/s
     time_lapse.....................: avg=27506.923648 min=13479    med=27769    max=35504  p(90)=29680    p(95)=31133.8 
     time_lapse_alert...............: avg=365.637328   min=146      med=305      max=4746   p(90)=456.8    p(95)=572     
     vus............................: 0      min=0      max=201
     vus_max........................: 202    min=202    max=202
     ws_connecting..................: avg=2.22s        min=2.22s    med=2.22s    max=2.22s  p(90)=2.22s    p(95)=2.22s   
     ws_msgs_received...............: 944    0.785512/s
     ws_msgs_sent...................: 2      0.001664/s
     ws_sessions....................: 1      0.000832/s
```
