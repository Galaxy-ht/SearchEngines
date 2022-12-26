const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  transpileDependencies: true
})

module.exports = {
  devServer: {
    proxy: {
      '/api': {
        target: "https://43.138.38.88",
        changeOrigin: true,
        ws: true,
        pathRewrite:{
          '^/api': '',
        }
      }
    }
  }
};