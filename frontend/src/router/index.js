import { createRouter, createWebHashHistory } from 'vue-router'
import AppLayout from '@/layout/AppLayout.vue'
import Page from '@/constants/page'
import constants from '@/constants'
import { StorageManager, translate } from '@/helper'
import { nextTick } from 'vue'
import { containsAny } from '@/utils/arrays'

const whiteListUrl = [Page.AUTH.ACCESS_DENIED.path, Page.AUTH.NOT_FOUND.path, Page.AUTH.ERROR.path]

const router = createRouter({
  history: createWebHashHistory(),
  routes: [
    {
      path: '/',
      component: AppLayout,
      children: [
        {
          path: Page.APP.DASH_BOARD.path,
          name: Page.APP.DASH_BOARD.name,
          meta: {
            title: Page.APP.DASH_BOARD.title,
            permissions: Page.APP.DASH_BOARD.permissions
          },
          component: () => import('@/views/Dashboard.vue')
        },
        {
          path: Page.APP.PERMISSION.ADMIN.path,
          name: Page.APP.PERMISSION.ADMIN.name,
          meta: {
            title: Page.APP.PERMISSION.ADMIN.title,
            permissions: Page.APP.PERMISSION.ADMIN.permissions
          },
          component: () => import('@/views/permission/Admin.vue')
        },
        {
          path: Page.APP.PERMISSION.EDITOR.path,
          name: Page.APP.PERMISSION.EDITOR.name,
          meta: {
            title: Page.APP.PERMISSION.EDITOR.title,
            permissions: Page.APP.PERMISSION.EDITOR.permissions
          },
          component: () => import('@/views/permission/Editor.vue')
        }
      ]
    },
    {
      path: '/:pathMatch(.*)*',
      name: 'notfound',
      component: () => import('@/views/pages/NotFound.vue')
    },

    {
      path: Page.AUTH.LOGIN.path,
      name: Page.AUTH.LOGIN.name,
      component: () => import('@/views/pages/auth/Login.vue')
    },
    {
      path: Page.AUTH.ACCESS_DENIED.path,
      name: Page.AUTH.ACCESS_DENIED.name,
      component: () => import('@/views/pages/auth/Access.vue')
    },
    {
      path: '/auth/error',
      name: 'error',
      component: () => import('@/views/pages/auth/Error.vue')
    }
  ]
})

router.beforeEach(async (to, from, next) => {
  if (isTokenNonExisted() || isTokenExpired()) {
    resetLocalData()
    await redirectIfInvalid(to, from, next)
  } else {
    await redirectIfValid(to, from, next)
  }
})

function resetLocalData() {
  const isSigninStateExisted = localStorage.getItem(constants.APP.SIGNIN_STATE)
  StorageManager.reset()
  if (isSigninStateExisted) {
    StorageManager.updateSigninState()
  }
}

function isTokenNonExisted() {
  return !localStorage.getItem(constants.TOKEN.ACCESS_TOKEN)
}

function isTokenExpired() {
  const accessTokenExpired = parseInt(localStorage.getItem(constants.TOKEN.ACCESS_TOKEN_EXPIRED))
  const now = new Date().getTime() / 1000
  return !!accessTokenExpired && now > accessTokenExpired
}

async function redirectIfInvalid(to, from, next) {
  if (to.path !== Page.AUTH.LOGIN.path) {
    await router.push({
      path: Page.AUTH.LOGIN.path
    })
  } else {
    next()
  }
}

async function redirectIfValid(to, from, next) {
  if (whiteListUrl.includes(to.path)) {
    next()
  } else if (to.path === Page.AUTH.LOGIN.path) {
    await router.push(Page.APP.DASH_BOARD.path)
  } else if (hasPermission(to.meta.permissions ?? [])) {
    next()
  } else {
    await router.push(Page.AUTH.ACCESS_DENIED)
  }
}

function hasPermission(permissions) {
  return permissions.length === 0 || containsAny(permissions, StorageManager.getTokenAuthorities())
}

router.afterEach(async (to) => {
  await nextTick(() => {
    const pageTitle = translate(to.meta.title ?? to.name.toUpperCase() + '')
    document.title = translate('global.menu-title.default') + ' - ' + pageTitle
  })
})

export default router
