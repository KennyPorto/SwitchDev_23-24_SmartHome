import SectionHeading from "../Headings/SectionHeading.jsx";
import PropTypes from "prop-types";

const Sidebar = ({ title = "", children }) => {
	return (
		<aside className="sidebar">
			<SectionHeading text={title} />
			{children}
		</aside>
	);
};

Sidebar.propTypes = {
	title: PropTypes.string,
	children: PropTypes.node,
}

export default Sidebar;
