import { createContext, useContext, useState, useEffect } from "react";
import PropTypes from "prop-types";
import {fetchDeviceLogs} from "../services/LogsService";
import {useSensor} from "./SensorContext.jsx";
import {useActuator} from "./ActuatorContext.jsx";

const LogsContext = createContext(null);

export const LogsProvider = ({ children }) => {
  const [logs, setLogs] = useState({ actuatorLogs: [], sensorLogs: [] });

  const sensors = useSensor();
  const actuators = useActuator();

  const getDeviceLogs = async (deviceId) => {
    if (!deviceId) return;
    const res = await fetchDeviceLogs(deviceId);

    res.actuatorLogs.sort(
      (a, b) =>
        Date.parse(b.timeStamp.split("T")[0]) -
        Date.parse(a.timeStamp.split("T")[0])
    );
    res.sensorLogs.sort(
      (a, b) =>
        Date.parse(b.timeStamp.split("T")[0]) -
        Date.parse(a.timeStamp.split("T")[0])
    );

    setLogs(res);
  };

  return (
    <LogsContext.Provider
      value={{
        logs,
        getDeviceLogs,
        sensors,
        actuators,
      }}
    >
      {children}
    </LogsContext.Provider>
  );
};

export function useDeviceLogs(deviceId) {
  const context = useContext(LogsContext);
  if (!context) {
    throw new Error("Logs Context Error");
  }

  const {
    logs,
    getDeviceLogs,
    sensors,
    actuators,
  } = context;

  useEffect(() => {
    getDeviceLogs(deviceId);
  }, []);

  return { logs, sensors, actuators, getDeviceLogs };
}

LogsProvider.propTypes = {
  children: PropTypes.node.isRequired,
};
