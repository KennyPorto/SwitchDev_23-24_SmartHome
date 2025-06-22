import {useEffect, useState} from "react";
import {useParams} from "react-router-dom";
import {useDevice} from "../context/DeviceContext.jsx";
import BaseLayout from "../components/BaseLayout/BaseLayout.jsx";
import Content from "../components/BaseLayout/Content.jsx";
import Sidebar from "../components/BaseLayout/Sidebar.jsx";
import IconInfo from "../components/IconInfo/IconInfo.jsx";
import {Bed, Devices, Power, QrCode} from "@phosphor-icons/react";
import ErrorPage from "./Status/ErrorPage.jsx";
import {useSensor} from "../context/SensorContext.jsx";
import {useActuator} from "../context/ActuatorContext.jsx";
import RectangularItem from "../components/ItemGrid/RectangularItem.jsx";
import RectangularItemGrid from "../components/ItemGrid/RectangularItemGrid.jsx";
import LoadingPage from "./Status/LoadingPage.jsx";
import PowerDevice from "../components/IconButtons/PowerDevice.jsx";
import SeeLogsButton from "../components/IconButtons/SeeLogsButton.jsx";
import BlindRoller from "../components/BlindRoller/BlindRoller.jsx";
import EmptyList from "../components/EmptyList/EmptyList.jsx";

const DeviceId = () => {
    const {deviceId, roomId, houseId} = useParams();
    const {devices} = useDevice();
    const device = devices.find((device) => device?.deviceID.toString() === deviceId.toString());
    const {actuators} = useActuator();
    const {sensors} = useSensor();
    const [sensor, setSensor] = useState([]);
    const [actuator, setActuator] = useState([]);
    const [blindRollerActuators, setBlindRollerActuators] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(false);
    const [hasScaleSensor, setHasScaleSensor] = useState(false)

    useEffect(() => {
        const loadingTimer = setTimeout(() => {
            setLoading(false);
            if (!device) {
                setError(true);
            }
        }, 500);
        return () => clearTimeout(loadingTimer);
    }, [device]);

    useEffect(() => {
        const sensorsDevice = sensors.filter((sensor) => sensor.deviceId.toString() === deviceId);
        setSensor(sensorsDevice);
        setHasScaleSensor(sensorsDevice.some(sensor => sensor.model === "ScaleSensor"))
    }, [sensors]);

    useEffect(() => {
        const actuatorsDevice = actuators.filter((actuator) => actuator.deviceId.toString() === deviceId);
        setActuator(actuatorsDevice);
        const blindRollerActuators = actuatorsDevice.filter((actuator) => actuator.model.toString() === "BlindRollerActuator");
        setBlindRollerActuators(blindRollerActuators);
    }, [actuators]);

    if (error) {
        return <ErrorPage customError={"Device not found"}/>;
    }

    if (loading) {
        return <LoadingPage/>;
    }

    return (
        <BaseLayout pageTitle={device?.deviceName}>
            <Content
                title={`Sensors and Actuators`}
                buttonPath={`/houses/${houseId}/rooms/${roomId}/devices/${deviceId}/add-sensor`}
                buttonText={device?.isActive ? "Add Sensor" : ""}
                altButtonPath={`/houses/${houseId}/rooms/${roomId}/devices/${deviceId}/add-actuator`}
                altButtonText={device?.isActive ? "Add Actuator" : ""}
            >
                {sensor?.length + actuator?.length > 0 ?
                    <RectangularItemGrid>
                        {sensor?.map((sensor) => (
                            <RectangularItem
                                key={sensor.sensorId}
                                itemName={sensor?.name}
                                itemDescription={sensor?.model}
                                backgroundImage={sensor.imageUrl}
                            />
                        ))}
                        {actuator?.map((actuator) => (
                            <RectangularItem
                                key={actuator.actuatorId}
                                itemName={actuator.name}
                                itemDescription={actuator.model}
                                backgroundImage={actuator.imageUrl}
                            />
                        ))}
                    </RectangularItemGrid>
                    :
                    <EmptyList listOf="Sensors & Actuators"/>
                }

            </Content>
            <Sidebar title="Device Info">
                <IconInfo icon={<Devices/>} label={"Sensors and Actuators"}
                          value={sensor.length + actuator.length}/>
                <IconInfo icon={<QrCode/>} label={"Model"} value={device?.deviceModel}/>
                <IconInfo icon={<Power/>} label={"Status"}
                          value={device?.isActive === true ? "Active" : "Inactive"}/>
                <IconInfo icon={<Bed/>} label={"Room Id"} value={device?.roomId}/>
                <h3 className="sideline">Actions</h3>
                <SeeLogsButton/>
                <PowerDevice deviceId={deviceId} isActive={device?.isActive}/>
                {(blindRollerActuators.length > 0 && hasScaleSensor && device?.isActive) &&
                    <BlindRoller deviceId={deviceId}
                                 blindRollerActuators={blindRollerActuators}/>}
            </Sidebar>
        </BaseLayout>
    );
};

export default DeviceId;
