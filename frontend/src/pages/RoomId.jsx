import {useEffect, useState} from "react";
import {useParams} from "react-router-dom";
import {useRoom} from "../context/RoomContext.jsx";
import Content from "../components/BaseLayout/Content.jsx";
import BaseLayout from "../components/BaseLayout/BaseLayout.jsx";
import Sidebar from "../components/BaseLayout/Sidebar.jsx";
import {ArrowsOut, Bed, Devices, House, LampPendant, Plant, Steps} from "@phosphor-icons/react";
import ItemGrid from "../components/ItemGrid/ItemGrid.jsx";
import IconInfo from "../components/IconInfo/IconInfo.jsx";
import ErrorPage from "./Status/ErrorPage.jsx";
import LoadingPage from "./Status/LoadingPage.jsx";
import {useDevice} from "../context/DeviceContext.jsx";
import Item from "../components/ItemGrid/Item.jsx";
import EmptyList from "../components/EmptyList/EmptyList.jsx";

const capitalize = (str) => {
    const lowerCase = str.toLowerCase();
    const firstLetter = lowerCase.charAt(0).toUpperCase();
    return firstLetter + lowerCase.slice(1);
};

const RoomId = () => {
    const {roomId} = useParams();
    const rooms = useRoom();
    const match = rooms.find((room) => room.roomId.toString() === roomId);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(false);
    const devicesContext = useDevice()
    const [devices, setDevices] = useState([]);

    useEffect(() => {
        const loadingTimer = setTimeout(() => {
            setLoading(false);
            if (!match) {
                setError(true);
            }
        }, 500);
        return () => clearTimeout(loadingTimer);
    }, [match]);


    useEffect(() => {
        const devicesRoom = devicesContext?.devices.filter((device) => device?.roomId?.toString() === roomId);
        setDevices(devicesRoom);
    }, [devicesContext]);


    if (error) {
        return <ErrorPage customError={"Room not found"}/>;
    }

    if (loading) {
        return <LoadingPage/>;
    }

    return (
        <BaseLayout pageTitle={match?.roomName}>
            <Content
                title={`Devices`}
                buttonPath={"add-device"}
                buttonText={"Add Device"}
                backButtonText={"Back"}
            >
                {devices?.length > 0 ?
                    <ItemGrid>
                        {devices?.map((device, index) => (
                            <div key={index}>
                                <Item
                                    itemName={device.deviceName}
                                    itemDescription={device.isActive ? "Active" : "Inactive"}
                                    backgroundImage={device.imageUrl}
                                    navigateTo={`devices/${device.deviceID}`}
                                />
                            </div>
                        ))}
                    </ItemGrid>
                    :
                    <EmptyList listOf="Devices"/>
                }

            </Content>

            <Sidebar title="Room Info">
                <IconInfo icon={<Devices/>} label={"Smart Devices"}
                          value={devices.length.toString()}/>
                <IconInfo
                    icon={match?.outdoorIndoor === 'INDOOR' ? <LampPendant/> : <Plant/>}
                    label={"Location"}
                    value={match && capitalize(match?.outdoorIndoor)}
                />
                <IconInfo icon={<ArrowsOut/>} label={"Dimensions"}
                          value={`${match?.length}m * ${match?.width}m * ${match?.height}m`}/>
                <IconInfo icon={<Steps/>} label={"Floor"}
                          value={match && capitalize(match?.houseFloor)}/>
                <IconInfo icon={<House/>} label={"House Id"} value={match?.houseId}/>
                <IconInfo icon={<Bed/>} label={"Room Id"} value={match?.roomId}/>
            </Sidebar>
        </BaseLayout>
    );
};

export default RoomId;

