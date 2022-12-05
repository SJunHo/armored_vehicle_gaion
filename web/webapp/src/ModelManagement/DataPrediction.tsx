import React, {useCallback, useContext, useMemo, useState,} from "react"
import Button from "react-bootstrap/Button";
import Spinner from "react-bootstrap/Spinner";
import Col from "react-bootstrap/Col";
import Container from "react-bootstrap/Container";
import Form from "react-bootstrap/Form";
import Row from "react-bootstrap/Row";
import {useTranslation} from "react-i18next";
import {Column, Row as TableRow} from "react-table";
import {DataInputOption, DataProvider, DbModelResponse, OpenApiContext, Pageable, SensorTempLife} from "../api";
import {ALGORITHM_INFO} from "../common/Common";
import {Section} from "../common/Section/Section";
import {Table} from "../common/Table";
import {Paginator} from "../common/Paginator";

export const DataPrediction: React.FC<{ algorithmName: string }> = ({algorithmName}) => {
  const [predicting, setPredicting] = useState(false);
  const [saving, setSaving] = useState(false);
  const [searchingData, setSearchingData] = useState(false);
  const [searchingModels, setSearchingModels] = useState(false);
  const [models, setModels] = useState<DbModelResponse[]>([]);
  const [selectedModel, setSelectedModel] = useState<DbModelResponse>();
  const [conditionData, setConditionData] = useState<any[]>([]);
  const [selectedData, setSelectedData] = useState<any[]>();
  const [selectedDataIdx, setSelectedDataIdx] = useState<any[]>();
  const [wb, setWb] = useState<string>("");
  const [tableColumns, setTableColumns] = useState<any>([]);
  const [targetClassCol, setTargetClassCol] = useState<string>("");
  const [totalPage, setTotalPage] = useState<number>(1);
  const [paginate, setPaginate] = useState<Pageable>();

  const {datasetDatabaseControllerApi, mlControllerApi} = useContext(OpenApiContext);
  const {t} = useTranslation();

  type SensorBearingLeftBallInput = {
    idx: number, ai_LBSF: string, ai_LBSF_ALGO: string, ai_LBSF_MODEL: string, ai_LBSF_DATE: string,
    w_RPM: number, l_B_V_1X: number, l_B_V_6912BSF: number, l_B_V_32924BSF: number, l_B_V_32922BSF: number,
    l_B_V_Crestfactor: number, l_B_V_Demodulation: number, l_B_S_Fault1: number, l_B_S_Fault2: number, l_B_T_Temperature: number,
    ac_h: number, ac_v: number, ac_a: number, date: string
  }

  type SensorBearingLeftInsideInput = {
    idx: number, ai_LBPFI: string, ai_LBPFI_ALGO: string, ai_LBPFI_MODEL: string, ai_LBPFI_DATE: string,
    w_RPM: number, l_B_V_1X: number, l_B_V_6912BPFI: number, l_B_V_32924BPFI: number, l_B_V_32922BPFI: number,
    l_B_V_Crestfactor: number, l_B_V_Demodulation: number, l_B_S_Fault1: number, l_B_S_Fault2: number, l_B_T_Temperature: number,
    ac_h: number, ac_v: number, ac_a: number, date: string
  }

  type SensorBearingLeftOutsideInput = {
    idx: number, ai_LBPFO: string, ai_LBPFO_ALGO: string, ai_LBPFO_MODEL: string, ai_LBPFO_DATE: string,
    w_RPM: number, l_B_V_1X: number, l_B_V_6912BPFO: number, l_B_V_32924BPFO: number, l_B_V_32922BPFO: number,
    l_B_V_Crestfactor: number, l_B_V_Demodulation: number, l_B_S_Fault1: number, l_B_S_Fault2: number, l_B_T_Temperature: number,
    ac_h: number, ac_v: number, ac_a: number, date: string
  }

  type SensorBearingLeftRetainerInput = {
    idx: number, ai_LFTF: string, ai_LFTF_ALGO: string, ai_LFTF_MODEL: string, ai_LFTF_DATE: string,
    w_RPM: number, l_B_V_1X: number, l_B_V_6912FTF: number, l_B_V_32924FTF: number, l_B_V_32922FTF: number,
    l_B_V_Crestfactor: number, l_B_V_Demodulation: number, l_B_S_Fault1: number, l_B_S_Fault2: number, l_B_T_Temperature: number,
    ac_h: number, ac_v: number, ac_a: number, date: string
  }

  type SensorBearingRightBallInput = {
    idx: number, ai_RBSF: string, ai_RBSF_ALGO: string, ai_RBSF_MODEL: string, ai_RBSF_DATE: string,
    w_RPM: number, r_B_V_1X: number, r_B_V_6912BSF: number, r_B_V_32924BSF: number, r_B_V_32922BSF: number,
    r_B_V_Crestfactor: number, r_B_V_Demodulation: number, r_B_S_Fault1: number, r_B_S_Fault2: number, r_B_T_Temperature: number,
    ac_h: number, ac_v: number, ac_a: number, date: string
  }

  type SensorBearingRightInsideInput = {
    idx: number, ai_RBPFI: string, ai_RBPFI_ALGO: string, ai_RBPFI_MODEL: string, ai_RBPFI_DATE: string,
    w_RPM: number, r_B_V_1X: number, r_B_V_6912BPFI: number, r_B_V_32924BPFI: number, r_B_V_32922BPFI: number,
    r_B_V_Crestfactor: number, r_B_V_Demodulation: number, r_B_S_Fault1: number, r_B_S_Fault2: number, r_B_T_Temperature: number,
    ac_h: number, ac_v: number, ac_a: number, date: string
  }

  type SensorBearingRightOutsideInput = {
    idx: number, ai_RBPFO: string, ai_RBPFO_ALGO: string, ai_RBPFO_MODEL: string, ai_RBPFO_DATE: string,
    w_RPM: number, r_B_V_1X: number, r_B_V_6912BPFO: number, r_B_V_32924BPFO: number, r_B_V_32922BPFO: number,
    r_B_V_Crestfactor: number, r_B_V_Demodulation: number, r_B_S_Fault1: number, r_B_S_Fault2: number, r_B_T_Temperature: number,
    ac_h: number, ac_v: number, ac_a: number, date: string
  }

  type SensorBearingRightRetainerInput = {
    idx: number, ai_RFTF: string, ai_RFTF_ALGO: string, ai_RFTF_MODEL: string, ai_RFTF_DATE: string,
    w_RPM: number, r_B_V_1X: number, r_B_V_6912FTF: number, r_B_V_32924FTF: number, r_B_V_32922FTF: number,
    r_B_V_Crestfactor: number, r_B_V_Demodulation: number, r_B_S_Fault1: number, r_B_S_Fault2: number, r_B_T_Temperature: number,
    ac_h: number, ac_v: number, ac_a: number, date: string
  }

  type SensorWheelLeftInput = {
    idx: number, ai_LW: string, ai_LW_ALGO: string, ai_LW_MODEL: string, ai_LW_DATE: string,
    w_RPM: number, l_W_V_2X: number, l_W_V_3X: number, l_W_S_Fault3: number,
    ac_h: number, ac_v: number, ac_a: number, date: string
  }

  type SensorWheelRightInput = {
    idx: number, ai_RW: string, ai_RW_ALGO: string, ai_RW_MODEL: string, ai_RW_DATE: string,
    w_RPM: number, r_W_V_2X: number, r_W_V_3X: number, r_W_S_Fault3: number,
    ac_h: number, ac_v: number, ac_a: number, date: string
  }

  type SensorGearboxInput = {
    idx: number, ai_GEAR: string, ai_GEAR_ALGO: string, ai_GEAR_MODEL: string, ai_GEAR_DATE: string,
    w_RPM: number, g_V_OverallRMS: number, g_V_Wheel1X: number, g_V_Wheel2X: number,
    g_V_Pinion1X: number, g_V_Pinion2X: number, g_V_GMF1X: number, g_V_GMF2X: number,
    ac_h: number, ac_v: number, ac_a: number, date: string
  }

  type SensorEngineInput = {
    idx: number, ai_ENGINE: string, ai_ENGINE_ALGO: string, ai_ENGINE_MODEL: string, ai_ENGINE_DATE: string,
    w_RPM: number, e_V_OverallRMS: number, e_V_1_2X: number, e_V_1X: number,
    e_V_Crestfactor: number, ac_h: number, ac_v: number, ac_a: number, date: string
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

  const SensorTempLifeDataColumns = useMemo<Column<SensorTempLife>[]>(
    () => [
      {
        Header: "예측 결과",
        accessor: "aiPredict",
      },
      {
        Header: "알고리즘",
        accessor: "aiAlgorithm",
      },
      {
        Header: "모델이름",
        accessor: "aiModel",
      },
      {
        Header: "ID",
        accessor: "idx",
      },
      {
        Header: "날짜",
        accessor: "time",
      },
      {
        Header: "Cpu Util",
        accessor: "cpuUtil",
      },
      {
        Header: "Disk Accesses",
        accessor: "diskAccesses",
      },
      {
        Header: "Disk Blocks",
        accessor: "diskBlocks",
      }, {
        Header: "Disk Util",
        accessor: "diskUtil",
      },
      {
        Header: "INST RETIRED",
        accessor: "instRetired",
      },
      {
        Header: "Last Level",
        accessor: "lastLevel",
      },
      {
        Header: "Memory Bus",
        accessor: "memoryBus",
      },
      {
        Header: "Core Cycle",
        accessor: "coreCycle",
      },
    ],
    []
  );

  const SensorBearingLeftBallColumns = useMemo<Column<SensorBearingLeftBallInput>[]>(
    () => [
      {
        Header: "ID",
        accessor: "idx",
      },
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
        Header: "ID",
        accessor: "idx",
      },
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
        Header: "ID",
        accessor: "idx",
      },
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
        Header: "ID",
        accessor: "idx",
      },
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
        Header: "ID",
        accessor: "idx",
      },
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
        Header: "ID",
        accessor: "idx",
      },
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
        Header: "ID",
        accessor: "idx",
      },
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
        Header: "ID",
        accessor: "idx",
      },
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
        Header: "ID",
        accessor: "idx",
      },
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
        Header: "ID",
        accessor: "idx",
      },
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
        Header: "ID",
        accessor: "idx",
      },
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
        Header: "ID",
        accessor: "idx",
      },
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

  const handleConditionSelected =
    useCallback((v: TableRow<any[]>[]) => {
      setSelectedData(v?.map((i) => i.original))
      setSelectedDataIdx(v?.map((i) => i.values.idx))
    }, []);

  const handleModelSelected = useCallback((v: TableRow<DbModelResponse>[]) => {
    setSelectedModel(v[0]?.original);
  }, []);

  // const handleConditionDataSelected = (algorithmName === "linear" || algorithmName === "lasso"
  //         ? useCallback((v: TableRow<SensorTempLife>[]) => {setSelectedData(v?.map((i) => i.original))}, [])
  //         : useCallback((v: TableRow<SensorBearing>[]) => {setSelectedData(v?.map((i) => i.original))},[])
  // );

  // useEffect(() => {
  //   mlControllerApi
  //     ?.getModels(ALGORITHM_INFO[algorithmName].className)
  //     .then((data) => {
  //       setModels((data.data || []).filter((model) => model.checked));
  //     });
  // }, [mlControllerApi, algorithmName]);

  function handleSearchModel(wb: any) {
    setSearchingModels(true);
    if (wb === "") {
      alert("부품을 먼저 선택해 주세요.")
      setSearchingModels(false)
    } else {
      mlControllerApi
        ?.getModels(ALGORITHM_INFO[algorithmName].className)
        .then((data) => {
          setModels((data.data || []).filter((model) => model.checked && model.partType === wb));
        })
        .finally(() => setSearchingModels(false));
    }
  }

  function handleSearchConditionData(wb: any, pageable?: Pageable) {
    setSearchingData(true);
    if (wb === "") {
      alert("부품을 먼저 선택해 주세요.")
      setSearchingData(false)
    } else {
      if (["BLB", "BLI", "BLO", "BLR", "BRB", "BRI", "BRO", "BRR"].includes(wb)) {
        datasetDatabaseControllerApi?.getUnlabeledBearingData(wb, pageable?.pageNumber, pageable?.pageSize)
          .then((res) => {
            setConditionData(res.data.content || [])
            setTotalPage(res.data.totalPages || 1)
            setPaginate(res.data.pageable);
          })
          .finally(() => setSearchingData(false));
      } else if (["WL", "WR"].includes(wb)) {
        datasetDatabaseControllerApi?.getUnlabeledWheelData(wb, pageable?.pageNumber, pageable?.pageSize)
          .then((res) => {
            setConditionData(res.data.content || [])
          })
          .finally(() => setSearchingData(false));
      } else if (wb === "G") {
        datasetDatabaseControllerApi?.getUnlabeledGearboxData(wb, pageable?.pageNumber, pageable?.pageSize)
          .then((res) => {
            setConditionData(res.data.content || [])
          })
          .finally(() => setSearchingData(false));
      } else if (wb === "E") {
        datasetDatabaseControllerApi?.getUnlabeledEngineData(wb, pageable?.pageNumber, pageable?.pageSize)
          .then((res) => {
            setConditionData(res.data.content || [])
          })
          .finally(() => setSearchingData(false));
      } else if (wb === "T") {
        datasetDatabaseControllerApi?.getUnlabeledTempLifeData(wb, pageable?.pageNumber, pageable?.pageSize)
          .then((res) => {
            setConditionData(res.data.content || [])
          })
          .finally(() => setSearchingData(false));
      }
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
      case "T":
        // Engine
        setTableColumns(SensorTempLifeDataColumns);
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
      case "T":
        // Engine
        setTargetClassCol("TEMP_LIFE");
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
          row[targetClassCol] = resultArr[resultArr.length - 1];
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

  async function handleUpdateData() {
    setSaving(true);
    datasetDatabaseControllerApi
      ?.updateData(
        selectedData!.map((inputs) => ({
          partType: wb,
          id: inputs.idx,
          aiAlgorithmName: inputs.aiAlgorithm,
          aiPredict: inputs[targetClassCol],
          aiModelName: inputs.aiModel,
        }))
      )
      .finally(() => setSaving(false));
  }

  // async function handleTempLifeUpdateData() {
  //   setSaving(true);
  //   datasetDatabaseControllerApi
  //     ?.updateData(
  //       selectedData!.map((inputs) => ({
  //         partType: wb,
  //         id: inputs.idx,
  //         aiAlgorithmName: inputs.aiAlgorithm,
  //         aiPredict: inputs[targetClassCol],
  //         aiModelName: inputs.aiModel,
  //         aiPredictDate: new Date().toLocaleString("ko-KR"),
  //       }))
  //     )
  //     .finally(() => setSaving(false));
  // }

  return (
    <Container fluid>
      <Section title="고장전조 예측 수행" className="mb-2">
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
              <option value="">선택해 주세요.</option>
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
              <option value="T">잔존수명(임시)</option>
            </Form.Select>
          </Col>
          <Col xs={1} className="Col ps-0" style={{marginLeft: "50px"}}>
            <Button
              className="button btn-block font-monospace fw-bold"
              onClick={() => {
                handleSearchModel(wb)
              }}
              size="sm"
            >
              모델 조회
            </Button>
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
              onClick={() => {
                handleSearchConditionData(wb)
                handleSearchTablesColumns(wb)
                handleSettingClassColByPart(wb)
              }}
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
      <Section title="고장전조 예측 결과" className="mb-2">
        <Col xl={12}>
          <div className="w-100 overflow-auto">
            {(totalPage) &&
							<Table
								columns={tableColumns}
								data={conditionData}
								onRowsSelected={handleConditionSelected}
							/>}
          </div>
          <div style={{display: 'inline-block'}}>
            <Paginator
              pageCount={totalPage}
              size={paginate?.pageSize || 0}
              selectedPage={paginate?.pageNumber || 0}
              onChange={(v) => {
                const newPaginate = {
                  ...paginate,
                  pageNumber: v,
                };
                setPaginate(newPaginate);
                handleSearchConditionData(wb, newPaginate);
              }}
            />
          </div>
        </Col>
        <Row className="row justify-content-end">
          <Col className="Col col-1 d-grid gap-2">
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
