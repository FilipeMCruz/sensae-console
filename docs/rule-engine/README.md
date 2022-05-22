# Rules Engine

Current version:

- `iot-core` : `0.1.16`
- `system` : `0.7.0`

## Introduction

A rule engine has the responsibility to execute rules against incoming data.

In this project a rule can be something like:

1. If valve X is open for more than 10 minutes, close it;
2. If temperature sensors in X place reported an average of 40 ºC for the last 10 minutes, sound the alarm;

This rule can be categorized with:

- Conditions;
- Actions;
- Facts;

Facts are inserted in a rule engine.
If this facts match a condition, the condition's action is triggered.

## Technology

[Drools](https://www.drools.org/) is an open-source rule engine widely used in the industry. It has support for sliding windows of time, integrates with `iot-core` package with ease since it is written in `java`, can be embedded in a server running java and supports dynamic loading rules at runtime.

Drools allows users to write and submit rules. As an example the following rule verifies if the inserted `data` belongs to Project #003, has a measure of type `PropertyName.TRIGGER` and it's `device` has not been inserted yet. When all this conditions match, then a new `ValveDevice`, for the device that sent the data is inserted into Drools and an alarm is sent informing that a new valve has been created.

``` drl
rule "Create new valve device if it belongs to Project #003 and is new"
    when
        data : ProcessedSensorDataDTO(
            getSensorData().hasProperty(PropertyName.TRIGGER)
        )
        exists DeviceRecordBasicEntryDTO(label == "Project" && content == "#003") from data.device.records.entry
        not(ValveDevice(deviceId == data.device.id))
    then
        ValveDevice valve = new ValveDevice();
        valve.setDeviceId(data.device.id);
        insert(valve)
        dispatcher.send(AlarmBuilder.create()
                          .setName("valve")
                          .setDescription("New Valve")
                          .setDeviceId(data.device.id)
                          .createAlarm());
end
```

## Architecture

The following diagram represents the idealized architecture:

![logical-level2](diagrams/logical-view-level2.svg)

- **Rule Engine Frontend**: this container is responsible for interacting with managers. Users can see, create, edit and delete rules using the UI.
- **Rule Engine Backend**: this container is responsible for verifying that the submitted rules can be compiled, if so it notifies the slaves that a rule was updated, deleted or added.
- **Rule Engine Slave**: this container is responsible for executing rules when new sensor data arrives to it from the message broker. When facts match a rule conditions alarms are produced. This alarms are send to the message broker so that other containers are notified about them.
- **Rule Engine Database**: this container is responsible for storing all rules.
- **Message Broker**: this container is responsible for sending new sensor data to **Rule Engine Slave** trough `sensor.topic`, send updates about rules to **Rule Engine Slave** trough `internal.topic`, let **Rule Engine Backend** publish new updates about rules in `internal.topic` and let **Rule Engine Slave** publish new alerts in `alerts.topic`.

## Rules examples

Some [examples](assets) of possible rules are provided.

## Further Discussion

As always, changes/improvements to this data model are expected.
