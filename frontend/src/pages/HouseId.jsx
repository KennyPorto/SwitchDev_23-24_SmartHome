import {useEffect, useState} from 'react';
import Content from "../components/BaseLayout/Content.jsx";
import BaseLayout from "../components/BaseLayout/BaseLayout.jsx";
import Sidebar from "../components/BaseLayout/Sidebar.jsx";
import {useParams} from "react-router-dom";
import {
    Bed,
    City,
    Compass,
    DoorOpen,
    Envelope,
    GlobeHemisphereEast,
    House,
    MapPin
} from "@phosphor-icons/react";
import ItemGrid from "../components/ItemGrid/ItemGrid.jsx";
import Item from "../components/ItemGrid/Item.jsx";
import {useRoom} from "../context/RoomContext.jsx";
import {useHouse} from "../context/HouseContext.jsx";
import IconInfo from "../components/IconInfo/IconInfo.jsx";
import ErrorPage from "./Status/ErrorPage.jsx";
import LoadingPage from "./Status/LoadingPage.jsx";
import EmptyList from "../components/EmptyList/EmptyList.jsx";

const HouseId = () => {
    const {houseId} = useParams();
    const roomContext = useRoom();
    const {currentHouse, getHouseById} = useHouse();
    const [rooms, setRooms] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(false);

    useEffect(() => {
        getHouseById(houseId);
    }, [houseId, getHouseById]);

    useEffect(() => {
        const loadingTimer = setTimeout(() => {
            setLoading(false);
            if (!currentHouse) {
                setError(true);
            }
        }, 500);
        return () => clearTimeout(loadingTimer);
    }, [currentHouse]);

    useEffect(() => {
        const roomsHouse = roomContext?.filter((room) => room?.houseId?.toString() === houseId);
        setRooms(roomsHouse);
    }, [roomContext]);

    if (error) {
        return <ErrorPage customError={"House not found"}/>;
    }

    if (loading) {
        return <LoadingPage/>;
    }

    return (
        <BaseLayout pageTitle={`Welcome, `} subtitle={`My Smart Home ${houseId}`}>

            <Content title="Rooms">
                {rooms?.length > 0 ?
                    <ItemGrid>
                        {rooms?.map((room) => (
                            <Item
                                key={room.roomId}
                                itemName={room.roomName}
                                itemDescription={room.outdoorIndoor}
                                backgroundImage={room.imageUrl}
                                navigateTo={`/houses/${houseId}/rooms/${room.roomId}`}
                            />
                        ))}
                    </ItemGrid>
                    :
                    <EmptyList listOf="Rooms"/>
                }
            </Content>

            <Sidebar title="House Info">
                <IconInfo icon={<House/>} label="House Id" value={houseId}/>
                <IconInfo icon={<Bed/>} label={"Rooms"} value={rooms.length.toString()}/>
                <IconInfo icon={<MapPin/>} label="Street"
                          value={currentHouse?.street || ''}/>
                <IconInfo icon={<DoorOpen/>} label="Door Number"
                          value={currentHouse?.doorNumber || ''}/>
                <IconInfo icon={<Envelope/>} label="Zip Code"
                          value={currentHouse?.zipCode || ''}/>
                <IconInfo icon={<City/>} label="City" value={currentHouse?.city || ''}/>
                <IconInfo icon={<GlobeHemisphereEast/>} label="Country"
                          value={currentHouse?.country || ''}/>
                <IconInfo icon={<Compass/>} label="Latitude"
                          value={currentHouse?.latitude || ''}/>
                <IconInfo icon={<Compass/>} label="Longitude"
                          value={currentHouse?.longitude || ''}/>
            </Sidebar>
        </BaseLayout>
    );
};

export default HouseId;
