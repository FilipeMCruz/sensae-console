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

  scenarios: (100.00%) 3 scenarios, 12 max VUs, 4m40s max duration (incl. graceful stop):
           * subscribe: 1 iterations shared among 1 VUs (maxDuration: 4m0s, exec: subscribe, gracefulStop: 30s)
           * ingestion: 10 iterations for each of 10 VUs (maxDuration: 4m0s, exec: ingestion, startTime: 5s, gracefulStop: 30s)
           * consumption: 1 iterations shared among 1 VUs (maxDuration: 10s, exec: consumption, startTime: 4m0s, gracefulStop: 30s)

INFO[0140] Expected: 500; Actual: 500                    source=console
INFO[0266] Data Units stored: 1000                       source=console

running (4m30.3s), 00/12 VUs, 102 complete and 0 interrupted iterations
subscribe   ✓ [======================================] 1 VUs   1m54.2s/4m0s  1/1 shared iters
ingestion   ✓ [======================================] 10 VUs  1m49.1s/4m0s  100/100 iters, 10 per VU
consumption ✓ [======================================] 1 VUs   00.4s/10s     1/1 shared iters

     ✓ status was 202
     ✓ all data units were received
     ✓ correct data units were received
     ✓ data units were all stored

     █ setup

     █ teardown

     checks.........................: 100.00% ✓ 1002     ✗ 0   
     data_received..................: 233 kB  860 B/s
     data_sent......................: 317 kB  1.2 kB/s
     http_req_blocked...............: avg=11.74ms  min=1.94µs   med=13.03µs  max=551.56ms p(90)=18.44µs  p(95)=21.97µs 
     http_req_connecting............: avg=4.62ms   min=0s       med=0s       max=183.45ms p(90)=0s       p(95)=0s      
     http_req_duration..............: avg=181.04ms min=173.45ms med=179.36ms max=223.08ms p(90)=187.29ms p(95)=189.12ms
       { expected_response:true }...: avg=181.04ms min=173.45ms med=179.36ms max=223.08ms p(90)=187.29ms p(95)=189.12ms
     http_req_failed................: 0.00%   ✓ 0        ✗ 503 
     http_req_receiving.............: avg=173.12µs min=34µs     med=171.32µs max=3.68ms   p(90)=211.82µs p(95)=229.59µs
     http_req_sending...............: avg=102.72µs min=15.17µs  med=101.87µs max=378.57µs p(90)=142.18µs p(95)=169.33µs
     http_req_tls_handshaking.......: avg=7.09ms   min=0s       med=0s       max=371.28ms p(90)=0s       p(95)=0s      
     http_req_waiting...............: avg=180.77ms min=173.14ms med=179.13ms max=222.8ms  p(90)=187.05ms p(95)=188.83ms
     http_reqs......................: 503     1.860972/s
     iteration_duration.............: avg=11.44s   min=405.11ms med=10.91s   max=1m54s    p(90)=13.43s   p(95)=16.88s  
     iterations.....................: 102     0.377374/s
     time_lapse.....................: avg=207.752  min=185      med=197      max=757      p(90)=205      p(95)=210     
     vus............................: 0       min=0      max=11
     vus_max........................: 12      min=12     max=12
     ws_connecting..................: avg=362.4ms  min=362.4ms  med=362.4ms  max=362.4ms  p(90)=362.4ms  p(95)=362.4ms 
     ws_msgs_received...............: 501     1.853573/s
     ws_msgs_sent...................: 2       0.007399/s
     ws_session_duration............: avg=1m53s    min=1m53s    med=1m53s    max=1m53s    p(90)=1m53s    p(95)=1m53s   
     ws_sessions....................: 1       0.0037/s
```
