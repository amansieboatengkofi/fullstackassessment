import axios from 'axios';

const API_BASE_URL = 'http://localhost:8080';
const ApiService = {
  async getTodos() {
    const response = await axios.get(`${API_BASE_URL}/todos`);
    return response;
  },

  async createTodo(todo) {
    const response = await axios.post(`${API_BASE_URL}/todos`, todo);
    return response;
  },

  async updateTodo(id, updates) {
    const response = await axios.put(`${API_BASE_URL}/todos/${id}`, updates);
    return response;
  },

  async deleteTodo(id) {
    const response = await axios.delete(`${API_BASE_URL}/todos/${id}`);
    return response;
  }
};

export default ApiService;
