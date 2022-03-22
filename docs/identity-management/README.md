# Identity Management

This document describes how `identity management` functions inside the system, according to the latest version.

Current version:

- `system` : `0.6.0`

## Introduction

## Improvements

Devices are identified only with it's `uuid` since no other information can be extrapolated by the data received. As an improvement we cloud provide a link to each `Device Record`s details.

The `Device Record`s tool can't understand what devices belong to the current user domains. As such it is advised to give no permissions related to `device records` to any domain other than the `root`. As an improvement, the `device record master` could query the `identity management master` for all the devices a user can write or read.

## Further Discussion

As always, changes/improvements to this page and `identity management`s behavior are expected.
