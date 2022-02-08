import ShowCaseDetail from "./ShowCaseDetail"
import * as React from 'react';
import { Container } from "@mui/material";

class ShowCase extends React.Component {

    constructor(props) {
        super(props)
        this.state = {showCaseDetails: null}
    }

    componentDidMount() {
        this.getShowCaseDetails()
    }

    getShowCaseDetails() {
        fetch('http://localhost:8080/match/getShowCaseDetails', { mode: 'cors' })
            .then(res => res.json())
            .then((data) => {
                this.setState({showCaseDetails: data})
            }).catch(console.log)
    }



    render() {

        // let list = [4070, 5213, 1641]
        // var list = [{ statName: "KDA", summonerName: "Shakobi", value: 2.7, description: "kekw" }, { statName: "WinRate", summonerName: "Cavendish31", value: 55, description: "kekw" }]
        return (
            <Container style={{width: "100%", marginTop : "2%"}}>{
                this.state.showCaseDetails ?
                this.state.showCaseDetails.map(element => {
                    return <ShowCaseDetail key={element.statName} stats={element} /*profileIconNum={list[index]}*/ />
                }) : <p>'Loading...'</p>
            }
            </Container>

        )


    }
}



export default ShowCase