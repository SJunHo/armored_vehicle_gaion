import {

    SELECT_EACH_INFO,
    SELECT_ALL_VEHICLE
  } from "../types";
  
  import vehicleStatistics from "../../services/analysis/vehicleStatistics.service";
  
  export const selectEachInfo = (id) => async (dispatch) => {
    try {
      const res = await vehicleStatistics.getVehicleData(id);
      dispatch({
        type: SELECT_EACH_INFO,
        payload: res.data,
      });
      console.log(res.data);
    } catch(err){
      console.log(err);
    }
  };
  export const selectAllVehicle = () => async (dispatch) => {
    try{
      const res = await vehicleStatistics.getAllVehicleData();
      dispatch({
        type: SELECT_ALL_VEHICLE,
        payload: res.data,
      });
    } catch(err){
      console.log(err);
    }
  }