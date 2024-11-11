import { defineConfig } from "vite";

// https://vitejs.dev/config/
export default defineConfig({
  envPrefix: "VITE_", //环境变量默认前缀
  server: {
    headers: {
      // 如果需要用到ffmpeg合并视频，需要将COEP和COOP打开，来确保ShareArrayBuffer能够正常使用
      "Cross-Origin-Embedder-Policy": "require-corp",
      "Cross-Origin-Opener-Policy": "same-origin",
    },
    proxy: {
      "/sys-api": {
        
      },
    },
  },
});
