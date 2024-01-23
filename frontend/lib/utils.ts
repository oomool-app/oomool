import { type ClassValue, clsx } from 'clsx';
import { twMerge } from 'tailwind-merge';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { camelize, getCurrentInstance, toHandlerKey } from 'vue';

export const cn = (...inputs: ClassValue[]): string => {
  return twMerge(clsx(inputs));
};

export const tw = (
  strings: TemplateStringsArray,
  ...values: string[]
): string => String.raw({ raw: strings }, ...values);
