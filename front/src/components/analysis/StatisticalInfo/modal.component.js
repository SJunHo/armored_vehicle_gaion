

import { connect } from "react-redux";
import React, { Component } from 'react';
import Modal from 'react-modal';
//npm install react-modal
import ChoiceDateModal from "./choiceDateModal.component";
import ChoiceSensorModal from "./choiceSensorModal.component";

// import {
//     selectAllVehicle
// } from "../../../actions/monitoring/vehicleStatics";
Modal.setAppElement("#root");

class OpenModal extends Component {
    constructor(props) {
        super(props);
        this.modalShow = this.modalShow.bind(this);
        this.carryModalShow = this.carryModalShow.bind(this);
        this.carryFileInfo = this.carryFileInfo.bind(this);
        
        this.carrySensorInfo = this.carrySensorInfo.bind(this);
        this.state = {
            isOpen : true,
            choiceThings : [],
            fileNameAndId : [],
            nummericSensor: [],
            categoricSensor: [],
            nummericSensorWithKor: [],
            categoricSensorWithKor: [],
        } 
    }

    componentDidMount() {
        
        console.log(this.props);
    }
    
    componentWillUnmount() {        //컴포넌트가 소멸될때 데이터를 searchEachInfo로 전달
        if(this.props.modalDiv === "choiceDate"){
            this.props.modalFunc(true);
        }else{
            this.props.modalFunc(true);
        }
    }

    componentDidUpdate() {
    }

    modalShow() {   //내부 팝업창 화면 오픈하기 위한 설정
        this.state.isOpen === true
        ? this.setState({ isOpen : false})
        : this.setState({ isOpen : true})
    }

    openModal(){   //searchEachInfo에서 팝업창 클릭을 해서 true/false 변환 위해
        this.setState({ setModal : !this.state.setModal});
    }

    carryFileInfo(res){ //searchEachInfo에 아이디, 파일값 전달
        console.log(res);
        this.setState({
            fileNameAndId: res
        }, () => {
            this.props.fileIdFunc(this.state.fileNameAndId);
        })
    }
    carrySensorInfo(res){       //searchEachInfo 에 데이터전달
        console.log(res);
        this.setState({
            nummericSensor: [...res[0]],
            categoricSensor: [...res[1]],
            nummericSensorWithKor: [...res[2]],
            categoricSensorWithKor: [...res[3]],
        }, () => {
            this.props.sensorFunc(res);
        })
    }
    carryModalShow(res){
        this.setState({
            isOpen : res
        })
    }

    render() {
        const name = this.props.name;
        console.log(this.props.data);
        return (
            <div>
                <Modal isOpen={this.state.isOpen} onRequestClose={this.modalShow}>
                    {
                        this.props.name !== "modal1"
                        ? this.props.name === "choiceSensor"
                            ? <ChoiceSensorModal name={name} 
                                func={this.carrySensorInfo}
                                modalFunc={this.carryModalShow} />

                            : <ChoiceDateModal name={name} data={this.props.data} func={this.carryFileInfo} modalFunc={this.carryModalShow} />
                        : <ChoiceDateModal name="modal1"/>

                    }
                </Modal>
            </div>
        );
    }
}

// const mapStateToProps = (state) => {
//     return {
//         allVehicleInfo : state.tutorials,
//     };
// };

export default connect(null, {
    // selectAllVehicle
})(OpenModal);
