export const getHousePosition = (house) => {
    if (!house) return "No address available";
    return `${house.street}, ${house.doorNumber}, ${house.zipCode}, ${house.city}, ${house.country}`;
};

export const getHouseCoordinates = (house) => {
    if (!house) return null; // Return null if house data is not available
    return {
        latitude: house.latitude,
        longitude: house.longitude,
    };
};
