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

  scenarios: (100.00%) 3 scenarios, 202 max VUs, 4m40s max duration (incl. graceful stop):
           * subscribe: 1 iterations shared among 1 VUs (maxDuration: 4m0s, exec: subscribe, gracefulStop: 30s)
           * ingestion: 10 iterations for each of 200 VUs (maxDuration: 4m0s, exec: ingestion, startTime: 5s, gracefulStop: 30s)
           * consumption: 1 iterations shared among 1 VUs (maxDuration: 10s, exec: consumption, startTime: 4m0s, gracefulStop: 30s)

INFO[0390] Received 1000 messages                        source=console
INFO[0412] Received 2000 messages                        source=console
INFO[0434] Received 3000 messages                        source=console
INFO[0455] Received 4000 messages                        source=console
INFO[0478] Received 5000 messages                        source=console
INFO[0495] Received 6000 messages                        source=console
INFO[0508] Received 7000 messages                        source=console
INFO[0522] Received 8000 messages                        source=console
INFO[0537] Received 9000 messages                        source=console
INFO[0554] Received 10000 messages                       source=console
INFO[0554] Expected: 10000; Actual: 10000                source=console
INFO[0612] Data Units stored: 10000                      source=console

running (10m15.0s), 000/202 VUs, 2002 complete and 0 interrupted iterations
subscribe   ✓ [======================================] 1 VUs    2m38.9s/4m0s  1/1 shared iters
ingestion   ✓ [======================================] 200 VUs  1m49.7s/4m0s  2000/2000 iters, 10 per VU
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
     http_req_blocked...............: avg=10.77ms   min=1.14µs   med=2.91µs   max=577.15ms p(90)=12.85µs  p(95)=16.34µs 
     http_req_connecting............: avg=3.63ms    min=0s       med=0s       max=217.04ms p(90)=0s       p(95)=0s      
     http_req_duration..............: avg=180.83ms  min=170.02ms med=179.74ms max=271.87ms p(90)=187.85ms p(95)=190.86ms
       { expected_response:true }...: avg=180.83ms  min=170.02ms med=179.74ms max=271.87ms p(90)=187.85ms p(95)=190.86ms
     http_req_failed................: 0.00%   ✓ 0         ✗ 10004
     http_req_receiving.............: avg=64.26µs   min=10.21µs  med=41.1µs   max=7ms      p(90)=152.3µs  p(95)=176.24µs
     http_req_sending...............: avg=34.88µs   min=7.87µs   med=20.33µs  max=1.83ms   p(90)=90.6µs   p(95)=105.86µs
     http_req_tls_handshaking.......: avg=7.13ms    min=0s       med=0s       max=402.93ms p(90)=0s       p(95)=0s      
     http_req_waiting...............: avg=180.73ms  min=169.98ms med=179.65ms max=271.81ms p(90)=187.76ms p(95)=190.75ms
     http_reqs......................: 10004   16.24416/s
     iteration_duration.............: avg=10.67s    min=435.87ms med=10.9s    max=6m10s    p(90)=11.48s   p(95)=15.96s  
     iterations.....................: 2002    3.25078/s
     time_lapse.....................: avg=20720.011 min=205      med=21854    max=44157    p(90)=36982.2  p(95)=41018.15
     vus............................: 0       min=0       max=201
     vus_max........................: 202     min=202     max=202
     ws_connecting..................: avg=369.18ms  min=369.18ms med=369.18ms max=369.18ms p(90)=369.18ms p(95)=369.18ms
     ws_msgs_received...............: 10001   16.239289/s
     ws_msgs_sent...................: 2       0.003248/s
     ws_session_duration............: avg=2m38s     min=2m38s    med=2m38s    max=2m38s    p(90)=2m38s    p(95)=2m38s   
     ws_sessions....................: 1       0.001624/s
```
