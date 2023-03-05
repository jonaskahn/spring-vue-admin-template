<script setup>
import { FilterMatchMode } from 'primevue/api'
import { onBeforeMount, onMounted, ref } from 'vue'
import ProductService from '@/service/ProductService'
import { useToast } from 'primevue/usetoast'
import { useLayout } from '@/layout/composables/layout'

const toast = useToast()
const { contextPath } = useLayout()

const products = ref(null)
const productDialog = ref(false)
const deleteProductDialog = ref(false)
const deleteProductsDialog = ref(false)
const product = ref({})
const selectedProducts = ref(null)
const dt = ref(null)
const filters = ref({})
const submitted = ref(false)
const statuses = ref([
  { label: 'INSTOCK', value: 'instock' },
  { label: 'LOWSTOCK', value: 'lowstock' },
  { label: 'OUTOFSTOCK', value: 'outofstock' }
])

const productService = new ProductService()

onBeforeMount(() => {
  initFilters()
})
onMounted(() => {
  productService.getProducts().then((data) => (products.value = data))
})
const formatCurrency = (value) => {
  return value.toLocaleString('en-US', { style: 'currency', currency: 'USD' })
}

const openNew = () => {
  product.value = {}
  submitted.value = false
  productDialog.value = true
}

const hideDialog = () => {
  productDialog.value = false
  submitted.value = false
}

const saveProduct = () => {
  submitted.value = true
  if (product.value.name && product.value.name.trim() && product.value.price) {
    if (product.value.id) {
      product.value.inventoryStatus = product.value.inventoryStatus.value
        ? product.value.inventoryStatus.value
        : product.value.inventoryStatus
      products.value[findIndexById(product.value.id)] = product.value
      toast.add({
        severity: 'success',
        summary: 'Successful',
        detail: 'Product Updated',
        life: 3000
      })
    } else {
      product.value.id = createId()
      product.value.code = createId()
      product.value.image = 'product-placeholder.svg'
      product.value.inventoryStatus = product.value.inventoryStatus
        ? product.value.inventoryStatus.value
        : 'INSTOCK'
      products.value.push(product.value)
      toast.add({
        severity: 'success',
        summary: 'Successful',
        detail: 'Product Created',
        life: 3000
      })
    }
    productDialog.value = false
    product.value = {}
  }
}

const editProduct = (editProduct) => {
  product.value = { ...editProduct }
  console.log(product)
  productDialog.value = true
}

const confirmDeleteProduct = (editProduct) => {
  product.value = editProduct
  deleteProductDialog.value = true
}

const deleteProduct = () => {
  products.value = products.value.filter((val) => val.id !== product.value.id)
  deleteProductDialog.value = false
  product.value = {}
  toast.add({ severity: 'success', summary: 'Successful', detail: 'Product Deleted', life: 3000 })
}

const findIndexById = (id) => {
  let index = -1
  for (let i = 0; i < products.value.length; i++) {
    if (products.value[i].id === id) {
      index = i
      break
    }
  }
  return index
}

const createId = () => {
  let id = ''
  const chars = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789'
  for (let i = 0; i < 5; i++) {
    id += chars.charAt(Math.floor(Math.random() * chars.length))
  }
  return id
}

const exportCSV = () => {
  dt.value.exportCSV()
}

const confirmDeleteSelected = () => {
  deleteProductsDialog.value = true
}
const deleteSelectedProducts = () => {
  products.value = products.value.filter((val) => !selectedProducts.value.includes(val))
  deleteProductsDialog.value = false
  selectedProducts.value = null
  toast.add({ severity: 'success', summary: 'Successful', detail: 'Products Deleted', life: 3000 })
}

const initFilters = () => {
  filters.value = {
    global: { value: null, matchMode: FilterMatchMode.CONTAINS }
  }
}
</script>

<template>
  <div class="grid">
    <div class="col-12">
      <div class="card">
        <Toast />
        <Toolbar class="mb-4">
          <template v-slot:start>
            <div class="my-2">
              <Button
                class="p-button-success mr-2"
                icon="pi pi-plus"
                label="New"
                @click="openNew"
              />
              <Button
                :disabled="!selectedProducts || !selectedProducts.length"
                class="p-button-danger"
                icon="pi pi-trash"
                label="Delete"
                @click="confirmDeleteSelected"
              />
            </div>
          </template>

          <template v-slot:end>
            <FileUpload
              :maxFileSize="1000000"
              accept="image/*"
              chooseLabel="Import"
              class="mr-2 inline-block"
              label="Import"
              mode="basic"
            />
            <Button
              class="p-button-help"
              icon="pi pi-upload"
              label="Export"
              @click="exportCSV($event)"
            />
          </template>
        </Toolbar>

        <DataTable
          ref="dt"
          v-model:selection="selectedProducts"
          :filters="filters"
          :paginator="true"
          :rows="10"
          :rowsPerPageOptions="[5, 10, 25]"
          :value="products"
          currentPageReportTemplate="Showing {first} to {last} of {totalRecords} products"
          dataKey="id"
          paginatorTemplate="FirstPageLink PrevPageLink PageLinks NextPageLink LastPageLink CurrentPageReport RowsPerPageDropdown"
          responsiveLayout="scroll"
        >
          <template #header>
            <div
              class="flex flex-column md:flex-row md:justify-content-between md:align-items-center"
            >
              <h5 class="m-0">Manage Products</h5>
              <span class="block mt-2 md:mt-0 p-input-icon-left">
                <i class="pi pi-search" />
                <InputText v-model="filters['global'].value" placeholder="Search..." />
              </span>
            </div>
          </template>

          <Column headerStyle="width: 3rem" selectionMode="multiple"></Column>
          <Column
            :sortable="true"
            field="code"
            header="Code"
            headerStyle="width:14%; min-width:10rem;"
          >
            <template #body="slotProps">
              <span class="p-column-title">Code</span>
              {{ slotProps.data.code }}
            </template>
          </Column>
          <Column
            :sortable="true"
            field="name"
            header="Name"
            headerStyle="width:14%; min-width:10rem;"
          >
            <template #body="slotProps">
              <span class="p-column-title">Name</span>
              {{ slotProps.data.name }}
            </template>
          </Column>
          <Column header="Image" headerStyle="width:14%; min-width:10rem;">
            <template #body="slotProps">
              <span class="p-column-title">Image</span>
              <img
                :alt="slotProps.data.image"
                :src="contextPath + 'demo/images/product/' + slotProps.data.image"
                class="shadow-2"
                width="100"
              />
            </template>
          </Column>
          <Column
            :sortable="true"
            field="price"
            header="Price"
            headerStyle="width:14%; min-width:8rem;"
          >
            <template #body="slotProps">
              <span class="p-column-title">Price</span>
              {{ formatCurrency(slotProps.data.price) }}
            </template>
          </Column>
          <Column
            :sortable="true"
            field="category"
            header="Category"
            headerStyle="width:14%; min-width:10rem;"
          >
            <template #body="slotProps">
              <span class="p-column-title">Category</span>
              {{ slotProps.data.category }}
            </template>
          </Column>
          <Column
            :sortable="true"
            field="rating"
            header="Reviews"
            headerStyle="width:14%; min-width:10rem;"
          >
            <template #body="slotProps">
              <span class="p-column-title">Rating</span>
              <Rating :cancel="false" :modelValue="slotProps.data.rating" :readonly="true" />
            </template>
          </Column>
          <Column
            :sortable="true"
            field="inventoryStatus"
            header="Status"
            headerStyle="width:14%; min-width:10rem;"
          >
            <template #body="slotProps">
              <span class="p-column-title">Status</span>
              <span
                :class="
                  'product-badge status-' +
                  (slotProps.data.inventoryStatus
                    ? slotProps.data.inventoryStatus.toLowerCase()
                    : '')
                "
                >{{ slotProps.data.inventoryStatus }}</span
              >
            </template>
          </Column>
          <Column headerStyle="min-width:10rem;">
            <template #body="slotProps">
              <Button
                class="p-button-rounded p-button-success mr-2"
                icon="pi pi-pencil"
                @click="editProduct(slotProps.data)"
              />
              <Button
                class="p-button-rounded p-button-warning mt-2"
                icon="pi pi-trash"
                @click="confirmDeleteProduct(slotProps.data)"
              />
            </template>
          </Column>
        </DataTable>

        <Dialog
          v-model:visible="productDialog"
          :modal="true"
          :style="{ width: '450px' }"
          class="p-fluid"
          header="Product Details"
        >
          <img
            v-if="product.image"
            :alt="product.image"
            :src="contextPath + 'demo/images/product/' + product.image"
            class="mt-0 mx-auto mb-5 block shadow-2"
            width="150"
          />
          <div class="field">
            <label for="name">Name</label>
            <InputText
              id="name"
              v-model.trim="product.name"
              :class="{ 'p-invalid': submitted && !product.name }"
              autofocus
              required="true"
            />
            <small v-if="submitted && !product.name" class="p-invalid">Name is required.</small>
          </div>
          <div class="field">
            <label for="description">Description</label>
            <Textarea
              id="description"
              v-model="product.description"
              cols="20"
              required="true"
              rows="3"
            />
          </div>

          <div class="field">
            <label class="mb-3" for="inventoryStatus">Inventory Status</label>
            <Dropdown
              id="inventoryStatus"
              v-model="product.inventoryStatus"
              :options="statuses"
              optionLabel="label"
              placeholder="Select a Status"
            >
              <template #value="slotProps">
                <div v-if="slotProps.value && slotProps.value.value">
                  <span :class="'product-badge status-' + slotProps.value.value">{{
                    slotProps.value.label
                  }}</span>
                </div>
                <div v-else-if="slotProps.value && !slotProps.value.value">
                  <span :class="'product-badge status-' + slotProps.value.toLowerCase()">{{
                    slotProps.value
                  }}</span>
                </div>
                <span v-else>
                  {{ slotProps.placeholder }}
                </span>
              </template>
            </Dropdown>
          </div>

          <div class="field">
            <label class="mb-3">Category</label>
            <div class="formgrid grid">
              <div class="field-radiobutton col-6">
                <RadioButton
                  id="category1"
                  v-model="product.category"
                  name="category"
                  value="Accessories"
                />
                <label for="category1">Accessories</label>
              </div>
              <div class="field-radiobutton col-6">
                <RadioButton
                  id="category2"
                  v-model="product.category"
                  name="category"
                  value="Clothing"
                />
                <label for="category2">Clothing</label>
              </div>
              <div class="field-radiobutton col-6">
                <RadioButton
                  id="category3"
                  v-model="product.category"
                  name="category"
                  value="Electronics"
                />
                <label for="category3">Electronics</label>
              </div>
              <div class="field-radiobutton col-6">
                <RadioButton
                  id="category4"
                  v-model="product.category"
                  name="category"
                  value="Fitness"
                />
                <label for="category4">Fitness</label>
              </div>
            </div>
          </div>

          <div class="formgrid grid">
            <div class="field col">
              <label for="price">Price</label>
              <InputNumber
                id="price"
                v-model="product.price"
                :class="{ 'p-invalid': submitted && !product.price }"
                :required="true"
                currency="USD"
                locale="en-US"
                mode="currency"
              />
              <small v-if="submitted && !product.price" class="p-invalid">Price is required.</small>
            </div>
            <div class="field col">
              <label for="quantity">Quantity</label>
              <InputNumber id="quantity" v-model="product.quantity" integeronly />
            </div>
          </div>
          <template #footer>
            <Button class="p-button-text" icon="pi pi-times" label="Cancel" @click="hideDialog" />
            <Button class="p-button-text" icon="pi pi-check" label="Save" @click="saveProduct" />
          </template>
        </Dialog>

        <Dialog
          v-model:visible="deleteProductDialog"
          :modal="true"
          :style="{ width: '450px' }"
          header="Confirm"
        >
          <div class="flex align-items-center justify-content-center">
            <i class="pi pi-exclamation-triangle mr-3" style="font-size: 2rem" />
            <span v-if="product"
              >Are you sure you want to delete <b>{{ product.name }}</b
              >?</span
            >
          </div>
          <template #footer>
            <Button
              class="p-button-text"
              icon="pi pi-times"
              label="No"
              @click="deleteProductDialog = false"
            />
            <Button class="p-button-text" icon="pi pi-check" label="Yes" @click="deleteProduct" />
          </template>
        </Dialog>

        <Dialog
          v-model:visible="deleteProductsDialog"
          :modal="true"
          :style="{ width: '450px' }"
          header="Confirm"
        >
          <div class="flex align-items-center justify-content-center">
            <i class="pi pi-exclamation-triangle mr-3" style="font-size: 2rem" />
            <span v-if="product">Are you sure you want to delete the selected products?</span>
          </div>
          <template #footer>
            <Button
              class="p-button-text"
              icon="pi pi-times"
              label="No"
              @click="deleteProductsDialog = false"
            />
            <Button
              class="p-button-text"
              icon="pi pi-check"
              label="Yes"
              @click="deleteSelectedProducts"
            />
          </template>
        </Dialog>
      </div>
    </div>
  </div>
</template>

<style lang="scss" scoped>
@import '@/assets/demo/styles/badges.scss';
</style>
