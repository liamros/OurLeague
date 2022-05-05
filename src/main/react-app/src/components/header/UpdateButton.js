import Button from '@mui/material/Button';
import React from "react";
import * as SockJS from 'sockjs-client';
import * as Stomp from 'stompjs';


const UpdateButton = () => {

    const onClick = () => {
        stompClient.send(
            "/requestRefreshData",
            {},
            "kekw"
          );
    }

    var stompClient
    React.useEffect(() => {
        var socket = new SockJS('http://localhost:5000/ws');
        stompClient = Stomp.over(socket)
        stompClient.connect({}, onConnected)
    }, [])
    const onConnected = () => {
        console.log("onConnected");
        stompClient.subscribe("/topic/dataRefresh", onMessageReceived);
    
        stompClient.send(
          "/requestRefreshData",
          {},
          "kekw"
        );
    }

    const onMessageReceived = (payload) => {
        console.log("onMessageReceived");
        var message = JSON.parse(payload.body);
        console.log(message)
    }
    return (
        <>
            <Button
                sx={{ my: 2, color: 'white', display: 'block' }}
                onClick={onClick}
            >
                Update
            </Button>
        </>

    )


}

export default UpdateButton