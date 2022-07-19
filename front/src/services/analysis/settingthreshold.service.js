import axios from "axios";
import authHeader from "../login/auth-header";

const API_URL = "http://localhost:8080/api/threshold";

class ThresholdService {

  getThresholdList() {
    return axios.get( API_URL + `/list`, { headers: authHeader() , "Content-type": "application/json", });
  }

  updateList(data) {
    return axios.post( API_URL + "/update", data , { headers: authHeader() , "Content-type": "application/json", });
  }
}

export default new ThresholdService();