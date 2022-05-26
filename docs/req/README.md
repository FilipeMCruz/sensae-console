# Functional Requirements

This section defines the functionalities and operations that the system supports as of the current version.

Current version:

- `system` : `0.8.0`

## Actors

The system actors are:

- **Manager**: has full control over an Environment and can be seen as the root tenant;
- **Tenant**: has control over the devices, employees and sub-domains registered in an
Environment under his/her own domain;
- **Anonymous User**: has access to information about defined devices in an Environment;
- **System Worker**: dispatches alarms in an Environment.

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
| **UC12**  | As a manager I want to see the information i've added to each device                 | Device Records      |
| **UC13**  | As a manager I want to delete information i've added about a specific device         | Device Records      |
| **UC14**  | As a tenant I want to authenticate myself                                            | Identity Management |
| **UC15**  | As a tenant I want to add/remove another tenant to/from a domain i own               | Identity Management |
| **UC16**  | As a tenant I want to add a new sub-domain to my domain                              | Identity Management |
| **UC17**  | As a tenant I want to add/remove a device to/from a domain i own                     | Identity Management |
| **UC18**  | As a tenant I want to change the permissions of a domain i own                       | Identity Management |
| **UC19**  | As a tenant I want to see the tenants, devices and permissions of a domain i own     | Identity Management |
| **UC20**  | As a tenant I want to create a gardening area                                        | Smart Irrigation    |
| **UC21**  | As a tenant I want to delete a gardening area                                        | Smart Irrigation    |
| **UC22**  | As a tenant I want to update a gardening area                                        | Smart Irrigation    |
| **UC23**  | As a tenant I want to see all gardening areas                                        | Smart Irrigation    |
| **UC24**  | As a tenant I want to open/close a valve                                             | Smart Irrigation    |
| **UC25**  | As a tenant I want to see the latest data of each device inside a garden             | Smart Irrigation    |
| **UC26**  | As a tenant I want to see the past data of a specific device                         | Smart Irrigation    |
| **UC27**  | As a manager I want to define a rule scenario so that alerts are sent                | Rule Management     |
| **UC28**  | As a manager I want to see the rule scenarios registered in the system               | Rule Management     |
| **UC29**  | As a manager I want to delete a rule scenario i've added                             | Rule Management     |

## Further Discussion

As always, changes/improvements to this page and the system goals are expected.
