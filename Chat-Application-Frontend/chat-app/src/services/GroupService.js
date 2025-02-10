import { httpClient } from "../helper/AxiosHelper";
export const getAllMessagesForGroup = async (groupId) => {
    const response = await httpClient.get(
        `/api/v1/groups/${groupId}/getMessages`
      );
      return response.data;
}

export const createOrJoinGroup = async (group) => {
    const response = await httpClient.post(
        "/api/v1/groups/create",
        group
      );
      return response.data;
}