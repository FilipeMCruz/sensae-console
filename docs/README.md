# Docs

This document aggregates all important info and documentation about location tracking.
It's intended mostly for developers and stakeholders.

## Functional Requirements

This section defines the functionalities and operations that the system supports.

### Actors

The system actors are:

- **Data Admin**: The Data Admin functions are analyzing the gathered information as he wishes;

### Use cases

| Use Cases | Description                                                                |
| --------- | -------------------------------------------------------------------------- |
| **UC01**  | As the data admin I want to see the live information for all device        |
| **UC02**  | As the data admin I want to see the live information for a specific device |

## Architecture

This section represents the architecture from different views and levels of abstraction (following the c4 and 4+1 models).

### Logical View - System Level

Logical View of the system and it's interactions with external systems and actors.

![logical-view-level1](diagrams/logical-view-level1.svg)

### Logical View - Container Level

Logical View of the containers that constitute the system and it's interactions.

![logical-view-level2](diagrams/logical-view-level2.svg)

The system is composed by the following containers:

- **Location Tracking Frontend**: Frontend that displays live information in a map;
- **Location Tracking Backend**: Backend that sends live information to the frontend;
- **Message Broker**: Container responsible for routing messages/events sent by the containers;
- **LGT 92 GPS Sensor Processor**: Container responsible for transforming the received data (LGT 92 GPS Sensor Data) into something that the system understands (GPS Sensor Data);
- **LGT 92 GPS Sensor Gateway**: Container responsible for receiving data (LGT 92 GPS Sensor Data) from the outside and propagate it in the system;
- **Data Gateway**: Container responsible for proxying sensor data requests to the assigned Sensor Gateway.

### Process View - Container Level

Process view of several UCs to display the system flow.

#### System Communication Process View - Container Level

**Description**: Once the environment is started containers subscribe to events in other containers. Some times containers may subscribe to new events or unsubscribe from old ones.

![process-view-level2-uc00](diagrams/process-view-level2-uc00.svg)

#### UC01 Process View - Container Level

**Description**: As the data admin I want to see the live information for all device.

Information updated from the received event:

![process-view-level2-uc01](diagrams/process-view-level2-uc01.svg)

#### UC02 Process View - Container Level

**Description**: As the data admin I want to see the live information for a specific device.

Information updated from the received event:

![process-view-level2-uc02](diagrams/process-view-level2-uc02.svg)

This flow is almost the same as the UC01, the only difference is that in this one a filter is applied to send only the requested data.

### Logical View - Component Level

Logical View of each container's component and it's interactions with other components.

#### Location Tracking Frontend

Currently the adopted architecture has, as reference architecture, the [Onion Architecture](https://jeffreypalermo.com/2008/07/the-onion-architecture-part-1/).
The following diagram describes it from a logical view.

![logical-view-level3-location-tracking-frontend](diagrams/logical-view-level3-location-tracking-frontend.svg)

#### Tracking Devices Backend

Currently the adopted architecture has, as reference architecture, the [Onion Architecture](https://jeffreypalermo.com/2008/07/the-onion-architecture-part-1/).
The following diagram describes it from a logical view.

![logical-view-level3-location-tracking-backend](diagrams/logical-view-level3-location-tracking-backend.svg)

#### LGT 92 GPS Sensor Processor

Currently the adopted architecture has, as reference architecture, the [Onion Architecture](https://jeffreypalermo.com/2008/07/the-onion-architecture-part-1/).
The following diagram describes it from a logical view.

![logical-view-level3-lgt-92-gps-sensor-processor](diagrams/logical-view-level3-lgt-92-gps-sensor-processor.svg)

#### LGT 92 GPS Sensor Gateway

Currently the adopted architecture has, as reference architecture, the [Onion Architecture](https://jeffreypalermo.com/2008/07/the-onion-architecture-part-1/).
The following diagram describes it from a logical view.

![logical-view-level3-lgt-92-gps-sensor-gateway](diagrams/logical-view-level3-lgt-92-gps-sensor-gateway.svg)

## API

This section will present the API that each backend service exposes.

### LGT 92 GPS Sensor Gateway API

This section will present every endpoint available in this service.
This information can be consulted [here](http://localhost:8080/swagger-ui/index.html) (this container must be running in dev mode).
The **endpoint** to register new lgt 92 gps sensor data is `/sensor-data`.
In production this endpoint can't be accessed, instead requests must be made to **Sensor Data Gateway**.

### Sensor Data Gateway

**Endpoint**: POST to `/lgt92-gps-sensor-data`

**Data Example**:

``` json
{
  "app_eui": "16217534746BD1D3",
  "decoded": {
    "payload": {
      "ALARM_status": false,
      "Accuracy": 3,
      "Altitude": 0,
      "BatV": 3.067,
      "FW": 153,
      "LON": "ON",
      "Latitude": 38.750244,
      "Longitude": -9.229148,
      "MD": "Disable",
      "Pitch": 0,
      "Roll": 0
    },
    "status": "success"
  },
  "dev_eui": "A84041CB91826E2F",
  "devaddr": "06000048",
  "downlink_url": "https://console.helium.com/api/v1/down/806c3543-450d-4d34-9948-582d130a38d2/ekQ508Ijd1Ip8L2nM6JjRBmOxs2luTz6/1194ea60-9a40-447a-b535-affba26da111",
  "fcnt": 188,
  "hotspots": [
    {
      "channel": 2,
      "frequency": 868.5,
      "id": "11Weepe1gHxjcaSq8cmh2QE8a9GxssEPXgbVifRvyNkprWc83BJ",
      "lat": 38.75500733680766,
      "long": -9.241637744431221,
      "name": "modern-gauze-rattlesnake",
      "reported_at": 1616349557723,
      "rssi": -113.0,
      "snr": -14.800000190734863,
      "spreading": "SF12BW125",
      "status": "success"
    }
  ],
  "id": "1194ea60-9a40-447a-b535-affba26ea111",
  "metadata": {
    "adr_allowed": false,
    "labels": [
      {
        "id": "036650d9-fb6c-48f3-99df-55773d816a5e",
        "name": "LGT_Decoder",
        "organization_id": "4004095e-8a2c-46a5-b9ef-7dbc4dbb1258"
      },
      {
        "id": "303896f0-8799-4328-a6fc-685c541b9cad",
        "name": "Cargo",
        "organization_id": "4004095e-8a2c-46a5-b9ef-7dbc4dbb1258"
      },
      {
        "id": "cac06b4e-b6fb-41d5-8861-ec836d2c8de0",
        "name": "IntegrationTest",
        "organization_id": "4004095e-8a2c-46a5-b9ef-7dbc4dbb1258"
      },
      {
        "id": "f5f80b55-f41b-41af-9e70-f7be1c5df928",
        "name": "Domus",
        "organization_id": "4004095e-8a2c-46a5-b9ef-7dbc4dbb1258"
      }
    ],
    "multi_buy": 1,
    "organization_id": "4004095e-8a2c-46a5-b9ef-7dbc4dbb1258"
  },
  "name": "Sharespot_Fernao #4",
  "payload": "Ak9IJP9zLKQL+yM=",
  "payload_size": 11,
  "port": 2,
  "reported_at": 1616349557723,
  "uuid": "979abd51-e754-4b02-a405-41e5c41c4b89"
}

```

This is the resource to point to, as an `http integration`, in helium console for LGT92 based sensors.

### Location Tracking Backend API

This section will present every endpoint available in this service.
Since the communication is made using GraphQL the only two endpoints are `/graphql` to request a subscription and `/subscriptions`.

#### Consult All GPS sensors live Data

**Endpoint**: `/graphql`

**Query**:

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

**Query**:

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

## Data Flow Diagram

On a Higher level data flows in the system as represented in here:

![data-flow](diagrams/data-flow.svg)
