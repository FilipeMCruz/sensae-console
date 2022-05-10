import {FetchResult} from '@apollo/client/core';

export function isNonNull<T>(value: T): value is NonNullable<T> {
  return value != null;
}

export function extract<T>(value: FetchResult<T>): T | null | undefined {
  return value.data;
}

export const groupBy = <T, K extends keyof any>(list: T[], getKey: (item: T) => K | undefined, orElse: (item: T) => K) =>
  list.reduce((previous, currentItem) => {
    let group = getKey(currentItem);
    if (group === undefined) {
      group = orElse(currentItem);
    }
    if (!previous[group]) previous[group] = [];
    previous[group].push(currentItem);
    return previous;
  }, {} as Record<K, T[]>);
