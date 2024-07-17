import { apiClient } from "./ApiClient"

export const getAllTodosByUsernameApi = (username) => apiClient.get(`users/${username}/todos`)
export const getTodoApi = (username,id) => apiClient.get(`users/${username}/todos/${id}`)
export const deleteTodoApi = (username,id) => apiClient.delete(`users/${username}/todos/${id}`)
export const updateTodoApi = (username,id,todo) => apiClient.put(`users/${username}/todos/${id}`, todo)
export const createTodoApi = (username, todo) => apiClient.post(`users/${username}/todos`, todo)

export const getAllUsersApi = () => apiClient.get("/users")
export const getUserByIdApi = (id) => apiClient.get(`/users/${id}`)
export const deleteUserApi = (id) => apiClient.delete(`/users/${id}`)
export const updateUserApi = (id, user) => apiClient.put(`/users/${id}`, user)
export const createUserApi = (user) => apiClient.post("/users", user)