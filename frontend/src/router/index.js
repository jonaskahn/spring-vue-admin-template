import { createRouter, createWebHashHistory } from "vue-router"
import AppLayout from "@/layout/AppLayout.vue"
import Page from "@/constants/page"
import constants from "@/constants"
import { LocalStorageManager, translate } from "@/helper"
import { nextTick } from "vue"
import { containsAny } from "@/utils/arrays"

const whiteListUrl = [
  Page.ACCESS.DENIED.name,
  Page.ACCESS.NOT_FOUND.name,
  Page.ACCESS.ERROR.name,
  Page.AUTH.RESET_PASSWORD.name,
  Page.AUTH.VERIFY.name
]

const router = createRouter({
  history: createWebHashHistory(),
  routes: [
    {
      path: "/",
      component: AppLayout,
      children: [
        {
          path: Page.APP.DASH_BOARD.path,
          name: Page.APP.DASH_BOARD.name,
          meta: {
            title: Page.APP.DASH_BOARD.title,
            permissions: Page.APP.DASH_BOARD.permissions
          },
          component: () => import("@/views/Dashboard.vue")
        },
        {
          path: Page.APP.PERMISSION.ADMIN.path,
          name: Page.APP.PERMISSION.ADMIN.name,
          meta: {
            title: Page.APP.PERMISSION.ADMIN.title,
            permissions: Page.APP.PERMISSION.ADMIN.permissions
          },
          component: () => import("@/views/permission/Admin.vue")
        },
        {
          path: Page.APP.PERMISSION.EDITOR.path,
          name: Page.APP.PERMISSION.EDITOR.name,
          meta: {
            title: Page.APP.PERMISSION.EDITOR.title,
            permissions: Page.APP.PERMISSION.EDITOR.permissions
          },
          component: () => import("@/views/permission/Editor.vue")
        }
      ]
    },
    {
      path: Page.AUTH.LOGIN.path,
      name: Page.AUTH.LOGIN.name,
      meta: {
        title: Page.AUTH.LOGIN.title,
        permissions: Page.AUTH.LOGIN.permissions
      },
      component: () => import("@/views/pages/auth/Login.vue")
    },
    {
      path: Page.AUTH.RESET_PASSWORD.path,
      name: Page.AUTH.RESET_PASSWORD.name,
      meta: {
        title: Page.AUTH.RESET_PASSWORD.title,
        permissions: Page.AUTH.RESET_PASSWORD.permissions
      },
      component: () => import("@/views/pages/auth/ResetPassword.vue")
    },
    {
      path: Page.AUTH.VERIFY.path,
      name: Page.AUTH.VERIFY.name,
      meta: {
        title: Page.AUTH.VERIFY.title,
        permissions: Page.AUTH.VERIFY.permissions
      },
      component: () => import("@/views/pages/auth/VerifyAccount.vue")
    },
    {
      path: Page.ACCESS.NOT_FOUND.path,
      name: Page.ACCESS.NOT_FOUND.name,
      meta: {
        title: Page.ACCESS.NOT_FOUND.title,
        permissions: Page.ACCESS.NOT_FOUND.permissions
      },
      component: () => import("@/views/pages/NotFound.vue")
    },
    {
      path: Page.ACCESS.DENIED.path,
      name: Page.ACCESS.DENIED.name,
      component: () => import("@/views/pages/Access.vue")
    },
    {
      path: Page.ACCESS.ERROR.path,
      name: Page.ACCESS.ERROR.name,
      meta: {
        title: Page.ACCESS.ERROR.title,
        permissions: Page.ACCESS.ERROR.permissions
      },
      component: () => import("@/views/pages/Error.vue")
    }
  ]
})

router.beforeEach(async (to, from, next) => {
  if (whiteListUrl.includes(to.name)) {
    next()
  } else if (isTokenNonExisted() || isTokenExpired()) {
    resetLocalData()
    await redirectIfInvalid(to, from, next)
  } else {
    await redirectIfValid(to, from, next)
  }
})

function resetLocalData() {
  const isSigninStateExisted = localStorage.getItem(constants.STORAGE.SIGNIN_STATE)
  LocalStorageManager.reset()
  if (isSigninStateExisted) {
    LocalStorageManager.updateSigninState()
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
  if (to.name === Page.AUTH.LOGIN.name) {
    await router.push(Page.APP.DASH_BOARD.path)
  } else if (hasPermission(to.meta.permissions ?? [])) {
    next()
  } else {
    await router.push(Page.ACCESS.DENIED)
  }
}

function hasPermission(permissions) {
  return (
    permissions.length === 0 || containsAny(permissions, LocalStorageManager.getTokenAuthorities())
  )
}

router.afterEach(async (to) => {
  await nextTick(() => {
    const pageTitle = translate(to.meta.title ?? to.name.toUpperCase() + "")
    document.title = translate("global.menu-title.default") + " - " + pageTitle
  })
})

export default router
