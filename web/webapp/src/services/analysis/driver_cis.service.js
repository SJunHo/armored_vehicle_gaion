import axios from "axios";
import authHeader from "../login/auth-header";
import { BASE_URL } from "../url";

const API_URL = BASE_URL +"/api/driverPostureCorrection";

class DriverCorrectionService {

  getList() {
    return axios.get( API_URL + `/list`, { headers: authHeader() , "Content-type": "application/json", });
  }

  updateList(data) {
    return axios.post( API_URL + "/update", data , { headers: authHeader() , "Content-type": "application/json", });
  }

  getSnsrList(){
    return axios.get( API_URL + `/snsrlist`, { headers: authHeader() , "Content-type": "application/json", });
  }
}

export default new DriverCorrectionService();