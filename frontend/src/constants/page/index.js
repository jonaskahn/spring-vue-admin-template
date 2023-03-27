import { PageAuthority } from '@/constants/page/authority'

export default {
  AUTH: {
    LOGIN: {
      path: '/auth/login',
      name: 'login',
      title: 'Login page',
      permissions: []
    },
    SIGN_IN: {
      path: '/auth/sign-in',
      name: 'sign-in',
      title: 'Sign in',
      permissions: []
    },
    ACCESS_DENIED: {
      path: '/access/denied',
      name: 'access-denied',
      title: 'Access denied',
      permissions: []
    },
    NOT_FOUND: {
      path: '/access/not-found',
      name: 'not-found',
      title: 'Not found',
      permissions: []
    },
    ERROR: {
      path: '/access/error',
      name: 'error',
      title: 'Error',
      permissions: []
    }
  },
  APP: {
    DASH_BOARD: {
      path: '/',
      name: 'dashboard',
      title: 'global.menu-title.dashboard',
      permissions: []
    },
    PERMISSION: {
      ADMIN: {
        path: '/permission/admin',
        name: 'permission-admin',
        title: 'global.menu-title.permission.admin',
        permissions: [PageAuthority.admin()]
      },
      EDITOR: {
        path: '/permission/editor',
        name: 'permission-editor',
        title: 'global.menu-title.permission.editor',
        permissions: [PageAuthority.editor(), PageAuthority.admin()]
      }
    }
  }
}
