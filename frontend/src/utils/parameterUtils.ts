const queryString = require('query-string')

const getQueryParamString = (params?: object) =>
  (params === undefined)
    ? ''
    : '?' + queryString.stringify(params);

export {
  getQueryParamString
};
