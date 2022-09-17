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

### Availability Test

The test setup is as follows:

- 1 decoder;
- 1 processor;
- 100 devices;
- Data is sent every second by each device for 100 iterations;

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

The `time_lapse` metric indicates the time, in milliseconds, that a message takes to be processed by the system and sent via websocket.

## Test Suite 2

This suite is responsible for testing the time it takes for alarms to be dispatched by the system.

### Availability Test

The test setup is as follows:

- 1 decoder;
- 1 processor;
- 100 devices;
- 1 rule that matches around 10% of the data units sent;
- Data is sent every second by each device for 100 iterations;

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

The `time_lapse` metric indicates the time, in milliseconds, that a notification takes to be processed by the system and sent via websocket.
The `time_lapse_full` metric indicates the time, in milliseconds, between the arrival of a data unit and the dispatch of a notification via websocket by the system.
