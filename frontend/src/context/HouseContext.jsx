import {createContext, useContext, useEffect, useState} from 'react';
import PropTypes from 'prop-types';
import {fetchHouses} from '../services/HouseService';

export const HouseContext = createContext(null);

export const HouseProvider = ({children}) => {
    const [houses, setHouses] = useState([]);
    const [currentHouse, setCurrentHouse] = useState(null);

    useEffect(() => {
        const fetchAllHouses = async () => {
            const res = await fetchHouses();
            setHouses(res);
        };

        fetchAllHouses();
    }, []);

    const getHouseById = (houseId) => {
        const house = houses.find(h => h.houseId === parseInt(houseId, 10));
        setCurrentHouse(house);
        return house;
    };

    return (
        <HouseContext.Provider value={{houses, currentHouse, getHouseById}}>
            {children}
        </HouseContext.Provider>
    );
};

export function useHouse() {
    return useContext(HouseContext);
}

HouseProvider.propTypes = {
    children: PropTypes.node.isRequired,
};
