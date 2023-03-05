<script setup>
import ProductService from "@/service/ProductService";
import PhotoService from "@/service/PhotoService";
import { onMounted, ref } from "vue";
import { useLayout } from "@/layout/composables/layout";

const { contextPath } = useLayout();

const products = ref([]);
const images = ref([]);
const galleriaResponsiveOptions = ref([
  {
    breakpoint: "1024px",
    numVisible: 5
  },
  {
    breakpoint: "960px",
    numVisible: 4
  },
  {
    breakpoint: "768px",
    numVisible: 3
  },
  {
    breakpoint: "560px",
    numVisible: 1
  }
]);
const carouselResponsiveOptions = ref([
  {
    breakpoint: "1024px",
    numVisible: 3,
    numScroll: 3
  },
  {
    breakpoint: "768px",
    numVisible: 2,
    numScroll: 2
  },
  {
    breakpoint: "560px",
    numVisible: 1,
    numScroll: 1
  }
]);

const productService = new ProductService();
const photoService = new PhotoService();

onMounted(() => {
  productService.getProductsSmall().then((data) => (products.value = data));
  photoService.getImages().then((data) => (images.value = data));
});
</script>

<template>
  <div class="grid p-fluid">
    <div class="col-12">
      <div class="card">
        <h5>Carousel</h5>
        <Carousel
          :circular="false"
          :numScroll="3"
          :numVisible="3"
          :responsiveOptions="carouselResponsiveOptions"
          :value="products"
        >
          <template #item="product">
            <div class="product-item">
              <div class="product-item-content">
                <div class="mb-3">
                  <img
                    :alt="product.data.name"
                    :src="contextPath + 'demo/images/product/' + product.data.image"
                    class="product-image"
                  />
                </div>
                <div>
                  <h4 class="mb-1">
                    {{ product.data.name }}
                  </h4>
                  <h6 class="mt-0 mb-3">${{ product.data.price }}</h6>
                  <span
                    :class="'product-badge status-' + product.data.inventoryStatus.toLowerCase()"
                  >{{ product.data.inventoryStatus }}</span
                  >
                  <div class="car-buttons mt-5">
                    <Button
                      class="p-button p-button-rounded mr-2"
                      icon="pi pi-search"
                      type="button"
                    ></Button>
                    <Button
                      class="p-button-success p-button-rounded mr-2"
                      icon="pi pi-star-fill"
                      type="button"
                    ></Button>
                    <Button
                      class="p-button-help p-button-rounded"
                      icon="pi pi-cog"
                      type="button"
                    ></Button>
                  </div>
                </div>
              </div>
            </div>
          </template>
        </Carousel>
      </div>
    </div>

    <div class="col-12">
      <div class="card">
        <h5>Galleria</h5>
        <Galleria
          :circular="true"
          :numVisible="7"
          :responsiveOptions="galleriaResponsiveOptions"
          :value="images"
          containerStyle="max-width: 800px; margin: auto"
        >
          <template #item="slotProps">
            <img
              :alt="slotProps.item.alt"
              :src="contextPath + slotProps.item.itemImageSrc"
              style="width: 100%; display: block"
            />
          </template>
          <template #thumbnail="slotProps">
            <img
              :alt="slotProps.item.alt"
              :src="contextPath + slotProps.item.thumbnailImageSrc"
              tyle="width: 100%; display: block;"
            />
          </template>
        </Galleria>
      </div>
    </div>

    <div class="col-12">
      <div class="card">
        <h5>Image</h5>
        <div class="flex justify-content-center">
          <Image
            :src="contextPath + 'demo/images/galleria/galleria11.jpg'"
            alt="Image"
            preview
            width="250"
          />
        </div>
      </div>
    </div>
  </div>
</template>

<style lang="scss" scoped>
@import '@/assets/demo/styles/badges.scss';
@import '@/assets/demo/styles/items.scss';
</style>
