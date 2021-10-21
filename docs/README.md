# Docs

This document aggregates all important info and documentation about location tracking. It's intended mostly for developers.

## Ideas/Future Work

This section lists all feature that could be important for this project and discusses the most important and complex ones.

- New backend service - Places Data Management (name, description, etc.)

## Relevant Documentation

- [AMQP - one message multiple consumers](https://stackoverflow.com/questions/10620976/rabbitmq-amqp-single-queue-multiple-consumers-for-same-message)

## Questions

This section lists all doubts about the current solution and it's ideas.

- Authorization? What about authentication? Use OAuth or rely on a local user registry?
- Authentication?

## Functional Requirements

This section defines the functionalities and operations that the system supports or will support in the future.

### Actors

The system actors are:

- **Data Admin**: The Data Admin functions are analyzing the gathered information as he wishes;

### Use cases

| Use Cases | Description                                                                | Importance | Progress |
| --------- | -------------------------------------------------------------------------- | ---------- | -------- |
| **UC01**  | As the data admin I want to see the live information for all device        | High       | Backend  |
| **UC02**  | As the data admin I want to see the live information for a specific device | Medium     | Backend  |
| **UC03**  | As the data admin I want to see the past information for all devices       | Low        | None     |
| **UC04**  | As the data admin I want to see the past information for a specific device | Low        | None     |

## Architecture

This section represents the MVP solution architecture from different views and levels of abstraction (following the c4 and 4+1 models).

### Logical View - Context Level

Logical View of the system and it's interactions with external systems and actors.

![logical-view-level1](diagrams/logical-view-level1.svg)

### Logical View - Container Level

```TODO```

Logical View of the containers that constitute the system and it's interactions.

![logical-view-level2](diagrams/logical-view-level2.svg)

### Process View - Container Level

Process view of several UCs to display the system flow.

#### UC01 Process View - Container Level

```TODO```

Subscription made from UI:

![process-view-level2-uc02](diagrams/process-view-level2-uc02-p1.svg)

Information updated from the received event:

![process-view-level2-uc02](diagrams/process-view-level2-uc02-p2.svg)

#### UC02 Process View - Container Level

The flow is the same as UC01 - [info](#UC01-Process-View---Container-Level)

#### Logical View - Component Level - Tracking Devices Backend

```TODO``` ainda não é bem assim

Currently the adopted architecture has, as reference architecture, the [Onion Architecture](https://jeffreypalermo.com/2008/07/the-onion-architecture-part-1/).
The following diagram describes it from a logical view. IT also follows [domain driven design](https://www.domainlanguage.com/wp-content/uploads/2016/05/DDD_Reference_2015-03.pdf) concepts.

![logical-view-level3-location-tracking-backend](diagrams/logical-view-level3-location-tracking-backend.svg)

## Domain model

This section will present the domain model for each service/backend.

### Location Tracking Backend Domain model

```TODO```

## API

This section will present the API that each backend service exposes.

### Data Management Backend API ```TODO```

This section will present every endpoint available in this service.
This information can be consulted [here](http://localhost:8080/swagger-ui/index.html) (this container must be running in dev mode).

#### Register LGT92 Sensor Data

**Endpoint**: POST to /lgt-92-sensors

This is the resource to point to, as an `http integration`, in helium console for LGT92 based sensors.

### Location Tracking Backend API

This section will present every endpoint available in this service.
```TODO``` This information can be consulted [here](http://localhost:8080/swagger-ui/index.html) (this container must be running in dev mode).
Since the communication is made using GraphQL the only endpoint is `/graphql`.

#### Consult All GPS sensors live Data

**Endpoint**: `/graphql`

**Subscription**:

``` graphql

subscription {
  locations() {
    dataId
    deviceId
    reportedAt
    data {
        longitude
        latitude
    }
  }
}

```

This is the resource used to subscribe to changes in the gps location of all sensors registered in the network.

#### Consult a Specific GPS Sensor live data

**Endpoint**: `/graphql`

**Subscription**:

``` graphql

subscription {
  location(deviceId: "XXX") {
    dataId
    deviceId
    reportedAt
    data {
        longitude
        latitude
    }
  }
}

```

This is the resource used to subscribe to changes in the gps location of a specific sensor registered in the network.

### Overview

Backend enpoints can only be access by the api gateway.
Currently this are the available endpoints:

- _$HOST_/api/sharespot-location-tracking-backend/graphql - [info](#Tracking-Devices-Backend-API)
