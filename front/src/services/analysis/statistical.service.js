import axios from "axios";
import authHeader from "../login/auth-header";

const API_URL = "http://localhost:8080/api/statistical";

class StatisticalService {

  getTree() {
    return axios.get( API_URL + `/info`, { headers: authHeader() , "Content-type": "application/json", });
  }

  getGraph(level,url,date){
    return axios.get( API_URL + `/graph/${level}/${url}/${date}` , { headers: authHeader() , "Content-type": "application/json", });
  }

  getTable(level,url,date){
    return axios.get( API_URL + `/table/${level}/${url}/${date}` , { headers: authHeader() , "Content-type": "application/json", });
  }
}

export default new StatisticalService();