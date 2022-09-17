# Performance

This document details the performance expected of the system.

Current version:

- `iot-core` : `0.1.20`
- `system` : `0.10.0`

## Introduction

The system was tested with the `k6` tool.

## Test Suite 1

The test setup is as follows:

- 1 decoder;
- 1 processor;
- 500 devices;
- Data is sent every second by each device for 60 iterations;

The results are:

```
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

## Test Suite 2

The test setup is as follows:

- 1 decoder;
- 1 processor;
- 100 devices;
- Data is sent every second by each device for 100 iterations;

The results are:

```
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

running (5m31.6s), 000/102 VUs, 10001 complete and 1 interrupted iterations
subscribe   ✗ [--------------------------------------] 1 VUs    5m30.0s/5m0s  0/1 shared iters
ingestion   ✓ [======================================] 100 VUs  4m15.4s/5m0s  10000/10000 iters, 100 per VU
consumption ✓ [======================================] 1 VUs    00.0s/10s     1/1 shared iters

     ✓ status was 202
     ✓ data units were received
     ✓ data unit was sent
     ✓ data unit were all stored

     █ setup

     █ teardown

     checks.........................: 100.00% ✓ 13470     ✗ 0    
     data_received..................: 3.4 MB  10 kB/s
     data_sent......................: 5.9 MB  18 kB/s
     http_req_blocked...............: avg=23.53µs  min=1.31µs   med=4.36µs   max=22.9ms   p(90)=17.33µs  p(95)=19.67µs 
     http_req_connecting............: avg=14.58µs  min=0s       med=0s       max=22.09ms  p(90)=0s       p(95)=0s      
     http_req_duration..............: avg=1.97ms   min=401.05µs med=969.52µs max=1.26s    p(90)=4.53ms   p(95)=4.79ms  
       { expected_response:true }...: avg=1.97ms   min=401.05µs med=969.52µs max=1.26s    p(90)=4.53ms   p(95)=4.79ms  
     http_req_failed................: 0.00%   ✓ 0         ✗ 10004
     http_req_receiving.............: avg=58.43µs  min=9.36µs   med=30.59µs  max=377.05µs p(90)=148.49µs p(95)=164.59µs
     http_req_sending...............: avg=50.43µs  min=10.7µs   med=24.26µs  max=422.29µs p(90)=115.87µs p(95)=131.41µs
     http_req_tls_handshaking.......: avg=0s       min=0s       med=0s       max=0s       p(90)=0s       p(95)=0s      
     http_req_waiting...............: avg=1.86ms   min=370.49µs med=912.14µs max=1.26s    p(90)=4.26ms   p(95)=4.52ms  
     http_reqs......................: 10004   30.167513/s
     iteration_duration.............: avg=2.49s    min=18.47ms  med=2.49s    max=3s       p(90)=2.9s     p(95)=2.95s   
     iterations.....................: 10001   30.158467/s
     time_lapse.....................: avg=27.7375  min=6        med=10       max=1439     p(90)=37       p(95)=45      
     vus............................: 0       min=0       max=101
     vus_max........................: 102     min=102     max=102
     ws_connecting..................: avg=935.86ms min=935.86ms med=935.86ms max=935.86ms p(90)=935.86ms p(95)=935.86ms
     ws_msgs_received...............: 10001   30.158467/s
     ws_msgs_sent...................: 2       0.006031/s
     ws_sessions....................: 1       0.003016/s
```
