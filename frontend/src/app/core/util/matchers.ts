export function anyMatch(str: string, values: string): boolean {
  return Array.from(str).some(c => values.includes(c));
}

export function allMatch(str: string, values: string): boolean {
  return Array.from(str).every(c => values.includes(c));
}
