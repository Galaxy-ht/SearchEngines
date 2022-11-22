const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  transpileDependencies: true
})


module.exports = {
  devServer: {
    proxy: {
      '/api': {
        target: "http://127.0.0.1:9090",
        changeOrigin: true,
        ws: true,
        pathRewrite:{
          '^/api': '',
        }
      }
    }
  }
};