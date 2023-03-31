<script setup>
import UserService from '@/service/UserService'
import { useRoute } from 'vue-router'
import { onBeforeMount, ref } from 'vue'
import Page from '@/constants/page'

const userService = new UserService()
let message = ref({
  ok: false,
  val: null
})
onBeforeMount(async () => {
  const res = await userService.verifyEmail({
    code: useRoute().params.code
  })
  message.value = {
    ok: res.state,
    val: res.payload
  }
})
</script>

<template>
  <div
    class="surface-ground flex align-items-center justify-content-center card-box min-h-screen min-w-screen overflow-hidden"
  >
    <div class="flex flex-column align-items-center justify-content-center">
      <img alt="Sakai logo" class="mb-5 w-6rem flex-shrink-0" src="@/assets/logo.png" />
      <div
        class="w-full surface-card py-8 px-5 sm:px-8 flex flex-column align-items-center"
        style="border-radius: 53px"
      >
        <div class="grid flex flex-column align-items-center">
          <div
            :class="[message.ok ? 'bg-green-500' : 'bg-red-500']"
            class="flex justify-content-center align-items-center bg-green-500 border-circle"
            style="width: 3.2rem; height: 3.2rem"
          >
            <i class="text-50 pi pi-fw pi-hashtag text-2xl"></i>
          </div>
          <h1 class="text-900 font-bold text-4xl lg:text-5xl mb-4">
            {{ $t('page.verify-account.title') }}
          </h1>
          <span
            :class="[message.ok ? 'text-green-500 text-500' : 'text-red-500 text-600 font-bold ']"
            class="font-italic text-xl mb-5"
          >
            {{ message.val }}</span
          >

          <div class="col-12 text-center">
            <Button
              :label="$t('page.verify-account.label.link-back-to-dashboard')"
              autofocus
              icon="pi pi-fw pi-home "
              raised
              @click="
                $router.push({
                  name: Page.APP.DASH_BOARD.name
                })
              "
            />
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.card-box {
  min-height: 80vh !important;
}
</style>
