import React, {useMemo} from "react";
import {Column} from "react-table";

export type SensorBearingLeftBallInput = {
  idx: number, ai_LBSF: string, ai_LBSF_ALGO: string, ai_LBSF_MODEL: string, ai_LBSF_DATE: string,
  user_LBSF: string, user_LBSF_ID: string, user_LBSF_DATE: string,
  w_RPM: number, l_B_V_1X: number, l_B_V_6912BSF: number, l_B_V_32924BSF: number, l_B_V_32922BSF: number,
  l_B_V_Crestfactor: number, l_B_V_Demodulation: number, l_B_S_Fault1: number, l_B_S_Fault2: number, l_B_T_Temperature: number,
  ac_h: number, ac_v: number, ac_a: number, date: string
}

export type SensorBearingLeftInsideInput = {
  idx: number, ai_LBPFI: string, ai_LBPFI_ALGO: string, ai_LBPFI_MODEL: string, ai_LBPFI_DATE: string,
  user_LBPFI: string, user_LBPFI_ID: string, user_LBPFI_DATE: string,
  w_RPM: number, l_B_V_1X: number, l_B_V_6912BPFI: number, l_B_V_32924BPFI: number, l_B_V_32922BPFI: number,
  l_B_V_Crestfactor: number, l_B_V_Demodulation: number, l_B_S_Fault1: number, l_B_S_Fault2: number, l_B_T_Temperature: number,
  ac_h: number, ac_v: number, ac_a: number, date: string
}

export type SensorBearingLeftOutsideInput = {
  idx: number, ai_LBPFO: string, ai_LBPFO_ALGO: string, ai_LBPFO_MODEL: string, ai_LBPFO_DATE: string,
  user_LBPFO: string, user_LBPFO_ID: string, user_LBPFO_DATE: string,
  w_RPM: number, l_B_V_1X: number, l_B_V_6912BPFO: number, l_B_V_32924BPFO: number, l_B_V_32922BPFO: number,
  l_B_V_Crestfactor: number, l_B_V_Demodulation: number, l_B_S_Fault1: number, l_B_S_Fault2: number, l_B_T_Temperature: number,
  ac_h: number, ac_v: number, ac_a: number, date: string
}

export type SensorBearingLeftRetainerInput = {
  idx: number, ai_LFTF: string, ai_LFTF_ALGO: string, ai_LFTF_MODEL: string, ai_LFTF_DATE: string,
  user_LFTF: string, user_LFTF_ID: string, user_LFTF_DATE: string,
  w_RPM: number, l_B_V_1X: number, l_B_V_6912FTF: number, l_B_V_32924FTF: number, l_B_V_32922FTF: number,
  l_B_V_Crestfactor: number, l_B_V_Demodulation: number, l_B_S_Fault1: number, l_B_S_Fault2: number, l_B_T_Temperature: number,
  ac_h: number, ac_v: number, ac_a: number, date: string
}

export type SensorBearingRightBallInput = {
  idx: number, ai_RBSF: string, ai_RBSF_ALGO: string, ai_RBSF_MODEL: string, ai_RBSF_DATE: string,
  user_RBSF: string, user_RBSF_ID: string, user_RBSF_DATE: string,
  w_RPM: number, r_B_V_1X: number, r_B_V_6912BSF: number, r_B_V_32924BSF: number, r_B_V_32922BSF: number,
  r_B_V_Crestfactor: number, r_B_V_Demodulation: number, r_B_S_Fault1: number, r_B_S_Fault2: number, r_B_T_Temperature: number,
  ac_h: number, ac_v: number, ac_a: number, date: string
}

export type SensorBearingRightInsideInput = {
  idx: number, ai_RBPFI: string, ai_RBPFI_ALGO: string, ai_RBPFI_MODEL: string, ai_RBPFI_DATE: string,
  user_RBPFI: string, user_RBPFI_ID: string, user_RBPFI_DATE: string,
  w_RPM: number, r_B_V_1X: number, r_B_V_6912BPFI: number, r_B_V_32924BPFI: number, r_B_V_32922BPFI: number,
  r_B_V_Crestfactor: number, r_B_V_Demodulation: number, r_B_S_Fault1: number, r_B_S_Fault2: number, r_B_T_Temperature: number,
  ac_h: number, ac_v: number, ac_a: number, date: string
}

export type SensorBearingRightOutsideInput = {
  idx: number, ai_RBPFO: string, ai_RBPFO_ALGO: string, ai_RBPFO_MODEL: string, ai_RBPFO_DATE: string,
  user_RBPFO: string, user_RBPFO_ID: string, user_RBPFO_DATE: string,
  w_RPM: number, r_B_V_1X: number, r_B_V_6912BPFO: number, r_B_V_32924BPFO: number, r_B_V_32922BPFO: number,
  r_B_V_Crestfactor: number, r_B_V_Demodulation: number, r_B_S_Fault1: number, r_B_S_Fault2: number, r_B_T_Temperature: number,
  ac_h: number, ac_v: number, ac_a: number, date: string
}

export type SensorBearingRightRetainerInput = {
  idx: number, ai_RFTF: string, ai_RFTF_ALGO: string, ai_RFTF_MODEL: string, ai_RFTF_DATE: string,
  user_RFTF: string, user_RFTF_ID: string, user_RFTF_DATE: string,
  w_RPM: number, r_B_V_1X: number, r_B_V_6912FTF: number, r_B_V_32924FTF: number, r_B_V_32922FTF: number,
  r_B_V_Crestfactor: number, r_B_V_Demodulation: number, r_B_S_Fault1: number, r_B_S_Fault2: number, r_B_T_Temperature: number,
  ac_h: number, ac_v: number, ac_a: number, date: string
}

export type SensorWheelLeftInput = {
  idx: number, ai_LW: string, ai_LW_ALGO: string, ai_LW_MODEL: string, ai_LW_DATE: string,
  user_LW: string, user_LW_ID: string, user_LW_DATE: string,
  w_RPM: number, l_W_V_2X: number, l_W_V_3X: number, l_W_S_Fault3: number,
  ac_h: number, ac_v: number, ac_a: number, date: string
}

export type SensorWheelRightInput = {
  idx: number, ai_RW: string, ai_RW_ALGO: string, ai_RW_MODEL: string, ai_RW_DATE: string,
  user_RW: string, user_RW_ID: string, user_RW_DATE: string,
  w_RPM: number, r_W_V_2X: number, r_W_V_3X: number, r_W_S_Fault3: number,
  ac_h: number, ac_v: number, ac_a: number, date: string
}

export type SensorGearboxInput = {
  idx: number, ai_GEAR: string, ai_GEAR_ALGO: string, ai_GEAR_MODEL: string, ai_GEAR_DATE: string,
  user_GEAR: string, user_GEAR_ID: string, user_GEAR_DATE: string,
  w_RPM: number, g_V_OverallRMS: number, g_V_Wheel1X: number, g_V_Wheel2X: number,
  g_V_Pinion1X: number, g_V_Pinion2X: number, g_V_GMF1X: number, g_V_GMF2X: number,
  ac_h: number, ac_v: number, ac_a: number, date: string
}

export type SensorEngineInput = {
  idx: number, ai_ENGINE: string, ai_ENGINE_ALGO: string, ai_ENGINE_MODEL: string, ai_ENGINE_DATE: string,
  user_ENGINE: string, user_ENGINE_ID: string, user_ENGINE_DATE: string,
  w_RPM: number, e_V_OverallRMS: number, e_V_1_2X: number, e_V_1X: number,
  e_V_Crestfactor: number, ac_h: number, ac_v: number, ac_a: number, date: string
}




