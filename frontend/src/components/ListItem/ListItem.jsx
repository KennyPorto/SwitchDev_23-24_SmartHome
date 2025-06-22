import styles from "./ListItem.module.css";
import {CalendarDots, Clock, Ruler} from "@phosphor-icons/react";
import Pill from "./Pill.jsx";
import PropTypes from "prop-types";

export default function ListItem({list, type}) {
    const renderItems = list.map((log, index) => (
        <section className="content-grid delay-fade-in" key={index}>
            <Pill>{`${type}: ${log.name}`}</Pill>
            <div className={styles.cardGrid}>
                <section>
                    <Ruler size={32} weight={"duotone"} color={"var(--primary-color)"}/>
                    {log.measurement}
                </section>
                <section>
                    <CalendarDots size={32} weight={"duotone"}
                                  color={"var(--primary-color)"}/>
                    {log.timeStamp.split("T")[0]}{" "}
                </section>
                <section>
                    <Clock size={32} weight={"duotone"} color={"var(--primary-color)"}/>
                    {log.timeStamp.split("T")[1].split("+")[0].split(".")[0]}
                </section>
            </div>
        </section>
    ));
    return <>{renderItems}</>;
}

ListItem.propTypes = {
    type: PropTypes.string,
    list: PropTypes.array
}