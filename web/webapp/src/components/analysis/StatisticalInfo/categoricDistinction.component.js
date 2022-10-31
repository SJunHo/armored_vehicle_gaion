

import { connect } from "react-redux";
import React, { Component } from 'react';
import "./searchEachInfo.css";

class Categoric extends Component {
    constructor(props) {
        super(props);
        this.categoric = this.categoric.bind(this);
        this.beforeStart = this.beforeStart.bind(this);
        this.state = {
            categoricSensor: [],
            forChartData: [],
            i: 0,
        }
    }

    componentDidMount() {
        this.setState({
            categoricSensor : this.props.data,
            forChartData: this.props.chartData,
            i: this.props.idx
        }, () => {
            console.log(this.state.categoricSensor);
        })
    }

    categoric(el){      //데이터 판별을 위한 함수
        let value;
        value = this.props.chartData[this.props.idx];

        if(this.props.chartData.length === this.props.idx){
            let lastValue = this.props.chartData[this.props.idx -1][el.el];
            return(
                <label>finish</label>
            );
        }

        switch(el.el){
            case 'ENGWARNING' :
                value = value.ENGWARNING;
                
                if(value === 0){
                    return (
                        <label className="btnn-gr">정상</label>
                            
                    )
                }else{
                    return (
                        <label className="btnn-re">경고</label>
                    )
                }
                
            case 'ENGOILSTA':
                value = value.ENGOILSTA;
                if(value === 0){
                    return(
                        <label className="btnn-gr">정상</label>
                    )
                }else{
                    return(
                        <label className="btnn-re">저압</label>
                    )
                }
            
            case 'ENGHEAT':
                value = value.ENGHEAT;
                
                if(value === 0){
                    return(
                        <label className="btnn-re">미작동</label>
                    )
                }else{
                    return(
                        <label className="btnn-gr">작동</label>
                    )
                }
            
            case 'AUTOTRANS':
                value = value.AUTOTRANS;
                if(value === 0){
                    return(
                        <label className="btnn-gr">정상</label>
                    )
                }else{
                    return(
                        <label className="btnn-re">비정상</label>
                    )
                }
            case 'ENGGOV':
                value = value.ENGGOV;
                
                if(value === 0){
                    return(
                        <label className="btnn-bl">해제</label>
                    )
                }else{
                    return (
                        <label className="btnn-bl">작동</label>
                    )
                }
            
            case 'INDUSTFIL':
                value = value.INDUSTFIL;
                
                if(value === 0){
                    return(
                        <label className="btnn-gr">정상</label>
                    )
                } else{
                    return (
                        <label className="btnn-re">막힘</label>
                    )
                }
            
            case 'COOLOIL':
                value = value.COOLOIL;
                
                if(value === 0){
                    return(
                        <label className="btnn-gr">정상</label>
                    )
                } else {
                    return (
                        <label className="btnn-re">부족</label>
                    )
                }
            
            case 'COOLLANT':
                value = value.COOLLANT;
                
                if(value === 0){
                    return(
                        <label className="btnn-gr">정상</label>
                    )
                } else {
                    return (
                        <label className="btnn-re">부족</label>
                    )
                }

            case 'ENGOVERCTLMD':
                value = value.ENGOVERCTLMD;
                
                if(value === 0){
                    return(
                        <label className="btnn-re">미운용</label>
                    )
                } else if(value === 1){
                    return(
                        <label className="btnn-bl">속도제어</label>
                    )
                } else if(value === 2){
                    return (
                        <label className="btnn-bl">토크제한</label>
                    )
                } else{
                    return(
                        <label className="btnn-bl">속도/토크제어</label>
                    )
                }
            
            case 'REQTRANS':
                value = value.REQTRANS;
                
                if(value === -1){
                    return(
                        <label className="btnn-bl">R</label>
                    )
                } else if(value === 0) {
                    return (
                        <label className="btnn-bl">N</label>
                    )
                }else{
                    return(
                        <label className="btnn-bl">D</label>
                    )
                }

            case 'EMERTRANSMD':
                value = value.EMERTRANSMD;
                
                if(value === 0){
                    return(
                        <label className="btnn-bl">해제</label>
                    )
                } else {
                    return (
                        <label className="btnn-bl">설정</label>
                    )
                }

            case 'TRANSOILHEAT':
                value = value.TRANSOILHEAT;
                if(value === 0){
                    return(
                        <label className="btnn-gr">정상</label>
                    )
                } else {
                    return (
                        <label className="btnn-re">과열</label>
                    )
                }

            case 'FANMD':
                value = value.FANMD;
                if(value === 0){
                    return(
                        <label className="btnn-gr">일반</label>
                    )
                } else if (value === 1){
                    return (
                        <label className="btnn-bl">냉방</label>
                    )
                }else if(value === 2){
                    return(
                        <label className="btnn-bl">리타더</label>
                    )
                }else if(value === 3){
                    return(
                        <label className="btnn-bl">유압방열</label>
                    )
                }else{
                    return(
                        <label className="btnn-bl">KickDown</label>
                    )
                }

            case 'FANVVALDUTY':
                value = value.TRANSOILHEAT;
                return (
                    <label className="btnn-gr">{value}</label>
                )
            
            case 'PARKSTA':
                value = value.PARKSTA;
                if(value === 0){
                    return(
                        <label className="btnn-bl">해제</label>
                    )
                } else {
                    return (
                        <label className="btnn-bl">잠김</label>
                    )
                }
            
            case 'RETDBREAK':
                value = value.RETDBREAK;
                if(value === 0){
                    return(
                        <label className="btnn-re">미작동</label>
                    )
                } else {
                    return (
                        <label className="btnn-gr">작동</label>
                    )
                }
                            
            case 'AIRMASTER':
                value = value.AIRMASTER;
                if(value === 0){
                    return(
                        <label className="btnn-gr">정상</label>
                    )
                } else {
                    return (
                        <label className="btnn-re">이상</label>
                    )
                }

            case 'BREAKOIL':
                value = value.BREAKOIL;
                if(value === 0){
                    return(
                        <label className="btnn-gr">정상</label>
                    )
                } else {
                    return (
                        <label className="btnn-re">이상</label>
                    )
                }
                
            case 'AIRTANKR':
                value = value.AIRTANKR;
                if(value === 0){
                    return(
                        <label className="btnn-gr">정상</label>
                    )
                } else {
                    return (
                        <label className="btnn-re">저공압</label>
                    )
                }
                                
            case 'AIRTANKL':
                value = value.AIRTANKL;
                if(value === 0){
                    return(
                        <label className="btnn-gr">정상</label>
                    )
                } else {
                    return (
                        <label className="btnn-re">저공압</label>
                    )
                }
                                                
            case 'FRTAIRTAND':
                value = value.FRTAIRTAND;
                if(value === 0){
                    return(
                        <label className="btnn-gr">정상</label>
                    )
                } else {
                    return (
                        <label className="btnn-re">저공압</label>
                    )
                }
                                                                
            case 'BATTSTA':
                value = value.BATTSTA;
                if(value === 0){
                    return(
                        <label className="btnn-bl">충전중</label>
                    )
                } else {
                    return (
                        <label className="btnn-gr">미발전</label>
                    )
                }
                                                                                
            case 'ABSOPER':
                value = value.ABSOPER;
                if(value === 0){
                    return(
                        <label className="btnn-re">미작동</label>
                    )
                } else {
                    return (
                        <label className="btnn-gr">작동</label>
                    )
                }                                                                  
            case 'ABSYAJI':
                value = value.ABSYAJI;
                if(value === 0){
                    return(
                        <label className="btnn-bl">해제</label>
                    )
                } else {
                    return (
                        <label className="btnn-bl">설정</label>
                    )
                }

            case 'ABSWARNING':
                value = value.ABSWARNING;
                if(value === 0){
                    return(
                        <label className="btnn-re">OFF</label>
                    )
                } else {
                    return (
                        <label className="btnn-gr">ON</label>
                    )
                }
                
            case '_1LOCK':
                value = value._1LOCK;
                if(value === 0){
                    return(
                        <label className="btnn-bl">해제</label>
                    )
                } else {
                    return (
                        <label className="btnn-bl">설정</label>
                    )
                }
                                
            case '_2LOCK':
                value = value._2LOCK;
                if(value === 0){
                    return(
                        <label className="btnn-bl">해제</label>
                    )
                } else {
                    return (
                        <label className="btnn-bl">설정</label>
                    )
                }
                                                
            case 'DMOTION':
                value = value._2LOCK;
                if(value === 0){
                    return(
                        <label className="btnn-bl">해제</label>
                    )
                } else {
                    return (
                        <label className="btnn-bl">잠금</label>
                    )
                }
                                                                
            case '_3LOCK':
                value = value._3LOCK;
                if(value === 0){
                    return(
                        <label className="btnn-bl">해제</label>
                    )
                } else {
                    return (
                        <label className="btnn-bl">설정</label>
                    )
                }
                                                                                
            case '_4LOCK':
                value = value._4LOCK;
                if(value === 0){
                    return(
                        <label className="btnn-bl">해제</label>
                    )
                } else {
                    return (
                        <label className="btnn-bl">설정</label>
                    )
                }

            case 'SHIFTMODE':
                value = value.SHIFTMODE;
                if(value === 0){
                    return(
                        <label className="btnn-bl">중립</label>
                    )
                } else if(value === 1){
                    return(
                        <label className="btnn-bl">저속</label>
                    )
                } else if(value === 2){
                    return (
                        <label className="btnn-bl">고속</label>
                    )
                } else {
                    return (
                        <label className="btnn-re">비정상</label>
                    )
                }
                                                                                
            case 'LOWSWITCH':
                value = value.LOWSWITCH;
                if(value === 0){
                    return(
                        <label className="btnn-bl">해제</label>
                    )
                } else {
                    return (
                        <label className="btnn-bl">설정</label>
                    )
                }

            case 'NORSWITCH':
                value = value.NORSWITCH;
                if(value === 0){
                    return(
                        <label className="btnn-bl">해제</label>
                    )
                } else {
                    return (
                        <label className="btnn-bl">설정</label>
                    )
                }
                
            case 'HIGHSWITCH':
                value = value.HIGHSWITCH;
                if(value === 0){
                    return(
                        <label className="btnn-bl">해제</label>
                    )
                } else {
                    return (
                        <label className="btnn-bl">설정</label>
                    )
                }

            case 'PNEUMATIC':
                value = value.PNEUMATIC;
                if(value === 0){
                    return(
                        <label className="btnn-gr">정상</label>
                    )
                } else {
                    return (
                        <label className="btnn-re">이상</label>
                    )
                }
                
            case '_8BY8':
                value = value._8BY8;
                if(value === 0){
                    return(
                        <label className="btnn-ye">8X4</label>
                    )
                } else if(value === 1){
                    return(
                        <label className="btnn-ye">8X6</label>
                    )
                } else if(value === 2){
                    return (
                        <label className="btnn-ye">8X8</label>
                    )
                } else if(value === 3){
                    return(
                        <label className="btnn-bl">접안</label>
                    )
                } else if(value === 4) {
                    return(
                        <label className="btnn-bl">수상</label>
                    )
                } else{
                    return(
                        <label className="btnn-re">비정상</label>
                    )
                }

            case '_6BY6':
                value = value._6BY6;
                if(value === 0){
                    return(
                        <label className="btnn-ye">6X4</label>
                    )
                } else if(value === 1){
                    return(
                        <label className="btnn-ye">6X6</label>
                    )
                } else if(value === 2){
                    return(
                        <label className="btnn-bl">견인</label>
                    )
                } else {
                    return (
                        <label className="btnn-re">비정상</label>
                    )
                }

            case '_1WHEEL':
                value = value._1WHEEL;
                if(value === 0){
                    return(
                        <label className="btnn-gr">연결</label>
                    )
                } else {
                    return (
                        <label className="btnn-re">차단</label>
                    )
                }
                
            case '_2WHEEL':
                value = value._2WHEEL;
                if(value === 0){
                    return(
                        <label className="btnn-gr">연결</label>
                    )
                } else {
                    return (
                        <label className="btnn-re">차단</label>
                    )
                }
                                            
            case '_3WHEEL':
                value = value._3WHEEL;
                if(value === 0){
                    return(
                        <label className="btnn-gr">연결</label>
                    )
                } else {
                    return (
                        <label className="btnn-re">차단</label>
                    )
                }
                                            
            case '_4WHEEL':
                value = value._4WHEEL;
                if(value === 0){
                    return(
                        <label className="btnn-gr">연결</label>
                    )
                } else {
                    return (
                        <label className="btnn-re">차단</label>
                    )
                }
                                                                    
            case 'PROMOTEWATER':
                value = value.PROMOTEWATER;
                if(value === 0){
                    return(
                        <label className="btnn-bl">해제</label>
                    )
                } else {
                    return (
                        <label className="btnn-bl">전달</label>
                    )
                }
                                                                                            
            case 'LOCKRELEASE':
                value = value.LOCKRELEASE;
                if(value === 0){
                    return(
                        <label className="btnn-bl">해제</label>
                    )
                } else {
                    return (
                        <label className="btnn-bl">자동풀림</label>
                    )
                }

            case 'LOWOILQTY':
                value = value.LOWOILQTY;
                if(value === 0){
                    return(
                        <label className="btnn-gr">정상</label>
                    )
                } else {
                    return (
                        <label className="btnn-re">고갈</label>
                    )
                }

            case 'LOWOILFILTER':
                value = value.LOWOILFILTER;
                if(value === 0){
                    return(
                        <label className="btnn-gr">정상</label>
                    )
                } else {
                    return (
                        <label className="btnn-re">막힘</label>
                    )
                }
                
            case 'WINCHCLUTCH':
                value = value.WINCHCLUTCH;
                if(value === 0){
                    return(
                        <label className="btnn-bl">해제</label>
                    )
                } else {
                    return (
                        <label className="btnn-bl">작동</label>
                    )
                }
                
            case 'BACKDOOROPEN':
                value = value.BACKDOOROPEN;
                if(value === 0){
                    return(
                        <label className="btnn-bl">해제</label>
                    )
                } else {
                    return (
                        <label className="btnn-bl">작동</label>
                    )
                }
                                        
            case 'OVERPRSEQP':
                value = value.OVERPRSEQP;
                if(value === 0){
                    return(
                        <label className="btnn-re">미작동</label>
                    )
                } else {
                    return (
                        <label className="btnn-gr">작동</label>
                    )
                }
            
            default:
                value = value.PBIT;
                return(
                    <label className="btnn">{value}</label>
                )

        }
    }

    beforeStart(){  //데이터가 들어오기전 센서만 선택되었을때 null값 세팅
        return(
            <label className="btnn">-</label>
        );
    }
    
    render() {
        return (
            <div>
            {
                this.state.categoricSensor.map((el, idx) => {
                    let id=el+"_"+idx;
                    return(
                        <div id={id} key={el} className="categoricSensorInfo">
                            <label>{this.props.data2[idx]}</label>
                            {
                                this.props.chartData.length === 0
                                ? this.beforeStart()
                                : this.categoric({el})//0이 아닐떄? 진행중이거나 그래프가 끝났을떄
                                
                            }
                        </div>
                    )
                })
            }
            </div>
        );
    }
}


export default connect(null, {
})(Categoric);
