<script setup>
import { onBeforeUnmount, onMounted, ref } from "vue";
import { useLayout } from "@/layout/composables/layout";

const { contextPath } = useLayout();

const value = ref(0);
let interval = null;

const startProgress = () => {
  interval = setInterval(() => {
    let newValue = value.value + Math.floor(Math.random() * 10) + 1;
    if (newValue >= 100) {
      newValue = 100;
    }
    value.value = newValue;
  }, 2000);
};
const endProgress = () => {
  clearInterval(interval);
  interval = null;
};

onMounted(() => {
  startProgress();
});

onBeforeUnmount(() => {
  endProgress();
});
</script>

<template>
  <div class="grid">
    <div class="col-12">
      <div class="card">
        <h5>ProgressBar</h5>
        <div class="grid">
          <div class="col">
            <ProgressBar :value="value"></ProgressBar>
          </div>
          <div class="col">
            <ProgressBar :showValue="false" :value="50"></ProgressBar>
          </div>
        </div>
      </div>
    </div>
    <div class="col-12 lg:col-6">
      <div class="card">
        <h4>Badge</h4>
        <h5>Numbers</h5>
        <div class="badges">
          <Badge :value="2" class="mr-2"></Badge>
          <Badge :value="8" class="mr-2" severity="success"></Badge>
          <Badge :value="4" class="mr-2" severity="info"></Badge>
          <Badge :value="12" class="mr-2" severity="warning"></Badge>
          <Badge :value="3" severity="danger"></Badge>
        </div>

        <h5>Positioned Badge</h5>
        <i v-badge="2" class="pi pi-bell mr-4 p-text-secondary" style="font-size: 2rem"></i>
        <i
          v-badge.danger="'10+'"
          class="pi pi-calendar mr-4 p-text-secondary"
          style="font-size: 2rem"
        ></i>
        <i v-badge.danger class="pi pi-envelope p-text-secondary" style="font-size: 2rem"></i>

        <h5>Inline Button Badge</h5>
        <Button badge="8" class="mr-2" label="Emails"></Button>
        <Button
          badge="8"
          badgeClass="p-badge-danger"
          class="p-button-warning"
          icon="pi pi-users"
          label="Messages"
        ></Button>

        <h5>Sizes</h5>
        <div class="badges">
          <Badge :value="2" class="mr-2"></Badge>
          <Badge :value="4" class="mr-2" severity="warning" size="large"></Badge>
          <Badge :value="6" severity="success" size="xlarge"></Badge>
        </div>
      </div>

      <div class="card">
        <h4>Avatar</h4>
        <h5>Avatar Group</h5>
        <AvatarGroup class="mb-3">
          <Avatar
            :image="contextPath + 'demo/images/avatar/amyelsner.png'"
            shape="circle"
            size="large"
          ></Avatar>
          <Avatar
            :image="contextPath + 'demo/images/avatar/asiyajavayant.png'"
            shape="circle"
            size="large"
          ></Avatar>
          <Avatar
            :image="contextPath + 'demo/images/avatar/onyamalimba.png'"
            shape="circle"
            size="large"
          ></Avatar>
          <Avatar
            :image="contextPath + 'demo/images/avatar/ionibowcher.png'"
            shape="circle"
            size="large"
          ></Avatar>
          <Avatar
            :image="contextPath + 'demo/images/avatar/xuxuefeng.png'"
            shape="circle"
            size="large"
          ></Avatar>
          <Avatar
            :style="{ 'background-color': '#9c27b0', color: '#ffffff' }"
            label="+2"
            shape="circle"
            size="large"
          ></Avatar>
        </AvatarGroup>

        <h5>Label - Circle</h5>
        <Avatar class="mr-2" label="P" shape="circle" size="xlarge"></Avatar>
        <Avatar
          :style="{ 'background-color': '#2196F3', color: '#ffffff' }"
          class="mr-2"
          label="V"
          shape="circle"
          size="large"
        ></Avatar>
        <Avatar
          :style="{ 'background-color': '#9c27b0', color: '#ffffff' }"
          class="mr-2"
          label="U"
          shape="circle"
        ></Avatar>

        <h5>Icon - Badge</h5>
        <Avatar v-badge.success="4" class="mr-2" icon="pi pi-user" size="xlarge"></Avatar>
      </div>

      <div class="card">
        <h4>ScrollTop</h4>
        <ScrollPanel :style="{ width: '250px', height: '200px' }">
          <p>
            Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor
            incididunt ut labore et dolore magna aliqua. Vitae et leo duis ut diam. Ultricies mi
            quis hendrerit dolor magna eget est lorem. Amet consectetur adipiscing elit ut. Nam
            libero justo laoreet sit amet. Pharetra massa massa ultricies mi quis hendrerit dolor
            magna. Est ultricies integer quis auctor elit sed vulputate. Consequat ac felis donec
            et. Tellus orci ac auctor augue mauris. Semper feugiat nibh sed pulvinar proin gravida
            hendrerit lectus a. Tincidunt arcu non sodales neque sodales. Metus aliquam eleifend mi
            in nulla posuere sollicitudin aliquam ultrices. Sodales ut etiam sit amet nisl purus.
            Cursus sit amet dictum sit amet. Tristique senectus et netus et malesuada fames ac
            turpis egestas. Et tortor consequat id porta nibh venenatis cras sed. Diam maecenas
            ultricies mi eget mauris. Eget egestas purus viverra accumsan in nisl nisi. Suscipit
            adipiscing bibendum est ultricies integer. Mattis aliquam faucibus purus in massa tempor
            nec.
          </p>
          <ScrollTop :threshold="100" icon="pi pi-arrow-up" target="parent"></ScrollTop>
        </ScrollPanel>
      </div>
    </div>
    <div class="col-12 lg:col-6">
      <div class="card">
        <h4>Tag</h4>
        <h5>Tags</h5>
        <Tag class="mr-2" value="Primary"></Tag>
        <Tag class="mr-2" severity="success" value="Success"></Tag>
        <Tag class="mr-2" severity="info" value="Info"></Tag>
        <Tag class="mr-2" severity="warning" value="Warning"></Tag>
        <Tag severity="danger" value="Danger"></Tag>

        <h5>Pills</h5>
        <Tag :rounded="true" class="mr-2" value="Primary"></Tag>
        <Tag :rounded="true" class="mr-2" severity="success" value="Success"></Tag>
        <Tag :rounded="true" class="mr-2" severity="info" value="Info"></Tag>
        <Tag :rounded="true" class="mr-2" severity="warning" value="Warning"></Tag>
        <Tag :rounded="true" severity="danger" value="Danger"></Tag>

        <h5>Icons</h5>
        <Tag class="mr-2" icon="pi pi-user" value="Primary"></Tag>
        <Tag class="mr-2" icon="pi pi-check" severity="success" value="Success"></Tag>
        <Tag class="mr-2" icon="pi pi-info-circle" severity="info" value="Info"></Tag>
        <Tag class="mr-2" con="pi pi-exclamation-triangle" severity="warning" value="Warning"></Tag>
        <Tag icon="pi pi-times" severity="danger" value="Danger"></Tag>
      </div>

      <div class="card">
        <h4>Chip</h4>
        <h5>Basic</h5>
        <div class="flex align-items-center flex-column sm:flex-row">
          <Chip class="mr-2 mb-2" label="Action"></Chip>
          <Chip class="mr-2 mb-2" label="Comedy"></Chip>
          <Chip class="mr-2 mb-2" label="Mystery"></Chip>
          <Chip :removable="true" class="mb-2" label="Thriller"></Chip>
        </div>

        <h5>Icon</h5>
        <div class="flex align-items-center flex-column sm:flex-row">
          <Chip class="mr-2 mb-2" icon="pi pi-apple" label="Apple"></Chip>
          <Chip class="mr-2 mb-2" icon="pi pi-facebook" label="Facebook"></Chip>
          <Chip class="mr-2 mb-2" icon="pi pi-google" label="Google"></Chip>
          <Chip :removable="true" class="mb-2" icon="pi pi-microsoft" label="Microsoft"></Chip>
        </div>

        <h5>Image</h5>
        <div class="flex align-items-center flex-column sm:flex-row">
          <Chip
            :image="contextPath + 'demo/images/avatar/amyelsner.png'"
            class="mr-2 mb-2"
            label="Amy Elsner"
          ></Chip>
          <Chip
            :image="contextPath + 'demo/images/avatar/asiyajavayant.png'"
            class="mr-2 mb-2"
            label="Asiya Javayant"
          ></Chip>
          <Chip
            :image="contextPath + 'demo/images/avatar/onyamalimba.png'"
            class="mr-2 mb-2"
            label="Onyama Limba"
          ></Chip>
        </div>
      </div>

      <div class="card">
        <h4>Skeleton</h4>
        <div class="border-round border-1 surface-border p-4">
          <div class="flex mb-3">
            <Skeleton class="mr-2" shape="circle" size="4rem"></Skeleton>
            <div>
              <Skeleton class="mb-2" width="10rem"></Skeleton>
              <Skeleton class="mb-2" width="5rem"></Skeleton>
              <Skeleton height=".5rem"></Skeleton>
            </div>
          </div>
          <Skeleton height="150px" width="100%"></Skeleton>
          <div class="flex justify-content-between mt-3">
            <Skeleton height="2rem" width="4rem"></Skeleton>
            <Skeleton height="2rem" width="4rem"></Skeleton>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
