# Results

````txt
          /\      |‾‾| /‾‾/   /‾‾/   
     /\  /  \     |  |/  /   /  /    
    /  \/    \    |     (   /   ‾‾\  
   /          \   |  |\  \ |  (‾)  | 
  / __________ \  |__| \__\ \_____/ .io

  execution: local
     script: test-suite-2-test.js
     output: csv (results/data.csv)

  scenarios: (100.00%) 3 scenarios, 42 max VUs, 4m40s max duration (incl. graceful stop):
           * subscribe: 1 iterations shared among 1 VUs (maxDuration: 4m0s, exec: subscribe, gracefulStop: 30s)
           * ingestion: 10 iterations for each of 40 VUs (maxDuration: 4m0s, exec: ingestion, startTime: 5s, gracefulStop: 30s)
           * consumption: 1 iterations shared among 1 VUs (maxDuration: 10s, exec: consumption, startTime: 4m0s, gracefulStop: 30s)

INFO[0089] Waiting for 3 minutes for rules to apply      source=console
INFO[0510] Notifications stored: 218                     source=console

running (9m04.2s), 00/42 VUs, 401 complete and 1 interrupted iterations
subscribe   ✗ [--------------------------------------] 1 VUs   4m30.0s/4m0s  0/1 shared iters
ingestion   ✓ [======================================] 40 VUs  0m39.6s/4m0s  400/400 iters, 10 per VU
consumption ✓ [======================================] 1 VUs   00.9s/10s     1/1 shared iters

     ✓ status was 202
     ✓ notifications were all stored

     █ setup

     █ teardown

     checks.........................: 100.00% ✓ 2001     ✗ 0   
     data_received..................: 522 kB  959 B/s
     data_sent......................: 1.2 MB  2.3 kB/s
     http_req_blocked...............: avg=11.23ms    min=1.13µs   med=5.35µs   max=670.26ms p(90)=16.93µs  p(95)=19.56µs 
     http_req_connecting............: avg=3.84ms     min=0s       med=0s       max=291.92ms p(90)=0s       p(95)=0s      
     http_req_duration..............: avg=189.36ms   min=170.17ms med=181.57ms max=3.06s    p(90)=198.47ms p(95)=218.39ms
       { expected_response:true }...: avg=189.36ms   min=170.17ms med=181.57ms max=3.06s    p(90)=198.47ms p(95)=218.39ms
     http_req_failed................: 0.00%   ✓ 0        ✗ 2001
     http_req_receiving.............: avg=98.31µs    min=11.28µs  med=71.55µs  max=1.54ms   p(90)=183.54µs p(95)=203.93µs
     http_req_sending...............: avg=60.9µs     min=8.08µs   med=34.52µs  max=1.5ms    p(90)=113.11µs p(95)=129.4µs 
     http_req_tls_handshaking.......: avg=7.37ms     min=0s       med=0s       max=410.15ms p(90)=0s       p(95)=0s      
     http_req_waiting...............: avg=189.2ms    min=170.1ms  med=181.42ms max=3.05s    p(90)=198.39ms p(95)=218.28ms
     http_reqs......................: 2001    3.676938/s
     iteration_duration.............: avg=4.5s       min=889.56ms med=3.92s    max=4m27s    p(90)=4.68s    p(95)=6.01s   
     iterations.....................: 401     0.736858/s
     time_lapse.....................: avg=855.633028 min=200      med=241      max=5629     p(90)=2378.8   p(95)=4874.9  
     time_lapse_alert...............: avg=213.46789  min=98       med=122.5    max=1576     p(90)=444.8    p(95)=537.9   
     vus............................: 0       min=0      max=41
     vus_max........................: 42      min=42     max=42
     ws_connecting..................: avg=2.23s      min=2.23s    med=2.23s    max=2.23s    p(90)=2.23s    p(95)=2.23s   
     ws_msgs_received...............: 219     0.402424/s
     ws_msgs_sent...................: 2       0.003675/s
     ws_sessions....................: 1       0.001838/s
```
