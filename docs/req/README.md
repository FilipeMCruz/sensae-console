# Functional Requirements

This section defines the functionalities and operations that the system supports.

## Actors

The system actors are:

- **Manager**: has full control over an Environment and can be seen as the root tenant;
- **Tenant**: has control over the devices, employees and sub-domains registered in an
Environment under his/her own domain;
- **Anonymous User**: has access to information about defined devices in an Environment;
- **System Worker**: dispatches alarms and assembles reports in an Environment.

## Use cases

This are the currently implemented use cases.

| Use Cases | Description                                                                          | Scope               |
| --------- | ------------------------------------------------------------------------------------ | ------------------- |
| **UC01**  | As a tenant I want to see the live information for all my devices                    | Fleet Management    |
| **UC02**  | As a tenant I want to see the live information for a specific device i own           | Fleet Management    |
| **UC03**  | As a tenant I want to see the past information for all my devices                    | Fleet Management    |
| **UC04**  | As a tenant I want to see the past information for a specific device i own           | Fleet Management    |
| **UC05**  | As a manager I want to define how to process data of a certain device type           | Data Processor      |
| **UC06**  | As a manager I want to see the transformations registered for all device types       | Data Processor      |
| **UC07**  | As a manager I want to delete a transformation i've added for a specific device type | Data Processor      |
| **UC08**  | As a tenant I want to define how to decode data of a certain device type             | Data Decoder        |
| **UC09**  | As a tenant I want to see the scripts registered for all device types                | Data Decoder        |
| **UC10**  | As a tenant I want to delete a script i've added for a specific device type          | Data Decoder        |
| **UC11**  | As a manager I want to enhance the information provided by a device                  | Device Records      |
| **UC13**  | As a manager I want to see the information i've added to each device                 | Device Records      |
| **UC14**  | As a manager I want to delete information i've added about a specific device         | Device Records      |
| **UC15**  | As a tenant I want to authenticate myself                                            | Identity Management |
| **UC16**  | As a tenant I want to add/remove another tenant to/from a domain i own               | Identity Management |
| **UC17**  | As a tenant I want to add a new sub-domain to my domain                              | Identity Management |
| **UC18**  | As a tenant I want to add/remove a device to/from a domain i own                     | Identity Management |
| **UC19**  | As a tenant I want to change the permissions of a domain i own                       | Identity Management |
| **UC20**  | As a tenant I want to see the tenants, devices and permissions of a domain i own     | Identity Management |

## Further Discussion

As always, changes/improvements to this page and the system goals are expected.
