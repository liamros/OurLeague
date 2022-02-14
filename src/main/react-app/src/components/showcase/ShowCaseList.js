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

    var rows = []
    let i = 0
    for (let [statName, stats] of Object.entries(showCaseDetails)) {
        rows.push(
            <motion.div
                key={i}
                custom={i}
                style={styles.detailContainer}
                initial="hidden"
                animate="visible"
                variants={variants}

            >
                <ShowCaseDetail key={i} name={statName} stats={stats} isSelected={statName === selectedId} />
            </motion.div>
        )
        i++
    }

    return (
        <ul className="container">
            {rows}
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