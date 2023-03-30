<script setup>
import { onBeforeMount } from 'vue'
import { useRoute } from 'vue-router'
import UserService from '@/service/UserService'

const userService = new UserService()

onBeforeMount(async () => {
  await userService.verifyEmail({
    code: useRoute().params.code
  })
})
</script>

<template>
  <div>
    {{ $route.params.code }}
  </div>
</template>

<style scoped></style>
