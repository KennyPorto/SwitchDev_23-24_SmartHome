import axios from "axios";
import { API_BASE_URL } from "./urls";

export const fetchRooms = async () => {
  try {
    const response = await axios.get(`${API_BASE_URL}/rooms`);

    return response.data;
  } catch (error) {
    console.error("Error fetching list of rooms:", error);
    throw error;
  }
};
