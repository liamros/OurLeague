import { Card, CardMedia } from "@mui/material";
import StyledEngineProvider from '@mui/material/StyledEngineProvider';
import { motion } from "framer-motion";
import React from 'react';
import { connect } from 'react-redux';
import { Link } from "react-router-dom";
import RankingTable from "../ranking/RankingTable";

const ShowCaseItem = ({ id, showCaseRankings }) => {

    const stats = showCaseRankings[id][0]


    var summonerName = stats.summonerName
    var profileIcon = stats.profileIcon

    const rank = stats.rank
    var tier = rank.tier.toLowerCase()
    var queueType = "HAS NOT BEEN PLACED"
    if (rank.queueType)
        queueType = rank.queueType

    return (
        <StyledEngineProvider injectFirst>
            <motion.div
                initial={{ opacity: 0 }}
                animate={{ opacity: 1 }}
                exit={{ opacity: 0, transition: { duration: 0.15 } }}
                transition={{ duration: 0.2, delay: 0.15 }}
                style={{ pointerEvents: "auto" }}
                className="overlay"

            >
                <Link to="/" />
            </motion.div>

            <div className="card-content-container open">
                <motion.div layoutId={`card-${id}`} className="content-container">
                    <Card
                        elevation={10}
                        className="content-container card"
                    >
                        <motion.div layoutId={`card-title-${id}`}>
                            <div className="card-typography-title">{id}</div>
                        </motion.div>
                        <motion.div layoutId={`card-content-${id}`}>
                            <CardMedia
                                component="img"
                                image={profileIcon}
                                className="card-media"
                            />
                        </motion.div>
                        <motion.div layoutId={`card-name-${id}`}>
                            <div className="card-typography">{summonerName}</div>
                        </motion.div>

                        <motion.div className="card-typography" animate>
                            <CardMedia
                                component="img"
                                image={require(`../../img/rank/emblem_${tier}.png`)}
                                className="card-media-rank"
                            />

                            {
                                rank.division ?
                                    (<>
                                        <div className="card-typography">{rank.tier} {rank.division} {rank.lp} LP in {queueType}</div>
                                    </>) :
                                    (<>
                                        <div className="card-typography">{rank.tier} - 0 LP</div>
                                    </>)
                            }

                            <RankingTable rankings={showCaseRankings[id]} />
                        </motion.div>
                    </Card>
                </motion.div>

            </div>
            </StyledEngineProvider>
    )
}


function mapStateToProps(state) {
    return {
        showCaseRankings: state.showCaseRankings.data,
    }
}

export default connect(
    mapStateToProps,
)(ShowCaseItem)