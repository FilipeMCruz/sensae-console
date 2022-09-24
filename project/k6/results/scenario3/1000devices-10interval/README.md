# Results

```txt          /\      |‾‾| /‾‾/   /‾‾/   
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

INFO[0388] Received 1000 messages                        source=console
INFO[0399] Received 2000 messages                        source=console
INFO[0410] Received 3000 messages                        source=console
INFO[0421] Received 4000 messages                        source=console
INFO[0432] Received 5000 messages                        source=console
INFO[0443] Received 6000 messages                        source=console
INFO[0456] Received 7000 messages                        source=console
INFO[0469] Received 8000 messages                        source=console
INFO[0485] Received 9000 messages                        source=console
INFO[0502] Received 10000 messages                       source=console
INFO[0502] Expected: 10000; Actual: 10000                source=console
INFO[0612] Data Units stored: 20000                      source=console

running (10m15.3s), 000/202 VUs, 2002 complete and 0 interrupted iterations
subscribe   ✓ [======================================] 1 VUs    2m11.3s/4m0s  1/1 shared iters
ingestion   ✓ [======================================] 200 VUs  1m49.7s/4m0s  2000/2000 iters, 10 per VU
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
     http_req_blocked...............: avg=10.69ms   min=1µs      med=2.38µs   max=558.79ms p(90)=10.82µs  p(95)=14.52µs 
     http_req_connecting............: avg=3.59ms    min=0s       med=0s       max=190.04ms p(90)=0s       p(95)=0s      
     http_req_duration..............: avg=179.82ms  min=169.83ms med=179.51ms max=229.2ms  p(90)=185.76ms p(95)=188.48ms
       { expected_response:true }...: avg=179.82ms  min=169.83ms med=179.51ms max=229.2ms  p(90)=185.76ms p(95)=188.48ms
     http_req_failed................: 0.00%   ✓ 0         ✗ 10003
     http_req_receiving.............: avg=51.89µs   min=10.15µs  med=33.82µs  max=3.4ms    p(90)=127.95µs p(95)=161.59µs
     http_req_sending...............: avg=27.89µs   min=6.55µs   med=16.66µs  max=1.28ms   p(90)=60.93µs  p(95)=96.87µs 
     http_req_tls_handshaking.......: avg=7.08ms    min=0s       med=0s       max=386.08ms p(90)=0s       p(95)=0s      
     http_req_waiting...............: avg=179.74ms  min=169.76ms med=179.42ms max=228.63ms p(90)=185.69ms p(95)=188.4ms 
     http_reqs......................: 10003   16.256029/s
     iteration_duration.............: avg=10.67s    min=376.35ms med=10.9s    max=6m10s    p(90)=11.47s   p(95)=16.38s  
     iterations.....................: 2002    3.253481/s
     time_lapse.....................: avg=2870.5263 min=189      med=673.5    max=16689    p(90)=10137.8  p(95)=13307.35
     vus............................: 0       min=0       max=201
     vus_max........................: 202     min=202     max=202
     ws_connecting..................: avg=358.06ms  min=358.06ms med=358.06ms max=358.06ms p(90)=358.06ms p(95)=358.06ms
     ws_msgs_received...............: 10001   16.252779/s
     ws_msgs_sent...................: 2       0.00325/s
     ws_session_duration............: avg=2m10s     min=2m10s    med=2m10s    max=2m10s    p(90)=2m10s    p(95)=2m10s   
     ws_sessions....................: 1       0.001625/s
```
