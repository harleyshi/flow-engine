<template>
    <div class="node">
      <div class="node-label">request</div>
    
      <Handle
        type="source"
        position="bottom"
        :id="`bottom-${id}`"
        :style="handleStyle"
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
.node {
    font-size: 10px;
    width: 70px;
    height: 24px;
    border: 1px solid #94e1e5;
    background-color: rgb(175 232 255 / 40%);
    color: #333;
    text-align: center;
    line-height: 24px;
    border-radius: 4px;
    box-shadow: 0 2px 8px rgba(180, 28, 28, 0.1);
}
  
.node-label {
  font-size: 10px;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
  padding-left: 2px;
  padding-right: 2px;
}
  
</style>
  