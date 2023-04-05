const Authority = {
  USER: "user",
  EDITOR: "editor",
  ADMIN: "admin"
}

const Page = {
  AUTH: {
    LOGIN: {
      path: "/auth/login",
      name: "login",
      title: "Login page",
      permissions: []
    },
    SIGN_IN: {
      path: "/auth/sign-in",
      name: "sign-in",
      title: "Sign in",
      permissions: []
    },
    RESET_PASSWORD: {
      path: "/auth/reset-password",
      name: "reset-password",
      title: "Reset password",
      permissions: []
    },
    VERIFY: {
      path: "/auth/verify/:code",
      name: "verify-account",
      title: "Verify Account",
      permissions: []
    }
  },
  ACCESS: {
    DENIED: {
      path: "/access/denied",
      name: "access-denied",
      title: "Access denied",
      permissions: []
    },
    NOT_FOUND: {
      path: "/:pathMatch(.*)*",
      name: "not-found",
      title: "Not found",
      permissions: []
    },
    ERROR: {
      path: "/access/error",
      name: "error",
      title: "Error",
      permissions: []
    }
  },
  APP: {
    DASH_BOARD: {
      path: "/",
      name: "dashboard",
      title: "global.menu-title.dashboard",
      permissions: []
    },
    PERMISSION: {
      ADMIN: {
        path: "/permission/admin",
        name: "permission-admin",
        title: "global.menu-title.permission.admin",
        permissions: [Authority.ADMIN]
      },
      EDITOR: {
        path: "/permission/editor",
        name: "permission-editor",
        title: "global.menu-title.permission.editor",
        permissions: [Authority.ADMIN, Authority.EDITOR]
      }
    }
  }
}

export default Page
