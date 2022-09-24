# Result

```txt
          /\      |‾‾| /‾‾/   /‾‾/   
     /\  /  \     |  |/  /   /  /    
    /  \/    \    |     (   /   ‾‾\  
   /          \   |  |\  \ |  (‾)  | 
  / __________ \  |__| \__\ \_____/ .io

  execution: local
     script: test-suite-1-test.js
     output: csv (results/data.csv)

  scenarios: (100.00%) 3 scenarios, 202 max VUs, 4m40s max duration (incl. graceful stop):
           * subscribe: 1 iterations shared among 1 VUs (maxDuration: 4m0s, exec: subscribe, gracefulStop: 30s)
           * ingestion: 10 iterations for each of 200 VUs (maxDuration: 4m0s, exec: ingestion, startTime: 5s, gracefulStop: 30s)
           * consumption: 1 iterations shared among 1 VUs (maxDuration: 10s, exec: consumption, startTime: 4m0s, gracefulStop: 30s)

INFO[0405] Received 1000 messages                        source=console
INFO[0433] Received 2000 messages                        source=console
INFO[0463] Received 3000 messages                        source=console
INFO[0479] Received 4000 messages                        source=console
INFO[0494] Received 5000 messages                        source=console
INFO[0509] Received 6000 messages                        source=console
INFO[0525] Received 7000 messages                        source=console
INFO[0542] Received 8000 messages                        source=console
INFO[0559] Received 9000 messages                        source=console
INFO[0575] Received 10000 messages                       source=console
INFO[0575] Expected: 10000; Actual: 10000                source=console
INFO[0611] Data Units stored: 10000                      source=console

running (10m44.2s), 000/202 VUs, 2001 complete and 1 interrupted iterations
subscribe   ✓ [======================================] 1 VUs    2m50.0s/4m0s  1/1 shared iters
ingestion   ✓ [======================================] 200 VUs  0m42.9s/4m0s  2000/2000 iters, 10 per VU
consumption ✓ [======================================] 1 VUs    00.4s/10s     1/1 shared iters

     ✓ status was 202
     ✓ all data units were received
     ✓ correct data units were received
     ✓ data units were all stored

     █ setup

     █ teardown

     checks.........................: 100.00% ✓ 20002     ✗ 0    
     data_received..................: 4.7 MB  7.7 kB/s
     data_sent......................: 6.2 MB  10 kB/s
     http_req_blocked...............: avg=10.65ms    min=1.07µs   med=4.34µs   max=560.75ms p(90)=15.12µs  p(95)=17.33µs  
     http_req_connecting............: avg=3.59ms     min=0s       med=0s       max=193.35ms p(90)=0s       p(95)=0s       
     http_req_duration..............: avg=227.48ms   min=169.19ms med=200.17ms max=727.11ms p(90)=319.77ms p(95)=376.98ms 
       { expected_response:true }...: avg=227.48ms   min=169.19ms med=200.17ms max=727.11ms p(90)=319.77ms p(95)=376.98ms 
     http_req_failed................: 0.00%   ✓ 0         ✗ 10004
     http_req_receiving.............: avg=83.72µs    min=10.68µs  med=59.04µs  max=5.32ms   p(90)=162.47µs p(95)=179.92µs 
     http_req_sending...............: avg=49.02µs    min=8.27µs   med=30.02µs  max=1.03ms   p(90)=104.58µs p(95)=120.44µs 
     http_req_tls_handshaking.......: avg=7.05ms     min=0s       med=0s       max=380.92ms p(90)=0s       p(95)=0s       
     http_req_waiting...............: avg=227.35ms   min=169.09ms med=200.01ms max=727.03ms p(90)=319.7ms  p(95)=376.88ms 
     http_reqs......................: 10004   16.225367/s
     iteration_duration.............: avg=4.31s      min=376.31ms med=4.07s    max=6m11s    p(90)=4.85s    p(95)=6.21s    
     iterations.....................: 2002    3.24702/s
     time_lapse.....................: avg=70060.5512 min=260      med=70976.5  max=122315   p(90)=109825.7 p(95)=115419.35
     vus............................: 0       min=0       max=201
     vus_max........................: 202     min=202     max=202
     ws_connecting..................: avg=375.45ms   min=375.45ms med=375.45ms max=375.45ms p(90)=375.45ms p(95)=375.45ms 
     ws_msgs_received...............: 10001   16.220501/s
     ws_msgs_sent...................: 2       0.003244/s
     ws_session_duration............: avg=2m49s      min=2m49s    med=2m49s    max=2m49s    p(90)=2m49s    p(95)=2m49s    
     ws_sessions....................: 1       0.001622/s
```
