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
subscribe   ✓ [======================================] 1 VUs    3m03.2s/4m0s  1/1 shared iters
ingestion   ✓ [======================================] 200 VUs  1m50.0s/4m0s  2000/2000 iters, 10 per VU
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
     http_req_blocked...............: avg=10.7ms     min=1.11µs   med=3.14µs   max=616.62ms p(90)=13.38µs  p(95)=15.92µs 
     http_req_connecting............: avg=3.61ms     min=0s       med=0s       max=258.96ms p(90)=0s       p(95)=0s      
     http_req_duration..............: avg=179.56ms   min=168.93ms med=179.11ms max=590.36ms p(90)=185.67ms p(95)=188.34ms
       { expected_response:true }...: avg=179.56ms   min=168.93ms med=179.11ms max=590.36ms p(90)=185.67ms p(95)=188.34ms
     http_req_failed................: 0.00%   ✓ 0         ✗ 10004
     http_req_receiving.............: avg=62.85µs    min=9.7µs    med=40.41µs  max=1.3ms    p(90)=147.36µs p(95)=164.67µs
     http_req_sending...............: avg=37.22µs    min=7.64µs   med=20.94µs  max=2.35ms   p(90)=94.62µs  p(95)=106.7µs 
     http_req_tls_handshaking.......: avg=7.08ms     min=0s       med=0s       max=440.25ms p(90)=0s       p(95)=0s      
     http_req_waiting...............: avg=179.46ms   min=168.88ms med=179ms    max=590.22ms p(90)=185.57ms p(95)=188.25ms
     http_reqs......................: 10004   16.266762/s
     iteration_duration.............: avg=10.7s      min=376.04ms med=10.9s    max=6m9s     p(90)=11.58s   p(95)=16.65s  
     iterations.....................: 2002    3.255304/s
     time_lapse.....................: avg=38740.0246 min=208      med=46672.5  max=68334    p(90)=62248.1  p(95)=65087.55
     vus............................: 0       min=0       max=201
     vus_max........................: 202     min=202     max=202
     ws_connecting..................: avg=359.68ms   min=359.68ms med=359.68ms max=359.68ms p(90)=359.68ms p(95)=359.68ms
     ws_msgs_received...............: 10001   16.261884/s
     ws_msgs_sent...................: 2       0.003252/s
     ws_session_duration............: avg=3m2s       min=3m2s     med=3m2s     max=3m2s     p(90)=3m2s     p(95)=3m2s    
     ws_sessions....................: 1       0.001626/s

```
