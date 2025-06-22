import {createBrowserRouter} from "react-router-dom";
import ErrorPage from "../pages/Status/ErrorPage.jsx";
import HouseId from "../pages/HouseId.jsx";
import Home from "../pages/Home.jsx";
import AddDevice from "../pages/AddDevice.jsx";
import AddActuator from "../pages/AddActuator.jsx";
import AddSensor from "../pages/AddSensor.jsx";
import RoomId from "../pages/RoomId.jsx";
import DeviceLogs from "../pages/DeviceLogs.jsx";
import DeviceId from "../pages/DeviceId.jsx";

const router = createBrowserRouter([
    {
        errorElement: <ErrorPage/>,
        path: "/",
        element: (
            <Home/>
        ),
    },
    {
        path: "/houses/:houseId/",
        element: (
            <HouseId/>
        ),
    },
    {
        path: "/houses/:houseId/rooms/:roomId",
        element: (
            <RoomId/>
        ),
    },
    {
        path: "/houses/:houseId/rooms/:roomId/devices/:deviceId",
        element: (
            <DeviceId/>
        ),
    },
    {
        path: "/houses/:houseId/rooms/:roomId/add-device",
        element: (
            <AddDevice/>
        ),
    },
    {
        path: "/houses/:houseId/rooms/:roomId/devices/:deviceId/add-sensor",
        element: (
            <AddSensor/>
        ),
    },
    {
        path: "/houses/:houseId/rooms/:roomId/devices/:deviceId/add-actuator",
        element: (
            <AddActuator/>
        ),
    },
    {
        path: "/houses/:houseId/rooms/:roomId/devices/:deviceId/logs",
        element: (
            <DeviceLogs/>
        ),
    },
    {
        path: "*",
        element: <ErrorPage/>,
    },
]);

export default router;
