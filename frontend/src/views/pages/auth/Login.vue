<script setup>
import { computed, inject, onMounted, reactive, ref } from "vue"
import AuthService from "@/service/AuthService"
import { useRouter } from "vue-router"
import LangPlate from "@/layout/LangPlate.vue"
import constants from "@/constants"
import { LocalStorageManager } from "@/helper"
import Page from "@/constants/page"

const usernameRef = ref(null)
const passwordRef = ref(null)
const data = reactive({
  username: "",
  password: "",
  rememberMe: false
})
const validation = reactive({
  username: null,
  password: null
})
const showDefaultUserBox = computed(() => import.meta.env.VITE_SHOW_DEFAULT_USER === "true")
const defaultUserBoxRef = ref(null)
const expiredDialogVisible = ref(false)

const authService = new AuthService()
const router = useRouter()
const $loading = inject("$loading")

onMounted(() => {
  if (localStorage.getItem(constants.STORAGE.SIGNIN_STATE)) {
    expiredDialogVisible.value = true
  }
})

const permanentCloseExpiredSessionDialog = () => {
  expiredDialogVisible.value = false
  LocalStorageManager.clearSigninState()
}

const isInputInvalid = () => {
  if (!data.password) {
    validation.password = "page.login.message.validation.password-required"
    passwordRef.value.$el.focus()
  }
  if (!data.username) {
    validation.username = "page.login.message.validation.username-required"
    usernameRef.value.$el.focus()
  }
  return validation.username || validation.password
}

const doLogin = async () => {
  validation.username = null
  validation.password = null
  if (!isInputInvalid()) {
    const loader = $loading.show(constants.SETTINGS.LOADER)
    //Prevent user submit too quick
    setTimeout(async () => {
      const result = await authService.login({
        username: data.username,
        password: data.password,
        rememberMe: data.rememberMe
      })
      if (result) {
        await router.push({
          path: Page.APP.DASH_BOARD.path
        })
      } else {
        validation.username = "page.login.message.validation.username-incorrect"
        validation.password = "page.login.message.validation.password-incorrect"
        usernameRef.value.$el.focus()
      }
      loader.hide()
    }, 1000)
  }
}

const onForgotPassword = async () => {
  await router.push({
    path: Page.AUTH.RESET_PASSWORD.path
  })
}

const openDefaultUserDialog = (event) => {
  defaultUserBoxRef.value.toggle(event)
}
const fillReservedUserAccount = (type) => {
  if (type === "admin") {
    data.username = "admin@localhost"
    data.password = "admin-password"
  } else if (type === "editor") {
    data.username = "editor@localhost"
    data.password = "editor-password"
  }
  doLogin()
}
</script>

<template>
  <div
    class="surface-ground flex align-items-center justify-content-center card-box min-w-min overflow-hidden"
  >
    <Dialog
      v-model:visible="expiredDialogVisible"
      :closable="false"
      :draggable="false"
      :header="$t('page.login.message.expired-dialog.title')"
      :modal="true"
      class="sm:w-1 md:w-6 lg:w-4"
      position="top"
      @keyup.esc="expiredDialogVisible = false"
    >
      <p class="m-0">
        {{ $t("page.login.message.expired-dialog.message") }}
      </p>
      <template #footer>
        <Button
          :label="$t('page.login.label.btn-expired-accept-ok')"
          autofocus
          raised
          @click="expiredDialogVisible = false"
        />
        <Button
          :label="$t('page.login.label.btn-expired-accept-off')"
          severity="warning"
          text
          @click="permanentCloseExpiredSessionDialog()"
        />
      </template>
    </Dialog>
    <div class="flex flex-column align-items-center justify-content-center m-4 md:m-6">
      <div class="flex">
        <img alt="app-logo" class="mb-5 sm:w-10rem w-8rem flex-shrink-0" src="@/assets/logo.png" />
      </div>
      <div
        class="surface-card sm:w-10 md:w-8 lg:w-5 pt-6 md:pt-8 px-4 md:px-8"
        style="border-radius: 50px"
      >
        <div class="mb-5 flex flex-row">
          <div
            class="flex-1 flex align-items-center justify-content-start text-center text-900 text-2xl"
          >
            <span class="text-left hidden md:inline">{{ $t("page.login.message.welcome") }}</span>
            <span class="text-left inline md:hidden">{{
              $t("page.login.message.welcome-short")
            }}</span>
          </div>
          <lang-plate class-layout="flex-1 flex align-items-center justify-content-end" />
        </div>

        <form @keyup.enter.prevent="doLogin">
          <div v-focustrap class="grid p-fluid">
            <div class="col-12">
              <div class="field">
                <label class="text-lg" for="username"
                  >{{ $t("page.login.label.input-username") }}
                  <span class="text-red-500">*</span></label
                >
                <span class="p-input-icon-left">
                  <i class="pi pi-user" />
                  <InputText
                    id="username"
                    ref="usernameRef"
                    v-model.trim="data.username"
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
            <div class="col-12">
              <div class="field">
                <label class="text-lg" for="password"
                  >{{ $t("page.login.label.input-password") }}
                  <span class="text-red-500">*</span></label
                >
                <span class="p-input-icon-left">
                  <i class="pi pi-key" />
                  <InputText
                    id="password"
                    ref="passwordRef"
                    v-model.trim="data.password"
                    :class="{ 'p-invalid': validation.password }"
                    aria-describedby="password-help"
                    autocomplete=""
                    class="p-inputtext-lg"
                    type="password"
                  />
                </span>

                <small v-if="validation.password" id="password-help" class="p-error font-italic">
                  {{ $t(validation.password) }}
                </small>
              </div>
            </div>
            <div class="col-12">
              <div class="flex align-items-center">
                <Checkbox id="remember-me" v-model="data.rememberMe" :binary="true" />
                <label
                  :class="{ 'font-medium': data.rememberMe }"
                  class="remember-me text-lg my-0 py-0"
                  for="remember-me"
                  @click="data.rememberMe = !data.rememberMe"
                  >{{ $t("page.login.label.input-remember-me") }}</label
                >
              </div>
            </div>
            <div class="col-12">
              <Button
                :label="$t('page.login.label.btn-submit')"
                class="w-full p-3 text-xl"
                iconPos="right"
                type="button"
                @click.prevent="doLogin"
              >
              </Button>
            </div>
            <Divider class="ml-0 mr-0 pl-0 pr-0" type="dotted" />
            <div class="col-12 p-2 mx-0 flex flex-column">
              <div class="flex align-items-center justify-content-end font-italic">
                <Button
                  :label="$t('page.login.label.btn-send-me-link')"
                  class="font-italic w-auto"
                  icon="pi pi-send"
                  iconPos="right"
                  link
                  @click.prevent
                />
              </div>
              <div class="flex align-items-center justify-content-end font-italic">
                <Button
                  :label="$t('page.login.label.btn-reset-password')"
                  class="font-italic w-auto"
                  icon="pi pi-search"
                  iconPos="right"
                  link
                  @click.prevent="onForgotPassword"
                />
              </div>
              <div
                v-if="showDefaultUserBox"
                class="flex align-items-center justify-content-end font-italic"
              >
                <Button
                  class="font-italic w-auto"
                  icon="pi pi-question-circle"
                  iconPos="right"
                  label="[Development - Use default users]"
                  link
                  @click.prevent="openDefaultUserDialog"
                />
                <OverlayPanel ref="defaultUserBoxRef">
                  <div class="flex text-center">
                    <div class="flex-1 align-items-center justify-content-center">
                      <Button
                        label="ADMIN"
                        outlined
                        text
                        type="button"
                        @click="fillReservedUserAccount('admin')"
                      />
                    </div>
                    <div class="flex-1 align-items-center justify-content-center">
                      <Button
                        label="EDITOR"
                        outlined
                        text
                        type="button"
                        @click="fillReservedUserAccount('editor')"
                      />
                    </div>
                  </div>
                </OverlayPanel>
              </div>
            </div>
          </div>
        </form>
      </div>
    </div>
  </div>
</template>

<style scoped>
.card-box {
  min-height: 80vh !important;
}

.remember-me {
  margin-left: 0.5rem;
}
</style>
