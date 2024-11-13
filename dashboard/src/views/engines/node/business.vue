<template>
    <div class="business-process-node">
      <div class="node-label">{{ label }}</div>
      
      <Handle
        type="target"
        position="top"
        :id="`top-${id}`"
        :style="handleStyle"
        :isConnectable="true"
      />
      <Handle
        type="source"
        position="bottom"
        :id="`bottom-${id}`"
        :style="handleStyle"
        :isConnectable="true"
      />
    </div>
</template>
  
<script lang="ts" setup>
  import { Handle } from "@vue-flow/core";
  
  defineProps({
    label: String,
    id: String,
  });
  
  const handleStyle = {
    width: "5px",
    height: "5px",
    borderRadius: "50%",
    backgroundColor: "black",
  };
  
</script>
  
<style scoped>
.business-process-node {
    position: relative;
    font-size: 10px;
    width: 70px;
    height: 24px;
    background-color: #ffca28;
    color: #333;
    text-align: center;
    line-height: 24px;
    border-radius: 4px;
    box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
}
  
.node-label {
    font-size: 10px;
}
  
</style>
  