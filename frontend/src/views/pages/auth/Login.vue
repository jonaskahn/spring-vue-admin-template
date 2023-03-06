<script setup>
import { ref } from 'vue'
import AuthService from '@/service/AuthService'
import { useRouter } from 'vue-router'
import routeInfo from '@/constants/routeInfo'

const username = ref('admin')
const password = ref('admin-password')

let isUsernameValid = ref(true)
let isPasswordValid = ref(true)
let isLoading = ref(false)

const authService = new AuthService()
const router = useRouter()

async function submit() {
  isLoading.value = true
  const result = await authService.login({
    username: username.value,
    password: password.value
  })
  isLoading.value = false
  if (result) {
    await router.push({
      path: routeInfo.APP.DASH_BOARD.path
    })
  }
}
</script>

<template>
  <div
    class="surface-ground flex align-items-center justify-content-center login-box min-w-min overflow-hidden"
  >
    <div class="flex flex-column align-items-center justify-content-center m-4">
      <div class="flex">
        <img :src="logoUrl" alt="Sakai logo" class="mb-5 w-6rem flex-shrink-0" />
      </div>
      <div
        class="surface-card xl:w-8 lg:w-10 md:w-12 sm:w-full py-8 px-5 sm:px-8"
        style="border-radius: 50px"
      >
        <div class="text-center mb-5">
          <div class="text-900 text-3xl font-medium mb-3">Sign in to continue</div>
        </div>

        <div>
          <form>
            <div class="grid p-fluid">
              <div class="col-12 md:col-12">
                <div class="field">
                  <label for="username">{{ $t('auth-login.label.username') }}</label>
                  <span class="p-input-icon-left">
                    <i class="pi pi-user" />
                    <InputText
                      id="username"
                      type="username"
                      aria-describedby="username-help"
                      class="p-inputtext-lg"
                      :class="{ 'p-invalid': !isUsernameValid }"
                      v-model="username"
                    />
                  </span>

                  <small v-if="!isUsernameValid" id="username-help" class="p-error"
                    >Username is not available.</small
                  >
                </div>
              </div>

              <div class="col-12 md:col-12">
                <div class="field">
                  <label for="password">{{ $t('auth-login.label.password') }}</label>
                  <span class="p-input-icon-left">
                    <i class="pi pi-key" />
                    <InputText
                      id="password"
                      type="password"
                      class="p-inputtext-lg"
                      aria-describedby="password-help"
                      :class="{ 'p-invalid': !isPasswordValid }"
                      v-model="password"
                    />
                  </span>

                  <small v-if="!isPasswordValid" id="password-help" class="p-error"
                    >Password is not available.</small
                  >
                </div>
              </div>
              <div class="col-12 md:col-12">
                <Button
                  class="w-full p-3 text-xl"
                  label="Sign In"
                  type="button"
                  icon="pi pi-check"
                  :loading="isLoading"
                  @click="submit"
                  iconPos="right"
                ></Button>
              </div>
            </div>
          </form>
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
