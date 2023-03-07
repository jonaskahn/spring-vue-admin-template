<script setup></script>

<template>
  <div
    class="surface-ground flex align-items-center justify-content-center min-h-screen min-w-screen overflow-hidden"
  >
    <div class="flex flex-column align-items-center justify-content-center">
      <img alt="Sakai logo" class="mb-5 w-6rem flex-shrink-0" src="@/assets/logo.png" />
      <div
        class="w-full surface-card py-8 px-5 sm:px-8 flex flex-column align-items-center"
        style="border-radius: 53px"
      >
        <div class="grid flex flex-column align-items-center">
          <div
            class="flex justify-content-center align-items-center bg-pink-500 border-circle"
            style="height: 3.2rem; width: 3.2rem"
          >
            <i class="pi pi-fw pi-exclamation-circle text-2xl text-white"></i>
          </div>
          <h1 class="text-900 font-bold text-5xl mb-2">Error Occured</h1>
          <span class="text-600 mb-5">Requested resource is not available.</span>
          <img alt="Error" class="mb-5" src="/demo/images/error/asset-error.svg" width="80%" />
          <div class="col-12 mt-5 text-center">
            <i class="pi pi-fw pi-arrow-left text-blue-500 mr-2" style="vertical-align: center"></i>
            <router-link class="text-blue-500" to="/">Go to Dashboard</router-link>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
