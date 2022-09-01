# Problems

Current version:

- `system` : `0.10.0`

## Auth

The graphql subscription spec, and the used libraries, don't easily handle headers.
Links:

- https://github.com/Netflix/dgs-framework/issues/450

The JWT token is, for now, being sent as a query input in subscriptions.
