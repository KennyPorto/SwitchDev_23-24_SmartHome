import {createContext, useContext, useEffect, useState} from "react";
import PropTypes from "prop-types";
import {fetchRooms} from "../services/RoomService";
import {fetchImages} from "../services/ImageService";

const RoomContext = createContext(null);

export const RoomProvider = ({children}) => {
    const [rooms, setRooms] = useState([]);

    useEffect(() => {
        const getData = async () => {
            const res = await fetchRooms();
            const usedImageUrls = new Set();
            const roomsWithImages = await Promise.all(
                res.map(async (room) => {
                    let images = await fetchImages(room.roomName);
                    images = images.filter(img => !usedImageUrls.has(img.id));
                    const randomImage = images[Math.floor(Math.random() * images.length)];
                    usedImageUrls.add(randomImage.id);

                    return {...room, imageUrl: randomImage?.src?.medium};
                })
            );
            setRooms(roomsWithImages);
        };
        getData().then();
    }, []);

    return <RoomContext.Provider value={rooms}>{children}</RoomContext.Provider>;
};

export function useRoom() {
    return useContext(RoomContext);
}

RoomProvider.propTypes = {
    children: PropTypes.node.isRequired,
};
