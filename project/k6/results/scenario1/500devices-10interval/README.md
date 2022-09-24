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
subscribe   ✓ [======================================] 1 VUs    1m54.8s/4m0s  1/1 shared iters
ingestion   ✓ [======================================] 100 VUs  1m49.7s/4m0s  1000/1000 iters, 10 per VU
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
     http_req_blocked...............: avg=10.89ms  min=1.15µs   med=4.66µs   max=667.27ms p(90)=15.39µs  p(95)=17.81µs 
     http_req_connecting............: avg=3.73ms   min=0s       med=0s       max=318.58ms p(90)=0s       p(95)=0s      
     http_req_duration..............: avg=180.24ms min=169.63ms med=179.29ms max=371.53ms p(90)=184.86ms p(95)=188.02ms
       { expected_response:true }...: avg=180.24ms min=169.63ms med=179.29ms max=371.53ms p(90)=184.86ms p(95)=188.02ms
     http_req_failed................: 0.00%   ✓ 0         ✗ 5004 
     http_req_receiving.............: avg=88.07µs  min=9.5µs    med=58.58µs  max=7.54ms   p(90)=168.63µs p(95)=187.12µs
     http_req_sending...............: avg=52.68µs  min=7.65µs   med=32.3µs   max=648.51µs p(90)=107.3µs  p(95)=120.1µs 
     http_req_tls_handshaking.......: avg=7.14ms   min=0s       med=0s       max=459.14ms p(90)=0s       p(95)=0s      
     http_req_waiting...............: avg=180.09ms min=169.47ms med=179.14ms max=371.41ms p(90)=184.7ms  p(95)=187.88ms
     http_reqs......................: 5004    11.547992/s
     iteration_duration.............: avg=10.72s   min=396.14ms med=10.9s    max=3m8s     p(90)=11.49s   p(95)=16.8s   
     iterations.....................: 1002    2.312368/s
     time_lapse.....................: avg=245.6422 min=183      med=221      max=898      p(90)=311      p(95)=354     
     vus............................: 0       min=0       max=101
     vus_max........................: 102     min=102     max=102
     ws_connecting..................: avg=368.18ms min=368.18ms med=368.18ms max=368.18ms p(90)=368.18ms p(95)=368.18ms
     ws_msgs_received...............: 5001    11.541069/s
     ws_msgs_sent...................: 2       0.004616/s
     ws_session_duration............: avg=1m54s    min=1m54s    med=1m54s    max=1m54s    p(90)=1m54s    p(95)=1m54s   
     ws_sessions....................: 1       0.002308/s
```
