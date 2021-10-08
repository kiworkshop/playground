const {createProxyMiddleware} = require('http-proxy-middleware');

const apis = [
  "/api",
];

module.exports = function (app) {
  app.use(
    createProxyMiddleware(apis, {
      target: 'http://localhost:8080',
      compress: true,
      historyApiFallback: true,
    })
  );

};
