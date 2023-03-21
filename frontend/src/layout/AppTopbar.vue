<script setup>
import { computed, onBeforeUnmount, onMounted, ref } from 'vue'
import { useLayout } from '@/layout/composables/layout'
import { useRouter } from 'vue-router'
import AuthService from "@/service/AuthService";
import RouteInfo from "@/constants/routeInfo";

const { onMenuToggle } = useLayout()

const outsideClickListener = ref(null)
const topbarMenuActive = ref(false)
const router = useRouter()
const authService = new AuthService()

onMounted(() => {
  bindOutsideClickListener()
})

onBeforeUnmount(() => {
  unbindOutsideClickListener()
})

const onTopBarMenuButton = () => {
  topbarMenuActive.value = !topbarMenuActive.value
}
const onLogoutClick = async () => {
  if (await authService.logout()) {
    await router.push(RouteInfo.AUTH.LOGIN.path)
  }

}
const topbarMenuClasses = computed(() => {
  return {
    'layout-topbar-menu-mobile-active': topbarMenuActive.value
  }
})

const bindOutsideClickListener = () => {
  if (!outsideClickListener.value) {
    outsideClickListener.value = (event) => {
      if (isOutsideClicked(event)) {
        topbarMenuActive.value = false
      }
    }
    document.addEventListener('click', outsideClickListener.value)
  }
}
const unbindOutsideClickListener = () => {
  if (outsideClickListener.value) {
    document.removeEventListener('click', outsideClickListener)
    outsideClickListener.value = null
  }
}
const isOutsideClicked = (event) => {
  if (!topbarMenuActive.value) return

  const sidebarEl = document.querySelector('.layout-topbar-menu')
  const topbarEl = document.querySelector('.layout-topbar-menu-button')

  return !(
    sidebarEl.isSameNode(event.target) ||
    sidebarEl.contains(event.target) ||
    topbarEl.isSameNode(event.target) ||
    topbarEl.contains(event.target)
  )
}
</script>

<template>
  <div class="layout-topbar">
    <button class="p-link layout-topbar-button mr-2" @click="onMenuToggle()">
      <i class="pi pi-bars"></i>
    </button>

    <button
      class="p-link layout-topbar-menu-button layout-topbar-button"
      @click="onTopBarMenuButton()"
    >
      <i class="pi pi-ellipsis-v"></i>
    </button>
    <div class="layout-topbar-logo" to="/">
      <router-link to="/">
        <img src="@/assets/logo.png" alt="logo" class="w-3rem h-3rem mr-4" />
      </router-link>
      <span class="hidden md:block">MEE SPRING VUE</span>
    </div>
    <div :class="topbarMenuClasses" class="layout-topbar-menu">
      <button class="p-link layout-topbar-button" @click="onTopBarMenuButton()">
        <i class="pi pi-bell"></i>
        <span>Notifications</span>
      </button>
      <button class="p-link layout-topbar-button" @click="onTopBarMenuButton()">
        <i class="pi pi-user"></i>
        <span>Profile</span>
      </button>
      <button class="p-link layout-topbar-button" @click="onLogoutClick()">
        <i class="pi pi-sign-out"></i>
        <span>Logout</span>
      </button>
    </div>
  </div>
</template>

<style lang="scss" scoped>
</style>
