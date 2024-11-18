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

    <div class="flow-container-wrapper">
      
      <div class="node-list-panel">
        <h3>特殊节点</h3>
        <div 
          v-for="(node, index) in nodeList" 
          :key="index" 
          class="node-item" 
          draggable="true"
          @dragstart="handleDragStart($event, node)">
          {{ node.label }}
        </div>
      </div>

      <VueFlow
        v-model="elements"
        fit-view-on-init
        class="flow-container"
        :node-types="nodeTypes"
        @node-click="handleNodeClick"
        @drop="handleNodeDrop"
        @dragover="handleDragOver"
      >
        <Background />
        <Controls />
      </VueFlow>

      <!-- 右侧面板 -->
      <el-aside width="300px" class="info-panel" v-if="selectedNode">
        <div class="panel-header">
          <h3>节点信息</h3>
        </div>
        <el-form label-width="100px">
          <el-form-item label="节点名称">
            <span>{{ selectedNode.label }}</span>
          </el-form-item>
          <el-form-item label="节点类型">
            <span>{{ selectedNode.type }}</span>
          </el-form-item>
          <el-form-item label="超时时间">
            <el-input v-model="selectedNode.customProperty"  placeholder=""/>
          </el-form-item>
          <el-form-item label="是否忽略异常">
            <el-input v-model="selectedNode.customProperty"  placeholder=""/>
          </el-form-item>
          <el-form-item label="是否异步">
            <el-input v-model="selectedNode.customProperty"  placeholder=""/>
          </el-form-item>
        </el-form>
      </el-aside>
    </div>
  </div>
</template>

<script lang="ts" setup>
import "@vue-flow/core/dist/style.css";
import "@vue-flow/core/dist/theme-default.css";
import "@/assets/bpmn.css";
import { Background, Controls } from "@vue-flow/additional-components";

import { VueFlow, useVueFlow } from "@vue-flow/core";
import { ref, watch, markRaw } from "vue";
import { ElMessage } from "element-plus";
import { useRoute, useRouter } from "vue-router";
import condition from "./node/condition.vue";
import business from "./node/business.vue";

const router = useRouter();
const { onPaneReady, onNodeDragStop, onConnect, addEdges, setTransform, toObject, nodes, edges } = useVueFlow();

const data = [
  { id: "1", type: "business", label: "Node 11111111111111111", position: { x: 250, y: 70 }, customProperty: "属性1" },
  { id: "2", type: "condition", label: "判断", position: { x: 150, y: 190 }, customProperty: "属性2" },
  { id: "3", type: "business", label: "Node 3", position: { x: 360, y: 160 }, customProperty: "属性3" },
  { id: "4", type: "business", label: "Node 4", position: { x: 380, y: 240 }, customProperty: "属性4" },
  { id: "5", type: "business", label: "Node 5", position: { x: 150, y: 260 }, customProperty: "属性5" },
  { id: "6", type: "business", label: "Node 6", position: { x: 250, y: 350 }, customProperty: "属性6" },
  { id: "e1-2", source: "1", target: "2" },
  { id: "e1-3", source: "1", target: "3" },
  { id: "e2-5", source: "2", target: "5" },
  { id: "e3-4", source: "3", target: "4" },
  { id: "e4-6", source: "4", target: "6" },
  { id: "e5-6", source: "5", target: "6" },
];

const elements = ref(data);

// 定义节点类型
const nodeTypes = ref({
  condition: markRaw(condition),
  business: markRaw(business)
});

// 选中的节点信息
const selectedNode = ref(null);

// 可拖拽的节点列表
const nodeList = ref([
  { label: "条件节点", type: "condition"},
]);

// 监听节点点击事件
const handleNodeClick = (event: any) => {
  const node = event.node;
  selectedNode.value = node; // 设置选中的节点
};

onPaneReady(({ fitView }) => {
  fitView();
});

onConnect((params) => addEdges([params]));

const updatePos = () => {
  nodes.value.forEach((el) => {
    el.position = {
      x: Math.random() * 400,
      y: Math.random() * 400
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






// 拖拽开始事件，设置拖拽节点类型
const handleDragStart = (event: DragEvent, node: any) => {
  event.dataTransfer.setData("nodeType", node.type); // 保存节点类型
};

// 处理拖拽到画布区域的事件
const handleDragOver = (event: DragEvent) => {
  event.preventDefault(); // 防止默认行为，允许放置
};

// 拖拽放置事件，处理节点加入画布
const handleNodeDrop = (event: DragEvent) => {
  const nodeType = event.dataTransfer.getData("nodeType"); // 获取拖拽的节点类型
  const newNode = {
    id: `${Math.random()}`,  // 为新节点生成唯一ID
    type: nodeType,  // 设置类型
    position: { x: event.clientX - 100, y: event.clientY - 100 },  // 将位置设置为放置位置
    label: nodeType === "business" ? "业务节点" : nodeType  // 设置默认标签
  };
  elements.value.push(newNode); // 将新节点添加到元素列表中
  ElMessage.info(`新增节点: ${newNode.label}`);
};


</script>

<style lang="less" scoped>
.flow-container-wrapper {
  display: flex;
  justify-content: space-between;
  margin-top: 20px;
}

.flow-container {
  margin: 10px;
  height: 700px;
  flex-grow: 1;
}

.node-list-panel {
  width: 250px;
  padding: 20px;
  background-color: #f9f9f9;
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
  height: 600px;
  overflow-y: auto;
  margin-right: 20px;
}

.node-item {
  padding: 10px;
  margin-bottom: 10px;
  background-color: #fff;
  border: 1px solid #ddd;
  cursor: pointer;
  border-radius: 4px;
  text-align: center;
  transition: background-color 0.3s;
}

.node-item:hover {
  background-color: #eaeaea;
}

.info-panel {
  position: fixed;
  top: 220px;
  right: 30px;
  width: 300px;
  height: 400px;
  background-color: #ffffff;
  padding: 10px;
  box-shadow: 0 4px 20px rgba(163, 101, 101, 0.1);
  border-radius: 4px;
  z-index: 1000;
}

.info-panel:hover {
  transform: translateY(-5px); /* 悬浮时轻微上移 */
}

.panel-header {
  font-size: 16px;
  font-weight: bold;
  margin-bottom: 15px;
  text-align: center;
}

.el-form-item {
  margin-bottom: 10px;
}

.el-form-item label {
  font-weight: bold;
}

.info-panel pre {
  background-color: #f7f7f7;
  border-radius: 4px;
  overflow-x: auto;
  white-space: pre-wrap;
  word-wrap: break-word;
}
</style>
