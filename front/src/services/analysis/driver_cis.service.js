import axios from "axios";
import authHeader from "../login/auth-header";

const API_URL = "http://localhost:8082/api/driverPostureCorrection";

class ThresholdService {

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

export default new ThresholdService();