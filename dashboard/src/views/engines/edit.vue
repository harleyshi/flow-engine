<template>
  <div class="container">
    <el-form :model="form" label-width="80px" v-loading="loading">
      <el-row :gutter="20">
        <el-col :span="6">
          <el-form-item label="名称：">
            <el-input class="short-input" v-model="form.name" placeholder="请输入名称"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="描述：">
            <el-input class="long-input" v-model="form.description" placeholder="请输入描述"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="6" v-if="isEdit">
          <el-form-item label="状态：">
            <el-radio-group v-model="form.status">
              <el-radio label="published">发布</el-radio>
              <el-radio label="unavailable">下架</el-radio>
            </el-radio-group>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>

    <div class="flow-container-wrapper">
      <div class="node-list-panel">
        <h3>特殊节点</h3>
        <div 
          v-for="(node, index) in nodeList" 
          :key="index" 
          class="node-item" 
          draggable="true"
          @dragstart="handleDragStart($event, node, '1')">
          {{ node.label }}
        </div>

        <h3>算子列表</h3>
        <div 
          v-for="(node, index) in operatorsList" 
          :key="index" 
          class="node-item" 
          draggable="true" 
          @dragstart="handleDragStart($event, node, '2')"
        >
          {{node.label}}
        </div>

        <!-- 分页控件 -->
        <el-pagination
          v-if="totalOperators > 0"
          background
          layout="prev, next"
          :total="totalOperators"
          :page-size="pageSize"
          :current-page="pageNo"
          @current-change="handlePageChange"
        />
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
        <ToolsControls 
          @save-engine="handleSaveEngine"
          @log-to-object="handleLogToObject"
          @go-back="handleGoBack"
        />
        <Background />
        <Controls />
      </VueFlow>

      <!-- 节点信息面板 -->
      <el-aside width="300px" class="info-panel" v-if="selectedNode">
        <div class="panel-header">
          <h3>节点信息</h3>
        </div>
        <el-form label-width="100px">
          <el-form-item label="节点id">
            <span>{{ selectedNode.id }}</span>
          </el-form-item>
          <el-form-item label="节点名称">
            <span>{{ selectedNode.label }}</span>
          </el-form-item>
          <el-form-item label="节点类型">
            <span>{{ selectedNode.type }}</span>
          </el-form-item>
          <el-form-item label="超时时间">
            <el-input v-model="selectedNode.config.timeout" placeholder=""/>
          </el-form-item>
          <el-form-item label="是否忽略异常">
            <el-input v-model="selectedNode.config.ignoreException" placeholder=""/>
          </el-form-item>
          <el-form-item label="是否异步">
            <el-input v-model="selectedNode.config.async" placeholder=""/>
          </el-form-item>
        </el-form>
        
        <!-- 底部按钮区域 -->
        <el-row class="node-btn-wrapper">
          <el-button type="primary" @click="closePanel">关闭</el-button>
          <el-button type="primary" @click="updateNodeData">确认</el-button>
        </el-row>
      </el-aside>

    </div>
  </div>
</template>



<script lang="ts" setup>
import "@vue-flow/core/dist/style.css";
import "@vue-flow/core/dist/theme-default.css";
import "@/assets/bpmn.css";
import { Background, Controls } from "@vue-flow/additional-components";
import { VueFlow, useVueFlow, MarkerType } from "@vue-flow/core";
import {engineDetail, operators, engineEdit } from '../../api/module/api';
import { ref, markRaw, onMounted} from "vue";
import { ElMessage } from "element-plus";
import { useRoute, useRouter } from "vue-router";
import ToolsControls from './tools.vue'
import Condition from "./node/condition.vue";
import Business from "./node/business.vue";

const route = useRoute();
const router = useRouter();
const { onPaneReady, onNodeDragStop, onConnect, addEdges, setTransform, project, toObject, nodes, edges } = useVueFlow();

const loading = ref(false);

// 判断当前是编辑还是新增
const isEdit = ref(false);
// 初始化表单数据
const form = ref({
  id: null,
  name: "",
  description: "",
  content: "",
  status: "unavailable",
  updateTime: null
});

const data = [];
const elements = ref(data);

// 定义节点类型
const nodeTypes = ref({
  condition: markRaw(Condition),
  business: markRaw(Business)
});

// 选中的节点信息
const selectedNode = ref(null);

// 可拖拽的节点列表
const nodeList = ref([
  { label: "条件节点", type: "condition"},
]);

// 算子列表和分页
const operatorsList = ref([]);
const searchName = ref('');
const pageNo = ref(1);
const pageSize = ref(10);
const totalOperators = ref(0);

// 监听节点点击事件
const handleNodeClick = (event: any) => {
  const node = event.node;
  selectedNode.value = node; // 设置选中的节点
};

onPaneReady(({ fitView }) => {
  fitView();
});

onConnect((params) => {
  const { source, target, sourceHandle, targetHandle } = params;

  // 检查是否是自连接
  if (source === target) {
    ElMessage.warning('不能连接到自身');
    return;
  }
  // 防止 top-to-top 连接
  if (sourceHandle && targetHandle && sourceHandle.includes("top") && targetHandle.includes("top")) {
    ElMessage.warning('不能连接相同位置类型的节点（top-to-top）');
    return;
  }
  // 防止 bottom-to-bottom 连接
  if (sourceHandle && targetHandle && sourceHandle.includes("bottom") && targetHandle.includes("bottom")) {
    ElMessage.warning('不能连接相同位置类型的节点（bottom-to-bottom）');
    return;
  }

  // 只有在连接符合规则时，才添加边
 // 添加边，并且指定边的类型
 addEdges([
    {
      ...params,
      type: 'button',
      markerEnd: MarkerType.ArrowClosed,
    }
  ]);
});


// 查询属性
const handleLogToObject = () => {
  ElMessage.info(JSON.stringify(toObject()));
};

// 获取编辑页面时的初始数据
const loadEngineData = async (id: string) => {
  try {
    loading.value = true;
    const response = await engineDetail(id);
    const result = response.data;
    if (result.code === "0000" && result.data) {
      form.value = result.data;
      const content = result.data.content ? JSON.parse(result.data.content) : { nodes: [], edges: [] };
      const { nodes = [], edges = [] } = content;
      elements.value = [...nodes, ...edges];
      
      if (nodes.length > 0) {
        const maxNodeId = Math.max(...nodes.map((node: { id: string }) => parseInt(node.id, 10)));
        nodeIdCounter = maxNodeId + 1; 
      } else {
        nodeIdCounter = 1;
      }
    }
  } catch (error) {
    console.error("加载引擎数据失败", error);
  } finally {
    loading.value = false;
  }
};

// 保存引擎
const handleSaveEngine = async () => {
  try {
    loading.value = true;
    const data = JSON.stringify(toObject());
    form.value.content = data;
    let response = await engineEdit(form.value);
    const result = response.data;
    if (result.code === "0000") {
      router.push({ path: "/engines/list", query: { refresh: "true" } });
    } else {
      console.error("保存失败", result.message);
    }
  } catch (error) {
    console.error("保存失败", error);
  } finally {
    loading.value = false;
  }
};

// 返回到列表页面
const handleGoBack = () => {
  router.push("/engines/list");
};

// 关闭面板
const closePanel = () => {
  selectedNode.value = null;
};

// 修改节点信息
const updateNodeData = () => {
  
};

// 定义一个自增ID变量
let nodeIdCounter = 1;

// 处理拖拽到画布区域的事件
const handleDragOver = (event: DragEvent) => {
  event.preventDefault(); // 防止默认行为，允许放置
};

// 拖拽开始事件，设置拖拽节点类型
const handleDragStart = (event: DragEvent, node: any, opsType: string) => {
  if (opsType === '2') {
    node.config = {
      timeout: 0,
      ignoreException: false,
      async: false
    };
  }
  event.dataTransfer.setData("nodeData", JSON.stringify(node)); 
};

// 拖拽放置事件，处理节点加入画布
const handleNodeDrop = (event: DragEvent) => {
  const nodeDataStr = event.dataTransfer.getData("nodeData"); 
  const nodeData = JSON.parse(nodeDataStr);
  // 获取鼠标在画布上的位置
  const { x: positionX, y: positionY } = getMousePositionOnCanvas(event, ".flow-container");
  const addNode = {
    id: `${nodeIdCounter++}`,
    type: nodeData.type,
    position: { x: positionX, y: positionY },
    label: nodeData.label,
    config: nodeData.config
  };
  elements.value.push(addNode);
  ElMessage.info(`新增节点: ${addNode.label}`);
};

// 获取鼠标在画布上的位置
const getMousePositionOnCanvas = (event: DragEvent, containerSelector: string) => {
  // 获取画布容器
  const flowContainer = document.querySelector(containerSelector);
  if (!flowContainer) {
    return { x: event.clientX - 100, y: event.clientY - 100 };
  }
  // 获取画布的边界
  const canvasRect = flowContainer.getBoundingClientRect();
  const mouseX = event.clientX - canvasRect.left;
  const mouseY = event.clientY - canvasRect.top;
  return project({ x: mouseX, y: mouseY });
};

// 获取算子数据
const fetchOperators = async () => {
  try {
    const response = await operators(searchName.value, pageNo.value, pageSize.value);
    operatorsList.value = response.data.data.records; // 更新算子列表
    totalOperators.value = response.data.data.total; // 更新总数
  } catch (error) {
    ElMessage.error('获取算子列表失败');
  }
};

// 处理分页变化
const handlePageChange = (newPage: number) => {
  pageNo.value = newPage;
  fetchOperators(); // 刷新数据
};


onMounted(() => {
  // 初始化获取算子列表
  fetchOperators();

  const engineId = route.query.id as string;
  // 编辑
  if (engineId) {
    isEdit.value = true;
    loadEngineData(engineId);
  }
 
});

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
  width: 260px;
  padding: 20px;
  background-color: #f9f9f9;
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
  height: 650px;
  overflow-y: auto;
  margin-right: 20px;
}

.node-item {
  width: 120px;
  height: 20px;
  line-height: 20px;
  padding: 6px;
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
  display: flex;    
  flex-direction: column;
  top: 180px;
  right: 30px;
  width: 300px;
  height: 450px;
  background-color: #f9f9f9;
  padding: 10px;
  box-shadow: 0 4px 20px rgba(163, 101, 101, 0.1);
  border-radius: 4px;
  z-index: 1000;
}

.node-btn-wrapper {
  display: flex; 
  justify-content: flex-end;
  margin-top: auto;
}

.node-btn-wrapper button {
  margin-right: 10px;
  margin-bottom: 10px;
}

.el-form-item {
  margin-bottom: 10px;
}

.panel-header {
  font-size: 16px;
  font-weight: bold;
  margin-bottom: 2px;
  text-align: center;
}
</style>
