import axios from "axios";
import { API_BASE_URL } from "./urls";

export const fetchDeviceLogs = async (deviceId) => {
  try {
    const response = await axios.get(
      `${API_BASE_URL}/devices/${deviceId}/logs`,
      {
        params: {
          start: new Date(
            Date.parse(new Date()) - Date.parse(new Date("2023-01-01"))
          ).toISOString(),
          end: new Date().toISOString(),
        },
      }
    );

    return response.data;
  } catch (error) {
    console.error("Error fetching device logs:", error);
    throw error;
  }
};
