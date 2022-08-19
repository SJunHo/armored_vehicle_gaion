import axios from "axios";
import authHeader from "../login/auth-header";

const API_URL = "http://localhost:8082/api/vehicleStatistics";

class vehicleStatistics {
  //김상희 추가
  getDataForChart(){
    return axios.get( API_URL + `/searchEachInfo` ,  { headers: authHeader() , "Content-type": "application/json", });
  }
  
  getVehicleData(id) {
    return axios.get( API_URL + `/searchEachInfo/${id}`, { headers: authHeader() , "Content-type": "application/json", });
  }

  getAllVehicleData() {
    return axios.get(API_URL + `/searchEachInfo/getAll`, {headers: authHeader() , "Content-type": "application/json", });
  }

  getAllFileWithId(id) {
    return axios.get(API_URL + `/searchEachInfo/getFile/${id}` , {headers: authHeader(), "Content-type": "application/json", });
  }

  getColumnsForBtnNummeric() {
    return axios.get(API_URL + `/searchEachInfo/getForBtn1` , {headers: authHeader(), "Content-type": "application/json", });
  }
  getColumnsForBtnCategoric() {
    return axios.get(API_URL + `/searchEachInfo/getForBtn2` , {headers: authHeader(), "Content-type" : "application/json", });
  }

  getChartData(param1, param2, param3){
    return axios.get(API_URL + `/searchEachInfo/getChartData/${param1}+${param2}+${param3}` , {headers: authHeader(), "Content-type" : "application/json", });
  }
  searchBookmark(id, grpid){
    return axios.get(API_URL + `/searchEachInfo/getBookmark/${id}/${grpid}`, {headers: authHeader(), "Content-type" : "application/json", });
  }
  insertBookmark(data){
    return axios.post( API_URL + "/searchEachInfo/insertBookmarks", data, {headers: authHeader(), "Content-type" : "application/json", });
  }
  getDtctsdaData(params1, params2, params3){
    return axios.get( API_URL + `/getDtctsdaData/${params1}/${params2}/${params3}`, {headers: authHeader(), "Content-type" : "application/json", });
  }

}


export default new vehicleStatistics();