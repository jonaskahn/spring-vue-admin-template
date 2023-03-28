<script>
/**
 * This component wrote in Option API as intended
 */
import { supportedLanguages } from '@/i18n'
import { getCurrentLocale, switchLanguage } from '@/helper'

export default {
  name: 'LangPlate',
  data() {
    return {
      selectedLocale: null,
      supportedLanguages
    }
  },
  computed: {
    flag() {
      return supportedLanguages.find((lang) => lang.locale === this.selectedLocale).flag
    }
  },
  props: ['classLayout'],
  methods: {
    toggle(e) {
      this.$refs.menuLang.toggle(e)
    },
    onSwitchLanguage(newLocale, event) {
      setTimeout(() => {
        this.selectedLocale = newLocale
        switchLanguage(newLocale)
        this.$refs.menuLang.hide(event)
      }, 150)
    }
  },
  created() {
    this.selectedLocale = getCurrentLocale()
  }
}
</script>

<template>
  <div :class="classLayout">
    <Button :icon="flag" outlined raised rounded @click="toggle" />
    <OverlayPanel ref="menuLang" class="lang-plate">
      <div class="flex flex-column">
        <div
          v-for="lang in supportedLanguages"
          :key="lang.locale"
          class="flex justify-content-left h-4rem font-bold text-white border-round m-2"
        >
          <Button
            :icon="lang.flag"
            :label="lang.name"
            autofocus
            text
            type="button"
            @click="onSwitchLanguage(lang.locale, $event)"
          ></Button>
        </div>
      </div>
    </OverlayPanel>
    <Toast />
  </div>
</template>

<style>
.lang-plate {
  border-radius: 1.25rem;
}

.lang-plate .p-overlaypanel-content {
  padding: 0.5rem;
}

.lang-plate:before {
  border-bottom-color: white;
}
</style>
