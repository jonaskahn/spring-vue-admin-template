<script setup>
import { reactive, ref } from 'vue'
import AuthService from '@/service/AuthService'
import { useRouter } from 'vue-router'
import routeInfo from '@/constants/routeInfo'
import { translate } from '@/helper/static'
import LangPlate from '@/layout/LangPlate.vue'

const usernameRef = ref(null)
const passwordRef = ref(null)

const data = reactive({
  username: 'admin',
  password: 'admin-password'
})

const validation = reactive({
  username: null,
  password: null
})

let isLoading = ref(false)

const authService = new AuthService()
const router = useRouter()

async function submit() {
  //Prevent user submit too quick
  setTimeout(async () => {
    isLoading.value = true
    validation.username = null
    validation.password = null
    if (!invalidInput()) {
      const result = await authService.login({
        username: data.username.trim(),
        password: data.password.trim()
      })
      if (result) {
        await router.push({
          path: routeInfo.APP.DASH_BOARD.path
        })
      } else {
        validation.username = translate('page.login.message.validation.username-incorrect')
        validation.password = translate('page.login.message.validation.password-incorrect')
        usernameRef.value.$el.focus()
      }
    }
    isLoading.value = false
  }, 500)
}

function invalidInput() {
  if (!data.password.trim()) {
    validation.password = translate('page.login.message.validation.password-required')
    passwordRef.value.$el.focus()
  }
  if (!data.username.trim()) {
    validation.username = translate('page.login.message.validation.username-required')
    usernameRef.value.$el.focus()
  }
  return validation.username || validation.password
}
</script>

<template>
  <div
    class="surface-ground flex align-items-center justify-content-center login-box min-w-min overflow-hidden"
  >
    <div class="flex flex-column align-items-center justify-content-center m-4 md:m-8">
      <div class="flex">
        <img alt="app-logo" class="mb-5 sm:w-10rem w-8rem flex-shrink-0" src="@/assets/logo.png" />
      </div>
      <div
        class="surface-card lg:w-10 md:w-12 sm:w-full py-8 px-5 sm:px-8"
        style="border-radius: 50px"
      >
        <div class="mb-5 flex flex-row">
          <div
            class="flex-1 flex align-items-center justify-content-start text-center text-900 text-3xl font-medium mb-3"
          >
            {{ $t('page.login.message.welcome') }}
          </div>
          <lang-plate class-layout="flex-1 flex align-items-center justify-content-end" />
        </div>

        <form>
          <div v-focustrap class="grid p-fluid">
            <div class="col-12 md:col-12">
              <div class="field">
                <label for="username"
                  >{{ $t('page.login.label.input-username') }}
                  <span class="text-red-500">*</span></label
                >
                <span class="p-input-icon-left">
                  <i class="pi pi-user" />
                  <InputText
                    id="username"
                    ref="usernameRef"
                    v-model="data.username"
                    :class="{ 'p-invalid': validation.username }"
                    aria-describedby="username-help"
                    class="p-inputtext-lg"
                    type="username"
                  />
                </span>
                <small v-if="validation.username" id="username-help" class="p-error font-italic">
                  {{ validation.username }}
                </small>
              </div>
            </div>

            <div class="col-12 md:col-12">
              <div class="field">
                <label for="password"
                  >{{ $t('page.login.label.input-password') }}
                  <span class="text-red-500">*</span></label
                >
                <span class="p-input-icon-left">
                  <i class="pi pi-key" />
                  <InputText
                    id="password"
                    ref="passwordRef"
                    v-model="data.password"
                    :class="{ 'p-invalid': validation.password }"
                    aria-describedby="password-help"
                    class="p-inputtext-lg"
                    type="password"
                  />
                </span>

                <small v-if="validation.password" id="password-help" class="p-error font-italic">
                  {{ validation.password }}
                </small>
              </div>
            </div>
            <div class="col-12 md:col-12">
              <Button
                :label="$t('page.login.label.btn-submit')"
                :loading="isLoading"
                class="w-full p-3 text-xl"
                iconPos="right"
                type="button"
                @click="submit"
              >
              </Button>
            </div>
          </div>
        </form>
      </div>
    </div>
  </div>
</template>

<style scoped>
.login-box {
  min-height: 80vh !important;
}
</style>
