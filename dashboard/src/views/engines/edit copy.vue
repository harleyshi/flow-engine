<template>
  <div class="container">
    <el-form :model="form" label-width="80px" v-loading="loading">
      <!-- 编码输入框，仅在编辑时显示 -->
      <el-form-item label="编码" v-if="isEdit">
        <el-input class="short-input" v-model="form.id" :disabled="true"></el-input>
      </el-form-item>

      <el-form-item label="名称">
        <el-input class="short-input" v-model="form.name" placeholder="请输入名称"></el-input>
      </el-form-item>

      <el-form-item label="描述">
        <el-input class="short-input" v-model="form.description" placeholder="请输入描述"></el-input>
      </el-form-item>

      <el-form-item label="状态">
        <el-radio-group v-model="form.status">
          <el-radio label="published">发布</el-radio>
          <el-radio label="unavailable">下架</el-radio>
        </el-radio-group>
      </el-form-item>

      <!-- 使用 monaco-editor -->
      <el-form-item label="内容">
        <div ref="editorContainer" class="editor-container"></div>
      </el-form-item>

      <el-form-item>
        <el-button type="primary" @click="saveData">{{ isEdit ? '保存' : '新增' }}</el-button>
        <el-button @click="goBack">返回</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script lang="ts" setup>
import { ref, onMounted, onBeforeUnmount } from 'vue';
import * as monaco from 'monaco-editor';
import { useRoute, useRouter } from 'vue-router';
import { engineDetail, engineEdit } from "../../api/module/api";

const route = useRoute();
const router = useRouter();
const loading = ref(false);
const editorInstance = ref<monaco.editor.IStandaloneCodeEditor | null>(null);
const editorContainer = ref<HTMLElement | null>(null);

// 初始化表单数据
const form = ref({
  id: null,
  name: "",
  description: "",
  content: "",
  status: "unavailable",
  updateTime: null
});

// 判断当前是编辑还是新增
const isEdit = ref(false);

// 加载引擎数据（用于编辑）
const loadEngineData = async (id: string) => {
  try {
    loading.value = true;
    const response = await engineDetail(id);
    const result = response.data;
    if (result.code === "0000" && result.data) {
      form.value = result.data;
      // 如果已经有编辑器实例，则更新内容
      if (editorInstance.value) {
        editorInstance.value.setValue(form.value.content || "");
      }
    }
  } catch (error) {
    console.error("加载引擎数据失败", error);
  } finally {
    loading.value = false;
  }
};

// 保存数据
const saveData = async () => {
  try {
    loading.value = true;
    // 从编辑器获取最新的内容
    form.value.content = editorInstance.value?.getValue() || "";
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
const goBack = () => {
  router.push("/engines/list");
};

// 组件挂载时加载数据并初始化编辑器
onMounted(() => {
  const engineId = route.query.id as string;
  if (engineId) {
    // 编辑
    isEdit.value = true;
    loadEngineData(engineId);
  } else {
    // 新增
    isEdit.value = false;
    form.value = {
      id: null,
      name: "",
      description: "",
      content: "",
      status: "unavailable",
      updateTime: null
    };
  }

  // 初始化 Monaco Editor
  editorInstance.value = monaco.editor.create(editorContainer.value!, {
    value: form.value.content || '',
    language: 'xml',
    theme: 'vs-dark',
    automaticLayout: true
  });
});

// 组件销毁时销毁编辑器实例
onBeforeUnmount(() => {
  if (editorInstance.value) {
    editorInstance.value.dispose();
  }
});
</script>

<style scoped>
.container {
  padding: 20px;
}

.el-form-item {
  margin-bottom: 20px;
}

.short-input {
  width: 75%;
}

.editor-container {
  width: 100%;
  height: 400px;
  border: 1px solid #dcdfe6;
  border-radius: 4px;
}
</style>
