import React, {useCallback, useContext, useEffect, useMemo, useState,} from "react"
import Button from "react-bootstrap/Button";
import Spinner from "react-bootstrap/Spinner";
import Col from "react-bootstrap/Col";
import Container from "react-bootstrap/Container";
import Form from "react-bootstrap/Form";
import Row from "react-bootstrap/Row";
import {useTranslation} from "react-i18next";
import {Column, Row as TableRow} from "react-table";
import {DataInputOption, DataProvider, DbModelResponse, OpenApiContext, Pageable} from "../api";
import {ALGORITHM_INFO} from "../common/Common";
import {Section} from "../common/Section/Section";
import {Table} from "../common/Table";

export const DataPrediction: React.FC<{ algorithmName: string }> = ({algorithmName}) => {
  const [predicting, setPredicting] = useState(false);
  const [saving, setSaving] = useState(false);
  const [searchingData, setSearchingData] = useState(false);
  const [searchingModels, setSearchingModels] = useState(false);
  const [models, setModels] = useState<DbModelResponse[]>([]);
  const [selectedModel, setSelectedModel] = useState<DbModelResponse>();
  const [conditionData, setConditionData] = useState<any[]>([]);
  const [selectedData, setSelectedData] = useState<any[]>([]);
  const [selectedDataIdx, setSelectedDataIdx] = useState<any[]>([]);
  const [wb, setWb] = useState<string>("");
  const [tableColumns, setTableColumns] = useState<any>([]);
  const [targetClassCol, setTargetClassCol] = useState<string>("");
  const [fromDate, setFromDate] = useState<Date>();
  const [toDate, setToDate] = useState<Date>(new Date());

  useEffect(() => {
    const thisDate = new Date();
    thisDate.setMonth(thisDate.getMonth() - 6);
    setFromDate(thisDate);
  }, []);

  const {datasetDatabaseControllerApi, mlControllerApi} = useContext(OpenApiContext);
  const {t} = useTranslation();

  const wholeBearingCycle = 540000
  const wholeWheelCycle = 160000
  const wholeGearboxCycle = 1080000
  const wholeEngineCycle = 480000

  type SensorBearingLeftBallInput = {
    idx: number, sdaid: string, ai_LBSF: string, ai_LBSF_ALGO: string, ai_LBSF_MODEL: string, ai_LBSF_DATE: string,
    w_RPM: number, l_B_V_1X: number, l_B_V_6912BSF: number, l_B_V_32924BSF: number, l_B_V_32922BSF: number,
    l_B_V_Crestfactor: number, l_B_V_Demodulation: number, l_B_S_Fault1: number, l_B_S_Fault2: number, l_B_T_Temperature: number,
    ac_h: number, ac_v: number, ac_a: number, date: string
  }

  type SensorBearingLeftInsideInput = {
    idx: number, sdaid: string, ai_LBPFI: string, ai_LBPFI_ALGO: string, ai_LBPFI_MODEL: string, ai_LBPFI_DATE: string,
    w_RPM: number, l_B_V_1X: number, l_B_V_6912BPFI: number, l_B_V_32924BPFI: number, l_B_V_32922BPFI: number,
    l_B_V_Crestfactor: number, l_B_V_Demodulation: number, l_B_S_Fault1: number, l_B_S_Fault2: number, l_B_T_Temperature: number,
    ac_h: number, ac_v: number, ac_a: number, date: string
  }

  type SensorBearingLeftOutsideInput = {
    idx: number, sdaid: string, ai_LBPFO: string, ai_LBPFO_ALGO: string, ai_LBPFO_MODEL: string, ai_LBPFO_DATE: string,
    w_RPM: number, l_B_V_1X: number, l_B_V_6912BPFO: number, l_B_V_32924BPFO: number, l_B_V_32922BPFO: number,
    l_B_V_Crestfactor: number, l_B_V_Demodulation: number, l_B_S_Fault1: number, l_B_S_Fault2: number, l_B_T_Temperature: number,
    ac_h: number, ac_v: number, ac_a: number, date: string
  }

  type SensorBearingLeftRetainerInput = {
    idx: number, sdaid: string, ai_LFTF: string, ai_LFTF_ALGO: string, ai_LFTF_MODEL: string, ai_LFTF_DATE: string,
    w_RPM: number, l_B_V_1X: number, l_B_V_6912FTF: number, l_B_V_32924FTF: number, l_B_V_32922FTF: number,
    l_B_V_Crestfactor: number, l_B_V_Demodulation: number, l_B_S_Fault1: number, l_B_S_Fault2: number, l_B_T_Temperature: number,
    ac_h: number, ac_v: number, ac_a: number, date: string
  }

  type SensorBearingRightBallInput = {
    idx: number, sdaid: string, ai_RBSF: string, ai_RBSF_ALGO: string, ai_RBSF_MODEL: string, ai_RBSF_DATE: string,
    w_RPM: number, r_B_V_1X: number, r_B_V_6912BSF: number, r_B_V_32924BSF: number, r_B_V_32922BSF: number,
    r_B_V_Crestfactor: number, r_B_V_Demodulation: number, r_B_S_Fault1: number, r_B_S_Fault2: number, r_B_T_Temperature: number,
    ac_h: number, ac_v: number, ac_a: number, date: string
  }

  type SensorBearingRightInsideInput = {
    idx: number, sdaid: string, ai_RBPFI: string, ai_RBPFI_ALGO: string, ai_RBPFI_MODEL: string, ai_RBPFI_DATE: string,
    w_RPM: number, r_B_V_1X: number, r_B_V_6912BPFI: number, r_B_V_32924BPFI: number, r_B_V_32922BPFI: number,
    r_B_V_Crestfactor: number, r_B_V_Demodulation: number, r_B_S_Fault1: number, r_B_S_Fault2: number, r_B_T_Temperature: number,
    ac_h: number, ac_v: number, ac_a: number, date: string
  }

  type SensorBearingRightOutsideInput = {
    idx: number, sdaid: string, ai_RBPFO: string, ai_RBPFO_ALGO: string, ai_RBPFO_MODEL: string, ai_RBPFO_DATE: string,
    w_RPM: number, r_B_V_1X: number, r_B_V_6912BPFO: number, r_B_V_32924BPFO: number, r_B_V_32922BPFO: number,
    r_B_V_Crestfactor: number, r_B_V_Demodulation: number, r_B_S_Fault1: number, r_B_S_Fault2: number, r_B_T_Temperature: number,
    ac_h: number, ac_v: number, ac_a: number, date: string
  }

  type SensorBearingRightRetainerInput = {
    idx: number, sdaid: string, ai_RFTF: string, ai_RFTF_ALGO: string, ai_RFTF_MODEL: string, ai_RFTF_DATE: string,
    w_RPM: number, r_B_V_1X: number, r_B_V_6912FTF: number, r_B_V_32924FTF: number, r_B_V_32922FTF: number,
    r_B_V_Crestfactor: number, r_B_V_Demodulation: number, r_B_S_Fault1: number, r_B_S_Fault2: number, r_B_T_Temperature: number,
    ac_h: number, ac_v: number, ac_a: number, date: string
  }

  type SensorWheelLeftInput = {
    idx: number, sdaid: string, ai_LW: string, ai_LW_ALGO: string, ai_LW_MODEL: string, ai_LW_DATE: string,
    w_RPM: number, l_W_V_2X: number, l_W_V_3X: number, l_W_S_Fault3: number,
    ac_h: number, ac_v: number, ac_a: number, date: string
  }

  type SensorWheelRightInput = {
    idx: number, sdaid: string, ai_RW: string, ai_RW_ALGO: string, ai_RW_MODEL: string, ai_RW_DATE: string,
    w_RPM: number, r_W_V_2X: number, r_W_V_3X: number, r_W_S_Fault3: number,
    ac_h: number, ac_v: number, ac_a: number, date: string
  }

  type SensorGearboxInput = {
    idx: number, sdaid: string, ai_GEAR: string, ai_GEAR_ALGO: string, ai_GEAR_MODEL: string, ai_GEAR_DATE: string,
    w_RPM: number, g_V_OverallRMS: number, g_V_Wheel1X: number, g_V_Wheel2X: number,
    g_V_Pinion1X: number, g_V_Pinion2X: number, g_V_GMF1X: number, g_V_GMF2X: number,
    ac_h: number, ac_v: number, ac_a: number, date: string
  }

  type SensorEngineInput = {
    idx: number, sdaid: string, ai_ENGINE: string, ai_ENGINE_ALGO: string, ai_ENGINE_MODEL: string, ai_ENGINE_DATE: string,
    w_RPM: number, e_V_OverallRMS: number, e_V_1_2X: number, e_V_1X: number,
    e_V_Crestfactor: number, ac_h: number, ac_v: number, ac_a: number, date: string
  }

  type SensorBearingLifeInput = {
    idx: number, sdaid: string, ai_Trip: number, aI_Trip_ALGO: string, aI_Trip_MODEL: string, aI_Trip_DATE: string,
    b_OverallRMS: number, b_1X: number, b_6912BPFO: number, b_6912BPFI: number, b_6912BSF: number, b_6912FTF: number,
    b_32924BPFO: number, b_32924BPFI: number, b_32924BSF: number, b_32924FTF: number,
    b_32922BPFO: number, b_32922BPFI: number, b_32922BSF: number, b_32922FTF: number,
    b_CrestFactor: number, b_Demodulation: number, b_Fault1: number, b_Fault2: number, b_Temperature: number, date: string
  }

  type SensorWheelLifeInput = {
    idx: number, sdaid: string, ai_Trip: number, aI_Trip_ALGO: string, aI_Trip_MODEL: string, aI_Trip_DATE: string,
    w_2X: number, w_3X: number, w_Fault3: number,
  }

  type SensorGearboxLifeInput = {
    idx: number, sdaid: string, ai_Trip: number, aI_Trip_ALGO: string, aI_Trip_MODEL: string, aI_Trip_DATE: string,
    g_OverallRMS: number, g_Wheel1X: number, g_Wheel2X: number, g_Pinion1X: number, g_Pinion2X: number, g_GMF1X: number, g_GMF2X: number
  }

  type SensorEngineLifeInput = {
    idx: number, sdaid: string, ai_Trip: number, aI_Trip_ALGO: string, aI_Trip_MODEL: string, aI_Trip_DATE: string,
    e_OverallRMS: number, e_1_2X: number, e_1X: number, e_CrestFactor: number
  }

  const modelResponseColumns = useMemo<Column<DbModelResponse>[]>(
    () => [
      {
        Header: "모델 이름",
        accessor: "modelName",
      },
      {
        Header: "메모",
        accessor: "description",
      },
      {
        Header: "정확도",
        accessor: "accuracy",
      },
      {
        Header: "RMSE",
        accessor: "rootMeanSquaredError",
      },
    ],
    [t]
  );

  const SensorBearingLeftBallColumns = useMemo<Column<SensorBearingLeftBallInput>[]>(
    () => [
      {
        Header: "예측 결과",
        accessor: (data) => {
          if (data.ai_LBSF === "0.0") {
            return "정상";
          } else if (data.ai_LBSF === "1.0") {
            return "고장";
          } else {
            return "-"
          }
        },
      },
      {
        Header: "차량 ID",
        accessor: "sdaid",
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
      {
        Header: "DATE",
        Cell: (value?: any) => {
          return new Date(value.row.original.date).toLocaleString("ko-KR")
        }
      },
    ],
    []
  );

  const SensorBearingLeftInsideColumns = useMemo<Column<SensorBearingLeftInsideInput>[]>(
    () => [
      {
        Header: "예측 결과",
        accessor: (data) => {
          if (data.ai_LBPFI === "0.0") {
            return "정상";
          } else if (data.ai_LBPFI === "1.0") {
            return "고장";
          } else {
            return "-"
          }
        },
      },
      {
        Header: "차량 ID",
        accessor: "sdaid",
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
      {
        Header: "DATE",
        Cell: (value?: any) => {
          return new Date(value.row.original.date).toLocaleString("ko-KR")
        }
      },
    ],
    []
  );

  const SensorBearingLeftOutsideColumns = useMemo<Column<SensorBearingLeftOutsideInput>[]>(
    () => [
      {
        Header: "예측 결과",
        accessor: (data) => {
          if (data.ai_LBPFO === "0.0") {
            return "정상";
          } else if (data.ai_LBPFO === "1.0") {
            return "고장";
          } else {
            return "-"
          }
        },
      },
      {
        Header: "차량 ID",
        accessor: "sdaid",
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
      {
        Header: "DATE",
        Cell: (value?: any) => {
          return new Date(value.row.original.date).toLocaleString("ko-KR")
        }
      },
    ],
    []
  );

  const SensorBearingLeftRetainerColumns = useMemo<Column<SensorBearingLeftRetainerInput>[]>(
    () => [
      {
        Header: "예측 결과",
        accessor: (data) => {
          if (data.ai_LFTF === "0.0") {
            return "정상";
          } else if (data.ai_LFTF === "1.0") {
            return "고장";
          } else {
            return "-"
          }
        },
      },
      {
        Header: "차량 ID",
        accessor: "sdaid",
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
      {
        Header: "DATE",
        Cell: (value?: any) => {
          return new Date(value.row.original.date).toLocaleString("ko-KR")
        }
      },
    ],
    []
  );

  const SensorBearingRightBallColumns = useMemo<Column<SensorBearingRightBallInput>[]>(
    () => [
      {
        Header: "예측 결과",
        accessor: (data) => {
          if (data.ai_RBSF === "0.0") {
            return "정상";
          } else if (data.ai_RBSF === "1.0") {
            return "고장";
          } else {
            return "-"
          }
        },
      },
      {
        Header: "차량 ID",
        accessor: "sdaid",
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
      {
        Header: "DATE",
        Cell: (value?: any) => {
          return new Date(value.row.original.date).toLocaleString("ko-KR")
        }
      },
    ],
    []
  );

  const SensorBearingRightInsideColumns = useMemo<Column<SensorBearingRightInsideInput>[]>(
    () => [
      {
        Header: "예측 결과",
        accessor: (data) => {
          if (data.ai_RBPFI === "0.0") {
            return "정상";
          } else if (data.ai_RBPFI === "1.0") {
            return "고장";
          } else {
            return "-"
          }
        },
      },
      {
        Header: "차량 ID",
        accessor: "sdaid",
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
      {
        Header: "DATE",
        Cell: (value?: any) => {
          return new Date(value.row.original.date).toLocaleString("ko-KR")
        }
      },
    ],
    []
  );

  const SensorBearingRightOutsideColumns = useMemo<Column<SensorBearingRightOutsideInput>[]>(
    () => [
      {
        Header: "예측 결과",
        accessor: (data) => {
          if (data.ai_RBPFO === "0.0") {
            return "정상";
          } else if (data.ai_RBPFO === "1.0") {
            return "고장";
          } else {
            return "-"
          }
        },
      },
      {
        Header: "차량 ID",
        accessor: "sdaid",
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
      {
        Header: "DATE",
        Cell: (value?: any) => {
          return new Date(value.row.original.date).toLocaleString("ko-KR")
        }
      },
    ],
    []
  );

  const SensorBearingRightRetainerColumns = useMemo<Column<SensorBearingRightRetainerInput>[]>(
    () => [
      {
        Header: "예측 결과",
        accessor: (data) => {
          if (data.ai_RFTF === "0.0") {
            return "정상";
          } else if (data.ai_RFTF === "1.0") {
            return "고장";
          } else {
            return "-"
          }
        },
      },
      {
        Header: "차량 ID",
        accessor: "sdaid",
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
      {
        Header: "DATE",
        Cell: (value?: any) => {
          return new Date(value.row.original.date).toLocaleString("ko-KR")
        }
      },
    ],
    []
  );

  const SensorWheelLeftColumns = useMemo<Column<SensorWheelLeftInput>[]>(
    () => [
      {
        Header: "예측 결과",
        accessor: (data) => {
          if (data.ai_LW === "0.0") {
            return "정상";
          } else if (data.ai_LW === "1.0") {
            return "고장";
          } else {
            return "-"
          }
        },
      },
      {
        Header: "차량 ID",
        accessor: "sdaid",
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
      {
        Header: "DATE",
        Cell: (value?: any) => {
          return new Date(value.row.original.date).toLocaleString("ko-KR")
        }
      },
    ],
    []
  );

  const SensorWheelRightColumns = useMemo<Column<SensorWheelRightInput>[]>(
    () => [
      {
        Header: "예측 결과",
        accessor: (data) => {
          if (data.ai_RW === "0.0") {
            return "정상";
          } else if (data.ai_RW === "1.0") {
            return "고장";
          } else {
            return "-"
          }
        },
      },
      {
        Header: "차량 ID",
        accessor: "sdaid",
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
      {
        Header: "DATE",
        Cell: (value?: any) => {
          return new Date(value.row.original.date).toLocaleString("ko-KR")
        }
      },
    ],
    []
  );

  const SensorGearboxColumns = useMemo<Column<SensorGearboxInput>[]>(
    () => [
      {
        Header: "예측 결과",
        accessor: (data) => {
          if (data.ai_GEAR === "0.0") {
            return "정상";
          } else if (data.ai_GEAR === "1.0") {
            return "고장";
          } else {
            return "-"
          }
        },
      },
      {
        Header: "차량 ID",
        accessor: "sdaid",
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
      {
        Header: "DATE",
        Cell: (value?: any) => {
          return new Date(value.row.original.date).toLocaleString("ko-KR")
        }
      },
    ],
    []
  );

  const SensorEngineColumns = useMemo<Column<SensorEngineInput>[]>(
    () => [
      {
        Header: "예측 결과",
        accessor: (data) => {
          if (data.ai_ENGINE === "0.0") {
            return "정상";
          } else if (data.ai_ENGINE === "1.0") {
            return "고장";
          } else {
            return "-"
          }
        },
      },
      {
        Header: "차량 ID",
        accessor: "sdaid",
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
      {
        Header: "DATE",
        Cell: (value?: any) => {
          return new Date(value.row.original.date).toLocaleString("ko-KR")
        }
      },
    ],
    []
  );

  const SensorBearingLifeColumns = useMemo<Column<SensorBearingLifeInput>[]>(
    () => [
      {
        Header: "ID",
        accessor: "idx",
      },
      {
        Header: "예측 결과(km)",
        accessor: (data) => {
          if (data.ai_Trip === null) {
            return "-"
          } else {
            return Math.round((wholeBearingCycle - data.ai_Trip) * 1000) / 1000
          }
        },
      },
      {
        Header: "B_OverallRMS",
        accessor: "b_OverallRMS",
      },
      {
        Header: "B_1X",
        accessor: "b_1X",
      },
      {
        Header: "B_6912BPFO",
        accessor: "b_6912BPFO",
      },
      {
        Header: "B_6912BPFI",
        accessor: "b_6912BPFI",
      },
      {
        Header: "B_6912BSF",
        accessor: "b_6912BSF",
      },
      {
        Header: "B_6912FTF",
        accessor: "b_6912FTF",
      },
      {
        Header: "B_32924BPFO",
        accessor: "b_32924BPFO",
      },
      {
        Header: "B_32924BPFI",
        accessor: "b_32924BPFI",
      },
      {
        Header: "B_32924BSF",
        accessor: "b_32924BSF",
      },
      {
        Header: "B_32924FTF",
        accessor: "b_32924FTF",
      },
      {
        Header: "B_32922BPFO",
        accessor: "b_32922BPFO",
      },
      {
        Header: "B_32922BPFI",
        accessor: "b_32922BPFI",
      },
      {
        Header: "B_32922BSF",
        accessor: "b_32922BSF",
      },
      {
        Header: "B_32922FTF",
        accessor: "b_32922FTF",
      },
      {
        Header: "B_CrestFactor",
        accessor: "b_CrestFactor",
      },
      {
        Header: "B_Demodulation",
        accessor: "b_Demodulation",
      },
      {
        Header: "B_Fault1",
        accessor: "b_Fault1",
      },
      {
        Header: "B_Fault2",
        accessor: "b_Fault2",
      },
      {
        Header: "B_Temperature",
        accessor: "b_Temperature",
      },
      {
        Header: "DATE",
        Cell: (value?: any) => {
          return new Date(value.row.original.date).toLocaleString("ko-KR")
        }
      },
    ],
    []
  );

  const SensorWheelLifeColumns = useMemo<Column<SensorWheelLifeInput>[]>(
    () => [
      {
        Header: "ID",
        accessor: "idx",
      },
      {
        Header: "예측 결과(km)",
        accessor: (data) => {
          if (data.ai_Trip === null) {
            return "-"
          } else {
            return Math.round((wholeWheelCycle - data.ai_Trip) * 1000) / 1000
          }
        },
      },
      {
        Header: "W_2X",
        accessor: "w_2X",
      },
      {
        Header: "W_3X",
        accessor: "w_3X",
      },
      {
        Header: "W_Fault3",
        accessor: "w_Fault3",
      },
      {
        Header: "DATE",
        Cell: (value?: any) => {
          return new Date(value.row.original.date).toLocaleString("ko-KR")
        }
      },
    ],
    []
  );

  const SensorGearboxLifeColumns = useMemo<Column<SensorGearboxLifeInput>[]>(
    () => [
      {
        Header: "ID",
        accessor: "idx",
      },
      {
        Header: "예측 결과(km)",
        accessor: (data) => {
          if (data.ai_Trip === null) {
            return "-"
          } else {
            return Math.round((wholeGearboxCycle - data.ai_Trip) * 1000) / 1000
          }
        },
      },
      {
        Header: "G_OverallRMS",
        accessor: "g_OverallRMS",
      },
      {
        Header: "G_Wheel1X",
        accessor: "g_Wheel1X",
      },
      {
        Header: "G_Wheel2X",
        accessor: "g_Wheel2X",
      },
      {
        Header: "G_Pinion1X",
        accessor: "g_Pinion1X",
      },
      {
        Header: "G_Pinion2X",
        accessor: "g_Pinion2X",
      },
      {
        Header: "G_GMF1X",
        accessor: "g_GMF1X",
      },
      {
        Header: "G_GMF2X",
        accessor: "g_GMF2X",
      },
      {
        Header: "DATE",
        Cell: (value?: any) => {
          return new Date(value.row.original.date).toLocaleString("ko-KR")
        }
      },
    ],
    []
  );

  const SensorEngineLifeColumns = useMemo<Column<SensorEngineLifeInput>[]>(
    () => [
      {
        Header: "ID",
        accessor: "idx",
      },
      {
        Header: "예측 결과(km)",
        accessor: (data) => {
          if (data.ai_Trip === null) {
            return "-"
          } else {
            return Math.round((wholeEngineCycle - data.ai_Trip) * 1000) / 1000
          }
        },
      },
      {
        Header: "E_OverallRMS",
        accessor: "e_OverallRMS",
      },
      {
        Header: "E_1_2X",
        accessor: "e_1_2X",
      },
      {
        Header: "E_1X",
        accessor: "e_1X",
      },
      {
        Header: "E_CrestFactor",
        accessor: "e_CrestFactor",
      },
      {
        Header: "DATE",
        Cell: (value?: any) => {
          return new Date(value.row.original.date).toLocaleString("ko-KR")
        }
      },
    ],
    []
  );

  const handleConditionSelected =
    useCallback((v: TableRow<any[]>[]) => {
      setSelectedData(v?.map((i) => i.original))
      setSelectedDataIdx(v?.map((i) => i.values.idx))
    }, []);

  const handleModelSelected = useCallback((v: TableRow<DbModelResponse>[]) => {
    setSelectedModel(v[0]?.original);
  }, [selectedModel]);

  function handleSearchModel() {
    if (wb === "" || null) {
      alert("부품이 선택되지 않았습니다.")
      setPredicting(false)
      return null
    }
    setSearchingModels(true);
    mlControllerApi
      ?.getModels(ALGORITHM_INFO[algorithmName].className)
      .then((data) => {
        setModels((data.data || []).filter((model) => model.checked && model.partType === wb));
      })
      .finally(() => setSearchingModels(false));
  }

  function handleSearchConditionData(wb: any, paginate?: Pageable) {
    if (wb === "" || null) {
      alert("부품이 선택되지 않았습니다.")
      setPredicting(false)
      return null
    }
    handleSearchTablesColumns(wb)
    handleSettingClassColByPart(wb)
    setSearchingData(true);
    if (["BLB", "BLI", "BLO", "BLR", "BRB", "BRI", "BRO", "BRR"].includes(wb)) {
      datasetDatabaseControllerApi?.getUnlabeledBearingData(wb, fromDate?.toLocaleDateString("en-US"), toDate?.toLocaleDateString("en-US"), 0, 10000)
        .then((res) => {
          setConditionData(res.data.content || [])
        })
        .finally(() => setSearchingData(false));
    } else if (["WL", "WR"].includes(wb)) {
      datasetDatabaseControllerApi?.getUnlabeledWheelData(wb, fromDate?.toLocaleDateString("en-US"), toDate?.toLocaleDateString("en-US"), 0, 10000)
        .then((res) => {
          setConditionData(res.data.content || [])
        })
        .finally(() => setSearchingData(false));
    } else if (wb === "G") {
      datasetDatabaseControllerApi?.getUnlabeledGearboxData(wb, fromDate?.toLocaleDateString("en-US"), toDate?.toLocaleDateString("en-US"), 0, 10000)
        .then((res) => {
          setConditionData(res.data.content || [])
        })
        .finally(() => setSearchingData(false));
    } else if (wb === "E") {
      datasetDatabaseControllerApi?.getUnlabeledEngineData(wb, fromDate?.toLocaleDateString("en-US"), toDate?.toLocaleDateString("en-US"), 0, 10000)
        .then((res) => {
          setConditionData(res.data.content || [])
        })
        .finally(() => setSearchingData(false));
    } else if (wb === "B_LIFE") {
      datasetDatabaseControllerApi?.getUnlabeledBearingLifeData(wb, fromDate?.toLocaleDateString("en-US"), toDate?.toLocaleDateString("en-US"), 0, 10000)
        .then((res) => {
          setConditionData(res.data.content || [])
        })
        .finally(() => setSearchingData(false));
    } else if (wb === "W_LIFE") {
      datasetDatabaseControllerApi?.getUnlabeledWheelLifeData(wb, fromDate?.toLocaleDateString("en-US"), toDate?.toLocaleDateString("en-US"), 0, 10000)
        .then((res) => {
          setConditionData(res.data.content || [])
        })
        .finally(() => setSearchingData(false));
    } else if (wb === "G_LIFE") {
      datasetDatabaseControllerApi?.getUnlabeledGearboxLifeData(wb, fromDate?.toLocaleDateString("en-US"), toDate?.toLocaleDateString("en-US"), 0, 10000)
        .then((res) => {
          setConditionData(res.data.content || [])
        })
        .finally(() => setSearchingData(false));
    } else if (wb === "E_LIFE") {
      datasetDatabaseControllerApi?.getUnlabeledEngineLifeData(wb, fromDate?.toLocaleDateString("en-US"), toDate?.toLocaleDateString("en-US"), 0, 10000)
        .then((res) => {
          setConditionData(res.data.content || [])
        })
        .finally(() => setSearchingData(false));
    }
  }

  function handleSearchTablesColumns(wb: any) {
    switch (wb) {
      case "BLB":
        // Bearing Left Ball
        setTableColumns(SensorBearingLeftBallColumns);
        break
      case "BLI":
        // Bearing Left Inside
        setTableColumns(SensorBearingLeftInsideColumns);
        break
      case "BLO":
        // Bearing Left Outside
        setTableColumns(SensorBearingLeftOutsideColumns);
        break
      case "BLR":
        // Bearing Left Retainer
        setTableColumns(SensorBearingLeftRetainerColumns);
        break
      case "BRB":
        // Bearing Right Ball
        setTableColumns(SensorBearingRightBallColumns);
        break
      case "BRI":
        // Bearing Right Inside
        setTableColumns(SensorBearingRightInsideColumns);
        break
      case "BRO":
        // Bearing Right Outside
        setTableColumns(SensorBearingRightOutsideColumns);
        break
      case "BRR":
        // Bearing Right Retainer
        setTableColumns(SensorBearingRightRetainerColumns);
        break
      case "WL":
        // Wheel Left
        setTableColumns(SensorWheelLeftColumns);
        break
      case "WR":
        // Wheel Right
        setTableColumns(SensorWheelRightColumns);
        break
      case "G":
        // Gearbox
        setTableColumns(SensorGearboxColumns);
        break
      case "E":
        // Engine
        setTableColumns(SensorEngineColumns);
        break
      case "B_LIFE":
        // Bearing remaining life
        setTableColumns(SensorBearingLifeColumns);
        break
      case "W_LIFE":
        // Wheel remaining life
        setTableColumns(SensorWheelLifeColumns);
        break
      case "G_LIFE":
        // Gearbox remaining life
        setTableColumns(SensorGearboxLifeColumns);
        break
      case "E_LIFE":
        // Engine remaining life
        setTableColumns(SensorEngineLifeColumns);
        break
    }
  }

  function handleSettingClassColByPart(wb: any) {
    switch (wb) {
      case "BLB":
        // Bearing Left Ball
        setTargetClassCol("ai_LBSF");
        break
      case "BLI":
        // Bearing Left Inside
        setTargetClassCol("ai_LBPFI");
        break
      case "BLO":
        // Bearing Left Outside
        setTargetClassCol("ai_LBPFO");
        break
      case "BLR":
        // Bearing Left Retainer
        setTargetClassCol("ai_LFTF");
        break
      case "BRB":
        // Bearing Right Ball
        setTargetClassCol("ai_RBSF");
        break
      case "BRI":
        // Bearing Right Inside
        setTargetClassCol("ai_RBPFI");
        break
      case "BRO":
        // Bearing Right Outside
        setTargetClassCol("ai_RBPFO");
        break
      case "BRR":
        // Bearing Right Retainer
        setTargetClassCol("ai_RFTF");
        break
      case "WL":
        // Wheel Left
        setTargetClassCol("ai_LW");
        break
      case "WR":
        // Wheel Right
        setTargetClassCol("ai_RW");
        break
      case "G":
        // Gearbox
        setTargetClassCol("ai_GEAR");
        break
      case "E":
        // Engine
        setTargetClassCol("ai_ENGINE");
        break
      case "B_LIFE":
      case "W_LIFE":
      case "G_LIFE":
      case "E_LIFE":
        // part of remaining life
        setTargetClassCol("ai_Trip");
        break
    }
  }

  async function handleClassificationData() {
    const res = await mlControllerApi?.classificationPredict(algorithmName, {
      classCol: targetClassCol,
      modelName: selectedModel?.modelName,
      dataProvider: DataProvider.Ktme,
      dataInputOption: DataInputOption.Db,
      listFieldsForPredict: selectedModel?.listFeatures,
      dataType: wb,
      dbDocIds: selectedDataIdx
    });
    const predictedData = res?.data.predictionInfo || [];
    setConditionData((old) =>
      old.map((row) => {
        const selectedIndex = selectedData!.findIndex(
          (selectedId) => selectedId.idx === row.idx
        );
        let resultArr;
        if (selectedIndex !== -1) {
          resultArr = JSON.parse(
            "[" + predictedData[selectedIndex] + "]"
          )
          row[targetClassCol] = resultArr[resultArr.length - 1];
          row.aiAlgorithm = algorithmName;
          row.aiModel = selectedModel?.modelName;
        }
        return row;
      })
    );
  }

  async function handleRegressionData() {
    const res = await mlControllerApi?.regressionPredict(algorithmName, {
      classCol: targetClassCol,
      modelName: selectedModel?.modelName,
      dataProvider: DataProvider.Ktme,
      dataInputOption: DataInputOption.Db,
      listFieldsForPredict: selectedModel?.listFeatures,
      dataType: wb,
      dbDocIds: selectedDataIdx
    });
    const predictedData = res?.data.predictionInfo || [];
    setConditionData((old) =>
      old.map((row) => {
        const selectedIndex = selectedData!.findIndex(
          (selectedId) => selectedId.idx === row.idx
        );
        let resultArr;
        if (selectedIndex !== -1) {
          resultArr = JSON.parse(
            "[" + predictedData[selectedIndex] + "]"
          )
          row[targetClassCol] = resultArr[0];
          row.aiAlgorithm = algorithmName;
          row.aiModel = selectedModel?.modelName;
        }
        return row;
      })
    );
  }

  async function handleOutlierDetectionData() {
    const res = await mlControllerApi?.predictCluster(algorithmName, {
      classCol: targetClassCol,
      modelName: selectedModel?.modelName,
      dataProvider: DataProvider.Ktme,
      dataInputOption: DataInputOption.Db,
      listFieldsForPredict: selectedModel?.listFeatures,
      dataType: wb,
      dbDocIds: selectedDataIdx
    });
    const predictedData = res?.data.predictionInfo || [];
    setConditionData((old) =>
      old.map((row) => {
        const selectedIndex = selectedData!.findIndex(
          (selectedId) => selectedId.idx === row.idx
        );
        let resultArr;
        if (selectedIndex !== -1) {
          var score = JSON.parse("[" + predictedData[selectedIndex] + "]")[1];
          score > 0.5 ? row[targetClassCol] = "1.0" : row[targetClassCol] = "0.0"
          row.aiAlgorithm = algorithmName;
          row.aiModel = selectedModel?.modelName;
        }
        return row;
      })
    );
  }

  async function handlePredictData() {
    setPredicting(true);
    if (selectedModel?.modelName === undefined || null) {
      alert("모델이 선택되지 않았습니다.")
      setPredicting(false)
    } else {
      if (algorithmName === "if") {
        await handleOutlierDetectionData().finally(() => setPredicting(false));
      } else if (algorithmName === "linear" || algorithmName === "lasso") {
        await handleRegressionData().finally(() => setPredicting(false));
      } else {
        await handleClassificationData().finally(() => setPredicting(false));
      }
    }
  }

  function handleUpdateData() {
    setSaving(true);
    if (algorithmName === "linear" || algorithmName === "lasso") {
      switch (wb) {
        case "B_LIFE":
          datasetDatabaseControllerApi
            ?.updateData(
              selectedData!.map((inputs) => ({
                partType: wb,
                id: inputs.idx,
                aiAlgorithmName: selectedModel?.algorithmType,
                aiPredict: wholeBearingCycle - inputs[targetClassCol],
                aiModelName: selectedModel?.modelName,
              }))
            )
            .finally(() => {
              alert("저장되었습니다")
              setSaving(false)
            });
          break;
        case "W_LIFE":
          datasetDatabaseControllerApi
            ?.updateData(
              selectedData!.map((inputs) => ({
                partType: wb,
                id: inputs.idx,
                aiAlgorithmName: selectedModel?.algorithmType,
                aiPredict: wholeWheelCycle - inputs[targetClassCol],
                aiModelName: selectedModel?.modelName,
              }))
            )
            .finally(() => {
              alert("저장되었습니다")
              setSaving(false)
            });
          break;
        case "G_LIFE":
          datasetDatabaseControllerApi
            ?.updateData(
              selectedData!.map((inputs) => ({
                partType: wb,
                id: inputs.idx,
                aiAlgorithmName: selectedModel?.algorithmType,
                aiPredict: wholeGearboxCycle - inputs[targetClassCol],
                aiModelName: selectedModel?.modelName,
              }))
            )
            .finally(() => {
              alert("저장되었습니다")
              setSaving(false)
            });
          break;
        case "E_LIFE":
          datasetDatabaseControllerApi
            ?.updateData(
              selectedData!.map((inputs) => ({
                partType: wb,
                id: inputs.idx,
                aiAlgorithmName: selectedModel?.algorithmType,
                aiPredict: wholeEngineCycle - inputs[targetClassCol],
                aiModelName: selectedModel?.modelName,
              }))
            )
            .finally(() => {
              alert("저장되었습니다")
              setSaving(false)
            });
          break;
      }
    } else {
      datasetDatabaseControllerApi
        ?.updateData(
          selectedData!.map((inputs) => ({
            partType: wb,
            id: inputs.idx,
            aiAlgorithmName: selectedModel?.algorithmType,
            aiPredict: parseInt(inputs[targetClassCol]),
            aiModelName: selectedModel?.modelName,
          }))
        )
        .finally(() => {
          alert("저장되었습니다")
          setSaving(false)
        });
    }
  }

  return (
    <Container fluid>
      <Section title={algorithmName === "linear" || algorithmName === "lasso" ? "잔존수명 예지 수행" : "고장예지 예측 수행"} className="mb-2">
        <Row className="row mb-2">
          <Col xs={1} className="Col pe-0 text-white">
            부품선택
          </Col>
          <Col xs={1} className="Col ps-0">
            <Form.Select
              size="sm"
              value={wb}
              onChange={(v) => {
                setWb((v.target as any).value)
              }}
            >
              {algorithmName === "linear" || algorithmName === "lasso" ? (
                <>
                  <option value="">선택해주세요.</option>
                  <option value="B_LIFE">베어링</option>
                  <option value="W_LIFE">휠</option>
                  <option value="G_LIFE">감속기(기어박스)</option>
                  <option value="E_LIFE">엔진</option>
                </>
              ) : (
                <>
                  <option value="">선택해주세요.</option>
                  <option value="BLB">베어링 좌측 볼</option>
                  <option value="BLO">베어링 좌측 외륜</option>
                  <option value="BLI">베어링 좌측 내륜</option>
                  <option value="BLR">베어링 좌측 리테이너</option>
                  <option value="BRB">베어링 우측 볼</option>
                  <option value="BRO">베어링 우측 내륜</option>
                  <option value="BRI">베어링 우측 외륜</option>
                  <option value="BRR">베어링 우측 리테이너</option>
                  <option value="WL">차륜 좌측</option>
                  <option value="WR">차륜 우측</option>
                  <option value="G">감속기(기어박스)</option>
                  <option value="E">엔진</option>
                </>
              )}
            </Form.Select>
          </Col>

          <Col xs={1} className="Col ps-0" style={{marginLeft: "50px"}}>
            <Button
              className="button btn-block font-monospace fw-bold"
              onClick={() => {
                handleSearchModel()
              }}
              size="sm"
            >
              모델 조회
            </Button>
          </Col>
          <Col xs={1} className="text-right">기간</Col>
          <Col xs={2}>
            <Form.Control
              size="sm"
              type="date"
              value={fromDate?.toLocaleDateString("en-CA")}
              onChange={(v) => setFromDate(new Date((v.target as any).value))}
            />
          </Col>
          <div className="font-weight-bold">~</div>
          <Col xs={2}>
            <Form.Control
              type="date"
              size="sm"
              value={toDate?.toLocaleDateString("en-CA")}
              onChange={(v) => setToDate(new Date((v.target as any).value))}
            />
          </Col>
        </Row>
        <Row>
          <Col xl={12}>
            <Table
              data={models}
              columns={modelResponseColumns}
              isSingleRowSelect
              onRowsSelected={handleModelSelected}
            />
          </Col>
        </Row>
        <Row>
          <Col className="Col d-grid gap-2">
            <Button
              className="button btn-block font-monospace fw-bold"
              onClick={() => handleSearchConditionData(wb)}
              size="sm"
              disabled={searchingData}
            >
              {searchingData && (
                <Spinner
                  as="span"
                  animation="border"
                  size="sm"
                  role="status"
                  aria-hidden="true"
                />
              )}
              데이터 조회
            </Button>
          </Col>
          <Col className="Col d-grid gap-2">
            <Button
              className="button font-monospace fw-bold w-100"
              onClick={handlePredictData}
              size="sm"
              variant="danger"
              disabled={predicting}
            >
              {predicting && (
                <Spinner
                  as="span"
                  animation="border"
                  size="sm"
                  role="status"
                  aria-hidden="true"
                />
              )}
              예측 수행
            </Button>
          </Col>
        </Row>
      </Section>
      <Section title={algorithmName === "linear" || algorithmName === "lasso" ? "잔존수명 예지 결과" : "고장예지 예측 결과"} className="mb-2">
        <Col xl={12}>
          <div className="w-100 overflow-auto">
            <Table
              columns={tableColumns}
              data={conditionData}
              autoResetSelectedRows={false}
              onRowsSelected={handleConditionSelected}
              getRowId={(row: any) => (row as any).idx}
              paginationOptions={{
                pageSize: 20,
                pageIndex: 0,
              }}
            />
          </div>
        </Col>
        <Row className="row justify-content-end">
          <Col className="Col col-1 d-grid p-0">
            <Button
              className="button font-monospace fw-bold"
              onClick={handleUpdateData}
              size="lg"
              disabled={predicting}
            >
              {saving && (
                <Spinner
                  as="span"
                  animation="border"
                  size="sm"
                  role="status"
                  aria-hidden="true"
                />
              )}
              결과 저장
            </Button>
          </Col>
        </Row>
      </Section>
    </Container>
  );
};
