import {House} from "@phosphor-icons/react";
import PropTypes from "prop-types";
import {useEffect} from "react";

const TitleHeading = ({title, subtitle = undefined}) => {

    useEffect(() => {
        const length = subtitle ?
            subtitle.replace(" ", "").length :
            title.replace(/ +/g, "").length;
        document.documentElement.style
            .setProperty('--custom-underline-width', `${length - 1}ch`);
    }, [subtitle, title.length]);

    return (
        <>
            {title === "Welcome," && (
                <House weight="duotone" color="var(--primary-color)" size={30}/>
            )}
            <h1 className={subtitle ? "" : "underline"}>{title}</h1>
            {subtitle && <h1 className="underline">{subtitle}</h1>}
        </>
    );
};

TitleHeading.propTypes = {
    title: PropTypes.string,
    subtitle: PropTypes.string,
}

export default TitleHeading;
