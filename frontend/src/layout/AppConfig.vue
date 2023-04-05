<script setup>
import RadioButton from "primevue/radiobutton"
import Button from "primevue/button"
import InputSwitch from "primevue/inputswitch"
import Sidebar from "primevue/sidebar"

import { ref } from "vue"
import { useLayout } from "@/layout/composables/layout"

defineProps({
  simple: {
    type: Boolean,
    default: false
  }
})
const scales = ref([12, 13, 14, 15, 16])
const visible = ref(false)

const { changeThemeSettings, setScale, layoutConfig } = useLayout()

const onConfigButtonClick = () => {
  visible.value = !visible.value
}
const onChangeTheme = (theme, mode) => {
  const elementId = "theme-css"
  const linkElement = document.getElementById(elementId)
  const cloneLinkElement = linkElement.cloneNode(true)
  const newThemeUrl = linkElement.getAttribute("href").replace(layoutConfig.theme.value, theme)
  cloneLinkElement.setAttribute("id", elementId + "-clone")
  cloneLinkElement.setAttribute("href", newThemeUrl)
  cloneLinkElement.addEventListener("load", () => {
    linkElement.remove()
    cloneLinkElement.setAttribute("id", elementId)
    changeThemeSettings(theme, mode === "dark")
  })
  linkElement.parentNode.insertBefore(cloneLinkElement, linkElement.nextSibling)
}
const decrementScale = () => {
  setScale(layoutConfig.scale.value - 1)
  applyScale()
}
const incrementScale = () => {
  setScale(layoutConfig.scale.value + 1)
  applyScale()
}
const applyScale = () => {
  document.documentElement.style.fontSize = layoutConfig.scale.value + "px"
}
</script>

<template>
  <button class="layout-config-button p-link" type="button" @click="onConfigButtonClick()">
    <i class="pi pi-cog"></i>
  </button>

  <Sidebar
    v-model:visible="visible"
    :transitionOptions="'.3s cubic-bezier(0, 0, 0.2, 1)'"
    class="layout-config-sidebar w-20rem"
    position="right"
  >
    <h5>Scale</h5>
    <div class="flex align-items-center">
      <Button
        :disabled="layoutConfig.scale.value === scales[0]"
        class="p-button-text p-button-rounded w-2rem h-2rem mr-2"
        icon="pi pi-minus"
        type="button"
        @click="decrementScale()"
      ></Button>
      <div class="flex gap-2 align-items-center">
        <i
          v-for="s in scales"
          :key="s"
          :class="{ 'text-primary-500': s === layoutConfig.scale.value }"
          class="pi pi-circle-fill text-300"
        ></i>
      </div>
      <Button
        :disabled="layoutConfig.scale.value === scales[scales.length - 1]"
        class="p-button-text p-button-rounded w-2rem h-2rem ml-2"
        icon="pi pi-plus"
        pButton
        type="button"
        @click="incrementScale()"
      ></Button>
    </div>

    <template v-if="!simple">
      <h5>Menu Type</h5>
      <div class="flex">
        <div class="field-radiobutton flex-1">
          <RadioButton
            v-model="layoutConfig.menuMode.value"
            inputId="mode1"
            name="menuMode"
            value="static"
          ></RadioButton>
          <label for="mode1">Static</label>
        </div>

        <div class="field-radiobutton flex-1">
          <RadioButton
            v-model="layoutConfig.menuMode.value"
            inputId="mode2"
            name="menuMode"
            value="overlay"
          ></RadioButton>
          <label for="mode2">Overlay</label>
        </div>
      </div>
    </template>

    <template v-if="!simple">
      <h5>Input Style</h5>
      <div class="flex">
        <div class="field-radiobutton flex-1">
          <RadioButton
            v-model="layoutConfig.inputStyle.value"
            inputId="outlined_input"
            name="inputStyle"
            value="outlined"
          ></RadioButton>
          <label for="outlined_input">Outlined</label>
        </div>
        <div class="field-radiobutton flex-1">
          <RadioButton
            v-model="layoutConfig.inputStyle.value"
            inputId="filled_input"
            name="inputStyle"
            value="filled"
          ></RadioButton>
          <label for="filled_input">Filled</label>
        </div>
      </div>

      <h5>Ripple Effect</h5>
      <InputSwitch v-model="layoutConfig.ripple.value"></InputSwitch>
    </template>
  </Sidebar>
</template>

<style lang="scss" scoped></style>
