# API

This section will present the API that each backend service exposes.

As explained in identity-management [docs](../identity-management/README.md) all APIs, except `Data Gateway API` require a JWT token for authentication/authorization.

## Data Gateway API

This section will present every endpoint available in this service.
This information can be consulted [here](http://localhost:8080/swagger-ui/index.html) (this container must be running in dev mode).
The **endpoint** to register new sensor data is `/sensor-data/{infoType}/{sensorType}`.
In production this endpoint can't be accessed, instead requests must be made to **Data Relayer**.

### Register new sensor data

**Endpoint**: POST to `/sensor-data/{infoType}/{sensorType}`

**Path Variables**:

- **infoType**: `decoded` or `encoded`
- **sensorType**: any string that represents a sensor type (this will later be correlated with the sensor type configured with the data processor and decoder)

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

This is the resource to point to, as an `http integration`, in helium console for sensor data.

If a secret key was defined to prevent malicious use, an HTTP Authorization Header has to be sent with the secret key.

## Data Processor Master Backend API

This section will present every endpoint available in this service.
Since the communication is made using GraphQL, and there are no `subscriptions` the only endpoint is `/graphql`.

### Index a Data Transformation (new or updated transformation)

``` graphql
mutation index($transformation: DataTransformationInput){
  index(transformation: $transformation){
    data{
      type
    }
    entries{
      oldPath
      newPath
    }
  }
}
```

This is the resource used to index a new or edited data transformation to the database and the slave cache.

### Consult all Data Transformations

``` graphql
query transformation{
  transformation{
    data{
      type
    }
    entries{
      oldPath
      newPath
    }
  }
}
```

This is the resource used to query all data transformations in the database.

### Erase a Data Transformation

``` graphql
mutation delete($type: DataTypeInput){
  delete(type: $type){
    type
  }
}
```

This is the resource used to remove a data transformation from the database and slave cache.

## Data Decoder Master Backend API

This section will present every endpoint available in this service.
Since the communication is made using GraphQL, and there are no `subscriptions` the only endpoint is `/graphql`.

### Index a Data Decoder (new or updated decoder)

``` graphql
mutation index($decoder: DataDecoderInput) {
  index(decoder: $decoder) {
    data {
      type
    }
    script
  }
}
```

This is the resource used to index a new or edited data decoder to the database and the slave cache.

### Consult all Data Decoder

``` graphql
query decoder {
  decoder {
    data {
      type
    }
    script
  }
}
```

This is the resource used to query all data decoders in the database.

### Erase a Data Decoder

``` graphql
mutation delete($type: DataTypeInput) {
  delete(type: $type) {
    type
  }
}
```

This is the resource used to remove a data transformation from the database and slave cache.

## Device Records Master Backend API

This section will present every endpoint available in this service.
Since the communication is made using GraphQL, and there are no `subscriptions` the only endpoint is `/graphql`.

### Index a Device Record (new or updated record)

``` graphql
mutation index($records: DeviceRecordsInput){
  index(records: $records){
    device{
      id
      name
    }
    entries{
      label
      content
      type
    }
  }
}
```

This is the resource used to index a new or edited device record to the database and cache.

### Consult all Device Records

``` graphql
query deviceRecords{
  deviceRecords{
    device{
      id
      name
    }
    entries{
      label
      content
      type
    }
  }
}
```

This is the resource used to query all device records in the database.

### Erase a Device Record

``` graphql
mutation delete($device: DeviceInput){
  delete(device: $device){
    id
    name
  }
}
```

This is the resource used to remove a device record from the cache and database.

## Fleet Management Backend API

This section will present every endpoint available in this service.
Since the communication is made using GraphQL the only two endpoints are `/graphql` to request a subscription and `/subscriptions`.

As pointed in the current [problems](../problems/README.md) the JWT token has to be sent as a GraphQL Input parameter, Authorization, and not as an usual HTTP Header.
The value of `Authorization` has to be 'Bearer <Token>'.

### Consult All GPS sensors live Data

**Endpoint**: `/graphql`

**Query**:

``` graphql
subscription locations($Authorization: String) {
  locations(Authorization: $Authorization) {
    dataId
    device {
      id
      name
      records {
        label
        content
      }
    }
    reportedAt
    data {
      gps {
        longitude
        latitude
      }
      status {
        motion
      }
    }
  }
}
```

This is the resource used to subscribe to changes in the gps location of all sensors registered in the network.

### Consult a Specific GPS Sensor live data

**Endpoint**: `/graphql`

**Query**:

``` graphql
subscription location($devices: [String], $Authorization: String) {
  location(devices: $devices, Authorization: $Authorization) {
    dataId
    device {
      id
      name
      records {
        label
        content
      }
    }
    reportedAt
    data {
      gps {
        longitude
        latitude
      }
      status {
        motion
      }
    }
  }
}
```

The `$devices` variable expects a list of device ids.

This is the resource used to subscribe to changes in the gps location of an array of sensors registered in the network.

### Consult GPS Sensors that match the content sent

``` graphql
subscription locationByContent($content: String, $Authorization: String) {
  locationByContent(content: $content, Authorization: $Authorization) {
    dataId
    device {
      id
      name
      records {
        label
        content
      }
    }
    reportedAt
    data {
      gps {
        longitude
        latitude
      }
      status {
        motion
      }
    }
  }
}
```

This is the resource used to subscribe to changes in the gps location of any sensor that has content matching the "content" sent.

### Consult GPS Sensors History

```graphql
query history($filters: GPSSensorDataQuery) {
  history(filters: $filters) {
    deviceId
    deviceName
    startTime
    endTime
    distance
    segments {
      type
      steps {
        status {
          motion
        }
        reportedAt
        gps {
          longitude
          latitude
        }
      }
    }
  }
}
```

The filter, GPSSensorDataQuery, has to following structure:

```ts
export interface GPSSensorDataQuery {
  device: Array<string>;
  startTime: string;
  endTime: string;
}
```

The `device` variable expects a list of device ids.
The `startTime` variable expects an unix timestamp in seconds.
The `endTime` variable expects an unix timestamp in seconds.

This is the resource used to fetch all history according to history.

### Consult Last entry of all Sensors

```graphql
query latest {
  latest {
    dataId
    device {
      id
      name
      records {
        label
        content
      }
    }
    reportedAt
    data {
      gps {
        longitude
        latitude
      }
      status {
        motion
      }
    }
  }
}
```

This is the resource used to fetch the last location of all sensors.

### Consult Last entry of Specific Sensors

```graphql
query latestByDevice($devices: [String]) {
  latestByDevice(devices: $devices) {
    dataId
    device {
      id
      name
      records {
        label
        content
      }
    }
    reportedAt
    data {
      gps {
        longitude
        latitude
      }
      status {
        motion
      }
    }
  }
}
```

The `$devices` variable expects a list of device ids.

This is the resource used to fetch the last location of each sensor.

## Identity Management Backend API

This section will present every endpoint available in this service.
Since the communication is made using GraphQL, and there are no `subscriptions` the only endpoint is `/graphql`.

### Add a Device to a Domain

```graphql
mutation addDevice($instructions: AddDeviceToDomain) {
  addDevice(instructions: $instructions) {
    oid
    domains {
      oid
      permission
    }
  }
}
```

This is the resource used to add a device to a domain with `read` or `read and write` permissions.

The `instructions` data has the following format:

```ts
export interface AddDeviceToDomainDTO {
  deviceOid: string;
  domainOid: string;
  writePermission: boolean;
}
```

The `deviceOid` variable expects a device id.
The `domainOid` variable expects a domain id.
the `writePermission` variable expects a boolean value, `false` -> `read`, `true` -> `read and write`.

### Add a Tenant to a Domain

```graphql
mutation addTenant($instructions: AddTenantToDomain) {
  addTenant(instructions: $instructions) {
    oid
    email
    name
  }
}
```

This is the resource used to add a tenant to a domain.

The `instructions` data has the following format:

```ts
export interface AddTenantToDomainDTO {
  tenantOid: string;
  domainOid: string;
}
```

The `tenantOid` variable expects a tenant id.
The `domainOid` variable expects a domain id.

### Change Domain Permissions

```graphql
mutation changeDomain($domain: ChangeDomain) {
  changeDomain(domain: $domain) {
    oid
    name
    path
    permissions
  }
}
```

This is the resource used to change the domain permissions.

The `domain` data has the following format:

```ts
export interface DomainDTO {
  oid: string;
  name: string;
  path: string[];
  permissions: string[];
}
```

### Create a Domain as a Child of another

```graphql
mutation createDomain($domain: CreateDomain) {
  createDomain(domain: $domain) {
    oid
    name
    path
    permissions
  }
}
```

This is the resource used to create a sub-domain.

The `domain` data has the following format:

```ts
export interface CreateDomainDTO {
  parentDomainOid: string;
  newDomainName: string;
}
```

### Consult Child Domains Info

```graphql
query viewChildDomainsInfo($domain: ViewDomain) {
  viewChildDomainsInfo(domain: $domain) {
    domain {
      oid
      name
      path
      permissions
    }
    devices {
      oid
      domains {
        oid
        permission
      }
    }
    tenants {
      oid
      email
      name
    }
  }
}
```

This is the resource used to check all information about child domains (only one level below).

The `domain` data has the following format:

```ts
export interface ViewDomainDTO {
  oid: string;
}
```

### Consult Devices in Domain

```graphql
query viewDevicesInDomain($domain: ViewDomain) {
  viewDevicesInDomain(domain: $domain) {
    oid
    domains {
      oid
      permission
    }
  }
}
```

This is the resource used to check all information about devices in the domain.

### Consult Domain Info

```graphql
query viewDomainInfo($domain: ViewDomain) {
  viewDomainInfo(domain: $domain) {
    domain {
      oid
      name
      path
      permissions
    }
    devices {
      oid
      domains {
        oid
        permission
      }
    }
    tenants {
      oid
      email
      name
    }
  }
}
```

This is the resource used to check all information about the domain.

### Consult Child Domains

```graphql
query viewDomain($domain: ViewDomain) {
  viewDomain(domain: $domain) {
    oid
    name
    path
    permissions
  }
}
```

This is the resource used to check basic information about all child domains.

### Consult Tenants in Domain

```graphql
query viewTenantsInDomain($domain: ViewDomain) {
  viewTenantsInDomain(domain: $domain) {
    oid
    email
    name
  }
}
```

This is the resource used to check all information about tenants in the domain.

### Remove a Device from a Domain

```graphql
mutation removeDevice($instructions: RemoveDeviceFromDomain) {
  removeDevice(instructions: $instructions) {
    oid
    domains {
      oid
      permission
    }
  }
}
```

This is the resource used to remove a device from a domain.

The `instructions` data has the following format:

```ts
export interface RemoveDeviceFromDomainDTO {
  deviceOid: string;
  domainOid: string;
}
```

### Remove a Tenant from a Domain

```graphql
mutation removeTenant($instructions: RemoveTenantFromDomain) {
  removeTenant(instructions: $instructions) {
    oid
    email
    name
  }
}
```

This is the resource used to remove a tenant from a domain.

The `instructions` data has the following format:

```ts
export interface RemoveTenantFromDomainDTO {
  tenantOid: string;
  domainOid: string;
}
```

### Authenticate User

```graphql
query authenticate {
  authenticate {
    token
  }
}
```

This is the resource used to exchange an `id token` from microsoft for an `access token` from `sensae` as explained in identity-management [docs](../identity-management/README.md).

## Further Discussion

As always, changes/improvements to this page and all APIs of the system are expected.
