import React, { Component } from "react";
import "./tableByTrouble.css";
import monitorDiagnostDataService from "../../../services/analysis/monitordiagnost.service";
import moment from "moment/moment";
import { tr } from "date-fns/locale";

export default class TableByTrouble extends Component {
    constructor(props) {
        super(props);
        
        this.tableMaker = this.tableMaker.bind(this);
        this.dividePredict = this.dividePredict.bind(this);
        this.confirmThreshold = this.confirmThreshold.bind(this);
        this.trClick = this.trClick.bind(this);
        this.setButtonStyle = this.setButtonStyle.bind(this);
        this.state = {
            troubleListForChart: [],
            isRowClicked : false,
            clickedIndex : "",
            thresholdList : [],
            clickedTr : null,
        };
    }

    componentDidMount(){
        monitorDiagnostDataService.getThreshold()
        .then((response) => {
            this.setState({
                thresholdList : response.data
            });
        })
        .catch((e) => {
        console.log(e);
        });
    }

    componentDidUpdate(prevProps){
        if(this.props.infoData !== prevProps.infoData){
            this.setState({
                isRowClicked : false,
            });
        }
    }

    confirmThreshold(value,name){
        let result = '';
        this.state.thresholdList.forEach((e)=>{
            if(name.includes(e.snsrid)){
                if(parseFloat(e.max) < parseFloat(value)){
                    result = 'over';
                }else if(parseFloat(e.min) > parseFloat(value)){
                    result = 'over';
                }
            }
        });
        return result;
    }

    trClick(res, idx){       //row클릭시 실행하는 함수 
        //this.props.load(true);
        
        //전체 테이블의 선택된 클래스 없애기, 클릭된 tr에 클래스 붙이기

        const trElement = document.getElementById('tr' + idx);
        //console.log(this.state.clickedTr);
        if(this.state.clickedTr !== null){
            this.state.clickedTr.classList.remove("highlight");
            trElement.classList.add("highlight");
        }else{
            trElement.classList.add("highlight");
        }

        const tdEList = trElement.getElementsByTagName("td");

        let overList = [];

        for(let i = 0; i < tdEList.length; i++){
            if(tdEList[i].classList.value !== ''){
                if(tdEList[i].classList.value.includes("over")){
                    overList.push(tdEList[i].classList.value.split(" ")[0]);
                }
            }
        }

        let check = true;
        if(res.time === this.state.clickedIndex){
            check = false;
            this.props.func(null, null);
            trElement.classList.remove("highlight");
        }else{
            if(overList.length < 1){
                check = false;
                this.props.func(null, null);
            }else{
                this.props.func(true,overList);
            }
        }
        this.setState({
            isRowClicked : check,
            clickedIndex : res.time,
            clickedTr : trElement
        })
    }

    setButtonStyle(param){
        if(param === "0"){
            return 'success';
        }else if(param === "1"){
            return 'danger';
        }else{
            return 'secondary';
        }
    }
    tableMaker(){
        switch(this.props.infoData.part){
            case "BEARING":
                return(
                    <div className="horizonbar">
                    <div 
                    id={`table${this.state.isRowClicked? 'horiver': 'hori'}`}
                    >
                            <table>
                                <thead>
                                    <tr >
                                        <td>좌/외륜</td>
                                        <td>좌/내륜</td>
                                        <td>좌/볼</td>
                                        <td>좌/리테이너</td>
                                        <td>우/외륜</td>
                                        <td>우/내륜</td>
                                        <td>우/볼</td>
                                        <td>우/리테이너</td>
                                        <td>차량이름</td>
                                        <td>시점종합</td>
                                        <td>time</td>
                                        <td>W_RPM</td>
                                        <td>L_B_V_OverallRMS</td>
                                        <td>L_B_V_1X</td>
                                        <td>L_B_V_6912BPFO</td>
                                        <td>L_B_V_6912BPFI</td>
                                        <td>L_B_V_6912BSF</td>
                                        <td>L_B_V_6912FTF</td>
                                        <td>L_B_V_32924BPFO</td>
                                        <td>L_B_V_32924BPFI</td>
                                        <td>L_B_V_32924BSF</td>
                                        <td>L_B_V_32924FTF</td>
                                        <td>L_B_V_32922BPFO</td>
                                        <td>L_B_V_32922BPFI</td>
                                        <td>L_B_V_32922BSF</td>
                                        <td>L_B_V_32922FTF</td>
                                        <td>L_B_V_Crestfactor</td>
                                        <td>L_B_V_Demodulation</td>
                                        <td>L_B_S_Fault1</td>
                                        <td>L_B_S_Fault2</td>
                                        <td>L_B_T_Temperature</td>
                                        <td>R_B_V_OverallRMS</td>
                                        <td>R_B_V_1X</td>
                                        <td>R_B_V_6912BPFO</td>
                                        <td>R_B_V_6912BPFI</td>
                                        <td>R_B_V_6912BSF</td>
                                        <td>R_B_V_6912FTF</td>
                                        <td>R_B_V_32924BPFO</td>
                                        <td>R_B_V_32924BPFI</td>
                                        <td>R_B_V_32924BSF</td>
                                        <td>R_B_V_32924FTF</td>
                                        <td>R_B_V_32922BPFO</td>
                                        <td>R_B_V_32922BPFI</td>
                                        <td>R_B_V_32922BSF</td>
                                        <td>R_B_V_32922FTF</td>
                                        <td>R_B_V_Crestfactor</td>
                                        <td>R_B_V_Demodulation</td>
                                        <td>R_B_S_Fault1</td>
                                        <td>R_B_S_Fault2</td>
                                        <td>R_B_T_Temperature</td>
                                        <td>AC_h</td>
                                        <td>AC_v</td>
                                        <td>AC_a</td>
                                        <td>FILENM</td>
                                    </tr>
                                </thead>
                                <tbody>
                                {
                                this.props.data.map((el, idx) => {
                                    const datetime = moment(el.date).format('YYYY-MM-DD hh:mm:ss');

                                    return(
                                    <tr key={idx} onClick={() => {
                                        this.trClick(el,idx);
                                    }} id={"tr" + idx}>
                                        <td>
                                            <button className={`btn btn-${this.setButtonStyle(el.ai_LBPFO)}`}>
                                                {this.dividePredict(el.ai_LBPFO)}
                                            </button>
                                        </td>
                                        <td>
                                            <button className={`btn btn-${this.setButtonStyle(el.ai_LBPFI)}`}>
                                                {this.dividePredict(el.ai_LBPFI)}
                                            </button>
                                        </td>
                                        <td>
                                            <button className={`btn btn-${this.setButtonStyle(el.ai_LBSF)}`}>
                                                {this.dividePredict(el.ai_LBSF)}
                                            </button>
                                        </td>
                                        <td>
                                            <button className={`btn btn-${this.setButtonStyle(el.ai_LFTF)}`}>
                                                {this.dividePredict(el.ai_LFTF)}
                                            </button>
                                        </td>
                                        <td>
                                            <button className={`btn btn-${this.setButtonStyle(el.ai_RBPFO)}`}>
                                                {this.dividePredict(el.ai_RBPFO)}
                                            </button>
                                        </td>
                                        <td>
                                            <button className={`btn btn-${this.setButtonStyle(el.ai_RBPFI)}`}>
                                                {this.dividePredict(el.ai_RBPFI)}
                                            </button>
                                        </td>
                                        <td>
                                            <button className={`btn btn-${this.setButtonStyle(el.ai_RBSF)}`}>
                                                {this.dividePredict(el.ai_RBSF)}
                                            </button>
                                        </td>
                                        <td>
                                            <button className={`btn btn-${this.setButtonStyle(el.ai_RFTF)}`}>
                                                {this.dividePredict(el.ai_RFTF)}
                                            </button>
                                        </td>
                                        <td>{el.sdanm}</td>
                                        <td>{datetime}</td>
                                        <td>{el.time}</td>
                                        <td className={`W_RPM ${this.confirmThreshold(el.w_RPM, "W_RPM")}`}>{el.w_RPM}</td>
                                        <td className={`L_B_V_OverallRMS ${this.confirmThreshold(el.l_B_V_OverallRMS, "L_B_V_OverallRMS")}`}>{el.l_B_V_OverallRMS}</td>
                                        <td className={`L_B_V_1X ${this.confirmThreshold(el.l_B_V_1X, "L_B_V_1X")}`}>{el.l_B_V_1X}</td>
                                        <td className={`L_B_V_6912BPFO ${this.confirmThreshold(el.l_B_V_6912BPFO, "L_B_V_6912BPFO")}`}>{el.l_B_V_6912BPFO}</td>
                                        <td className={`L_B_V_6912BPFI ${this.confirmThreshold(el.l_B_V_6912BPFI, "L_B_V_6912BPFI")}`}>{el.l_B_V_6912BPFI}</td>
                                        <td className={`L_B_V_6912BSF ${this.confirmThreshold(el.l_B_V_6912BSF, "L_B_V_6912BSF")}`}>{el.l_B_V_6912BSF}</td>
                                        <td className={`L_B_V_6912FTF ${this.confirmThreshold(el.l_B_V_6912FTF, "L_B_V_6912FTF")}`}>{el.l_B_V_6912FTF}</td>
                                        <td className={`L_B_V_32924BPFO ${this.confirmThreshold(el.l_B_V_32924BPFO, "L_B_V_32924BPFO")}`}>{el.l_B_V_32924BPFO}</td>
                                        <td className={`L_B_V_32924BPFI ${this.confirmThreshold(el.l_B_V_32924BPFI, "L_B_V_32924BPFI")}`}>{el.l_B_V_32924BPFI}</td>
                                        <td className={`L_B_V_32924BSF ${this.confirmThreshold(el.l_B_V_32924BSF, "L_B_V_32924BSF")}`}>{el.l_B_V_32924BSF}</td>
                                        <td className={`L_B_V_32924FTF ${this.confirmThreshold(el.l_B_V_32924FTF, "L_B_V_32924FTF")}`}>{el.l_B_V_32924FTF}</td>
                                        <td className={`L_B_V_32922BPFO ${this.confirmThreshold(el.l_B_V_32922BPFO, "L_B_V_32922BPFO")}`}>{el.l_B_V_32922BPFO}</td>
                                        <td className={`L_B_V_32922BPFI ${this.confirmThreshold(el.l_B_V_32922BPFI, "L_B_V_32922BPFI")}`}>{el.l_B_V_32922BPFI}</td>
                                        <td className={`L_B_V_32922BSF ${this.confirmThreshold(el.l_B_V_32922BSF, "L_B_V_32922BSF")}`}>{el.l_B_V_32922BSF}</td>
                                        <td className={`L_B_V_32922FTF ${this.confirmThreshold(el.l_B_V_32922FTF, "L_B_V_32922FTF")}`}>{el.l_B_V_32922FTF}</td>
                                        <td className={`L_B_V_Crestfactor ${this.confirmThreshold(el.l_B_V_Crestfactor, "L_B_V_Crestfactor")}`}>{el.l_B_V_Crestfactor}</td>
                                        <td className={`L_B_V_Demodulation ${this.confirmThreshold(el.l_B_V_Demodulation, "L_B_V_Demodulation")}`}>{el.l_B_V_Demodulation}</td>
                                        <td className={`L_B_S_Fault1 ${this.confirmThreshold(el.l_B_S_Fault1, "L_B_S_Fault1")}`}>{el.l_B_S_Fault1}</td>
                                        <td className={`L_B_S_Fault2 ${this.confirmThreshold(el.l_B_S_Fault2, "L_B_S_Fault2")}`}>{el.l_B_S_Fault2}</td>
                                        <td className={`L_B_T_Temperature ${this.confirmThreshold(el.l_B_T_Temperature, "L_B_T_Temperature")}`}>{el.l_B_T_Temperature}</td>
                                        <td className={`R_B_V_OverallRMS ${this.confirmThreshold(el.r_B_V_OverallRMS, "R_B_V_OverallRMS")}`}>{el.r_B_V_OverallRMS}</td>
                                        <td className={`R_B_V_1X ${this.confirmThreshold(el.r_B_V_1X, "R_B_V_1X")}`}>{el.r_B_V_1X}</td>
                                        <td className={`R_B_V_6912BPFO ${this.confirmThreshold(el.r_B_V_6912BPFO, "R_B_V_6912BPFO")}`}>{el.r_B_V_6912BPFO}</td>
                                        <td className={`R_B_V_6912BPFI ${this.confirmThreshold(el.r_B_V_6912BPFI, "R_B_V_6912BPFI")}`}>{el.r_B_V_6912BPFI}</td>
                                        <td className={`R_B_V_6912BSF ${this.confirmThreshold(el.r_B_V_6912BSF, "R_B_V_6912BSF")}`}>{el.r_B_V_6912BSF}</td>
                                        <td className={`R_B_V_6912FTF ${this.confirmThreshold(el.r_B_V_6912FTF, "R_B_V_6912FTF")}`}>{el.r_B_V_6912FTF}</td>
                                        <td className={`R_B_V_32924BPFO ${this.confirmThreshold(el.r_B_V_32924BPFO, "R_B_V_32924BPFO")}`}>{el.r_B_V_32924BPFO}</td>
                                        <td className={`R_B_V_32924BPFI ${this.confirmThreshold(el.r_B_V_32924BPFI, "R_B_V_32924BPFI")}`}>{el.r_B_V_32924BPFI}</td>
                                        <td className={`R_B_V_32924BSF ${this.confirmThreshold(el.r_B_V_32924BSF, "R_B_V_32924BSF")}`}>{el.r_B_V_32924BSF}</td>
                                        <td className={`R_B_V_32924FTF ${this.confirmThreshold(el.r_B_V_32924FTF, "R_B_V_32924FTF")}`}>{el.r_B_V_32924FTF}</td>
                                        <td className={`R_B_V_32922BPFO ${this.confirmThreshold(el.r_B_V_32922BPFO, "R_B_V_32922BPFO")}`}>{el.r_B_V_32922BPFO}</td>
                                        <td className={`R_B_V_32922BPFI ${this.confirmThreshold(el.r_B_V_32922BPFI, "R_B_V_32922BPFI")}`}>{el.r_B_V_32922BPFI}</td>
                                        <td className={`R_B_V_32922BSF ${this.confirmThreshold(el.r_B_V_32922BSF, "R_B_V_32922BSF")}`}>{el.r_B_V_32922BSF}</td>
                                        <td className={`R_B_V_32922FTF ${this.confirmThreshold(el.r_B_V_32922FTF, "R_B_V_32922FTF")}`}>{el.r_B_V_32922FTF}</td>
                                        <td className={`R_B_V_Crestfactor ${this.confirmThreshold(el.r_B_V_Crestfactor, "R_B_V_Crestfactor")}`}>{el.r_B_V_Crestfactor}</td>
                                        <td className={`R_B_V_Demodulation ${this.confirmThreshold(el.r_B_V_Demodulation, "R_B_V_Demodulation")}`}>{el.r_B_V_Demodulation}</td>
                                        <td className={`R_B_S_Fault1 ${this.confirmThreshold(el.r_B_S_Fault1, "R_B_S_Fault1")}`}>{el.r_B_S_Fault1}</td>
                                        <td className={`R_B_S_Fault2 ${this.confirmThreshold(el.r_B_S_Fault2, "R_B_S_Fault2")}`}>{el.r_B_S_Fault2}</td>
                                        <td className={`R_B_T_Temperature ${this.confirmThreshold(el.r_B_T_Temperature, "R_B_T_Temperature")}`}>{el.r_B_T_Temperature}</td>
                                        <td className={`AC_h ${this.confirmThreshold(el.ac_h, "AC_h")}`}>{el.ac_h}</td>
                                        <td className={`AC_v ${this.confirmThreshold(el.ac_v, "AC_v")}`}>{el.ac_v}</td>
                                        <td className={`AC_a ${this.confirmThreshold(el.ac_a, "AC_a")}`}>{el.ac_a}</td>
                                        <td>{el.filenm}</td>
                                    </tr>
                                    )
                                })
                                }
                                </tbody>
                            </table>
                        </div>
                  </div>
                );
        
              case "ENGINE":
                return(
                    <div className="horizonbar">
                        <div 
                        id={`table${this.state.isRowClicked? 'horiver': 'hori'}`}
                        >
                            <table>
                                <thead>
                                    <tr>
                                        <td>엔진윤활</td>
                                        <td>차량이름</td>
                                        <td>시점종합</td>
                                        <td>time</td>
                                        <td>W_RPM</td>
                                        <td>E_V_OverallRMS</td>
                                        <td>E_V_1-2X</td>
                                        <td>E_V_1X</td>
                                        <td>E_V_Crestfactor</td>
                                        <td>AC_h</td>
                                        <td>AC_v</td>
                                        <td>AC_a</td>
                                        <td>LA</td>
                                        <td>LO</td>
                                        <td>FILENM</td>
                                    </tr>
                        
                                </thead>
                                <tbody>
                                    {
                                    this.props.data.map((el, idx) => {
                                        const datetime = moment(el.date).format('YYYY-MM-DD hh:mm:ss');
                                    return(
                                        <tr key={idx} onClick={() => {
                                            this.trClick(el,idx);
                                        }} id={"tr" + idx}>
                                            <td>
                                                <button className={`btn btn-${this.setButtonStyle(el.ai_ENGINE)}`}>
                                                    {this.dividePredict(el.ai_ENGINE)}
                                                </button>
                                            </td>
                                            <td>{el.sdanm}</td>
                                            <td>{datetime}</td>
                                            <td>{el.time}</td>
                                            <td className={`W_RPM ${this.confirmThreshold(el.w_RPM, "W_RPM")}`}>{el.w_RPM}</td>
                                            <td className={`E_V_OverallRMS ${this.confirmThreshold(el.e_V_OverallRMS, "E_V_OverallRMS")}`}>{el.e_V_OverallRMS}</td>
                                            <td className={`E_V_1_2X ${this.confirmThreshold(el.e_V_1_2X, "E_V_1_2X")}`}>{el.e_V_1_2X}</td>
                                            <td className={`E_V_1X ${this.confirmThreshold(el.e_V_1X, "E_V_1X")}`}>{el.e_V_1X}</td>
                                            <td className={`E_V_Crestfactor ${this.confirmThreshold(el.e_V_Crestfactor, "E_V_Crestfactor")}`}>{el.e_V_Crestfactor}</td>
                                            <td className={`AC_h ${this.confirmThreshold(el.ac_h, "AC_h")}`}>{el.ac_h}</td>
                                            <td className={`AC_v ${this.confirmThreshold(el.ac_v, "AC_v")}`}>{el.ac_v}</td>
                                            <td className={`AC_a ${this.confirmThreshold(el.ac_a, "AC_a")}`}>{el.ac_a}</td>
                                            <td className={`La ${this.confirmThreshold(el.g_V_OverallRMS, "La")}`}>{el.la}</td>
                                            <td className={`Lo ${this.confirmThreshold(el.lo, "Lo")}`}>{el.lo}</td>
                                            <td>{el.filenm}</td>
                                        </tr>
                                    )
                                    })
                                    }
                                </tbody>
                            </table>
                        </div>
                    </div>
                );
        
              case 'GEARBOX':
                return(
                    <div className="horizonbar">
                        <div 
                        id={`table${this.state.isRowClicked? 'horiver': 'hori'}`}
                        >
                            <table>
                                <thead>
                                    <tr>
                                        <td>감속기</td>
                                        <td>차량이름</td>
                                        <td>시점종합</td>
                                        <td>time</td>
                                        <td>W_RPM</td>
                                        <td>G_V_OverallRMS</td>
                                        <td>G_V_Wheel1X</td>
                                        <td>G_V_Wheel2X</td>
                                        <td>G_V_Pinion1X</td>
                                        <td>G_V_Pinion2X</td>
                                        <td>G_V_GMF1X</td>
                                        <td>G_V_GMF2X</td>
                                        <td>AC_h</td>
                                        <td>AC_v</td>
                                        <td>AC_a</td>
                                        <td>FILENM</td>
                                        
                                    </tr>
                        
                                </thead>
                                <tbody>
                                    {
                                    this.props.data.map((el, idx) => {
                                        const datetime = moment(el.date).format('YYYY-MM-DD hh:mm:ss');

                                    return(
                                        <tr key={idx} onClick={() => {
                                            this.trClick(el,idx);
                                        }} id={"tr" + idx}>
                                            <td>
                                                <button className={`btn btn-${this.setButtonStyle(el.ai_GEAR)}`}>
                                                    {this.dividePredict(el.ai_GEAR)}
                                                </button>
                                            </td>
                                            <td>{el.sdanm}</td>
                                            <td>{datetime}</td>
                                            <td>{el.time}</td>
                                            <td className={`W_RPM ${this.confirmThreshold(el.w_RPM, "W_RPM")}`}>{el.w_RPM}</td>
                                            <td className={`G_V_OverallRMS ${this.confirmThreshold(el.g_V_OverallRMS, "G_V_OverallRMS")}`}>{el.g_V_OverallRMS}</td>
                                            <td className={`G_V_Wheel1X ${this.confirmThreshold(el.g_V_Wheel1X, "G_V_Wheel1X")}`}>{el.g_V_Wheel1X}</td>
                                            <td className={`G_V_Wheel2X ${this.confirmThreshold(el.g_V_Wheel2X, "G_V_Wheel2X")}`}>{el.g_V_Wheel2X}</td>
                                            <td className={`G_V_Pinion1X ${this.confirmThreshold(el.g_V_Pinion1X, "G_V_Pinion1X")}`}>{el.g_V_Pinion1X}</td>
                                            <td className={`G_V_Pinion2X ${this.confirmThreshold(el.g_V_Pinion2X, "G_V_Pinion2X")}`}>{el.g_V_Pinion2X}</td>
                                            <td className={`G_V_GMF1X ${this.confirmThreshold(el.g_V_GMF1X, "G_V_GMF1X")}`}>{el.g_V_GMF1X}</td>
                                            <td className={`G_V_GMF2X ${this.confirmThreshold(el.g_V_GMF2X, "G_V_GMF2X")}`}>{el.g_V_GMF2X}</td>
                                            <td className={`AC_h ${this.confirmThreshold(el.ac_h, "AC_h")}`}>{el.ac_h}</td>
                                            <td className={`AC_v ${this.confirmThreshold(el.ac_v, "AC_v")}`}>{el.ac_v}</td>
                                            <td className={`AC_a ${this.confirmThreshold(el.ac_a, "AC_a")}`}>{el.ac_a}</td>
                                            <td>{el.filenm}</td>
                                        </tr>
                                    )
                                    })
                                    }
                                </tbody>
                            </table>
                        </div>
                    </div>
                );
        
              case 'WHEEL': 
                return(
                    <div className="horizonbar">
                        <div 
                        id={`table${this.state.isRowClicked? 'horiver': 'hori'}`}
                        >
                        <table>
                            <thead>
                                <tr>
                                    <td>좌측</td>
                                    <td>우측</td>
                                    <td>차량이름</td>
                                    <td>시점종합</td>
                                    <td>time</td>
                                    <td>W_RPM</td>
                                    <td>L_W_V_2X</td>
                                    <td>L_W_V_3X</td>
                                    <td>L_W_S_Fault3</td>
                                    <td>R_W_V_2X</td>
                                    <td>R_W_V_3X</td>
                                    <td>R_W_S_Fault3</td>
                                    <td>AC_h</td>
                                    <td>AC_v</td>
                                    <td>AC_a</td>
                                    <td>FILENM</td>
                                </tr>
                            </thead>
                            <tbody>
                                {
                                this.props.data.map((el, idx) => {
                                    const datetime = moment(el.date).format('YYYY-MM-DD hh:mm:ss');
                                return(
                                    <tr key={idx} onClick={() => {
                                        this.trClick(el, idx);
                                    }} id={"tr" + idx}>
                                        <td>
                                            <button className={`btn btn-${this.setButtonStyle(el.ai_LW)}`}>
                                                {this.dividePredict(el.ai_LW)}
                                            </button>
                                        </td>
                                        <td>
                                            <button className={`btn btn-${this.setButtonStyle(el.ai_RW)}`}>
                                                {this.dividePredict(el.ai_RW)}
                                            </button>
                                        </td>
                                        <td>{el.sdanm}</td>
                                        <td>{datetime}</td>
                                        <td>{el.time}</td>
                                        <td>{el.w_RPM}</td>
                                        <td className={`L_W_V_2X ${this.confirmThreshold(el.l_W_V_2X, "L_W_V_2X")}`}>{el.l_W_V_2X}</td>
                                        <td className={`L_W_V_3X ${this.confirmThreshold(el.l_W_V_3X, "L_W_V_3X")}`}>{el.l_W_V_3X}</td>
                                        <td className={`L_W_V_Fault3 ${this.confirmThreshold(el.l_W_S_Fault3, "L_W_V_Fault3")}`}>{el.l_W_S_Fault3}</td>
                                        <td className={`R_W_V_2X ${this.confirmThreshold(el.r_W_V_2X, "R_W_V_2X")}`}>{el.r_W_V_2X}</td>
                                        <td className={`R_W_V_3X ${this.confirmThreshold(el.r_W_V_3X, "R_W_V_3X")}`}>{el.r_W_V_3X}</td>
                                        <td className={`R_W_V_Fault3 ${this.confirmThreshold(el.r_W_S_Fault3, "R_W_V_Fault3")}`}>{el.r_W_S_Fault3}</td>
                                        <td className={`AC_h ${this.confirmThreshold(el.ac_h, "AC_h")}`}>{el.ac_h}</td>
                                        <td className={`AC_v ${this.confirmThreshold(el.ac_v, "AC_v")}`}>{el.ac_v}</td>
                                        <td className={`AC_a ${this.confirmThreshold(el.ac_a, "AC_a")}`}>{el.ac_a}</td>
                                        <td>{el.filenm}</td>
                                    </tr>
                                )
                                })
                                }
                            </tbody>
                        </table>
                        </div>
                    </div>
                );
        
                case "SIMULATION":
                return(
                    <div className="horizonbar">
                    <div 
                    id={`table${this.state.isRowClicked? 'horiver': 'hori'}`}
                    >
                            <table>
                                <thead>
                                    <tr >
                                        <td>좌/외륜</td>
                                        <td>좌/내륜</td>
                                        <td>좌/볼</td>
                                        <td>좌/리테이너</td>
                                        <td>우/외륜</td>
                                        <td>우/내륜</td>
                                        <td>우/볼</td>
                                        <td>우/리테이너</td>
                                        <td>차량이름</td>
                                        <td>시점종합</td>
                                        <td>time</td>
                                        <td>W_RPM</td>
                                        <td>L_B_V_OverallRMS</td>
                                        <td>L_B_V_1X</td>
                                        <td>L_B_V_6912BPFO</td>
                                        <td>L_B_V_6912BPFI</td>
                                        <td>L_B_V_6912BSF</td>
                                        <td>L_B_V_6912FTF</td>
                                        <td>L_B_V_32924BPFO</td>
                                        <td>L_B_V_32924BPFI</td>
                                        <td>L_B_V_32924BSF</td>
                                        <td>L_B_V_32924FTF</td>
                                        <td>L_B_V_32922BPFO</td>
                                        <td>L_B_V_32922BPFI</td>
                                        <td>L_B_V_32922BSF</td>
                                        <td>L_B_V_32922FTF</td>
                                        <td>L_B_V_Crestfactor</td>
                                        <td>L_B_V_Demodulation</td>
                                        <td>L_B_S_Fault1</td>
                                        <td>L_B_S_Fault2</td>
                                        <td>L_B_T_Temperature</td>
                                        <td>R_B_V_OverallRMS</td>
                                        <td>R_B_V_1X</td>
                                        <td>R_B_V_6912BPFO</td>
                                        <td>R_B_V_6912BPFI</td>
                                        <td>R_B_V_6912BSF</td>
                                        <td>R_B_V_6912FTF</td>
                                        <td>R_B_V_32924BPFO</td>
                                        <td>R_B_V_32924BPFI</td>
                                        <td>R_B_V_32924BSF</td>
                                        <td>R_B_V_32924FTF</td>
                                        <td>R_B_V_32922BPFO</td>
                                        <td>R_B_V_32922BPFI</td>
                                        <td>R_B_V_32922BSF</td>
                                        <td>R_B_V_32922FTF</td>
                                        <td>R_B_V_Crestfactor</td>
                                        <td>R_B_V_Demodulation</td>
                                        <td>R_B_S_Fault1</td>
                                        <td>R_B_S_Fault2</td>
                                        <td>R_B_T_Temperature</td>
                                        <td>FILENM</td>
                                    </tr>
                                </thead>
                                <tbody>
                                {
                                this.props.data.map((el, idx) => {
                                    const datetime = moment(el.date).format('YYYY-MM-DD hh:mm:ss');
                                    return(
                                    <tr key={idx} onClick={() => {
                                        this.trClick(el,idx);
                                    }} id={"tr" + idx}>
                                        <td>
                                            <button className={`btn btn-${this.setButtonStyle(el.ai_LBPFO)}`}>
                                                {this.dividePredict(el.ai_LBPFO)}
                                            </button>
                                        </td>
                                        <td>
                                            <button className={`btn btn-${this.setButtonStyle(el.ai_LBPFI)}`}>
                                                {this.dividePredict(el.ai_LBPFI)}
                                            </button>
                                        </td>
                                        <td>
                                            <button className={`btn btn-${this.setButtonStyle(el.ai_LBSF)}`}>
                                                {this.dividePredict(el.ai_LBSF)}
                                            </button>
                                        </td>
                                        <td>
                                            <button className={`btn btn-${this.setButtonStyle(el.ai_LFTF)}`}>
                                                {this.dividePredict(el.ai_LFTF)}
                                            </button>
                                        </td>
                                        <td>
                                            <button className={`btn btn-${this.setButtonStyle(el.ai_RBPFO)}`}>
                                                {this.dividePredict(el.ai_RBPFO)}
                                            </button>
                                        </td>
                                        <td>
                                            <button className={`btn btn-${this.setButtonStyle(el.ai_RBPFI)}`}>
                                                {this.dividePredict(el.ai_RBPFI)}
                                            </button>
                                        </td>
                                        <td>
                                            <button className={`btn btn-${this.setButtonStyle(el.ai_RBSF)}`}>
                                                {this.dividePredict(el.ai_RBSF)}
                                            </button>
                                        </td>
                                        <td>
                                            <button className={`btn btn-${this.setButtonStyle(el.ai_RFTF)}`}>
                                                {this.dividePredict(el.ai_RFTF)}
                                            </button>
                                        </td>
                                        <td>{el.sdaid}</td>
                                        <td>{datetime}</td>
                                        <td>{el.time}</td>
                                        <td className={`W_RPM ${this.confirmThreshold(el.w_RPM, "W_RPM")}`}>{el.w_RPM}</td>
                                        <td className={`L_B_V_OverallRMS ${this.confirmThreshold(el.l_B_V_OverallRMS, "L_B_V_OverallRMS")}`}>{el.l_B_V_OverallRMS}</td>
                                        <td className={`L_B_V_1X ${this.confirmThreshold(el.l_B_V_1X, "L_B_V_1X")}`}>{el.l_B_V_1X}</td>
                                        <td className={`L_B_V_6912BPFO ${this.confirmThreshold(el.l_B_V_6912BPFO, "L_B_V_6912BPFO")}`}>{el.l_B_V_6912BPFO}</td>
                                        <td className={`L_B_V_6912BPFI ${this.confirmThreshold(el.l_B_V_6912BPFI, "L_B_V_6912BPFI")}`}>{el.l_B_V_6912BPFI}</td>
                                        <td className={`L_B_V_6912BSF ${this.confirmThreshold(el.l_B_V_6912BSF, "L_B_V_6912BSF")}`}>{el.l_B_V_6912BSF}</td>
                                        <td className={`L_B_V_6912FTF ${this.confirmThreshold(el.l_B_V_6912FTF, "L_B_V_6912FTF")}`}>{el.l_B_V_6912FTF}</td>
                                        <td className={`L_B_V_32924BPFO ${this.confirmThreshold(el.l_B_V_32924BPFO, "L_B_V_32924BPFO")}`}>{el.l_B_V_32924BPFO}</td>
                                        <td className={`L_B_V_32924BPFI ${this.confirmThreshold(el.l_B_V_32924BPFI, "L_B_V_32924BPFI")}`}>{el.l_B_V_32924BPFI}</td>
                                        <td className={`L_B_V_32924BSF ${this.confirmThreshold(el.l_B_V_32924BSF, "L_B_V_32924BSF")}`}>{el.l_B_V_32924BSF}</td>
                                        <td className={`L_B_V_32924FTF ${this.confirmThreshold(el.l_B_V_32924FTF, "L_B_V_32924FTF")}`}>{el.l_B_V_32924FTF}</td>
                                        <td className={`L_B_V_32922BPFO ${this.confirmThreshold(el.l_B_V_32922BPFO, "L_B_V_32922BPFO")}`}>{el.l_B_V_32922BPFO}</td>
                                        <td className={`L_B_V_32922BPFI ${this.confirmThreshold(el.l_B_V_32922BPFI, "L_B_V_32922BPFI")}`}>{el.l_B_V_32922BPFI}</td>
                                        <td className={`L_B_V_32922BSF ${this.confirmThreshold(el.l_B_V_32922BSF, "L_B_V_32922BSF")}`}>{el.l_B_V_32922BSF}</td>
                                        <td className={`L_B_V_32922FTF ${this.confirmThreshold(el.l_B_V_32922FTF, "L_B_V_32922FTF")}`}>{el.l_B_V_32922FTF}</td>
                                        <td className={`L_B_V_Crestfactor ${this.confirmThreshold(el.l_B_V_Crestfactor, "L_B_V_Crestfactor")}`}>{el.l_B_V_Crestfactor}</td>
                                        <td className={`L_B_V_Demodulation ${this.confirmThreshold(el.l_B_V_Demodulation, "L_B_V_Demodulation")}`}>{el.l_B_V_Demodulation}</td>
                                        <td className={`L_B_S_Fault1 ${this.confirmThreshold(el.l_B_S_Fault1, "L_B_S_Fault1")}`}>{el.l_B_S_Fault1}</td>
                                        <td className={`L_B_S_Fault2 ${this.confirmThreshold(el.l_B_S_Fault2, "L_B_S_Fault2")}`}>{el.l_B_S_Fault2}</td>
                                        <td className={`L_B_T_Temperature ${this.confirmThreshold(el.l_B_T_Temperature, "L_B_T_Temperature")}`}>{el.l_B_T_Temperature}</td>
                                        <td className={`R_B_V_OverallRMS ${this.confirmThreshold(el.r_B_V_OverallRMS, "R_B_V_OverallRMS")}`}>{el.r_B_V_OverallRMS}</td>
                                        <td className={`R_B_V_1X ${this.confirmThreshold(el.r_B_V_1X, "R_B_V_1X")}`}>{el.r_B_V_1X}</td>
                                        <td className={`R_B_V_6912BPFO ${this.confirmThreshold(el.r_B_V_6912BPFO, "R_B_V_6912BPFO")}`}>{el.r_B_V_6912BPFO}</td>
                                        <td className={`R_B_V_6912BPFI ${this.confirmThreshold(el.r_B_V_6912BPFI, "R_B_V_6912BPFI")}`}>{el.r_B_V_6912BPFI}</td>
                                        <td className={`R_B_V_6912BSF ${this.confirmThreshold(el.r_B_V_6912BSF, "R_B_V_6912BSF")}`}>{el.r_B_V_6912BSF}</td>
                                        <td className={`R_B_V_6912FTF ${this.confirmThreshold(el.r_B_V_6912FTF, "R_B_V_6912FTF")}`}>{el.r_B_V_6912FTF}</td>
                                        <td className={`R_B_V_32924BPFO ${this.confirmThreshold(el.r_B_V_32924BPFO, "R_B_V_32924BPFO")}`}>{el.r_B_V_32924BPFO}</td>
                                        <td className={`R_B_V_32924BPFI ${this.confirmThreshold(el.r_B_V_32924BPFI, "R_B_V_32924BPFI")}`}>{el.r_B_V_32924BPFI}</td>
                                        <td className={`R_B_V_32924BSF ${this.confirmThreshold(el.r_B_V_32924BSF, "R_B_V_32924BSF")}`}>{el.r_B_V_32924BSF}</td>
                                        <td className={`R_B_V_32924FTF ${this.confirmThreshold(el.r_B_V_32924FTF, "R_B_V_32924FTF")}`}>{el.r_B_V_32924FTF}</td>
                                        <td className={`R_B_V_32922BPFO ${this.confirmThreshold(el.r_B_V_32922BPFO, "R_B_V_32922BPFO")}`}>{el.r_B_V_32922BPFO}</td>
                                        <td className={`R_B_V_32922BPFI ${this.confirmThreshold(el.r_B_V_32922BPFI, "R_B_V_32922BPFI")}`}>{el.r_B_V_32922BPFI}</td>
                                        <td className={`R_B_V_32922BSF ${this.confirmThreshold(el.r_B_V_32922BSF, "R_B_V_32922BSF")}`}>{el.r_B_V_32922BSF}</td>
                                        <td className={`R_B_V_32922FTF ${this.confirmThreshold(el.r_B_V_32922FTF, "R_B_V_32922FTF")}`}>{el.r_B_V_32922FTF}</td>
                                        <td className={`R_B_V_Crestfactor ${this.confirmThreshold(el.r_B_V_Crestfactor, "R_B_V_Crestfactor")}`}>{el.r_B_V_Crestfactor}</td>
                                        <td className={`R_B_V_Demodulation ${this.confirmThreshold(el.r_B_V_Demodulation, "R_B_V_Demodulation")}`}>{el.r_B_V_Demodulation}</td>
                                        <td className={`R_B_S_Fault1 ${this.confirmThreshold(el.r_B_S_Fault1, "R_B_S_Fault1")}`}>{el.r_B_S_Fault1}</td>
                                        <td className={`R_B_S_Fault2 ${this.confirmThreshold(el.r_B_S_Fault2, "R_B_S_Fault2")}`}>{el.r_B_S_Fault2}</td>
                                        <td className={`R_B_T_Temperature ${this.confirmThreshold(el.r_B_T_Temperature, "R_B_T_Temperature")}`}>{el.r_B_T_Temperature}</td>
                                        <td>{el.filenm}</td>
                                    </tr>
                                    )
                                })
                                }
                                </tbody>
                            </table>
                        </div>
                  </div>
                );                
            }
    }
    
    dividePredict(res) {
        if(res === "0"){
            return "양호";
        }else if(res === "1"){
            return "심각";
        }else{
            return "측정필요";
        }
    }

    render() {
        return (
            <>
                {
                    this.tableMaker()
                }                       
            </>
        )
    }
}

