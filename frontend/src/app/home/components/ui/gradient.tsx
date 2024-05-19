//Warning lowkey ugly
import React from "react";

const GradientBackground = () => {
    const getRandomGradient = () => {
        const colors = [
            "linear-gradient(45deg, #ff0000, #ff7700)",
            "linear-gradient(45deg, #ff00ff, #ff0077)",
            "linear-gradient(45deg, #ffff00, #ffaa00)",
            "linear-gradient(45deg, #00ff00, #007700)",
        ];
        return colors[Math.floor(Math.random() * colors.length)];
    };

    const backgroundStyle: React.CSSProperties = {
        background: getRandomGradient(),
        width: "100vw",
        height: "100vh",
        position: "fixed",
        top: 0,
        left: 0,
        zIndex: -1,
    };

    return <div style={backgroundStyle}></div>;
};

export default GradientBackground;
