import React from 'react'
import { Link } from "react-router-dom";
import { motion } from "framer-motion";
import { LoremIpsum } from "react-lorem-ipsum";
import { Card, CardMedia, CircularProgress, Typography } from "@mui/material";
import { useStore } from 'react-redux';

export const ShowCaseItem = ({ id }) => {

    const store = useStore()
    var stats = store.getState()["showCaseDetails"][id]



    var description = stats.description
    var summonerName = stats.summonerName
    var value = stats.value
    var profileIcon = stats.profileIcon



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

            (<div className="card-content-container open">
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
                                style={styles.cardMedia}
                            />

                            <Typography style={styles.typographyTitle}>{summonerName}</Typography>
                            <Typography style={styles.typography}>{id}</Typography>
                            {value ?
                                <Typography style={styles.typography}>{value}</Typography>
                                : <Typography style={styles.typography}>{description}</Typography>}
                        </motion.div>
                        <motion.div style={styles.typography} animate>
                            <LoremIpsum
                                p={6}
                                avgWordsPerSentence={6}
                                avgSentencesPerParagraph={4}

                            />
                        </motion.div>


                    </Card>
                </motion.div>

            </div>)
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
    cardMedia: {
        borderRadius: "50%",
        width: "20%",
        margin: "auto",
        marginTop: "5%",
        border: "2px solid rgb(208, 168, 92)"
    },
    container: {
        width: '100%',
        display: "inline-block",
        margin: "0%",
        padding: "0%"
    }
}
