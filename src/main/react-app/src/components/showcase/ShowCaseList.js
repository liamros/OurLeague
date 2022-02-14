import { motion } from "framer-motion";
import * as React from 'react';
import ShowCaseDetail from "./ShowCaseDetail";

const ShowCaseList = ({ selectedId, showCaseDetails }) => {






    const variants = {
        visible: i => ({
            opacity: 1,
            transition: {
                delay: i * 0.5,
                duration: 1,
            },
        }),
        hidden: { opacity: 0 },
    }

    return (
        <ul className="container">
            {
                showCaseDetails.map((stats, i) => {
                    return (
                        <motion.div
                            key={i}
                            custom={i}
                            style={styles.detailContainer}
                            initial="hidden"
                            animate="visible"
                            variants={variants}

                        >
                            <ShowCaseDetail key={i} stats={stats} isSelected={stats.statName === selectedId} />
                        </motion.div>
                    )
                })
            }
        </ul>

    )



}

const styles = {
    detailContainer: {
        display: "inline-block",
        width: '29%',
        margin: "2%"
    }

}


export default ShowCaseList