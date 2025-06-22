import PropTypes from "prop-types";
import {WarningCircle} from "@phosphor-icons/react";

const EmptyList = ({listOf}) => {
    return <section className="content-grid empty-grid delay-fade-in">
        <WarningCircle size={86} weight={"duotone"} color={"var(--primary-color)"}/>
        <h3>No {listOf} found!</h3>
    </section>;
};

EmptyList.propTypes = {
    listOf: PropTypes.string,
}

export default EmptyList;
