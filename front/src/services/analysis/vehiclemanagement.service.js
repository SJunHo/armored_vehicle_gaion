import axios from "axios";
import authHeader from "../login/auth-header";

const API_URL = "http://localhost:8082/api/vehicleManagement";

class StatisticalService {

  getVehicles(params) {
    return axios.get( API_URL + `/list`, { headers: authHeader() , params});
  }

  getVehicle(id){
    return axios.get( API_URL + `/info/${id}`, { headers: authHeader()});
  }

  getDivsList(){
    return axios.get( API_URL + `/divsList/`, { headers: authHeader()});
  }

  getBnList(){
    return axios.get( API_URL + `/bnList/`, { headers: authHeader()});
  }

  create(data) {
    return axios.post( API_URL + "/create", data , { headers: authHeader() , "Content-type": "application/json", });
  }

  update(id, data) {
    return axios.put( API_URL + `/update/${id}`, data , { headers: authHeader() , "Content-type": "application/json", });
  }

}

export default new StatisticalService();