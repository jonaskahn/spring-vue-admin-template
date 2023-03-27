import { containsAny } from '@/utils/arrays'
import { StorageManager } from '@/helper'

export const hasAnyPermissionChildren = (menu) => {
  if (menu.items) {
    let hasPermission = false
    menu.items.forEach((item) => {
      const permissions = item.permissions ?? []
      hasPermission =
        hasPermission ||
        permissions.length === 0 ||
        containsAny(permissions, StorageManager.getTokenAuthorities())
    })
    return hasPermission
  }
  return true
}
