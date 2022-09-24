# Results

```txt          /\      |‾‾| /‾‾/   /‾‾/   
     /\  /  \     |  |/  /   /  /    
    /  \/    \    |     (   /   ‾‾\  
   /          \   |  |\  \ |  (‾)  | 
  / __________ \  |__| \__\ \_____/ .io

  execution: local
     script: test-suite-1-test.js
     output: csv (results/data.csv)

  scenarios: (100.00%) 3 scenarios, 22 max VUs, 4m40s max duration (incl. graceful stop):
           * subscribe: 1 iterations shared among 1 VUs (maxDuration: 4m0s, exec: subscribe, gracefulStop: 30s)
           * ingestion: 10 iterations for each of 20 VUs (maxDuration: 4m0s, exec: ingestion, startTime: 5s, gracefulStop: 30s)
           * consumption: 1 iterations shared among 1 VUs (maxDuration: 10s, exec: consumption, startTime: 4m0s, gracefulStop: 30s)

INFO[0158] Received 1000 messages                        source=console
INFO[0158] Expected: 1000; Actual: 1000                  source=console
INFO[0285] Data Units stored: 1000                       source=console

running (4m48.7s), 00/22 VUs, 202 complete and 0 interrupted iterations
subscribe   ✓ [======================================] 1 VUs   1m53.5s/4m0s  1/1 shared iters
ingestion   ✓ [======================================] 20 VUs  1m48.4s/4m0s  200/200 iters, 10 per VU
consumption ✓ [======================================] 1 VUs   00.4s/10s     1/1 shared iters

     ✓ status was 202
     ✓ all data units were received
     ✓ correct data units were received
     ✓ data units were all stored

     █ setup

     █ teardown

     checks.........................: 100.00% ✓ 2002     ✗ 0   
     data_received..................: 474 kB  1.6 kB/s
     data_sent......................: 624 kB  2.2 kB/s
     http_req_blocked...............: avg=11.31ms  min=1.19µs   med=3.62µs   max=541.85ms p(90)=15.99µs  p(95)=19.22µs 
     http_req_connecting............: avg=4.28ms   min=0s       med=0s       max=184.62ms p(90)=0s       p(95)=0s      
     http_req_duration..............: avg=179.91ms min=172.04ms med=179.65ms max=229.77ms p(90)=182.64ms p(95)=185.87ms
       { expected_response:true }...: avg=179.91ms min=172.04ms med=179.65ms max=229.77ms p(90)=182.64ms p(95)=185.87ms
     http_req_failed................: 0.00%   ✓ 0        ✗ 1004
     http_req_receiving.............: avg=94.67µs  min=14.41µs  med=59.39µs  max=601.36µs p(90)=183.22µs p(95)=206.23µs
     http_req_sending...............: avg=54.84µs  min=7.94µs   med=27.79µs  max=457.74µs p(90)=111.08µs p(95)=128.25µs
     http_req_tls_handshaking.......: avg=7.01ms   min=0s       med=0s       max=360.33ms p(90)=0s       p(95)=0s      
     http_req_waiting...............: avg=179.76ms min=171.76ms med=179.53ms max=229.17ms p(90)=182.54ms p(95)=185.7ms 
     http_reqs......................: 1004    3.478164/s
     iteration_duration.............: avg=11.01s   min=396.22ms med=10.9s    max=1m53s    p(90)=11.66s   p(95)=16.55s  
     iterations.....................: 202     0.69979/s
     time_lapse.....................: avg=203.972  min=181      med=190      max=761      p(90)=202.1    p(95)=210     
     vus............................: 0       min=0      max=21
     vus_max........................: 22      min=22     max=22
     ws_connecting..................: avg=352.65ms min=352.65ms med=352.65ms max=352.65ms p(90)=352.65ms p(95)=352.65ms
     ws_msgs_received...............: 1001    3.467771/s
     ws_msgs_sent...................: 2       0.006929/s
     ws_session_duration............: avg=1m53s    min=1m53s    med=1m53s    max=1m53s    p(90)=1m53s    p(95)=1m53s   
     ws_sessions....................: 1       0.003464/s
```
