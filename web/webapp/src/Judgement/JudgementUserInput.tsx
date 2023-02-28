import React, {useContext, useEffect, useMemo, useState} from "react";
import {Button} from "react-bootstrap";
import Col from "react-bootstrap/Col";
import Container from "react-bootstrap/Container";
import Form from "react-bootstrap/Form";
import Row from "react-bootstrap/Row";
import {Column} from "react-table";
import {OpenApiContext} from "../api";
import {Table} from "../common/Table";
import {
  partTypes,
  SensorBearingLeftBallInput,
  SensorBearingLeftInsideInput,
  SensorBearingLeftOutsideInput,
  SensorBearingLeftRetainerInput,
  SensorBearingRightBallInput,
  SensorBearingRightInsideInput,
  SensorBearingRightOutsideInput,
  SensorBearingRightRetainerInput,
  SensorEngineInput,
  SensorGearboxInput,
  SensorWheelLeftInput,
  SensorWheelRightInput
} from "../ModelManagement/useDataPredictionColumns";
import {Section} from "../common/Section/Section";
import "./judgementStyle.css"
import DatePicker from "react-datepicker";


export const JudgementUserInput: React.FC = () => {
  const [partType, setPartType] = useState<string>("BLB");
  const [carsList, setCarsList] = useState<string[]>([]);
  const [selectedCar, setSelectedCar] = useState<string>();
  const [modelList, setModelList] = useState<string[]>([]);
  const [selectedModel, setSelectedModel] = useState<string>();

  const [fromDate, setFromDate] = useState<Date>(new Date());
  const [toDate, setToDate] = useState<Date>(new Date());

  const [tableColumn, setTableColumn] = useState<any>([]);
  const [predictedData, setPredictedData] = useState<any[]>([]);

  const {databaseJudgementControllerApi} = useContext(OpenApiContext);

  const [updateDefectUserList, setUpdateDefectUserList] = useState<{ idx: number; userJudgement: number; }[]>([]);

  function onClickHandler(score: any, idx: any, e: any) {
    // Whenever defectUser value comes in through radio button,
    // it is saved in 'updateDefectUserList' in the form of 'index: {esId: ~, defectUser: ~}' one by one.
    var existUpdateList = updateDefectUserList
    var updateDict = {idx: idx, userJudgement: score}

    var index = existUpdateList.findIndex(el => el.idx === idx);
    if (index === -1) {
      existUpdateList.push(updateDict);
    } else {
      existUpdateList[index] = updateDict;
    }
    setUpdateDefectUserList(existUpdateList)

  }

  function radioButtonHandler(value: any, idx: any) {
    var existUpdateList = updateDefectUserList
    var index = existUpdateList.findIndex(el => el.idx === idx);

    if (index === -1) {
      return false
    } else {
      return existUpdateList[index].userJudgement == value
    }

  }

  const SensorBearingLeftBallColumns = useMemo<Column<SensorBearingLeftBallInput>[]>(
    () => {
      return [
        {
          Header: "작업자 판정값",
          Cell: (value: any) => {
            return (
              <>
                <form className="d-flex">
                  <div className="m-1">
                    <input
                      type="radio"
                      name="defectUser"
                      key={value.row.original.idx}
                      value={1}
                      defaultChecked={(value.row.original.user_LBSF == 1) || (radioButtonHandler(1, value.row.original.idx))}
                      onClick={(e: any) =>
                        onClickHandler(1, value.row.original.idx, e)
                      }
                      style={{border: '0px', width: '100%', height: '1em'}}
                    />
                    <label className="m-1"> 고장 </label>
                  </div>
                  <div className="m-1">
                    <input
                      type="radio"
                      name="defectUser"
                      key={value.row.original.idx}
                      value={0}
                      defaultChecked={(value.row.original.user_LBSF == 0) || (radioButtonHandler(0, value.row.original.idx))}
                      onClick={(e: any) =>
                        onClickHandler(0, value.row.original.idx, e)
                      }
                      style={{border: '0px', width: '100%', height: '1em'}}
                    />
                    <label className="m-1"> 정상 </label>
                  </div>
                </form>
              </>
            )
          }
        },
        {
          Header: "예측 결과",
          accessor: (data) => {
            let result = data.ai_LBSF == "0" ? "정상" : "고장"
            return (
              <>
                <div>{result}</div>
              </>
            )
          },
        },
        {
          Header: "차량 ID",
          accessor: "sdaId",
        },
        {
          Header: "운용날짜",
          Cell: (value?: any) => {
            return new Date(value.row.original.date).toLocaleString("ko-KR")
          }
        },
        {
          Header: "작업자 ID",
          accessor: "user_LBSF_ID",
        },
        {
          Header: "작업자 판정 날짜",
          accessor: (value) => {
            let result = value.user_LBSF_DATE == null ? "" : new Date(value.user_LBSF_DATE).toLocaleString("ko-KR")
            return result
          }
        },
        {
          Header: "알고리즘",
          accessor: "ai_LBSF_ALGO",
        },
        {
          Header: "모델이름",
          accessor: "ai_LBSF_MODEL",
        },
        {
          Header: "모델 판정 날짜",
          Cell: (value?: any) => {
            return new Date(value.row.original.ai_LBSF_DATE).toLocaleString("ko-KR")
          }
        },
        {
          Header: "W_RPM",
          accessor: "w_RPM",
        },
        {
          Header: "L_B_V_1X",
          accessor: "l_B_V_1X",
        },
        {
          Header: "L_B_V_6912BSF",
          accessor: "l_B_V_6912BSF",
        },
        {
          Header: "L_B_V_32924BSF",
          accessor: "l_B_V_32924BSF",
        },
        {
          Header: "L_B_V_32922BSF",
          accessor: "l_B_V_32922BSF",
        },
        {
          Header: "L_B_V_Crestfactor",
          accessor: "l_B_V_Crestfactor",
        },
        {
          Header: "L_B_V_Demodulation",
          accessor: "l_B_V_Demodulation",
        },
        {
          Header: "L_B_S_Fault1",
          accessor: "l_B_S_Fault1",
        },
        {
          Header: "L_B_S_Fault2",
          accessor: "l_B_S_Fault2",
        },
        {
          Header: "L_B_T_Temperature",
          accessor: "l_B_T_Temperature",
        },
        {
          Header: "AC_h",
          accessor: "ac_h",
        },
        {
          Header: "AC_v",
          accessor: "ac_v",
        },
        {
          Header: "AC_a",
          accessor: "ac_a",
        },
      ];
    },
    [onClickHandler, radioButtonHandler]
  );

  const SensorBearingLeftInsideColumns = useMemo<Column<SensorBearingLeftInsideInput>[]>(
    () => [
      {
        Header: "작업자 판정값",
        Cell: (value: any) => {
          return (
            <>
              <form className="d-flex">
                <div className="m-1">
                  <input
                    type="radio"
                    name="defectUser"
                    key={value.row.original.idx}
                    value={1}
                    defaultChecked={(value.row.original.user_LBPFI == 1) || (radioButtonHandler(1, value.row.original.idx))}
                    onClick={(e: any) =>
                      onClickHandler(1, value.row.original.idx, e)
                    }
                    style={{border: '0px', width: '100%', height: '1em'}}
                  />
                  <label className="m-1"> 고장 </label>
                </div>
                <div className="m-1">
                  <input
                    type="radio"
                    name="defectUser"
                    key={value.row.original.idx}
                    value={0}
                    defaultChecked={(value.row.original.user_LBPFI == 0) || (radioButtonHandler(0, value.row.original.idx))}
                    onClick={(e: any) =>
                      onClickHandler(0, value.row.original.idx, e)
                    }
                    style={{border: '0px', width: '100%', height: '1em'}}
                  />
                  <label className="m-1"> 정상 </label>
                </div>
              </form>
            </>
          )
        }
      },
      {
        Header: "예측 결과",
        accessor: (data) => {
          let result = data.ai_LBPFI == "0" ? "정상" : "고장"
          return (
            <>
              <div>{result}</div>
            </>
          )
        },
      },
      {
        Header: "차량 ID",
        accessor: "sdaId",
      },
      {
        Header: "운용날짜",
        Cell: (value?: any) => {
          return new Date(value.row.original.date).toLocaleString("ko-KR")
        }
      },
      {
        Header: "작업자 ID",
        accessor: "user_LBPFI_ID",
      },
      {
        Header: "작업자 판정 날짜",
        accessor: (value) => {
          let result = value.user_LBPFI_DATE == null ? "" : new Date(value.user_LBPFI_DATE).toLocaleString("ko-KR")
          return result
        }
      },
      {
        Header: "알고리즘",
        accessor: "ai_LBPFI_ALGO",
      },
      {
        Header: "모델이름",
        accessor: "ai_LBPFI_MODEL",
      },
      {
        Header: "모델 판정 날짜",
        Cell: (value?: any) => {
          return new Date(value.row.original.ai_LBPFI_DATE).toLocaleString("ko-KR")
        }
      },
      {
        Header: "W_RPM",
        accessor: "w_RPM",
      },
      {
        Header: "L_B_V_1X",
        accessor: "l_B_V_1X",
      },
      {
        Header: "L_B_V_6912BPFI",
        accessor: "l_B_V_6912BPFI",
      },
      {
        Header: "L_B_V_32924BPFI",
        accessor: "l_B_V_32924BPFI",
      },
      {
        Header: "L_B_V_32922BPFI",
        accessor: "l_B_V_32922BPFI",
      },
      {
        Header: "L_B_V_Crestfactor",
        accessor: "l_B_V_Crestfactor",
      },
      {
        Header: "L_B_V_Demodulation",
        accessor: "l_B_V_Demodulation",
      },
      {
        Header: "L_B_S_Fault1",
        accessor: "l_B_S_Fault1",
      },
      {
        Header: "L_B_S_Fault2",
        accessor: "l_B_S_Fault2",
      },
      {
        Header: "L_B_T_Temperature",
        accessor: "l_B_T_Temperature",
      },
      {
        Header: "AC_h",
        accessor: "ac_h",
      },
      {
        Header: "AC_v",
        accessor: "ac_v",
      },
      {
        Header: "AC_a",
        accessor: "ac_a",
      },
    ],
    [onClickHandler, radioButtonHandler]
  );

  const SensorBearingLeftOutsideColumns = useMemo<Column<SensorBearingLeftOutsideInput>[]>(
    () => [
      {
        Header: "작업자 판정값",
        Cell: (value: any) => {
          return (
            <>
              <form className="d-flex">
                <div className="m-1">
                  <input
                    type="radio"
                    name="defectUser"
                    key={value.row.original.idx}
                    value={1}
                    defaultChecked={(value.row.original.user_LBPFO == 1) || (radioButtonHandler(1, value.row.original.idx))}
                    onClick={(e: any) =>
                      onClickHandler(1, value.row.original.idx, e)
                    }
                    style={{border: '0px', width: '100%', height: '1em'}}
                  />
                  <label className="m-1"> 고장 </label>
                </div>
                <div className="m-1">
                  <input
                    type="radio"
                    name="defectUser"
                    key={value.row.original.idx}
                    value={0}
                    defaultChecked={(value.row.original.user_LBPFO == 0) || (radioButtonHandler(0, value.row.original.idx))}
                    onClick={(e: any) =>
                      onClickHandler(0, value.row.original.idx, e)
                    }
                    style={{border: '0px', width: '100%', height: '1em'}}
                  />
                  <label className="m-1"> 정상 </label>
                </div>
              </form>
            </>
          )
        }
      },
      {
        Header: "예측 결과",
        accessor: (data) => {
          let result = data.ai_LBPFO == "0" ? "정상" : "고장"
          return (
            <>
              <div>{result}</div>
            </>
          )
        },
      },
      {
        Header: "차량 ID",
        accessor: "sdaId",
      },
      {
        Header: "운용날짜",
        Cell: (value?: any) => {
          return new Date(value.row.original.date).toLocaleString("ko-KR")
        }
      },
      {
        Header: "작업자 ID",
        accessor: "user_LBPFO_ID",
      },
      {
        Header: "작업자 판정 날짜",
        accessor: (value) => {
          let result = value.user_LBPFO_DATE == null ? "" : new Date(value.user_LBPFO_DATE).toLocaleString("ko-KR")
          return result
        }
      },
      {
        Header: "알고리즘",
        accessor: "ai_LBPFO_ALGO",
      },
      {
        Header: "모델이름",
        accessor: "ai_LBPFO_MODEL",
      },
      {
        Header: "모델 판정 날짜",
        Cell: (value?: any) => {
          return new Date(value.row.original.ai_LBPFO_DATE).toLocaleString("ko-KR")
        }
      },
      {
        Header: "W_RPM",
        accessor: "w_RPM",
      },
      {
        Header: "L_B_V_1X",
        accessor: "l_B_V_1X",
      },
      {
        Header: "L_B_V_6912BPFO",
        accessor: "l_B_V_6912BPFO",
      },
      {
        Header: "L_B_V_32924BPFO",
        accessor: "l_B_V_32924BPFO",
      },
      {
        Header: "L_B_V_32922BPFO",
        accessor: "l_B_V_32922BPFO",
      },
      {
        Header: "L_B_V_Crestfactor",
        accessor: "l_B_V_Crestfactor",
      },
      {
        Header: "L_B_V_Demodulation",
        accessor: "l_B_V_Demodulation",
      },
      {
        Header: "L_B_S_Fault1",
        accessor: "l_B_S_Fault1",
      },
      {
        Header: "L_B_S_Fault2",
        accessor: "l_B_S_Fault2",
      },
      {
        Header: "L_B_T_Temperature",
        accessor: "l_B_T_Temperature",
      },
      {
        Header: "AC_h",
        accessor: "ac_h",
      },
      {
        Header: "AC_v",
        accessor: "ac_v",
      },
      {
        Header: "AC_a",
        accessor: "ac_a",
      },
    ],
    [onClickHandler, radioButtonHandler]
  );

  const SensorBearingLeftRetainerColumns = useMemo<Column<SensorBearingLeftRetainerInput>[]>(
    () => [
      {
        Header: "작업자 판정값",
        Cell: (value: any) => {
          return (
            <>
              <form className="d-flex">
                <div className="m-1">
                  <input
                    type="radio"
                    name="defectUser"
                    key={value.row.original.idx}
                    value={1}
                    defaultChecked={(value.row.original.user_LFTF == 1) || (radioButtonHandler(1, value.row.original.idx))}
                    onClick={(e: any) =>
                      onClickHandler(1, value.row.original.idx, e)
                    }
                    style={{border: '0px', width: '100%', height: '1em'}}
                  />
                  <label className="m-1"> 고장 </label>
                </div>
                <div className="m-1">
                  <input
                    type="radio"
                    name="defectUser"
                    key={value.row.original.idx}
                    value={0}
                    defaultChecked={(value.row.original.user_LFTF == 0) || (radioButtonHandler(0, value.row.original.idx))}
                    onClick={(e: any) =>
                      onClickHandler(0, value.row.original.idx, e)
                    }
                    style={{border: '0px', width: '100%', height: '1em'}}
                  />
                  <label className="m-1"> 정상 </label>
                </div>
              </form>
            </>
          )
        }
      },
      {
        Header: "예측 결과",
        accessor: (data) => {
          let result = data.ai_LFTF == "0" ? "정상" : "고장"
          return (
            <>
              <div>{result}</div>
            </>
          )
        },
      },
      {
        Header: "차량 ID",
        accessor: "sdaId",
      },
      {
        Header: "운용날짜",
        Cell: (value?: any) => {
          return new Date(value.row.original.date).toLocaleString("ko-KR")
        }
      },
      {
        Header: "작업자 ID",
        accessor: "user_LFTF_ID",
      },
      {
        Header: "작업자 판정 날짜",
        accessor: (value) => {
          let result = value.user_LFTF_DATE == null ? "" : new Date(value.user_LFTF_DATE).toLocaleString("ko-KR")
          return result
        }
      },
      {
        Header: "알고리즘",
        accessor: "ai_LFTF_ALGO",
      },
      {
        Header: "모델이름",
        accessor: "ai_LFTF_MODEL",
      },
      {
        Header: "모델 판정 날짜",
        Cell: (value?: any) => {
          return new Date(value.row.original.ai_LFTF_DATE).toLocaleString("ko-KR")
        }
      },
      {
        Header: "W_RPM",
        accessor: "w_RPM",
      },
      {
        Header: "L_B_V_1X",
        accessor: "l_B_V_1X",
      },
      {
        Header: "L_B_V_6912FTF",
        accessor: "l_B_V_6912FTF",
      },
      {
        Header: "L_B_V_32924FTF",
        accessor: "l_B_V_32924FTF",
      },
      {
        Header: "l_B_V_32922FTF",
        accessor: "l_B_V_32922FTF",
      },
      {
        Header: "L_B_V_Crestfactor",
        accessor: "l_B_V_Crestfactor",
      },
      {
        Header: "L_B_V_Demodulation",
        accessor: "l_B_V_Demodulation",
      },
      {
        Header: "L_B_S_Fault1",
        accessor: "l_B_S_Fault1",
      },
      {
        Header: "L_B_S_Fault2",
        accessor: "l_B_S_Fault2",
      },
      {
        Header: "L_B_T_Temperature",
        accessor: "l_B_T_Temperature",
      },
      {
        Header: "AC_h",
        accessor: "ac_h",
      },
      {
        Header: "AC_v",
        accessor: "ac_v",
      },
      {
        Header: "AC_a",
        accessor: "ac_a",
      },
    ],
    [onClickHandler, radioButtonHandler]
  );

  const SensorBearingRightBallColumns = useMemo<Column<SensorBearingRightBallInput>[]>(
    () => [
      {
        Header: "작업자 판정값",
        Cell: (value: any) => {
          return (
            <>
              <form className="d-flex">
                <div className="m-1">
                  <input
                    type="radio"
                    name="defectUser"
                    key={value.row.original.idx}
                    value={1}
                    defaultChecked={(value.row.original.user_RBSF == 1) || (radioButtonHandler(1, value.row.original.idx))}
                    onClick={(e: any) =>
                      onClickHandler(1, value.row.original.idx, e)
                    }
                    style={{border: '0px', width: '100%', height: '1em'}}
                  />
                  <label className="m-1"> 고장 </label>
                </div>
                <div className="m-1">
                  <input
                    type="radio"
                    name="defectUser"
                    key={value.row.original.idx}
                    value={0}
                    defaultChecked={(value.row.original.user_RBSF == 0) || (radioButtonHandler(0, value.row.original.idx))}
                    onClick={(e: any) =>
                      onClickHandler(0, value.row.original.idx, e)
                    }
                    style={{border: '0px', width: '100%', height: '1em'}}
                  />
                  <label className="m-1"> 정상 </label>
                </div>
              </form>
            </>
          )
        }
      },
      {
        Header: "예측 결과",
        accessor: (data) => {
          let result = data.ai_RBSF == "0" ? "정상" : "고장"
          return (
            <>
              <div>{result}</div>
            </>
          )
        },
      },
      {
        Header: "차량 ID",
        accessor: "sdaId",
      },
      {
        Header: "운용날짜",
        Cell: (value?: any) => {
          return new Date(value.row.original.date).toLocaleString("ko-KR")
        }
      },
      {
        Header: "작업자 ID",
        accessor: "user_RBSF_ID",
      },
      {
        Header: "작업자 판정 날짜",
        accessor: (value) => {
          let result = value.user_RBSF_DATE == null ? "" : new Date(value.user_RBSF_DATE).toLocaleString("ko-KR")
          return result
        }
      },
      {
        Header: "알고리즘",
        accessor: "ai_RBSF_ALGO",
      },
      {
        Header: "모델이름",
        accessor: "ai_RBSF_MODEL",
      },
      {
        Header: "모델 판정 날짜",
        Cell: (value?: any) => {
          return new Date(value.row.original.ai_RBSF_DATE).toLocaleString("ko-KR")
        }
      },
      {
        Header: "W_RPM",
        accessor: "w_RPM",
      },
      {
        Header: "R_B_V_1X",
        accessor: "r_B_V_1X",
      },
      {
        Header: "R_B_V_6912BSF",
        accessor: "r_B_V_6912BSF",
      },
      {
        Header: "R_B_V_32924BSF",
        accessor: "r_B_V_32924BSF",
      },
      {
        Header: "R_B_V_32922BSF",
        accessor: "r_B_V_32922BSF",
      },
      {
        Header: "R_B_V_Crestfactor",
        accessor: "r_B_V_Crestfactor",
      },
      {
        Header: "R_B_V_Demodulation",
        accessor: "r_B_V_Demodulation",
      },
      {
        Header: "R_B_S_Fault1",
        accessor: "r_B_S_Fault1",
      },
      {
        Header: "R_B_S_Fault2",
        accessor: "r_B_S_Fault2",
      },
      {
        Header: "R_B_T_Temperature",
        accessor: "r_B_T_Temperature",
      },
      {
        Header: "AC_h",
        accessor: "ac_h",
      },
      {
        Header: "AC_v",
        accessor: "ac_v",
      },
      {
        Header: "AC_a",
        accessor: "ac_a",
      },
    ],
    [onClickHandler, radioButtonHandler]
  );

  const SensorBearingRightInsideColumns = useMemo<Column<SensorBearingRightInsideInput>[]>(
    () => [
      {
        Header: "작업자 판정값",
        Cell: (value: any) => {
          return (
            <>
              <form className="d-flex">
                <div className="m-1">
                  <input
                    type="radio"
                    name="defectUser"
                    key={value.row.original.idx}
                    value={1}
                    defaultChecked={(value.row.original.user_RBPFI == 1) || (radioButtonHandler(1, value.row.original.idx))}
                    onClick={(e: any) =>
                      onClickHandler(1, value.row.original.idx, e)
                    }
                    style={{border: '0px', width: '100%', height: '1em'}}
                  />
                  <label className="m-1"> 고장 </label>
                </div>
                <div className="m-1">
                  <input
                    type="radio"
                    name="defectUser"
                    key={value.row.original.idx}
                    value={0}
                    defaultChecked={(value.row.original.user_RBPFI == 0) || (radioButtonHandler(0, value.row.original.idx))}
                    onClick={(e: any) =>
                      onClickHandler(0, value.row.original.idx, e)
                    }
                    style={{border: '0px', width: '100%', height: '1em'}}
                  />
                  <label className="m-1"> 정상 </label>
                </div>
              </form>
            </>
          )
        }
      },
      {
        Header: "예측 결과",
        accessor: (data) => {
          let result = data.ai_RBPFI == "0" ? "정상" : "고장"
          return (
            <>
              <div>{result}</div>
            </>
          )
        },
      },
      {
        Header: "차량 ID",
        accessor: "sdaId",
      },
      {
        Header: "운용날짜",
        Cell: (value?: any) => {
          return new Date(value.row.original.date).toLocaleString("ko-KR")
        }
      },
      {
        Header: "작업자 ID",
        accessor: "user_RBPFI_ID",
      },
      {
        Header: "작업자 판정 날짜",
        accessor: (value) => {
          let result = value.user_RBPFI_DATE == null ? "" : new Date(value.user_RBPFI_DATE).toLocaleString("ko-KR")
          return result
        }
      },
      {
        Header: "알고리즘",
        accessor: "ai_RBPFI_ALGO",
      },
      {
        Header: "모델이름",
        accessor: "ai_RBPFI_MODEL",
      },
      {
        Header: "모델 판정 날짜",
        Cell: (value?: any) => {
          return new Date(value.row.original.ai_RBPFI_DATE).toLocaleString("ko-KR")
        }
      },
      {
        Header: "W_RPM",
        accessor: "w_RPM",
      },
      {
        Header: "R_B_V_1X",
        accessor: "r_B_V_1X",
      },
      {
        Header: "R_B_V_6912BPFI",
        accessor: "r_B_V_6912BPFI",
      },
      {
        Header: "R_B_V_32924BPFI",
        accessor: "r_B_V_32924BPFI",
      },
      {
        Header: "R_B_V_32922BPFI",
        accessor: "r_B_V_32922BPFI",
      },
      {
        Header: "R_B_V_Crestfactor",
        accessor: "r_B_V_Crestfactor",
      },
      {
        Header: "R_B_V_Demodulation",
        accessor: "r_B_V_Demodulation",
      },
      {
        Header: "R_B_S_Fault1",
        accessor: "r_B_S_Fault1",
      },
      {
        Header: "R_B_S_Fault2",
        accessor: "r_B_S_Fault2",
      },
      {
        Header: "R_B_T_Temperature",
        accessor: "r_B_T_Temperature",
      },
      {
        Header: "AC_h",
        accessor: "ac_h",
      },
      {
        Header: "AC_v",
        accessor: "ac_v",
      },
      {
        Header: "AC_a",
        accessor: "ac_a",
      },
    ],
    [onClickHandler, radioButtonHandler]
  );

  const SensorBearingRightOutsideColumns = useMemo<Column<SensorBearingRightOutsideInput>[]>(
    () => [
      {
        Header: "작업자 판정값",
        Cell: (value: any) => {
          return (
            <>
              <form className="d-flex">
                <div className="m-1">
                  <input
                    type="radio"
                    name="defectUser"
                    key={value.row.original.idx}
                    value={1}
                    defaultChecked={(value.row.original.user_RBPFO == 1) || (radioButtonHandler(1, value.row.original.idx))}
                    onClick={(e: any) =>
                      onClickHandler(1, value.row.original.idx, e)
                    }
                    style={{border: '0px', width: '100%', height: '1em'}}
                  />
                  <label className="m-1"> 고장 </label>
                </div>
                <div className="m-1">
                  <input
                    type="radio"
                    name="defectUser"
                    key={value.row.original.idx}
                    value={0}
                    defaultChecked={(value.row.original.user_RBPFO == 0) || (radioButtonHandler(0, value.row.original.idx))}
                    onClick={(e: any) =>
                      onClickHandler(0, value.row.original.idx, e)
                    }
                    style={{border: '0px', width: '100%', height: '1em'}}
                  />
                  <label className="m-1"> 정상 </label>
                </div>
              </form>
            </>
          )
        }
      },
      {
        Header: "예측 결과",
        accessor: (data) => {
          let result = data.ai_RBPFO == "0" ? "정상" : "고장"
          return (
            <>
              <div>{result}</div>
            </>
          )
        },
      },
      {
        Header: "차량 ID",
        accessor: "sdaId",
      },
      {
        Header: "운용날짜",
        Cell: (value?: any) => {
          return new Date(value.row.original.date).toLocaleString("ko-KR")
        }
      },
      {
        Header: "작업자 ID",
        accessor: "user_RBPFO_ID",
      },
      {
        Header: "작업자 판정 날짜",
        accessor: (value) => {
          let result = value.user_RBPFO_DATE == null ? "" : new Date(value.user_RBPFO_DATE).toLocaleString("ko-KR")
          return result
        }
      },
      {
        Header: "알고리즘",
        accessor: "ai_RBPFO_ALGO",
      },
      {
        Header: "모델이름",
        accessor: "ai_RBPFO_MODEL",
      },
      {
        Header: "모델 판정 날짜",
        Cell: (value?: any) => {
          return new Date(value.row.original.ai_RBPFO_DATE).toLocaleString("ko-KR")
        }
      },
      {
        Header: "W_RPM",
        accessor: "w_RPM",
      },
      {
        Header: "R_B_V_1X",
        accessor: "r_B_V_1X",
      },
      {
        Header: "R_B_V_6912BPFO",
        accessor: "r_B_V_6912BPFO",
      },
      {
        Header: "R_B_V_32924BPFO",
        accessor: "r_B_V_32924BPFO",
      },
      {
        Header: "R_B_V_32922BPFO",
        accessor: "r_B_V_32922BPFO",
      },
      {
        Header: "R_B_V_Crestfactor",
        accessor: "r_B_V_Crestfactor",
      },
      {
        Header: "R_B_V_Demodulation",
        accessor: "r_B_V_Demodulation",
      },
      {
        Header: "R_B_S_Fault1",
        accessor: "r_B_S_Fault1",
      },
      {
        Header: "R_B_S_Fault2",
        accessor: "r_B_S_Fault2",
      },
      {
        Header: "R_B_T_Temperature",
        accessor: "r_B_T_Temperature",
      },
      {
        Header: "AC_h",
        accessor: "ac_h",
      },
      {
        Header: "AC_v",
        accessor: "ac_v",
      },
      {
        Header: "AC_a",
        accessor: "ac_a",
      },
    ],
    [onClickHandler, radioButtonHandler]
  );

  const SensorBearingRightRetainerColumns = useMemo<Column<SensorBearingRightRetainerInput>[]>(
    () => [
      {
        Header: "작업자 판정값",
        Cell: (value: any) => {
          return (
            <>
              <form className="d-flex">
                <div className="m-1">
                  <input
                    type="radio"
                    name="defectUser"
                    key={value.row.original.idx}
                    value={1}
                    defaultChecked={(value.row.original.user_RFTF == 1) || (radioButtonHandler(1, value.row.original.idx))}
                    onClick={(e: any) =>
                      onClickHandler(1, value.row.original.idx, e)
                    }
                    style={{border: '0px', width: '100%', height: '1em'}}
                  />
                  <label className="m-1"> 고장 </label>
                </div>
                <div className="m-1">
                  <input
                    type="radio"
                    name="defectUser"
                    key={value.row.original.idx}
                    value={0}
                    defaultChecked={(value.row.original.user_RFTF == 0) || (radioButtonHandler(0, value.row.original.idx))}
                    onClick={(e: any) =>
                      onClickHandler(0, value.row.original.idx, e)
                    }
                    style={{border: '0px', width: '100%', height: '1em'}}
                  />
                  <label className="m-1"> 정상 </label>
                </div>
              </form>
            </>
          )
        }
      },
      {
        Header: "예측 결과",
        accessor: (data) => {
          let result = data.ai_RFTF == "0" ? "정상" : "고장"
          return (
            <>
              <div>{result}</div>
            </>
          )
        },
      },
      {
        Header: "차량 ID",
        accessor: "sdaId",
      },
      {
        Header: "운용날짜",
        Cell: (value?: any) => {
          return new Date(value.row.original.date).toLocaleString("ko-KR")
        }
      },
      {
        Header: "작업자 ID",
        accessor: "user_RFTF_ID",
      },
      {
        Header: "작업자 판정 날짜",
        accessor: (value) => {
          let result = value.user_RFTF_DATE == null ? "" : new Date(value.user_RFTF_DATE).toLocaleString("ko-KR")
          return result
        }
      },
      {
        Header: "알고리즘",
        accessor: "ai_RFTF_ALGO",
      },
      {
        Header: "모델이름",
        accessor: "ai_RFTF_MODEL",
      },
      {
        Header: "모델 판정 날짜",
        Cell: (value?: any) => {
          return new Date(value.row.original.ai_RFTF_DATE).toLocaleString("ko-KR")
        }
      },
      {
        Header: "W_RPM",
        accessor: "w_RPM",
      },
      {
        Header: "R_B_V_1X",
        accessor: "r_B_V_1X",
      },
      {
        Header: "R_B_V_6912FTF",
        accessor: "r_B_V_6912FTF",
      },
      {
        Header: "R_B_V_32924FTF",
        accessor: "r_B_V_32924FTF",
      },
      {
        Header: "R_B_V_32922FTF",
        accessor: "r_B_V_32922FTF",
      },
      {
        Header: "R_B_V_Crestfactor",
        accessor: "r_B_V_Crestfactor",
      },
      {
        Header: "R_B_V_Demodulation",
        accessor: "r_B_V_Demodulation",
      },
      {
        Header: "R_B_S_Fault1",
        accessor: "r_B_S_Fault1",
      },
      {
        Header: "R_B_S_Fault2",
        accessor: "r_B_S_Fault2",
      },
      {
        Header: "R_B_T_Temperature",
        accessor: "r_B_T_Temperature",
      },
      {
        Header: "AC_h",
        accessor: "ac_h",
      },
      {
        Header: "AC_v",
        accessor: "ac_v",
      },
      {
        Header: "AC_a",
        accessor: "ac_a",
      },
    ],
    [onClickHandler, radioButtonHandler]
  );

  const SensorWheelLeftColumns = useMemo<Column<SensorWheelLeftInput>[]>(
    () => [
      {
        Header: "작업자 판정값",
        Cell: (value: any) => {
          return (
            <>
              <form className="d-flex">
                <div className="m-1">
                  <input
                    type="radio"
                    name="defectUser"
                    key={value.row.original.idx}
                    value={1}
                    defaultChecked={(value.row.original.user_LW == 1) || (radioButtonHandler(1, value.row.original.idx))}
                    onClick={(e: any) =>
                      onClickHandler(1, value.row.original.idx, e)
                    }
                    style={{border: '0px', width: '100%', height: '1em'}}
                  />
                  <label className="m-1"> 고장 </label>
                </div>
                <div className="m-1">
                  <input
                    type="radio"
                    name="defectUser"
                    key={value.row.original.idx}
                    value={0}
                    defaultChecked={(value.row.original.user_LW == 0) || (radioButtonHandler(0, value.row.original.idx))}
                    onClick={(e: any) =>
                      onClickHandler(0, value.row.original.idx, e)
                    }
                    style={{border: '0px', width: '100%', height: '1em'}}
                  />
                  <label className="m-1"> 정상 </label>
                </div>
              </form>
            </>
          )
        }
      },
      {
        Header: "예측 결과",
        accessor: (data) => {
          let result = data.ai_LW == "0" ? "정상" : "고장"
          return (
            <>
              <div>{result}</div>
            </>
          )
        },
      },
      {
        Header: "차량 ID",
        accessor: "sdaId",
      },
      {
        Header: "운용날짜",
        Cell: (value?: any) => {
          return new Date(value.row.original.date).toLocaleString("ko-KR")
        }
      },
      {
        Header: "작업자 ID",
        accessor: "user_LW_ID",
      },
      {
        Header: "작업자 판정 날짜",
        accessor: (value) => {
          let result = value.user_LW_DATE == null ? "" : new Date(value.user_LW_DATE).toLocaleString("ko-KR")
          return result
        }
      },
      {
        Header: "알고리즘",
        accessor: "ai_LW_ALGO",
      },
      {
        Header: "모델이름",
        accessor: "ai_LW_MODEL",
      },
      {
        Header: "모델 판정 날짜",
        Cell: (value?: any) => {
          return new Date(value.row.original.ai_LW_DATE).toLocaleString("ko-KR")
        }
      },
      {
        Header: "W_RPM",
        accessor: "w_RPM",
      },
      {
        Header: "L_W_V_2X",
        accessor: "l_W_V_2X",
      },
      {
        Header: "L_W_V_3X",
        accessor: "l_W_V_3X",
      },
      {
        Header: "L_W_S_Fault3",
        accessor: "l_W_S_Fault3",
      },
      {
        Header: "AC_h",
        accessor: "ac_h",
      },
      {
        Header: "AC_v",
        accessor: "ac_v",
      },
      {
        Header: "AC_a",
        accessor: "ac_a",
      },
    ],
    [onClickHandler, radioButtonHandler]
  );

  const SensorWheelRightColumns = useMemo<Column<SensorWheelRightInput>[]>(
    () => [
      {
        Header: "작업자 판정값",
        Cell: (value: any) => {
          return (
            <>
              <form className="d-flex">
                <div className="m-1">
                  <input
                    type="radio"
                    name="defectUser"
                    key={value.row.original.idx}
                    value={1}
                    defaultChecked={(value.row.original.user_RW == 1) || (radioButtonHandler(1, value.row.original.idx))}
                    onClick={(e: any) =>
                      onClickHandler(1, value.row.original.idx, e)
                    }
                    style={{border: '0px', width: '100%', height: '1em'}}
                  />
                  <label className="m-1"> 고장 </label>
                </div>
                <div className="m-1">
                  <input
                    type="radio"
                    name="defectUser"
                    key={value.row.original.idx}
                    value={0}
                    defaultChecked={(value.row.original.user_RW == 0) || (radioButtonHandler(0, value.row.original.idx))}
                    onClick={(e: any) =>
                      onClickHandler(0, value.row.original.idx, e)
                    }
                    style={{border: '0px', width: '100%', height: '1em'}}
                  />
                  <label className="m-1"> 정상 </label>
                </div>
              </form>
            </>
          )
        }
      },
      {
        Header: "예측 결과",
        accessor: (data) => {
          let result = data.ai_RW == "0" ? "정상" : "고장"
          return (
            <>
              <div>{result}</div>
            </>
          )
        },
      },
      {
        Header: "차량 ID",
        accessor: "sdaId",
      },
      {
        Header: "운용날짜",
        Cell: (value?: any) => {
          return new Date(value.row.original.date).toLocaleString("ko-KR")
        }
      },
      {
        Header: "작업자 ID",
        accessor: "user_RW_ID",
      },
      {
        Header: "작업자 판정 날짜",
        accessor: (value) => {
          let result = value.user_RW_DATE == null ? "" : new Date(value.user_RW_DATE).toLocaleString("ko-KR")
          return result
        }
      },
      {
        Header: "알고리즘",
        accessor: "ai_RW_ALGO",
      },
      {
        Header: "모델이름",
        accessor: "ai_RW_MODEL",
      },
      {
        Header: "모델 판정 날짜",
        Cell: (value?: any) => {
          return new Date(value.row.original.ai_RW_DATE).toLocaleString("ko-KR")
        }
      },
      {
        Header: "W_RPM",
        accessor: "w_RPM",
      },
      {
        Header: "R_W_V_2X",
        accessor: "r_W_V_2X",
      },
      {
        Header: "R_W_V_3X",
        accessor: "r_W_V_3X",
      },
      {
        Header: "R_W_S_Fault3",
        accessor: "r_W_S_Fault3",
      },
      {
        Header: "AC_h",
        accessor: "ac_h",
      },
      {
        Header: "AC_v",
        accessor: "ac_v",
      },
      {
        Header: "AC_a",
        accessor: "ac_a",
      },
    ],
    [onClickHandler, radioButtonHandler]
  );

  const SensorGearboxColumns = useMemo<Column<SensorGearboxInput>[]>(
    () => [
      {
        Header: "작업자 판정값",
        Cell: (value: any) => {
          return (
            <>
              <form className="d-flex">
                <div className="m-1">
                  <input
                    type="radio"
                    name="defectUser"
                    key={value.row.original.idx}
                    value={1}
                    defaultChecked={(value.row.original.user_GEAR == 1) || (radioButtonHandler(1, value.row.original.idx))}
                    onClick={(e: any) =>
                      onClickHandler(1, value.row.original.idx, e)
                    }
                    style={{border: '0px', width: '100%', height: '1em'}}
                  />
                  <label className="m-1"> 고장 </label>
                </div>
                <div className="m-1">
                  <input
                    type="radio"
                    name="defectUser"
                    key={value.row.original.idx}
                    value={0}
                    defaultChecked={(value.row.original.user_GEAR == 0) || (radioButtonHandler(0, value.row.original.idx))}
                    onClick={(e: any) =>
                      onClickHandler(0, value.row.original.idx, e)
                    }
                    style={{border: '0px', width: '100%', height: '1em'}}
                  />
                  <label className="m-1"> 정상 </label>
                </div>
              </form>
            </>
          )
        }
      },
      {
        Header: "예측 결과",
        accessor: (data) => {
          let result = data.ai_GEAR == "0" ? "정상" : "고장"
          return (
            <>
              <div>{result}</div>
            </>
          )
        },
      },
      {
        Header: "차량 ID",
        accessor: "sdaId",
      },
      {
        Header: "운용날짜",
        Cell: (value?: any) => {
          return new Date(value.row.original.date).toLocaleString("ko-KR")
        }
      },
      {
        Header: "작업자 ID",
        accessor: "user_GEAR_ID",
      },
      {
        Header: "작업자 판정 날짜",
        accessor: (value) => {
          let result = value.user_GEAR_DATE == null ? "" : new Date(value.user_GEAR_DATE).toLocaleString("ko-KR")
          return result
        }
      },
      {
        Header: "알고리즘",
        accessor: "ai_GEAR_ALGO",
      },
      {
        Header: "모델이름",
        accessor: "ai_GEAR_MODEL",
      },
      {
        Header: "모델 판정 날짜",
        Cell: (value?: any) => {
          return new Date(value.row.original.ai_GEAR_DATE).toLocaleString("ko-KR")
        }
      },
      {
        Header: "W_RPM",
        accessor: "w_RPM",
      },
      {
        Header: "G_V_OverallRMS",
        accessor: "g_V_OverallRMS",
      },
      {
        Header: "G_V_Wheel1X",
        accessor: "g_V_Wheel1X",
      },
      {
        Header: "G_V_Wheel2X",
        accessor: "g_V_Wheel2X",
      },
      {
        Header: "G_V_Pinion1X",
        accessor: "g_V_Pinion1X",
      },
      {
        Header: "G_V_Pinion2X",
        accessor: "g_V_Pinion2X",
      },
      {
        Header: "G_V_GMF1X",
        accessor: "g_V_GMF1X",
      },
      {
        Header: "G_V_GMF2X",
        accessor: "g_V_GMF2X",
      },
      {
        Header: "AC_h",
        accessor: "ac_h",
      },
      {
        Header: "AC_v",
        accessor: "ac_v",
      },
      {
        Header: "AC_a",
        accessor: "ac_a",
      },
    ],
    [onClickHandler, radioButtonHandler]
  );

  const SensorEngineColumns = useMemo<Column<SensorEngineInput>[]>(
    () => [
      {
        Header: "작업자 판정값",
        Cell: (value: any) => {
          return (
            <>
              <form className="d-flex">
                <div className="m-1">
                  <input
                    type="radio"
                    name="defectUser"
                    key={value.row.original.idx}
                    value={1}
                    defaultChecked={(value.row.original.user_ENGINE == 1) || (radioButtonHandler(1, value.row.original.idx))}
                    onClick={(e: any) =>
                      onClickHandler(1, value.row.original.idx, e)
                    }
                    style={{border: '0px', width: '100%', height: '1em'}}
                  />
                  <label className="m-1"> 고장 </label>
                </div>
                <div className="m-1">
                  <input
                    type="radio"
                    name="defectUser"
                    key={value.row.original.idx}
                    value={0}
                    defaultChecked={(value.row.original.user_ENGINE == 0) || (radioButtonHandler(0, value.row.original.idx))}
                    onClick={(e: any) =>
                      onClickHandler(0, value.row.original.idx, e)
                    }
                    style={{border: '0px', width: '100%', height: '1em'}}
                  />
                  <label className="m-1"> 정상 </label>
                </div>
              </form>
            </>
          )
        }
      },
      {
        Header: "예측 결과",
        accessor: (data) => {
          let result = data.ai_ENGINE == "0" ? "정상" : "고장"
          return (
            <>
              <div>{result}</div>
            </>
          )
        },
      },
      {
        Header: "차량 ID",
        accessor: "sdaId",
      },
      {
        Header: "운용날짜",
        Cell: (value?: any) => {
          return new Date(value.row.original.date).toLocaleString("ko-KR")
        }
      },
      {
        Header: "작업자 ID",
        accessor: "user_ENGINE_ID",
      },
      {
        Header: "작업자 판정 날짜",
        accessor: (value) => {
          let result = value.user_ENGINE_DATE == null ? "" : new Date(value.user_ENGINE_DATE).toLocaleString("ko-KR")
          return result
        }
      },
      {
        Header: "알고리즘",
        accessor: "ai_ENGINE_ALGO",
      },
      {
        Header: "모델이름",
        accessor: "ai_ENGINE_MODEL",
      },
      {
        Header: "모델 판정 날짜",
        Cell: (value?: any) => {
          return new Date(value.row.original.ai_ENGINE_DATE).toLocaleString("ko-KR")
        }
      },
      {
        Header: "W_RPM",
        accessor: "w_RPM",
      },
      {
        Header: "E_V_OverallRMS",
        accessor: "e_V_OverallRMS",
      },
      {
        Header: "E_V_1_2X",
        accessor: "e_V_1_2X",
      },
      {
        Header: "E_V_1X",
        accessor: "e_V_1X",
      },
      {
        Header: "E_V_Crestfactor",
        accessor: "e_V_Crestfactor",
      },
      {
        Header: "AC_h",
        accessor: "ac_h",
      },
      {
        Header: "AC_v",
        accessor: "ac_v",
      },
      {
        Header: "AC_a",
        accessor: "ac_a",
      },
    ],
    [onClickHandler, radioButtonHandler]
  );

  async function onClickSaveButtonHandler() {
    let proceed = window.confirm("저장하시겠습니까?");
    if (proceed) {
      // Update to DB with edited defectUser values
      databaseJudgementControllerApi?.updateUserJudgement(partType, updateDefectUserList)
        .then(res => {
          handleSearchData()
        })
    }
  }

  function handleSearchTablesColumns(partType: any) {
    switch (partType) {
      case "BLB":
        // Bearing Left Ball
        return SensorBearingLeftBallColumns
      case "BLI":
        // Bearing Left Inside
        return SensorBearingLeftInsideColumns
      case "BLO":
        // Bearing Left Outside
        return SensorBearingLeftOutsideColumns
      case "BLR":
        // Bearing Left Retainer
        return SensorBearingLeftRetainerColumns
      case "BRB":
        // Bearing Right Ball
        return SensorBearingRightBallColumns
      case "BRI":
        // Bearing Right Inside
        return SensorBearingRightInsideColumns
      case "BRO":
        // Bearing Right Outside
        return SensorBearingRightOutsideColumns
      case "BRR":
        // Bearing Right Retainer
        return SensorBearingRightRetainerColumns
      case "WL":
        // Wheel Left
        return SensorWheelLeftColumns
      case "WR":
        // Wheel Right
        return SensorWheelRightColumns
      case "G":
        // Gearbox
        return SensorGearboxColumns
      case "E":
        // Engine
        return SensorEngineColumns
    }
  }


  useEffect(() => {
    const thisDate = new Date();
    thisDate.setMonth(thisDate.getMonth() - 6);
    setFromDate(thisDate);
  }, []);

  useEffect(() => {
    if (partType) {
      databaseJudgementControllerApi?.findDistinctByCarId(partType)
        .then((res) => {
          setCarsList(res.data)
          setSelectedCar(res.data[0])
        });
    }
  }, [partType, databaseJudgementControllerApi]);

  useEffect(() => {
    if (partType) {
      databaseJudgementControllerApi?.findDistinctByModelName(partType)
        .then((res) => {
          setModelList(res.data)
          setSelectedModel(res.data[0])
        });
    }
  }, [partType, databaseJudgementControllerApi]);

  function handleSearchData() {
    if (selectedCar == undefined || selectedModel == undefined) {
      return []
    }
    setTableColumn(handleSearchTablesColumns(partType))

    if (partType == 'BLB') {
      databaseJudgementControllerApi?.getBearingLeftBallPredictedData(
        selectedCar,
        selectedModel,
        fromDate?.toLocaleDateString("en-US"),
        toDate?.toLocaleDateString("en-US"),
      ).then((res) => {
        setPredictedData(res.data || []);
      });
    }
    if (partType == 'BLI') {
      databaseJudgementControllerApi?.getBearingLeftInsidePredictedData(
        selectedCar,
        selectedModel,
        fromDate?.toLocaleDateString("en-US"),
        toDate?.toLocaleDateString("en-US"),
      ).then((res) => {
        setPredictedData(res.data || []);
      });
    }
    if (partType == 'BLO') {
      databaseJudgementControllerApi?.getBearingLeftOutsidePredictedData(
        selectedCar,
        selectedModel,
        fromDate?.toLocaleDateString("en-US"),
        toDate?.toLocaleDateString("en-US"),
      ).then((res) => {
        setPredictedData(res.data || []);
      });
    }
    if (partType == 'BLR') {
      databaseJudgementControllerApi?.getBearingLeftRetainerPredictedData(
        selectedCar,
        selectedModel,
        fromDate?.toLocaleDateString("en-US"),
        toDate?.toLocaleDateString("en-US"),
      ).then((res) => {
        setPredictedData(res.data || []);
      });
    }
    if (partType == 'BRB') {
      databaseJudgementControllerApi?.getBearingRightBallPredictedData(
        selectedCar,
        selectedModel,
        fromDate?.toLocaleDateString("en-US"),
        toDate?.toLocaleDateString("en-US"),
      ).then((res) => {
        setPredictedData(res.data || []);
      });
    }
    if (partType == 'BRI') {
      databaseJudgementControllerApi?.getBearingRightInsidePredictedData(
        selectedCar,
        selectedModel,
        fromDate?.toLocaleDateString("en-US"),
        toDate?.toLocaleDateString("en-US"),
      ).then((res) => {
        setPredictedData(res.data || []);
      });
    }
    if (partType == 'BRO') {
      databaseJudgementControllerApi?.getBearingRightOutsidePredictedData(
        selectedCar,
        selectedModel,
        fromDate?.toLocaleDateString("en-US"),
        toDate?.toLocaleDateString("en-US"),
      ).then((res) => {
        setPredictedData(res.data || []);
      });
    }
    if (partType == 'BRR') {
      databaseJudgementControllerApi?.getBearingRightRetainerPredictedData(
        selectedCar,
        selectedModel,
        fromDate?.toLocaleDateString("en-US"),
        toDate?.toLocaleDateString("en-US"),
      ).then((res) => {
        setPredictedData(res.data || []);
      });
    }

    if (partType == 'E') {
      databaseJudgementControllerApi?.getEnginePredictedData(
        selectedCar,
        selectedModel,
        fromDate?.toLocaleDateString("en-US"),
        toDate?.toLocaleDateString("en-US"),
      ).then((res) => {
        setPredictedData(res.data || []);
      });
    }
    if (partType == 'G') {
      databaseJudgementControllerApi?.getGearboxPredictedData(
        selectedCar,
        selectedModel,
        fromDate?.toLocaleDateString("en-US"),
        toDate?.toLocaleDateString("en-US"),
      ).then((res) => {
        setPredictedData(res.data || []);
      });
    }
    if (partType == 'WL') {
      databaseJudgementControllerApi?.getWheelLeftPredictedData(
        selectedCar,
        selectedModel,
        fromDate?.toLocaleDateString("en-US"),
        toDate?.toLocaleDateString("en-US"),
      ).then((res) => {
        setPredictedData(res.data || []);
      });
    }
    if (partType == 'WR') {
      databaseJudgementControllerApi?.getWheelRightPredictedData(
        selectedCar,
        selectedModel,
        fromDate?.toLocaleDateString("en-US"),
        toDate?.toLocaleDateString("en-US"),
      ).then((res) => {
        setPredictedData(res.data || []);
      });
    }
  }

  return (
    <Container className="p-0 w-100 ">
      <Section title="검색 조건 입력">
        <Row className="mt-2">
          <Col xs={2} className="text-right pr-2">
            부품 선택
            <Form.Select
              size="sm"
              className="ml-2"
              value={partType}
              onChange={(v) => {
                setPartType((v.target as any).value);
                setPredictedData([])
                // handleSearchTablesColumns((v.target as any).value)
              }}
            >
              {partTypes.map((part) => (
                <option key={part.value} value={part.value}>{part.label}</option>
              ))}
            </Form.Select>
          </Col>
          <Col xs={2} className="text-right">
            차량 선택
            <Form.Select
              size="sm"
              className="ml-2"
              value={selectedCar}
              onChange={(v) => setSelectedCar((v.target as any).value)}
            >
              {carsList.map((car) => (
                <option key={car} value={car}>{car}</option>
              ))}
            </Form.Select>
          </Col>
          <Col xs={2} className="text-right">
            모델 선택
            <Form.Select
              size="sm"
              className="ml-2"
              value={selectedModel}
              onChange={(v) => setSelectedModel((v.target as any).value)}
            >
              {modelList.map((model) => (
                <option key={model} value={model}>{model}</option>
              ))}
            </Form.Select>
          </Col>
          <Col xs={5}>
            <Row className="ml-5">
              기간
              <Col xs={5}>
                <DatePicker
                  className="text-dark"
                  dateFormat="yyyy-MM-dd"
                  selected={fromDate}
                  onChange={(v: Date) => {
                    setFromDate(v)
                  }}
                />
              </Col>
              <div className="font-weight-bold">~</div>
              <Col xs={5}>
                <DatePicker
                  className="text-dark"
                  dateFormat="yyyy-MM-dd"
                  selected={toDate}
                  onChange={(v: Date) => {
                    setToDate(v)
                  }}
                />
              </Col>
            </Row>
          </Col>
          <Col xs={1}>
            <div style={{float: 'right'}}>
              <Button type="button" onClick={() => {
                handleSearchData()
              }}>검색</Button>
            </div>
          </Col>
        </Row>
      </Section> <Section title="결과 조회">
      <Col xl={12} className="w-100">
        <Row className="overflow-auto">
          <Table
            columns={tableColumn}
            data={predictedData}
            paginationOptions={{
              pageSize: 20,
              pageIndex: 0,
            }}
          />
          {/*<div id="paginator" className="pt-4">
            <Paginator
              pageCount={totalPage || 0}
              size={paginate?.pageSize || 0}
              selectedPage={paginate?.pageNumber || 0}
              onChange={(v) => {
                const newPaginate = {
                  ...paginate,
                  pageNumber: v,
                };
                setPaginate(newPaginate)
                handleSearchData(newPaginate)
              }}
            />
          </div>*/}
        </Row>
        <Row className="float-right">
          <Button type="button" className="btn btn-primary m-lg-1"
                  onClick={() => {
                    onClickSaveButtonHandler()
                  }}>
            결과 저장
          </Button>
        </Row>
      </Col>
    </Section>
    </Container>
  );
};


