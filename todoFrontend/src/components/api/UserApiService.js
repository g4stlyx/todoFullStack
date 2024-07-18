import { apiClient } from "./ApiClient"

export const getAllUsersApi = () => apiClient.get("/users")
// export const getUserByIdApi = (id) => apiClient.get(`/users/${id}`)
export const getUserByUsernameApi = (username) => apiClient.get(`/users/${username}`)
export const deleteUserApi = (id) => apiClient.delete(`/users/${id}`)
export const updateUserApi = (username, user) => apiClient.put(`/users/${username}`, user)
export const createUserApi = (user) => apiClient.post("/users", user)