import { Card, CardMedia, Typography } from "@mui/material";
import { motion } from "framer-motion";
import React from 'react';
import { connect } from 'react-redux';
import { Link } from "react-router-dom";

const ShowCaseItem = ({ id, showCaseRankings }) => {

    const stats = showCaseRankings[id][0]



    var description = stats.description
    var summonerName = stats.summonerName
    var value = stats.value
    var profileIcon = stats.profileIcon

    const rank = stats.rank
    var tier = rank.tier.toLowerCase()
    var queueType = "HAS NOT BEEN PLACED"
    if (rank.queueType)
        queueType = rank.queueType.replaceAll("_", " ").replaceAll("5x5", "").replaceAll("SR", "")

    return (
        <>
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
                        style={styles.card}
                        className="content-container"
                    >
                        <motion.div layoutId={`card-content-${id}`}>
                            <CardMedia
                                component="img"
                                image={profileIcon}
                                style={styles.cardMediaProfile}
                            />

                            <Typography style={styles.typographyTitle}>{summonerName}</Typography>
                            <Typography style={styles.typography}>{id}</Typography>
                            <Typography style={styles.typography}>{description}</Typography>
                        </motion.div>
                        <motion.div style={styles.typography} animate>
                            <Typography style={styles.typographyTitle}>{queueType}</Typography>
                            <CardMedia
                                component="img"
                                image={require(`../../img/rank/emblem_${tier}.png`)}
                                style={styles.cardMediaRank}
                            />
                            <Typography style={styles.typography}>{rank.tier}</Typography>
                            {
                                rank.division && rank.lp ?
                                    (<>
                                        <Typography style={styles.typography}>{rank.division}</Typography>
                                        <Typography style={styles.typography}>{rank.lp} LP</Typography>
                                    </>) : 
                                    (<>
                                        <Typography style={styles.typography}>-</Typography>
                                        <Typography style={styles.typography}>0 LP</Typography>
                                    </>)
                            }
                        </motion.div>


                    </Card>
                </motion.div>

            </div>
        </>
    )
}



const styles = {
    typography: {
        margin: "2%",
        color: "rgb(208, 168, 92)",
        fontSize: "0.9vw",
    },
    typographyTitle: {
        margin: "2%",
        color: "rgb(208, 168, 92)",
        fontWeight: "bold",
        fontSize: "1.0vw",
    },
    card: {
        width: '100%',
        display: "inline-block",
        margin: "0%",
        backgroundColor: "rgb(6, 28, 37)",
        border: "3px solid rgb(208, 168, 92)"
    },
    cardMediaProfile: {
        borderRadius: "50%",
        width: "20%",
        margin: "auto",
        marginTop: "5%",
        border: "2px solid rgb(208, 168, 92)"
    },
    cardMediaRank: {
        width: "20%",
        margin: "auto",

    },
    container: {
        width: '100%',
        display: "inline-block",
        margin: "0%",
        padding: "0%"
    }
}


function mapStateToProps(state) {
    return {
        showCaseRankings: state.showCaseRankings,
    }
}

export default connect(
    mapStateToProps,
)(ShowCaseItem)