#!/usr/bin/sh

for i in $(seq -f "%03g" 1 $1); do
    echo \"P$i - Milesight EM300-TH\",\"$(cat /proc/sys/kernel/random/uuid)\",\"$(shuf -i 35-45 -n 1).$(shuf -i 000001-999999 -n 1)\",\"-$(shuf -i 5-10 -n 1).$(shuf -i 000001-999999 -n 1)\"
done

