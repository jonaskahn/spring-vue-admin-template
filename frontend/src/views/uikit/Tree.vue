<script setup>
import NodeService from '@/service/NodeService'
import { onMounted, ref } from 'vue'

const treeValue = ref(null)
const selectedTreeValue = ref(null)
const treeTableValue = ref(null)
const selectedTreeTableValue = ref(null)
const nodeService = new NodeService()

onMounted(() => {
  nodeService.getTreeNodes().then((data) => (treeValue.value = data))
  nodeService.getTreeTableNodes().then((data) => (treeTableValue.value = data))
})
</script>

<template>
  <div class="grid">
    <div class="col-12">
      <div class="card">
        <h5>Tree</h5>
        <Tree
          v-model:selectionKeys="selectedTreeValue"
          :value="treeValue"
          selectionMode="checkbox"
        ></Tree>
      </div>
    </div>
    <div class="col-12">
      <div class="card">
        <h5>TreeTable</h5>
        <TreeTable
          v-model:selectionKeys="selectedTreeTableValue"
          :value="treeTableValue"
          selectionMode="checkbox"
        >
          <template #header> FileSystem</template>
          <Column :expander="true" field="name" header="Name"></Column>
          <Column field="size" header="Size"></Column>
          <Column field="type" header="Type"></Column>
        </TreeTable>
      </div>
    </div>
  </div>
</template>
