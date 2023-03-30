<script setup>
import Page from '@/constants/page'
import UserService from '@/service/UserService'
import { useRoute } from 'vue-router'
import { onBeforeMount, reactive } from 'vue'

const userService = new UserService()
let message = reactive(null)
onBeforeMount(async () => {
  message = await userService.verifyEmail({
    code: useRoute().params.code
  })
})
</script>

<template>
  <div
    class="surface-ground flex align-items-center justify-content-center login-box overflow-hidden"
  >
    <div class="flex flex-column align-items-center justify-content-center m-4 md:m-8">
      <div class="flex">
        <img alt="app-logo" class="mb-5 sm:w-10rem w-8rem flex-shrink-0" src="@/assets/logo.png" />
      </div>
      <div class="surface-card w-full px-4 py-4 md:px-6" style="border-radius: 40px">
        <div class="mb-5 flex flex-row">
          <div class="flex-1 flex align-items-center justify-content-start">
            <Button
              :title="$t('page.reset-password.label.btn-back')"
              icon="pi pi-home"
              iconPos="left"
              outlined
              raised
              rounded
              type="button"
              @click.prevent="$router.push({ name: Page.APP.DASH_BOARD.name })"
            />
          </div>

          <div
            class="flex-1 flex align-items-center justify-content-end text-center text-900 text-2xl"
          >
            <span class="text-left hidden md:inline">{{ $t('Xac minh tai khoan') }}</span>
          </div>
        </div>
        <div v-focustrap class="grid">
          <Divider />
          <div class="text-center">
            <span class="text-900 font-italic">TTTTTTTTTTTTTTTTTTTTTTTT{{ message }}</span>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.login-box {
  min-height: 80vh !important;
}
</style>
