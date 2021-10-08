import axios, {AxiosRequestConfig, AxiosResponse} from "axios";
import {getQueryParamString} from "./parameterUtils";

const DEFAULT_TIMEOUT = 600000;
const token = localStorage.getItem('token');
const bearerToken = token ? `Bearer ${token}` : ''

const instance = axios.create({
  headers: {
    'Content-Type': 'application/json',
    'Authorization': bearerToken
  },
  timeout: DEFAULT_TIMEOUT,
})

instance.interceptors.response.use(
  (response: AxiosResponse) => {
    return response;
  },
  (error) => {
    if (error.response.status === 401) {
      window.location.href = '/'
    }
    return Promise.reject(error);
  }
);

const request = (() => {

  const get = async (url: string, params?: object, config?: AxiosRequestConfig) => {
    const queryString = await getQueryParamString(params);

    return instance.get(url + queryString, config);
  };

  const post = async (url: string, postObject: object, config?: AxiosRequestConfig) => {
    const queryString = await getQueryParamString({});

    return instance.post(url + queryString, postObject, config);
  };

  const put = async (url: string, putObject: object, config?: AxiosRequestConfig) => {
    const queryString = await getQueryParamString({});

    return instance.put(url + queryString, putObject, config);
  };

  const deleteMethod = async (url: string, config?: AxiosRequestConfig) => {
    const queryString = await getQueryParamString({});

    return instance.delete(url + queryString, config);
  };

  return {
    get,
    post,
    put,
    deleteMethod
  };

})();

export {
  request
}
