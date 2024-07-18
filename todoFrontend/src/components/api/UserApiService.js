import { apiClient } from "./ApiClient"

export const getAllUsersApi = () => apiClient.get("/users")
// export const getUserByIdApi = (id) => apiClient.get(`/users/${id}`)
export const getUserByUsernameApi = (username) => apiClient.get(`/users/${username}`)
export const deleteUserApi = (id) => apiClient.delete(`/users/${id}`)
export const updateUserApi = (id, user) => apiClient.put(`/users/${id}`, user)
export const createUserApi = (user) => apiClient.post("/users", user)