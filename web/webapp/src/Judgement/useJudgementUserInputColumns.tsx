import React, {useMemo, useState} from "react";
import {Column} from "react-table";
import {
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

export const useJudgementUserInputColumns: (partType: string) => any = (partType:string) => {

  const [updateDefectUserList, setUpdateDefectUserList] = useState<{ idx: number; userJudgement: number }[]>([])

  function radioButtonHandler(value: any, idx: any) {
    var existUpdateList = updateDefectUserList
    var index = existUpdateList.findIndex(el => el.idx === idx);

    if (index === -1) {
      return false
    } else {
      return existUpdateList[index].userJudgement == value
    }
  }

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
    console.log(existUpdateList)
    setUpdateDefectUserList(existUpdateList)
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
    []
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
    []
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
    []
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
    []
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
    []
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
    []
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
    []
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
    []
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
    []
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
    []
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
    []
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
    []
  );

  var resultColumns: any[] = []
  switch (partType) {
    case "BLB":
      // Bearing Left Ball
      resultColumns = SensorBearingLeftBallColumns;
      break
    case "BLI":
      // Bearing Left Inside
      resultColumns = SensorBearingLeftInsideColumns;
      break
    case "BLO":
      // Bearing Left Outside
      resultColumns = SensorBearingLeftOutsideColumns;
      break
    case "BLR":
      // Bearing Left Retainer
      resultColumns = SensorBearingLeftRetainerColumns;
      break
    case "BRB":
      // Bearing Right Ball
      resultColumns = SensorBearingRightBallColumns;
      break
    case "BRI":
      // Bearing Right Inside
      resultColumns = SensorBearingRightInsideColumns;
      break
    case "BRO":
      // Bearing Right Outside
      resultColumns = SensorBearingRightOutsideColumns;
      break
    case "BRR":
      // Bearing Right Retainer
      resultColumns = SensorBearingRightRetainerColumns;
      break
    case "WL":
      // Wheel Left
      resultColumns = SensorWheelLeftColumns;
      break
    case "WR":
      // Wheel Right
      resultColumns = SensorWheelRightColumns;
      break
    case "G":
      // Gearbox
      resultColumns = SensorGearboxColumns;
      break
    case "E":
      // Engine
      resultColumns = SensorEngineColumns;
      break
  }

  return [resultColumns, updateDefectUserList];
};