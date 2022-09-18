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

INFO[0002] Waiting for 2 minutes for rules to apply      source=console
INFO[0422] Expected: 1000; Actual: 0                     source=console

running (7m00.6s), 000/102 VUs, 10002 complete and 0 interrupted iterations
subscribe   ✓ [======================================] 1 VUs    5m00.0s/5m0s  1/1 shared iters
ingestion   ✓ [======================================] 100 VUs  2m37.1s/5m0s  10000/10000 iters, 100 per VU
consumption ✓ [======================================] 1 VUs    00.0s/10s     1/1 shared iters

     ✓ status was 202
     ✗ notifications were all stored
      ↳  0% — ✓ 0 / ✗ 1

     █ setup

     █ teardown

     checks.........................: 99.99% ✓ 10000     ✗ 1    
     data_received..................: 902 kB 2.1 kB/s
     data_sent......................: 5.9 MB 14 kB/s
     http_req_blocked...............: avg=9.12µs  min=2.64µs   med=5.64µs  max=1.07ms   p(90)=6.99µs  p(95)=7.91µs
     http_req_connecting............: avg=1.95µs  min=0s       med=0s      max=976.44µs p(90)=0s      p(95)=0s    
     http_req_duration..............: avg=1.62ms  min=908.46µs med=1.43ms  max=65.8ms   p(90)=2.05ms  p(95)=2.56ms
       { expected_response:true }...: avg=1.62ms  min=908.46µs med=1.43ms  max=65.8ms   p(90)=2.05ms  p(95)=2.56ms
     http_req_failed................: 0.00%  ✓ 0         ✗ 10003
     http_req_receiving.............: avg=59.62µs min=15.73µs  med=56µs    max=1.9ms    p(90)=74.46µs p(95)=82.4µs
     http_req_sending...............: avg=38.01µs min=10.95µs  med=34.11µs max=6.04ms   p(90)=48.06µs p(95)=55.2µs
     http_req_tls_handshaking.......: avg=0s      min=0s       med=0s      max=0s       p(90)=0s      p(95)=0s    
     http_req_waiting...............: avg=1.52ms  min=830.51µs med=1.33ms  max=65.56ms  p(90)=1.96ms  p(95)=2.47ms
     http_reqs......................: 10003  23.783075/s
     iteration_duration.............: avg=1.33s   min=9.1ms    med=1.29s   max=5m0s     p(90)=1.83s   p(95)=1.91s 
     iterations.....................: 10002  23.780697/s
     vus............................: 1      min=0       max=101
     vus_max........................: 102    min=102     max=102
     ws_connecting..................: avg=5.77ms  min=5.77ms   med=5.77ms  max=5.77ms   p(90)=5.77ms  p(95)=5.77ms
     ws_msgs_received...............: 1      0.002378/s
     ws_msgs_sent...................: 2      0.004755/s
     ws_session_duration............: avg=5m0s    min=5m0s     med=5m0s    max=5m0s     p(90)=5m0s    p(95)=5m0s  
     ws_sessions....................: 1      0.002378/s
```
