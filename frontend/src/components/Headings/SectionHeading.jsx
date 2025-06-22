import PropTypes from "prop-types";

const SectionHeading = ({ text }) => {
	return (
		<h3 className="sideline delay-fade-down">
			{text}
		</h3>
	);
};

SectionHeading.propTypes = {
	text: PropTypes.string,
}

export default SectionHeading;
