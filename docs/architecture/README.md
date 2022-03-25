# Architecture

This section represents the architecture from different views and levels of abstraction (following the c4 and 4+1 models).

## Logical View - System Level

Logical View of the system and it's interactions with external systems and actors.

![logical-view-level1](diagrams/logical/logical-view-level1.svg)

## Logical View - Container Level

Logical View of the containers that constitute the system and it's interactions.

![logical-view-level2](diagrams/logical/logical-view-level2.svg)

The system is composed by the following containers:

- **UI Aggregator**: Container responsible for loading other frontend;
- **Fleet Management Frontend**: Frontend that displays live information in a map;
- **Fleet Management Backend**: Backend that receives correct GPS data, stores it and sends it as live information to the frontend;
- **Fleet Management Database**: Database that stores GPS data received from **Fleet Management Backend**;
- **Device Records Frontend**: Frontend that allows the data admin to add, change and see information about a specific device;
- **Device Records Master Backend**: Backend that stores device data (records) and notifies slaves about changes to this data;
- **Device Records Slave Backend**: Backend that changes the data that goes trough him by adding specific device information;
- **Device Records Database**: Database that records information about each device;
- **Device Validator Backend**: Backend that verifies if the data that goes trough him is valid, (e.g. gps data does not point to the sea, temperature isn't 500 ºC);
- **Message Broker**: Container responsible for routing messages/events sent by the containers;
- **Data Processor Frontend**: Frontend that allows the data admin to add, change and see information about a device transformation;
- **Data Processor Slave Backend**: Backend responsible for transforming the received data into something that the system understands (though property mapping);
- **Data Processor Master Backend**: Backend responsible for notifying slaves that new data transformation are available;
- **Data Processor Database**: Database that records information about how each device type can be transformed (mapping);
- **Data Decoder Frontend**: Frontend that allows the data admin to add, change and see the content of script used to decode a device data (though scripting);
- **Data Decoder Slave Backend**: Backend responsible for transforming the received data into something that the system understands though scripts;
- **Data Decoder Master Backend**: Backend responsible for notifying slaves that new data decoders are available;
- **Data Decoder Database**: Database that records information about how each device type can be decoded (scripts);
- **Data Gateway**: Backend responsible for proxying sensor data requests to the assigned **Data Processor Slave** or **Data Decoder Slave**;
- **Identity Management Frontend**: Frontend responsible for requesting user identification (to Azure active directory), provide access to the environment and manage identity/access/ownership issues inside the environment;
- **Identity Management Backend**: Backend responsible for validating user credentials and handle device ownership, domain/company permissions and users domain;
- **Identity Management Slave Backend**: Backend that changes the data that goes trough him by adding device ownership information;
- **Identity Management Database**: Database that records information about the structure of the organization;
- **Data Store**: Backend responsible for recording data in the defined data flow stage, e.g. record every invalid gps data, or, record every data that goes out of data decoder slave;
- **Data Store Database**: Database that records information given by **Data Store**, has the purpose of acting like a Data Lake;
- **Data Relayer**: Backend responsible for routing data to **Data Gateway**.

## Process View - Container Level

Process view of several UCs to display the system flow.

### System Communication Process View - Container Level

**Description**: Once the environment is started containers subscribe to events. Some times containers may subscribe to new events or unsubscribe from old ones.

![process-view-level2-uc00](diagrams/process/process-view-level2-uc00.svg)

### UC01 Process View - Container Level

**Description**: As a tenant I want to see the live information for all my devices.

Information updated from the received event:

![process-view-level2-uc01](diagrams/process/process-view-level2-uc01.svg)

### UC08 Process View - Container Level

**Description**: As a tenant I want to define how to decode data of a certain device type

Information updated from the received event:

![process-view-level2-uc08](diagrams/process/process-view-level2-uc08.svg)

This flow is almost the same as the UC01, the only difference is that in this one a filter is applied to send only the requested data.

### UC10 Process View - Container Level

**Description**: As a tenant I want to delete a script i've added for a specific device type.

![process-view-level2-uc10](diagrams/process/process-view-level2-uc10.svg)

### UC13 Process View - Container Level

**Description**: As a manager I want to see the information i've added to each device.

![process-view-level2-uc13](diagrams/process/process-view-level2-uc13.svg)

## Logical View - Component Level

Logical View of each container's component and it's interactions with other components.
Currently the adopted architecture for most container has, as reference architecture, the [Onion Architecture](https://jeffreypalermo.com/2008/07/the-onion-architecture-part-1/).

### Fleet Management Frontend

The following diagram describes it from a logical view.

![logical-view-level3-fleet-management-frontend](diagrams/logical/logical-view-level3-fleet-management-frontend.svg)

### Fleet Management Backend

The following diagram describes it from a logical view.

![logical-view-level3-fleet-management-backend](diagrams/logical/logical-view-level3-fleet-management-backend.svg)

### Device Records Frontend

The following diagram describes it from a logical view.

![logical-view-level3-device-records-frontend](diagrams/logical/logical-view-level3-device-records-frontend.svg)

### Device Records Master Backend

The following diagram describes it from a logical view.

![logical-view-level3-device-records-master-backend](diagrams/logical/logical-view-level3-device-records-master-backend.svg)

### Device Records Slave Backend

The following diagram describes it from a logical view.

![logical-view-level3-device-records-slave-backend](diagrams/logical/logical-view-level3-device-records-slave-backend.svg)

### Data Processor Slave Backend

The following diagram describes it from a logical view.

![logical-view-level3-data-processor-slave-backend](diagrams/logical/logical-view-level3-data-processor-slave-backend.svg)

### Data Processor Master Backend

The following diagram describes it from a logical view.

![logical-view-level3-data-processor-master-backend](diagrams/logical/logical-view-level3-data-processor-master-backend.svg)

### Data Processor Frontend

The following diagram describes it from a logical view.

![logical-view-level3-data-processor-frontend](diagrams/logical/logical-view-level3-data-processor-frontend.svg)

### Data Decoder Slave Backend

The following diagram describes it from a logical view.

![logical-view-level3-data-decoder-slave-backend](diagrams/logical/logical-view-level3-data-decoder-slave-backend.svg)

### Data Decoder Master Backend

The following diagram describes it from a logical view.

![logical-view-level3-data-decoder-master-backend](diagrams/logical/logical-view-level3-data-decoder-master-backend.svg)

### Data Decoder Frontend

The following diagram describes it from a logical view.

![logical-view-level3-data-decoder-frontend](diagrams/logical/logical-view-level3-data-decoder-frontend.svg)

### Identity Management Slave Backend

The following diagram describes it from a logical view.

![logical-view-level3-identity-management-slave-backend](diagrams/logical/logical-view-level3-identity-management-slave-backend.svg)

### Identity Management Master Backend

The following diagram describes it from a logical view.

![logical-view-level3-identity-management-master-backend](diagrams/logical/logical-view-level3-identity-management-master-backend.svg)

### Identity Management Frontend

The following diagram describes it from a logical view.

![logical-view-level3-identity-management-frontend](diagrams/logical/logical-view-level3-identity-management-frontend.svg)

### Data Gateway

The following diagram describes it from a logical view.

![logical-view-level3-data-gateway](diagrams/logical/logical-view-level3-data-gateway.svg)

### Data Store

The following diagram describes it from a logical view.

![logical-view-level3-data-store](diagrams/logical/logical-view-level3-data-store.svg)

### Data Validator

The following diagram describes it from a logical view.

![logical-view-level3-data-validator](diagrams/logical/logical-view-level3-data-validator.svg)
