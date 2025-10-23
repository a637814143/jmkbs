import axios from 'axios';

const client = axios.create({
  baseURL: '/api',
  timeout: 8000
});

client.interceptors.response.use(
  (response) => response,
  (error) => {
    if (error.response?.data?.message) {
      return Promise.reject(new Error(error.response.data.message));
    }
    return Promise.reject(error);
  }
);

export function registerUser(payload) {
  return client.post('/auth/register', payload).then((res) => res.data);
}

export function loginUser(payload) {
  return client.post('/auth/login', payload).then((res) => res.data);
}
