import axios from "axios";
import authHeader from "../login/auth-header";
import { BASE_URL } from "../url";

const API_URL = BASE_URL +"/api/vehicleManagement";

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