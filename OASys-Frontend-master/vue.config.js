module.exports = {

    outputDir: "dist",
    filenameHashing: false,
    productionSourceMap: false,
    devServer: {
        proxy: "http://localhost:8080",
        port:80
    },
    configureWebpack: {performance: {hints: false}}
};