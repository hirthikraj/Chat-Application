import { httpClient } from "../helper/AxiosHelper";
export const getAllGroupsForUser = async (userId) => {
    const response = await httpClient.get(
        `/api/v1/users/${userId}/groups/all`
      );
      return response.data;
}

export const createNewUser = async (user) => {
    const response = await httpClient.post(
        "/api/v1/users/login",
        user
      );
      return response.data;
}