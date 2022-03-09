import { FetchResult } from '@apollo/client/core';

export function isNonNull<T>(value: T): value is NonNullable<T> {
  return value != null;
}

export function extract<T>(value: FetchResult<T>): T | null | undefined {
  return value.data;
}
