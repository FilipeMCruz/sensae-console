# Problems

Current version:

- `system` : `0.10.0`

## Auth

The graphql subscription spec, and the used libraries, don't easily handle headers.
Links:

- https://github.com/apollographql/subscriptions-transport-ws/issues/171
- https://github.com/Netflix/dgs-framework/issues/450

The JWT token is, for now, being sent as a query input in subscriptions.

## GraphQL Subscriptions

The graphql subscription is currently using  `subscriptions-transport-ws`, this implementation has been deprecated in favor of `graphql-ws`, sadly `dgs-framework` doesn't support it yet, according to <https://github.com/Netflix/dgs-framework/pull/686>.
