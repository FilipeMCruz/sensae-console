# Results

```txt
          /\      |‾‾| /‾‾/   /‾‾/   
     /\  /  \     |  |/  /   /  /    
    /  \/    \    |     (   /   ‾‾\  
   /          \   |  |\  \ |  (‾)  | 
  / __________ \  |__| \__\ \_____/ .io

  execution: local
     script: test-suite-1-test.js
     output: csv (results/data.csv)

  scenarios: (100.00%) 3 scenarios, 102 max VUs, 4m40s max duration (incl. graceful stop):
           * subscribe: 1 iterations shared among 1 VUs (maxDuration: 4m0s, exec: subscribe, gracefulStop: 30s)
           * ingestion: 10 iterations for each of 100 VUs (maxDuration: 4m0s, exec: ingestion, startTime: 5s, gracefulStop: 30s)
           * consumption: 1 iterations shared among 1 VUs (maxDuration: 10s, exec: consumption, startTime: 4m0s, gracefulStop: 30s)

INFO[0216] Received 1000 messages                        source=console
INFO[0239] Received 2000 messages                        source=console
INFO[0260] Received 3000 messages                        source=console
INFO[0282] Received 4000 messages                        source=console
INFO[0304] Received 5000 messages                        source=console
INFO[0304] Expected: 5000; Actual: 5000                  source=console
INFO[0430] Data Units stored: 5000                       source=console

running (7m13.3s), 000/102 VUs, 1002 complete and 0 interrupted iterations
subscribe   ✓ [======================================] 1 VUs    1m54.6s/4m0s  1/1 shared iters
ingestion   ✓ [======================================] 100 VUs  1m49.4s/4m0s  1000/1000 iters, 10 per VU
consumption ✓ [======================================] 1 VUs    00.4s/10s     1/1 shared iters

     ✓ status was 202
     ✓ all data units were received
     ✓ correct data units were received
     ✓ data units were all stored

     █ setup

     █ teardown

     checks.........................: 100.00% ✓ 10002     ✗ 0    
     data_received..................: 2.4 MB  5.4 kB/s
     data_sent......................: 3.1 MB  7.2 kB/s
     http_req_blocked...............: avg=10.81ms  min=977ns    med=2.73µs   max=568.97ms p(90)=7.83µs   p(95)=12.7µs  
     http_req_connecting............: avg=3.72ms   min=0s       med=0s       max=212.42ms p(90)=0s       p(95)=0s      
     http_req_duration..............: avg=180.47ms min=169.76ms med=179.76ms max=260.22ms p(90)=186.85ms p(95)=189.67ms
       { expected_response:true }...: avg=180.47ms min=169.76ms med=179.76ms max=260.22ms p(90)=186.85ms p(95)=189.67ms
     http_req_failed................: 0.00%   ✓ 0         ✗ 5004 
     http_req_receiving.............: avg=55.82µs  min=14.25µs  med=40.49µs  max=6.2ms    p(90)=103.08µs p(95)=149.69µs
     http_req_sending...............: avg=25.44µs  min=8.1µs    med=19.11µs  max=703.43µs p(90)=43.28µs  p(95)=61.82µs 
     http_req_tls_handshaking.......: avg=7.08ms   min=0s       med=0s       max=385.98ms p(90)=0s       p(95)=0s      
     http_req_waiting...............: avg=180.39ms min=169.6ms  med=179.7ms  max=260.06ms p(90)=186.74ms p(95)=189.58ms
     http_reqs......................: 5004    11.546084/s
     iteration_duration.............: avg=10.73s   min=375.48ms med=10.9s    max=3m8s     p(90)=11.54s   p(95)=17.13s  
     iterations.....................: 1002    2.311986/s
     time_lapse.....................: avg=389.4792 min=183      med=299      max=1290     p(90)=760.1    p(95)=978     
     vus............................: 0       min=0       max=101
     vus_max........................: 102     min=102     max=102
     ws_connecting..................: avg=369.96ms min=369.96ms med=369.96ms max=369.96ms p(90)=369.96ms p(95)=369.96ms
     ws_msgs_received...............: 5001    11.539162/s
     ws_msgs_sent...................: 2       0.004615/s
     ws_session_duration............: avg=1m54s    min=1m54s    med=1m54s    max=1m54s    p(90)=1m54s    p(95)=1m54s   
     ws_sessions....................: 1       0.002307/s
```
