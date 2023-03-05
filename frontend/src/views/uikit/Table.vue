<script setup>
import { FilterMatchMode, FilterOperator } from "primevue/api";
import CustomerService from "@/service/CustomerService";
import ProductService from "@/service/ProductService";
import { onBeforeMount, ref } from "vue";
import { useLayout } from "@/layout/composables/layout";

const { contextPath } = useLayout();

const customer1 = ref(null);
const customer2 = ref(null);
const customer3 = ref(null);
const filters1 = ref(null);
const loading1 = ref(null);
const loading2 = ref(null);
const idFrozen = ref(false);
const products = ref(null);
const expandedRows = ref([]);
const statuses = ref(["unqualified", "qualified", "new", "negotiation", "renewal", "proposal"]);
const representatives = ref([
  { name: "Amy Elsner", image: "amyelsner.png" },
  { name: "Anna Fali", image: "annafali.png" },
  { name: "Asiya Javayant", image: "asiyajavayant.png" },
  { name: "Bernardo Dominic", image: "bernardodominic.png" },
  { name: "Elwin Sharvill", image: "elwinsharvill.png" },
  { name: "Ioni Bowcher", image: "ionibowcher.png" },
  { name: "Ivan Magalhaes", image: "ivanmagalhaes.png" },
  { name: "Onyama Limba", image: "onyamalimba.png" },
  { name: "Stephen Shaw", image: "stephenshaw.png" },
  { name: "XuXue Feng", image: "xuxuefeng.png" }
]);

const customerService = new CustomerService();
const productService = new ProductService();

onBeforeMount(() => {
  productService.getProductsWithOrdersSmall().then((data) => (products.value = data));
  customerService.getCustomersLarge().then((data) => {
    customer1.value = data;
    loading1.value = false;
    customer1.value.forEach((customer) => (customer.date = new Date(customer.date)));
  });
  customerService.getCustomersLarge().then((data) => (customer2.value = data));
  customerService.getCustomersMedium().then((data) => (customer3.value = data));
  loading2.value = false;

  initFilters1();
});

const initFilters1 = () => {
  filters1.value = {
    global: { value: null, matchMode: FilterMatchMode.CONTAINS },
    name: {
      operator: FilterOperator.AND,
      constraints: [{ value: null, matchMode: FilterMatchMode.STARTS_WITH }]
    },
    "country.name": {
      operator: FilterOperator.AND,
      constraints: [{ value: null, matchMode: FilterMatchMode.STARTS_WITH }]
    },
    representative: { value: null, matchMode: FilterMatchMode.IN },
    date: {
      operator: FilterOperator.AND,
      constraints: [{ value: null, matchMode: FilterMatchMode.DATE_IS }]
    },
    balance: {
      operator: FilterOperator.AND,
      constraints: [{ value: null, matchMode: FilterMatchMode.EQUALS }]
    },
    status: {
      operator: FilterOperator.OR,
      constraints: [{ value: null, matchMode: FilterMatchMode.EQUALS }]
    },
    activity: { value: [0, 50], matchMode: FilterMatchMode.BETWEEN },
    verified: { value: null, matchMode: FilterMatchMode.EQUALS }
  };
};

const clearFilter1 = () => {
  initFilters1();
};
const expandAll = () => {
  expandedRows.value = products.value.filter((p) => p.id);
};
const collapseAll = () => {
  expandedRows.value = null;
};
const formatCurrency = (value) => {
  return value.toLocaleString("en-US", { style: "currency", currency: "USD" });
};

const formatDate = (value) => {
  return value.toLocaleDateString("en-US", {
    day: "2-digit",
    month: "2-digit",
    year: "numeric"
  });
};
const calculateCustomerTotal = (name) => {
  let total = 0;
  if (customer3.value) {
    for (let customer of customer3.value) {
      if (customer.representative.name === name) {
        total++;
      }
    }
  }

  return total;
};
</script>

<template>
  <div class="grid">
    <div class="col-12">
      <div class="card">
        <h5>Filter Menu</h5>
        <DataTable
          v-model:filters="filters1"
          :filters="filters1"
          :globalFilterFields="['name', 'country.name', 'representative.name', 'balance', 'status']"
          :loading="loading1"
          :paginator="true"
          :rowHover="true"
          :rows="10"
          :value="customer1"
          class="p-datatable-gridlines"
          dataKey="id"
          filterDisplay="menu"
          responsiveLayout="scroll"
        >
          <template #header>
            <div class="flex justify-content-between flex-column sm:flex-row">
              <Button
                class="p-button-outlined mb-2"
                icon="pi pi-filter-slash"
                label="Clear"
                type="button"
                @click="clearFilter1()"
              />
              <span class="p-input-icon-left mb-2">
                <i class="pi pi-search" />
                <InputText
                  v-model="filters1['global'].value"
                  placeholder="Keyword Search"
                  style="width: 100%"
                />
              </span>
            </div>
          </template>
          <template #empty> No customers found.</template>
          <template #loading> Loading customers data. Please wait.</template>
          <Column field="name" header="Name" style="min-width: 12rem">
            <template #body="{ data }">
              {{ data.name }}
            </template>
            <template #filter="{ filterModel }">
              <InputText
                v-model="filterModel.value"
                class="p-column-filter"
                placeholder="Search by name"
                type="text"
              />
            </template>
          </Column>
          <Column filterField="country.name" header="Country" style="min-width: 12rem">
            <template #body="{ data }">
              <img
                :alt="data.country.name"
                :class="'flag flag-' + data.country.code"
                src="/demo/images/flag/flag_placeholder.png"
                width="30"
              />
              <span class="image-text" style="margin-left: 0.5em; vertical-align: middle">{{
                  data.country.name
                }}</span>
            </template>
            <template #filter="{ filterModel }">
              <InputText
                v-model="filterModel.value"
                class="p-column-filter"
                placeholder="Search by country"
                type="text"
              />
            </template>
            <template #filterclear="{ filterCallback }">
              <Button
                class="p-button-secondary"
                icon="pi pi-times"
                type="button"
                @click="filterCallback()"
              ></Button>
            </template>
            <template #filterapply="{ filterCallback }">
              <Button
                class="p-button-success"
                icon="pi pi-check"
                type="button"
                @click="filterCallback()"
              ></Button>
            </template>
          </Column>
          <Column
            :filterMenuStyle="{ width: '14rem' }"
            :showFilterMatchModes="false"
            filterField="representative"
            header="Agent"
            style="min-width: 14rem"
          >
            <template #body="{ data }">
              <img
                :alt="data.representative.name"
                :src="contextPath + 'demo/images/avatar/' + data.representative.image"
                style="vertical-align: middle"
                width="32"
              />
              <span class="image-text" style="margin-left: 0.5em; vertical-align: middle">{{
                  data.representative.name
                }}</span>
            </template>
            <template #filter="{ filterModel }">
              <div class="mb-3 text-bold">Agent Picker</div>
              <MultiSelect
                v-model="filterModel.value"
                :options="representatives"
                class="p-column-filter"
                optionLabel="name"
                placeholder="Any"
              >
                <template #option="slotProps">
                  <div class="p-multiselect-representative-option">
                    <img
                      :alt="slotProps.option.name"
                      :src="contextPath + 'demo/images/avatar/' + slotProps.option.image"
                      style="vertical-align: middle"
                      width="32"
                    />
                    <span class="image-text" style="margin-left: 0.5em; vertical-align: middle">{{
                        slotProps.option.name
                      }}</span>
                  </div>
                </template>
              </MultiSelect>
            </template>
          </Column>
          <Column dataType="date" filterField="date" header="Date" style="min-width: 10rem">
            <template #body="{ data }">
              {{ formatDate(data.date) }}
            </template>
            <template #filter="{ filterModel }">
              <Calendar
                v-model="filterModel.value"
                dateFormat="mm/dd/yy"
                placeholder="mm/dd/yyyy"
              />
            </template>
          </Column>
          <Column
            dataType="numeric"
            filterField="balance"
            header="Balance"
            style="min-width: 10rem"
          >
            <template #body="{ data }">
              {{ formatCurrency(data.balance) }}
            </template>
            <template #filter="{ filterModel }">
              <InputNumber
                v-model="filterModel.value"
                currency="USD"
                locale="en-US"
                mode="currency"
              />
            </template>
          </Column>
          <Column
            :filterMenuStyle="{ width: '14rem' }"
            field="status"
            header="Status"
            style="min-width: 12rem"
          >
            <template #body="{ data }">
              <span :class="'customer-badge status-' + data.status">{{ data.status }}</span>
            </template>
            <template #filter="{ filterModel }">
              <Dropdown
                v-model="filterModel.value"
                :options="statuses"
                :showClear="true"
                class="p-column-filter"
                placeholder="Any"
              >
                <template #value="slotProps">
                  <span
                    v-if="slotProps.value"
                    :class="'customer-badge status-' + slotProps.value"
                  >{{ slotProps.value }}</span
                  >
                  <span v-else>{{ slotProps.placeholder }}</span>
                </template>
                <template #option="slotProps">
                  <span :class="'customer-badge status-' + slotProps.option">{{
                      slotProps.option
                    }}</span>
                </template>
              </Dropdown>
            </template>
          </Column>
          <Column
            :showFilterMatchModes="false"
            field="activity"
            header="Activity"
            style="min-width: 12rem"
          >
            <template #body="{ data }">
              <ProgressBar
                :showValue="false"
                :value="data.activity"
                style="height: 0.5rem"
              ></ProgressBar>
            </template>
            <template #filter="{ filterModel }">
              <Slider v-model="filterModel.value" :range="true" class="m-3"></Slider>
              <div class="flex align-items-center justify-content-between px-2">
                <span>{{ filterModel.value ? filterModel.value[0] : 0 }}</span>
                <span>{{ filterModel.value ? filterModel.value[1] : 100 }}</span>
              </div>
            </template>
          </Column>
          <Column
            bodyClass="text-center"
            dataType="boolean"
            field="verified"
            header="Verified"
            style="min-width: 8rem"
          >
            <template #body="{ data }">
              <i
                :class="{
                  'text-green-500 pi-check-circle': data.verified,
                  'text-pink-500 pi-times-circle': !data.verified
                }"
                class="pi"
              ></i>
            </template>
            <template #filter="{ filterModel }">
              <TriStateCheckbox v-model="filterModel.value" />
            </template>
          </Column>
        </DataTable>
      </div>
    </div>

    <div class="col-12">
      <div class="card">
        <h5>Frozen Columns</h5>
        <ToggleButton
          v-model="idFrozen"
          offIcon="pi pi-lock-open"
          offLabel="Freeze Id"
          onIcon="pi pi-lock"
          onLabel="Unfreeze Id"
          style="width: 10rem"
        />

        <DataTable
          :loading="loading2"
          :scrollable="true"
          :value="customer2"
          class="mt-3"
          scrollDirection="both"
          scrollHeight="400px"
        >
          <Column :style="{ width: '150px' }" field="name" frozen header="Name"></Column>
          <Column :frozen="idFrozen" :style="{ width: '100px' }" field="id" header="Id"></Column>
          <Column :style="{ width: '200px' }" field="country.name" header="Country">
            <template #body="{ data }">
              <img
                :class="'flag flag-' + data.country.code"
                src="/demo/images/flag/flag_placeholder.png"
                width="30"
              />
              <span class="image-text" style="margin-left: 0.5em; vertical-align: middle">{{
                  data.country.name
                }}</span>
            </template>
          </Column>
          <Column :style="{ width: '200px' }" field="date" header="Date"></Column>
          <Column :style="{ width: '200px' }" field="company" header="Company"></Column>
          <Column :style="{ width: '200px' }" field="status" header="Status">
            <template #body="{ data }">
              <span :class="'customer-badge status-' + data.status">{{ data.status }}</span>
            </template>
          </Column>
          <Column :style="{ width: '200px' }" field="activity" header="Activity"></Column>
          <Column :style="{ width: '200px' }" field="representative.name" header="Representative">
            <template #body="{ data }">
              <img
                :alt="data.representative.name"
                :src="contextPath + 'demo/images/avatar/' + data.representative.image"
                style="vertical-align: middle"
                width="32"
              />
              <span class="image-text" style="margin-left: 0.5em; vertical-align: middle">{{
                  data.representative.name
                }}</span>
            </template>
          </Column>
          <Column
            :style="{ width: '150px' }"
            alignFrozen="right"
            field="balance"
            frozen
            header="Balance"
          >
            <template #body="{ data }">
              <span class="text-bold">{{ formatCurrency(data.balance) }}</span>
            </template>
          </Column>
        </DataTable>
      </div>
    </div>

    <div class="col-12">
      <div class="card">
        <h5>Row Expand</h5>
        <DataTable
          v-model:expandedRows="expandedRows"
          :value="products"
          dataKey="id"
          responsiveLayout="scroll"
        >
          <template #header>
            <div>
              <Button class="mr-2 mb-2" icon="pi pi-plus" label="Expand All" @click="expandAll" />
              <Button class="mb-2" icon="pi pi-minus" label="Collapse All" @click="collapseAll" />
            </div>
          </template>
          <Column :expander="true" headerStyle="width: 3rem" />
          <Column :sortable="true" field="name" header="Name">
            <template #body="slotProps">
              {{ slotProps.data.name }}
            </template>
          </Column>
          <Column header="Image">
            <template #body="slotProps">
              <img
                :alt="slotProps.data.image"
                :src="contextPath + 'demo/images/product/' + slotProps.data.image"
                class="shadow-2"
                width="100"
              />
            </template>
          </Column>
          <Column :sortable="true" field="price" header="Price">
            <template #body="slotProps">
              {{ formatCurrency(slotProps.data.price) }}
            </template>
          </Column>
          <Column :sortable="true" field="category" header="Category">
            <template #body="slotProps">
              {{ formatCurrency(slotProps.data.category) }}
            </template>
          </Column
          >
          <Column :sortable="true" field="rating" header="Reviews">
            <template #body="slotProps">
              <Rating :cancel="false" :modelValue="slotProps.data.rating" :readonly="true" />
            </template>
          </Column>
          <Column :sortable="true" field="inventoryStatus" header="Status">
            <template #body="slotProps">
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
          <template #expansion="slotProps">
            <div class="p-3">
              <h5>Orders for {{ slotProps.data.name }}</h5>
              <DataTable :value="slotProps.data.orders" responsiveLayout="scroll">
                <Column :sortable="true" field="id" header="Id">
                  <template #body="slotProps">
                    {{ slotProps.data.id }}
                  </template>
                </Column>
                <Column :sortable="true" field="customer" header="Customer">
                  <template #body="slotProps">
                    {{ slotProps.data.customer }}
                  </template>
                </Column>
                <Column :sortable="true" field="date" header="Date">
                  <template #body="slotProps">
                    {{ slotProps.data.date }}
                  </template>
                </Column>
                <Column :sortable="true" field="amount" header="Amount">
                  <template #body="slotProps">
                    {{ formatCurrency(slotProps.data.amount) }}
                  </template>
                </Column>
                <Column :sortable="true" field="status" header="Status">
                  <template #body="slotProps">
                    <span
                      :class="
                        'order-badge order-' +
                        (slotProps.data.status ? slotProps.data.status.toLowerCase() : '')
                      "
                    >{{ slotProps.data.status }}</span
                    >
                  </template>
                </Column>
                <Column headerStyle="width:4rem">
                  <template #body>
                    <Button icon="pi pi-search" />
                  </template>
                </Column>
              </DataTable>
            </div>
          </template>
        </DataTable>
      </div>
    </div>

    <div class="col-12">
      <div class="card">
        <h5>Subheader Grouping</h5>
        <DataTable
          :sortOrder="1"
          :value="customer3"
          groupRowsBy="representative.name"
          rowGroupMode="subheader"
          scrollHeight="400px"
          scrollable
          sortField="representative.name"
          sortMode="single"
        >
          <Column field="representative.name" header="Representative"></Column>
          <Column field="name" header="Name" style="min-width: 200px"></Column>
          <Column field="country" header="Country" style="min-width: 200px">
            <template #body="slotProps">
              <img
                :class="'flag flag-' + slotProps.data.country.code"
                src="/demo/images/flag/flag_placeholder.png"
                width="30"
              />
              <span class="image-text ml-2">{{ slotProps.data.country.name }}</span>
            </template>
          </Column>
          <Column field="company" header="Company" style="min-width: 200px"></Column>
          <Column field="status" header="Status" style="min-width: 200px">
            <template #body="slotProps">
              <span :class="'customer-badge status-' + slotProps.data.status">{{
                  slotProps.data.status
                }}</span>
            </template>
          </Column>
          <Column field="date" header="Date" style="min-width: 200px"></Column>
          <template #groupheader="slotProps">
            <img
              :alt="slotProps.data.representative.name"
              :src="contextPath + 'demo/images/avatar/' + slotProps.data.representative.image"
              style="vertical-align: middle"
              width="32"
            />
            <span class="image-text font-bold ml-2">{{ slotProps.data.representative.name }}</span>
          </template>
          <template #groupfooter="slotProps">
            <td class="text-bold pr-6" style="text-align: right">
              Total Customers: {{ calculateCustomerTotal(slotProps.data.representative.name) }}
            </td>
          </template>
        </DataTable>
      </div>
    </div>
  </div>
</template>

<style lang="scss" scoped>
@import '@/assets/demo/styles/badges.scss';

::v-deep(.p-datatable-frozen-tbody) {
  font-weight: bold;
}

::v-deep(.p-datatable-scrollable .p-frozen-column) {
  font-weight: bold;
}
</style>
