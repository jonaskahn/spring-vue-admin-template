import { containsAny } from '@/utils/arrays'
import { LocalStorageManager } from '@/helper'

export const hasAnyPermissionChildren = (menu) => {
  if (menu.items) {
    let hasPermission = false
    menu.items.forEach((item) => {
      const permissions = item.permissions ?? []
      hasPermission =
        hasPermission ||
        permissions.length === 0 ||
        containsAny(permissions, LocalStorageManager.getTokenAuthorities())
    })
    return hasPermission
  }
  return true
}
