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
subscribe   ✗ [--------------------------------------] 1 VUs    4m30.0s/4m0s  0/1 shared iters
ingestion   ✓ [======================================] 200 VUs  0m45.3s/4m0s  2000/2000 iters, 10 per VU
consumption ✓ [======================================] 1 VUs    00.4s/10s     1/1 shared iters

     ✓ status was 202
     ✓ all data units were received
     ✓ correct data units were received
     ✓ data units were all stored

     █ setup

     █ teardown

     checks.........................: 100.00% ✓ 12460     ✗ 0    
     data_received..................: 4.7 MB  7.3 kB/s
     data_sent......................: 6.2 MB  9.7 kB/s
     http_req_blocked...............: avg=10.73ms    min=974ns    med=11.2µs   max=564.95ms p(90)=15.76µs  p(95)=18.57µs  
     http_req_connecting............: avg=3.56ms     min=0s       med=0s       max=189.95ms p(90)=0s       p(95)=0s       
     http_req_duration..............: avg=234.27ms   min=168.91ms med=193.74ms max=1.2s     p(90)=331.66ms p(95)=432.79ms 
       { expected_response:true }...: avg=234.27ms   min=168.91ms med=193.74ms max=1.2s     p(90)=331.66ms p(95)=432.79ms 
     http_req_failed................: 0.00%   ✓ 0         ✗ 10004
     http_req_receiving.............: avg=96.97µs    min=10.97µs  med=116.29µs max=3.44ms   p(90)=162.14µs p(95)=177.8µs  
     http_req_sending...............: avg=65.88µs    min=7.26µs   med=76.16µs  max=3.2ms    p(90)=110.52µs p(95)=127.78µs 
     http_req_tls_handshaking.......: avg=7.15ms     min=0s       med=0s       max=384.75ms p(90)=0s       p(95)=0s       
     http_req_waiting...............: avg=234.11ms   min=168.79ms med=193.57ms max=1.2s     p(90)=331.54ms p(95)=432.71ms 
     http_reqs......................: 10004   15.528382/s
     iteration_duration.............: avg=4.36s      min=357.46ms med=4.04s    max=6m9s     p(90)=5.25s    p(95)=8.26s    
     iterations.....................: 2001    3.105987/s
     time_lapse.....................: avg=89990.6578 min=759      med=95680    max=155438   p(90)=143052   p(95)=150549.55
     vus............................: 0       min=0       max=201
     vus_max........................: 202     min=202     max=202
     ws_connecting..................: avg=356.73ms   min=356.73ms med=356.73ms max=356.73ms p(90)=356.73ms p(95)=356.73ms 
     ws_msgs_received...............: 10001   15.523726/s
     ws_msgs_sent...................: 2       0.003104/s
     ws_sessions....................: 1       0.001552/s
```
