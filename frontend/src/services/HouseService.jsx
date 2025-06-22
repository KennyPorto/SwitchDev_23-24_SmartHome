import axios from "axios";
import { API_BASE_URL } from "./urls.js";

const API_WEATHER_SERVICE = "http://10.9.24.170:8080";

export const getHouseById = async (houseId) => {
  try {
    const response = await axios.get(`${API_BASE_URL}/houses/${houseId}`);
    return response.data;
  } catch (error) {
    console.error("Error fetching house:", error);
    throw error;
  }
};

export const fetchHouses = async () => {
  try {
    const response = await axios.get(`${API_BASE_URL}/houses`);
    return response.data;
  } catch (error) {
    console.error('Error fetching houses:', error);
    throw error;
  }
};

export const fetchInstantaneousTemperature = async (groupNumber, latitude, longitude, hour) => {
  try {
    const response = await axios.get(
      `${API_WEATHER_SERVICE}/InstantaneousTemperature`,
      {
        params: {
          groupNumber,
          latitude,
          longitude,
          hour,
        },
      }
    );
    return response.data;
  } catch (error) {
    console.error("Error fetching temperature:", error);
    throw error;
  }
};
