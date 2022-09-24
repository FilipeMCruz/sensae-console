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

  scenarios: (100.00%) 3 scenarios, 202 max VUs, 4m40s max duration (incl. graceful stop):
           * subscribe: 1 iterations shared among 1 VUs (maxDuration: 4m0s, exec: subscribe, gracefulStop: 30s)
           * ingestion: 10 iterations for each of 200 VUs (maxDuration: 4m0s, exec: ingestion, startTime: 5s, gracefulStop: 30s)
           * consumption: 1 iterations shared among 1 VUs (maxDuration: 10s, exec: consumption, startTime: 4m0s, gracefulStop: 30s)

INFO[0392] Received 1000 messages                        source=console
INFO[0402] Received 2000 messages                        source=console
INFO[0411] Received 3000 messages                        source=console
INFO[0422] Received 4000 messages                        source=console
INFO[0431] Received 5000 messages                        source=console
INFO[0441] Received 6000 messages                        source=console
INFO[0454] Received 7000 messages                        source=console
INFO[0467] Received 8000 messages                        source=console
INFO[0483] Received 9000 messages                        source=console
INFO[0499] Received 10000 messages                       source=console
INFO[0499] Expected: 10000; Actual: 10000                source=console
INFO[0611] Data Units stored: 20000                      source=console

running (10m14.1s), 000/202 VUs, 2002 complete and 0 interrupted iterations
subscribe   ✓ [======================================] 1 VUs    2m09.2s/4m0s  1/1 shared iters
ingestion   ✓ [======================================] 200 VUs  0m40.6s/4m0s  2000/2000 iters, 10 per VU
consumption ✓ [======================================] 1 VUs    00.4s/10s     1/1 shared iters

     ✓ status was 202
     ✓ all data units were received
     ✓ correct data units were received
     ✓ data units were all stored

     █ setup

     █ teardown

     checks.........................: 100.00% ✓ 20002     ✗ 0    
     data_received..................: 4.6 MB  7.5 kB/s
     data_sent......................: 6.3 MB  10 kB/s
     http_req_blocked...............: avg=10.89ms    min=942ns    med=2.6µs    max=1.09s    p(90)=12.74µs  p(95)=16.24µs 
     http_req_connecting............: avg=3.6ms      min=0s       med=0s       max=204.67ms p(90)=0s       p(95)=0s      
     http_req_duration..............: avg=193.24ms   min=170.02ms med=182.12ms max=581.6ms  p(90)=219.16ms p(95)=249.24ms
       { expected_response:true }...: avg=193.24ms   min=170.02ms med=182.12ms max=581.6ms  p(90)=219.16ms p(95)=249.24ms
     http_req_failed................: 0.00%   ✓ 0         ✗ 10003
     http_req_receiving.............: avg=59.16µs    min=10.25µs  med=35.44µs  max=9.88ms   p(90)=142.85µs p(95)=167.89µs
     http_req_sending...............: avg=31.96µs    min=6.45µs   med=17.31µs  max=2.03ms   p(90)=87.01µs  p(95)=102.48µs
     http_req_tls_handshaking.......: avg=7.28ms     min=0s       med=0s       max=913.56ms p(90)=0s       p(95)=0s      
     http_req_waiting...............: avg=193.15ms   min=169.91ms med=182.02ms max=581.47ms p(90)=219.08ms p(95)=249.2ms 
     http_reqs......................: 10003   16.288825/s
     iteration_duration.............: avg=4.12s      min=376.5ms  med=3.93s    max=6m9s     p(90)=4.49s    p(95)=6.19s   
     iterations.....................: 2002    3.260045/s
     time_lapse.....................: avg=38427.1521 min=218      med=35387    max=83373    p(90)=71524.3  p(95)=77078.85
     vus............................: 0       min=0       max=201
     vus_max........................: 202     min=202     max=202
     ws_connecting..................: avg=366.52ms   min=366.52ms med=366.52ms max=366.52ms p(90)=366.52ms p(95)=366.52ms
     ws_msgs_received...............: 10001   16.285568/s
     ws_msgs_sent...................: 2       0.003257/s
     ws_session_duration............: avg=2m8s       min=2m8s     med=2m8s     max=2m8s     p(90)=2m8s     p(95)=2m8s    
     ws_sessions....................: 1       0.001628/s
```
