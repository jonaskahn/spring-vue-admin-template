<script>
  export default {
    name: 'LangPlate',
    methods: {
      toggle(e) {
          this.$refs.menuLang.toggle(e)
      }
    }
  }
</script>

<template>
  <div>
    <Button icon="fi fi-vn" outlined raised rounded text @click="toggle" />
    <OverlayPanel ref="menuLang" class="">
      <div class="flex flex-column">
        <div class="flex justify-content-left h-4rem font-bold text-white border-round m-2">
          <Button icon="fi fi-vn" label="Vietnamese" text></Button>
        </div>
        <div class="flex justify-content-left h-4rem font-bold text-white border-round m-2">
          <Button icon="fi fi-us" label="English" text></Button>
        </div>
      </div>
    </OverlayPanel>
    <Toast />
  </div>
</template>

<style scoped>
:deep(.p-overlaypanel .p-overlaypanel-content) {
  padding: 0.25rem;
}
</style>
