import React, { Component } from "react";
import "./tableByTrouble.css";
// import monitorDiagnostDataService from "../../../services/monitoring/monitordiagnost.service";
import moment from "moment/moment";

export default class TableByRemaining extends Component {
    constructor(props) {
        super(props);
        this.tableMaker = this.tableMaker.bind(this);
        this.state = {

        };
    }

    tableMaker(){
        switch(this.props.partName){
            case "ber":
                return(
                    <div>
                        <div className="horizonbar">
                            <table>
                                <thead>
                                    <tr>
                                        <td>B_OverallRMS</td>
                                        <td>B_1X</td>
                                        <td>B_6912BPFO</td>
                                        <td>B_6912BPFI</td>
                                        <td>B_6912BSF</td>
                                        <td>B_6912FTF</td>
                                        <td>B_32924BPFO</td>
                                        <td>B_32924BPFI</td>
                                        <td>B_32924BSF</td>
                                        <td>B_32924FTF</td>
                                        <td>B_32922BPFO</td>
                                        <td>B_32922BPFI</td>
                                        <td>B_32922BSF</td>
                                        <td>B_32922FTF</td>
                                        <td>B_CrestFactor</td>
                                        <td>B_Demodulation</td>
                                        <td>B_Fault1</td>
                                        <td>B_Fault2</td>
                                        <td>B_Temperature</td>
                                        <td>Trip</td>
                                        <td>FILENM</td>
                                    </tr>
                                </thead>
                                <tbody>
                                {
                                this.props.data.map((el, idx) => {
                                    return(
                                        <tr key={idx}>
                                            <td>{el.b_OverallRMS}</td>
                                            <td>{el.b_1X}</td>
                                            <td>{el.b_6912BPFO}</td>
                                            <td>{el.b_6912BPFI}</td>
                                            <td>{el.b_6912BSF}</td>
                                            <td>{el.b_6912FTF}</td>
                                            <td>{el.b_32924BPFO}</td>
                                            <td>{el.b_32924BPFI}</td>
                                            <td>{el.b_32924BSF}</td>
                                            <td>{el.b_32924FTF}</td>
                                            <td>{el.b_32922BPFO}</td>
                                            <td>{el.b_32922BPFI}</td>
                                            <td>{el.b_32922BSF}</td>
                                            <td>{el.b_32922FTF}</td>
                                            <td>{el.b_CrestFactor}</td>
                                            <td>{el.b_Demodulation}</td>
                                            <td>{el.b_Fault1}</td>
                                            <td>{el.b_Fault2}</td>
                                            <td>{el.b_Temperature}</td>
                                            <td>{el.trip}</td>
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
        
              case "eng":
                return(
                    <div>
                        <div className="horizonbar">
                            <table>
                                <thead>
                                    <tr>
                                        <td>E_OverallRMS</td>
                                        <td>E_1_2X</td>
                                        <td>E_1X</td>
                                        <td>E_CrestFactor</td>
                                        <td>Trip</td>
                                        <td>FILENM</td>
                                    </tr>
                        
                                </thead>
                                <tbody>
                                    {
                                    this.props.data.map((el, idx) => {
                                    return(
                                        <tr key={idx}>
                                            <td>{el.e_OverallRMS}</td>
                                            <td>{el.e_1_2X}</td>
                                            <td>{el.e_1X}</td>
                                            <td>{el.e_CrestFactor}</td>
                                            <td>{el.trip}</td>
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
        
              case 'grb':
                return(
                    <div>
                        <div className="horizonbar">
                            <table>
                                <thead>
                                    <tr>
                                        <td>G_OverallRMS</td>
                                        <td>G_Wheel1X</td>
                                        <td>G_Wheel2X</td>
                                        <td>G_Pinion1X</td>
                                        <td>G_Pinion2X</td>
                                        <td>G_GMF1X</td>
                                        <td>G_GMF2X</td>
                                        <td>Trip</td>
                                        <td>FILENM</td>
                                        
                                    </tr>
                        
                                </thead>
                                <tbody>
                                    {
                                    this.props.data.map((el, idx) => {
                                    return(
                                            <tr key={idx}>
                                                <td>{el.g_OverallRMS}</td>
                                                <td>{el.g_Wheel1X}</td>
                                                <td>{el.g_Wheel2X}</td>
                                                <td>{el.g_Pinion1X}</td>
                                                <td>{el.g_Pinion2X}</td>
                                                <td>{el.g_GMF1X}</td>
                                                <td>{el.g_GMF2X}</td>
                                                <td>{el.trip}</td>
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
        
              default:
                return(
                    <div>
                        <div className="horizonbar">
                            <table>
                                <thead>
                                    <tr>
                                        <td>W_2X</td>
                                        <td>W_3X</td>
                                        <td>W_Fault3</td>
                                        <td>Trip</td>
                                        <td>FILENM</td>
                                    </tr>
                                </thead>
                                <tbody>
                                    {
                                    this.props.data.map((el, idx) => {
                                    return(
                                            <tr key={idx}>
                                                <td>{el.w_2X}</td>
                                                <td>{el.w_3X}</td>
                                                <td>{el.w_Fault3}</td>
                                                <td>{el.trip}</td>
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

