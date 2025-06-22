import {Link, useLocation, useNavigate} from "react-router-dom";
import {ArrowFatLeft} from "@phosphor-icons/react";
import styles from "./breadcrumbs.module.css";
import {useRoom} from "../../context/RoomContext.jsx";
import {useDevice} from "../../context/DeviceContext.jsx";

const Breadcrumbs = () => {
    let location = useLocation();
    const rooms = useRoom();
    const {devices} = useDevice();
    let redirect = useNavigate();
    let paths = location.pathname.split("/").filter((x) => x);

    const generateBreadcrumb = (paths) => {
        return paths.reduce((acc, path, index) => {
            let displayName = path.replace("-", " ");
            displayName = displayName.charAt(0).toUpperCase() + displayName.slice(1);

            if (index === 0 && path.toLowerCase() === "houses") {
                acc.push({
                    name: "Home",
                    path: "/"
                });
                return acc;
            }

            if (!isNaN(path)) {
                const previousPath = paths[index - 1];
                if (previousPath === "houses") {
                    displayName = `My Smart Home ${path}`;
                } else if (previousPath === "rooms") {
                    displayName = rooms.find(room => room.roomId === parseInt(path))?.roomName ?? `Room ${path}`;
                } else if (previousPath === "devices") {
                    displayName = devices?.find(dev => dev?.deviceID === parseInt(path))?.deviceName ?? `Device ${path}`;
                }
            }

            const url = `/${paths.slice(0, index + 1).join("/")}`;

            if (path !== "rooms" && path !== "devices") {
                acc.push({
                    name: displayName,
                    path: url
                });
            }

            return acc;
        }, []);
    };
    return (
        paths.length > 1 && (
            <nav>
                <ArrowFatLeft
                    weight="duotone"
                    color="var(--primary-color)"
                    size={24}
                    onClick={() => redirect(-1)}
                    className={styles.back}
                />
                <ol>
                    {generateBreadcrumb(paths).map((crumb, index) => {
                        return (
                            <li key={index}>
                                <Link to={crumb.path}>{crumb.name}</Link>
                            </li>
                        );
                    })}
                </ol>
            </nav>
        )
    );
};

export default Breadcrumbs;
