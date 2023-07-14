const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  transpileDependencies: true,


})

module.exports = {
  chainWebpack: config => {
    config.plugins.delete('prefetch'); //prefetch 삭제
  },

  
  outputDir: "../src/main/resources/static",
  devServer:{
    proxy: {
      '/':{
        target: 'http://localhost:8080',
        changeOrigin: true,
      }
    }
  }
}