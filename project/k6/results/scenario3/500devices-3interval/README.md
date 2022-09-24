# Results

```txt
          /\      |‾‾| /‾‾/   /‾‾/   
     /\  /  \     |  |/  /   /  /    
    /  \/    \    |     (   /   ‾‾\  
   /          \   |  |\  \ |  (‾)  | 
  / __________ \  |__| \__\ \_____/ .io

  execution: local
     script: test-suite-3-test.js
     output: csv (results/data.csv)

  scenarios: (100.00%) 3 scenarios, 102 max VUs, 4m40s max duration (incl. graceful stop):
           * subscribe: 1 iterations shared among 1 VUs (maxDuration: 4m0s, exec: subscribe, gracefulStop: 30s)
           * ingestion: 10 iterations for each of 100 VUs (maxDuration: 4m0s, exec: ingestion, startTime: 5s, gracefulStop: 30s)
           * consumption: 1 iterations shared among 1 VUs (maxDuration: 10s, exec: consumption, startTime: 4m0s, gracefulStop: 30s)

INFO[0204] Received 1000 messages                        source=console
INFO[0210] Received 2000 messages                        source=console
INFO[0218] Received 3000 messages                        source=console
INFO[0226] Received 4000 messages                        source=console
INFO[0235] Received 5000 messages                        source=console
INFO[0235] Expected: 5000; Actual: 5000                  source=console
INFO[0429] Data Units stored: 10000                      source=console

running (7m12.9s), 000/102 VUs, 1002 complete and 0 interrupted iterations
subscribe   ✓ [======================================] 1 VUs    0m45.9s/4m0s  1/1 shared iters
ingestion   ✓ [======================================] 100 VUs  0m39.7s/4m0s  1000/1000 iters, 10 per VU
consumption ✓ [======================================] 1 VUs    00.4s/10s     1/1 shared iters

     ✓ status was 202
     ✓ all data units were received
     ✓ correct data units were received
     ✓ data units were all stored

     █ setup

     █ teardown

     checks.........................: 100.00% ✓ 10002     ✗ 0    
     data_received..................: 2.3 MB  5.3 kB/s
     data_sent......................: 3.1 MB  7.3 kB/s
     http_req_blocked...............: avg=10.74ms  min=973ns    med=2.49µs   max=556.39ms p(90)=13.09µs  p(95)=16.26µs 
     http_req_connecting............: avg=3.65ms   min=0s       med=0s       max=202.85ms p(90)=0s       p(95)=0s      
     http_req_duration..............: avg=179.55ms min=169.75ms med=178.61ms max=250.1ms  p(90)=186.13ms p(95)=189.27ms
       { expected_response:true }...: avg=179.55ms min=169.75ms med=178.61ms max=250.1ms  p(90)=186.13ms p(95)=189.27ms
     http_req_failed................: 0.00%   ✓ 0         ✗ 5003 
     http_req_receiving.............: avg=58.98µs  min=9.95µs   med=34.73µs  max=827.57µs p(90)=149.94µs p(95)=174.52µs
     http_req_sending...............: avg=34.2µs   min=6.5µs    med=16.81µs  max=1.69ms   p(90)=95.18µs  p(95)=110.04µs
     http_req_tls_handshaking.......: avg=7.08ms   min=0s       med=0s       max=370.63ms p(90)=0s       p(95)=0s      
     http_req_waiting...............: avg=179.45ms min=169.7ms  med=178.52ms max=250.07ms p(90)=185.98ms p(95)=189.13ms
     http_reqs......................: 5003    11.558095/s
     iteration_duration.............: avg=4.03s    min=367.01ms med=3.9s     max=3m7s     p(90)=4.47s    p(95)=6.02s   
     iterations.....................: 1002    2.314853/s
     time_lapse.....................: avg=921.2226 min=183      med=754      max=2992     p(90)=2015.3   p(95)=2326.15 
     vus............................: 0       min=0       max=101
     vus_max........................: 102     min=102     max=102
     ws_connecting..................: avg=349.23ms min=349.23ms med=349.23ms max=349.23ms p(90)=349.23ms p(95)=349.23ms
     ws_msgs_received...............: 5001    11.553474/s
     ws_msgs_sent...................: 2       0.00462/s
     ws_session_duration............: avg=45.51s   min=45.51s   med=45.51s   max=45.51s   p(90)=45.51s   p(95)=45.51s  
     ws_sessions....................: 1       0.00231/s
```
