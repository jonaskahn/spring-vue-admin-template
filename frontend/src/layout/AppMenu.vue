<script setup>
import { reactive, toRaw } from 'vue'

import AppMenuItem from './AppMenuItem.vue'
import Page from '@/constants/page'
import { hasAnyPermissionChildren } from '@/layout/composables/permission'

const model = reactive([
  {
    label: 'Home',
    items: [
      {
        label: Page.APP.DASH_BOARD.title,
        icon: 'pi pi-fw pi-home',
        to: Page.APP.DASH_BOARD.path,
        permissions: Page.APP.DASH_BOARD.permissions
      }
    ]
  },
  {
    label: 'Permissions',
    items: [
      {
        label: Page.APP.PERMISSION.EDITOR.title,
        icon: 'pi pi-fw pi-file-edit',
        to: Page.APP.PERMISSION.EDITOR.path,
        permissions: Page.APP.PERMISSION.EDITOR.permissions,
        badge: {
          value: 'Demo',
          severity: 'danger'
        }
      },
      {
        label: Page.APP.PERMISSION.ADMIN.title,
        icon: 'pi pi-fw pi-bolt',
        to: Page.APP.PERMISSION.ADMIN.path,
        permissions: Page.APP.PERMISSION.ADMIN.permissions,
        badge: {
          value: 'Demo',
          severity: 'danger'
        }
      },
      {
        label: 'Sub-Permission 1',
        icon: 'pi pi-fw pi-clock',
        items: [
          {
            label: 'Faker 1',
            icon: 'pi pi-fw pi-angle-double-right',
            to: '/faker1',
            permissions: ['faker']
          },
          {
            label: 'Faker 2',
            icon: 'pi pi-fw pi-angle-double-right',
            to: '/faker2',
            permissions: ['faker']
          }
        ]
      },
      {
        label: 'Sub-Permission 2',
        icon: 'pi pi-fw pi-clock',
        badge: {
          value: 'Demo',
          severity: 'danger'
        },
        items: [
          {
            label: 'Faker 1',
            icon: 'pi pi-fw pi-angle-double-right',
            to: '/faker1'
          },
          {
            label: 'Faker 2',
            icon: 'pi pi-fw pi-angle-double-right',
            to: '/faker2',
            permissions: ['faker']
          }
        ]
      }
    ]
  },
  {
    label: 'Primvue',
    badge: {
      value: 'Official',
      severity: 'warning'
    },
    items: [
      {
        label: 'Document',
        icon: 'pi pi-fw pi-question',
        url: 'https://primevue.org/',
        target: '_bank'
      },
      {
        label: 'Sakai Theme',
        icon: 'pi pi-fw pi-bookmark',
        url: 'https://www.primefaces.org/sakai-vue',
        target: '_bank',
        permissions: []
      }
    ]
  }
])
</script>

<template>
  <ul class="layout-menu">
    <template v-for="(item, i) in model" :key="item">
      <app-menu-item
        v-if="!item.separator && hasAnyPermissionChildren(toRaw(item))"
        :index="i"
        :item="item"
      ></app-menu-item>
      <li v-if="item.separator" class="menu-separator"></li>
    </template>
  </ul>
</template>

<style lang="scss" scoped></style>
