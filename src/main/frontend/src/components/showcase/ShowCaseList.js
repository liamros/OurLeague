import { motion } from "framer-motion";
import * as React from 'react';
import ShowCaseRanking from "./ShowCaseRanking";

const ShowCaseList = ({ selectedId, showCaseRankings }) => {



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
    for (let [statName, stats] of Object.entries(showCaseRankings)) {
        rows.push(
            <motion.div
                key={i}
                custom={i}
                className="detail-container"
                initial="hidden"
                animate="visible"
                variants={variants}

            >
                <ShowCaseRanking key={i} name={statName} stats={stats[0]} isSelected={statName === selectedId} />
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


export default ShowCaseList