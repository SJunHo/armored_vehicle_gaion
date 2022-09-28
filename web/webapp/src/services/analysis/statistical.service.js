import axios from "axios";
import authHeader from "../login/auth-header";
import { BASE_URL } from "../url";

const API_URL = BASE_URL +"/api/statistical";

class StatisticalService {

  getTree() {
    return axios.get( API_URL + `/info`, { headers: authHeader() , "Content-type": "application/json", });
  }

  getGraph(data){
    return axios.post( API_URL + `/graph`, data , { headers: authHeader() , "Content-type": "application/json", });
  }

  getTable(data){
    return axios.post( API_URL + `/table`, data , { headers: authHeader() , "Content-type": "application/json", });
  }
}

export default new StatisticalService();