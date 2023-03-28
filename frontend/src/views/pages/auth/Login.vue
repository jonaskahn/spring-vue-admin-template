<script setup>
import { inject, onMounted, reactive, ref } from 'vue'
import AuthService from '@/service/AuthService'
import { useRouter } from 'vue-router'
import routeInfo from '@/constants/page'
import LangPlate from '@/layout/LangPlate.vue'
import constants from '@/constants'
import { LocalStorageManager } from '@/helper'

const usernameRef = ref(null)
const passwordRef = ref(null)
const data = reactive({
  username: '',
  password: ''
})

const dialogVisibleRef = ref(false)
onMounted(() => {
  if (localStorage.getItem(constants.STORAGE.SIGNIN_STATE)) {
    dialogVisibleRef.value = true
  }
})

function permanentCloseExpiredSessionDialog() {
  dialogVisibleRef.value = false
  LocalStorageManager.clearSigninState()
}

const validation = reactive({
  username: null,
  password: null
})

function invalidInput() {
  if (!data.password.trim()) {
    validation.password = 'page.login.message.validation.password-required'
    passwordRef.value.$el.focus()
  }
  if (!data.username.trim()) {
    validation.username = 'page.login.message.validation.username-required'
    usernameRef.value.$el.focus()
  }
  return validation.username || validation.password
}

const authService = new AuthService()
const router = useRouter()
const $loading = inject('$loading')

async function submit() {
  validation.username = null
  validation.password = null
  if (!invalidInput()) {
    const loader = $loading.show(constants.SETTINGS.LOADER)
    //Prevent user submit too quick
    setTimeout(async () => {
      const result = await authService.login({
        username: data.username.trim(),
        password: data.password.trim()
      })
      if (result) {
        await router.push({
          path: routeInfo.APP.DASH_BOARD.path
        })
      } else {
        validation.username = 'page.login.message.validation.username-incorrect'
        validation.password = 'page.login.message.validation.password-incorrect'
        usernameRef.value.$el.focus()
      }
      loader.hide()
    }, 1000)
  }
}

function userDefaultAccount(type) {
  if (type === 'admin') {
    data.username = 'admin'
    data.password = 'admin-password'
  } else if (type === 'editor') {
    data.username = 'editor'
    data.password = 'editor-password'
  }
  submit()
}
</script>

<template>
  <div
    class="surface-ground flex align-items-center justify-content-center login-box min-w-min overflow-hidden"
  >
    <Dialog
      v-model:visible="dialogVisibleRef"
      :closable="false"
      :draggable="false"
      :header="$t('page.login.message.expired-dialog.title')"
      :modal="true"
      class="sm:w-1 md:w-6 lg:w-4"
      position="top"
      @keyup.esc="dialogVisibleRef = false"
    >
      <p class="m-0">
        {{ $t('page.login.message.expired-dialog.message') }}
      </p>
      <template #footer>
        <Button
          :label="$t('page.login.label.btn-expired-accept-ok')"
          autofocus
          raised
          @click="dialogVisibleRef = false"
        />
        <Button
          :label="$t('page.login.label.btn-expired-accept-off')"
          severity="warning"
          text
          @click="permanentCloseExpiredSessionDialog()"
        />
      </template>
    </Dialog>
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
            class="flex-1 flex align-items-center justify-content-start text-center text-900 text-3xl font-medium"
          >
            {{ $t('page.login.message.welcome') }}
          </div>
          <lang-plate class-layout="flex-1 flex align-items-center justify-content-end" />
        </div>

        <form @keyup.enter.prevent="submit">
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
                  {{ $t(validation.username) }}
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
                  {{ $t(validation.password) }}
                </small>
              </div>
            </div>
            <div class="col-12 md:col-12">
              <Button
                :label="$t('page.login.label.btn-submit')"
                class="w-full p-3 text-xl"
                iconPos="right"
                type="button"
                @click="submit"
              >
              </Button>
            </div>
          </div>
        </form>
        <Divider type="dotted" />
        <div class="col-12 md:col-12">
          <Panel class="Card" header="Default users">
            <div class="flex card-container indigo-container">
              <div class="flex-1 align-items-center justify-content-center">
                <Button
                  aria-label="Filter"
                  icon="pi pi-user"
                  label="use admin account"
                  outlined
                  type="button"
                  @click="userDefaultAccount('admin')"
                />
              </div>
              <div class="flex-1 align-items-center justify-content-center">
                <Button
                  aria-label="Filter"
                  icon="pi pi-user"
                  label="use editor account"
                  outlined
                  type="button"
                  @click="userDefaultAccount('editor')"
                />
              </div>
            </div>
          </Panel>
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
