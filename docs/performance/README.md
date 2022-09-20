# Performance

This document details the performance expected of the system.

Current version:

- `iot-core` : `0.1.20`
- `system` : `0.10.0`

## Introduction

The system was tested with the `k6` tool.

The system has a default configuration and no container is replicated.

## Test Suite 1

This suite is responsible for testing the time it takes for data units to be dispatched by the system and determine the throughput the system can handle.

### Throughput Test

The test setup is as follows:

- 1 decoder;
- 1 processor;
- 500 devices;
- Data is sent every second by each device for 60 iterations;

#### Local Test

The results are:

``` txt
          /\      |‾‾| /‾‾/   /‾‾/   
     /\  /  \     |  |/  /   /  /    
    /  \/    \    |     (   /   ‾‾\  
   /          \   |  |\  \ |  (‾)  |
  / __________ \  |__| \__\ \_____/ .io

  execution: local
     script: test-suite-1.js
     output: statsd (localhost:8125)

  scenarios: (100.00%) 3 scenarios, 502 max VUs, 5m40s max duration (incl. graceful stop):
           * subscribe: 1 iterations shared among 1 VUs (maxDuration: 5m0s, exec: subscribe, gracefulStop: 30s)
           * ingestion: 60 iterations for each of 500 VUs (maxDuration: 5m0s, exec: ingestion, startTime: 5s, gracefulStop: 30s)
           * consumption: 1 iterations shared among 1 VUs (maxDuration: 10s, exec: consumption, startTime: 5m0s, gracefulStop: 30s)

running (5m04.3s), 000/502 VUs, 30002 complete and 0 interrupted iterations
subscribe   ✓ [======================================] 1 VUs    0m02.5s/5m0s  1/1 shared iters
ingestion   ✓ [======================================] 500 VUs  2m38.8s/5m0s  30000/30000 iters, 60 per VU
consumption ✓ [======================================] 1 VUs    00.1s/10s     1/1 shared iters

     ✓ status was 202
     ✓ data unit were all stored

     █ setup

     █ teardown

     checks.........................: 100.00% ✓ 30001     ✗ 0    
     data_received..................: 2.7 MB  8.9 kB/s
     data_sent......................: 18 MB   58 kB/s
     http_req_blocked...............: avg=29.21µs  min=1.41µs   med=3.53µs   max=34.09ms  p(90)=5.6µs    p(95)=6.96µs  
     http_req_connecting............: avg=24.44µs  min=0s       med=0s       max=34.06ms  p(90)=0s       p(95)=0s      
     http_req_duration..............: avg=1.06ms   min=362.35µs med=740.53µs max=1.28s    p(90)=1.07ms   p(95)=1.33ms  
       { expected_response:true }...: avg=1.06ms   min=362.35µs med=740.53µs max=1.28s    p(90)=1.07ms   p(95)=1.33ms  
     http_req_failed................: 0.00%   ✓ 0         ✗ 30004
     http_req_receiving.............: avg=32.5µs   min=8.38µs   med=28.89µs  max=3.47ms   p(90)=48.63µs  p(95)=58.98µs 
     http_req_sending...............: avg=23.63µs  min=9.47µs   med=21.29µs  max=544.96µs p(90)=32.92µs  p(95)=39.77µs 
     http_req_tls_handshaking.......: avg=0s       min=0s       med=0s       max=0s       p(90)=0s       p(95)=0s      
     http_req_waiting...............: avg=1ms      min=341.92µs med=688.48µs max=1.28s    p(90)=999.96µs p(95)=1.24ms  
     http_reqs......................: 30004   98.601008/s
     iteration_duration.............: avg=2.5s     min=64.06ms  med=2.5s     max=3.02s    p(90)=2.9s     p(95)=2.95s   
     iterations.....................: 30002   98.594436/s
     vus............................: 0       min=0       max=500
     vus_max........................: 502     min=502     max=502
     ws_connecting..................: avg=992.45ms min=992.45ms med=992.45ms max=992.45ms p(90)=992.45ms p(95)=992.45ms
     ws_msgs_received...............: 1       0.003286/s
     ws_msgs_sent...................: 2       0.006573/s
     ws_session_duration............: avg=1.24s    min=1.24s    med=1.24s    max=1.24s    p(90)=1.24s    p(95)=1.24s   
     ws_sessions....................: 1       0.003286/s
```

Due to the high number of VUs the subscription didn't started.

#### e2-standard-4

The results are:

``` txt
          /\      |‾‾| /‾‾/   /‾‾/   
     /\  /  \     |  |/  /   /  /    
    /  \/    \    |     (   /   ‾‾\  
   /          \   |  |\  \ |  (‾)  | 
  / __________ \  |__| \__\ \_____/ .io

  execution: local
     script: test-suite-1.js
     output: -

  scenarios: (100.00%) 3 scenarios, 502 max VUs, 5m40s max duration (incl. graceful stop):
           * subscribe: 1 iterations shared among 1 VUs (maxDuration: 5m0s, exec: subscribe, gracefulStop: 30s)
           * ingestion: 60 iterations for each of 500 VUs (maxDuration: 5m0s, exec: ingestion, startTime: 5s, gracefulStop: 30s)
           * consumption: 1 iterations shared among 1 VUs (maxDuration: 10s, exec: consumption, startTime: 5m0s, gracefulStop: 30s)

WARN[0028] Request Failed                                error="Post \"http://localhost:8080/sensor-data/irrigation/encoded/em300th\": EOF"

running (5m02.5s), 000/502 VUs, 30002 complete and 0 interrupted iterations
subscribe   ✓ [======================================] 1 VUs    0m00.1s/5m0s  1/1 shared iters
ingestion   ✓ [======================================] 500 VUs  1m44.6s/5m0s  30000/30000 iters, 60 per VU
consumption ✓ [======================================] 1 VUs    00.0s/10s     1/1 shared iters

     ✗ status was 202
      ↳  61% — ✓ 18328 / ✗ 11672
     ✗ data units were all stored
      ↳  0% — ✓ 0 / ✗ 1

     █ setup

     █ teardown

     checks.........................: 61.09% ✓ 18328     ✗ 11673
     data_received..................: 3.8 MB 12 kB/s
     data_sent......................: 18 MB  58 kB/s
     http_req_blocked...............: avg=191.64µs min=2.88µs   med=6.31µs  max=117.06ms p(90)=271.56µs p(95)=474.78µs
     http_req_connecting............: avg=125.31µs min=0s       med=0s      max=116.52ms p(90)=163.03µs p(95)=276.29µs
     http_req_duration..............: avg=156.28ms min=822.61µs med=42.2ms  max=2.53s    p(90)=453.66ms p(95)=699.01ms
       { expected_response:true }...: avg=76.7ms   min=822.61µs med=9.75ms  max=2.23s    p(90)=231.13ms p(95)=398.54ms
     http_req_failed................: 38.90% ✓ 11673     ✗ 18331
     http_req_receiving.............: avg=82.99µs  min=0s       med=49.92µs max=11.78ms  p(90)=122.21µs p(95)=231.74µs
     http_req_sending...............: avg=83µs     min=13.09µs  med=38.06µs max=56.39ms  p(90)=79.68µs  p(95)=111.46µs
     http_req_tls_handshaking.......: avg=0s       min=0s       med=0s      max=0s       p(90)=0s       p(95)=0s      
     http_req_waiting...............: avg=156.12ms min=729.91µs med=42.03ms max=2.53s    p(90)=453.11ms p(95)=698.9ms 
     http_reqs......................: 30004  99.173187/s
     iteration_duration.............: avg=1.42s    min=6.16ms   med=1.39s   max=4.21s    p(90)=1.98s    p(95)=2.17s   
     iterations.....................: 30002  99.166577/s
     vus............................: 0      min=0       max=500
     vus_max........................: 502    min=502     max=502
     ws_connecting..................: avg=5.55ms   min=5.55ms   med=5.55ms  max=5.55ms   p(90)=5.55ms   p(95)=5.55ms  
     ws_msgs_received...............: 1      0.003305/s
     ws_msgs_sent...................: 2      0.006611/s
     ws_session_duration............: avg=42.28ms  min=42.28ms  med=42.28ms max=42.28ms  p(90)=42.28ms  p(95)=42.28ms 
     ws_sessions....................: 1      0.003305/s
```

With 500 VUs the project can't even collect all data units.

Trying with less resources.

```txt
          /\      |‾‾| /‾‾/   /‾‾/   
     /\  /  \     |  |/  /   /  /    
    /  \/    \    |     (   /   ‾‾\  
   /          \   |  |\  \ |  (‾)  | 
  / __________ \  |__| \__\ \_____/ .io

  execution: local
     script: test-suite-1.js
     output: -

  scenarios: (100.00%) 3 scenarios, 302 max VUs, 5m40s max duration (incl. graceful stop):
           * subscribe: 1 iterations shared among 1 VUs (maxDuration: 5m0s, exec: subscribe, gracefulStop: 30s)
           * ingestion: 100 iterations for each of 300 VUs (maxDuration: 5m0s, exec: ingestion, startTime: 5s, gracefulStop: 30s)
           * consumption: 1 iterations shared among 1 VUs (maxDuration: 10s, exec: consumption, startTime: 5m0s, gracefulStop: 30s)

INFO[0417] Data Units stored: 26205                      source=console

running (7m00.0s), 000/302 VUs, 30002 complete and 0 interrupted iterations
subscribe   ✓ [======================================] 1 VUs    0m01.0s/5m0s  1/1 shared iters
ingestion   ✓ [======================================] 300 VUs  2m58.8s/5m0s  30000/30000 iters, 100 per VU
consumption ✓ [======================================] 1 VUs    00.4s/10s     1/1 shared iters

     ✓ status was 202
     ✗ data units were all stored
      ↳  0% — ✓ 0 / ✗ 1

     █ setup

     █ teardown

     checks.........................: 99.99% ✓ 30000     ✗ 1    
     data_received..................: 2.7 MB 6.4 kB/s
     data_sent......................: 18 MB  42 kB/s
     http_req_blocked...............: avg=1.9ms    min=1.14µs   med=4.73µs   max=207.34ms p(90)=16.85µs  p(95)=18.86µs 
     http_req_connecting............: avg=1.89ms   min=0s       med=0s       max=207.15ms p(90)=0s       p(95)=0s      
     http_req_duration..............: avg=191.91ms min=171.39ms med=184.82ms max=704.96ms p(90)=202.72ms p(95)=240.75ms
       { expected_response:true }...: avg=191.91ms min=171.39ms med=184.82ms max=704.96ms p(90)=202.72ms p(95)=240.75ms
     http_req_failed................: 0.00%  ✓ 0         ✗ 30003
     http_req_receiving.............: avg=70.8µs   min=7.25µs   med=44.53µs  max=1.13ms   p(90)=144.85µs p(95)=161.19µs
     http_req_sending...............: avg=55.07µs  min=7.74µs   med=29.67µs  max=1.39ms   p(90)=111.73µs p(95)=129.21µs
     http_req_tls_handshaking.......: avg=0s       min=0s       med=0s       max=0s       p(90)=0s       p(95)=0s      
     http_req_waiting...............: avg=191.79ms min=171.33ms med=184.69ms max=704.8ms  p(90)=202.6ms  p(95)=240.61ms
     http_reqs......................: 30003  71.442019/s
     iteration_duration.............: avg=1.45s    min=375.94ms med=1.44s    max=1m53s    p(90)=2s       p(95)=2.1s    
     iterations.....................: 30002  71.439638/s
     vus............................: 0      min=0       max=300
     vus_max........................: 302    min=302     max=302
     ws_connecting..................: avg=386.32ms min=386.32ms med=386.32ms max=386.32ms p(90)=386.32ms p(95)=386.32ms
     ws_msgs_received...............: 1      0.002381/s
     ws_msgs_sent...................: 2      0.004762/s
     ws_session_duration............: avg=616.26ms min=616.26ms med=616.26ms max=616.26ms p(90)=616.26ms p(95)=616.26ms
     ws_sessions....................: 1      0.002381/s
```

With 300 VUs the project can collect all data units but fails to process/store some of them.

Trying with less resources.

``` txt
          /\      |‾‾| /‾‾/   /‾‾/   
     /\  /  \     |  |/  /   /  /    
    /  \/    \    |     (   /   ‾‾\  
   /          \   |  |\  \ |  (‾)  | 
  / __________ \  |__| \__\ \_____/ .io

  execution: local
     script: test-suite-1.js
     output: -

  scenarios: (100.00%) 3 scenarios, 202 max VUs, 5m40s max duration (incl. graceful stop):
           * subscribe: 1 iterations shared among 1 VUs (maxDuration: 5m0s, exec: subscribe, gracefulStop: 30s)
           * ingestion: 100 iterations for each of 200 VUs (maxDuration: 5m0s, exec: ingestion, startTime: 5s, gracefulStop: 30s)
           * consumption: 1 iterations shared among 1 VUs (maxDuration: 10s, exec: consumption, startTime: 5m0s, gracefulStop: 30s)

INFO[0380] Data Units stored: 20000                      source=console

running (6m24.1s), 000/202 VUs, 20002 complete and 0 interrupted iterations
subscribe   ✓ [======================================] 1 VUs    0m08.6s/5m0s  1/1 shared iters
ingestion   ✓ [======================================] 200 VUs  2m57.3s/5m0s  20000/20000 iters, 100 per VU
consumption ✓ [======================================] 1 VUs    00.4s/10s     1/1 shared iters

     ✓ status was 202
     ✓ data units were all stored

     █ setup

     █ teardown

     checks.........................: 100.00% ✓ 20001     ✗ 0    
     data_received..................: 1.8 MB  4.7 kB/s
     data_sent......................: 12 MB   31 kB/s
     http_req_blocked...............: avg=1.9ms    min=1.24µs   med=14.26µs  max=201.4ms  p(90)=17.93µs  p(95)=20.02µs 
     http_req_connecting............: avg=1.89ms   min=0s       med=0s       max=201.28ms p(90)=0s       p(95)=0s      
     http_req_duration..............: avg=203.28ms min=171.3ms  med=184.77ms max=2.98s    p(90)=196.55ms p(95)=225.41ms
       { expected_response:true }...: avg=203.28ms min=171.3ms  med=184.77ms max=2.98s    p(90)=196.55ms p(95)=225.41ms
     http_req_failed................: 0.00%   ✓ 0         ✗ 20003
     http_req_receiving.............: avg=102.21µs min=7.92µs   med=116.23µs max=822.96µs p(90)=156.25µs p(95)=175.53µs
     http_req_sending...............: avg=81.19µs  min=7.68µs   med=93.18µs  max=1.68ms   p(90)=124.02µs p(95)=143.52µs
     http_req_tls_handshaking.......: avg=0s       min=0s       med=0s       max=0s       p(90)=0s       p(95)=0s      
     http_req_waiting...............: avg=203.09ms min=171.15ms med=184.59ms max=2.98s    p(90)=196.39ms p(95)=225.26ms
     http_reqs......................: 20003   52.075825/s
     iteration_duration.............: avg=1.48s    min=372.06ms med=1.47s    max=1m18s    p(90)=2.02s    p(95)=2.12s   
     iterations.....................: 20002   52.073222/s
     vus............................: 0       min=0       max=201
     vus_max........................: 202     min=202     max=202
     ws_connecting..................: avg=2.65s    min=2.65s    med=2.65s    max=2.65s    p(90)=2.65s    p(95)=2.65s   
     ws_msgs_received...............: 1       0.002603/s
     ws_msgs_sent...................: 2       0.005207/s
     ws_session_duration............: avg=5.38s    min=5.38s    med=5.38s    max=5.38s    p(90)=5.38s    p(95)=5.38s   
     ws_sessions....................: 1       0.002603/s
```

### Availability Test

The test setup is as follows:

- 1 decoder;
- 1 processor;
- 100 devices;
- Data is sent every second by each device for 100 iterations;

The `time_lapse` metric indicates the time, in milliseconds, that a message takes to be processed by the system and sent via websocket.

#### Local Test

The results are:

``` txt
          /\      |‾‾| /‾‾/   /‾‾/   
     /\  /  \     |  |/  /   /  /    
    /  \/    \    |     (   /   ‾‾\  
   /          \   |  |\  \ |  (‾)  | 
  / __________ \  |__| \__\ \_____/ .io

  execution: local
     script: test-suite-1.js
     output: statsd (localhost:8125)

  scenarios: (100.00%) 3 scenarios, 102 max VUs, 5m40s max duration (incl. graceful stop):
           * subscribe: 1 iterations shared among 1 VUs (maxDuration: 5m0s, exec: subscribe, gracefulStop: 30s)
           * ingestion: 100 iterations for each of 100 VUs (maxDuration: 5m0s, exec: ingestion, startTime: 5s, gracefulStop: 30s)
           * consumption: 1 iterations shared among 1 VUs (maxDuration: 10s, exec: consumption, startTime: 5m0s, gracefulStop: 30s)

INFO[0162] Expected: 10000; Actual: 10000                source=console

running (5m31.9s), 000/102 VUs, 10001 complete and 1 interrupted iterations
subscribe   ✗ [--------------------------------------] 1 VUs    5m30.0s/5m0s  0/1 shared iters
ingestion   ✓ [======================================] 100 VUs  2m36.1s/5m0s  10000/10000 iters, 100 per VU
consumption ✓ [======================================] 1 VUs    00.0s/10s     1/1 shared iters

     ✓ status was 202
     ✓ data units were received
     ✓ data units was sent
     ✓ data units were all stored

     █ setup

     █ teardown

     checks.........................: 100.00% ✓ 14970     ✗ 0    
     data_received..................: 3.4 MB  10 kB/s
     data_sent......................: 5.9 MB  18 kB/s
     http_req_blocked...............: avg=14.57µs min=1.34µs   med=3.6µs    max=23.58ms p(90)=15.83µs  p(95)=17.69µs 
     http_req_connecting............: avg=7.88µs  min=0s       med=0s       max=23.48ms p(90)=0s       p(95)=0s      
     http_req_duration..............: avg=1.34ms  min=410.42µs med=827.5µs  max=40.45ms p(90)=4.09ms   p(95)=4.56ms  
       { expected_response:true }...: avg=1.34ms  min=410.42µs med=827.5µs  max=40.45ms p(90)=4.09ms   p(95)=4.56ms  
     http_req_failed................: 0.00%   ✓ 0         ✗ 10004
     http_req_receiving.............: avg=48.43µs min=9.47µs   med=30.95µs  max=8.55ms  p(90)=127.73µs p(95)=150.15µs
     http_req_sending...............: avg=38.49µs min=10.33µs  med=22.01µs  max=433.5µs p(90)=107.47µs p(95)=118.98µs
     http_req_tls_handshaking.......: avg=0s      min=0s       med=0s       max=0s      p(90)=0s       p(95)=0s      
     http_req_waiting...............: avg=1.25ms  min=376.11µs med=771.18µs max=39.86ms p(90)=3.83ms   p(95)=4.29ms  
     http_reqs......................: 10004   30.138251/s
     iteration_duration.............: avg=1.21s   min=18.37ms  med=1.2s     max=2s      p(90)=1.75s    p(95)=1.88s   
     iterations.....................: 10001   30.129214/s
     time_lapse.....................: avg=12.9143 min=5        med=9        max=82      p(90)=24       p(95)=35      
     vus............................: 0       min=0       max=101
     vus_max........................: 102     min=102     max=102
     ws_connecting..................: avg=3.68ms  min=3.68ms   med=3.68ms   max=3.68ms  p(90)=3.68ms   p(95)=3.68ms  
     ws_msgs_received...............: 10001   30.129214/s
     ws_msgs_sent...................: 2       0.006025/s
     ws_sessions....................: 1       0.003013/s
```

#### e2-standard-4

The results are:

``` txt
          /\      |‾‾| /‾‾/   /‾‾/   
     /\  /  \     |  |/  /   /  /    
    /  \/    \    |     (   /   ‾‾\  
   /          \   |  |\  \ |  (‾)  | 
  / __________ \  |__| \__\ \_____/ .io

  execution: local
     script: test-suite-1.js
     output: -

  scenarios: (100.00%) 3 scenarios, 102 max VUs, 5m40s max duration (incl. graceful stop):
           * subscribe: 1 iterations shared among 1 VUs (maxDuration: 5m0s, exec: subscribe, gracefulStop: 30s)
           * ingestion: 100 iterations for each of 100 VUs (maxDuration: 5m0s, exec: ingestion, startTime: 5s, gracefulStop: 30s)
           * consumption: 1 iterations shared among 1 VUs (maxDuration: 10s, exec: consumption, startTime: 5m0s, gracefulStop: 30s)

INFO[0162] Expected: 10000; Actual: 10000                source=console

running (5m31.6s), 000/102 VUs, 10001 complete and 1 interrupted iterations
subscribe   ✗ [--------------------------------------] 1 VUs    5m30.0s/5m0s  0/1 shared iters
ingestion   ✓ [======================================] 100 VUs  2m35.6s/5m0s  10000/10000 iters, 100 per VU
consumption ✓ [======================================] 1 VUs    00.0s/10s     1/1 shared iters

     ✓ status was 202
     ✓ data units were received
     ✓ data units was sent
     ✓ data units were all stored

     █ setup

     █ teardown

     checks.........................: 100.00% ✓ 13960     ✗ 0    
     data_received..................: 3.4 MB  10 kB/s
     data_sent......................: 5.9 MB  18 kB/s
     http_req_blocked...............: avg=9.32µs   min=2.29µs   med=5.97µs  max=3.23ms   p(90)=7.69µs  p(95)=8.35µs 
     http_req_connecting............: avg=1.8µs    min=0s       med=0s      max=924.29µs p(90)=0s      p(95)=0s     
     http_req_duration..............: avg=2.63ms   min=985.85µs med=1.99ms  max=54.41ms  p(90)=4.41ms  p(95)=5.67ms 
       { expected_response:true }...: avg=2.63ms   min=985.85µs med=1.99ms  max=54.41ms  p(90)=4.41ms  p(95)=5.67ms 
     http_req_failed................: 0.00%   ✓ 0         ✗ 10004
     http_req_receiving.............: avg=55.51µs  min=15.32µs  med=49.91µs max=908.79µs p(90)=66.76µs p(95)=75.19µs
     http_req_sending...............: avg=39.11µs  min=12.21µs  med=31.52µs max=6.39ms   p(90)=47.36µs p(95)=56.24µs
     http_req_tls_handshaking.......: avg=0s       min=0s       med=0s      max=0s       p(90)=0s      p(95)=0s     
     http_req_waiting...............: avg=2.54ms   min=917.08µs med=1.9ms   max=54.22ms  p(90)=4.32ms  p(95)=5.59ms 
     http_reqs......................: 10004   30.169476/s
     iteration_duration.............: avg=1.25s    min=8.93ms   med=1.26s   max=2s       p(90)=1.8s    p(95)=1.9s   
     iterations.....................: 10001   30.160428/s
     time_lapse.....................: avg=125.4684 min=9        med=29      max=2045     p(90)=120     p(95)=1090   
     vus............................: 0       min=0       max=101
     vus_max........................: 102     min=102     max=102
     ws_connecting..................: avg=5.51ms   min=5.51ms   med=5.51ms  max=5.51ms   p(90)=5.51ms  p(95)=5.51ms 
     ws_msgs_received...............: 10001   30.160428/s
     ws_msgs_sent...................: 2       0.006031/s
     ws_sessions....................: 1       0.003016/s
```

## Test Suite 2

This suite is responsible for testing the time it takes for alarms to be dispatched by the system.

### Throughput Test

The test setup is as follows:

- 1 decoder;
- 1 processor;
- 100 devices;
- 1 rule that matches around 10% of the data units sent;
- Data is sent every second by each device for 100 iterations;

The `time_lapse` metric indicates the time, in milliseconds, that a notification takes to be processed by the system and sent via websocket.
The `time_lapse_full` metric indicates the time, in milliseconds, between the arrival of a data unit and the dispatch of a notification via websocket by the system.

#### Local Test

The results are:

``` txt

          /\      |‾‾| /‾‾/   /‾‾/   
     /\  /  \     |  |/  /   /  /    
    /  \/    \    |     (   /   ‾‾\  
   /          \   |  |\  \ |  (‾)  | 
  / __________ \  |__| \__\ \_____/ .io

  execution: local
     script: test-suite-2.js
     output: statsd (localhost:8125)

  scenarios: (100.00%) 3 scenarios, 102 max VUs, 5m40s max duration (incl. graceful stop):
           * subscribe: 1 iterations shared among 1 VUs (maxDuration: 5m0s, exec: subscribe, gracefulStop: 30s)
           * ingestion: 100 iterations for each of 100 VUs (maxDuration: 5m0s, exec: ingestion, startTime: 5s, gracefulStop: 30s)
           * consumption: 1 iterations shared among 1 VUs (maxDuration: 10s, exec: consumption, startTime: 5m0s, gracefulStop: 30s)

INFO[0002] Waiting for 6 minutes for rules to apply      source=console
INFO[0500] Expected: 1000; Actual: 991                   source=console

running (11m00.9s), 000/102 VUs, 10002 complete and 0 interrupted iterations
subscribe   ✓ [======================================] 1 VUs    3m16.9s/5m0s  1/1 shared iters
ingestion   ✓ [======================================] 100 VUs  2m34.8s/5m0s  10000/10000 iters, 100 per VU
consumption ✓ [======================================] 1 VUs    00.1s/10s     1/1 shared iters

     ✓ status was 202
     ✓ notifications were sent
     ✓ notifications were all stored

     █ setup

     █ teardown

     checks.........................: 100.00% ✓ 10992     ✗ 0    
     data_received..................: 1.3 MB  1.9 kB/s
     data_sent......................: 5.9 MB  8.9 kB/s
     http_req_blocked...............: avg=25.55µs   min=1.56µs   med=4.09µs   max=41.57ms  p(90)=15.06µs  p(95)=17.74µs 
     http_req_connecting............: avg=17.05µs   min=0s       med=0s       max=41.42ms  p(90)=0s       p(95)=0s      
     http_req_duration..............: avg=1.47ms    min=392.23µs med=864.4µs  max=1.28s    p(90)=2.64ms   p(95)=4.48ms  
       { expected_response:true }...: avg=1.47ms    min=392.23µs med=864.4µs  max=1.28s    p(90)=2.64ms   p(95)=4.48ms  
     http_req_failed................: 0.00%   ✓ 0         ✗ 10003
     http_req_receiving.............: avg=48.51µs   min=9.46µs   med=31.52µs  max=1.34ms   p(90)=99.25µs  p(95)=147.77µs
     http_req_sending...............: avg=37.79µs   min=10.28µs  med=24.16µs  max=976.55µs p(90)=86.15µs  p(95)=114.45µs
     http_req_tls_handshaking.......: avg=0s        min=0s       med=0s       max=0s       p(90)=0s       p(95)=0s      
     http_req_waiting...............: avg=1.39ms    min=353.31µs med=804.41µs max=1.28s    p(90)=2.46ms   p(95)=4.2ms   
     http_reqs......................: 10003   15.135254/s
     iteration_duration.............: avg=1.31s     min=54.52ms  med=1.25s    max=6m0s     p(90)=1.8s     p(95)=1.9s    
     iterations.....................: 10002   15.133741/s
     time_lapse.....................: avg=20.180626 min=4        med=7        max=1046     p(90)=14       p(95)=22      
     time_lapse_full................: avg=78.078708 min=9        med=16       max=2433     p(90)=36       p(95)=62.5    
     vus............................: 0       min=0       max=101
     vus_max........................: 102     min=102     max=102
     ws_connecting..................: avg=798.89ms  min=798.89ms med=798.89ms max=798.89ms p(90)=798.89ms p(95)=798.89ms
     ws_msgs_received...............: 992     1.500967/s
     ws_msgs_sent...................: 2       0.003026/s
     ws_session_duration............: avg=3m15s     min=3m15s    med=3m15s    max=3m15s    p(90)=3m15s    p(95)=3m15s   
     ws_sessions....................: 1       0.001513/s
```

#### e2-standard-4

The results are:

``` txt
          /\      |‾‾| /‾‾/   /‾‾/   
     /\  /  \     |  |/  /   /  /    
    /  \/    \    |     (   /   ‾‾\  
   /          \   |  |\  \ |  (‾)  | 
  / __________ \  |__| \__\ \_____/ .io

  execution: local
     script: test-suite-2.js
     output: -

  scenarios: (100.00%) 3 scenarios, 102 max VUs, 5m40s max duration (incl. graceful stop):
           * subscribe: 1 iterations shared among 1 VUs (maxDuration: 5m0s, exec: subscribe, gracefulStop: 30s)
           * ingestion: 100 iterations for each of 100 VUs (maxDuration: 5m0s, exec: ingestion, startTime: 5s, gracefulStop: 30s)
           * consumption: 1 iterations shared among 1 VUs (maxDuration: 10s, exec: consumption, startTime: 5m0s, gracefulStop: 30s)

INFO[0045] Waiting for 2 minutes for rules to apply      source=console
INFO[0466] Expected: 1000; Actual: 0                     source=console
INFO[0466] Notifications stored: 955                     source=console

running (7m51.5s), 000/102 VUs, 10002 complete and 0 interrupted iterations
subscribe   ✓ [======================================] 1 VUs    5m00.8s/5m0s  1/1 shared iters
ingestion   ✓ [======================================] 100 VUs  2m57.1s/5m0s  10000/10000 iters, 100 per VU
consumption ✓ [======================================] 1 VUs    01.0s/10s     1/1 shared iters

     ✓ status was 202
     ✓ notifications were all stored

     █ setup

     █ teardown

     checks.........................: 100.00% ✓ 10001     ✗ 0    
     data_received..................: 902 kB  1.9 kB/s
     data_sent......................: 5.9 MB  13 kB/s
     http_req_blocked...............: avg=1.92ms   min=1.34µs   med=15.36µs  max=211.65ms p(90)=19.16µs  p(95)=21.36µs 
     http_req_connecting............: avg=1.9ms    min=0s       med=0s       max=211.36ms p(90)=0s       p(95)=0s      
     http_req_duration..............: avg=186.79ms min=171.42ms med=183.75ms max=378.19ms p(90)=192.25ms p(95)=198.95ms
       { expected_response:true }...: avg=186.79ms min=171.42ms med=183.75ms max=378.19ms p(90)=192.25ms p(95)=198.95ms
     http_req_failed................: 0.00%   ✓ 0         ✗ 10003
     http_req_receiving.............: avg=135.7µs  min=10.31µs  med=139.78µs max=1.12ms   p(90)=178.87µs p(95)=200.96µs
     http_req_sending...............: avg=103.34µs min=10.69µs  med=106.59µs max=491.46µs p(90)=137.21µs p(95)=154.55µs
     http_req_tls_handshaking.......: avg=0s       min=0s       med=0s       max=0s       p(90)=0s       p(95)=0s      
     http_req_waiting...............: avg=186.55ms min=171.19ms med=183.51ms max=377.97ms p(90)=192.02ms p(95)=198.78ms
     http_reqs......................: 10003   21.216768/s
     iteration_duration.............: avg=1.48s    min=682.23ms med=1.43s    max=5m0s     p(90)=1.99s    p(95)=2.09s   
     iterations.....................: 10002   21.214647/s
     vus............................: 0       min=0       max=101
     vus_max........................: 102     min=102     max=102
     ws_connecting..................: avg=369.54ms min=369.54ms med=369.54ms max=369.54ms p(90)=369.54ms p(95)=369.54ms
     ws_msgs_received...............: 1       0.002121/s
     ws_msgs_sent...................: 2       0.004242/s
     ws_session_duration............: avg=5m0s     min=5m0s     med=5m0s     max=5m0s     p(90)=5m0s     p(95)=5m0s    
     ws_sessions....................: 1       0.002121/s
```

With less VUs the node is capable of using websocket to send real-time notifications.

``` txt
          /\      |‾‾| /‾‾/   /‾‾/   
     /\  /  \     |  |/  /   /  /    
    /  \/    \    |     (   /   ‾‾\  
   /          \   |  |\  \ |  (‾)  | 
  / __________ \  |__| \__\ \_____/ .io

  execution: local
     script: test-suite-2.js
     output: -

  scenarios: (100.00%) 3 scenarios, 52 max VUs, 5m40s max duration (incl. graceful stop):
           * subscribe: 1 iterations shared among 1 VUs (maxDuration: 5m0s, exec: subscribe, gracefulStop: 30s)
           * ingestion: 100 iterations for each of 50 VUs (maxDuration: 5m0s, exec: ingestion, startTime: 5s, gracefulStop: 30s)
           * consumption: 1 iterations shared among 1 VUs (maxDuration: 10s, exec: consumption, startTime: 5m0s, gracefulStop: 30s)

INFO[0026] Waiting for 10 minutes for rules to apply     source=console
INFO[0771] Expected: 500; Actual: 491                    source=console
INFO[0927] Notifications stored: 527                     source=console

running (15m33.3s), 00/52 VUs, 5002 complete and 0 interrupted iterations
subscribe   ✓ [======================================] 1 VUs   2m42.3s/5m0s  1/1 shared iters
ingestion   ✓ [======================================] 50 VUs  2m53.3s/5m0s  5000/5000 iters, 100 per VU
consumption ✓ [======================================] 1 VUs   00.9s/10s     1/1 shared iters

     ✓ status was 202
     ✓ notifications were sent
     ✓ notifications were all stored

     █ setup

     █ teardown

     checks.........................: 100.00% ✓ 5492     ✗ 0   
     data_received..................: 639 kB  685 B/s
     data_sent......................: 3.0 MB  3.2 kB/s
     http_req_blocked...............: avg=1.97ms     min=1.76µs   med=4.95µs   max=210.07ms p(90)=18.39µs  p(95)=21.44µs 
     http_req_connecting............: avg=1.96ms     min=0s       med=0s       max=209.79ms p(90)=0s       p(95)=0s      
     http_req_duration..............: avg=198.39ms   min=173.14ms med=184.13ms max=2.65s    p(90)=237.66ms p(95)=290.36ms
       { expected_response:true }...: avg=198.39ms   min=173.14ms med=184.13ms max=2.65s    p(90)=237.66ms p(95)=290.36ms
     http_req_failed................: 0.00%   ✓ 0        ✗ 5003
     http_req_receiving.............: avg=86.82µs    min=10.07µs  med=56.25µs  max=8.76ms   p(90)=165.88µs p(95)=189.43µs
     http_req_sending...............: avg=61.05µs    min=10.98µs  med=35.07µs  max=1.43ms   p(90)=126.55µs p(95)=143.85µs
     http_req_tls_handshaking.......: avg=0s         min=0s       med=0s       max=0s       p(90)=0s       p(95)=0s      
     http_req_waiting...............: avg=198.24ms   min=172.96ms med=183.99ms max=2.65s    p(90)=237.53ms p(95)=290.21ms
     http_reqs......................: 5003    5.36033/s
     iteration_duration.............: avg=1.56s      min=683.73ms med=1.41s    max=10m25s   p(90)=1.96s    p(95)=2.07s   
     iterations.....................: 5002    5.359258/s
     time_lapse.....................: avg=205.211813 min=100      med=112      max=2522     p(90)=188      p(95)=323     
     time_lapse_full................: avg=390.197556 min=197      med=218      max=7086     p(90)=337      p(95)=478     
     vus............................: 0       min=0      max=51
     vus_max........................: 52      min=52     max=52
     ws_connecting..................: avg=2.11s      min=2.11s    med=2.11s    max=2.11s    p(90)=2.11s    p(95)=2.11s   
     ws_msgs_received...............: 492     0.52714/s
     ws_msgs_sent...................: 2       0.002143/s
     ws_session_duration............: avg=2m39s      min=2m39s    med=2m39s    max=2m39s    p(90)=2m39s    p(95)=2m39s   
     ws_sessions....................: 1       0.001071/s
```