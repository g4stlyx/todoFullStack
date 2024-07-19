import { apiClient } from "./ApiClient"

export const getAllTodosByUsernameApi = (username) => apiClient.get(`users/${username}/todos`)
export const getTodoApi = (username,id) => apiClient.get(`users/${username}/todos/${id}`)
export const deleteTodoApi = (username,id) => apiClient.delete(`users/${username}/todos/${id}`)
// export const updateTodoApi = (username,id,todo) => apiClient.put(`users/${username}/todos/${id}`, todo)
export const updateTodoApi = (username, id, todo) => {
    apiClient.put(`users/${username}/todos/${id}`, todo)
        .catch(error => {
            if (error.response.status === 403) {
                throw new Error("You are not authorized to update this todo.");
            } else {
                throw error;
            }
        });
}
export const createTodoApi = (username, todo) => apiClient.post(`users/${username}/todos`, todo)
