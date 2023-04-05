<script setup>
import { inject, reactive, ref } from "vue"
import LangPlate from "@/layout/LangPlate.vue"
import constants from "@/constants"

const username = reactive({
  data: "",
  validation: null
})
const usernameRef = ref(null)
const $loading = inject("$loading")

const onSubmitResetPassword = () => {
  username.validation = null
  if (!username.data) {
    username.validation = "page.login.message.validation.username-required"
    usernameRef.value.$el.focus()
  } else {
    const loader = $loading.show(constants.SETTINGS.LOADER)
    setTimeout(() => {
      loader.hide()
    }, 1000)
  }
}
</script>

<template>
  <div
    class="surface-ground flex align-items-center justify-content-center card-box min-w-min overflow-hidden"
  >
    <div class="flex flex-column align-items-center justify-content-center m-4 md:m-8">
      <div class="flex">
        <img alt="app-logo" class="mb-5 sm:w-10rem w-8rem flex-shrink-0" src="@/assets/logo.png" />
      </div>
      <div class="surface-card w-full px-4 pt-6 pb-4 md:px-8" style="border-radius: 50px">
        <div class="mb-5 flex flex-row">
          <div class="flex-1 flex align-items-center justify-content-start">
            <Button
              :title="$t('page.reset-password.label.btn-back')"
              icon="pi pi-arrow-left"
              iconPos="left"
              outlined
              raised
              rounded
              type="button"
              @click.prevent="$router.go(-1)"
            />
          </div>

          <div
            class="flex-2 flex align-items-center justify-content-center text-center text-900 text-2xl"
          >
            <span class="text-left hidden md:inline">{{
              $t("page.reset-password.message.welcome")
            }}</span>
          </div>
          <lang-plate class-layout="flex-1 flex align-items-center justify-content-end" />
        </div>

        <form @keyup.enter.prevent="onSubmitResetPassword">
          <div v-focustrap class="grid p-fluid">
            <div class="col-12">
              <div class="field">
                <label class="text-lg" for="fg-username"
                  >{{ $t("page.login.label.input-username") }}
                  <span class="text-red-500">*</span></label
                >
                <span class="p-input-icon-left">
                  <i class="pi pi-user" />
                  <InputText
                    id="fg-username"
                    ref="usernameRef"
                    v-model.trim="username.data"
                    :class="{ 'p-invalid': username.validation }"
                    autofocus
                    class="p-inputtext-lg"
                    type="username"
                  />
                </span>
                <small v-if="username.validation" id="fg-username-help" class="p-error font-italic">
                  {{ $t(username.validation) }}
                </small>
              </div>
            </div>
            <div class="col-12">
              <Button
                :label="$t('page.reset-password.label.btn-reset-password')"
                class="w-full p-3 text-xl"
                iconPos="right"
                type="button"
                @click.prevent="onSubmitResetPassword"
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
.card-box {
  min-height: 80vh !important;
}
</style>
