<script setup>
import { Panel, useVueFlow } from '@vue-flow/core'
import { ElMessage } from "element-plus";
import Icon from '../../components/Icon.vue'

const emit = defineEmits();

// 定义触发事件的函数
const triggerSaveEngine = () => {
  // 触发 'save-engine' 事件
  emit('save-engine');
}

const triggerLogToObject = () => {
  // 触发 'log-to-object' 事件
  emit('log-to-object');
}

const triggerGoBack = () => {
  // 触发 'go-back' 事件
  emit('go-back');
}
</script>

<template>
  <Panel position="top-right">
    <div class="buttons">
      <!-- 保存按钮 -->
      <button title="保存" @click="triggerSaveEngine">
        <Icon name="save" />
      </button>

      <!-- 导出按钮 -->
      <button title="导出" @click="triggerLogToObject">
        <Icon name="download" />
      </button>

      <!-- 返回按钮 -->
      <button title="返回" @click="triggerGoBack">
        <Icon name="return" />
      </button>
    </div>
  </Panel>
</template>


<style scoped>
.vue-flow__panel {
    background-color:#2d3748;
    padding:10px;
    border-radius:8px;
    box-shadow:0 0 10px #00000080;
}

.vue-flow__panel .buttons {
    display:flex;
    gap:8px
}

.vue-flow__panel button {
    border:none;
    cursor:pointer;
    background-color:#4a5568;
    border-radius:8px;
    color:#fff;
    box-shadow:0 0 10px #0000004d;
    width:40px;
    height:40px;
    font-size:16px;
    display:flex;
    align-items:center;
    justify-content:center
}

.vue-flow__panel button:hover {
    background-color:#2563eb;
    transition:background-color .2s
}
</style>