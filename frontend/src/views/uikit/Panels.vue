<script setup>
import { ref } from 'vue'

const toolbarItems = ref([
  {
    label: 'Save',
    icon: 'pi pi-check'
  },
  {
    label: 'Update',
    icon: 'pi pi-upload'
  },
  {
    label: 'Delete',
    icon: 'pi pi-trash'
  },
  {
    label: 'Home Page',
    icon: 'pi pi-home'
  }
])
const cardMenu = ref([
  { label: 'Save', icon: 'pi pi-fw pi-check' },
  { label: 'Update', icon: 'pi pi-fw pi-refresh' },
  { label: 'Delete', icon: 'pi pi-fw pi-trash' }
])
const menuRef = ref(null)

const toggle = () => {
  menuRef.value.toggle(event)
}
</script>

<template>
  <div class="grid">
    <div class="col-12">
      <div class="card">
        <h5>Toolbar</h5>
        <Toolbar>
          <template v-slot:start>
            <Button class="mr-2" icon="pi pi-plus" label="New" />
            <Button class="p-button-secondary mr-2" icon="pi pi-folder-open" label="Open" />

            <i class="pi pi-bars p-toolbar-separator mr-2"></i>

            <Button class="p-button-success mr-2" icon="pi pi-check" />
            <Button class="p-button-warning mr-2" icon="pi pi-trash" />
            <Button class="p-button-danger" icon="pi pi-print" />
          </template>
          <template v-slot:end>
            <SplitButton :model="toolbarItems" label="Options"></SplitButton>
          </template>
        </Toolbar>
      </div>
    </div>
    <div class="col-12 md:col-6">
      <div class="card">
        <h5>AccordionPanel</h5>
        <Accordion :activeIndex="0">
          <AccordionTab header="Header I">
            <p class="line-height-3 m-0">
              Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor
              incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud
              exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure
              dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur.
              Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt
              mollit anim id est laborum.
            </p>
          </AccordionTab>
          <AccordionTab header="Header II">
            <p class="line-height-3 m-0">
              Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque
              laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi
              architecto beatae vitae dicta sunt explicabo. Nemo enim ipsam voluptatem quia voluptas
              sit aspernatur aut odit aut fugit, sed quia consequuntur magni dolores eos qui ratione
              voluptatem sequi nesciunt. Consectetur, adipisci velit, sed quia non numquam eius
              modi.
            </p>
          </AccordionTab>
          <AccordionTab header="Header III">
            <p class="line-height-3 m-0">
              At vero eos et accusamus et iusto odio dignissimos ducimus qui blanditiis praesentium
              voluptatum deleniti atque corrupti quos dolores et quas molestias excepturi sint
              occaecati cupiditate non provident, similique sunt in culpa qui officia deserunt
              mollitia animi, id est laborum et dolorum fuga. Et harum quidem rerum facilis est et
              expedita distinctio. Nam libero tempore, cum soluta nobis est eligendi optio cumque
              nihil impedit quo minus.
            </p>
          </AccordionTab>
        </Accordion>
      </div>
      <div class="card">
        <h5>TabView</h5>
        <TabView>
          <TabPanel header="Header I">
            <p class="line-height-3 m-0">
              Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor
              incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud
              exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure
              dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur.
              Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt
              mollit anim id est laborum.
            </p></TabPanel
          >
          <TabPanel header="Header II">
            <p class="line-height-3 m-0">
              Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque
              laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi
              architecto beatae vitae dicta sunt explicabo. Nemo enim ipsam voluptatem quia voluptas
              sit aspernatur aut odit aut fugit, sed quia consequuntur magni dolores eos qui ratione
              voluptatem sequi nesciunt. Consectetur, adipisci velit, sed quia non numquam eius
              modi.
            </p>
          </TabPanel>
          <TabPanel header="Header III">
            <p class="line-height-3 m-0">
              At vero eos et accusamus et iusto odio dignissimos ducimus qui blanditiis praesentium
              voluptatum deleniti atque corrupti quos dolores et quas molestias excepturi sint
              occaecati cupiditate non provident, similique sunt in culpa qui officia deserunt
              mollitia animi, id est laborum et dolorum fuga. Et harum quidem rerum facilis est et
              expedita distinctio. Nam libero tempore, cum soluta nobis est eligendi optio cumque
              nihil impedit quo minus.
            </p>
          </TabPanel>
        </TabView>
      </div>
    </div>
    <div class="col-12 md:col-6">
      <div class="card">
        <h5>Panel</h5>
        <Panel :toggleable="true" header="Header">
          <p class="line-height-3 m-0">
            Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor
            incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud
            exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure
            dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur.
            Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt
            mollit anim id est laborum.
          </p>
        </Panel>
      </div>
      <div class="card">
        <h5>Fieldset</h5>
        <Fieldset :toggleable="true" legend="Legend">
          <p class="line-height-3 m-0">
            Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor
            incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud
            exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure
            dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur.
            Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt
            mollit anim id est laborum.
          </p>
        </Fieldset>
      </div>

      <Card>
        <template v-slot:title>
          <div class="flex align-items-center justify-content-between mb-0">
            <h5>Card</h5>
            <Button class="p-button-text" icon="pi pi-plus" @click="toggle" />
          </div>
          <Menu id="config_menu" ref="menuRef" :model="cardMenu" :popup="true" />
        </template>

        <template v-slot:content>
          <p class="line-height-3 m-0">
            Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor
            incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud
            exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure
            dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur.
            Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt
            mollit anim id est laborum.
          </p>
        </template>
      </Card>
    </div>

    <div class="col-12">
      <div class="card">
        <h5>Divider</h5>
        <div class="grid">
          <div class="col-5 flex align-items-center justify-content-center">
            <div class="p-fluid">
              <div class="field">
                <label for="username">Username</label>
                <InputText id="username" type="text" />
              </div>
              <div class="field">
                <label for="password">Password</label>
                <InputText id="password" type="password" />
              </div>
              <Button class="mt-2" label="Login"></Button>
            </div>
          </div>

          <div class="col-2">
            <Divider layout="vertical">
              <b>OR</b>
            </Divider>
          </div>
          <div class="col-5 align-items-center justify-content-center">
            <p class="line-height-3 m-0">
              Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque
              laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi
              architecto beatae vitae dicta sunt explicabo. Nemo enim ipsam voluptatem quia voluptas
              sit aspernatur aut odit aut fugit, sed quia consequuntur magni dolores eos qui ratione
              voluptatem sequi nesciunt. Consectetur, adipisci velit, sed quia non numquam eius
              modi.
            </p>

            <Divider align="center" layout="horizontal">
              <span class="p-tag">Badge</span>
            </Divider>

            <p class="line-height-3 m-0">
              At vero eos et accusamus et iusto odio dignissimos ducimus qui blanditiis praesentium
              voluptatum deleniti atque corrupti quos dolores et quas molestias excepturi sint
              occaecati cupiditate non provident, similique sunt in culpa qui officia deserunt
              mollitia animi, id est laborum et dolorum fuga. Et harum quidem rerum facilis est et
              expedita distinctio. Nam libero tempore, cum soluta nobis est eligendi optio cumque
              nihil impedit quo minus.
            </p>

            <Divider align="right">
              <Button class="p-button-outlined" icon="pi pi-search" label="Button"></Button>
            </Divider>

            <p class="line-height-3 m-0">
              Temporibus autem quibusdam et aut officiis debitis aut rerum necessitatibus saepe
              eveniet ut et voluptates repudiandae sint et molestiae non recusandae. Itaque earum
              rerum hic tenetur a sapiente delectus, ut aut reiciendis voluptatibus maiores alias
              consequatur aut perferendis doloribus asperiores repellat. Donec vel volutpat ipsum.
              Integer nunc magna, posuere ut tincidunt eget, egestas vitae sapien. Morbi dapibus
              luctus odio.
            </p>
          </div>
        </div>
      </div>
    </div>

    <div class="col-12">
      <div class="card">
        <h5>Splitter</h5>

        <Splitter class="mb-5" style="height: 300px">
          <SplitterPanel :minSize="10" :size="30" style="overflow: scroll">
            <div className="h-full flex align-items-center justify-content-center">Panel 1</div>
          </SplitterPanel>
          <SplitterPanel :size="70" style="overflow: scroll">
            <Splitter layout="vertical">
              <SplitterPanel :size="15">
                <div className="h-full flex align-items-center justify-content-center">Panel 2</div>
              </SplitterPanel>
              <SplitterPanel :size="50">
                <div className="h-full flex align-items-center justify-content-center">Panel 3</div>
              </SplitterPanel>
            </Splitter>
          </SplitterPanel>
        </Splitter>
      </div>
    </div>
  </div>
</template>
