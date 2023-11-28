import axios from "axios";

const BACKEND_URL = process.env.REACT_APP_NODE_API;

const backendCall = axios.create({
  baseURL: BACKEND_URL,
});


backendCall.interceptors.response.use(
  (response) => {
    return response;
  },
  (error) => {
    if (error.response && error.response.status === 403) {
      // window.localStorage.removeItem('token');
      // window.localStorage.removeItem('role');
      // window.location = '/login';
    }
    return Promise.reject(error);
  }
);

export default backendCall;