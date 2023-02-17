import React, {useMemo} from "react";
import {Column} from "react-table";

export type SensorBearingLeftBallInput = {
  idx: number, sdaId: string, ai_LBSF: string, ai_LBSF_ALGO: string, ai_LBSF_MODEL: string, ai_LBSF_DATE: string,
  user_LBSF: string, user_LBSF_ID: string, user_LBSF_DATE: string,
  w_RPM: number, l_B_V_1X: number, l_B_V_6912BSF: number, l_B_V_32924BSF: number, l_B_V_32922BSF: number,
  l_B_V_Crestfactor: number, l_B_V_Demodulation: number, l_B_S_Fault1: number, l_B_S_Fault2: number, l_B_T_Temperature: number,
  ac_h: number, ac_v: number, ac_a: number, date: string
}

export type SensorBearingLeftInsideInput = {
  idx: number, sdaId: string, ai_LBPFI: string, ai_LBPFI_ALGO: string, ai_LBPFI_MODEL: string, ai_LBPFI_DATE: string,
  user_LBPFI: string, user_LBPFI_ID: string, user_LBPFI_DATE: string,
  w_RPM: number, l_B_V_1X: number, l_B_V_6912BPFI: number, l_B_V_32924BPFI: number, l_B_V_32922BPFI: number,
  l_B_V_Crestfactor: number, l_B_V_Demodulation: number, l_B_S_Fault1: number, l_B_S_Fault2: number, l_B_T_Temperature: number,
  ac_h: number, ac_v: number, ac_a: number, date: string
}

export type SensorBearingLeftOutsideInput = {
  idx: number, sdaId: string, ai_LBPFO: string, ai_LBPFO_ALGO: string, ai_LBPFO_MODEL: string, ai_LBPFO_DATE: string,
  user_LBPFO: string, user_LBPFO_ID: string, user_LBPFO_DATE: string,
  w_RPM: number, l_B_V_1X: number, l_B_V_6912BPFO: number, l_B_V_32924BPFO: number, l_B_V_32922BPFO: number,
  l_B_V_Crestfactor: number, l_B_V_Demodulation: number, l_B_S_Fault1: number, l_B_S_Fault2: number, l_B_T_Temperature: number,
  ac_h: number, ac_v: number, ac_a: number, date: string
}

export type SensorBearingLeftRetainerInput = {
  idx: number, sdaId: string, ai_LFTF: string, ai_LFTF_ALGO: string, ai_LFTF_MODEL: string, ai_LFTF_DATE: string,
  user_LFTF: string, user_LFTF_ID: string, user_LFTF_DATE: string,
  w_RPM: number, l_B_V_1X: number, l_B_V_6912FTF: number, l_B_V_32924FTF: number, l_B_V_32922FTF: number,
  l_B_V_Crestfactor: number, l_B_V_Demodulation: number, l_B_S_Fault1: number, l_B_S_Fault2: number, l_B_T_Temperature: number,
  ac_h: number, ac_v: number, ac_a: number, date: string
}

export type SensorBearingRightBallInput = {
  idx: number, sdaId: string, ai_RBSF: string, ai_RBSF_ALGO: string, ai_RBSF_MODEL: string, ai_RBSF_DATE: string,
  user_RBSF: string, user_RBSF_ID: string, user_RBSF_DATE: string,
  w_RPM: number, r_B_V_1X: number, r_B_V_6912BSF: number, r_B_V_32924BSF: number, r_B_V_32922BSF: number,
  r_B_V_Crestfactor: number, r_B_V_Demodulation: number, r_B_S_Fault1: number, r_B_S_Fault2: number, r_B_T_Temperature: number,
  ac_h: number, ac_v: number, ac_a: number, date: string
}

export type SensorBearingRightInsideInput = {
  idx: number, sdaId: string, ai_RBPFI: string, ai_RBPFI_ALGO: string, ai_RBPFI_MODEL: string, ai_RBPFI_DATE: string,
  user_RBPFI: string, user_RBPFI_ID: string, user_RBPFI_DATE: string,
  w_RPM: number, r_B_V_1X: number, r_B_V_6912BPFI: number, r_B_V_32924BPFI: number, r_B_V_32922BPFI: number,
  r_B_V_Crestfactor: number, r_B_V_Demodulation: number, r_B_S_Fault1: number, r_B_S_Fault2: number, r_B_T_Temperature: number,
  ac_h: number, ac_v: number, ac_a: number, date: string
}

export type SensorBearingRightOutsideInput = {
  idx: number, sdaId: string, ai_RBPFO: string, ai_RBPFO_ALGO: string, ai_RBPFO_MODEL: string, ai_RBPFO_DATE: string,
  user_RBPFO: string, user_RBPFO_ID: string, user_RBPFO_DATE: string,
  w_RPM: number, r_B_V_1X: number, r_B_V_6912BPFO: number, r_B_V_32924BPFO: number, r_B_V_32922BPFO: number,
  r_B_V_Crestfactor: number, r_B_V_Demodulation: number, r_B_S_Fault1: number, r_B_S_Fault2: number, r_B_T_Temperature: number,
  ac_h: number, ac_v: number, ac_a: number, date: string
}

export type SensorBearingRightRetainerInput = {
  idx: number, sdaId: string, ai_RFTF: string, ai_RFTF_ALGO: string, ai_RFTF_MODEL: string, ai_RFTF_DATE: string,
  user_RFTF: string, user_RFTF_ID: string, user_RFTF_DATE: string,
  w_RPM: number, r_B_V_1X: number, r_B_V_6912FTF: number, r_B_V_32924FTF: number, r_B_V_32922FTF: number,
  r_B_V_Crestfactor: number, r_B_V_Demodulation: number, r_B_S_Fault1: number, r_B_S_Fault2: number, r_B_T_Temperature: number,
  ac_h: number, ac_v: number, ac_a: number, date: string
}

export type SensorWheelLeftInput = {
  idx: number, sdaId: string, ai_LW: string, ai_LW_ALGO: string, ai_LW_MODEL: string, ai_LW_DATE: string,
  user_LW: string, user_LW_ID: string, user_LW_DATE: string,
  w_RPM: number, l_W_V_2X: number, l_W_V_3X: number, l_W_S_Fault3: number,
  ac_h: number, ac_v: number, ac_a: number, date: string
}

export type SensorWheelRightInput = {
  idx: number, sdaId: string, ai_RW: string, ai_RW_ALGO: string, ai_RW_MODEL: string, ai_RW_DATE: string,
  user_RW: string, user_RW_ID: string, user_RW_DATE: string,
  w_RPM: number, r_W_V_2X: number, r_W_V_3X: number, r_W_S_Fault3: number,
  ac_h: number, ac_v: number, ac_a: number, date: string
}

export type SensorGearboxInput = {
  idx: number, sdaId: string, ai_GEAR: string, ai_GEAR_ALGO: string, ai_GEAR_MODEL: string, ai_GEAR_DATE: string,
  user_GEAR: string, user_GEAR_ID: string, user_GEAR_DATE: string,
  w_RPM: number, g_V_OverallRMS: number, g_V_Wheel1X: number, g_V_Wheel2X: number,
  g_V_Pinion1X: number, g_V_Pinion2X: number, g_V_GMF1X: number, g_V_GMF2X: number,
  ac_h: number, ac_v: number, ac_a: number, date: string
}

export type SensorEngineInput = {
  idx: number, sdaId: string, ai_ENGINE: string, ai_ENGINE_ALGO: string, ai_ENGINE_MODEL: string, ai_ENGINE_DATE: string,
  user_ENGINE: string, user_ENGINE_ID: string, user_ENGINE_DATE: string,
  w_RPM: number, e_V_OverallRMS: number, e_V_1_2X: number, e_V_1X: number,
  e_V_Crestfactor: number, ac_h: number, ac_v: number, ac_a: number, date: string
}

export type SensorBearingLifeInput = {
  idx: number, sdaId: string, ai_Trip: number, aI_Trip_ALGO: string, aI_Trip_MODEL: string, aI_Trip_DATE: string,
  b_OverallRMS: number, b_1X: number, b_6912BPFO: number, b_6912BPFI: number, b_6912BSF: number, b_6912FTF: number,
  b_32924BPFO: number, b_32924BPFI: number, b_32924BSF: number, b_32924FTF: number,
  b_32922BPFO: number, b_32922BPFI: number, b_32922BSF: number, b_32922FTF: number,
  b_CrestFactor: number, b_Demodulation: number, b_Fault1: number, b_Fault2: number, b_Temperature: number, date: string
}

export type SensorWheelLifeInput = {
  idx: number, sdaId: string, ai_Trip: number, aI_Trip_ALGO: string, aI_Trip_MODEL: string, aI_Trip_DATE: string,
  w_2X: number, w_3X: number, w_Fault3: number,
}

export type SensorGearboxLifeInput = {
  idx: number, sdaId: string, ai_Trip: number, aI_Trip_ALGO: string, aI_Trip_MODEL: string, aI_Trip_DATE: string,
  g_OverallRMS: number, g_Wheel1X: number, g_Wheel2X: number, g_Pinion1X: number, g_Pinion2X: number, g_GMF1X: number, g_GMF2X: number
}

export type SensorEngineLifeInput = {
  idx: number, sdaId: string, ai_Trip: number, aI_Trip_ALGO: string, aI_Trip_MODEL: string, aI_Trip_DATE: string,
  e_OverallRMS: number, e_1_2X: number, e_1X: number, e_CrestFactor: number
}

export const partTypes = [
  // bearing
  {
    value: "BLB",
    label: "베어링 좌측 볼",
  },
  {
    value: "BLI",
    label: "베어링 좌측 내륜",
  },
  {
    value: "BLO",
    label: "베어링 좌측 외륜",
  },
  {
    value: "BLR",
    label: "베어링 좌측 리테이너",
  },
  {
    value: "BRB",
    label: "베어링 우측 볼",
  },
  {
    value: "BRI",
    label: "베어링 우측 내륜",
  },
  {
    value: "BRO",
    label: "베어링 우측 외륜",
  },
  {
    value: "BRR",
    label: "베어링 우측 리테이너",
  },
  // wheel
  {
    value: "WL",
    label: "차륜 좌측",
  },
  {
    value: "WR",
    label: "차륜 우측",
  },
  {
    value: "G",
    label: "감속기(기어박스)",
  },
  {
    value: "E",
    label: "엔진",
  }
]

export const useDataPredictionColumns: (partType: string) => any = (partType:string) => {

  const wholeBearingCycle = 540000
  const wholeWheelCycle = 160000
  const wholeGearboxCycle = 1080000
  const wholeEngineCycle = 480000

  const SensorBearingLeftBallColumns = useMemo<Column<SensorBearingLeftBallInput>[]>(
    () => [
      {
        Header: "ID",
        accessor: "idx"
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
    ],
    []
  );

  const SensorBearingLeftInsideColumns = useMemo<Column<SensorBearingLeftInsideInput>[]>(
    () => [
      {
        Header: "ID",
        accessor: "idx"
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
        Header: "ID",
        accessor: "idx"
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
        Header: "ID",
        accessor: "idx"
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
        Header: "ID",
        accessor: "idx"
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
        Header: "ID",
        accessor: "idx"
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
        Header: "ID",
        accessor: "idx"
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
        Header: "ID",
        accessor: "idx"
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
        Header: "ID",
        accessor: "idx"
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
        Header: "ID",
        accessor: "idx"
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
        Header: "ID",
        accessor: "idx"
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
        Header: "ID",
        accessor: "idx"
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

  const SensorBearingLifeColumns = useMemo<Column<SensorBearingLifeInput>[]>(
    () => [
      {
        Header: "ID",
        accessor: "idx"
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
    ],
    []
  );

  const SensorWheelLifeColumns = useMemo<Column<SensorWheelLifeInput>[]>(
    () => [
      {
        Header: "ID",
        accessor: "idx"
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
    ],
    []
  );

  const SensorGearboxLifeColumns = useMemo<Column<SensorGearboxLifeInput>[]>(
    () => [
      {
        Header: "ID",
        accessor: "idx"
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
    ],
    []
  );

  const SensorEngineLifeColumns = useMemo<Column<SensorEngineLifeInput>[]>(
    () => [
      {
        Header: "ID",
        accessor: "idx"
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
    case "B_LIFE":
      // Bearing remaining life
      resultColumns = SensorBearingLifeColumns;
      break
    case "W_LIFE":
      // Wheel remaining life
      resultColumns = SensorWheelLifeColumns;
      break
    case "G_LIFE":
      // Gearbox remaining life
      resultColumns = SensorGearboxLifeColumns;
      break
    case "E_LIFE":
      // Engine remaining life
      resultColumns = SensorEngineLifeColumns;
      break
  }


  return resultColumns;
};