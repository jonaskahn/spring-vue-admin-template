<script setup>
import ProductService from '@/service/ProductService'
import { onMounted, ref } from 'vue'
import { useToast } from 'primevue/usetoast'
import { useConfirm } from 'primevue/useconfirm'
import { useLayout } from '@/layout/composables/layout'

const { contextPath } = useLayout()

const display = ref(false)
const displayConfirmation = ref(false)
const visibleLeft = ref(false)
const visibleRight = ref(false)
const visibleTop = ref(false)
const visibleBottom = ref(false)
const visibleFull = ref(false)
const products = ref(null)
const selectedProduct = ref(null)
const op = ref(null)
const op2 = ref(null)
const popup = ref(null)

const productService = new ProductService()
const toast = useToast()
const confirmPopup = useConfirm()

onMounted(() => {
  productService.getProductsSmall().then((data) => (products.value = data))
})

const open = () => {
  display.value = true
}

const close = () => {
  display.value = false
}

const openConfirmation = () => {
  displayConfirmation.value = true
}

const closeConfirmation = () => {
  displayConfirmation.value = false
}

const toggle = (event) => {
  op.value.toggle(event)
}

const toggleDataTable = (event) => {
  op2.value.toggle(event)
}

const formatCurrency = (value) => {
  return value.toLocaleString('en-US', { style: 'currency', currency: 'USD' })
}

const onProductSelect = (event) => {
  op.value.hide()
  toast.add({ severity: 'info', summary: 'Product Selected', detail: event.data.name, life: 3000 })
}

const confirm = (event) => {
  confirmPopup.require({
    target: event.target,
    message: 'Are you sure you want to proceed?',
    icon: 'pi pi-exclamation-triangle',
    accept: () => {
      toast.add({ severity: 'info', summary: 'Confirmed', detail: 'You have accepted', life: 3000 })
    },
    reject: () => {
      toast.add({ severity: 'info', summary: 'Rejected', detail: 'You have rejected', life: 3000 })
    }
  })
}
</script>

<template>
  <div class="grid">
    <div class="col-12 lg:col-6">
      <div class="card p-fluid">
        <h5>Dialog</h5>
        <Dialog
          v-model:visible="display"
          :breakpoints="{ '960px': '75vw' }"
          :modal="true"
          :style="{ width: '30vw' }"
          header="Dialog"
        >
          <p class="line-height-3 m-0">
            Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor
            incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud
            exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure
            dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur.
            Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt
            mollit anim id est laborum.
          </p>
          <template #footer>
            <Button class="p-button-outlined" icon="pi pi-check" label="Ok" @click="close" />
          </template>
        </Dialog>
        <Button icon="pi pi-external-link" label="Show" style="width: auto" @click="open" />
      </div>
      <div class="card p-fluid">
        <h5>Overlay Panel</h5>
        <div class="flex flex-wrap gap-2">
          <div>
            <Button class="p-button-success" label="Image" type="button" @click="toggle" />
            <OverlayPanel ref="op" :showCloseIcon="true" appendTo="body">
              <img alt="Nature 9" src="/demo/images/nature/nature9.jpg" />
            </OverlayPanel>
          </div>
          <div>
            <Button
              class="p-button-success"
              label="DataTable"
              type="button"
              @click="toggleDataTable"
            />
            <OverlayPanel
              id="overlay_panel"
              ref="op2"
              :showCloseIcon="true"
              appendTo="body"
              style="width: 450px"
            >
              <DataTable
                v-model:selection="selectedProduct"
                :paginator="true"
                :rows="5"
                :value="products"
                responsiveLayout="scroll"
                selectionMode="single"
                @row-select="onProductSelect"
              >
                <Column
                  :sortable="true"
                  field="name"
                  header="Name"
                  headerStyle="min-width:10rem;"
                ></Column>
                <Column header="Image" headerStyle="min-width:10rem;">
                  <template #body="slotProps">
                    <img
                      :alt="slotProps.data.image"
                      :src="contextPath + 'demo/images/product/' + slotProps.data.image"
                      class="shadow-2"
                      width="100"
                    />
                  </template>
                </Column>
                <Column :sortable="true" field="price" header="Price" headerStyle="min-width:8rem;">
                  <template #body="slotProps">
                    {{ formatCurrency(slotProps.data.price) }}
                  </template>
                </Column>
              </DataTable>
            </OverlayPanel>
          </div>
        </div>
      </div>
    </div>
    <div class="col-12 lg:col-6">
      <div class="card p-fluid">
        <h5>Confirmation</h5>
        <Button
          class="p-button-danger"
          icon="pi pi-trash"
          label="Delete"
          style="width: auto"
          @click="openConfirmation"
        />
        <Dialog
          v-model:visible="displayConfirmation"
          :modal="true"
          :style="{ width: '350px' }"
          header="Confirmation"
        >
          <div class="flex align-items-center justify-content-center">
            <i class="pi pi-exclamation-triangle mr-3" style="font-size: 2rem" />
            <span>Are you sure you want to proceed?</span>
          </div>
          <template #footer>
            <Button
              class="p-button-text"
              icon="pi pi-times"
              label="No"
              @click="closeConfirmation"
            />
            <Button
              autofocus
              class="p-button-text"
              icon="pi pi-check"
              label="Yes"
              @click="closeConfirmation"
            />
          </template>
        </Dialog>
      </div>
      <div class="card">
        <h5>Sidebar</h5>
        <Sidebar v-model:visible="visibleLeft" :baseZIndex="1000">
          <h1 style="font-weight: normal">Left Sidebar</h1>
        </Sidebar>

        <Sidebar v-model:visible="visibleRight" :baseZIndex="1000" position="right">
          <h1 style="font-weight: normal">Right Sidebar</h1>
        </Sidebar>

        <Sidebar v-model:visible="visibleTop" :baseZIndex="1000" position="top">
          <h1 style="font-weight: normal">Top Sidebar</h1>
        </Sidebar>

        <Sidebar v-model:visible="visibleBottom" :baseZIndex="1000" position="bottom">
          <h1 style="font-weight: normal">Bottom Sidebar</h1>
        </Sidebar>

        <Sidebar v-model:visible="visibleFull" :baseZIndex="1000" position="full">
          <h1 style="font-weight: normal">Full Screen</h1>
        </Sidebar>

        <Button
          class="p-button-warning"
          icon="pi pi-arrow-right"
          style="margin-right: 0.25em"
          @click="visibleLeft = true"
        />
        <Button
          class="p-button-warning"
          icon="pi pi-arrow-left"
          style="margin-right: 0.25em"
          @click="visibleRight = true"
        />
        <Button
          class="p-button-warning"
          icon="pi pi-arrow-down"
          style="margin-right: 0.25em"
          @click="visibleTop = true"
        />
        <Button
          class="p-button-warning"
          icon="pi pi-arrow-up"
          style="margin-right: 0.25em"
          @click="visibleBottom = true"
        />
        <Button class="p-button-warning" icon="pi pi-external-link" @click="visibleFull = true" />
      </div>
    </div>
    <div class="col-12 lg:col-6">
      <div class="card">
        <h5>Tooltip</h5>
        <div class="formgroup-inline">
          <div class="field">
            <InputText v-tooltip="'Your username'" placeholder="Username" type="text" />
          </div>

          <Button v-tooltip="'Click to proceed'" icon="pi pi-check" label="Save" type="button" />
        </div>
      </div>
    </div>
    <div class="col-12 lg:col-6">
      <div class="card">
        <h5>ConfirmPopup</h5>
        <ConfirmPopup></ConfirmPopup>
        <Toast />
        <Button
          ref="popup"
          class="mr-2"
          icon="pi pi-check"
          label="Confirm"
          @click="confirm($event)"
        ></Button>
      </div>
    </div>
  </div>
</template>
