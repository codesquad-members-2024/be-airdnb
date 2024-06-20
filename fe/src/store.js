import { writable } from 'svelte/store';

export const minPrice = writable(100000);
export const maxPrice = writable(1000000);
