import {useNavigate} from "react-router-dom";
import SectionHeading from "../Headings/SectionHeading.jsx";
import PropTypes from "prop-types";
import Button from "../Button/Button.jsx";

const Content = ({
                     title = "",
                     children,
                     buttonPath,
                     buttonText,
                     altButtonPath,
                     altButtonText
                 }) => {
    const navigate = useNavigate();
    const hasButton = buttonPath && buttonText;
    const hasSecondButton = altButtonPath && altButtonText;

    return (
        <section className="content">
            <div className="content-buttons delay-fade-down">
                <SectionHeading text={title}/>
                <div className={"buttons"}>
                    {hasButton && (
                        <Button onClick={() => navigate(buttonPath)} text={buttonText}/>
                    )}
                    {hasSecondButton && (
                        <Button onClick={() => navigate(altButtonPath)}
                                text={altButtonText}
                                isSecondary/>
                    )}
                </div>
            </div>
            {children}
        </section>
    );
};

Content.propTypes = {
    title: PropTypes.string,
    children: PropTypes.node,
    buttonPath: PropTypes.string,
    buttonText: PropTypes.string,
    altButtonPath: PropTypes.string,
    altButtonText: PropTypes.string
}

export default Content;
