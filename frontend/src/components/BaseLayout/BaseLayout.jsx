import TitleHeading from "../Headings/TitleHeading.jsx";
import Breadcrumbs from "../Breadcrumbs/Breadcrumbs.jsx";
import PropTypes from "prop-types";

const BaseLayout = ({ pageTitle = "", subtitle = undefined, children }) => {
	return (
		<main className="base-layout">
			<header className="header fade-down">
				<Breadcrumbs />
				<TitleHeading title={pageTitle} subtitle={subtitle} />
			</header>
			{children}
		</main>
	);
};

BaseLayout.propTypes = {
	pageTitle: PropTypes.string,
	children: PropTypes.node,
	subtitle: PropTypes.string,
}

export default BaseLayout;
