# Results

```txt
          /\      |‾‾| /‾‾/   /‾‾/   
     /\  /  \     |  |/  /   /  /    
    /  \/    \    |     (   /   ‾‾\  
   /          \   |  |\  \ |  (‾)  | 
  / __________ \  |__| \__\ \_____/ .io

  execution: local
     script: test-suite-1.js
     output: csv (results/data.csv)

  scenarios: (100.00%) 3 scenarios, 102 max VUs, 4m40s max duration (incl. graceful stop):
           * subscribe: 1 iterations shared among 1 VUs (maxDuration: 4m0s, exec: subscribe, gracefulStop: 30s)
           * ingestion: 10 iterations for each of 100 VUs (maxDuration: 4m0s, exec: ingestion, startTime: 5s, gracefulStop: 30s)
           * consumption: 1 iterations shared among 1 VUs (maxDuration: 10s, exec: consumption, startTime: 4m0s, gracefulStop: 30s)

INFO[0254] Received 1000 messages                        source=console
INFO[0267] Received 2000 messages                        source=console
INFO[0276] Received 3000 messages                        source=console
INFO[0284] Received 4000 messages                        source=console
INFO[0292] Received 5000 messages                        source=console
INFO[0292] Expected: 5000; Actual: 5000                  source=console
INFO[0439] Data Units stored: 5000                       source=console

running (7m23.2s), 000/102 VUs, 1002 complete and 0 interrupted iterations
subscribe   ✓ [======================================] 1 VUs    0m58.7s/4m0s  1/1 shared iters
ingestion   ✓ [======================================] 100 VUs  0m39.8s/4m0s  1000/1000 iters, 10 per VU
consumption ✓ [======================================] 1 VUs    00.4s/10s     1/1 shared iters

     ✓ status was 202
     ✓ all data units were received
     ✓ correct data units were received
     ✓ data units were all stored

     █ setup

     █ teardown

     checks.........................: 100.00% ✓ 10002     ✗ 0    
     data_received..................: 2.4 MB  5.5 kB/s
     data_sent......................: 3.1 MB  7.2 kB/s
     http_req_blocked...............: avg=10.85ms    min=1.15µs   med=4.52µs   max=593.13ms p(90)=15.63µs  p(95)=18.07µs 
     http_req_connecting............: avg=3.7ms      min=0s       med=0s       max=195.7ms  p(90)=0s       p(95)=0s      
     http_req_duration..............: avg=180.37ms   min=170.07ms med=179.18ms max=249.25ms p(90)=187.05ms p(95)=189.94ms
       { expected_response:true }...: avg=180.37ms   min=170.07ms med=179.18ms max=249.25ms p(90)=187.05ms p(95)=189.94ms
     http_req_failed................: 0.00%   ✓ 0         ✗ 5004 
     http_req_receiving.............: avg=90.92µs    min=9.83µs   med=61.51µs  max=7.34ms   p(90)=174.25µs p(95)=194.41µs
     http_req_sending...............: avg=52.18µs    min=7.5µs    med=31.58µs  max=1.8ms    p(90)=107.69µs p(95)=120.8µs 
     http_req_tls_handshaking.......: avg=7.13ms     min=0s       med=0s       max=401.92ms p(90)=0s       p(95)=0s      
     http_req_waiting...............: avg=180.23ms   min=169.81ms med=179.02ms max=249.11ms p(90)=186.87ms p(95)=189.78ms
     http_reqs......................: 5004    11.551288/s
     iteration_duration.............: avg=4.05s      min=414.78ms med=3.9s     max=3m8s     p(90)=4.53s    p(95)=6.07s   
     iterations.....................: 1002    2.313028/s
     time_lapse.....................: avg=12948.5888 min=255      med=13713    max=20000    p(90)=14604.3  p(95)=15338.05
     vus............................: 0       min=0       max=101
     vus_max........................: 102     min=102     max=102
     ws_connecting..................: avg=359.79ms   min=359.79ms med=359.79ms max=359.79ms p(90)=359.79ms p(95)=359.79ms
     ws_msgs_received...............: 5001    11.544363/s
     ws_msgs_sent...................: 2       0.004617/s
     ws_session_duration............: avg=58.27s     min=58.27s   med=58.27s   max=58.27s   p(90)=58.27s   p(95)=58.27s  
     ws_sessions....................: 1       0.002308/s
```
