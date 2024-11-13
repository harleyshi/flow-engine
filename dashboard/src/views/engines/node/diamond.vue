<template>
  <div class="diamond-node">
    <div class="diamond-label">{{ label }}</div>

    <Handle
      type="target"
      position="top"
      :id="`top-${id}`"
      :style="handleStyleTop"
      :isConnectable="true"
    />
    <Handle
      type="source"
      position="bottom"
      :id="`bottom-${id}`"
      :style="handleStyleBottom"
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
  
  const handleStyleTop = {
    width: "5px",
    height: "5px",
    borderRadius: "50%",
    backgroundColor: "black",
    transform: "translate(-200%, -50%)",  // 调整到尖端位置
  };

  const handleStyleBottom = {
    width: "5px",
    height: "5px",
    borderRadius: "50%",
    backgroundColor: "black",
    transform: "translate(100%, 50%)",  // 调整到尖端位置
};
  
</script>

<style scoped>
.diamond-node {
  width: 20px;
  height: 20px;
  background-color: #ff7043;
  color: #333;
  text-align: center;
  line-height: 20px;
  transform: rotate(45deg);
  border-radius: 2px;
  box-shadow: 0 2px 8px rgba(99, 94, 94, 0.1);
 
}

.diamond-label {
  font-size: 8px;
  transform: rotate(-45deg);
}
</style>