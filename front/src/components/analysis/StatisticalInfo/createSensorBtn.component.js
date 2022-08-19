

import { connect } from "react-redux";
import React, { Component } from 'react';
// import {
//     retrieveTutorials
// } from "../../actions/monitoring/tutorials";

class SensorBtn extends Component {
    constructor(props) {
        super(props);
        this.clickBtn = this.clickBtn.bind(this);
        this.clearBtn = this.clearBtn.bind(this);
        this.state = {
            isClicked: false,

        }
    }

    componentDidMount() {

    }
    componentDidUpdate(prevProps, prevState) {
        console.log(this.props);
        console.log(this.props.clearData);
        if(this.props.clearData){
            // this.clearBtn();
        }
    }
    clearBtn(){
        this.setState({
            isClicked: false,
        })
    }


    clickBtn(res){
        let param = [];
        console.log(res)
        this.setState({
            isClicked : !this.state.isClicked
        })
        if(this.props.name === "nummeric"){

            if(this.state.isClicked){
                param.push(-1);     //-1이면 배열에서 제거하기위함
                param.push(this.props.content.var);
                param.push(this.props.content.expln);
                this.props.btnFunc(param);
            }else{
                param.push(1);      //1이면 배열에 추가하기 위해
                param.push(this.props.content.var);
                param.push(this.props.content.expln);
                this.props.btnFunc(param);
            }
        } else{
            if(this.state.isClicked){
                param.push(-1);     //-1이면 배열에서 제거하기위함
                param.push(this.props.content.var);
                param.push(this.props.content.expln);
                this.props.btnFunc2(param);
            }else{
                param.push(1);      //1이면 배열에 추가하기 위해
                param.push(this.props.content.var);
                param.push(this.props.content.expln);
                this.props.btnFunc2(param);
            }
        }
    }



    render() {
        const {isClicked} = this.state;
        const {content} =this.props;
        if(this.props.clearData){
            // this.clearBtn();
        }
        return (
            <button 
                className={`buttonList ${isClicked? 'blue': 'red'}`}
                id={content.var}
                style={{backgroundColor: isClicked ? 'blue' : 'red'}}
                onClick={this.clickBtn} >
                {content.expln}
            </button>
        );
    }
}


export default connect(null, {
})(SensorBtn);