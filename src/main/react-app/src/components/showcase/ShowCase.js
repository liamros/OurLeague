import { Container } from "@mui/material";
import { motion } from "framer-motion";
import * as React from 'react';
import ShowCaseDetail from "./ShowCaseDetail";

const ShowCase = () => {



    const [showCaseDetails, setShowCaseDetails] = React.useState(null)

    React.useEffect(() => {
        getShowCaseDetails()
    }, [])

    const getShowCaseDetails = () => {
        fetch('http://localhost:8080/match/getShowCaseDetails', { mode: 'cors' })
            .then(res => res.json())
            .then((data) => {
                setShowCaseDetails(data)
            }).catch(console.log)
    }

    // let list = [4070, 5213, 1641]
    // var list = [{ statName: "KDA", summonerName: "Shakobi", value: 2.7, description: "kekw" }, { statName: "WinRate", summonerName: "Cavendish31", value: 55, description: "kekw" }]
    
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
        <Container style={{width: "100%", height: "100%", margin: "0%", padding: "0%", display: "inline-block"}}>
            {
                showCaseDetails ?
                    showCaseDetails.map((element, i) => {
                        return (
                            <motion.li
                                key={i}
                                custom={i}
                                style={styles.detailContainer}
                                initial="hidden"
                                animate="visible"
                                variants={variants}
                            >
                                <ShowCaseDetail key={i} stats={element} /*profileIconNum={list[index]}*/ />
                            </motion.li>
                        )
                    }) : <p>'Loading...'</p>
            }
        </Container>

    )



}

const styles = {
    detailContainer: {
        display: "inline-block",
        width: '29%',
        margin: "2%"
    }

}


export default ShowCase