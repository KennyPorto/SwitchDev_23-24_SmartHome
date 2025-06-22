import {useEffect, useState} from "react";
import BaseLayout from "../components/BaseLayout/BaseLayout";
import Content from "../components/BaseLayout/Content";
import {useParams} from "react-router-dom";
import Sidebar from "../components/BaseLayout/Sidebar";
import InputText from "../components/Form/InputText";
import {useDeviceLogs} from "../context/LogsContext";
import {useDevice} from "../context/DeviceContext";
import {Bounce, toast, ToastContainer} from "react-toastify";
import ListItem from "../components/ListItem/ListItem";
import EmptyList from "../components/EmptyList/EmptyList.jsx";

const ONE_WEEK_MILLIS = 604800000;

const addNameToList = (compList, compNamesList) => {
    if (compList.length === 0) return;
    if (compNamesList.length === 0) return;

    compList.forEach((item) => {
        const logFound = compNamesList.find((it) => it.sensorId === item.sensorId);
        if (logFound) {
            item.name = logFound.name;
        }
    });
};

export default function DeviceLogs() {
    const [startDate, setStartDate] = useState(
        new Date(Date.parse(new Date()) - ONE_WEEK_MILLIS).toISOString()
    );
    const [endDate, setEndDate] = useState(new Date().toISOString());

    const {deviceId} = useParams();
    const deviceLogs = useDeviceLogs(deviceId);

    const [sensorLogs, setSensorLogs] = useState([]);
    const [actuatorLogs, setActuatorLogs] = useState([]);
    const [nameFilter, setNameFilter] = useState("");

    const {devices} = useDevice();

    const device = devices.find(
        (device) => device.deviceID.toString() === deviceId
    );

    useEffect(() => {
        addNameToList(deviceLogs.logs.sensorLogs, deviceLogs.sensors.sensors);
        addNameToList(deviceLogs.logs.actuatorLogs, deviceLogs.actuators.actuators);
        const filteredSensors = filterList(deviceLogs.logs.sensorLogs, "Sensor");
        const filteredActuators = filterList(deviceLogs.logs.actuatorLogs, "Actuator");
        setSensorLogs([...filteredSensors]);
        setActuatorLogs([...filteredActuators]);
    }, [deviceLogs.logs, startDate, endDate, nameFilter]);

    const onNameChange = (e) => {
        setNameFilter(e.target.value);
    };

    const onStartDateChange = (e) => {
        const selectedDateMillis = Date.parse(e.target.value);
        const endDateMillis = Date.parse(endDate);

        if (selectedDateMillis < endDateMillis) {
            setStartDate(e.target.value);
        } else {
            toast.error("Start date can't be after end date", {
                position: "top-right",
                autoClose: 4000,
                theme: "colored",
                transition: Bounce,
            });
        }
    };

    const onEndDateChange = (e) => {
        const selectedDateMillis = Date.parse(e.target.value);
        const instantMillis = Date.parse(new Date().toString());

        if (selectedDateMillis < instantMillis) {
            setEndDate(e.target.value);
        } else {
            toast.error("End date can't be a future date", {
                position: "top-right",
                autoClose: 3000,
                theme: "colored",
                transition: Bounce,
            });
        }
    };

    const filterList = (list, type) => {
        const logsFiltered = list.filter((log) => {
            const logTime = Date.parse(log.timeStamp.split("T")[0]);
            const startTime = Date.parse(startDate);
            const endTime = Date.parse(endDate);
            return logTime >= startTime && logTime <= endTime;
        });

        let filtered = logsFiltered;

        if (logsFiltered.length !== 0 && logsFiltered[0].name) {
            filtered = logsFiltered.filter((log) =>
                `${type}: ${log.name}`.toLowerCase().includes(nameFilter.toLowerCase())
            );
        }

        return filtered;
    };

    let renderActuatorLogs;
    if (actuatorLogs) {
        renderActuatorLogs = <ListItem list={actuatorLogs} type={"Actuator"}/>;
    }

    let renderSensorLogs;
    if (sensorLogs) {
        renderSensorLogs = <ListItem list={sensorLogs} type={"Sensor"}/>;
    }

    return (
        <BaseLayout pageTitle="Device Logs">
            <Content title={device !== undefined ? device.deviceName : ""}>
                {(actuatorLogs.length <= 0 && sensorLogs.length <= 0) &&
                    <EmptyList listOf={"Logs"}/>}
                {actuatorLogs.length > 0 && renderActuatorLogs}
                {sensorLogs.length > 0 && renderSensorLogs}
            </Content>
            <ToastContainer className="toast-container"/>
            <Sidebar title="Filters">
                <InputText
                    label="By Input:"
                    placeholder="Temperature"
                    onChange={onNameChange}
                />
                <InputText
                    label="By Start Date:"
                    onChange={onStartDateChange}
                    type="date"
                    value={startDate.split("T")[0]}
                />
                <InputText
                    label="By End Date:"
                    onChange={onEndDateChange}
                    type="date"
                    value={endDate.split("T")[0]}
                />
            </Sidebar>
        </BaseLayout>
    );
}
