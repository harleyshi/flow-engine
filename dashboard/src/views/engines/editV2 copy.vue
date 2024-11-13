<template>
  <div class="container">
    <el-row class="mb-4">
      <el-button type="primary" @click="goBack">保存</el-button>
      <el-button type="primary" @click="resetTransform">重置</el-button>
      <el-button type="primary" @click="updatePos">修改属性</el-button>
      <el-button type="primary" @click="toggleclass">修改样式</el-button>
      <el-button type="primary" @click="logToObject">查看属性</el-button>
      <el-button type="primary" @click="goBack">返回</el-button>
    </el-row>

    <VueFlow v-model="elements" fit-view-on-init class="flow-container" :node-types="nodeTypes">
      <Background/>
      <Controls/>
    </VueFlow>
  </div>
</template>

<script lang="ts" setup>
import "@vue-flow/core/dist/style.css";
import "@vue-flow/core/dist/theme-default.css";
import "@/assets/bpmn.css";

import { Background, Controls } from "@vue-flow/additional-components";
import { VueFlow, useVueFlow } from "@vue-flow/core";
import { ref, watch } from "vue";
import { ElMessage } from "element-plus";
import { useRoute, useRouter } from "vue-router";
import diamond from "./node/diamond.vue";  
import circle from "./node/circle.vue";  
import business from "./node/business.vue";  

const router = useRouter();

const data = [
  { id: "1", type: "business", label: "Node 1", position: { x: 250, y: 5 } },
  { id: "2", type: "diamond", label: "条件", position: { x: 250, y: 150 } },
  { id: "3", type: "business", label: "Node 3", position: { x: 400, y: 100 } },
  { id: "4", type: "circle", label: "Node 4", position: { x: 400, y: 200 } },
  { id: "5", type: "business", label: "Node 5", position: { x: 120, y: 260 } },
  { id: "e1-2", source: "1", target: "2", animated: true },
  { id: "e1-3", source: "1", target: "3" },
  { id: "e2-5", source: "2", target: "5" },
  { id: "e3-4", source: "3", target: "4" },
];

const elements = ref(data);

// 定义节点类型
const nodeTypes = ref({
  diamond: diamond,
  circle: circle,
  business: business
});

const isHidden = ref(false);

const { onPaneReady, onNodeDragStop, onConnect, addEdges, setTransform, toObject, nodes, edges } = useVueFlow();

watch(isHidden, () => {
  nodes.value.forEach((n) => (n.hidden = isHidden.value));
  edges.value.forEach((e) => (e.hidden = isHidden.value));
});

onPaneReady(({ fitView }) => {
  fitView();
});

onNodeDragStop((e) => console.log("drag stop", e));

onConnect((params) => addEdges([params]));

const updatePos = () => {
  nodes.value.forEach((el) => {
    el.position = {
      x: Math.random() * 400,
      y: Math.random() * 400,
    };
  });
};

const logToObject = () => {
  ElMessage.info(JSON.stringify(toObject()));
};

const resetTransform = () => {
  elements.value = data;
  setTransform({ x: 0, y: 0, zoom: 1 });
};

const toggleclass = () => nodes.value.forEach((el) => (el.class = el.class === "node-light" ? "node-dark" : "node-light"));

// 返回到列表页面
const goBack = () => {
  router.push("/engines/list");
};
</script>

<style lang="less" scoped>
.flow-container {
  margin: 10px;
  height: 700px;

  :deep(.node-light) {
    background: none;
  }

  :deep(.node-dark) {
    background: #eeeeee;
  }
}
</style>
