import axios from "axios";
import authHeader from "../login/auth-header";

const API_URL = "http://localhost:8082/api/cim";

class CIMService {
  getAll(params) {
    return axios.get( API_URL + "/list", { headers: authHeader() , params });
  }

  get(id) {
    return axios.get( API_URL + `/info/${id}`, { headers: authHeader() , "Content-type": "application/json", });
  }

  create(data) {
    return axios.post( API_URL + "/create", data , { headers: authHeader() , "Content-type": "application/json", });
  }

  update(data) {
    return axios.post( API_URL + `/update`, data , { headers: authHeader() , "Content-type": "application/json", });
  }

  delete(id) {
    return axios.get( API_URL + `/delete/${id}` , { headers: authHeader() , "Content-type": "application/json", });
  }

}

export default new CIMService();