import * as React from "react";
import * as ReactDOM from "react-dom/client";
import {RouterProvider} from "react-router-dom";
import router from "./router/index.jsx";
import "@fontsource-variable/work-sans";
import "@fontsource-variable/work-sans/wght-italic.css";
import Footer from "./components/Footer/Footer.jsx";
import "./styles/index.css";
import "react-toastify/dist/ReactToastify.css";
import {RoomProvider} from "./context/RoomContext.jsx";
import {HouseProvider} from "./context/HouseContext.jsx";
import {DeviceProvider} from "./context/DeviceContext.jsx";
import {LogsProvider} from "./context/LogsContext.jsx";
import {SensorProvider} from "./context/SensorContext.jsx";
import {ActuatorProvider} from "./context/ActuatorContext.jsx";

ReactDOM.createRoot(document.getElementById("root")).render(
    <React.StrictMode>
        <HouseProvider>
            <RoomProvider>
                <DeviceProvider>
                    <SensorProvider>
                        <ActuatorProvider>
                            <LogsProvider>
                                <RouterProvider router={router}/>
                                <Footer/>
                            </LogsProvider>
                        </ActuatorProvider>
                    </SensorProvider>
                </DeviceProvider>
            </RoomProvider>
        </HouseProvider>
    </React.StrictMode>
)
;
