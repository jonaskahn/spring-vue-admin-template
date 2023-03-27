export function containsAny(arr1, arr2) {
  return arr1.some((element) => {
    return arr2.indexOf(element) !== -1
  })
}
